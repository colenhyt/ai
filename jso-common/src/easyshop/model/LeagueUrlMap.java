package easyshop.model;

import es.util.url.URLStrFormattor;
import es.util.url.URLStrHelper;

public class LeagueUrlMap {
	static LeagueUrlMap map;
	public static LeagueUrlMap getInstance(){
		if (map==null)
			map=new LeagueUrlMap();
		return map;
	}
	public static String MID_yiqifa="33149";
	public static String LEAGUE_CHINAPUB="http://click.linktech.cn/?m=chinapub&a=A100037888&l=99999&u_id=easybikee&l_type2=0&tu=";
	public static String LEAGUE_N99="http://click.linktech.cn/?m=99read&a=A100037888&l=99999&u_id=easybikee&l_type2=0&tu=";
	public static String LEAGUE_2688="http://click.linktech.cn/?m=2688&a=A100037888&l=99999&u_id=easybikee&l_type2=0&tu=";
	public static String LEAGUE_DANGDANG_OWN="http://www.dangdang.com/league/leagueref.asp?from=P-242112&backurl=";
	public static String LEAGUE_DD_yiqifa="http://www.dangdang.com/league/leagueref.asp?from=";
	public static String LEAGUE_DANGDANG_lkt="http://click.linktech.cn/?m=dangdang&a=A100037888&l=99999&u_id=easybikee&l_type2=0&tu=";
	public static String LEAGUE_JOYO_OWN="source=allenhyt";
	public static String LEAGUE_JOYO="http://click.linktech.cn/?m=joyo&a=A100037888&l=99999&u_id=easybikee&l_type2=0&tu=";
//	public static String LEAGUE_WELAN="id=12471";
	public static String LEAGUE_WELAN="http://click.linktech.cn/?m=welan&a=A100037888&l=99999&u_id=easybikee&l_type2=0&tu=";
//	public static String LEAGUE_BCCHINA="adservice=353141";
	public static String LEAGUE_BCCHINA="http://click.linktech.cn/?m=bookschina&a=A100037888&l=99999&u_id=easybikee&l_type2=0&tu=";
	public static String LEAGUE_D1="http://click.linktech.cn/?m=d1bianli&a=A100037888&l=99999&u_id=easybikee&l_type2=0&tu=";
	public static String LEAGUE_SINOSHU="dl_userid=easybikee";
	public String getLeagueUrl(String urlStr){
			if (urlStr.indexOf("dangdang.com")>0)
				return LEAGUE_DANGDANG_lkt+URLStrFormattor.encode(urlStr);
	//			return LEAGUE_DANGDANG+urlStr;
			else if (urlStr.indexOf("amazon.cn")>0||urlStr.indexOf("joyo.com")>0){
				return LEAGUE_JOYO+URLStrFormattor.encode(urlStr);
	//			return mergeParam(urlStr,LEAGUE_JOYO);
			}else if (urlStr.indexOf("welan.com")>0){
				return LEAGUE_WELAN+URLStrFormattor.encode(urlStr);
	//			return mergeParam(urlStr,LEAGUE_WELAN);
			}else if (urlStr.indexOf("bookschina.com")>0){
				return LEAGUE_BCCHINA+URLStrFormattor.encode(urlStr);
	//			return mergeParam(urlStr,LEAGUE_BCCHINA);
			}else if (urlStr.indexOf("sinoshu.com")>0){
				return mergeParam(urlStr,LEAGUE_SINOSHU);
			}else if (urlStr.indexOf("china-pub.com")>0){
				return LEAGUE_CHINAPUB+URLStrFormattor.encode(urlStr);
			}else if (urlStr.indexOf("99read.com")>0){
				return LEAGUE_N99+URLStrFormattor.encode(urlStr);
			}else if (urlStr.indexOf("2688.com")>0){
				return LEAGUE_2688+URLStrFormattor.encode(urlStr);
			}else
				return urlStr;
		}

	public String getLeagueUrl(String urlStr,long memberId){
		if (urlStr.indexOf("dangdang.com")>0)
			return LEAGUE_DD_yiqifa+"419-"+MID_yiqifa+"|"+memberId+"&backurl="+URLStrFormattor.encode(urlStr);
		else if (urlStr.indexOf("amazon.cn")>0||urlStr.indexOf("joyo.com")>0){
			return mergeParam(urlStr,"source=eqifa|"+MID_yiqifa+"|"+memberId);
		}else if (urlStr.indexOf("welan.com")>0||urlStr.indexOf("wl.cn")>0){
			return "http://www.welan.com/union/eqifafront.asp?website_id="+MID_yiqifa+"|"+memberId+"&url="+URLStrFormattor.encode(urlStr);
//			return mergeParam(urlStr,LEAGUE_WELAN);
		}else if (urlStr.indexOf("bookschina.com")>0){
			return "http://www.bookschina.com/union/eqifabook.asp?website_id="+MID_yiqifa+"&eu_id="+memberId+"&tourl="+URLStrFormattor.encode(urlStr);
//			return LEAGUE_BCCHINA+URLStrFormattor.encode(urlStr);
//			return mergeParam(urlStr,LEAGUE_BCCHINA);
		}else if (urlStr.indexOf("sinoshu.com")>0){
			return mergeParam(urlStr,LEAGUE_SINOSHU);
		}else if (urlStr.indexOf("china-pub.com")>0){
			return mergeParam(urlStr,"website_id="+MID_yiqifa+"&eu_id="+memberId);
		}else if (urlStr.indexOf("99read.com")>0){
			return "http://www.99read.com/union/eqifafront.aspx?a_id=791&b_id="+MID_yiqifa+"&eu_id="+memberId+"&rd=30&rd_type=D&url="+
			URLStrFormattor.encode(urlStr);
		}else if (urlStr.indexOf("2688.com")>0){
			return mergeParam(urlStr,"agentFrom=677596&LMID=768|"+MID_yiqifa+"|"+memberId);
		}else if (urlStr.indexOf("d1.com.cn")>0){
			return "http://www.d1.com.cn/buy/eqifa.asp?website_id="+MID_yiqifa+"|"+memberId+"&url="+URLStrFormattor.encode(urlStr);
		}else if (urlStr.indexOf("huachu.com.cn")>0){
			return mergeParam(urlStr,"website_id="+MID_yiqifa+"&eu_id="+memberId);
		}else
			return urlStr;
	}
	
	public static String convertUrlStr(String leagalUrl){
		leagalUrl=leagalUrl.toLowerCase();
		if (leagalUrl.indexOf("dangdang.com")>0)
			return leagalUrl.substring(leagalUrl.indexOf("backurl=")+"backurl=".length()+1);
		else if (leagalUrl.indexOf("amazon.cn")>0||leagalUrl.indexOf("joyo.com")>0){
			return URLStrHelper.cutParameter(leagalUrl,"source");
		}else if (leagalUrl.indexOf("welan.com")>0){
			return URLStrHelper.cutParameter(leagalUrl,"id");
		}else if (leagalUrl.indexOf("bookschina.com")>0){
			return URLStrHelper.cutParameter(leagalUrl,"adservice");
		}else if (leagalUrl.indexOf("sinoshu.com")>0){
			return URLStrHelper.cutParameter(leagalUrl,"dl_userid");
		}else if (leagalUrl.indexOf("china-pub.com")>0){
			String url=leagalUrl.substring(leagalUrl.indexOf("&tu=")+"&tu=".length()+1);
			return URLStrFormattor.decode(url);
		}else if (leagalUrl.indexOf("99read.com")>0){
			String url=leagalUrl.substring(leagalUrl.indexOf("&tu=")+"&tu=".length()+1);
			return URLStrFormattor.decode(url);
		}else if (leagalUrl.indexOf("2688.com")>0){
			String url=leagalUrl.substring(leagalUrl.indexOf("&tu=")+"&tu=".length()+1);
			return URLStrFormattor.decode(url);
		}else
			return leagalUrl;		
	}
	public static String getLeagueRoot(String urlStr){
		if (urlStr.indexOf("dangdang.com")>0)
			return LEAGUE_DANGDANG_lkt+"http://www.dangdang.com";
		else if (urlStr.indexOf("amazon.cn")>0||urlStr.indexOf("joyo.com")>0){
			return mergeParam("http://www.amazon.cn",LEAGUE_JOYO);
		}else if (urlStr.indexOf("welan.com")>0||urlStr.indexOf("wl.cn")>0){
			return mergeParam("http://www.welan.cn",LEAGUE_WELAN);
		}else if (urlStr.indexOf("bookschina.com")>0){
			return mergeParam("http://www.bookschina.com",LEAGUE_BCCHINA);
		}else if (urlStr.indexOf("sinoshu.com")>0){
			return mergeParam("http://www.sinoshu.com",LEAGUE_SINOSHU);
		}else if (urlStr.indexOf("china-pub.com")>0){
			return LEAGUE_CHINAPUB+"http%3A%2F%2Fwww.china-pub.com";
		}else if (urlStr.indexOf("99read.com")>0){
			return LEAGUE_N99+"http%3A%2F%2Fwww.99read.com";
		}else if (urlStr.indexOf("2688.com")>0){
			return LEAGUE_2688+"http%3A%2F%2Fwww.2688.com";
		}else
			return urlStr;
	}

	public static String getLeagueRoot(String urlStr,long memberId){
		if (urlStr.indexOf("dangdang.com")>0)
			return LEAGUE_DD_yiqifa+"419-"+MID_yiqifa+"|"+memberId+"&backurl="+"http://www.dangdang.com";
		else if (urlStr.indexOf("amazon.cn")>0||urlStr.indexOf("joyo.com")>0){
			return mergeParam("http://www.amazon.cn","source=eqifa|"+MID_yiqifa+"|"+memberId);
		}else if (urlStr.indexOf("welan.com")>0){
			return "http://www.welan.com/union/eqifafront.asp?website_id="+MID_yiqifa+"|"+memberId+"&url="+"http://www.welan.cn";
		}else if (urlStr.indexOf("bookschina.com")>0){
			return "http://www.bookschina.com/union/eqifabook.asp?website_id="+MID_yiqifa+"&eu_id="+memberId+"&tourl=http://www.bookschina.com";
//			return mergeParam("http://www.bookschina.com",LEAGUE_BCCHINA);
		}else if (urlStr.indexOf("sinoshu.com")>0){
			return mergeParam("http://www.sinoshu.com",LEAGUE_SINOSHU);
		}else if (urlStr.indexOf("china-pub.com")>0){
			return mergeParam("http://www.china-pub.com","website_id="+MID_yiqifa+"&eu_id="+memberId);
		}else if (urlStr.indexOf("99read.com")>0){
			return "http://www.99read.com/union/eqifafront.aspx?a_id=791&b_id="+MID_yiqifa+"&eu_id="+memberId+"&rd=30&rd_type=D&url="+
			"http%3A%2F%2Fwww.99read.com";
		}else if (urlStr.indexOf("2688.com")>0){
			return mergeParam("http://www.2688.com","agentFrom=677596&LMID=768|"+MID_yiqifa+"|"+memberId);
		}else if (urlStr.indexOf("d1.com.cn")>0){
			return "http://www.d1.com.cn/buy/eqifa.asp?website_id="+MID_yiqifa+"|"+memberId+"&url=http://www.d1.com.cn";
		}else if (urlStr.indexOf("huachu.com.cn")>0){
			return mergeParam("http://www.huachu.com.cn","website_id="+MID_yiqifa+"&eu_id="+memberId);
		}else
			return urlStr;
	}	
	private static String mergeParam(String urlStr,String paraStr){
		if (urlStr.indexOf("?")>0){
			if (urlStr.endsWith("&"))
				return urlStr+paraStr;
			else
				return urlStr+"&"+paraStr;
		}else
			return urlStr+"?"+paraStr;		
	}
}
