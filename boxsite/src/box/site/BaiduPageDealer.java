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
	static String BAIDU_URL = "http://www.baidu.com/s?";
	
	public BaiduPageDealer(){
		siteGetter.setSiteId(siteId);
		siteGetter.start();
	}
	
	@Override
	public List<PageRef> deal(OriHttpPage _page) {
		page = _page;
		siteGetter.pushPage(_page);
		List<PageRef> newurls = findPagingRefs(_page.getRefId());
		
		return newurls;
	}
	
	private List<PageRef> findPagingRefs(int parentWordid){
		List<PageRef> newurls = new ArrayList<PageRef>();
		SiteService siteService = new SiteService();
		htmlHelper.init(page.getContent());
		List<PageRef> refs = htmlHelper.getUrlsByLinkKey(BAIDU_URL);
		for (PageRef ref:refs){
				int wordid = siteService.addWord(ref.getRefWord());
				siteService.addWordRelation(parentWordid, wordid, 1);
				//自动分页:
				for (int i=0;i<20;i++){
					String urlstr = BAIDU_URL+"wd="+ref.getRefWord()+"&pn="+i*10;
					if (siteService.addSearchUrl(urlstr,wordid)){
						PageRef url = new PageRef(urlstr,ref.getRefWord());
						url.setRefId(wordid);
						newurls.add(url);
					}
					
				}
		}
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
		SiteService service = new SiteService();
		
		//找已有未完成URL:
		List<Baiduurls> newurls = service.getNewBaiduurls();
		for (Baiduurls url:newurls){
			PageRef ref = new PageRef(url.getUrl(),url.getWord());
			ref.setRefId(url.getWordid());
			urls.add(ref);	
		}
		
		//新组装baidu urls:
		List<Websitewords>  words = service.getNewwords();
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
					String url = BAIDU_URL+"wd="+wordstr;
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
