package box.site;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import box.site.db.SiteService;
import box.site.model.Website;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;

public class SiteContentGetter extends Thread {
	private String alexaapi = "http://data.alexa.com/data?cli=10&url=%s";
	private String baiduRank = "http://baidurank.aizhan.com/baidu/%s/position/";
	private String googlePr = "http://toolbarqueries.google.com/search?client=navclient-auto&features=Rank&ch=8&q=info:";
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	SiteService siteService = new SiteService();
	Set<OriHttpPage> pages = new HashSet<OriHttpPage>();
	
	public void run() {
		while (1==1){
			synchronized(this){
				for (OriHttpPage page:pages){
					this.genWebSites(page);
				}
				pages.clear();
			}
		}
	}
	
	public synchronized void pushPage(OriHttpPage page){
		pages.add(page);
	}
	
	public synchronized void genWebSites(OriHttpPage page){
		Vector<Website> sites = findWebSites(page);
		siteService.addSites(sites);
	}
	
	public Vector<Website> findWebSites(OriHttpPage page){
		htmlHelper.init(page.getContent());
		Vector<Website> sites = new Vector<Website>();
		
		String[] itemsContent = htmlHelper.getDivsByClassValue("result c-container ");
		for (String itemstr:itemsContent){
			Website site = new Website();
			site.setUrl(itemstr);
			site.setAlexa(this.getAlexa(site.getUrl()));
			site.setBdrank(this.getBdRank(site.getUrl()));	
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
