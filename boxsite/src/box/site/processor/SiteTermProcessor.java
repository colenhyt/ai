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
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
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
	private String startUrl;
	JiebaSegmenter segmenter;
	String domainName;
	Set<String>	stoplistWords;
	private String path;
	Set<String> notDownloadurls;
	Set<String> allDownloadUrls;
	String urlPath;
	private Site site;
	int maxpagecount;
	
	public SiteTermProcessor(String _startUrl,int _maxCount){
		maxpagecount = 10;
		if (_maxCount>0)
			maxpagecount = _maxCount;
		
		startUrl = _startUrl;
		domainName = URLStrHelper.getHost(startUrl).toLowerCase();
		
		path = "data/terms/"+domainName;
		List<File> files = FileUtil.getFiles(path);
		queryCount = files.size();
		
		allDownloadUrls = new HashSet<String>();
		notDownloadurls = new HashSet<String>();
		urlPath = path+".urls";
		String urlcontent = FileUtil.readFile(urlPath);
		if (urlcontent!=null&&urlcontent.trim().length()>0){
			List<String> urls = (List<String>)JSON.parse(urlcontent);
			allDownloadUrls.addAll(urls);
			for (String url:urls){
				String code = DigestUtils.md5Hex(url);
				File f = new File(path+"/"+code+".json");
				if (f.exists()) continue;
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
        Spider.create(new SiteTermProcessor(url,-1)).addPipeline(new SiteTermPipeline()).run();
	}

	@Override
	public void process(Page page) {
		
		page.putField("MaxPageCount", maxpagecount);
		
		path = "data/terms/"+domainName;
		List<File> files = FileUtil.getFiles(path);
		int currpagecount = files.size();
		if (currpagecount>=maxpagecount){
			log.warn("下载完成，总共数量 "+currpagecount);
			System.exit(0);
		}
		
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
				
				if (termsMap.containsKey(w))
					termsMap.put(w, termsMap.get(w).intValue()+1);
				else
					termsMap.put(w, 1);
			}
		}
		
		page.putField("PageTerm", termsMap);
		page.putField("DomainName", domainName);
		log.warn("get terms "+termsMap.size()+",pageCount:"+queryCount);
//		
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
