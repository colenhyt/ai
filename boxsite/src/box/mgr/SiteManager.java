package box.mgr;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import box.main.SitesContainer;
import box.site.SitePageDealing;
import box.site.db.SiteService;
import box.site.model.Website;
import box.site.model.WebsiteDNA;
import box.site.model.Websitekeys;
import box.site.model.Websitewords;
import box.site.model.Wordrelation;
import cn.hd.util.FileUtil;
import cn.hd.util.RedisClient;
import cn.hd.util.RedisConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SiteManager {
	public static final String DATA_WEBSITE_DNA = "data_website_dna";
	
	protected Logger  log = Logger.getLogger(getClass()); 
	DataThread dataThread;
	private int nextWordId = 0;
	private int pageCount = 20;
	private int nextWebsiteId = 0;

	private Map<String,Website>			siteMap;
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
	

	
	public SiteManager(){
		String path = "/root/";
		URL  res = Thread.currentThread().getContextClassLoader().getResource("/");
		String cfgstr = FileUtil.readFile(path + "config.properties");
		if (cfgstr == null || cfgstr.trim().length() <= 0) {
			if (res==null){
				log.warn("game start failed: "+path);
				return;
			}
			cfgstr = FileUtil.readFile(res.getPath() + "config.properties");
			if (cfgstr==null|| cfgstr.trim().length() <= 0){
			log.warn("game start failed: "+path);
			return;
			}
		}
		JSONObject cfgObj = JSON.parseObject(cfgstr);
		String cfgstr0 = cfgObj.getString("redisCfg");
		RedisConfig redisCfg = JSON.parseObject(cfgstr0, RedisConfig.class);
		
		dataThread = new DataThread(redisCfg);
		dataThread.start();
		
		client = new RedisClient(redisCfg);
		
		SiteService service = new SiteService();
		
		wordsMap = new HashMap<String,Websitewords>();
		
		List<Websitewords> wl = service.getWebsitewords();
		for (Websitewords word:wl){
			wordsMap.put(word.getWord(), word);
		}
		
		wordRelationKeys = new HashSet<String>();
		
		List<Wordrelation> rls = service.getWordrelations();
		for (Wordrelation rel:rls){
			String key = rel.getWordid()+"_"+rel.getRelatewordid()+"_"+rel.getRelatetype();
			wordRelationKeys.add(key);
		}
		
		keysMap = new HashMap<String,Websitekeys>();
		List<Websitekeys> ll = service.getWebsitekeys();
		for (Websitekeys item:ll){
			String key = item.getSiteid()+"_"+item.getWordid();
			keysMap.put(key, item);
		}
		
		siteMap = new HashMap<String,Website>();
		siteMapById = new HashMap<Integer,Website>();
		
		List<Website> list2 = service.getWebsites();
		for (Website item:list2){
			siteMap.put(item.getUrl(), item);
			siteMapById.put(item.getSiteid(), item);
		}
		
		dealing = new SitePageDealing();
		int[] a = new int[1];
		SitesContainer con = new SitesContainer(a,dealing);
		con.runningPages();			
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
}
