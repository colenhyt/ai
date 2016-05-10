package box.site.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import box.site.model.Baiduurls;
import box.site.model.BaiduurlsExample;
import box.site.model.BaiduurlsMapper;
import box.site.model.Website;
import box.site.model.WebsiteExample;
import box.site.model.WebsiteExample.Criteria;
import box.site.model.WebsiteMapper;
import box.site.model.Websitekeys;
import box.site.model.WebsitekeysExample;
import box.site.model.WebsitekeysMapper;
import box.site.model.Websitewords;
import box.site.model.WebsitewordsExample;
import box.site.model.WebsitewordsMapper;
import box.site.model.Wordrelation;
import box.site.model.WordrelationExample;
import box.site.model.WordrelationMapper;
import cn.hd.base.BaseService;

public class SiteService extends BaseService{
	private int nextWordId = 0;

	public WebsiteMapper getWebsiteMapper() {
		return websiteMapper;
	}

	public void setWebsiteMapper(WebsiteMapper websiteMapper) {
		this.websiteMapper = websiteMapper;
	}

	public WebsitekeysMapper getWebsitekeysMapper() {
		return websitekeysMapper;
	}

	public void setWebsitekeysMapper(WebsitekeysMapper websitekeysMapper) {
		this.websitekeysMapper = websitekeysMapper;
	}

	private WebsiteMapper websiteMapper;
	private WebsitekeysMapper websitekeysMapper;
	private BaiduurlsMapper searchurlMapper;
	private WebsitewordsMapper websitewordsMapper; 
	private WordrelationMapper wordrelationMapper;
	public WordrelationMapper getWordrelationMapper() {
		return wordrelationMapper;
	}

	public void setWordrelationMapper(WordrelationMapper wordrelationMapper) {
		this.wordrelationMapper = wordrelationMapper;
	}

	public WebsitewordsMapper getWebsitewordsMapper() {
		return websitewordsMapper;
	}

	public void setWebsitewordsMapper(WebsitewordsMapper websitewordsMapper) {
		this.websitewordsMapper = websitewordsMapper;
	}

	private Map<String,Baiduurls>		searchUrlMap;
	private Map<String,Website>			siteMap;
	private Map<String,Websitewords>		wordsMap;
	private Map<String,Websitekeys>		keysMap;
	private Set<String> 	wordRelationKeys;
	
	public BaiduurlsMapper getSearchurlMapper() {
		return searchurlMapper;
	}

	public void setSearchurlMapper(BaiduurlsMapper searchurlMapper) {
		this.searchurlMapper = searchurlMapper;
	}

	public SiteService()
	{
		initMapper("websiteMapper","websitekeysMapper","searchurlMapper","websitewordsMapper","wordrelationMapper");
		
		searchUrlMap = new HashMap<String,Baiduurls>();
		
		wordsMap = new HashMap<String,Websitewords>();
		
		WebsitewordsExample e0 = new WebsitewordsExample();
		List<Websitewords> wl = websitewordsMapper.selectByExample(e0);
		for (Websitewords word:wl){
			wordsMap.put(word.getWord(), word);
		}
		
		wordRelationKeys = new HashSet<String>();
		
		WordrelationExample ee = new WordrelationExample();
		List<Wordrelation> rls = wordrelationMapper.selectByExample(ee);
		for (Wordrelation rel:rls){
			String key = rel.getWordid()+"_"+rel.getRelatewordid()+"_"+rel.getRelatetype();
			wordRelationKeys.add(key);
		}
		
		keysMap = new HashMap<String,Websitekeys>();
		WebsitekeysExample e3 = new WebsitekeysExample();
		List<Websitekeys> ll = websitekeysMapper.selectByExample(e3);
		for (Websitekeys item:ll){
			String key = item.getSiteid()+"_"+item.getWordid();
			keysMap.put(key, item);
		}
		
		siteMap = new HashMap<String,Website>();
		WebsiteExample e2 = new WebsiteExample();
		List<Website> list2 = websiteMapper.selectByExample(e2);
		for (Website item:list2){
			siteMap.put(item.getUrl(), item);
		}
		
		BaiduurlsExample  example = new BaiduurlsExample();
//		BaiduurlsExample.Criteria criteria = example.createCriteria();
//		criteria.andStatusEqualTo(0);
		List<Baiduurls> list = searchurlMapper.selectByExample(example);
		for (Baiduurls url:list){
			searchUrlMap.put(url.getUrl(), url);
		}
		
	}
	
	public int findWordId(String word){
		if (wordsMap.containsKey(word))
			wordsMap.get(word).getWordid();
		
		return -1;
	}
	
	public List<Websitewords> getDonewords(){
		wordsMap = new HashMap<String,Websitewords>();
		List<Websitewords>  words = new ArrayList<Websitewords>();
		WebsitewordsExample e2 = new WebsitewordsExample();
		WebsitewordsExample.Criteria cri = e2.createCriteria();
//		cri.andStatusEqualTo(1);
		List<Websitewords> wordslist = websitewordsMapper.selectByExample(e2);		
		return wordslist;
	}
	
	public List<Websitewords> getNewwords(){
		wordsMap = new HashMap<String,Websitewords>();
		List<Websitewords>  words = new ArrayList<Websitewords>();
		WebsitewordsExample e2 = new WebsitewordsExample();
		List<Websitewords> wordslist = websitewordsMapper.selectByExample(e2);
		for (Websitewords item:wordslist){
			if (item.getStatus()==0){
				wordsMap.put(item.getWord(),item);
				words.add(item);
			}
		}
		
		return words;
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
		wordrelationMapper.insert(r);
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
		websitewordsMapper.insert(record);
		wordsMap.put(word, record);
		return record.getWordid();
		
	}
	public List<Baiduurls> getNewBaiduurls(){
		List<Baiduurls> newurls = new ArrayList<Baiduurls>();
		
		for (Baiduurls item:searchUrlMap.values())		{
			if (item.getStatus()==0)
				newurls.add(item);
		}
		return newurls;
	}
	
	public int getMaxSiteid(){
		int maxSiteid = 0;
		WebsiteExample e = new WebsiteExample();
		List<Website> list = websiteMapper.selectByExample(e);
		for (Website item:list){
			if (item.getSiteid()>maxSiteid)
				maxSiteid = item.getSiteid();
		}
		return maxSiteid;
	}
	
	public List<Website> getNotInfoWebsite(){
		WebsiteExample e = new WebsiteExample();
		WebsiteExample.Criteria criteria = e.createCriteria();
		criteria.andAlexaIsNull();
		criteria.andBdrankIsNull();
		criteria.andUrlIsNotNull();
		List<Website> list = websiteMapper.selectByExample(e);
//		List<Website> list2 = new ArrayList<Website>();
//		for (int i=0;i<2;i++)
//			list2.add(list.get(i));
		return list;
	}
	
	public synchronized int getNextWordId(){
		if (nextWordId==0){
		nextWordId = getMaxWordid();
		}
		nextWordId++;
		return nextWordId;
	}
	
	public int getMaxWordid(){
		int maxwordid = 0;
		WebsitewordsExample e = new WebsitewordsExample();
		List<Websitewords> list = websitewordsMapper.selectByExample(e);
		for (Websitewords item:list){
			if (item.getWordid()>maxwordid)
				maxwordid = item.getWordid();
		}
		return maxwordid;
	}
	
	public boolean containsSearchUrl(String url){
		return searchUrlMap.containsKey(url);
	}
	
	public boolean updateSearchUrl(String url){
		Baiduurls item = searchUrlMap.get(url);
		if (item!=null)
			return updateSearchUrl(item);
		return false;
	}
	
	public boolean updateSearchUrl(Baiduurls record){
		record.setStatus(1);
		searchurlMapper.updateByPrimaryKeySelective(record);
		return true;
	}
	
	public void updateWebsites(List<Website> sites){
		for (Website site:sites){
			websiteMapper.updateByPrimaryKey(site);
		}
	}
	
	public int deleteWebsitekeys (List<Websitekeys> items){
		int ret = 0;
		for (Websitekeys item:items){
			WebsitekeysExample e = new WebsitekeysExample();
			WebsitekeysExample.Criteria cri = e.createCriteria();
			cri.andWordidEqualTo(item.getWordid());
			cri.andSiteidEqualTo(item.getSiteid());
			ret = websitekeysMapper.deleteByExample(e);
			
		}
		this.DBCommit();
		return ret;
	}
	
	public void updateWebsite(Website site){
			websiteMapper.updateByPrimaryKey(site);
	}
	
	public boolean addSearchUrl(String url,int wordid){
		if (searchUrlMap.containsKey(url))
			return false;
		
		Baiduurls item = new Baiduurls();
		item.setUrl(url);
		item.setStatus(0);
		item.setWordid(wordid);
		item.setCrdate(new Date());
		searchUrlMap.put(url, item);
		searchurlMapper.insert(item);
		return true;
	}
	
	public List<Website> getWebsites(int wordid){
		WebsitekeysExample example = new WebsitekeysExample();
		WebsitekeysExample.Criteria cri = example.createCriteria();
		cri.andWordidEqualTo(wordid);
		List<Websitekeys> keys = websitekeysMapper.selectByExample(example);
		List<Website> sites = new ArrayList<Website>();
		for (Websitekeys item:keys){
			WebsiteExample e2 = new WebsiteExample();
			WebsiteExample.Criteria cri2 = e2.createCriteria();
			cri2.andSiteidEqualTo(item.getSiteid());
			List<Website> l = websiteMapper.selectByExample(e2);
			sites.addAll(l);
		}
		return sites;
	}
	
	public int getWebsiteCount(int wordid){
		WebsitekeysExample example = new WebsitekeysExample();
		WebsitekeysExample.Criteria cri = example.createCriteria();
		cri.andWordidEqualTo(wordid);
		return websitekeysMapper.countByExample(example);
	}
	
	public List<Baiduurls> getNotDoneSearchUrls(int wordid){
		
		BaiduurlsExample example = new BaiduurlsExample();
		BaiduurlsExample.Criteria criteria = example.createCriteria();
		criteria.andWordidEqualTo(wordid);
		criteria.andStatusEqualTo(0);
		return searchurlMapper.selectByExample(example);
	}
	
	public Vector<Website> addSites(Vector<Website> records){
		Vector<Website> sites2 = new Vector<Website>();
		for (Website record:records){
			if (!siteMap.containsKey(record.getUrl())){
				sites2.add(record);
				siteMap.put(record.getUrl(), record);
//				if (record.getUrl().length()>200){
//					log.warn(record.getUrl());
//				}
				websiteMapper.insertSelective(record);
			}
		}
		return sites2;
	}
	
	public int addSitekey(Websitekeys record){
		String key = record.getSiteid()+"_"+record.getWordid();
		if (keysMap.containsKey(key))
			return -1;
		
		keysMap.put(key, record);
		return websitekeysMapper.insertSelective(record);
	}
	
	public List<Websitekeys> getSitekeys(String key){	
		WebsitewordsExample e2 = new WebsitewordsExample();
		WebsitewordsExample.Criteria cr2 = e2.createCriteria();
		cr2.andWordEqualTo(key);
		List<Websitewords> list = websitewordsMapper.selectByExample(e2);
		Websitewords worditem = null;
		if (list.size()>0)
			worditem = list.get(0);
		else
			return null;
		
		WebsitekeysExample example = new WebsitekeysExample();
		WebsitekeysExample.Criteria criteria = example.createCriteria();
		criteria.andWordidEqualTo(worditem.getWordid());
		return websitekeysMapper.selectByExample(example);
	}
	
	public List<Website> getSites(String key){
		List<Websitekeys> keys = this.getSitekeys(key);
		if (keys==null||keys.size()<=0)
			return null;
		
		List<Integer> siteIds = new ArrayList<Integer>();
		for (int i=0;i<keys.size();i++){
			siteIds.add(keys.get(i).getSiteid());
		}
		WebsiteExample example = new WebsiteExample();
		Criteria criteria = example.createCriteria();
		criteria.andSiteidIn(siteIds);
		return websiteMapper.selectByExample(example);
	}
}
