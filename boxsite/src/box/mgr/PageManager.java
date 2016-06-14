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
import box.site.classify.NewsClassifier;
import box.site.model.TopItem;
import box.site.model.WebUrl;
import cn.hd.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.FileUtil;

public class PageManager extends MgrBase{
	private static PageManager uniqueInstance = null;
	private Set<String>	sitekeys;
	private NewsClassifier newsClassifier = new NewsClassifier();
	private Map<String,Map<String,WebUrl>> allSiteUrlsMap = new HashMap<String,Map<String,WebUrl>>();
	private Map<Integer,TopItem> processItemsMap = new HashMap<Integer,TopItem>();
	private Map<String,List<TopItem>> viewItemsMap = new HashMap<String,List<TopItem>>();
	private Map<String,Set<String>> savedUrlsMap = new HashMap<String,Set<String>>();
	protected Logger  log = Logger.getLogger(getClass()); 
	String pagesPath = "c:/boxsite/data/pages/";
	String traniningpath = "c:/boxsite/data/training/";
	String itemPath = "c:/boxsite/data/items/";
	private boolean inited = false;

	public static void main(String[] args) {
		PageManager.getInstance().init();
		PageManager.getInstance().process();

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
		
		List<File> folders = FileUtil.getFolders(pagesPath);
		for (File folder:folders){
			sitekeys.add(folder.getName());
			String urlspath = (pagesPath+folder.getName()+"_urls.json");
			Map<String,WebUrl> siteurls2 = getFileUrls(urlspath);
			if (siteurls2!=null) {
				allSiteUrlsMap.put(folder.getName(), siteurls2);
			}
			String savedPath = (pagesPath+folder.getName()+"_done_urls.json");
			Set<String> siteurls3 = getFileUrls2(savedPath);
			if (siteurls3!=null) {
				savedUrlsMap.put(folder.getName(), siteurls3);
			}			
		}
		
		
		//load view topitems:
		viewItemsMap = new HashMap<String,List<TopItem>>();
		List<File> catfs = FileUtil.getFolders(itemPath);
		for (File f:catfs){
			List<File> subitemPaths = FileUtil.getFolders(f.getAbsolutePath());
			
			for (File datepath:subitemPaths){
				
				String key = f.getName()+"_"+datepath.getName();
				List<File> itemfiles = FileUtil.getFiles(datepath.getAbsolutePath());
				if (itemfiles.size()>0){
					List<TopItem> items = new ArrayList<TopItem>();
					for (File itemf:itemfiles){
						String cc = FileUtil.readFile(itemf);
						if (cc!=null&&cc.trim().length()>0)
							StringUtil.json2List(cc, items,TopItem.class);	
					}
					Collections.sort(items);
					viewItemsMap.put(key, items);
				}
			}
		}
		
		log.warn("aaa "+allSiteUrlsMap.toString());
	}
	
	private Set<String> getFileUrls2(String filePath){
		File urlfile = new File(filePath);
		if (!urlfile.exists()) return null;
		
		String content = FileUtil.readFile(filePath);
		List<String> urls = (List<String>)JSON.parse(content);
		Set<String> siteurls2 = new HashSet<String>();
		siteurls2.addAll(urls);
		return siteurls2;
	}
	
	public String getCatNews(int catid,long startTime){
		if (catid<=0)
			return null;
		
		//循环5天取有内容的当天topitem:
		List<TopItem> citems = new ArrayList<TopItem>();
		boolean get = findLatestItems(catid,startTime,citems);
		if (!get)
			return JSON.toJSONString(citems);
		
		List<TopItem> retitems = new ArrayList<TopItem>();
		
		//每次返回30条:
		int perCount = 10;
		if (startTime>0){			//最新
			int starti = 0;
			int ii = 0;
			for (int i=0;i<citems.size();i++){				
				if (citems.get(i).getContentTime()>=startTime){
					starti = i;
					break;
				}
			}
			//从后往前
			for (int j=starti;j<citems.size();j++){
				if (ii>=perCount) break;
				retitems.add(citems.get(j));
				ii++;				
			}
			//跨后一日:
			if (retitems.size()<perCount){
				startTime += 3600*24*1000;
				List<TopItem> citems2 = new ArrayList<TopItem>();
				findLatestItems(catid,startTime,citems2);
				int pcount = perCount - retitems.size();
				int maxi = citems2.size()>pcount?pcount:citems2.size()-1;
				for (int i=0;i<maxi;i++){
					retitems.add(citems2.get(i));
				}
			}			
		}else {
			int starti = citems.size()-1;
			//初次获取，返回30条:
			if (startTime==0){
				perCount = 30;		//首次，30条
			}else {
				startTime = 0 - startTime;
				for (int i=citems.size()-1;i>=0;i--){				
					if (citems.get(i).getContentTime()<=startTime){
						starti = i;
						break;
					}
				}
				
			}
			int ii = 0;
			//从前往后:
			for (int i=starti;i>=0;i--){
				if (ii>=perCount) break;
				retitems.add(citems.get(i));
				ii++;
			}
			//跨前一日
			if (startTime<0&&retitems.size()<perCount){
				startTime -= 3600*24*1000;
				List<TopItem> citems2 = new ArrayList<TopItem>();
				findLatestItems(catid,startTime,citems2);
				int pcount = perCount - retitems.size();
				int maxi = citems2.size()>pcount?pcount:citems2.size()-1;
				for (int i=maxi;i>=0;i--){
					retitems.add(citems2.get(i));
				}				
			}
		}
		
		return JSON.toJSONString(retitems);
	}
	
	public String getSiteNotTrainingUrls(String sitekey,boolean isAll){
		Map<String,WebUrl> urls = allSiteUrlsMap.get(sitekey);
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
		List<File> folders = FileUtil.getFolders(pagesPath);
		
		List<TopItem> newItems = new ArrayList<TopItem>();
		for (File folder:folders){
			String sitekey = folder.getName();
			//获取该站点所有网页正文
			Map<String,WebUrl> urls = allSiteUrlsMap.get(sitekey);
			Set<String> saves = savedUrlsMap.get(sitekey);
			for (String url:urls.keySet()){
				WebUrl item = urls.get(url);
				if (saves!=null&&saves.contains(url)) continue;
				String filePath = pagesPath + sitekey + "/"+ url.hashCode()+".html";
				String pageContent = FileUtil.readFile(filePath);
				//获取正文:
				String content = PageContentGetter.getContent(pageContent);
				if (content==null){
					log.warn("could not get content "+filePath);
					continue;
				}
				TopItem titem = new TopItem();
				titem.setUrl(url);
				titem.setId(url.hashCode());
				titem.setContent(content);
				//尚未分类，即可分类:
				if (item.getCat()<=0) {
//					int catid = newsClassifier.testClassify(titem);
//					if (catid<=0) continue;
//					titem.setCat(catid);
					continue;
				};
				titem.setCat(item.getCat());
				titem.setCrDate(new Date());
				titem.setSitekey(sitekey);
				File f = new File(filePath);
				if (f.exists()){
					titem.setContentTime(f.lastModified());
				}
				newItems.add(titem);
				processItemsMap.put(titem.getId(),titem);
			}
			if (newItems.size()<=0){
				continue;
			}
			
			if (saves==null){
				saves = new HashSet<String>();
				
			}
			//入库后处理:
			for (TopItem item2:newItems){
				//放到已完成列表:
				saves.add(item2.getUrl());
				//移除当前url:
				urls.remove(item2.getUrl());
			}
			savedUrlsMap.put(sitekey, saves);
			
			FileUtil.writeFile(pagesPath+sitekey+"_urls.json", JSON.toJSONString(urls));
			FileUtil.writeFile(pagesPath+sitekey+"_done_urls.json", JSON.toJSONString(saves));		
		}

		Map<Integer,List<TopItem>> mapitems = new HashMap<Integer,List<TopItem>>();
		//topitem 入map库:
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
		Calendar c = Calendar.getInstance();
		for (int catid:mapitems.keySet()){
			c.setTime(currDate);
			List<TopItem> citems = mapitems.get(catid);
			String key = keyPath(c.getTime(),catid);
			//放入viewItemMap内存:
			List<TopItem> vitems = viewItemsMap.get(key);
			if (vitems==null){
				vitems = new ArrayList<TopItem>();
			}
			vitems.addAll(citems);
			Collections.sort(vitems);
			viewItemsMap.put(key, vitems);
			
			//topitem 落地
			String datePath = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
			String itempath = itemPath+catid+"/"+datePath+"/"+currDate.getTime()+"_items.json";			
			String content = FileUtil.readFile(itempath);
			if (content!=null&&content.trim().length()>0){
				StringUtil.json2List(content, citems,TopItem.class);
			}
			FileUtil.writeFile(itempath, JSON.toJSONString(citems));
			
			for (TopItem item2:citems){
			//page移到历史库,删除当前网页:
			String fileName = item2.getUrl().hashCode()+".html";
			String fileP = pagesPath+item2.getSitekey()+"/"+fileName;
			String pageC = FileUtil.readFile(fileP);
//			FileUtil.writeFile(hispath+item2.getSitekey()+"/"+fileName,pageC);
//			FileUtil.del(fileP);		
			}
		}
	}
	
	//发现有内容的最新的一个目录
	private boolean findLatestItems(int catid,long currTime,List<TopItem> items){
		//当天,往前5天或者往后5天
		long findTime = Math.abs(currTime);
		String key = keyPath(new Date(findTime),catid);
		if (!viewItemsMap.containsKey(key)){
			Calendar c = Calendar.getInstance();
			long dateSec = 60*60*24*1000;
			for (int i=0;i<5;i++){
				long du = dateSec*i;
				long dtime = findTime - du;
				if (currTime>0)
					dtime = findTime + du;
				
				if (dtime>System.currentTimeMillis()) break;
				
				c.setTimeInMillis(dtime);
				key = keyPath(c.getTime(),catid);
				if (viewItemsMap.containsKey(key)){
					break;
				}
			}
		}
		if (viewItemsMap.containsKey(key)){
			items = viewItemsMap.get(key);
			return true;
		}
		return false;
		
	}

	@Override
	public void update(){
		this.process();
	}
	
	public String addTrainingurls(String sitekey,String tradingUrlsStr){
		Map<String,WebUrl> siteUrlsMap = allSiteUrlsMap.get(sitekey);
		if (siteUrlsMap==null){
			siteUrlsMap = new HashMap<String,WebUrl>();
			allSiteUrlsMap.put(sitekey,siteUrlsMap);
		}
		
		List<WebUrl> urls2 = (List<WebUrl>)JSON.parseArray(tradingUrlsStr, WebUrl.class);
		for (WebUrl url:urls2){
			WebUrl item = siteUrlsMap.get(url.getUrl());
			item.setCat(url.getCat());
			siteUrlsMap.put(url.getUrl(),item);
		}
		
		//update url cat type:
		File urlfile = new File(pagesPath+sitekey+"_urls.json");
		FileUtil.writeFile(urlfile, JSON.toJSONString(siteUrlsMap));
		
		
		return "";
	}

	public String getSitekeys(){
		List<String> sitekeys = new ArrayList<String>();
		List<File> folders = FileUtil.getFolders(pagesPath);
		for (File folder:folders){
			sitekeys.add(folder.getName());
		}
		return JSON.toJSONString(sitekeys);
	}
	
	private Map<String,WebUrl> getFileUrls(String filePath){
		File urlfile = new File(filePath);
		if (!urlfile.exists()) return null;
		
		String content = FileUtil.readFile(filePath);
		Map<String,JSONObject> urls = (Map<String,JSONObject>)JSON.parse(content);
		Map<String,WebUrl> siteurls2 = new HashMap<String,WebUrl>();
		for (JSONObject json:urls.values()){
			WebUrl item = JSON.parseObject(json.toJSONString(), WebUrl.class);
			if (item==null||item.getText()==null||item.getText().trim().length()<=0) continue;
			siteurls2.put(item.getUrl(),item);
		}		
		return siteurls2;
	}

	private String keyPath(Date currDate,int catid){
		Calendar c = Calendar.getInstance();
		c.setTime(currDate);
		String datePath = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
		
		String key = catid+"_"+datePath;
		return key;
	}
	
}
