package box.site.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;
import box.site.model.WebUrl;
import cn.hd.util.FileUtil;

import com.alibaba.fastjson.JSON;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

import easyshop.html.HTMLInfoSupplier;
import es.download.flow.DownloadContext;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

public class SitePageGetProcessor implements PageProcessor{
	protected Logger  log = Logger.getLogger(getClass());
    private HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	int queryCount;
	public String startUrl;
	String domainName;
	private String pagesPath;
	public Set<String> notDownloadurls;
	public Set<String> doneDownloadurls;
	public Set<String> allDownloadUrls;
	String urlPath;
	private Site site;
	int maxpagecount;
	
	public SitePageGetProcessor(String _startUrl,int _maxCount){
		maxpagecount = 10;
		if (_maxCount>0)
			maxpagecount = _maxCount;
		
		startUrl = _startUrl;
		domainName = URLStrHelper.getHost(startUrl).toLowerCase();
		
		pagesPath = "data/pages/"+domainName;
		
		List<File> files = FileUtil.getFiles(pagesPath);
		queryCount = files.size();
		
		allDownloadUrls = new HashSet<String>();
		notDownloadurls = new HashSet<String>();
		doneDownloadurls = new HashSet<String>();
		urlPath = pagesPath+".urls";
		String urlcontent = FileUtil.readFile(urlPath);
		if (urlcontent!=null&&urlcontent.trim().length()>0){
			List<String> urls = (List<String>)JSON.parse(urlcontent);
			allDownloadUrls.addAll(urls);
			for (String url:urls){
				String fileName = url.hashCode()+".html";
				File f = new File(pagesPath+"/"+fileName);
				if (f.exists()) {
					doneDownloadurls.add(url);
					continue;
				}
				notDownloadurls.add(url);
			}
		}
		if (notDownloadurls.size()>0){
			Object[] urlstrs = notDownloadurls.toArray();
			startUrl = (String)urlstrs[0];
		}
		allDownloadUrls.add(startUrl);
		
		site = new Site();
		String userAgent = DownloadContext.getSpiderContext().getUserAgent();
		site.addHeader("User-Agent", userAgent);
		Set<Integer> codes = new HashSet<Integer>();
		codes.add(200);
		codes.add(404);
		site.setAcceptStatCode(codes);
		site.addStartUrl(startUrl);		
	}
	
	public static void main(String[] args) {
		String url = "http://developer.51cto.com";
		
		Set<String> sites = new HashSet<String>();
		sites.add(url);
		
		for (String site:sites){
			SitePageGetProcessor p1 = new SitePageGetProcessor(site,20);
	        Spider.create(p1).addPipeline(new SiteURLsPipeline()).run();
		}
		
	}

	@Override
	public void process(Page page) {
		
		page.putField("MaxPageCount", maxpagecount);
		
		doneDownloadurls.add(page.getRequest().getUrl());
		
		//保存网页:
		String fileName = page.getRequest().getUrl().hashCode()+".html";
		String pagePath = pagesPath +"/"+fileName;
		FileUtil.writeFile(pagePath, page.getRawText(),page.getCharset());
		
		List<File> files = FileUtil.getFiles(pagesPath);
		queryCount = files.size();
		if (queryCount>=maxpagecount){
			log.warn("页面下载完成，总共有页面 "+queryCount);
			System.exit(0);
		}
		
		List<String> requests = new ArrayList<String>();
		if (notDownloadurls.size()>0){
			requests.addAll(notDownloadurls);
			queryCount += notDownloadurls.size();
			notDownloadurls.clear();
		}
		
		//找到合法url,并塞入下载链接:
		if (queryCount<maxpagecount){
			//找页内urls:
			 List<String> links = page.getHtml().links().all();
			 Set<String> newurls = new HashSet<String>();
			 for (String url:links){
				 if (url.toLowerCase().indexOf(domainName)<0) continue;
				 if (allDownloadUrls.contains(url)) continue;
				 
				 newurls.add(url);
			 }
			 allDownloadUrls.addAll(newurls);
			 requests.addAll(newurls);
			 queryCount += newurls.size();
			 FileUtil.writeFile(urlPath, JSON.toJSONString(allDownloadUrls));
		}
		
		page.addTargetRequests(requests);	
		
		//取urls:
		Map<String,WebUrl> urls = getUrls(page);
		
		
		page.putField("PageUrls", urls);
		page.putField("DomainName", domainName);
		log.warn("get page "+urls.size()+",pageCount:"+queryCount);
//		
	}

	public void parseFromPage(String urlstr,String charset){
		String fileName = urlstr.hashCode()+".html";
		String pagePath = pagesPath +"/"+fileName;	
		String content = FileUtil.readFile(pagePath, charset);
		Request request = new Request(urlstr);
		Page page = new Page();
        page.setRawText(content);
        page.setRequest(request);
        page.setUrl(new PlainText(urlstr));
	}
	
	public Map<String,WebUrl> getUrls(Page page){
		Map<String,WebUrl> urls = new HashMap<String,WebUrl>();
		htmlHelper.init(page.getRequest().getUrl(),page.getRawText().getBytes(), page.getCharset());
		List<PageRef> refs = htmlHelper.getUrls(domainName);
		
		
		for (PageRef ref:refs){
			WebUrl url = new WebUrl();
			url.setText(ref.getRefWord());
			url.setUrl(ref.getUrlStr());
			urls.put(url.getUrl(),url);
		}
		return urls;
	}
	
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
