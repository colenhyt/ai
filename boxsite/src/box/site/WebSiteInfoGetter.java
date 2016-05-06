package box.site;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import box.site.db.SiteService;
import box.site.model.Website;
import box.site.model.Websitekeys;
import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import es.download.flow.DownloadContext;
import es.util.http.PostPageGetter;

public class WebSiteInfoGetter extends Thread {
	protected Logger  log = Logger.getLogger(getClass()); 
    private HttpClient httpClient = null;
    private String alexaapi = "http://data.alexa.com/data?cli=10&url=";
	private String baiduRank = "http://baidurank.aizhan.com/baidu/";
	private String googlePr = "http://toolbarqueries.google.com/search?client=navclient-auto&features=Rank&ch=8&q=info:";
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	private String userAgent;
	private PostPageGetter pageGetter;
	
	public static void main(String[] args){
		WebSiteInfoGetter getter = new WebSiteInfoGetter();
		getter.start();
	}
	
	public void fillWebsiteInfo(){
//		int bdRank = 
		SiteService service = new SiteService();
		List<Website> list = service.getNotInfoWebsite();
		for (Website site:list){
			int alexa = this.getAlexa(site.getUrl());
			int bdrank = this.getBdRank(site.getUrl());
			site.setAlexa(alexa);
			site.setBdrank(bdrank);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			service.updateWebsite(site);
			service.DBCommit();
		}
//		service.updateWebsites(list);
		service.DBCommit();
	}
	public WebSiteInfoGetter(){
		userAgent = DownloadContext.getSpiderContext().getUserAgent();
		initHttpClient();
		pageGetter = new PostPageGetter();
	}
	
	public void run() {
		while (1==1){
			synchronized(this){
				fillWebsiteInfo();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void initHttpClient(){
		if (httpClient==null){
	      	httpClient = HttpClients.createDefault();
		}
	}
	
	public int getBdRank(String weburl){
		int rank=-1;
		String urlStr = baiduRank+weburl+"/position/";
		HttpPage page = pageGetter.getHttpPage(urlStr, httpClient);
		if (page.getContent()==null)
			return rank;
		String content  = new String(page.getContent());
		htmlHelper.init(page.getContent());
		String div = htmlHelper.getDivByClassValue("box_17");
		if (div!=null){
			String startKey = "/brs/";
			String endKey = ".gif";
			if (content.indexOf(startKey)<=0)
				return rank;
			String rankText = content.substring(content.indexOf(startKey)+startKey.length(),content.indexOf(endKey));
			if (rankText!=null)
				rank = Integer.valueOf(rankText);			
		}
		log.warn("bdrank "+rank);
		return rank;
	}
	
	public int getAlexa(String weburl){
		int alexa = -1;
		String urlStr = alexaapi + weburl;
		HttpPage page = pageGetter.getHttpPage(urlStr, httpClient);
		if (page.getContent()==null)
			return alexa;
		String content  = new String(page.getContent());
//		log.warn(content);
		String startKey = "TEXT=\"";
		String endKey = "\"";
		if (content.indexOf(startKey)<=0){
			log.warn("wrong alexa:"+content);
			return alexa;
		}
		String subt = content.substring(content.indexOf(startKey)+startKey.length());
		String rankText = subt.substring(0,subt.indexOf(endKey));
		if (rankText!=null)
			alexa = Integer.valueOf(rankText);
		log.warn("alexa "+alexa);
		return alexa;
	}
	
}
