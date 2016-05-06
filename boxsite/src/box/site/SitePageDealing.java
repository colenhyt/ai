package box.site;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easyshop.downloadhelper.OriHttpPage;
import es.webref.model.PageRef;
import box.util.IPageDealer;
import box.util.IPageDealing;

public class SitePageDealing implements IPageDealing {
	private Map<String,IPageDealer> dealers = new HashMap<String,IPageDealer>();
	
	public SitePageDealing(){
		IPageDealer dealer = new BaiduSiteDealer();
		dealers.put(dealer.getSiteId(), dealer);
	}
	
	@Override
	public String[] getSetupSites() {
		String[] siteStrs = new String[1];
		siteStrs[0] = "baidu";
		return siteStrs;
	}

	@Override
	public List<PageRef> add(Object newPage) {
		OriHttpPage pp = (OriHttpPage)newPage;
		return dealers.get(pp.getSiteId()).deal(pp);
	}

	@Override
	public String getFirstUrl(String siteId) {
		return dealers.get(siteId).getFirstUrl();
	}

	@Override
	public List<PageRef> getFirstRefs(String siteId) {
		return dealers.get(siteId).getFirstRefs();
	}

}
