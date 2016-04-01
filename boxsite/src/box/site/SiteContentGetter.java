package box.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import box.site.db.SiteService;
import box.site.model.Website;
import box.site.model.Websitekeys;
import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import es.util.http.PostPageGetter;

public class SiteContentGetter extends Thread {
    private HttpClient httpClient = null;
    private int maxConnPerHost=2;
    private int timeOut=6000;
    private int maxTotalConn=20;
    public final static String HTTP_USER_AGENT="Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)";
  
    private String alexaapi = "http://data.alexa.com/data?cli=10&url=%s";
	private String baiduRank = "http://baidurank.aizhan.com/baidu/%s/position/";
	private String googlePr = "http://toolbarqueries.google.com/search?client=navclient-auto&features=Rank&ch=8&q=info:";
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	private String siteId;
	private Map<String,String> classKeys = new HashMap<String,String>();
	Set<OriHttpPage> pages = new HashSet<OriHttpPage>();
	private int nextWebsiteId = 0;
	private int nextWordId = 0;
	
	public SiteContentGetter(){
		
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
	
	public synchronized int getNextWordId(){
		if (nextWordId==0){
		SiteService siteService = new SiteService();
		nextWordId = siteService.getMaxWordid();
		}
		nextWordId++;
		return nextWordId;
	}
	
	public synchronized void genWebSites(OriHttpPage page){
		Vector<Website> sites = findWebSites(page);
		SiteService siteService = new SiteService();
		
		
		//site insert:
		siteService.addSites(sites);
		
		//site word map:
		for (Website item:sites){
			Websitekeys key = new Websitekeys();
			key.setSiteid(item.getSiteid());
			key.setWordid(page.getRefId());
			siteService.addSitekey(key);
		}
		
		//searchurl update:
		siteService.updateSearchUrl(page.getUrlStr());
		
		siteService.DBCommit();
	}
	
	public void initHttpClient(){
		if (httpClient==null){
	      	httpClient = HttpClients.createDefault();
		}
	}
	
	public Vector<Website> findWebSites(OriHttpPage page){
		initHttpClient();
		
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
			System.out.println("error,没有找到该页面list item");
			return sites;
		}
		
		String[] itemsContent = htmlHelper.getDivsByClassValue(classkey);
		for (String itemstr:itemsContent){
			Website site = new Website();
			htmlHelper.init(itemstr.getBytes());
			List<String> urls =htmlHelper.getUrlStrsByLinkKey(keys.get(0));
			if (urls.size()<=0) continue;
			String url = urls.get(0);
			String realurl =new PostPageGetter().getRealUrl(url, httpClient);
			site.setUrl(realurl);
			site.setBaiduurl(url);
			site.setSiteid(getNextWebisteId());
			site.setStatus(SiteDataManager.WEBSITE_STATUS_DONEURL);
			site.setAlexa(this.getAlexa(site.getUrl()));
			site.setBdrank(this.getBdRank(site.getUrl()));	
			site.setRemark(page.getRefWord());
			sites.add(site);
		}

		return sites;
	}
	
	public int getBdRank(String weburl){
		int rank=0;
		return rank;
	}
	
	public int getAlexa(String weburl){
		int alexa = 0;
		return alexa;
	}
	
}
