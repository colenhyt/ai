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

import box.site.PageContentGetter;
import box.site.model.TopItem;
import box.site.model.User;
import box.site.model.WebUrl;
import box.site.parser.sites.ImgGetter;
import cn.hd.util.StringUtil;

import com.alibaba.fastjson.JSON;

import easyshop.html.HTMLInfoSupplier;
import es.util.FileUtil;
import es.util.url.URLStrHelper;

public class PageManager extends MgrBase{
	private static PageManager uniqueInstance = null;
	private Set<String>	sitekeys;
	private Map<String,Map<String,WebUrl>> allSiteUrlsMap = new HashMap<String,Map<String,WebUrl>>();
	private Map<String,List<TopItem>> viewListItemsMap = Collections.synchronizedMap(new HashMap<String,List<TopItem>>());
	private Map<Integer,TopItem> viewItemsMap = new HashMap<Integer,TopItem>();
	private Map<Long, User>   userMap = new HashMap<Long,User>();
	private Set<String> loadedList = Collections.synchronizedSet(new HashSet<String>());
	private boolean inited = false;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	private PageContentGetter contentGetter = new PageContentGetter();

	public static void main(String[] args) {
		PageManager.getInstance().init();

//		PageManager.getInstance().resetTrainingurls();
		
		String url = "http://tech.ifeng.com/a/20160624/41628054_0.shtml";
		String reg = "http://tech.ifeng.com/a/[0-9]+/[0-9]+_[0-9].shtml";
		System.out.println(url.matches(reg));

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
			Map<String,WebUrl> siteurls2 = _getFileUrls(urlspath,folder.getName());
			if (siteurls2!=null) {
				allSiteUrlsMap.put(folder.getName(), siteurls2);
			}
		}
		
		//load view topitems:
		viewListItemsMap = new HashMap<String,List<TopItem>>();
		List<File> catfs = FileUtil.getFolders(listPath);
		for (File f:catfs){
			List<File> subitemPaths = FileUtil.getFolders(f.getAbsolutePath());
			
			for (File datepath:subitemPaths){
				
				String key = f.getName()+"_"+datepath.getName();
				List<File> itemfiles = FileUtil.getFiles(datepath.getAbsolutePath());
				if (itemfiles.size()>0){
					List<TopItem> items = new ArrayList<TopItem>();
					for (File itemf:itemfiles){
						String cc = FileUtil.readFile(itemf);
						if (cc!=null&&cc.trim().length()>0){
							StringUtil.json2List(cc, items,TopItem.class);
							loadedList.add(key+"_"+itemf.getName());
						}
					}
					Collections.sort(items);
					for (TopItem item:items){
						viewItemsMap.put(item.getId(), item);
					}
					viewListItemsMap.put(key, items);
				}
			}
		}
		
	}
	
	public synchronized void pushNewItem(TopItem item){
		String key = _keyPath(item.getContentTime(),item.getCat());
		//放入viewItemMap内存:
		List<TopItem> vitems = viewListItemsMap.get(key);
		if (vitems==null){
			vitems = Collections.synchronizedList(new ArrayList<TopItem>());
			viewListItemsMap.put(key, vitems);
		}
		vitems.add(item);
		Collections.sort(vitems);	
	}
	
	public void pushNewItems(Map<Integer,List<TopItem>> mapitems){
		
		//按日期入内存库,一天一个json:
		for (int catid:mapitems.keySet()){
			List<TopItem> citems = mapitems.get(catid);
			for (TopItem item:citems){
				pushNewItem(item);
			}
		}
	}
	
	public String getNews2(String url){
		String sitekey = URLStrHelper.getHost(url).toLowerCase();
		String content = FileUtil.readFile(pagesPath+sitekey+"/"+url.hashCode()+".html");
		TopItem item = new TopItem();
		item.setUrl(url);
		if (content.trim().length()>0){
			String title = contentGetter.getTitle(content);
			item.setCtitle(title);
			List<String> ccs = contentGetter.getHtmlContent(url, content);
			if (ccs!=null){
				String context = ccs.get(1);
				Set<String> imgurls = ImgGetter.findImgUrls(url, context);
				for (String imgurl:imgurls){
					String rimgUrl = imgurl;
					if (imgurl.indexOf("http")<0){
						String urlHead = url.substring(0,url.lastIndexOf("/"));
						rimgUrl = urlHead+imgurl;
					}
					String fileType = rimgUrl.substring(rimgUrl.lastIndexOf("."));
					String imgPath = "pics/"+sitekey+"/"+rimgUrl.hashCode()+fileType;
					
					File f = new File("c:/boxsite/"+imgPath);
					if (f.exists()){
						context = context.replace(imgurl, imgPath);
					}
				}
				item.setHtmlContent(context);				
			}

		}
			
		return JSON.toJSONString(item);
		
	}
	
	public String getNews(int itemid){
		TopItem item = viewItemsMap.get(itemid);
		if (item!=null){
			return JSON.toJSONString(item);
		}else {
			
		}
		
		return null;
	}
	
	public String getNewsCount(int catid,long startTime){
	  int newsCount = 0;
	  List<TopItem> retitems = _findNewsitems(catid,startTime);
	  if (retitems!=null)
		  newsCount = retitems.size();
	  
	  return String.valueOf(newsCount);
	}
	
	private List<TopItem> _findNewsitems(int catid,long startTime){
		//循环5天取有内容的当天topitem:
		List<TopItem> citems = new ArrayList<TopItem>();
		boolean get = __findLatestItems(catid,startTime,citems);
		if (!get)
			return citems;
		
		List<TopItem> retitems = new ArrayList<TopItem>();
		
		//每次返回5条:
		int perCount = 5;
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
				__findLatestItems(catid,startTime,citems2);
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
				__findLatestItems(catid,startTime,citems2);
				int pcount = perCount - retitems.size();
				int maxi = citems2.size()>pcount?pcount:citems2.size()-1;
				for (int i=maxi;i>=0;i--){
					retitems.add(citems2.get(i));
				}				
			}
		}		
		
		return retitems;
	}
	
	public String getNewslist(int catid,long startTime){
		if (catid<0)
			return null;
		
		List<TopItem> retitems = _findNewsitems(catid,startTime);
		
		String newstr = JSON.toJSONString(retitems);
		String retstr = "{'cat':"+catid+",'news':"+newstr+",'startTime':"+startTime+"}";
		return retstr;
	}
	
	public String getSiteTrainingUrls(String sitekey){
		Map<String,WebUrl> urls = allSiteUrlsMap.get(sitekey);
		if (urls!=null){
			Set<WebUrl> notUrls = new HashSet<WebUrl>();
			for (WebUrl url:urls.values()){
				if (url.getCat()>0)
					notUrls.add(url);
			}
			return JSON.toJSONString(notUrls);
		}
		return null;
	}
	
	public String getSiteNotTrainingUrls(String sitekey,boolean isAll){
		Map<String,WebUrl> urls = allSiteUrlsMap.get(sitekey);
		if (urls!=null){
			Set<WebUrl> notUrls = new HashSet<WebUrl>();
			for (WebUrl url:urls.values()){
				if (isAll||url.getCat()<=0){
					notUrls.add(url);
				}
			}
			return JSON.toJSONString(notUrls);
		}
		return null;
	}
	
	//定期扫最新的latest:
	@Override
	public void process(){
		log.warn("load latest list");
		List<File> files = FileUtil.getFiles(listPath);
		for (File f:files){
			if (!f.getName().endsWith(".latest")) continue;
			String lcontent = FileUtil.readFile(f);
			List<String> liststr = (List<String>)JSON.parse(lcontent);
			for (String str:liststr){
				String[] parts = str.split("/");
				int catid = Integer.parseInt(parts[4]);
				String keystr = catid+"_"+parts[5];
				String filestr = keystr+"_"+parts[6];
				if (loadedList.contains(filestr)) continue;
				
				String listcontent = FileUtil.readFile(str);
				List<TopItem> newlist = Collections.synchronizedList(new ArrayList<TopItem>());
				StringUtil.json2List(listcontent, newlist,TopItem.class);	
				List<TopItem> vitems = viewListItemsMap.get(keystr);
				if (vitems==null){
					vitems = Collections.synchronizedList(new ArrayList<TopItem>());
					viewListItemsMap.put(keystr, vitems);
				}
				vitems.addAll(newlist);
				for (TopItem item:newlist){
					viewItemsMap.put(item.getId(), item);
				}
				Collections.sort(vitems);	
			}
					
		}
	}
	
	//发现有内容的最新的一个目录
	private boolean __findLatestItems(int catid,long currTime,List<TopItem> items){
		//当天,往前5天或者往后5天
		long currT = currTime;
		if (currT==0)
			currT = System.currentTimeMillis();
		long findTime = Math.abs(currT);
		String key = _keyPath(findTime,catid);
		if (!viewListItemsMap.containsKey(key)){
			Calendar c = Calendar.getInstance();
			long dateSec = 60*60*24*1000;
			for (int i=1;i<6;i++){
				long du = dateSec*i;
				long dtime = findTime - du;
				if (currTime>0)
					dtime = findTime + du;
				
				if (dtime>System.currentTimeMillis()) break;
				
				c.setTimeInMillis(dtime);
				key = _keyPath(dtime,catid);
				if (viewListItemsMap.containsKey(key)){
					break;
				}
			}
		}
		if (viewListItemsMap.containsKey(key)){
			List<TopItem> sitems = viewListItemsMap.get(key);
			items.addAll(sitems);
			return true;
		}
		return false;
		
	}

	public void renameTrainingurlTitles(){
		Set<String> sites = new HashSet<String>();
		sites.add("tmtpost.com");
		for (String sitekey:allSiteUrlsMap.keySet()){
			Map<String,WebUrl> siteUrlsMap = allSiteUrlsMap.get(sitekey);
			if (sites.contains(sitekey)){
				for (WebUrl item:siteUrlsMap.values()){
					String ppath = pagesPath + sitekey + "/"+ item.getUrl().hashCode()+".html";
					String fcontent = FileUtil.readFile(ppath);
					if (fcontent.trim().length()<=0) continue;
					htmlHelper.init(fcontent.getBytes());
					String title = htmlHelper.getTitleContent();
					if (title!=null)						
						item.setText(title);					
				}
				File urlfile = new File(pagesPath+sitekey+"_urls.json");
				FileUtil.writeFile(urlfile, JSON.toJSONString(siteUrlsMap));			
			}				
		}
	}
	
	public void resetTrainingurls(){
		for (String sitekey:allSiteUrlsMap.keySet()){
			Map<String,WebUrl> siteUrlsMap = allSiteUrlsMap.get(sitekey);
			for (WebUrl item:siteUrlsMap.values()){
				item.setCat(0);
			}
			File urlfile = new File(pagesPath+sitekey+"_urls.json");
			FileUtil.writeFile(urlfile, JSON.toJSONString(siteUrlsMap));			
		}
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

	//获取收藏列表:
	public String getFavos(long clientSessionid){
		User user = userMap.get(clientSessionid);
		if (user!=null){
			String itemstr = user.getFavos();
			List<TopItem> items = new ArrayList<TopItem>();
			String[] itemids = itemstr.split(",");
			for (String itemidstr:itemids){
				int itemid = Integer.valueOf(itemidstr);
				TopItem item = viewItemsMap.get(itemid);
				items.add(item);
			}
			return JSON.toJSONString(items);
		}
		return null;
	}
	
	//收藏文章
	public String addFavo(long clientSessionid,int itemid){
		User user = userMap.get(clientSessionid);
		if (user!=null){
			String itemstr = user.getFavos();
			if (itemstr==null)
				itemstr = String.valueOf(itemid);
			else
				itemstr += ","+itemid;
		}
		return null;
	}
	
	//点赞文章
	public String addLike(long clientSessionid,int itemid){
		TopItem item = viewItemsMap.get(itemid);
		if (item!=null){
			item.setLike(item.getLike()+1);
		}
		//???持久化 itemMap:
		
		return null;
	}
	
	//user登录
	public String login(long clientSessionid){
		User user =  new User();
		//new user
		if (clientSessionid<=0){
			user.setSessionid(System.currentTimeMillis());
			userMap.put(user.getSessionid(),user);			
			FileUtil.writeFile(userFilePath,JSON.toJSONString(userMap));			
		}else {
			User user2 = userMap.get(clientSessionid);
			if (user2!=null)
				user.setSessionid(user2.getSessionid());
		}
				
		return JSON.toJSONString(user);
	}
	
	public String getSitekeys(){
		List<String> sitekeys = new ArrayList<String>();
		List<File> folders = FileUtil.getFolders(pagesPath);
		for (File folder:folders){
			sitekeys.add(folder.getName());
		}
		return JSON.toJSONString(sitekeys);
	}
	
	private String _keyPath(long time,int catid){
		Calendar c = Calendar.getInstance();
		if (time<=0)
			time = System.currentTimeMillis();
		c.setTimeInMillis(time);
		String datePath = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
		
		String key = catid+"_"+datePath;
		return key;
	}
	
}
