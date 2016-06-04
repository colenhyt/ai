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
import cn.hd.util.FileUtil;

import com.alibaba.fastjson.JSON;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

import easyshop.html.HTMLInfoSupplier;
import es.download.flow.DownloadContext;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;

public class SiteTermProcessor implements PageProcessor{
	protected Logger  log = Logger.getLogger(getClass());
	int queryCount;
	public String startUrl;
	JiebaSegmenter segmenter;
	String domainName;
	Set<String>	stoplistWords;
	Set<String>	filterlistWords;
	private String termsPath;
	private String pagesPath;
	public Set<String> notDownloadurls;
	public Set<String> doneDownloadurls;
	public Set<String> allDownloadUrls;
	String urlPath;
	private Site site;
	int maxpagecount;
	
	public SiteTermProcessor(String _startUrl,int _maxCount){
		maxpagecount = 10;
		if (_maxCount>0)
			maxpagecount = _maxCount;
		
		startUrl = _startUrl;
		domainName = URLStrHelper.getHost(startUrl).toLowerCase();
		
		termsPath = "data/terms/"+domainName;
		pagesPath = "data/pages/"+domainName;
		
		List<File> files = FileUtil.getFiles(termsPath);
		queryCount = files.size();
		
		allDownloadUrls = new HashSet<String>();
		notDownloadurls = new HashSet<String>();
		doneDownloadurls = new HashSet<String>();
		urlPath = termsPath+".urls";
		String urlcontent = FileUtil.readFile(urlPath);
		if (urlcontent!=null&&urlcontent.trim().length()>0){
			List<String> urls = (List<String>)JSON.parse(urlcontent);
			allDownloadUrls.addAll(urls);
			for (String url:urls){
				String code = DigestUtils.md5Hex(url);
				File f = new File(termsPath+"/"+code+".json");
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
		
		segmenter = new JiebaSegmenter();
		stoplistWords = new HashSet<String>();
		List<String> cc = FileUtil.readFileWithLine("stoplist.txt");
		for (String w:cc){
			stoplistWords.add(w);
		}
		
		
		filterlistWords = new HashSet<String>();
		cc = FileUtil.readFileWithLine("filterlist.txt");
		for (String w:cc){
			filterlistWords.add(w);
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
		String url = "http://developer.51cto.com";
		
		SiteTermProcessor processor = new SiteTermProcessor(url,-1);
//		Set<String> urls = processor.doneDownloadurls;
//		for (String url1:urls){
//			processor.parseFromPage(url,"gb2312");
//		}
		
		Set<String> sites = new HashSet<String>();
		
		for (String site:sites){
			SiteTermProcessor p1 = new SiteTermProcessor(site,-1);
	        Spider.create(p1).addPipeline(new SiteTermPipeline()).run();
			
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
		
		//parseFromPage(page.getRequest().getUrl(),site.getCharset());
		
		List<String> requests = new ArrayList<String>();
		if (notDownloadurls.size()>0){
			requests.addAll(notDownloadurls);
			queryCount += notDownloadurls.size();
			notDownloadurls.clear();
		}
		
		//塞入下载链接:
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
		
		//取词:
		Map<String,Integer> termsMap = getTerms(page);
		
		page.putField("PageTerm", termsMap);
		page.putField("DomainName", domainName);
		log.warn("get terms "+termsMap.size()+",pageCount:"+queryCount);
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
        Map<String,Integer> terms = getTerms(page);
        log.warn(terms.toString());
	}
	
	public Map<String,Integer> getTerms(Page page){
		Map<String,Integer> termsMap = new HashMap<String,Integer>();
		
		Html html = page.getHtml();
		Document doc = html.getDocument();
		//取页面所有文本:
		List<String> words = HTMLInfoSupplier.getMainTerms(doc);
		
		for (String word:words){
//			log.warn("words "+word);
			if (word==null||word.trim().length()<=0) continue;
			//切割分词:
			List<SegToken> segToken = segmenter.process(word, SegMode.INDEX);
			for (SegToken token:segToken){
				String w = token.word;
				if (w==null||w.trim().length()<=0) continue;
				
				//不记录数字
				if (StringHelper.isNumber(w)) continue;
				//不记录过长词:
				if (w.trim().length()>25){
					log.warn("发现过长词 :"+w);
					continue;
				}
				//去掉常用停用词:
				if (stoplistWords.contains(w)) continue;
				//字符过滤:
				String w1 = filter(w);
				
				if (w1.trim().length()<=0) continue;
				
				if (termsMap.containsKey(w1))
					termsMap.put(w1, termsMap.get(w1).intValue()+1);
				else
					termsMap.put(w1, 1);
			}
		}
		return termsMap;
	}
	
	public String filter(String word){
		String w = word;
		for (String str:filterlistWords){
			w = w.replace(str, "");
		}
		return w;
	}
	
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
