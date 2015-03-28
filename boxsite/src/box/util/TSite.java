package box.util;

import es.simple.TPageTypes;
import es.simple.TSingleSpecieStage;

public class TSite extends TSingleSpecieStage {
	
	public TSite(TPageTypes ps,String _siteId){
		
		super(ps,_siteId);
	}
	
	static private String findSiteId(String siteUrl)
	{
		if (siteUrl.indexOf("jd.com")>0)
		 return IPageDealer.SITEID_JD;
		else if (siteUrl.indexOf("tmall.com")>0)
		return IPageDealer.SITEID_TMALL;
		
		return null;
	}
}
