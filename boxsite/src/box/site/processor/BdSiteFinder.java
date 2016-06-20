package box.site.processor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.Page;
import box.mgr.SiteManager;
import box.site.SiteContentGetter;
import box.site.model.Website;
import es.util.url.URLStrHelper;

public class BdSiteFinder implements IItemFinder {
	protected Logger  log = Logger.getLogger(getClass());
	Set<String>		relateWords = new HashSet<String>();
	private SiteContentGetter contentGetter = new SiteContentGetter();

	@Override
	public String getStartUrl(String word) {
		return "https://www.baidu.com/s?wd="+word;
	}

	@Override
	public void process(Page page) {
		//1. find sites:
		Set<String> sites = new HashSet<String>();
		Vector<Website> sitesVec = contentGetter.findWebSitesInPage(page.getRawText().getBytes(), null,false);
		
		Set<Website> newSites = SiteManager.getInstance().addSites(sitesVec);
		for (Website site:newSites){
//			contentGetter.fillSiteInfo(site);
			sites.add(JSON.toJSONString(site));
		}		
		page.putField("items", sites);
		
		//2. find paging urls:
		String regx = ".*s.*wd=.*&pn=.*";
		List<String> pagingurls = page.getHtml().links().regex(regx).all();
		if (pagingurls.size()<=0){
			log.warn("could not find paging urls ");
			page.putField("pagingurls", pagingurls);
			return;
		}
		page.putField("pagingurls", pagingurls);
		
		//3. find related words:
		List<String> r2 = page.getHtml().xpath("//div[@id=rs").links().all();
		for (String str:r2){
			String hexwstr = URLStrHelper.getParamValue(str, "wd");
			String wstr = URLStrHelper.unescape(hexwstr);
			relateWords.add(wstr);
		}
		page.putField("relateWords", relateWords);
	}

}
