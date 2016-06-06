package box.site.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import box.mgr.SiteManager;
import cn.hd.util.StringUtil;
import es.download.flow.DownloadContext;
import es.util.FileUtil;
import es.util.url.URLStrHelper;

public class ListSearchProcessor implements PageProcessor{
	protected Logger  log = Logger.getLogger(getClass());
	private String startUrl;
	private IItemFinder searchDoer;
	String domainName;
	String siteKey;
	String keyWord;
	String path = "data/list/";
	Set<String>		searchUrls;
	Set<String>		doneUrls;
	Set<String>		relateWords;
	private Site site;

	public void init(String _siteKey,String _word){
		
		siteKey = _siteKey;
		path += siteKey +"/";
		File folder = new File(path);
		if (!folder.exists())
			folder.mkdir();
		
		if (siteKey.equals("sogou"))
			searchDoer = new SogouWXPublicFinder();
		else if (siteKey.equals("baidu"))
			searchDoer = new BdSiteFinder();
		
		keyWord = _word;
		
		SiteManager.getInstance();
		
		searchUrls = new HashSet<String>();
		doneUrls = new HashSet<String>();
		relateWords = new HashSet<String>();
		
		String searUrls = FileUtil.readFile(path+keyWord+".json");
		if (searUrls!=null&&searUrls.trim().length()>0){
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
		
		if (startUrl==null){
			startUrl = searchDoer.getStartUrl(_word);	
			if (doneUrls.contains(startUrl))
				startUrl = null;
		}
		
		if (startUrl==null){
			log.warn("no url need to download");
			System.exit(0);
		}
		
		domainName = URLStrHelper.getHost(startUrl).toLowerCase();
		site = new Site();
		site.setCharset("utf-8");
		String userAgent = DownloadContext.getSpiderContext().getUserAgent();
		site.addHeader("User-Agent", userAgent);
		site.addStartUrl(startUrl);
	}
	
	public static void main(String[] args) {
		String word = "电影";
		ListSearchProcessor p = new ListSearchProcessor();
		p.init("sogou",word);
       Spider.create(p).addPipeline(new ListSearchPipeline()).run();

	}

	@Override
	public void process(Page page) {
		searchDoer.process(page);
		
		Set<String> newurls = new HashSet<String>();
		doneUrls.add(page.getRequest().getUrl());
		for (String url:searchUrls){
			if (!doneUrls.contains(url))
				newurls.add(url);
		}
		
		List<String> pagingurls = page.getResultItems().get("pagingurls");
		
		for (String url:pagingurls){
			String uniStr = URLStrHelper.toUtf8String(keyWord);
			if (url.indexOf(keyWord)<=0&&url.indexOf(uniStr)<=0)continue;
			if (searchUrls.contains(url)||doneUrls.contains(url)) continue;
			newurls.add(url);
		}
		List<String> requests = new ArrayList<String>();
		requests.addAll(newurls);
		page.addTargetRequests(requests);
		
		searchUrls.addAll(newurls);
		searchUrls.remove(page.getRequest().getUrl());
		
		page.putField("siteKey", siteKey);
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
