package box.site.processor;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
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

public class PagesGetProcessor implements PageProcessor{
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
	
	public PagesGetProcessor(MultiPageTask task,String _startUrl,int _maxCount){
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
		PagesGetProcessor process = new PagesGetProcessor(null,url,20);
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
		String fileName = page.getRequest().getUrl().hashCode()+".html";
		String pagePath = pagesPath +"/"+fileName;
		FileUtil.writeFile(pagePath, pageContent,charset);
		
		 
//		page.addTargetRequests(requests);	
		
		
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
	
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
