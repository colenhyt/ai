package box.site;

import java.util.ArrayList;
import java.util.List;

import box.site.db.SiteService;
import box.site.model.Baiduurls;
import box.site.model.Websitewords;
import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import es.webref.model.PageRef;

public class BaiduPageDealer implements IPageDealer {
	private static final String siteId = "baidu";
	private OriHttpPage page;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	SiteContentGetter siteGetter = new SiteContentGetter();
	
	public BaiduPageDealer(){
		siteGetter.setSiteId(siteId);
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
		SiteService siteService = new SiteService();
		htmlHelper.init(page.getContent());
		String[] pagingContent = htmlHelper.getDivsByClassValue("xdGcVm UWeZEg Hxgnfk UQfpJC EZLYns");
		for (int i=0;i<pagingContent.length;i++){
			String pc = pagingContent[i];
			htmlHelper.init(pc.getBytes());
			String[] divs = htmlHelper.getDivsByClassValue("f13");
			for (int j=0;j<divs.length;j++){
				htmlHelper.init(divs[j].getBytes());
				String div = htmlHelper.getBlockByOneProp("span", "class", "g");
				div += "a";
				String url = div;
				int wordid = page.getRefId();
				if (siteService.addSearchUrl(url,wordid)){
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
		List<PageRef> urls = new ArrayList<PageRef>();
		String key = "http://www.baidu.com/s?wd=";
		SiteService service = new SiteService();
		List<Websitewords>  words = service.getNewwords();
		List<Baiduurls> newurls = service.getNewBaiduurls();
		for (Baiduurls url:newurls){
			PageRef ref = new PageRef(url.getUrl(),url.getWord());
			ref.setRefId(url.getWordid());
			urls.add(ref);	
		}
		
		if (urls.size()<=0){
			for (Websitewords item:words){
				String[] warray = item.getWord().split(",");
				String wordstr = "";
				for (int i=0;i<warray.length;i++){
					if (i>0)
						wordstr += "%20";
					wordstr += warray[i];
				}
				for (int j=0;j<20;j++){
					String url = key+wordstr;
					if (j>0)
						url += "&pn="+j*10;
					PageRef ref = new PageRef(url,item.getWord());
					ref.setRefId(item.getWordid());
					urls.add(ref);		
					service.addSearchUrl(url, item.getWordid());
				}
			}
			service.DBCommit();
		}
		return urls;
	}

}
