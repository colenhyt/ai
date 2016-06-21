package box.site.processor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Page;
import box.mgr.SiteManager;
import box.site.SiteContentGetter;
import box.site.SiteDataManager;
import box.site.model.Website;
import easyshop.html.HTMLInfoSupplier;
import es.util.url.URLStrHelper;

public class SogouSiteFinder implements IItemFinder {
	protected Logger  log = Logger.getLogger(getClass());
	Set<String>		relateWords = new HashSet<String>();
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	private SiteContentGetter contentGetter = new SiteContentGetter();
	private Set<String> stopDomains = new HashSet<String>();

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
			String url = sogouurl ;
			String siteword = null;
			if (linstr!=null&&linstr.indexOf("-")>0){
				int i0 = linstr.indexOf("-");
				siteword = linstr.substring(0,i0);
				int i1 = linstr.indexOf("/");
				int i2 = linstr.indexOf("...");
				if (i1>i0+1)
					url = linstr.substring(i0+1,i1);
				else if (i2>i0+1)
					url = linstr.substring(i0+1,i2);
			}
			if (url!=null)
				url = url.trim();
			boolean foundStop = false;
			for (String domain:stopDomains){
				if (url.indexOf(domain)>0){
					foundStop = true;
					break;
				}
			}
			if (foundStop) continue;			
			
			Website site = new Website();
			site.setBaiduurl(url);
			site.setStatus(SiteDataManager.WEBSITE_STATUS_DONEURL);
			if (url!=null){
				site.setUrl(url);
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
		page.putField("items", sites);
		
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
