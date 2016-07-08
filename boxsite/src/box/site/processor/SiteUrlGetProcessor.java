package box.site.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;
import box.mgr.ProcessManager;
import box.site.model.WebUrl;
import box.site.parser.sites.MultiPageTask;
import cn.hd.util.FileUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.download.flow.DownloadContext;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

public class SiteUrlGetProcessor implements PageProcessor{
	protected Logger  log = Logger.getLogger(getClass());
	public String startUrl;
	String domainName;
	public Set<String> doneDownloadurlSet;
	private Site site;
	private int CURRENT_DOWNLOAD_COUNT = 50;		//当次下载数量限制:
	private Spider spider;
	int maxpagecount;
	
	public SiteUrlGetProcessor(String _startUrl,int _maxCount){
		maxpagecount = -1;
		if (_maxCount>0)
			CURRENT_DOWNLOAD_COUNT = _maxCount;
		
		startUrl = _startUrl;
		domainName = URLStrHelper.getHost(startUrl).toLowerCase();
	
		doneDownloadurlSet = new HashSet<String>();
		
		site = new Site();
		String userAgent = DownloadContext.getSpiderContext().getUserAgent();
		site.addHeader("User-Agent", userAgent);
		Set<Integer> codes = new HashSet<Integer>();
		codes.add(200);
		codes.add(404);
		site.setAcceptStatCode(codes);
		site.addStartUrl(startUrl);		
	}
	
	public void setSpider(Spider _spider){
		spider = _spider;
	}
	
	public static void main(String[] args) {
		String url = "http://tech.163.com/16/0622/02/BQ4NNB9600097U7T.html";
		
		String reg = "http://tech.163.com/[0-9]+/[0-9]+/[0-9]+/[a-zA-Z0-9]+.html";
		boolean bb = url.matches(reg);
		System.out.println(bb);
	}

	@Override
	public void process(Page page) {
		
		doneDownloadurlSet.add(page.getRequest().getUrl());
		String pageContent = page.getRawText();
		String charset = page.getCharset();
		if (page.getCharset().equalsIgnoreCase("gbk")||page.getCharset().equalsIgnoreCase("gb2312")){
			pageContent = StringHelper.gbk2utf8(page.getRawText());
			charset = "utf-8";
		}
		 
		if (CURRENT_DOWNLOAD_COUNT>0&&doneDownloadurlSet.size()>=CURRENT_DOWNLOAD_COUNT){
			log.warn("urls获取完成，当次获取数: "+doneDownloadurlSet.size());
			spider.stop();
			return;			
		}
		
		List<String> requests = new ArrayList<String>();
		//找页内urls:
		 List<String> links = page.getHtml().links().all();
		 Set<String> newurls = new HashSet<String>();
		 for (String url:links){
			 if (url.toLowerCase().indexOf(domainName)<0) continue;
			 if (doneDownloadurlSet.contains(url)) continue;
			 
			 newurls.add(url);
		 }
		 requests.addAll(newurls);
		
		page.addTargetRequests(requests);	
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}