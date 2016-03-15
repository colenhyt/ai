package box.site;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	private String siteId;
	private Map<String,String> classKeys = new HashMap<String,String>();
	Set<OriHttpPage> pages = new HashSet<OriHttpPage>();
	
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
			
		}
	}
	
	public synchronized void pushPage(OriHttpPage page){
		pages.add(page);
	}
	
	public synchronized void genWebSites(OriHttpPage page){
		Vector<Website> sites = findWebSites(page);
		SiteService siteService = new SiteService();
		
		siteService.addSites(sites);
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
			System.out.println("error,没有找到该页面list item");
			return sites;
		}
		
		String[] itemsContent = htmlHelper.getDivsByClassValue(classkey);
		for (String itemstr:itemsContent){
			Website site = new Website();
			htmlHelper.init(itemstr.getBytes());
			List<String> urls =htmlHelper.getUrlStrsByLinkKey(keys.get(0));
			if (urls.size()<=0) continue;
			site.setUrl(urls.get(0));
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
