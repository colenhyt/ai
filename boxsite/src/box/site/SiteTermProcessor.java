package box.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import cn.hd.util.FileUtil;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

import easyshop.html.HTMLInfoSupplier;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;

public class SiteTermProcessor implements PageProcessor{
	protected Logger  log = Logger.getLogger(getClass());
	int queryCount;
	private String startUrl;
	JiebaSegmenter segmenter;
	String domainName;
	Set<String>	stoplistWords;
	
	public SiteTermProcessor(String _startUrl){
		startUrl = _startUrl;
		domainName = URLStrHelper.getHost(startUrl).toLowerCase();
		
		segmenter = new JiebaSegmenter();
		stoplistWords = new HashSet<String>();
		List<String> cc = FileUtil.readFileWithLine("stoplist.txt");
		for (String w:cc){
			stoplistWords.add(w);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://www.open-open.com/jsoup/set-text.htm";
        Spider.create(new SiteTermProcessor(url)).addPipeline(new SiteTermPipeline()).run();
	}

	@Override
	public void process(Page page) {
		queryCount++;
		
		page.putField("PageCount", queryCount);
		if (queryCount<=0){
			List<String> requests = new ArrayList<String>();
			Set<String> seturls = new HashSet<String>();
			if (domainName!=null){
			 List<String> links = page.getHtml().links().all();
			 for (String url:links){
				 if (url.toLowerCase().indexOf(domainName)<0) continue;
				 seturls.add(url);
			 }
			 requests.addAll(seturls);
			}
			
			if (requests.size()>0)
				page.addTargetRequests(requests);			
		}
		
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
				
				if (StringHelper.isNumber(w)) continue;
				
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
		return Site.me().setDomain(domainName).addStartUrl(startUrl);
	}

}
