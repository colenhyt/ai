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

public class SiteContentGetter extends Thread {
	protected Logger  log = Logger.getLogger(getClass()); 
    private HttpClient httpClient = null;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	private String siteId;
	private String userAgent;
	private PostPageGetter pageGetter;
	private Map<String,String> classKeys = new HashMap<String,String>();
	Set<OriHttpPage> pages = new HashSet<OriHttpPage>();
	private int nextWebsiteId = 0;
	
	public static void main(String[] args){
		SiteContentGetter getter = new SiteContentGetter(DownloadContext.getSpiderContext().getUserAgent());
	}
	public SiteContentGetter(String vuserAgent){
		userAgent = vuserAgent;
		initHttpClient();
		pageGetter = new PostPageGetter();
	}
	
	public void setSiteId(String site){
		siteId = site;
	}
	public void run() {
		while (1==1){
			synchronized(this){
				for (OriHttpPage page:pages){
					this.genWebSites(page);
				}
				pages.clear();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void pushPage(OriHttpPage page){
		pages.add(page);
	}
	
	public synchronized int getNextWebisteId(){
		if (nextWebsiteId==0){
		SiteService siteService = new SiteService();
		nextWebsiteId = siteService.getMaxSiteid();
		}
		nextWebsiteId++;
		return nextWebsiteId;
	}
	
	public synchronized void genWebSites(OriHttpPage page){
		Vector<Website> sites = findWebSites(page);
		SiteService siteService = new SiteService();
		
		
		//site insert:
		Vector<Website> sites2 = siteService.addSites(sites);
		
		//site word map:
		for (Website item:sites2){
			Websitekeys key = new Websitekeys();
			key.setSiteid(item.getSiteid());
			key.setWordid(page.getRefId());
			siteService.addSitekey(key);
		}
		
		//baiduurl update:
		siteService.updateSearchUrl(page.getUrlStr());
		
		siteService.DBCommit();
	}
	
	public void initHttpClient(){
		if (httpClient==null){
	      	httpClient = HttpClients.createDefault();
		}
	}
	
	public Vector<Website> findWebSites(OriHttpPage page){
		
		htmlHelper.init(page.getContent());
		Vector<Website> sites = new Vector<Website>();
		
		String classkey = classKeys.get(siteId);
		Vector<String> keys = new Vector<String>();
		keys.add("link?");
		keys.add("百度快照");
		if (classkey==null){
			Vector<String> vv2 = new Vector<String>();
			classkey = htmlHelper.findMaxSizeDivClassValue(keys, vv2);
			classKeys.put(siteId, classkey);
		}
		if (classkey==null){
			log.warn("error,没有找到该页面list item,根据关键字:"+keys.toString());
			return sites;
		}
		
		String[] itemsContent = htmlHelper.getDivsByClassValue(classkey);
		for (String itemstr:itemsContent){
			Website site = new Website();
			htmlHelper.init(itemstr.getBytes());
			List<String> urls =htmlHelper.getUrlStrsByLinkKey(keys.get(0));
			if (urls.size()<=0) continue;
			String url = urls.get(0);
			String realurl =new PostPageGetter(userAgent).getRealUrl(url, httpClient);
			if (realurl==null)
				realurl = url;
			site.setUrl(realurl);
			site.setBaiduurl(url);
			site.setSiteid(getNextWebisteId());
			site.setStatus(SiteDataManager.WEBSITE_STATUS_DONEURL);
			site.setRemark(page.getRefWord());
			site.setCrdate(new Date());
			sites.add(site);
		}
		log.warn("找到websites: "+sites.size());
		return sites;
	}
	
}
