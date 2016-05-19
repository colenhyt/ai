package box.site;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import box.site.model.WebsiteDNA;
import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import es.webref.model.PageRef;

public class WebSiteDealer implements IPageDealer {
	protected Logger  log = Logger.getLogger(getClass()); 
	private Map<String,WebsiteDNA> webDnas;
	private OriHttpPage page;
	private SiteDNAParser dnaParser;

	private static final String siteId = "website";
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	
	public WebSiteDealer(){
		webDnas = Collections
				.synchronizedMap(new HashMap<String,WebsiteDNA>());
		dnaParser = new SiteDNAParser();
	}
	
	@Override
	public List<PageRef> deal(OriHttpPage _page) {
		page = _page;
		List<PageRef> newurls = new ArrayList<PageRef>();
		log.debug("get page "+_page.getUrlStr());
		String domainName = _page.getUrlStr();
		WebsiteDNA dna = webDnas.get(domainName);
		if (dna==null){
			dna = dnaParser.parse2DNAs(_page);
			if (dna!=null){
				webDnas.put(domainName, dna);
			}
		}
		if (dna==null){
			log.warn("could not parse site dna:"+domainName+",urlstr:"+_page.getUrlStr());
			return newurls;
		}
		
		//分页urls:
		newurls.addAll(findPagingRefs(dna));
		
		return newurls;
	}
	
	private List<PageRef> findPagingRefs(WebsiteDNA dna){
		List<PageRef> newurls = new ArrayList<PageRef>();

		log.warn("get site urls:"+newurls.size());
		return newurls;
	}
	@Override
	public String getSiteId() {
		// TODO Auto-generated method stub
		return siteId;
	}

	@Override
	public String getFirstUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<PageRef> getFirstRefs() {
		// TODO Auto-generated method stub
		List<PageRef> urls = new ArrayList<PageRef>();


		return urls;
	}

	@Override
	public void pushSearchWord(String word) {
		// TODO Auto-generated method stub
		
	}

}
