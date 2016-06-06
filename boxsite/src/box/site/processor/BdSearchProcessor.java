package box.site.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import box.mgr.SiteManager;
import box.site.SiteContentGetter;
import box.site.model.Website;
import cn.hd.util.StringUtil;
import es.download.flow.DownloadContext;
import es.util.FileUtil;
import es.util.url.URLStrHelper;

public class BdSearchProcessor implements PageProcessor{
	static String BAIDU_URL = "https://www.baidu.com/s?";
	protected Logger  log = Logger.getLogger(getClass());
	private String startUrl;
	private SiteContentGetter contentGetter;
	String domainName;
	String keyWord;
	String path = "data/baidu/";
	Set<String>		searchUrls;
	Set<String>		doneUrls;
	Set<String>		relateWords;
	private Site site;

	public void init(String _word){
		
		keyWord = _word;
		contentGetter = new SiteContentGetter();
		
		SiteManager.getInstance();
		
		searchUrls = new HashSet<String>();
		doneUrls = new HashSet<String>();
		relateWords = new HashSet<String>();
		
		String searUrls = FileUtil.readFile(path+keyWord+".json");
		if (searUrls==null||searUrls.trim().length()<=0){
			startUrl = BAIDU_URL+"wd="+keyWord;
		}else {
			StringUtil.json2Set(searUrls, searchUrls,String.class);
			String relates = FileUtil.readFile(path+keyWord+"_relate.json");
			if (relates!=null&&relates.trim().length()>0){
				StringUtil.json2Set(relates, relateWords,String.class);
			}
			
			String jsonStr = FileUtil.readFile(path+keyWord+"_done.json");
			if (jsonStr!=null&&jsonStr.trim().length()>0){
				StringUtil.json2Set(jsonStr, doneUrls,String.class);
			}
			
			for (String url:searchUrls){
				if (!doneUrls.contains(url)){
					startUrl = url;
					break;
				}
			}
		}
		
		domainName = URLStrHelper.getHost(startUrl).toLowerCase();
		site = new Site();
		site.setCharset("utf-8");
		String userAgent = DownloadContext.getSpiderContext().getUserAgent();
		site.addHeader("User-Agent", userAgent);
		site.addStartUrl(startUrl);
	}
	
	public static void main(String[] args) {
		String word = "架构";
		BdSearchProcessor p = new BdSearchProcessor();
		p.init(word);
		HttpClient httpClient = HttpClients.createDefault();
//		PostPageGetter gg = new PostPageGetter(DownloadContext.getSpiderContext().getUserAgent());
//		HttpPage page = gg.getHttpPage("https://www.baidu.com/s?wd="+word, httpClient);
//			System.out.println(page.getContent().length);
       Spider.create(p).addPipeline(new BdSearchPipeline()).run();

	}

	@Override
	public void process(Page page) {
		//1. find sites:
		Set<String> sites = new HashSet<String>();
		Vector<Website> sitesVec = contentGetter.findWebSitesInPage(page.getRawText().getBytes(), null,false);
		
		Set<Website> newSites = SiteManager.getInstance().addSites(sitesVec);
		for (Website site:newSites){
			contentGetter.fillSiteInfo(site);
			sites.add(site.toString());
		}
		
		//2. find paging urls:
		String regx = ".*s.*wd=.*&pn=.*";
		List<String> pagingurls = page.getHtml().links().regex(regx).all();
		if (pagingurls.size()<=0){
			log.warn("could not find paging urls ");
			return;
		}
		
		Set<String> nexturls = new HashSet<String>();
		for (String url:pagingurls){
			String uniStr = URLStrHelper.toUtf8String(keyWord);
			if (url.indexOf(keyWord)<=0&&url.indexOf(uniStr)<=0)continue;
			if (searchUrls.contains(url)||doneUrls.contains(url)) continue;
			nexturls.add(url);
		}
		List<String> requests = new ArrayList<String>();
		requests.addAll(nexturls);
		page.addTargetRequests(requests);
		
		//3. find related words:
		List<String> r2 = page.getHtml().xpath("//div[@id=rs").links().all();
		for (String str:r2){
			String hexwstr = URLStrHelper.getParamValue(str, "wd");
			String wstr = URLStrHelper.unescape(hexwstr);
			relateWords.add(wstr);
		}
		
		searchUrls.addAll(nexturls);
		doneUrls.add(page.getRequest().getUrl());
		searchUrls.remove(page.getRequest().getUrl());
		
		page.putField("sites", sites);
		page.putField("relateWords", relateWords);
		page.putField("searchWord", keyWord);
		page.putField("searchUrls", searchUrls);
		page.putField("doneUrls", doneUrls);
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
