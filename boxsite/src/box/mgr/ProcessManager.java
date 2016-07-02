package box.mgr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import box.site.PageContentGetter;
import box.site.classify.NewsClassifier;
import box.site.model.TopItem;
import box.site.model.WebUrl;
import box.site.parser.sites.BaseTopItemParser;
import box.site.parser.sites.ImgGetterThread;
import box.site.parser.sites.MultiPageTask;
import cn.hd.util.SimHash;
import es.util.FileUtil;

public class ProcessManager extends MgrBase {
	private static ProcessManager uniqueInstance = null;
	private Set<String>	sitekeys = new HashSet<String>();
	private boolean inited = false;
	private boolean running = true;
	private int runningSpiderCount = 0;
	private boolean processStart = false;
	//最新批次topitems:
	private List<TopItem> newTopitemList = Collections.synchronizedList(new ArrayList<TopItem>());
	private Map<Integer,Map<String,String>>	pageSimHashMap = Collections.synchronizedMap(new HashMap<Integer,Map<String,String>>());
	private NewsClassifier newsClassifier = new NewsClassifier();
	private Map<String,Map<String,WebUrl>> allSiteUrlsMap = new HashMap<String,Map<String,WebUrl>>();
	private Map<Integer,TopItem> processItemsMap = new HashMap<Integer,TopItem>();
	private Map<String,Set<String>> savedUrlsMap = new HashMap<String,Set<String>>();
	private PageContentGetter contentGetter = new PageContentGetter();
    private BaseTopItemParser parser = new BaseTopItemParser();
	ImgGetterThread imgGetter = new ImgGetterThread();

	public static ProcessManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ProcessManager();
		}
		return uniqueInstance;
	}

	public void init(){
		if (inited) return;
		
		inited = true;	
		running = true;
		
		List<File> folders = FileUtil.getFolders(pagesPath);
		for (File folder:folders){
			sitekeys.add(folder.getName());
			String urlspath = (pagesPath+folder.getName()+"_urls.json");
			Map<String,WebUrl> siteurls2 = _getFileUrls(urlspath,folder.getName());
			if (siteurls2!=null) {
				allSiteUrlsMap.put(folder.getName(), siteurls2);
			}
			String savedPath = (pagesPath+folder.getName()+"_done_urls.json");
			Set<String> siteurls3 = _getFileUrls2(savedPath);
			if (siteurls3!=null) {
				savedUrlsMap.put(folder.getName(), siteurls3);
			}			
		}		
		
		List<File> dictFiles = FileUtil.getFiles(dictPath);
//		for (File f:dictFiles){
//			String content = FileUtil.readFile(f);
//    		if (content!=null&&content.trim().length()>0){
//    			String catstr = f.getName().substring(0,f.getName().indexOf("."));
//    			int catid = Integer.valueOf(catstr);
//    			Map<String,String>  catDictMap = Collections.synchronizedMap(new HashMap<String,String>());
//    			Map<String,JSONObject> jsonstr = (Map<String,JSONObject>)JSON.parseObject(content, HashMap.class);
//    			for (String simStr:jsonstr.keySet()){
//    				catDictMap.put(simStr, simStr);
//    			}
//    			pageSimHashMap.put(catid, catDictMap);
//    		}			
//		}		
	}
	
	public void tesSimHash(){
		List<File> urlFiles = FileUtil.getFiles(pagesPath);
		for (File urlF:urlFiles){
			int index = urlF.getName().indexOf(".urls");
			if (index<=0) continue;
			String sitekey = urlF.getName().substring(0,index);
			String urlContent = FileUtil.readFile(urlF);
			List<String> urls = (List<String>)JSON.parse(urlContent);
			Set<String> urlSet = new HashSet<String>();
			urlSet.addAll(urls);
			for (String url:urls){
				String filePath = pagesPath+sitekey+"/"+url.hashCode()+".html";
				String pageContent = FileUtil.readFile(filePath);
				if (pageContent.trim().length()<=0) continue;
				log.warn("parse "+url);
				TopItem item = parser.parse(url, pageContent);
				if (item==null){
					//log.warn("could not parse: "+strCode);
					continue;
				}
//				log.warn("parse "+sitekey+",file "+url);
				this.hasDocSim(1, url, item.getContent());				
				
			}
		}
	}
	public void spiderFinished(String sitekey){
		runningSpiderCount--;
		log.warn(sitekey+ " spider finished");
		
		if (runningSpiderCount>0) return;
		
		log.warn("all sites spiders finished,sleep..");
		
		//处理该频次新增topitem:
		boolean hasNew = processListUrls(newTopitemList);
		
		//运转间隔
		final int PROCESS_DURATION = 5 * 1000;		//1小时:60 * 60 * 1000;
		try {
			Thread.sleep(PROCESS_DURATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		processStart = false;
	}
	
	public synchronized void pushNewItem(TopItem item){
		newTopitemList.add(item);
	}
	
	public void processSpiders(){
	
		//spider:
		Set<String> sites = new HashSet<String>();
		sites.add("http://www.tmtpost.com");
//		sites.add("http://www.leiphone.com");
//		sites.add("http://www.huxiu.com");
//		sites.add("http://www.iheima.com/");
//		sites.add("http://www.pintu360.com/");
//		sites.add("http://www.ikanchai.com/");
//		sites.add("http://www.iyiou.com/");
//		sites.add("http://www.techweb.com.cn/");
//		sites.add("http://www.ifanr.com/");
//		sites.add("http://www.cyzone.cn/");
//		sites.add("http://www.sootoo.com/");
		
//		sites.add("http://tech.163.com");
//		sites.add("http://tech.qq.com/");
//		sites.add("http://tech.sina.com.cn/");
		sites.add("http://it.sohu.com");
//		sites.add("http://tech.ifeng.com/");
//		sites.add("http://www.geekpark.net/");
		sites.add("http://techcrunch.cn/");
		
		
//		sites.add("http://www.ebrun.com/");
		
		log.warn("spiders start,sites:"+sites.size());
		runningSpiderCount = sites.size();
		for (String site:sites){
			MultiPageTask task = new MultiPageTask(this,site,300);
			Thread t2=new Thread(task);
			t2.start();
		}
		
	
	}
	
	public boolean hasDocSim(int catid,String url,String blockContent){
		PageManager pageMgr = PageManager.getInstance();
		pageMgr.init();
		
		boolean has = false;
		Map<String,String> catDictMap = pageSimHashMap.get(catid);
		if (catDictMap==null){
			//return has;
			catDictMap = new HashMap<String,String>();
			pageSimHashMap.put(catid, catDictMap);
		}
		
		try {
			SimHash sim = new SimHash(blockContent, 64);
			List<String> simStrs = sim.toTables();
			for (String simStr:simStrs){
				if (catDictMap.containsKey(simStr)){
					has = true;
					String relUrl = catDictMap.get(simStr);
					String content = pageMgr.getNews2(relUrl);
					TopItem item = (TopItem)JSON.parseObject(content, TopItem.class);
					log.warn("相似文档 : "+catDictMap.get(simStr)+",相似内容:"+item.getContent());
					log.warn("新文档 : "+catDictMap.get(simStr)+",当前内容:"+blockContent);
					break;
				}
			}
			if (!has){
				for (String simStr:simStrs){
					catDictMap.put(simStr, url);
				}
//				String fileName = dictPath+catid+".dict";
//				FileUtil.writeFile(fileName, JSON.toJSONString(catDictMap));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return has;
	}
	@Override
	public void process(){
		
		try {
			while (running){
				if (!processStart){
					this.processSpiders();
					processStart = true;
				}
				Thread.sleep(1000*5);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	//获取正文，items入库
	public List<TopItem> processClassfiy(){
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
				
				TopItem titem = parser.parse(url, pageContent);
				if (titem==null){
					log.warn("page parse failed:"+filePath);
					continue;
				}

				//未找到分类:
				if (titem.getCat()<=0) {
					log.warn("could not classify :"+filePath);
//					continue;
				};
				newItems.add(titem);
				parser.save(rootPath, titem);
			}
			
			if (newItems.size()<=0){
				continue;
			}
			
			if (newItems.size()>5)
			 break;
			
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
			
//			FileUtil.writeFile(pagesPath+sitekey+"_urls.json", JSON.toJSONString(urls));
//			FileUtil.writeFile(pagesPath+sitekey+"_done_urls.json", JSON.toJSONString(saves));		
		}
		
		return newItems;

	}

	//产生对应日期的内容列表并落地
	public boolean processListUrls(List<TopItem> items){
		Map<String,List<Integer>> newItemsMap = Collections.synchronizedMap(new HashMap<String,List<Integer>>());
		List<String> newsList = Collections.synchronizedList(new ArrayList<String>());
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		for (TopItem item:items){
			long crtime = item.getContentTime();
			c.setTimeInMillis(crtime);
			String dateStr = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
			String fileName = listPath+item.getCat()+"/"+dateStr+"/"+now.getTime()+".list";
			List<Integer> list = newItemsMap.get(fileName);
			if (list==null){
				list = Collections.synchronizedList(new ArrayList<Integer>());
				newItemsMap.put(fileName, list);
				newsList.add(fileName);
			}
			list.add(item.getUrl().hashCode());
		}
		//list data(按日期)落地:
		for (String listkey:newItemsMap.keySet()){
			List<Integer> itemlist = newItemsMap.get(listkey);
			FileUtil.writeFile(listkey, JSON.toJSONString(itemlist));
		}
		//最新list落地，等待site获取:
		FileUtil.writeFile(listPath+now.getTime()+".latest", JSON.toJSONString(newsList));
		return newsList.size()>0;
	}
	
	private Set<String> _getFileUrls2(String filePath){
		File urlfile = new File(filePath);
		if (!urlfile.exists()) return null;
		
		String content = FileUtil.readFile(filePath);
		List<String> urls = (List<String>)JSON.parse(content);
		Set<String> siteurls2 = new HashSet<String>();
		siteurls2.addAll(urls);
		return siteurls2;
	}

	public static void main(String[] args) {
		ProcessManager.getInstance().init();
		ProcessManager.getInstance().tesSimHash();
//		ProcessManager.getInstance().process();
//		ProcessManager.getInstance().processSpiders();
//		List<TopItem> items =ProcessManager.getInstance().processClassfiy();
//		ProcessManager.getInstance().processListUrls(items);
	}

}
