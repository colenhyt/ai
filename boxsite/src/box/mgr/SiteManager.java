package box.mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import box.site.SitePageDealing;
import box.site.db.SiteService;
import box.site.model.Website;
import box.site.model.WebsiteDNA;
import box.site.model.Websitekeys;
import box.site.model.Websiteterms;
import box.site.model.Websitewords;
import box.site.model.Wordrelation;
import cn.hd.util.FileUtil;
import cn.hd.util.RedisClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.string.StringHelper;
import es.util.url.URLStrHelper;

public class SiteManager extends MgrBase{
	
	protected Logger  log = Logger.getLogger(getClass()); 
	DataThread dataThread;
	private int nextWordId = 0;
	private int pageCount = 20;
	private int nextWebsiteId = 0;
	String path = "data/sites/sites.json";

	private Map<String,Website>			siteMap;
	private Map<String,Website>			srcSiteMap;
	private Map<Integer,Website>			siteMapById;
	private Map<String,Websitewords>		wordsMap;
	private Map<String,Websitekeys>		keysMap;
	private Set<String> 	wordRelationKeys;
	private SitePageDealing dealing;
	private RedisClient client;
	
	private static SiteManager uniqueInstance = null;

	public static SiteManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new SiteManager();
		}
		return uniqueInstance;
	}
	
	public static void main(String[] args){
//		SiteManager.getInstance().pushSiteTerms2DB();
		String str ="投资,公司,创业,用户,产品,平台,互联网";
		SiteManager.getInstance().searchWordGroupSites(str);
	}
	
	public SiteManager(){
	
		srcSiteMap = new HashMap<String,Website>();
		String content = FileUtil.readFile(path);
		if (content!=null&&content.trim().length()>0){
			Map<String,JSONObject> data = (Map<String,JSONObject>)JSON.parse(content);
			for (String key:data.keySet()){
				JSONObject itemd = data.get(key);
				srcSiteMap.put(key, (Website)JSON.parseObject(itemd.toJSONString(), Website.class));
			}
		}

		wordRelationKeys = new HashSet<String>();
		keysMap = new HashMap<String,Websitekeys>();
		siteMapById = new HashMap<Integer,Website>();
		

		siteMap = new HashMap<String,Website>();

//		dataThread = new DataThread(redisCfg);
//		dataThread.start();
		
//		client = new RedisClient(redisCfg);
		
//		SiteService service = new SiteService();
//		
//		wordsMap = new HashMap<String,Websitewords>();
//		
//		List<Websitewords> wl = service.getWebsitewords();
//		for (Websitewords word:wl){
//			wordsMap.put(word.getWord(), word);
//		}
//		
//		
//		List<Wordrelation> rls = service.getWordrelations();
//		for (Wordrelation rel:rls){
//			String key = rel.getWordid()+"_"+rel.getRelatewordid()+"_"+rel.getRelatetype();
//			wordRelationKeys.add(key);
//		}
//		
//		List<Websitekeys> ll = service.getWebsitekeys();
//		for (Websitekeys item:ll){
//			String key = item.getSiteid()+"_"+item.getWordid();
//			keysMap.put(key, item);
//		}
//		
//		List<Website> list2 = service.getWebsites();
//		for (Website item:list2){
//			siteMap.put(item.getUrl(), item);
//			siteMapById.put(item.getSiteid(), item);
//		}
//		
	}
	
	public List<Websitewords> getNewwords(){
		SiteService service = new SiteService();
		List<Websitewords>  words = new ArrayList<Websitewords>();
		List<Websitewords> wordslist = service.getNewwords();
		for (Websitewords item:wordslist){
				wordsMap.put(item.getWord(),item);
				words.add(item);
		}
		
		return words;
	}
	
	public List<Website> getWebsites(int wordid,int page){
		SiteService service = new SiteService();
		
		List<Websitekeys> keys = service.getSitekeys(wordid);
		List<Website> sites = new ArrayList<Website>();
		for (int i=0;i<keys.size();i++){
			sites.add(siteMapById.get(keys.get(i).getSiteid()));
		}
		
		int maxCount = sites.size();
		if (maxCount>(page+1)*pageCount)
			maxCount = (page+1)*pageCount;
		
		List<Website> pagesites = new ArrayList<Website>();
		for (int i=page*pageCount;i<maxCount;i++){
			pagesites.add(sites.get(i));
		}
		return pagesites;
	}
	
	public int findWordId(String word){
		if (wordsMap.containsKey(word))
			return wordsMap.get(word).getWordid();
		
		return -1;
	}
	
	public Websitewords findWord(int wordid){
		Collection<Websitewords> cc = wordsMap.values();
		for (Websitewords item:cc){
			if (item.getWordid()==wordid)
				return item;
		}
		
		return null;
	}
	
	public void addWordRelation(SiteService service,int wordid,int relateWordid,int relatetype){
		String key = wordid+"_"+relateWordid+"_"+relatetype;
		if (wordRelationKeys.contains(key))
			return;
		
		Wordrelation r = new Wordrelation();
		r.setWordid(wordid);
		r.setRelatewordid(relateWordid);
		r.setRelatetype(relatetype);
		wordRelationKeys.add(key);
		service.addWordRelation(r);
	}
	
	public int addSitekey(SiteService service,Websitekeys record){
		String key = record.getSiteid()+"_"+record.getWordid();
		if (keysMap.containsKey(key))
			return -1;
		
		keysMap.put(key, record);
		this.updateWordSiteCount(service, record.getWordid());
		return service.addSitekey(record);
	}
	
	public void updateWordSiteCount(SiteService service,int wordid){
		Websitewords sitewords = findWord(wordid);
		if (sitewords==null){
			return;
		}
		sitewords.setSitecount(sitewords.getSitecount()+1);
		wordsMap.get(sitewords.getWord()).setSitecount(sitewords.getSitecount());
		service.updateWordCount(wordid,sitewords.getSitecount());
	}
	
	public boolean existSite(String siteurl){
		return siteMap.containsKey(siteurl);
	}	
	
	public Set<Integer> findSiteIdsByDomainNameCode(String folderName){
		Set<Integer> ids = new HashSet<Integer>();
		ids.add(3);
		for (String url:siteMap.keySet()){
			if (url==null) continue;
			String domainName = URLStrHelper.getHost(url);
			if (domainName!=null){
				if (folderName.equalsIgnoreCase(domainName)){
					ids.add(siteMap.get(url).getSiteid());
				}
				
			}
		}
		return ids;
	}
	
	public void pushSiteTerms2DB(){
		int addCount = 0;
		List<File> files = FileUtil.getFiles("data/terms/");
		List<Websiteterms> termlist = new ArrayList<Websiteterms>();
		for (File file:files){
			int index = file.getName().indexOf(".json");
			if (index>0){
				String folderName = file.getName().substring(0,index);
				Set<Integer> ids = findSiteIdsByDomainNameCode(folderName);
				String content = FileUtil.readFile(file);
				Map<String,Integer> termsmap = (Map<String,Integer>)JSON.parse(content);
				for (int siteid:ids){
					for (String term:termsmap.keySet()){
						Websiteterms terms = new Websiteterms();
						terms.setSiteid(siteid);
						terms.setTermcount(termsmap.get(term));
						if (term.trim().length()>25){
							term = term.substring(0,25);
						}
						terms.setTerm(term);
						termlist.add(terms);
					}
				}
			}
		}
		SiteService service = new SiteService();
		for (Websiteterms terms:termlist){
			service.addWebsiteterms(terms);
		}
		service.DBCommit();
		log.warn("网站词频导入完成 ,增加数:"+termlist.size());
	}
	
	public void _findNewSites(Vector<Website> records,Vector<Website> newSites){
		for (Website record:records){
			if (!siteMap.containsKey(record.getUrl())){
				record.setSiteid(this.getNextWebisteId());
				newSites.add(record);
			}
		}
	}
	
	public int addSites(SiteService service,Vector<Website> records){
		for (Website record:records){
			if (!siteMap.containsKey(record.getUrl())){
				siteMap.put(record.getUrl(), record);
				 service.addSite(record);
			}
		}
		return 0;
	}
	
	public Set<Website> addSites(Vector<Website> records){
//		SiteService service = new SiteService();
		Set<Website> newsites = new HashSet<Website>();
		for (Website record:records){
			if (record==null) continue;
			String url = record.getBaiduurl();
			if (record.getUrl()!=null)
				url = record.getUrl();
			if (url.indexOf("http")<0)
				url = "http://"+url;
			String domainName = URLStrHelper.getHost(url).toLowerCase();
			record.setSiteid(domainName.hashCode());
			record.setCrdate(new Date());
			if (!srcSiteMap.containsKey(url)){
				srcSiteMap.put(url, record);
				newsites.add(record);
			}
			if (!siteMap.containsKey(url)){
				siteMap.put(url, record);
//				 service.addSite(record);
			}			
		}
		//落地:
		if (newsites.size()>0){
			FileUtil.writeFile(path, JSON.toJSONString(srcSiteMap));
		}
		return newsites;
	}
	
	public synchronized int getNextWebisteId(){
		if (nextWebsiteId==0){
		SiteService siteService = new SiteService();
		nextWebsiteId = siteService.getMaxSiteid();
		}
		nextWebsiteId++;
		return nextWebsiteId;
	}
	
	public int addWord(SiteService service,String word){
		if (word==null)
			return -1;
		
		if (wordsMap.containsKey(word))
			return wordsMap.get(word).getWordid();
		
		Websitewords  record = new Websitewords();
		record.setWord(word);
		record.setWordid(this.getNextWordId());
		record.setStatus(0);
		wordsMap.put(word, record);
		service.addWord(record);
		return record.getWordid();
	}
	
	
	public synchronized int getNextWordId(){
		if (nextWordId==0){
			SiteService service = new SiteService();
		nextWordId = service.getMaxWordid();
		}
		nextWordId++;
		return nextWordId;
	}

	public WebsiteDNA getSiteDNA(String domainName){
		Jedis jedis = client.getJedis();
		String str = jedis.hget(SiteManager.DATA_WEBSITE_DNA, domainName);
		if (str==null){
			return JSON.parseObject(str, WebsiteDNA.class);
		}
		client.returnResource(jedis);
		return null;
	}
	
	public static String getSiteWords(){
		SiteService service = new SiteService();
		List<Websitewords> words = service.getDonewords();
		return JSON.toJSONString(words);
	}
	
	public String getHotwordlist(){
		List<String> words = new ArrayList<String>();
		String content = FileUtil.readFile(listPath+"sites.wordlist");
		if (content.trim().length()>0){
			List<String> wordlist = (List<String>)JSON.parse(content);
			for (String str:wordlist){
				words.add(str);
				words.add(String.valueOf(str.hashCode()));
			}
		}
		
		return JSON.toJSONString(words);
	}
	
	public String getHotwords(){
		Collection<Websitewords> words = wordsMap.values();
		List<Websitewords> items = new ArrayList<Websitewords>();
		for (Websitewords item:words){
			if (item.getSitecount()>0)
				items.add(item);
		}
		Collections.sort((List<Websitewords>)items);
		List<Websitewords> list = new ArrayList<Websitewords>();
		for (int i=0;i<10;i++){
			if (items.size()>i)
				list.add(items.get(i));
		}
		return JSON.toJSONString(list);
	}
	
	
	public static String getSiteWordlist(){
		SiteService service = new SiteService();
		List<Websitewords> list = service.getDonewords();
		return JSON.toJSONString(list);
	}
	
	private void addSearchWord(String word){
		dealing.addWord(word);
	}
	
	public String querySites(String word,int page){
		int wordid = this.findWordId(word);
		if (wordid<=0){
			this.addSearchWord(word);
			return "[]";
		}
		
		List<Website> list = this.getWebsites(wordid,page);
		if (list.size()<=0){
			//this.startupSpider(word);
		}
		
		return JSON.toJSONString(list);
	}
	
	public String searchWordGroupSites(String wordlist){
		String key = String.valueOf(wordlist.hashCode());
		SiteSearchManager.getInstance().addNewWords(key, wordlist, true);
		return null;
	}
	
	public String querySearchCount(String wordlist,String sitekey){
		int count = 0;
		Set<String> words = StringHelper.getStrArray(wordlist, ",");
		for (String word:words){
			int c = SiteSearchManager.getInstance().querySiteCount(word, sitekey);
			count += c;
		}
		
		return String.valueOf(count);
	}
	
	public String querySites2(String word,int page){
		Set<String>  siteset = new HashSet<String>();
		List<Website> list = queryWordSites(word,siteset);
		return JSON.toJSONString(list);
	}
	
	public String queryWordGroupSites(String key){
		List<Website> sites = new ArrayList<Website>();
		Set<String>  siteset = new HashSet<String>();
		
		String content = FileUtil.readFile(listPath+key+".wordgroup");
		if (content.trim().length()>0){
			List<String> wordList = (List<String>)JSON.parse(content);
			for (String word:wordList){
				List<Website> list = queryWordSites(word,siteset);
				sites.addAll(list);
			}
		}
		
		return JSON.toJSONString(sites);
	}
	
	
	private List<Website> queryWordSites(String word,Set<String> siteset){
		List<Website> list = new ArrayList<Website>();
		
		Set<String> searchEngines = Collections.synchronizedSet(new HashSet<String>());
		searchEngines.add("baidu");
		searchEngines.add("bing");
		searchEngines.add("sogou");
		
		word = word.replace(",", "%20");
		for (String sitekey:searchEngines){
			String path = sitePath + sitekey +"/"+word+".sites";
			String content = FileUtil.readFile(path);
			if (content.trim().length()>0){
				List<String> stritems = (List<String>)JSON.parse(content);
				for (String str:stritems){
					JSONObject obj = JSONObject.parseObject(str);
					Website item = (Website)JSON.toJavaObject(obj, Website.class);
					if (!siteset.contains(item.getUrl())){
						siteset.add(item.getUrl());
						list.add(item);
					}
				}
			}			
		}
	
		return list;
	}
	
	public int deleteWordid(int wordid,int siteid){
		Websitekeys key = new Websitekeys();
		key.setWordid(wordid);
		key.setSiteid(siteid);
		dataThread.addDeleteKeys(key);
		return 0;
	}
	
	public int updateMyranks(String ranksStr){
		SiteService service = new SiteService();
		List<Website> sites = JSON.parseArray(ranksStr, Website.class);
		service.updateMyranks(sites);
		return 0;
	}

	public void pushSrcSites2DB(){
		SiteService service = new SiteService();
		int addCount = 0;
		for (Website record:srcSiteMap.values()){
			if (this.existSite(record.getUrl())) continue;
			record.setSiteid(this.getNextWebisteId());
			record.setCrdate(new Date());
			if (record.getCdesc()!=null){
				int len = record.getCdesc().length()>100?100:record.getCdesc().length()-1;
				if (len>0)
					record.setCdesc(record.getCdesc().substring(0,len));
			}
			siteMap.put(record.getUrl(), record);
			service.addSite(record);
			addCount++;
		}
		service.DBCommit();
		log.warn("网站导入完成 ,增加数:"+addCount);
	}

	public String getHotwords2(){
		Set<String> searchEngines = Collections.synchronizedSet(new HashSet<String>());
		searchEngines.add("baidu");
		searchEngines.add("bing");
		searchEngines.add("sogou");
		
		Set<String> wordset = new HashSet<String>();
		for (String sitekey:searchEngines){
			List<File> files = FileUtil.getFiles(sitePath+sitekey+"/");
			for (File f:files){
				String name = f.getName();
				name = name.replace("%20", ",").replace(".sites", "");
				wordset.add(name);
			}
		}
		
		List<String> words = new ArrayList<String>();
		words.addAll(wordset);
		return JSON.toJSONString(words);
	}
}
