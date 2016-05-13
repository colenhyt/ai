package box.mgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import box.main.SitesContainer;
import box.site.SitePageDealing;
import box.site.db.SiteService;
import box.site.model.Baiduurls;
import box.site.model.BaiduurlsExample;
import box.site.model.Website;
import box.site.model.WebsiteExample;
import box.site.model.Websitekeys;
import box.site.model.WebsitekeysExample;
import box.site.model.Websitewords;
import box.site.model.WebsitewordsExample;
import box.site.model.Wordrelation;
import box.site.model.WordrelationExample;
import box.util.IPageDealing;

import com.alibaba.fastjson.JSON;

public class SiteManager {
	DataThread dataThread;
	private int nextWordId = 0;
	private int pageCount = 20;

	private Map<String,Website>			siteMap;
	private Map<Integer,Website>			siteMapById;
	private Map<String,Websitewords>		wordsMap;
	private Map<String,Websitekeys>		keysMap;
	private Set<String> 	wordRelationKeys;
	
	private static SiteManager uniqueInstance = null;

	public static SiteManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new SiteManager();
		}
		return uniqueInstance;
	}
	

	
	public SiteManager(){
		dataThread = new DataThread();
		dataThread.start();
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
		List<Website> list2 = service.getWebsites();
		for (Website item:list2){
			siteMap.put(item.getUrl(), item);
			siteMapById.put(item.getSiteid(), item);
		}

		
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
			wordsMap.get(word).getWordid();
		
		return -1;
	}
	
	public void addWordRelation(int wordid,int relateWordid,int relatetype){
		String key = wordid+"_"+relateWordid+"_"+relatetype;
		if (wordRelationKeys.contains(key))
			return;
		
		Wordrelation r = new Wordrelation();
		r.setWordid(wordid);
		r.setRelatewordid(relateWordid);
		r.setRelatetype(relatetype);
		wordRelationKeys.add(key);
		SiteService service = new SiteService();
		service.addWordRelation(r);
	}
	
	public int addSitekey(Websitekeys record){
		String key = record.getSiteid()+"_"+record.getWordid();
		if (keysMap.containsKey(key))
			return -1;
		
		keysMap.put(key, record);
		SiteService service = new SiteService();
		return service.addSitekey(record);
	}
	
	
	public int addSites(Vector<Website> records,int startSiteId,Vector<Website> addedSites){
		int maxSiteId = startSiteId;
		SiteService service = new SiteService();
		for (Website record:records){
			if (!siteMap.containsKey(record.getUrl())){
				addedSites.add(record);
				record.setSiteid(maxSiteId);
				siteMap.put(record.getUrl(), record);
				maxSiteId++;
				service.addSite(record);
			}
		}
		return maxSiteId;
	}
	
	public int addWord(String word){
		if (word==null)
			return -1;
		
		if (wordsMap.containsKey(word))
			return wordsMap.get(word).getWordid();
		
		Websitewords  record = new Websitewords();
		record.setWord(word);
		record.setWordid(this.getNextWordId());
		record.setStatus(0);
		wordsMap.put(word, record);
		SiteService service = new SiteService();
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

	
	public static String getSiteWords(){
		SiteService service = new SiteService();
		List<Websitewords> words = service.getDonewords();
		return JSON.toJSONString(words);
	}
	
	public static String getHotwords(){
		SiteService service = new SiteService();
		List<Websitewords> list = service.getDonewords();
		return JSON.toJSONString(list);
	}
	
	
	public static String getSiteWordlist(){
		SiteService service = new SiteService();
		List<Websitewords> list = service.getDonewords();
		return JSON.toJSONString(list);
	}
	
	private void startupSpider(String word){
		IPageDealing dealing = new SitePageDealing(word);
		int[] a = new int[1]; 
		//Date dd = DateHelper.formatDate("3", "23", "19:10:10");
		SitesContainer con = new SitesContainer(a,dealing);
		con.runningPages();		
	}
	
	public String querySites(String word,int page){
		int wordid = this.findWordId(word);
		if (wordid<=0){
			wordid = this.addWord(word);
			this.startupSpider(word);
		}
		
		List<Website> list = this.getWebsites(wordid,page);
		if (list.size()<=0){
			this.startupSpider(word);
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
