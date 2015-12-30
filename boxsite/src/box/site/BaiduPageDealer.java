package box.site;

import java.util.ArrayList;
import java.util.List;

import box.site.db.SiteService;
import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import es.webref.model.PageRef;

public class BaiduPageDealer implements IPageDealer {
	private static final String siteId = "baidu";
	private OriHttpPage page;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	SiteService siteService = new SiteService();
	SiteContentGetter siteGetter = new SiteContentGetter();
	
	public BaiduPageDealer(){
		siteGetter.start();
	}
	
	@Override
	public List<PageRef> deal(OriHttpPage _page) {
		page = _page;
		siteGetter.pushPage(_page);
		List<PageRef> newurls = findPagingRefs();
		
		return newurls;
	}
	
	private List<PageRef> findPagingRefs(){
		List<PageRef> newurls = new ArrayList<PageRef>();
		htmlHelper.init(page.getContent());
		String[] pagingContent = htmlHelper.getDivsByClassValue("result c-container ");
		for (int i=0;i<pagingContent.length;i++){
			String pc = pagingContent[i];
			htmlHelper.init(pc.getBytes());
			String[] divs = htmlHelper.getDivsByClassValue("f13");
			for (int j=0;j<divs.length;j++){
				htmlHelper.init(divs[j].getBytes());
				String div = htmlHelper.getBlockByOneProp("span", "class", "g");
				div += "a";
				String url = div;
				if (siteService.addSearchUrl(url)){
					PageRef ref = new PageRef(url);
					newurls.add(ref);
				}
			}
		}
		if (newurls.size()>0)
			siteService.DBCommit();
		
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
		return null;
	}

}
