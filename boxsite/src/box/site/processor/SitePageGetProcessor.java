package box.site.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;
import box.site.model.WebUrl;
import cn.hd.util.FileUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import easyshop.html.HTMLInfoSupplier;
import es.download.flow.DownloadContext;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

public class SitePageGetProcessor implements PageProcessor{
	protected Logger  log = Logger.getLogger(getClass());
	int queryCount;
	public String startUrl;
	String domainName;
	private String pagesPath;
	public Set<String> notDownloadurls;
	public Set<String> doneDownloadurls;
	public Set<String> allDownloadUrls;
	private Set<String> urlRegs = new HashSet<String>();
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
		
		urlRegs = new HashSet<String>();
		String weburlpath = "data/pages/" + domainName+"_urls.json";
		String weburlc = FileUtil.readFile(weburlpath);
		if (weburlc!=null&&weburlc.trim().length()>0){
			Map<String,JSONObject> jsonstr = (Map<String,JSONObject>)JSON.parseObject(weburlc, HashMap.class);
			for (String url:jsonstr.keySet()){
				JSONObject jsonobj = jsonstr.get(url);
				WebUrl item = (WebUrl)JSON.parseObject(jsonobj.toJSONString(),WebUrl.class);
				if (item.getCat()>0){
					String regUrl = URLStrHelper.getUrlRex(item.getUrl());
					urlRegs.add(regUrl);
				}
			}
		}
		
		
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
		
//		url = "http://book.51cto.com/art/200909/149969.htm";
//		String rr = "http://book.51cto.com/art/[0-9]+/[0-9]+.htm";
//		boolean b = url.matches(rr);
//		String reg = URLStrHelper.getUrlRex(url);
//		System.out.println(reg);
		
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
		List<PageRef> refs = new ArrayList<PageRef>();
		
		Elements els = page.getHtml().getDocument().getElementsByTag("a");
		for (Element e:els){
			String text = e.text();
			if (text==null||text.trim().length()<=0) continue;
			String link = e.attr("href");
			
//			log.warn("link "+link+",text:"+text);
			
			WebUrl url = new WebUrl();
			url.setText(text);
			url.setUrl(link);
			urls.put(url.getUrl(),url);
			
			PageRef ref = new PageRef(url.getUrl(),text);
			refs.add(ref);
		}
		
		Map<String,Vector<PageRef>> maprefs = HTMLInfoSupplier.findSortUrls(refs);
		log.warn(maprefs.toString());
		
		return urls;
	}
	
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
