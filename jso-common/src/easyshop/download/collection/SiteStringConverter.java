/*
 * Created on 2005-11-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.download.collection;

import es.cfg.Configuration;


/**
 * @author Allenhuang
 *
 * created on 2005-11-3
 */
public class SiteStringConverter {
    
	public static String getSiteId(String urlStr){
		if (urlStr.indexOf("dangdang.com")>0)
			return "dangdang";
		else  if (urlStr.indexOf("amazon.cn")>0||urlStr.indexOf("joyo.com")>0)
			return "joyo";
		else  if (urlStr.indexOf("china-pub.com")>0)
			return "chinapub";
		else  if (urlStr.indexOf("jingqi.com")>0)
			return "jingqi";
		else  if (urlStr.indexOf("wl.cn")>0||urlStr.indexOf("welan.com")>0)
			return "welan";
		else  if (urlStr.indexOf("99read.com")>0)
			return "n99";
		else  if (urlStr.indexOf("bookschina.com")>0)
			return "bookschina";
		else  if (urlStr.indexOf("dearbook.com")>0)
			return "dearbook";
		else  if (urlStr.indexOf("sinoshu.com")>0)
			return "sinoshu";
		else  if (urlStr.indexOf("wfjsd.com")>0)
			return "wfj";
		else  if (urlStr.indexOf("golden-book.com")>0)
			return "gb";
		else  if (urlStr.indexOf("2688.com")>0)
			return "2688";
		else  if (urlStr.indexOf("dayoo.com")>0)
			return "dayoo";
		else  if (urlStr.indexOf("chaoyishudian.com")>0)
			return "chaoyi";
		else  if (urlStr.indexOf("buildbook.com.cn")>0)
			return "buildbook";
		else  if (urlStr.indexOf("jqcq.com")>0)
			return "jqcq";
		else
			return "";
	}
	
	public static String getSiteDesc(String urlStr){
		if (urlStr.indexOf("dangdang.com")>0)
			return "当当网";
		else  if (urlStr.indexOf("amazon.cn")>0||urlStr.indexOf("joyo.com")>0)
			return "卓越亚马逊";
		else  if (urlStr.indexOf("china-pub.com")>0)
			return "ChinaPub互动出版";
		else  if (urlStr.indexOf("jingqi.com")>0)
			return "旌旗图书网";
		else  if (urlStr.indexOf("wl.cn")>0||urlStr.indexOf("welan.com")>0)
			return "蔚蓝网";
		else  if (urlStr.indexOf("99read.com")>0)
			return "99图书网";
		else  if (urlStr.indexOf("bookschina.com")>0)
			return "中国图书网";
		else  if (urlStr.indexOf("dearbook.com")>0)
			return "第二书店";
		else  if (urlStr.indexOf("sinoshu.com")>0)
			return "中国书网";
		else  if (urlStr.indexOf("wfjsd.com")>0)
			return "王府井书店";
		else  if (urlStr.indexOf("golden-book.com")>0)
			return "中国科技金书网";
		else  if (urlStr.indexOf("2688.com")>0)
			return "2688图书网";
		else  if (urlStr.indexOf("dayoo.com")>0)
			return "大洋书城";
		else  if (urlStr.indexOf("chaoyishudian.com")>0)
			return "超逸书店";
		else  if (urlStr.indexOf("buildbook.com.cn")>0)
			return "工成网";
		else  if (urlStr.indexOf("jqcq.com")>0)
			return "金桥书城";
		else
			return "";
	}
    public static String getSiteString(String str){
        return Configuration.get().getSiteKeyStr(str);
    }

}
