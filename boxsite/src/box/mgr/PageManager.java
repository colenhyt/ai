package box.mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import box.site.PageContentGetter;
import box.site.model.TopItem;
import box.site.model.WebUrl;
import cn.hd.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.FileUtil;

public class PageManager extends MgrBase{
	private static PageManager uniqueInstance = null;
	private Set<String>	sitekeys;
	private Map<String,Map<String,WebUrl>> siteUrls = new HashMap<String,Map<String,WebUrl>>();
	private Map<Integer,TopItem> currItems = new HashMap<Integer,TopItem>();
	private Map<String,Set<String>> savedUrls = new HashMap<String,Set<String>>();
	protected Logger  log = Logger.getLogger(getClass()); 
	String path = "c:/boxsite/data/pages/";
	String hispath = "c:/boxsite/data/hispages/";
	String traniningpath = "c:/boxsite/data/training/";
	private boolean inited = false;

	public static void main(String[] args) {
		PageManager.getInstance().init();
		String a = PageManager.getInstance().getSiteNotTrainingUrls("51cto.com",true);
		WebUrl w1 = new WebUrl();
		w1.setUrl("aaa");
		WebUrl w2 = new WebUrl();
		w2.setUrl("aaa");
		int c1 = w1.hashCode();
		int c2 = w2.hashCode();
		Set<WebUrl> set = new HashSet<WebUrl>();
		set.add(w1);
		boolean t = w1.hashCode()==w2.hashCode();
		System.out.println(a);
		PageManager.getInstance().addTrainingurls("51cto.com", a);

	}

	public static PageManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new PageManager();
		}
		return uniqueInstance;
	}

	public void init(){
		if (inited) return;
		
		inited = true;
		
		sitekeys = new HashSet<String>();
		
		List<File> folders = FileUtil.getFolders(path);
		for (File folder:folders){
			sitekeys.add(folder.getName());
			String urlspath = (path+folder.getName()+"_urls.json");
			Map<String,WebUrl> siteurls2 = getFileUrls(urlspath);
			if (siteurls2!=null) {
				siteUrls.put(folder.getName(), siteurls2);
			}
			String savedPath = (path+folder.getName()+"_done_urls.json");
			Set<String> siteurls3 = getFileUrls2(savedPath);
			if (siteurls3!=null) {
				savedUrls.put(folder.getName(), siteurls3);
			}			
		}
		log.warn("aaa "+siteUrls.toString());
	}
	
	private Set<String> getFileUrls2(String filePath){
		File urlfile = new File(filePath);
		if (!urlfile.exists()) return null;
		
		String content = FileUtil.readFile(filePath);
		Map<String,JSONObject> urls = JSON.parseObject(content,HashMap.class);
		Set<String> siteurls2 = new HashSet<String>();
		for (String url:urls.keySet()){
			JSONObject json = urls.get(url);
			String item = JSON.parseObject(json.toJSONString(),String.class);
			if (item==null||item.trim().length()<=0) continue;
			siteurls2.add(item);
		}		
		return siteurls2;
	}
	
	public String getCatNews(int catid,int startTime){
		if (catid<=0)
			return null;
		
		Date d = new Date();
		//循环5天取有内容的当天topitem:
		String filePath = findLatestItemPath(catid);
		if (filePath==null)
			return null;
		
		String content = FileUtil.readFile(filePath);
		List<TopItem> citems = new ArrayList<TopItem>();
		StringUtil.json2List(content, citems,TopItem.class);
		Collections.sort(citems);
		
		//返回最新:
		if (startTime<=0)
			return content;
		else {
		}
		
		return null;
	}
	
	public String getSiteNotTrainingUrls(String sitekey,boolean isAll){
		Map<String,WebUrl> urls = siteUrls.get(sitekey);
		if (urls!=null){
			Set<WebUrl> notUrls = new HashSet<WebUrl>();
			for (WebUrl url:urls.values()){
				if (isAll||url.getCat()<=0)
					notUrls.add(url);
			}
			return JSON.toJSONString(notUrls);
		}
		return null;
	}
	
	//获取正文，入库
	public void process(){
		List<File> folders = FileUtil.getFolders(path);
		
		List<TopItem> newItems = new ArrayList<TopItem>();
		for (File folder:folders){
			String sitekey = folder.getName();
			//获取该站点所有网页正文
			Map<String,WebUrl> urls = siteUrls.get(sitekey);
			Set<String> saves = savedUrls.get(sitekey);
			for (String url:urls.keySet()){
				WebUrl item = urls.get(url);
				if (item.getCat()<=0) continue;
				if (saves.contains(url)) continue;
				String filePath = path + sitekey + "/"+ url.hashCode()+".html";
				String pageContent = FileUtil.readFile(filePath);
				//获取正文:
				String content = PageContentGetter.getContent(pageContent);
				if (content==null){
					log.warn("could not get content "+filePath);
					continue;
				}
				TopItem titem = new TopItem();
				titem.setCat(item.getCat());
				titem.setUrl(url);
				titem.setContent(content);
				titem.setCrDate(new Date());
				titem.setId(url.hashCode());
				titem.setSitekey(sitekey);
				newItems.add(titem);
				currItems.put(titem.getId(),titem);
			}
			if (newItems.size()<=0){
				continue;
			}
			
			//入库后处理:
			for (TopItem item2:newItems){
				//放到已完成列表:
				saves.add(item2.getUrl());
				//移除当前url:
				urls.remove(item2.getUrl());
				//page移到历史库,删除当前网页:
				String fileName = item2.getUrl().hashCode()+".html";
				String fileP = path+sitekey+"/"+fileName;
				String pageC = FileUtil.readFile(fileP);
				FileUtil.writeFile(hispath+sitekey+"/"+fileName,pageC);
				FileUtil.del(fileP);
			}
			File urlfile = new File(path+sitekey+"_urls.json");
			FileUtil.writeFile(urlfile, JSON.toJSONString(urls));
			urlfile = new File(path+sitekey+"_done_urls.json");
			FileUtil.writeFile(urlfile, JSON.toJSONString(savedUrls));		
		}

		Map<Integer,List<TopItem>> mapitems = new HashMap<Integer,List<TopItem>>();
		//topitem 入库:
		for (TopItem item:newItems){
			List<TopItem> catItems = mapitems.get(item.getCat());
			if (catItems==null){
				catItems = new ArrayList<TopItem>();
			}
			catItems.add(item);
			mapitems.put(item.getCat(), catItems);
		}
		
		Date currDate = new Date();
		//按日期入库,一天一个json:
		for (int catid:mapitems.keySet()){
			String itempath = itemPath(currDate,catid);
			String content = FileUtil.readFile(itempath);
			List<TopItem> citems = mapitems.get(catid);
			if (content!=null&&content.trim().length()>0){
				StringUtil.json2List(content, citems,TopItem.class);
			}
			FileUtil.writeFile(itempath, JSON.toJSONString(citems));
		}

	}
	
	//发现有内容的最新的一个目录
	private String findLatestItemPath(int catid){
		//当天,-5天
		Calendar c = Calendar.getInstance();
		String path = itemPath(new Date(),catid);
		File f = new File(path);
		if (f.exists()){
			return path;
		}
		
		long dateSec = 60*60*24*1000;
		for (int i=0;i<5;i++){
			long du = dateSec*i;
			long dtime = System.currentTimeMillis()-du;
			c.setTimeInMillis(dtime);
			path = itemPath(c.getTime(),catid);
			File ff = new File(path);
			if (ff.exists()){
				return path;
			}
		}
		
		return path;
		
	}
	
	private String itemPath(Date currDate,int catid){
		Calendar c = Calendar.getInstance();
		c.setTime(currDate);
		String datePath = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
		String itemPath = "data/items/";
		String itempath = itemPath+catid+"/"+datePath+"_items.json";
		return itempath;
	}
	
	public String addTrainingurls(String sitekey,String tradingUrlsStr){
		Map<String,WebUrl> urls = siteUrls.get(sitekey);
		if (urls==null){
			urls = new HashMap<String,WebUrl>();
			siteUrls.put(sitekey,urls);
		}
		
		Set<WebUrl> urls2 = new HashSet<WebUrl>();
		StringUtil.json2Set(tradingUrlsStr, urls2,WebUrl.class);
		for (WebUrl url:urls2){
			urls.put(url.getUrl(),url);
			
			//copy cat url page to training path:
			String fileName = url.getUrl().hashCode()+".html";
			String fileP = path+sitekey+"/"+fileName;
			String pageC = FileUtil.readFile(fileP);
			FileUtil.writeFile(traniningpath+url.getCat()+"/"+fileName,pageC);			
		}
		
		//update url cat type:
		File urlfile = new File(path+sitekey+"_urls.json");
		FileUtil.writeFile(urlfile, JSON.toJSONString(siteUrls));
		
		
		
		return "";
	}

	private Map<String,WebUrl> getFileUrls(String filePath){
		File urlfile = new File(filePath);
		if (!urlfile.exists()) return null;
		
		String content = FileUtil.readFile(filePath);
		Map<String,JSONObject> urls = JSON.parseObject(content,HashMap.class);
		Map<String,WebUrl> siteurls2 = new HashMap<String,WebUrl>();
		for (String url:urls.keySet()){
			JSONObject json = urls.get(url);
			WebUrl item = JSON.parseObject(json.toJSONString(),WebUrl.class);
			if (item.getText()==null||item.getText().trim().length()<=0) continue;
			siteurls2.put(item.getUrl(),item);
		}		
		return siteurls2;
	}
	
}
