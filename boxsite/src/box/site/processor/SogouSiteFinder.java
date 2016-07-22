package box.site.processor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import box.mgr.SiteManager;
import box.site.SiteContentGetter;
import box.site.SiteDataManager;
import box.site.model.Website;

import com.alibaba.fastjson.JSON;

import easyshop.html.HTMLInfoSupplier;
import es.download.flow.DownloadContext;

public class SogouSiteFinder implements IItemFinder {
	protected Logger  log = Logger.getLogger(getClass());
	Set<String>		relateWords = new HashSet<String>();
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	private HttpClient httpClient = HttpClients.createDefault();
	private SiteContentGetter contentGetter = new SiteContentGetter();
	private Set<String> stopDomains = new HashSet<String>();
	private String userAgent;

	public SogouSiteFinder(){
		userAgent = DownloadContext.getSpiderContext().getUserAgent();
		
	}
	
	@Override
	public String getStartUrl(String word) {
		return "https://www.sogou.com/web?query="+word;
	}

	public Vector<Website> findWebsites(String content){
		Vector<Website> sites = new Vector<Website>();
		htmlHelper.init(content.getBytes());
		String[] strs = htmlHelper.getFullBlocksByOneProp("div", "class", "vrwrap");
		for (String str:strs){
			htmlHelper.init(str.getBytes());
			List<String> urls =htmlHelper.getUrlStrs();
			if (urls.size()<=0) continue;
			String sogouurl = urls.get(0);
			
			String div = htmlHelper.getDivByClassValue("fb");
			if (div==null) continue;
			htmlHelper.init(div.getBytes());
			String linstr = htmlHelper.getBlock("cite");
			String realurl = null ;
			String siteword = null;
			if (linstr!=null&&linstr.indexOf("-")>0){
				int i0 = linstr.indexOf("-");
				String substr = linstr.substring(0,i0);
				if (substr.indexOf("/")>0)
					realurl = linstr.substring(0,substr.indexOf("/"));
				else {
					siteword = substr;
					int i1 = linstr.indexOf("/");
					int i2 = linstr.indexOf("...");
					int i3 = linstr.indexOf("-",i0+1);
					if (i1>i0+1)
						realurl = linstr.substring(i0+1,i1);
					else if (i2>i0+1)
						realurl = linstr.substring(i0+1,i2);
					else if (i3>i0+1)
						realurl = linstr.substring(i0+1,i3);
				}
			}
			
			if (realurl!=null){
				realurl = realurl.trim();
				if (realurl.indexOf("&nbsp;")>0)
					realurl = realurl.substring(0,realurl.indexOf("&nbsp;"));
			}
			boolean foundStop = false;
			for (String domain:stopDomains){
				if (realurl.indexOf(domain)>0){
					foundStop = true;
					break;
				}
			}
			if (foundStop) continue;			
			
			Website site = new Website();
			site.setBaiduurl(sogouurl);
			site.setStatus(SiteDataManager.WEBSITE_STATUS_DONEURL);
			if (realurl!=null){
				site.setUrl(realurl);
			}
			site.setCtitle(siteword);
			site.setCrdate(new Date());
			
			sites.add(site);			
		}
		return sites;
	}
	
	@Override
	public void process(Page page) {
		stopDomains.add(".org");
		stopDomains.add(".gov");
		//1. find sites:
		Set<String> sites = new HashSet<String>();
		Vector<Website> sitesVec = this.findWebsites(page.getRawText());
		
		Set<Website> newSites = SiteManager.getInstance().addSites(sitesVec);
		for (Website site:newSites){
//			contentGetter.fillSiteInfo(site);
			sites.add(JSON.toJSONString(site));
		}		
		page.putField("sites", sites);
		
		//2. find paging urls:
		String regx = ".*s.*query=.*&page=[0-9]*.*";
		List<String> pagingurls = page.getHtml().links().regex(regx).all();
		if (pagingurls.size()<=0){
			log.warn("could not find paging urls ");
			page.putField("pagingurls", pagingurls);
			return;
		}
		page.putField("pagingurls", pagingurls);
		
		//3. find related words:
//		List<String> r2 = page.getHtml().xpath("//div[@id=rs").links().all();
//		for (String str:r2){
//			String hexwstr = URLStrHelper.getParamValue(str, "wd");
//			String wstr = URLStrHelper.unescape(hexwstr);
//			relateWords.add(wstr);
//		}
//		page.putField("relateWords", relateWords);
	}

}
