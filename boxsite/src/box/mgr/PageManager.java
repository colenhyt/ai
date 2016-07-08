package box.mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import box.site.model.TopItem;
import box.site.model.User;
import box.site.model.WebUrl;
import box.site.parser.sites.BaseTopItemParser;
import box.site.parser.sites.ImgGetter;
import cn.hd.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import easyshop.html.HTMLInfoSupplier;
import es.util.FileUtil;
import es.util.url.URLStrHelper;

public class PageManager extends MgrBase{
	private static PageManager uniqueInstance = null;
	private Set<String>	sitekeys;
	private Map<String,Map<String,WebUrl>> allSiteUrlsMap = Collections.synchronizedMap(new HashMap<String,Map<String,WebUrl>>());
//	private Map<String,List<TopItem>> viewListItemsMap = Collections.synchronizedMap(new HashMap<String,List<TopItem>>());
	private Map<Integer,Map<Long,Integer>> timeSortedCatsItemIdMap;
	private Map<Integer,Map<Integer,Long>>  catItemIdTimeMap = Collections.synchronizedMap(new HashMap<Integer,Map<Integer,Long>>());
	private Map<Integer,TopItem> viewItemsMap = Collections.synchronizedMap(new HashMap<Integer,TopItem>());
	private Map<Long, User>   userMap = Collections.synchronizedMap(new HashMap<Long,User>());
	private Set<String> loadedList = Collections.synchronizedSet(new HashSet<String>());
	private boolean inited = false;
	private BaseTopItemParser parser = new BaseTopItemParser();
	private HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();

	public static void main(String[] args) {
		PageManager.getInstance().init();
		PageManager.getInstance().resetTrainingurls();
		int itemid = 0;
		int dir = 1;
//		PageManager.getInstance()._findNewsitems(51, itemid, dir, -1);
//		itemid = -2053154274 ; //		,-1873341786
//		PageManager.getInstance()._findNewsitems(51, itemid, dir, -1);
//		PageManager.getInstance()._findNewsitems(51, itemid, dir, 34);
//		PageManager.getInstance()._findNewsitems(51, itemid, -1, 34);
//		PageManager.getInstance()._findNewsitems(51, itemid, 1, 34);
//		itemid = -1873341786 ; //		,-1873341786
//		PageManager.getInstance()._findNewsitems(51, itemid, 1, 34);
//		PageManager.getInstance()._findNewsitems(51, itemid, -1, 34);
//		PageManager.getInstance().resetTrainingurls();
		
		String url = "http://tech.ifeng.com/a/20160624/41628054_0.shtml";
		String reg = "http://tech.ifeng.com/a/[0-9]+/[0-9]+_[0-9].shtml";
//		System.out.println(url.matches(reg));

	}

	public static PageManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new PageManager();
		}
		return uniqueInstance;
	}

	class MapIntKeyComparator implements Comparator<Long>{

		@Override
		public int compare(Long int1, Long int2) {
			
			return int2.compareTo(int1);
		}
	}
	public void init(){
		if (inited) return;
		
		inited = true;
		
		sitekeys = new HashSet<String>();
		
		String userContent = FileUtil.readFile(userFilePath);
		if (userContent.trim().length()>0){
			Map<String,JSONObject> data = (Map<String,JSONObject>)JSON.parse(userContent);
			for (String sessionIdStr:data.keySet()){
				Long  sessionId = Long.valueOf(sessionIdStr);
				JSONObject json = data.get(sessionIdStr);
				User user = (User)JSON.parseObject(json.toJSONString(), User.class);
				userMap.put(sessionId, user);
			}
		}
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
		List<File> catfs = FileUtil.getFolders(itemPath);
		for (File f:catfs){
			if (!f.getName().matches("[0-9]+"))continue;
			List<File> itemfiles = FileUtil.getFiles(f.getAbsolutePath());
			for (File itemF:itemfiles){
				int itemid = Integer.valueOf(itemF.getName().substring(0,itemF.getName().indexOf(".")));
				String content = FileUtil.readFile(itemF);
				TopItem item = (TopItem)JSON.parseObject(content, TopItem.class);
				viewItemsMap.put(itemid, item);
			}
		}
		
		//load item time list:
		timeSortedCatsItemIdMap = Collections.synchronizedMap(new HashMap<Integer,Map<Long,Integer>>());
		List<File> catfs2 = FileUtil.getFolders(listPath);
		for (File f:catfs2){
			if (!f.getName().matches("[0-9]+"))continue;
			int catid = Integer.valueOf(f.getName());
			Map<Long,Integer> catItemIdMap = timeSortedCatsItemIdMap.get(catid);
			if (catItemIdMap==null){
				catItemIdMap = Collections.synchronizedMap(new TreeMap<Long,Integer>(new MapIntKeyComparator()));
				timeSortedCatsItemIdMap.put(catid, catItemIdMap);
			}
			Map<Integer,Long> catTimeItemMap = catItemIdTimeMap.get(catid);
			if (catTimeItemMap==null){
				catTimeItemMap = Collections.synchronizedMap(new HashMap<Integer,Long>());
				catItemIdTimeMap.put(catid, catTimeItemMap);
			}			
			List<File> subitemPaths = FileUtil.getFolders(f.getAbsolutePath());
			
			for (File datepath:subitemPaths){
				List<File> itemfiles = FileUtil.getFiles(datepath.getAbsolutePath());
				for (File itemF:itemfiles){
					long timeName = Long.valueOf(itemF.getName().substring(0,itemF.getName().indexOf(".")));
					String content = FileUtil.readFile(itemF);
					List<TopItem> itemidlist = new ArrayList<TopItem>();
					StringUtil.json2List(content, itemidlist, TopItem.class);
					for (TopItem item:itemidlist){
						catItemIdMap.put(item.getContentTime(), item.getId());
						catTimeItemMap.put(item.getId(), item.getContentTime());
					}					
//					for (int itemid:itemidlist){
//						catItemIdMap.put(timeName, itemid);
//						catTimeItemMap.put(itemid, timeName);
//					}
				}
			}
		}
	}
	
	public String getNews2(String url){
		String sitekey = URLStrHelper.getHost(url).toLowerCase();
		int code = url.hashCode();
		String content = FileUtil.readFile(pagesPath+sitekey+"/"+code+".html");
		TopItem item = new TopItem();
		item.setUrl(url);
		if (content.trim().length()>0){
			item = parser.parse(url, content);
				String context = item.getHtmlContent();
				Set<String> imgurls = ImgGetter.findImgUrls(url, context);
				for (String imgurl:imgurls){
					String rimgUrl = imgurl;
					if (imgurl.indexOf("http")<0){
						String urlHead = url.substring(0,url.lastIndexOf("/"));
						rimgUrl = urlHead+imgurl;
					}
					String fileType = rimgUrl.substring(rimgUrl.lastIndexOf("."));
					String imgFilePath = sitekey+"/"+rimgUrl.hashCode()+fileType;
					
					File f = new File(imgPath+imgFilePath);
					if (f.exists()){
						context = context.replace(imgurl, imgPath);
					}
				}
				item.setHtmlContent(context);				

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
	
	public String getNewsCount(int catid,int itemid,int dir){
	  int newsCount = 0;
	  List<TopItem> retitems = _findNewsitems(catid,itemid,dir,-1);
	  if (retitems!=null)
		  newsCount = retitems.size();
	  
	  return String.valueOf(newsCount);
	}
	
	public List<TopItem> _findNewsitems(int catid,int itemid,int dir,int count){
		Map<Integer,Long> timeMap = catItemIdTimeMap.get(catid);
		Map<Long,Integer> itemMap = timeSortedCatsItemIdMap.get(catid);
		if (timeMap==null||itemMap.size()<=0||itemMap==null||itemMap.size()<=0)
			return null;
		
		Long timeLine = null;
		int index = -1;
		if (itemid==0){
			index = 0;
			for (Long tl:itemMap.keySet()){
				timeLine = tl;
				break;
			}
		}else {
			timeLine = timeMap.get(itemid);
			if (timeLine==null)
				return null;
			
			if (!itemMap.containsKey(timeLine))
				return null;
		}
		
		Long[] times = new Long[itemMap.keySet().size()];
		itemMap.keySet().toArray(times);
		for (int i=0;i<times.length;i++){
			if (times[i].longValue()==timeLine.longValue()){
				index = i;
				break;
			}
		}
		
		
		int perCount = -1;
		if (count>0){							//指定数量
			if (dir==1){
				perCount = index>count?count:index;		//取较小值
			}else {
				int size = times.length - index;
				perCount = size>count?count:size;		//取较小值
			}
		}else{									//如果没有确定数量,每次随机返回5-15(最多)条:		
			int max=15;
	        int min=5;
	        //向前
	        if (dir==1){
	        	max = index>max?max:index;
	        	min = index>min?min:index;
	        }else {
	        	int size = times.length - index;
	        	max = size>max?max:size;
	        	min = size>min?min:size;
	        }
	        if (max<=0||min<=0){
				log.warn("max or min is error:max:"+max+",min:"+min);
				return null;	        	
	        }
	        Random random = new Random();
	        perCount = random.nextInt(max)%(max-min+1) + min;
		}
		if (perCount<=0){
			log.warn("perCount is 0");
			return null;
		}
		
		int start = -1;
		int end = -1;
        if (dir==1){
    		start = index-perCount;
    		end = index;        	
        }else{
        	start = index;
        	end = index+ perCount;
        }
		
        if (start<0||end<0){
        	log.warn("index error:start:"+start+",end:"+end);
        	return null;
        }
		List<TopItem> retitems = new ArrayList<TopItem>();
    	for (int j=start;j<end;j++){
    		int itemid2 = itemMap.get(times[j]);
    		TopItem item = viewItemsMap.get(itemid2);
    		retitems.add(item);
    	}
		log.warn("size: "+retitems.size());
		return retitems;
	}
	
	public String getNewslist(int catid,int itemid,int dir,int count){
		if (catid<0)
			return null;
		
		init();
		
		List<TopItem> retitems = _findNewsitems(catid,itemid,dir,count);
//		retitems = new ArrayList<TopItem>();
		for (int i=0;i<3;i++){
			TopItem item = new TopItem();
			item.setCat(51);
			item.setHtmlContent("aaa");
			item.setUrl("http://");
			item.setCtitle("a"+i+":"+System.currentTimeMillis());
			item.setSitekey("huxiu.com");
			item.setContentTime(System.currentTimeMillis());
			item.setId(1);
//			retitems.add(item);			
		}

		
		String newstr = JSON.toJSONString(retitems);
		String retstr = "{'cat':"+catid+",'news':"+newstr+",'itemid':"+itemid+",'dir':"+dir+"}";
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
				long itTime = Long.valueOf(parts[6]);
				String listcontent = FileUtil.readFile(str);
				List<Integer> newlist = (List<Integer>)JSON.parse(listcontent);
				Map<Long,Integer> catItemIdMap = timeSortedCatsItemIdMap.get(catid);
				Map<Integer,Long> catTimeMap = catItemIdTimeMap.get(catid);
				for (int itemid:newlist){
					catItemIdMap.put(itTime, itemid);
					catTimeMap.put(itemid, itTime);
				}
			}
					
		}
	}
	
	//发现有内容的最新的一个目录
	private boolean __findLatestItems(int catid,long currTime,List<TopItem> items){
		Map<Long,Integer> catItemIds = timeSortedCatsItemIdMap.get(catid);
		if (catItemIds==null||catItemIds.size()<=0)
			return false;
		
		Long[] idTimes = new Long[catItemIds.keySet().size()];
		catItemIds.keySet().toArray(idTimes);
		long startTimeKey = 0;
		for (int i=0;i<idTimes.length;i++){
			if (idTimes[i]>currTime&&currTime>=idTimes[i+1]){
				startTimeKey = idTimes[i+1];
			}
		}

		return false;
		
	}

	public void renameTrainingurlTitles(){
		Set<String> sites = new HashSet<String>();
//		sites.add("tmtpost.com");
//		sites.add("163.com");
//		sites.add("qq.com");
//		sites.add("cyzone.cn");
//		sites.add("geekpark.net");
		sites.add("techcrunch.cn");
		sites.add("techweb.com.cn");
		for (String sitekey:allSiteUrlsMap.keySet()){
			Map<String,WebUrl> siteUrlsMap = allSiteUrlsMap.get(sitekey);
			if (sites.contains(sitekey)){
				for (WebUrl item:siteUrlsMap.values()){
					String ppath = pagesPath + sitekey + "/"+ item.getUrl().hashCode()+".html";
					String fcontent = FileUtil.readFile(ppath);
					if (fcontent.trim().length()<=0) continue;
					log.warn("page size "+fcontent.length());
					htmlHelper.init(fcontent);
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
	public String getFavors(long clientSessionid){
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
		if (clientSessionid<=0)
			return null;
		
		User user = userMap.get(clientSessionid);
		if (user==null){
			user = new User();
			user.setSessionid(clientSessionid);
			userMap.put(user.getSessionid(),user);				
		}
		
		String itemstr = user.getFavos();
		if (itemstr==null)
			itemstr = String.valueOf(itemid);
		else if (itemstr.indexOf(String.valueOf(itemid))<0){
			itemstr += ","+itemid;
		}
		user.setFavos(itemstr);			
		
		FileUtil.writeFile(userFilePath,JSON.toJSONString(userMap));			
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
	
	protected String _keyPath(long time,int catid){
		Calendar c = Calendar.getInstance();
		if (time<=0)
			time = System.currentTimeMillis();
		c.setTimeInMillis(time);
		String datePath = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
		
		String key = catid+"_"+datePath;
		return key;
	}
	
}
