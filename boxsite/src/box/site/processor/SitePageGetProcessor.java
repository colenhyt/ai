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
import box.site.getter.ISiteContentGetter;
import box.site.getter.SiteContentGetterFactory;
import box.site.model.WebUrl;
import box.site.parser.sites.MultiPageTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import easyshop.html.HTMLInfoSupplier;
import es.util.FileUtil;
import es.util.SpiderConfigProxy;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

public class SitePageGetProcessor implements PageProcessor{
	protected Logger  log = Logger.getLogger(getClass());
	private MultiPageTask mainThread;
	int queryCount;
	public String startUrl;
	String sitekey;
	private String pagesPath;
	public Set<String> notDownloadurlsSet;
	public Set<String> doneDownloadurlSet;
	public Set<String> allDownloadUrlSet;
	private Set<String> urlRegs = new HashSet<String>();
	private Map<String,WebUrl> webitemMap = new HashMap<String,WebUrl>();
	HTMLInfoSupplier infoSupp = new HTMLInfoSupplier();
	String urlPath;
	private Site site;
	private int CURRENT_DOWNLOAD_COUNT = 50;		//当次下载数量限制:
	private int currCount = 0;		//当前下载数量
	int maxpagecount;
	private ISiteContentGetter urlsGetter;
	
	public SitePageGetProcessor(MultiPageTask task,String _startUrl,int _maxCount){
		mainThread = task;
		
		currCount = 0;
		
		maxpagecount = -1;
		if (_maxCount>0)
			CURRENT_DOWNLOAD_COUNT = _maxCount;
		
		startUrl = _startUrl;
		sitekey = URLStrHelper.getHost(startUrl).toLowerCase();
		
		SiteContentGetterFactory getterFac = new SiteContentGetterFactory();
		
		urlsGetter = getterFac.createGetter(sitekey);
		
		pagesPath = "data/pages/"+sitekey;
		
			
		List<File> files = FileUtil.getFiles(pagesPath);
		queryCount = files.size();
		
		urlRegs = new HashSet<String>();
		String regpath = "dna/" + sitekey+".json";
		String regc = FileUtil.readFile(regpath);
		if (regc!=null&&regc.trim().length()>0){
			List<String> regs = (List<String>)JSON.parse(regc);
			urlRegs.addAll(regs);
		}
		
		String weburlpath = "data/pages/" + sitekey+"_urls.json";
		String weburlc = FileUtil.readFile(weburlpath);
		if (weburlc!=null&&weburlc.trim().length()>0){
			Map<String,JSONObject> jsonstr = (Map<String,JSONObject>)JSON.parseObject(weburlc, HashMap.class);
			for (String url:jsonstr.keySet()){
				JSONObject jsonobj = jsonstr.get(url);
				WebUrl item = (WebUrl)JSON.parseObject(jsonobj.toJSONString(),WebUrl.class);
				if (item.getCat()>0){
					String regUrl = URLStrHelper.getUrlRex(item.getUrl());
					urlRegs.add(regUrl);
					webitemMap.put(item.getUrl(), item);
				}
			}
		}
		
		allDownloadUrlSet = new HashSet<String>();
		notDownloadurlsSet = new HashSet<String>();
		doneDownloadurlSet = new HashSet<String>();
		String doneContent = FileUtil.readFile(pagesPath+"_done_urls.json");
		if (doneContent!=null&&doneContent.trim().length()>0){
			List<String> doneUrls = (List<String>)JSON.parse(doneContent);
			doneDownloadurlSet.addAll(doneUrls);
		}
		
		
		urlPath = pagesPath+".urls";
		String urlcontent = FileUtil.readFile(urlPath);
		if (urlcontent!=null&&urlcontent.trim().length()>0){
			List<String> urls = (List<String>)JSON.parse(urlcontent);
			allDownloadUrlSet.addAll(urls);
			for (String url:urls){
				if (doneDownloadurlSet.contains(url)) continue;
				
				String fileName = url.hashCode()+".html";
				File f = new File(pagesPath+"/"+fileName);
				if (f.exists()) {
					doneDownloadurlSet.add(url);
					continue;
				}
				//优先下载已分类url:
//				if (webitemMap.containsKey(url)){
//					WebUrl item = webitemMap.get(url);
//					if (item.getCat()>0)
//						notDownloadurls.add(url);
//				}
				notDownloadurlsSet.add(url);
			}
		}
//		if (notDownloadurls.size()>0){
//			Object[] urlstrs = notDownloadurls.toArray();
//			startUrl = (String)urlstrs[0];
//		}
		allDownloadUrlSet.add(startUrl);
		
		site = new Site();
		String userAgent = SpiderConfigProxy.getSpiderContext().getUserAgent();
		site.addHeader("User-Agent", userAgent);
		Set<Integer> codes = new HashSet<Integer>();
		codes.add(200);
		codes.add(404);
		site.setAcceptStatCode(codes);
		site.addStartUrl(startUrl);		
	}
	
	public static void main(String[] args) {
		String url = "http://tech.163.com/16/0622/02/BQ4NNB9600097U7T.html";
		
		String reg = "http://tech.163.com/[0-9]+/[0-9]+/[0-9]+/[a-zA-Z0-9]+.html";
		boolean bb = url.matches(reg);
		System.out.println(bb);
		
		url = "http://tech.163.com/";
		SitePageGetProcessor process = new SitePageGetProcessor(null,url,20);
		Spider.create(process).addPipeline(new SiteURLsPipeline()).run();
		
		//String content = FileUtil.readFile("data/pages/36kr.com/1322533216.html");
		
	}

	@Override
	public void process(Page page) {
		currCount++;
		
		page.putField("MaxPageCount", maxpagecount);
		
		doneDownloadurlSet.add(page.getRequest().getUrl());
		String pageContent = page.getRawText();
		String charset = page.getCharset();
		if (page.getCharset().equalsIgnoreCase("gbk")||page.getCharset().equalsIgnoreCase("gb2312")){
			pageContent = StringHelper.gbk2utf8(page.getRawText());
			charset = "utf-8";
		}
		 
		//保存网页:
		if (!page.getRequest().getUrl().equalsIgnoreCase(startUrl)){
			String fileName = page.getRequest().getUrl().hashCode()+".html";
			String pagePath = pagesPath +"/"+fileName;
			FileUtil.writeFile(pagePath, pageContent,charset);			
		}
		
		if (CURRENT_DOWNLOAD_COUNT>0&&currCount>=CURRENT_DOWNLOAD_COUNT){
			log.warn("页面下载完成，当次下载页面数: "+currCount);
			if (mainThread!=null)
				mainThread.finishCallback(sitekey);
			else
				System.exit(0);
			return;			
		}
		
		if (maxpagecount>0){
			List<File> files = FileUtil.getFiles(pagesPath);
			queryCount = files.size();
			if (queryCount>=maxpagecount){
				log.warn("页面下载完成，总共有页面 "+queryCount);
				mainThread.finishCallback(sitekey);
				return;
			}
		}
		
		List<String> requests = new ArrayList<String>();
		List<String> links = urlsGetter.findItemUrls(page);
		 Set<String> newurls = new HashSet<String>();
		 for (String url:links){
			 if (url.toLowerCase().indexOf(sitekey)<0) continue;
			 if (doneDownloadurlSet.contains(url)) continue;
			 if (allDownloadUrlSet.contains(url)) continue;
			 newurls.add(url);
		 }	
		 allDownloadUrlSet.addAll(newurls);
		 requests.addAll(newurls);
		 queryCount += newurls.size();
		
		if (notDownloadurlsSet.size()>0){
			requests.addAll(notDownloadurlsSet);
			queryCount += notDownloadurlsSet.size();
			notDownloadurlsSet.clear();
		}
		
		//找到合法url,并塞入下载链接:
//		if (queryCount<maxpagecount){
//			//找页内urls:
//			 List<String> links = page.getHtml().links().all();
//			 Set<String> newurls = new HashSet<String>();
//			 for (String url:links){
//				 if (url.toLowerCase().indexOf(domainName)<0) continue;
//				 if (allDownloadUrls.contains(url)) continue;
//				 
//				 newurls.add(url);
//			 }
//			 allDownloadUrls.addAll(newurls);
//			 requests.addAll(newurls);
//			 queryCount += newurls.size();
//		}
		
		 FileUtil.writeFile(urlPath, JSON.toJSONString(allDownloadUrlSet));
		 
		page.addTargetRequests(requests);	
		
		//取urls:
		Map<String,WebUrl> urls = getUrls(page);
		
		
		page.putField("Charset", charset);
		page.putField("PageUrls", urls);
		page.putField("PageContent", pageContent);
		page.putField("Url", page.getRequest().getUrl());
		page.putField("DomainName", sitekey);
	//	log.warn("get page "+urls.size()+",pageCount:"+queryCount);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
	}

	private List<String> _findRegUrls(String _sitekey,Page page,String regUrl){
		List<String> links = null;
		if (_sitekey.indexOf("36kr.com")>0){
			int start = page.getRawText().indexOf("props=");
			int end = page.getRawText().indexOf("</script>", start);
			String listContent = page.getRawText().substring(start+"props=".length(),end);
			
		}else
			links = page.getHtml().links().regex(regUrl).all();
		return links;
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
	
	private boolean isReg(String url){
		if (urlRegs.size()<=0)
			return true;
		
		for (String reg:urlRegs){
			if (url.matches(reg))
			 return true;
		}
		return false;
	}
	public Map<String,WebUrl> getUrls(Page page){
		Map<String,WebUrl> urls = new HashMap<String,WebUrl>();
		List<PageRef> refs = new ArrayList<PageRef>();
		
		Elements els = page.getHtml().getDocument().getElementsByTag("a");
		for (Element e:els){
			String link = e.attr("href");
			if (link.toLowerCase().indexOf(sitekey)<0) continue;
			if (!isReg(link)) continue;
			String text = e.text();
			if (text==null||text.trim().length()<=0) continue;
			
//			log.warn("link "+link+",text:"+text);
			
			WebUrl url = new WebUrl();
			url.setText(text);
			url.setUrl(link);
			urls.put(url.getUrl(),url);
			
//			PageRef ref = new PageRef(url.getUrl(),text);
//			refs.add(ref);
		}
		
//		Map<String,Vector<PageRef>> maprefs = HTMLInfoSupplier.findSortUrls(refs);
//		log.warn(maprefs.toString());
		
		return urls;
	}
	
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
