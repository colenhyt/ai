/*
 * Created on 2006-6-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package box.util;

import java.util.HashMap;
import java.util.Map;

import box.sites.JDPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import es.download.DownloadAfterDealer;
import es.download.NewOriginalPagesSaver;
import es.webref.model.PageRef;


/**
 * Tracy&Allen.EasyShop 0.9
 *
 * @author Allenhuang
 * 
 * created on 2006-6-10
 */
public class PageDealing extends NewOriginalPagesSaver{
	private Map<String,IPageDealer>		mapDealers;
	
	 DownloadAfterDealer dealder=new DownloadAfterDealer();
	 int perCount=100;
	 
    public PageDealing(String sId,int pageType) {
		this(sId, pageType,-2,50);
	}
    
    public PageDealing(String sId, int pageType,int pageActionType, int perCount) {
		super(sId, pageType,pageActionType,perCount);
		
		mapDealers = new HashMap<String,IPageDealer>();
		
		IPageDealer dealer = null;
		
		dealer = new JDPageDealer();
		mapDealers.put(dealer.getSiteId(), dealer);
	}

	public void add(Object newPage){
		OriHttpPage pp = (OriHttpPage)newPage;
		IPageDealer dealer = mapDealers.get(pp.getSiteId());
		dealer.deal(pp);
    }

	public void setDoDeal(boolean save) {
		// TODO Auto-generated method stub
		
	}
    
}
