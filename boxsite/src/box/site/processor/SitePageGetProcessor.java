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
import box.site.model.WebUrl;
import cn.hd.util.FileUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.download.flow.DownloadContext;
import es.util.string.StringHelper;
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
	private Map<String,WebUrl> webitemMap = new HashMap<String,WebUrl>();
	String urlPath;
	private Site site;
	int maxpagecount;
	
	public SitePageGetProcessor(String _startUrl,int _maxCount){
		maxpagecount = 50;
		if (_maxCount>0)
			maxpagecount = _maxCount;
		
		startUrl = _startUrl;
		domainName = URLStrHelper.getHost(startUrl).toLowerCase();
		
		pagesPath = "data/pages/"+domainName;
		
			
		List<File> files = FileUtil.getFiles(pagesPath);
		queryCount = files.size();
		
		urlRegs = new HashSet<String>();
		String regpath = "dna/" + domainName+"_reg.json";
		String regc = FileUtil.readFile(regpath);
		if (regc!=null&&regc.trim().length()>0){
			List<String> regs = (List<String>)JSON.parse(regc);
			urlRegs.addAll(regs);
		}
		
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
					webitemMap.put(item.getUrl(), item);
				}
			}
		}
		
		allDownloadUrls = new HashSet<String>();
		notDownloadurls = new HashSet<String>();
		doneDownloadurls = new HashSet<String>();
		String doneContent = FileUtil.readFile(pagesPath+"_done_urls.json");
		if (doneContent!=null&&doneContent.trim().length()>0){
			List<String> doneUrls = (List<String>)JSON.parse(doneContent);
			doneDownloadurls.addAll(doneUrls);
		}
		
		
		urlPath = pagesPath+".urls";
		String urlcontent = FileUtil.readFile(urlPath);
		if (urlcontent!=null&&urlcontent.trim().length()>0){
			List<String> urls = (List<String>)JSON.parse(urlcontent);
			allDownloadUrls.addAll(urls);
			for (String url:urls){
				if (doneDownloadurls.contains(url)) continue;
				
				String fileName = url.hashCode()+".html";
				File f = new File(pagesPath+"/"+fileName);
				if (f.exists()) {
					doneDownloadurls.add(url);
					continue;
				}
				//优先下载已分类url:
				if (webitemMap.containsKey(url)){
					WebUrl item = webitemMap.get(url);
					if (item.getCat()>0)
						notDownloadurls.add(url);
				}
//				notDownloadurls.add(url);
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
		String url = "http://www.51cto.com/";
		
		Set<String> sites = new HashSet<String>();
		sites.add(url);
		
		url = "http://www.tmtpost.com/1822225.html";
		String rr = "http://www.tmtpost.com/[0-9]+.html.*";
		boolean b = url.matches(rr);
		String reg = URLStrHelper.getUrlRex(url);
		System.out.println(reg);
		
		for (String site:sites){
			SitePageGetProcessor p1 = new SitePageGetProcessor(site,20);
	        Spider.create(p1).addPipeline(new SiteURLsPipeline()).run();
		}
		
	}

	@Override
	public void process(Page page) {
		
		page.putField("MaxPageCount", maxpagecount);
		
		doneDownloadurls.add(page.getRequest().getUrl());
		String pageContent = page.getRawText();
		String charset = page.getCharset();
		if (page.getCharset().equalsIgnoreCase("gbk")||page.getCharset().equalsIgnoreCase("gb2312")){
			pageContent = StringHelper.gbk2utf8(page.getRawText());
			charset = "utf-8";
		}
		 
		page.putField("Charset", charset);
		
		//保存网页:
		String fileName = page.getRequest().getUrl().hashCode()+".html";
		String pagePath = pagesPath +"/"+fileName;
		FileUtil.writeFile(pagePath, pageContent,charset);
		
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
		
		//根据正则表达式找link:
		for (String regUrl:urlRegs){
			List<String> links = page.getHtml().links().regex(regUrl).all();
			 Set<String> newurls = new HashSet<String>();
			 for (String url:links){
				 if (url.toLowerCase().indexOf(domainName)<0) continue;
				 if (doneDownloadurls.contains(url)) continue;
				 if (allDownloadUrls.contains(url)) continue;
				 newurls.add(url);
			 }	
			 allDownloadUrls.addAll(newurls);
			 requests.addAll(newurls);
			 queryCount += newurls.size();
			 if (queryCount>=maxpagecount)
				 break;
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
		}
		
		 FileUtil.writeFile(urlPath, JSON.toJSONString(allDownloadUrls));
		 
		page.addTargetRequests(requests);	
		
		//取urls:
		Map<String,WebUrl> urls = getUrls(page);
		
		
		page.putField("PageUrls", urls);
		page.putField("DomainName", domainName);
		log.warn("get page "+urls.size()+",pageCount:"+queryCount);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			if (link.toLowerCase().indexOf(domainName)<0) continue;
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
