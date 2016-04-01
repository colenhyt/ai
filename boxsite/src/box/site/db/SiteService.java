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
import cn.hd.base.BaseService;

public class SiteService extends BaseService{
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
	public WebsitewordsMapper getWebsitewordsMapper() {
		return websitewordsMapper;
	}

	public void setWebsitewordsMapper(WebsitewordsMapper websitewordsMapper) {
		this.websitewordsMapper = websitewordsMapper;
	}

	private Map<String,Baiduurls>		searchUrlMap;
	private Map<String,Website>			siteMap;
	private Set<String>		wordsSet;
	
	public BaiduurlsMapper getSearchurlMapper() {
		return searchurlMapper;
	}

	public void setSearchurlMapper(BaiduurlsMapper searchurlMapper) {
		this.searchurlMapper = searchurlMapper;
	}

	public SiteService()
	{
		initMapper("websiteMapper","websitekeysMapper","searchurlMapper","websitewordsMapper");
		searchUrlMap = new HashMap<String,Baiduurls>();
		
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
	
	public List<Websitewords> getNewwords(){
		wordsSet = new HashSet<String>();
		List<Websitewords>  words = new ArrayList<Websitewords>();
		WebsitewordsExample e2 = new WebsitewordsExample();
		List<Websitewords> wordslist = websitewordsMapper.selectByExample(e2);
		for (Websitewords item:wordslist){
			if (item.getStatus()==0){
				wordsSet.add(item.getWord());
				words.add(item);
			}
		}
		
		return words;
	}
	
	public void addWord(String word){
		if (wordsSet.contains(word))
			return;
		
		Websitewords  record = new Websitewords();
		record.setWord(word);
		record.setStatus(0);
		websitewordsMapper.insert(record);
		
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
	
	public Vector<Website> addSites(Vector<Website> records){
		Vector<Website> sites2 = new Vector<Website>();
		for (Website record:records){
			if (!siteMap.containsKey(record.getUrl())){
				sites2.add(record);
				siteMap.put(record.getUrl(), record);
				websiteMapper.insertSelective(record);
			}
		}
		return sites2;
	}
	
	public int addSitekey(Websitekeys record){
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
