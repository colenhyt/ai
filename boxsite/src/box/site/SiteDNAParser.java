package box.site;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

import box.mgr.SiteManager;
import box.site.db.SiteService;
import box.site.model.WebpageDNA;
import box.site.model.WebsiteDNA;
import box.site.model.Websitekeys;
import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import es.download.flow.DownloadContext;
import es.util.http.PostPageGetter;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

public class SiteDNAParser {
	protected Logger  log = Logger.getLogger(getClass()); 
	JiebaSegmenter segmenter;
   private HttpClient httpClient = null;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	private String userAgent;
	private PostPageGetter pageGetter;
	private Map<String,WebpageDNA>  dnaPageMap;
	
	Set<OriHttpPage> pages = new HashSet<OriHttpPage>();
	
	public static void main(String[] args){
		SiteDNAParser getter = new SiteDNAParser();
//		String a = "01dab6e3-5c87-4648-8d7a-902e0ccccda8";
//		String b = URLStrHelper.getUrlDNA(a);
//		PostPageGetter pageGetter = new PostPageGetter(DownloadContext.getSpiderContext().getUserAgent());
//		HttpPage p = pageGetter.getHttpPage("http://news.ifeng.com/", HttpClients.createDefault());
//		OriHttpPage p2 = new OriHttpPage(p.getUrlStr(),p.getContent(),null,p.getCharSet());
		getter.queryPageDNA("http://news.ifeng.com/");
	}
	public SiteDNAParser(){
		userAgent = DownloadContext.getSpiderContext().getUserAgent();
		dnaPageMap = new HashMap<String,WebpageDNA>();
		initHttpClient();
		pageGetter = new PostPageGetter(userAgent);
		segmenter = new JiebaSegmenter();
	}
	
	public WebpageDNA queryPageDNA(String urlStr){
		HttpPage page = pageGetter.getHttpPage(urlStr, httpClient);
		htmlHelper.init(page.getContent(),page.getCharSet());
//		Set<WebTerm> words = htmlHelper.getWords();
//		for (WebTerm w:words){
//			log.warn(w.getTagName()+":"+w.getText());
//		}
		String domainName = URLStrHelper.getHost(page.getUrlStr());
		WebpageDNA pageDNA = new WebpageDNA();
		//find urls:
		List<PageRef> refs = htmlHelper.getUrls(domainName);
		for (PageRef ref:refs){
			pageDNA.addDomainUrl(ref.getUrlStr());
		}
		//find texts:
		
		return null;
	}
	
	public void defineListAndDataPage(){
		
	}
	public WebsiteDNA parse2DNAs(OriHttpPage page){
		Map<String,Vector<PageRef>> urls = findSortUrls(page);
		if (urls.size()<=0)
			return null;
		
		//define list or data page by link count and relate links:
		//假定: 排除了相同url后, list页url数量会比data多,data页无链接文本会比list页多:找出page urls和无链接文本
		WebsiteDNA dna = new WebsiteDNA();
		for (String dirUrl:urls.keySet()){
			Vector<PageRef> refs = urls.get(dirUrl);
			if (refs.size()>5){
				WebpageDNA pageDNA = dnaPageMap.get(dirUrl);
				if (pageDNA==null){
					pageDNA = queryPageDNA(refs.get(0).getUrlStr());
					dnaPageMap.put(dirUrl, pageDNA);
					continue;
				}
			}
		}
		
		if (dnaPageMap.size()>0){
			defineListAndDataPage();
		}
		
		return dna;
	}
	
	public void initHttpClient(){
		if (httpClient==null){
	      	httpClient = HttpClients.createDefault();
		}
	}
	
	private Map<String,Vector<PageRef>> findSortUrls(OriHttpPage page){
		
		htmlHelper.init(page.getContent());
		String domainName = URLStrHelper.getHost(page.getUrlStr());
		List<PageRef> refs = htmlHelper.getUrls(domainName);
		Map<String,Vector<PageRef>>  urlDirMap = new HashMap<String,Vector<PageRef>>();
		for (PageRef ref:refs){
			String dna = URLStrHelper.getUrlDNA(ref.getUrlStr());
			log.warn("url("+ref.getUrlStr()+") dna="+dna);
			Vector<PageRef> dirUrls = urlDirMap.get(dna);
			if (dirUrls==null){
				dirUrls = new Vector<PageRef>();
				urlDirMap.put(dna, dirUrls);
			}
			boolean isBrother = true;
			for (PageRef ref2:dirUrls){
				if (!URLStrHelper.isBrotherUrl(ref2.getUrlStr(), ref.getUrlStr())){
					isBrother = false;
					break;
				}
			}
			if (isBrother){
				dirUrls.add(ref);				
			}
		}

		 //通过ArrayList构造函数把map.entrySet()转换成list 
		List<Map.Entry<String,Vector<PageRef>>> mappingList = new ArrayList<Map.Entry<String,Vector<PageRef>>>(urlDirMap.entrySet()); 
		  //通过比较器实现比较排序 
		  Collections.sort(mappingList, new Comparator<Map.Entry<String,Vector<PageRef>>>(){ 
		   public int compare(Map.Entry<String,Vector<PageRef>> mapping1,Map.Entry<String,Vector<PageRef>> mapping2){ 
		    return mapping1.getValue().size()> mapping2.getValue().size()?1:0; 
		   } 
		  }); 
		  
		log.warn("找到domain urlsmap: "+urlDirMap.size());
		return urlDirMap;
	}
	
	public void dealUrlWords(String weburl,int parentid,int siteId,SiteService siteService){
		Set<String> words = new HashSet<String>();
		
		List<String> urlwords = htmlHelper.getUrlWords();
		int count = 0;
		for (String sentence:urlwords){
			if (sentence==null) continue;
			List<SegToken> segToken = segmenter.process(sentence, SegMode.INDEX);
			for (SegToken item:segToken){
				words.add(item.word);
				int wordid = SiteManager.getInstance().addWord(siteService,item.word);
				if (wordid>0){
					Websitekeys key = new Websitekeys();
					key.setSiteid(siteId);
					key.setWordid(wordid);
					SiteManager.getInstance().addSitekey(siteService,key);
					
					SiteManager.getInstance().addWordRelation(siteService,wordid, parentid, 2);
					count++;
				}
			}			
		}
		
		log.warn(weburl+" 找到链接词语:  "+count);
		
	}
	
}
