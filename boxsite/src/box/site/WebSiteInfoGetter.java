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

public class WebSiteInfoGetter extends SiteContentGetter {
	protected Logger  log = Logger.getLogger(getClass()); 
    private HttpClient httpClient = null;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	
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
	
}
