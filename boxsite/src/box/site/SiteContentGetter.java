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
    private String alexaapi = "http://data.alexa.com/data?cli=10&url=";
	private String baiduRank = "http://baidurank.aizhan.com/baidu/";
	private String googlePr = "http://toolbarqueries.google.com/search?client=navclient-auto&features=Rank&ch=8&q=info:";
	private Map<String,String> classKeys = new HashMap<String,String>();
	Set<OriHttpPage> pages = new HashSet<OriHttpPage>();
	private int nextWebsiteId = 0;
	
	public static void main(String[] args){
		SiteContentGetter getter = new SiteContentGetter();
	}
	public SiteContentGetter(){
		userAgent = DownloadContext.getSpiderContext().getUserAgent();
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
					this.genWebSites(page,false);
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
	
	public synchronized void genWebSites(OriHttpPage page,boolean findInfo){
		Vector<Website> sites = findWebSites(page);
		SiteService siteService = new SiteService();
		
		if (findInfo){
			for (Website site:sites){
				if (site.getUrl()!=null){
					site.setAlexa(this.getAlexa(site.getUrl()));
					site.setBdrank(this.getBdRank(site.getUrl()));	
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
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
			site.setBaiduurl(url);
			site.setSiteid(getNextWebisteId());
			site.setStatus(SiteDataManager.WEBSITE_STATUS_DONEURL);
			if (realurl!=null){
				site.setUrl(realurl);
//				site.setAlexa(this.getAlexa(realurl));
//				site.setBdrank(this.getBdRank(realurl));
			}
			site.setRemark(page.getRefWord());
			site.setCrdate(new Date());
			sites.add(site);
		}
		log.warn("找到websites: "+sites.size());
		return sites;
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
	
}
