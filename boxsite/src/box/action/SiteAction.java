package box.action;

import java.util.List;

import box.site.db.SiteService;
import box.site.model.Website;
import cn.hd.base.BaseAction;

import com.alibaba.fastjson.JSON;

public class SiteAction extends BaseAction{
	SiteService siteService;
	
	public SiteService getSiteService() {
		return siteService;
	}
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	public SiteAction(){
		init("siteService");
	}
	public String getSites(){
		String keyword = this.getHttpRequest().getParameter("keyword");
		List<Website> sites = siteService.getSites(keyword);
		if (sites==null||sites.size()<=0){
			
		}
		String jsonstr  = JSON.toJSONString(sites);
		super.write(jsonstr);
		return null;
	}
}
