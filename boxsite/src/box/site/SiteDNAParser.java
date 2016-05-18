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

import box.mgr.SiteManager;
import box.site.db.SiteService;
import box.site.model.Websitekeys;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import easyshop.html.jericho.Element;
import es.download.flow.DownloadContext;
import es.util.http.PostPageGetter;
import es.util.url.URLStrHelper;
import es.util.word.WordToken;
import es.webref.model.PageRef;

public class SiteDNAParser extends Thread {
	protected Logger  log = Logger.getLogger(getClass()); 
	JiebaSegmenter segmenter;
   private HttpClient httpClient = null;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	private String siteId;
	private String userAgent;
	private PostPageGetter pageGetter;
    private String alexaapi = "http://data.alexa.com/data?cli=10&url=";
	private String baiduRank = "http://baidurank.aizhan.com/baidu/";
	private String googlePr = "http://toolbarqueries.google.com/search?client=navclient-auto&features=Rank&ch=8&q=info:";
	private Map<String,String> classKeys = new HashMap<String,String>();
	Set<OriHttpPage> pages = new HashSet<OriHttpPage>();
	
	public static void main(String[] args){
		SiteDNAParser getter = new SiteDNAParser();
		SiteService siteService = new SiteService();
		getter.dealUrlWords("http://news.ifeng.com/", 3, 5, siteService);
		siteService.DBCommit();
	}
	public SiteDNAParser(){
		userAgent = DownloadContext.getSpiderContext().getUserAgent();
		initHttpClient();
		pageGetter = new PostPageGetter(userAgent);
		segmenter = new JiebaSegmenter();
	}
	
	public void setSiteId(String site){
		siteId = site;
	}
	public void run() {
		while (1==1){
			synchronized(this){
				for (OriHttpPage page:pages){
					this.parse2DNAs(page,false);
				}
				pages.clear();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void pushPage(OriHttpPage page){
		pages.add(page);
	}
	
	public synchronized void parse2DNAs(OriHttpPage page,boolean findInfo){
		Map<String,Vector<PageRef>> urls = findUrls(page);

	}
	
	public void initHttpClient(){
		if (httpClient==null){
	      	httpClient = HttpClients.createDefault();
		}
	}
	
	public Map<String,Vector<PageRef>> findUrls(OriHttpPage page){
		
		htmlHelper.init(page.getContent());
		String domainName = URLStrHelper.getHost(page.getUrlStr());
		List<PageRef> refs = htmlHelper.getUrls(domainName);
		Map<String,Vector<PageRef>>  urlDirMap = new HashMap<String,Vector<PageRef>>();
		for (PageRef ref:refs){
			String dna = URLStrHelper.getUrlDNA(ref.getUrlStr());
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
	
	public int getAlexa(String weburl){
			int alexa = -1;
			String urlStr = alexaapi + weburl;
			HttpPage page = pageGetter.getHttpPage(urlStr, httpClient);
			if (page.getContent()==null)
				return alexa;
			String content  = new String(page.getContent());
	//		log.warn(content);
			String startKey = "TEXT=\"";
			String endKey = "\"";
			if (content.indexOf(startKey)<=0){
				log.warn("wrong alexa:"+content);
				return alexa;
			}
			String subt = content.substring(content.indexOf(startKey)+startKey.length());
			String rankText = subt.substring(0,subt.indexOf(endKey));
			if (rankText!=null)
				alexa = Integer.valueOf(rankText);
			log.warn("alexa "+alexa);
			return alexa;
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
