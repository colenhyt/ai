package box.site;

import java.util.Date;
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
import box.site.model.Website;
import box.site.model.Websitekeys;
import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import easyshop.html.jericho.Element;
import es.download.flow.DownloadContext;
import es.util.http.PostPageGetter;
import es.util.word.WordToken;

public class SiteContentGetter extends Thread {
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
		SiteContentGetter getter = new SiteContentGetter();
		getter.getDesc("http://news.ifeng.com/",true);
		SiteService siteService = new SiteService();
		getter.dealUrlWords("http://news.ifeng.com/", 3, 5, siteService);
		siteService.DBCommit();
	}
	public SiteContentGetter(){
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
					this.genWebSites(page,false);
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
	
	public void fillSiteInfo(Website site){
		String realurl =site.getUrl();
		if (realurl==null) return;
		
		site.setAlexa(this.getAlexa(site.getUrl()));
		site.setBdrank(this.getBdRank(site.getUrl()));	
		Vector<String> descs = this.getDesc(site.getUrl(),false);
		if (descs.size()>0){
			site.setName(descs.get(0));
			site.setCtitle(descs.get(0));
			site.setCdesc(descs.get(1));
			site.setKeywords(descs.get(2));
		}		
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public synchronized void genWebSites(OriHttpPage page,boolean findInfo){
		Vector<Website> sites = findWebSitesInPage(page);
		SiteService siteService = new SiteService();
		
		//site insert:
		Vector<Website> newSites = new Vector<Website>();
		SiteManager.getInstance()._findNewSites(sites,newSites);
		if (findInfo){
			for (Website site:newSites){
				String realurl =site.getUrl();
				if (realurl==null) continue;
				
				site.setAlexa(this.getAlexa(site.getUrl()));
				site.setBdrank(this.getBdRank(site.getUrl()));	
				Vector<String> descs = this.getDesc(site.getUrl(),false);
				this.dealUrlWords(site.getUrl(), page.getRefId(),site.getSiteid(),siteService);
				if (descs.size()>0){
					site.setName(descs.get(0));
					site.setCtitle(descs.get(0));
					site.setCdesc(descs.get(1));
					site.setKeywords(descs.get(2));
				}
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//add new sites:
		SiteManager.getInstance().addSites(siteService, newSites);
		
		//site word map:
		for (Website item:newSites){
			Websitekeys key = new Websitekeys();
			key.setSiteid(item.getSiteid());
			key.setWordid(page.getRefId());
			SiteManager.getInstance().addSitekey(siteService,key);
		}
		
		//baiduurl update:
		siteService.updateSearchUrl(page.getUrlStr());
		
		siteService.DBCommit();
	}
	
	public void initHttpClient(){
		if (httpClient==null){
	      	httpClient = HttpClients.createDefault();
		}
	}
	
	public Vector<Website> findWebSitesInPage(OriHttpPage page){
		return findWebSitesInPage(page.getContent(),page.getRefWord(),false);
	}
	
	public Vector<Website> findWebSitesInPage(byte[] content,String refWord,boolean findInfo){
		
		htmlHelper.init(content);
		Vector<Website> sites = new Vector<Website>();
		
		String classkey = classKeys.get(siteId);
		Vector<String> keys = new Vector<String>();
		keys.add("link?");
		keys.add("百度快照");
		if (classkey==null){
			Vector<String> vv2 = new Vector<String>();
			classkey = htmlHelper.findMaxSizeDivClassValue(keys, vv2);
			classKeys.put(siteId, classkey);
		}
		if (classkey==null){
			log.warn("error,没有找到该页面list item,根据关键字:"+keys.toString());
			return sites;
		}
		
		String[] itemsContent = htmlHelper.getDivsByClassValue(classkey);
		for (String itemstr:itemsContent){
			Website site = new Website();
			htmlHelper.init(itemstr.getBytes());
			List<String> urls =htmlHelper.getUrlStrsByLinkKey(keys.get(0));
			if (urls.size()<=0) continue;
			String url = urls.get(0);
			String realurl =new PostPageGetter(userAgent).getRealUrl(url, httpClient);
			site.setBaiduurl(url);
			site.setStatus(SiteDataManager.WEBSITE_STATUS_DONEURL);
			if (realurl!=null){
				site.setUrl(realurl);
			}
			site.setRemark(refWord);
			site.setCrdate(new Date());
			
			if (findInfo){
				fillSiteInfo(site);				
			}
			
			sites.add(site);
		}
		log.warn("找到websites: "+sites.size());
		return sites;
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
	
	public Set<WordToken> getPageWordTokens(String weburl){
		Set<WordToken> tokens = new HashSet<WordToken>();
		
		Map<String,WordToken> tokensMap = new HashMap<String,WordToken>();
		List<String> words = htmlHelper.getUrlWords();
		for (String sentence:words){
			if (sentence==null) continue;
			List<SegToken> segToken = segmenter.process(sentence, SegMode.INDEX);
			for (SegToken item:segToken){
				if (!tokensMap.containsKey(item.word)){
					WordToken token = new WordToken();
					token.setWord(item.word);
					tokensMap.put(item.word, token);
				}
				tokensMap.get(item.word).addFreq();	
			}			
		}
		
		tokens.addAll(tokensMap.values());
		log.warn(tokens.toString());
		return tokens;
	}
	
	public Vector<String> getDesc(String weburl,boolean findWords){
		Vector<String> descs = new Vector<String>();
		String urlStr = weburl;
		if (weburl.indexOf("http")<0)
			urlStr = "http://"+weburl;
		HttpPage page = pageGetter.getHttpPage(urlStr, httpClient);
		if (page.getContent()==null)
			return descs;	
		
		htmlHelper.init(page.getContent(),page.getCharSet());
		
		String title = htmlHelper.getBlockByTagName("title");
		if (title==null)
			title = "";
		descs.add(title);
		String desc = "";
		Element e = htmlHelper.getElementByOneProp("meta", "name", "description");
		if (e!=null){
		String desc2 = e.getAttributes().get("content").getValue();
			if (desc2!=null)
				desc = desc2;
		}
		descs.add(desc);
		
		String keywords = "";
		e = htmlHelper.getElementByOneProp("meta", "name", "keywords");
		
		if (e!=null){
		String desc2 = e.getAttributes().get("content").getValue();
			if (desc2!=null)
				keywords = desc2;
		}
		descs.add(keywords);
		
		return descs;
	}
	
	public int getBdRank(String weburl){
		int rank=-1;
		String urlStr = baiduRank+weburl+"/position/";
		HttpPage page = pageGetter.getHttpPage(urlStr, httpClient);
		if (page.getContent()==null)
			return rank;
		String content  = new String(page.getContent());
		htmlHelper.init(page.getContent());
		String div = htmlHelper.getDivByClassValue("box_17");
		if (div!=null){
			String startKey = "/brs/";
			String endKey = ".gif";
			if (content.indexOf(startKey)<=0)
				return rank;
			String rankText = content.substring(content.indexOf(startKey)+startKey.length(),content.indexOf(endKey));
			if (rankText!=null)
				rank = Integer.valueOf(rankText);			
		}
		log.warn("bdrank "+rank);
		return rank;
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
