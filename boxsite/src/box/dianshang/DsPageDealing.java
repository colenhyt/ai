/*
 * Created on 2006-6-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package box.dianshang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.util.IPageDealer;
import box.util.IPageDealing;
import easyshop.downloadhelper.OriHttpPage;
import es.download.DownloadAfterDealer;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;


/**
 * Tracy&Allen.EasyShop 0.9
 *
 * @author Allenhuang
 * 
 * created on 2006-6-10
 */
public class DsPageDealing implements IPageDealing{
	private Map<String,IPageDealer>		mapDealers;
	
	 DownloadAfterDealer dealder=new DownloadAfterDealer();
	 int perCount=100;
	 
    public DsPageDealing() {
		this(null, 0,-2,50);
	}
    
    public DsPageDealing(String sId, int pageType,int pageActionType, int perCount) {
		
		mapDealers = new HashMap<String,IPageDealer>();
		
		IPageDealer dealer = null;
		
		dealer = new JDPageDealer();
		mapDealers.put(dealer.getSiteId(), dealer);
	}

    public String[] getSetupSites()
    {
    	String[] sites = new String[1];
    	sites[0] = "jd";
    	return sites;
    }
    
    public String getFirstUrl(String siteId)
    {
		IPageDealer dealer = mapDealers.get(siteId);
		return dealer.getFirstUrl();
    }
	public List<PageRef> add(Object newPage){
		OriHttpPage pp = (OriHttpPage)newPage;
		IPageDealer dealer = mapDealers.get(pp.getSiteId());
		return dealer.deal(pp);
    }

	@Override
	public List<PageRef> getFirstRefs(String siteId) {
		return mapDealers.get(siteId).getFirstRefs();
	}

}
