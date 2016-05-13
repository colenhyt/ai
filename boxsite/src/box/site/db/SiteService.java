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

	
	public BaiduurlsMapper getSearchurlMapper() {
		return searchurlMapper;
	}

	public void setSearchurlMapper(BaiduurlsMapper searchurlMapper) {
		this.searchurlMapper = searchurlMapper;
	}
	private Map<String,Baiduurls>		searchUrlMap;

	public SiteService()
	{
		initMapper("websiteMapper","websitekeysMapper","searchurlMapper","websitewordsMapper","wordrelationMapper");
		
		searchUrlMap = new HashMap<String,Baiduurls>();
		BaiduurlsExample  example = new BaiduurlsExample();
		List<Baiduurls> list = searchurlMapper.selectByExample(example);
		for (Baiduurls url:list){
			searchUrlMap.put(url.getUrl(), url);
		}		
	}

	public List<Websitewords> getWebsitewords(){
		WebsitewordsExample e0 = new WebsitewordsExample();
		return websitewordsMapper.selectByExample(e0);		
	}

	public List<Websitekeys> getWebsitekeys(){
		WebsitekeysExample e0 = new WebsitekeysExample();
		return websitekeysMapper.selectByExample(e0);		
	}

	public List<Website> getWebsites(){
		WebsiteExample e0 = new WebsiteExample();
		return websiteMapper.selectByExample(e0);		
	}
	
	public List<Wordrelation> getWordrelations(){
		WordrelationExample e0 = new WordrelationExample();
		return wordrelationMapper.selectByExample(e0);		
	}
	
	public List<Websitewords> getDonewords(){
		WebsitewordsExample e2 = new WebsitewordsExample();
		List<Websitewords> wordslist = websitewordsMapper.selectByExample(e2);		
		return wordslist;
	}
	
	public List<Websitewords> getNewwords(){
		WebsitewordsExample e2 = new WebsitewordsExample();
		WebsitewordsExample.Criteria cri = e2.createCriteria();
		cri.andStatusEqualTo(0);
		return websitewordsMapper.selectByExample(e2);
	}
	
	public List<Baiduurls> getNewBaiduurls(){
		List<Baiduurls> newurls = new ArrayList<Baiduurls>();
		
		for (Baiduurls item:searchUrlMap.values())		{
			if (item.getStatus()==0)
				newurls.add(item);
		}
		return newurls;
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
		
		SiteService service = new SiteService();
		service.addSearchUrl(item);
		return true;
	}
	
	public void addWordRelation(Wordrelation r){
		wordrelationMapper.insert(r);
	}
	
	public int addWord(Websitewords record){
		websitewordsMapper.insert(record);
		return record.getWordid();
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
	
	public void updateMyranks(List<Website> sites){
		for (Website record:sites){
			WebsiteExample example = new WebsiteExample();
			WebsiteExample.Criteria cri = example.createCriteria();
			cri.andSiteidEqualTo(record.getSiteid());
			cri.andMyrankEqualTo(record.getMyrank());
			websiteMapper.updateByExampleSelective(record, example);
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
	
	public boolean addSearchUrl(Baiduurls record){
		searchurlMapper.insert(record);
		return true;
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
	
	public int addSite(Website record){
		return websiteMapper.insertSelective(record);
	}
	
	public int addSitekey(Websitekeys record){
		return websitekeysMapper.insertSelective(record);
	}
	
	public List<Websitekeys> getSitekeys(int wordid){
		WebsitekeysExample example = new WebsitekeysExample();
		WebsitekeysExample.Criteria criteria = example.createCriteria();
		criteria.andWordidEqualTo(wordid);
		return websitekeysMapper.selectByExample(example);	
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
		
		return this.getSitekeys(worditem.getWordid());
	}
}
