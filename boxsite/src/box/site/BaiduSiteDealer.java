package box.site;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import box.site.db.SiteService;
import box.site.model.Baiduurls;
import box.site.model.Websitewords;
import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import es.download.flow.DownloadContext;
import es.util.FileUtil;
import es.webref.model.PageRef;

public class BaiduSiteDealer implements IPageDealer {
	protected Logger  log = Logger.getLogger(getClass()); 

	private static final String siteId = "baidu";
	private OriHttpPage page;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	SiteContentGetter siteGetter;
	WebSiteInfoGetter infoGetter;
	static String BAIDU_URL = "https://www.baidu.com/s?";
	static String BAIDU_URL0 = "http://www.baidu.com/s?";
	static String BAIDU_URL00 = "https://www.baidu.com/";
	
	public BaiduSiteDealer(){
		siteGetter = new SiteContentGetter();
		siteGetter.setSiteId(siteId);
	}
	
	@Override
	public List<PageRef> deal(OriHttpPage _page) {
		page = _page;
		List<PageRef> newurls = new ArrayList<PageRef>();
		log.debug("get page "+_page.getUrlStr());
		String filepath = "./pages/"+_page.getUrlStr().hashCode()+".html";
		String pageContent = new String(_page.getContent());
		if (pageContent.indexOf("百度快照")<0){
			log.warn("没有找到搜索内容");
			FileUtil.writeFile(filepath, pageContent);
			return newurls;
		}
		siteGetter.genWebSites(_page,true);
		SiteService service = new SiteService();
		int siteCount = service.getWebsiteCount(_page.getRefId());
		if (siteCount>50){
			log.warn("该关键字相关网站已有 ："+siteCount);
			return newurls;
		}		
		
		newurls.addAll(findPagingRefs(_page.getRefId()));
		
		return newurls;
	}
	
	private List<PageRef> findPagingRefs(int parentWordid){
		List<PageRef> newurls = new ArrayList<PageRef>();
		SiteService siteService = new SiteService();
		htmlHelper.init(page.getContent());
		String relatdiv = htmlHelper.getBlockByOneProp("div","id","rs");
		if (relatdiv==null)
			return newurls;
		
		htmlHelper.init(relatdiv.getBytes());
		List<PageRef> refs = htmlHelper.getUrls();
		for (PageRef ref:refs){
			if (ref.getRefWord()==null) continue;
			
//			String urlstr0 = ref.getUrlStr();
//			if (urlstr0.indexOf("s?wd")==0)
//				urlstr0 = BAIDU_URL00 + urlstr0;
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
		log.warn("get baidu urls:"+newurls.size());
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
		String keyword = "育儿,教育";
		SiteService service = new SiteService();
		int wordid = service.findWordId(keyword);
		//not done word:
		if (wordid>0){
			int siteCount = service.getWebsiteCount(wordid);
			if (siteCount>50){
				log.warn("该关键字相关网站已有 ："+siteCount);
				return urls;
			}
			
			List<Baiduurls> bdurls = service.getNotDoneSearchUrls(wordid);
			for (Baiduurls item:bdurls){
				PageRef ref = new PageRef(item.getUrl(),keyword);
				ref.setRefId(wordid);
				urls.add(ref);		
			}
		}
		
		if (urls.size()<=0) {
			wordid = service.addWord(keyword);
			service.DBCommit();
			String[] warray = keyword.split(",");
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
				PageRef ref = new PageRef(url,keyword);
				ref.setRefId(wordid);
				urls.add(ref);		
				service.addSearchUrl(url, wordid);
			}						
		}

		return urls;
	}

}
