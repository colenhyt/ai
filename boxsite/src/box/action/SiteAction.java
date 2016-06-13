package box.action;

import box.site.db.SiteService;
import cn.hd.base.BaseAction;

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

		return null;
	}
}
