package box.weixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.db.Wxsite;
import box.db.WxsiteService;
import box.util.IPageDealer;
import box.util.IPageDealing;
import easyshop.downloadhelper.OriHttpPage;
import es.webref.model.PageRef;

public class WxPageDealing implements IPageDealing {
	private Map<String,IPageDealer> dealers = new HashMap<String,IPageDealer>();
	WxsiteService wxService = new WxsiteService();
	private List<Wxsite> sites;
	
	public WxPageDealing()
	{
		IPageDealer dealer = new NewrankPageDealer();
		dealers.put(dealer.getSiteId(), dealer);
		dealer = new A5118PageDealer();
		dealers.put(dealer.getSiteId(), dealer);
		dealer = new SougouPageDealer();
		dealers.put(dealer.getSiteId(), dealer);		
	}
	
	@Override
	public String[] getSetupSites() {
		sites = wxService.findsites();
		// TODO Auto-generated method stub
		String[] siteStrs = new String[sites.size()];
		
		//从数据库取
		for (int i=0;i<sites.size();i++)
		{
			siteStrs[i]=sites.get(i).getSiteid();
		}
		//直接设:
		siteStrs = new String[1];
		siteStrs[0] = "newrank";
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
