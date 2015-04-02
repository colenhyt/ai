package cl.site.util;

import es.datamodel.AnalysersFactory;
import es.datamodel.PageContentAnalyser;
import es.util.url.URLStrHelper;

public class SiteStrConverter {
	public static String toSiteKey(String siteId){
        int i=siteId.indexOf(".");
        String siteKey="";
        if (i>0)
        	siteKey=siteId.substring(i+1);
        
        return siteKey;
	}
	
	public static String getShopItemUrlKey(String urlStr){
		String siteId=getShopSiteId(urlStr);
		PageContentAnalyser canalyser = (PageContentAnalyser) AnalysersFactory.get()
		.findContentMap().get(siteId);
		if (canalyser!=null)
			return canalyser.getDictionary().getItemKey(urlStr);
		else
			return null;
	}
	
	public static String getShopSiteId(String urlStr){
		if (urlStr.indexOf("dangdang.com")>0)
			return "dangdang";
		else if (urlStr.indexOf("china-pub.com")>0)
			return "chinapub";
		else if (urlStr.indexOf("amazon.cn")>0||urlStr.indexOf("joyo.com")>0)
			return "joyo";
		else if (urlStr.indexOf("wl.cn")>0||urlStr.indexOf("welan.com")>0)
			return "welan";
		else if (urlStr.indexOf("2688.com")>0)
			return "2688";
		else if (urlStr.indexOf("wfjsd.com")>0)
			return "wfj";
		else if (urlStr.indexOf("chaoyi.com")>0)
			return "chaoyi";
		else if (urlStr.indexOf("sinoshu.com")>0)
			return "sinoshu";
		else if (urlStr.indexOf("bookschina.com")>0)
			return "bookschina";
		else if (urlStr.indexOf("jingqi.com")>0)
			return "jingqi";
		else if (urlStr.indexOf("dearbook.com")>0)
			return "dearbook";
		else if (urlStr.indexOf("huachu.com")>0)
			return "huachu";
		else if (urlStr.indexOf("jqcq.com")>0)
			return "jqcq";
		else if (urlStr.indexOf("golden-book.com.com")>0)
			return "gb";
		else if (urlStr.indexOf("dayoo.com")>0)
			return "dy";
		else if (urlStr.indexOf("jqcq.com")>0)
			return "jqcq";
		else if (urlStr.indexOf("buildbook.com")>0)
			return "buildbook";
		else if (urlStr.indexOf("99read.com")>0)
			return "n9";
		else if (urlStr.indexOf("www.d1.com.cn")>0)
			return "d1";
		return null;
	}
	public static String getSiteId(String urlStr){
		if (urlStr==null) return null;
		
		urlStr=urlStr.toLowerCase();
		
		String host=URLStrHelper.getHost(urlStr);

		String pre1="";
		if (urlStr.startsWith("http://"))
			pre1="h.";
		else if (urlStr.startsWith("https://"))
			pre1="hs.";
		else if (urlStr.startsWith("ftp://"))
			pre1="f.";
		else if (urlStr.startsWith("ftps://"))
			pre1="fs.";
		//组装:
		if (host!=null)
			return pre1+host;
		return null;
	}
}
