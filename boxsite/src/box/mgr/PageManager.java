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
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import box.news.NewsRecommender;
import box.site.classify.NewsClassifier;
import box.site.model.TopItem;
import box.site.model.User;
import box.site.model.WebUrl;
import box.site.parser.sites.BaseTopItemParser;
import box.site.parser.sites.ImgGetter;
import box.site.processor.SiteTermProcessor;
import cn.hd.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import easyshop.html.HTMLInfoSupplier;
import es.util.FileUtil;
import es.util.url.URLStrHelper;

public class PageManager extends MgrBase{
	private static PageManager uniqueInstance = null;
	private Set<String>	sitekeys;
	private Map<String,Map<String,WebUrl>> allSiteUrlsMap = Collections.synchronizedMap(new HashMap<String,Map<String,WebUrl>>());
//	private Map<String,List<TopItem>> viewListItemsMap = Collections.synchronizedMap(new HashMap<String,List<TopItem>>());
	private Map<Integer,Map<Long,Integer>> 		timeSortedCatsItemIdMap = Collections.synchronizedMap(new HashMap<Integer,Map<Long,Integer>>());
	private Map<Integer,Map<Integer,Long>>  catItemIdTimeMap = Collections.synchronizedMap(new HashMap<Integer,Map<Integer,Long>>());
	private Map<Integer,TopItem> viewItemsMap = Collections.synchronizedMap(new HashMap<Integer,TopItem>());
	private Map<Long, User>   userMap = Collections.synchronizedMap(new HashMap<Long,User>());
//	private Map<Long, List<UserView>>   userViewsMap = Collections.synchronizedMap(new HashMap<Long,List<UserView>>());
	private Map<String,Set<Integer>>   userViewsMap = Collections.synchronizedMap(new HashMap<String,Set<Integer>>());
	public final static int NEWS_CAT_REC = 1000;		//推荐分类
	private Set<String> loadedList = Collections.synchronizedSet(new HashSet<String>());
	private NewsRecommender commendar = new NewsRecommender();
	private boolean inited = false;
	private BaseTopItemParser parser;
	private HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();

	public static void main(String[] args) {
		PageManager.getInstance().init();
//		PageManager.getInstance().getNews(10, 192492392);
//		PageManager.getInstance().resetTrainingurls();
		PageManager.getInstance().resetCatUrlsByTitle();
//		PageManager.getInstance().findPagesMainContentAndTerms("techcrunch.cn");
//		PageManager.getInstance().findPagesMainContentAndTerms("sina.com.cn");//,techcrunch.cn,techweb.com.cn
//		PageManager.getInstance().findPagesMainContentAndTerms("sootoo.com");
//		PageManager.getInstance().resetCatUrlsByTitle();
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
		
//		parser = new BaseTopItemParser(dnaPath);
		
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
		
		List<File> files = FileUtil.getFiles(viewPath,"views");
		for (File viewF:files){
			int index = viewF.getName().lastIndexOf(".views");
			String key = viewF.getName().substring(0,index);
			String content = FileUtil.readFile(viewF);
			Set<Integer> itemids = new HashSet<Integer>();
			List<Integer> itemidlist =(List<Integer>)JSON.parse(content);
			itemids.addAll(itemidlist);
			userViewsMap.put(key,itemids);
		}
		
		//load item time list:
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
					String content = FileUtil.readFile(itemF);
					List<Integer> itemids = (List<Integer>)JSON.parseObject(content, ArrayList.class);
					for (Integer itemid:itemids){
						String itemPath = super.itemPath+catid+"/"+itemid+".item";
						String itemStr = FileUtil.readFile(itemPath);
						if (itemStr.trim().length()<=0) continue;
						TopItem item = (TopItem)JSON.parseObject(itemStr,TopItem.class);
						catItemIdMap.put(item.getContentTime(), item.getId());
						catTimeItemMap.put(item.getId(), item.getContentTime());
					}					
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
	
	//推荐新闻输出
	public String getRecNewslist(long clientSessionid,int itemid){
		String key = clientSessionid+"_"+NEWS_CAT_REC;
		Set<Integer> currids = userViewsMap.get(key);
		List<Integer> itemids = commendar.recNews(clientSessionid,currids);
		if (currids==null||currids.size()<=0){
			currids = new HashSet<Integer>();
			userViewsMap.put(key, currids);
		}
		if (itemids.size()>0){
			currids.addAll(itemids);
			FileUtil.writeFile(viewPath+key+".views", JSON.toJSONString(currids));
		}
		
		return JSON.toJSONString(itemids);
	}
	
	public String getNews(long clientSessionid,int itemid){
		TopItem item = viewItemsMap.get(itemid);
		if (item!=null){
			//log view:
			if (clientSessionid>0)
			{
				String key = clientSessionid+"_"+item.getCat();
				Set<Integer>  viewset = userViewsMap.get(key);
				if (viewset==null){
					viewset = new HashSet<Integer>();
					userViewsMap.put(key, viewset);
				}
				if (!viewset.contains(itemid)){
					viewset.add(itemid);
					FileUtil.writeFile(viewPath+key+".views", JSON.toJSONString(viewset));
				}
			}
			return JSON.toJSONString(item);
		}else {
			
		}
		
		return null;
	}
	
	public String getNewsCount(HttpServletRequest request){
		String catstr = request.getParameter("cat");
		int catid = Integer.valueOf(catstr);
		
		String itemidstr = request.getParameter("itemid");
		int itemid = 0;
		if (itemidstr!=null&&!itemidstr.equals("undefined")){
			 itemid = Integer.valueOf(itemidstr);
		}
		
		String dirstr = request.getParameter("dir");
		
		int dir = 1;
		if (dirstr!=null)
			 dir = Integer.valueOf(dirstr);
		
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
	
	public String getNewslist(HttpServletRequest request){
		long clientSessionid = -1;
		String sessionstr = request.getParameter("sessionid");
		if (sessionstr!=null&&sessionstr.matches("[0-9]+"))
			clientSessionid = Long.valueOf(sessionstr);
		
		String catstr = request.getParameter("cat");
		int catid = Integer.valueOf(catstr);
		
		String itemidstr = request.getParameter("itemid");
		int itemid = 0;
		if (itemidstr!=null&&itemidstr.matches("[0-9]+")){
			 itemid = Integer.valueOf(itemidstr);
		}
		
		String dirstr = request.getParameter("dir");
		
		int dir = 1;
		if (dirstr!=null)
			 dir = Integer.valueOf(dirstr);
		
		String countstr = request.getParameter("count");
		int count = -1;
		if (countstr!=null)
			 count = Integer.valueOf(countstr);
		
		if (catid<0)
			return null;
		
		//推荐分类
		if (catid==NEWS_CAT_REC){
			return getRecNewslist(clientSessionid,itemid);
		}
		
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
	
	public String getSiteTrainingUrls(String sitekey,int catid){
		Map<String,WebUrl> urls = allSiteUrlsMap.get(sitekey);
		if (urls!=null){
			Set<WebUrl> notUrls = new HashSet<WebUrl>();
			for (WebUrl url:urls.values()){
				if (url.getCat()==catid)
					notUrls.add(url);
			}
			return JSON.toJSONString(notUrls);
		}
		return null;
	}
	
	public String getSiteTrainingUrls(String sitekey){
		Map<String,WebUrl> urls = allSiteUrlsMap.get(sitekey);
		if (urls!=null){
			Set<WebUrl> notUrls = new HashSet<WebUrl>();
			for (WebUrl url:urls.values()){
				if (url.getCat()>0&&url.getCatStr()==null)
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
				if (isAll||url.getCat()<=0||(url.getCatStr()!=null&&url.getCatStr().equals("000"))){
//					String path = super.pagesPath+sitekey+"/"+url.getUrl().hashCode()+".html";
//					String pageContent = FileUtil.readFile(path);
//					TopItem topitem = parser.parse(url.getUrl(), pageContent);
//					if (topitem!=null){
//						url.setCat(topitem.getCat());
//					}
//					 String termPath = super.termPath+sitekey+"/"+url.getUrl().hashCode()+".terms";
//					 String content = FileUtil.readFile(termPath);
//						JSONArray ss = JSON.parseArray(content);
//						String termStr = "";
//						if (ss!=null){
//						int count = ss.size()<15?ss.size():15;
//						for (int i=0;i<count;i++){
//							JSONObject obj = (JSONObject)ss.get(i);
//							termStr += obj.getString("key")+":"+obj.getInteger("value")+";";
//							if (i%9==8)
//								termStr += "<br>";
//						}	
//						}
//						url.setTermsStr(termStr);
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
		List<File> files = FileUtil.getFiles(listPath);
		for (File f:files){
			if (!f.getName().endsWith(".latest")) continue;
			String lcontent = FileUtil.readFile(f);
			List<String> liststr = (List<String>)JSON.parse(lcontent);
			for (String str:liststr){
				String[] parts = str.split("/");
				int size = parts.length;
				int catid = Integer.parseInt(parts[size-3]);
				String keystr = catid+"_"+parts[size-2];
				String filestr = keystr+"_"+parts[size-1];
				if (loadedList.contains(filestr)) continue;
				String listcontent = FileUtil.readFile(str);
				if (listcontent.trim().length()<=0) continue;
				loadedList.add(filestr);
				List<Integer> newlist = (List<Integer>)JSON.parse(listcontent);
				Map<Long,Integer> catItemIdMap = timeSortedCatsItemIdMap.get(catid);
				Map<Integer,Long> catTimeMap = catItemIdTimeMap.get(catid);
				log.warn("load latestlist :"+str+",count:"+newlist.size());
				for (int itemid:newlist){
//					catItemIdMap.put(itTime, itemid);
//					catTimeMap.put(itemid, itTime);
					String itemPath = super.itemPath+catid+"/"+itemid+".item";
					String itemStr = FileUtil.readFile(itemPath);
					if (itemStr.trim().length()<=0) continue;
					TopItem item = (TopItem)JSON.parseObject(itemStr,TopItem.class);
					catItemIdMap.put(item.getContentTime(), item.getId());
					catTimeMap.put(item.getId(), item.getContentTime());					
					
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
				item.setCatStr(null);
				item.setCat(0);
			}
			File urlfile = new File(pagesPath+sitekey+"_urls.json");
			FileUtil.writeFile(urlfile, JSON.toJSONString(siteUrlsMap));			
		}
	}
	
	public void resetCatUrlsByTitle(){
		parser = new BaseTopItemParser(dnaPath);
		for (String sitekey:allSiteUrlsMap.keySet()){
			if (!sitekey.equalsIgnoreCase("163.com"))continue;
			Map<String,WebUrl> siteUrlsMap = allSiteUrlsMap.get(sitekey);
			int totalCount = siteUrlsMap.size();
			int catCount = 0;
			for (WebUrl item:siteUrlsMap.values()){
				if (item.getCat()>0&&item.getCatStr()==null) continue;
				String path = super.pagesPath+sitekey+"/"+item.getUrl().hashCode()+".html";
				String pageContent = FileUtil.readFile(path);
				TopItem topitem = parser.parse(item.getUrl(), pageContent);
				if (topitem!=null){
					item.setCat(topitem.getCat());
					item.setCatStr("000");
					if (topitem.getCat()>0){
						catCount++;
					}
				}
			}
			log.warn("总数量 "+totalCount+",分类数量"+catCount);
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
			item.setCatStr(url.getCatStr());
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

	public void findPagesMainContentAndTerms(){
		BaseTopItemParser parser= new BaseTopItemParser(super.dnaPath);
		SiteTermProcessor processor = null;
		for (String sitekey:allSiteUrlsMap.keySet()){
			Map<String,WebUrl> siteUrlsMap = allSiteUrlsMap.get(sitekey);
			String url = "http://www."+sitekey;
			processor = new SiteTermProcessor(url,10);
			for (WebUrl item:siteUrlsMap.values()){
				String path = super.pagesPath+sitekey+"/"+item.getUrl().hashCode()+".html";
				String pageContent = FileUtil.readFile(path);
				TopItem topitem = parser.parse(item.getUrl(), pageContent);
				if (topitem!=null){
					Map<String,Integer> termsMap = new HashMap<String,Integer>();
					processor.getWordTerms(topitem.getContent(), termsMap,1);
					
					  //通过比较器实现比较排序 
					List<Map.Entry<String,Integer>> mappingList = new ArrayList<Map.Entry<String,Integer>>(termsMap.entrySet()); 
				  Collections.sort(mappingList, new Comparator<Map.Entry<String,Integer>>(){ 
				   public int compare(Map.Entry<String,Integer> mapping1,Map.Entry<String,Integer> mapping2){ 
					   return mapping2.getValue().compareTo(mapping1.getValue()); 
				   } 
				  }); 
				  String termPath = super.termPath+sitekey+"/";
					String fileName = termPath +item.getUrl().hashCode()+".terms";
					FileUtil.writeFile(fileName, JSON.toJSONString(mappingList));
					log.warn(sitekey+"/"+item.getUrl().hashCode()+" get terms "+termsMap.size());
				}else
					log.warn("could not parse item: "+item.getUrl().hashCode());
			}
		}		
	}

	public void findPagesMainContentAndTerms(String sitekey){
		BaseTopItemParser parser= new BaseTopItemParser(super.dnaPath);
		SiteTermProcessor processor = null;
			Map<String,WebUrl> siteUrlsMap = allSiteUrlsMap.get(sitekey);
			String url = "http://www."+sitekey;
			processor = new SiteTermProcessor(url,10);
			for (WebUrl item:siteUrlsMap.values()){
				String path = super.pagesPath+sitekey+"/"+item.getUrl().hashCode()+".html";
				String pageContent = FileUtil.readFile(path);
				TopItem topitem = parser.parse(item.getUrl(), pageContent);
				if (topitem!=null){
					Map<String,Integer> termsMap = new HashMap<String,Integer>();
					processor.getWordTerms(topitem.getContent(), termsMap,2);
					
					  //通过比较器实现比较排序 
					List<Map.Entry<String,Integer>> mappingList = new ArrayList<Map.Entry<String,Integer>>(termsMap.entrySet()); 
				  Collections.sort(mappingList, new Comparator<Map.Entry<String,Integer>>(){ 
				   public int compare(Map.Entry<String,Integer> mapping1,Map.Entry<String,Integer> mapping2){ 
					   return mapping2.getValue().compareTo(mapping1.getValue()); 
				   } 
				  }); 
				  String termPath = super.termPath+sitekey+"/";
					String fileName = termPath +item.getUrl().hashCode()+".terms";
					FileUtil.writeFile(fileName, JSON.toJSONString(mappingList));
					log.warn(sitekey+"/"+item.getUrl().hashCode()+" get terms "+termsMap.size());
				}else
					log.warn("could not parse item: "+item.getUrl().hashCode());
			}
	}
	
}
