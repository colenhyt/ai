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
import box.db.Wxpublic;
import box.site.getter.WXPublicArticleGetter;
import box.site.parser.sites.MultiPageTask;
import box.weixin.SougouPageDealer;

import com.alibaba.fastjson.JSON;

import easyshop.html.HTMLInfoSupplier;
import es.download.flow.DownloadContext;
import es.util.FileUtil;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;

//爬取搜狗网站的微信公众号文章:
public class SogouWXPageGetProcessor implements PageProcessor{
	public final static int SOGOU_URL_TYPE_LIST = 0;	//搜索结果页
	public final static int SOGOU_URL_TYPE_PUBLIC = 1;	//公众号首页
	public final static int SOGOU_URL_TYPE_ARTICLE = 2; //公众号文章页
	protected Logger  log = Logger.getLogger(getClass());
	private SougouPageDealer sogouDealer = new SougouPageDealer();
	private MultiPageTask mainThread;
	int queryCount;
	public String startUrl;
	String sitekey;
	private String pagesPath;
	public Set<String> notDownloadurlsSet;
	public Set<String> doneDownloadurlSet;
	public Set<String> allDownloadUrlSet;
	private List<String> pbs = new ArrayList<String>();
	private List<String> sogouPbSearchUrls = new ArrayList<String>();
	HTMLInfoSupplier infoSupp = new HTMLInfoSupplier();
	String urlPath;
	private Site site;
	private int CURRENT_DOWNLOAD_COUNT = 50;		//当次下载数量限制:
	private int currCount = 0;		//当前下载数量
	int maxpagecount;
	private WXPublicArticleGetter articleGetter = new WXPublicArticleGetter();
	
	public SogouWXPageGetProcessor(MultiPageTask task,int _maxCount){
		mainThread = task;
		
		currCount = 0;
		
		maxpagecount = -1;
		if (_maxCount>0)
			CURRENT_DOWNLOAD_COUNT = _maxCount;
		
		startUrl = "http://weixin.sogou.com";
		
		sitekey = URLStrHelper.getHost(startUrl).toLowerCase();
		
		pagesPath = "data/pbs/"+sitekey;
		
		String content = FileUtil.readFile("data/source.pbs");
		pbs = (List<String>)JSON.parseObject(content,ArrayList.class);
		startUrl = "http://weixin.sogou.com/weixin?type=1&ie=utf8&query="+pbs.get(0);
		for (int i=1;i<pbs.size();i++){
			String url = "http://weixin.sogou.com/weixin?type=1&ie=utf8&query="+pbs.get(i);
			sogouPbSearchUrls.add(url);
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
				notDownloadurlsSet.add(url);
			}
		}
		
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
		String url = "http://tech.163.com/16/0622/02/BQ4NNB9600097U7T.html";
		
		String reg = "http://tech.163.com/[0-9]+/[0-9]+/[0-9]+/[a-zA-Z0-9]+.html";
		boolean bb = url.matches(reg);
		System.out.println(bb);
		
		SogouWXPageGetProcessor process = new SogouWXPageGetProcessor(null,20);
		String content = FileUtil.readFile("data/pbs/sogou.com/.html");
		Page page = new Page();
		page.setRawText(content);
//		process.processPublic(page);
		Spider.create(process).run();
		
	}

	public int sogouType(String url){
		if (url.indexOf("sogou.com/weixin?")>0&&url.indexOf("type=1")>0)
			return SOGOU_URL_TYPE_LIST;
		else if (url.indexOf("weixin.qq.com/profile?")>0)
			return SOGOU_URL_TYPE_PUBLIC;
		else if (url.indexOf("weixin.qq.com/s?")>0)
			return SOGOU_URL_TYPE_ARTICLE;
		
		return -1;
	}
	
	//根据url获取公众号首页
	public void processSearch(Page page){
		//剩余的塞进去
		if (sogouPbSearchUrls.size()>1){
			page.addTargetRequests(sogouPbSearchUrls);
			sogouPbSearchUrls.clear();
		}
		String wxhao = URLStrHelper.getParamValue(page.getRequest().getUrl(), "query");
		List<Wxpublic> wps = sogouDealer.findWxPublics(page.getRawText().getBytes());
		for (Wxpublic pb:wps){
			if (pb.getWxhao().equalsIgnoreCase(wxhao)){
				page.addTargetRequest(pb.getSogouUrl());
			}
		}
		
	}
	
	//获取公众号最新文章url:
	public void processPublic(Page page){
		
	}
	
	//保存文章页面文件:
	public void processArticle(Page page,String charset){
		//得到公众号:
		boolean parsed =articleGetter.parseItem(page.getRequest().getUrl(), page.getRawText());
		
		//保存网页:
		String fileName = page.getRequest().getUrl().hashCode()+".html";
		String pagePath = pagesPath +"/"+fileName;
		FileUtil.writeFile(pagePath, page.getRawText(),charset);		
	}
	
	@Override
	public void process(Page page) {
		currCount++;
		
		doneDownloadurlSet.add(page.getRequest().getUrl());
		String pageContent = page.getRawText();
		String charset = page.getCharset();
		if (page.getCharset().equalsIgnoreCase("gbk")||page.getCharset().equalsIgnoreCase("gb2312")){
			pageContent = StringHelper.gbk2utf8(page.getRawText());
			charset = "utf-8";
		}
		 
		String fileName = page.getRequest().getUrl().hashCode()+".html";
		String pagePath = pagesPath +"/"+fileName;
		FileUtil.writeFile(pagePath, page.getRawText(),charset);	
		
		int type = sogouType(page.getRequest().getUrl());
		switch (type){
		case SOGOU_URL_TYPE_LIST:		//获取公众号
			processSearch(page);
			break;
		case SOGOU_URL_TYPE_PUBLIC:
			processPublic(page);
			break;
		case SOGOU_URL_TYPE_ARTICLE:
			processArticle(page,charset);
			break;
		}

		try {
			Thread.sleep(1500);
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
