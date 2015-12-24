package box.site.db;

import java.util.ArrayList;
import java.util.List;

import box.site.model.Website;
import box.site.model.WebsiteExample;
import box.site.model.WebsiteExample.Criteria;
import box.site.model.WebsiteMapper;
import box.site.model.Websitekeys;
import box.site.model.WebsitekeysExample;
import box.site.model.WebsitekeysMapper;
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
	
	public SiteService()
	{
		initMapper("websiteMapper","websitekeysMapper");
	}
	
	public int addSite(Website record){
		return websiteMapper.insertSelective(record);
	}
	
	public int addSitekey(Websitekeys record){
		return websitekeysMapper.insertSelective(record);
	}
	
	public List<Websitekeys> getSitekeys(String key){	
		WebsitekeysExample example = new WebsitekeysExample();
		WebsitekeysExample.Criteria criteria = example.createCriteria();
		criteria.andKeywordEqualTo(key);
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
