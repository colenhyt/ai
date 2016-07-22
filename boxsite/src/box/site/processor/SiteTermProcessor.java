package box.site.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;
import box.site.model.WebUrl;

import com.alibaba.fastjson.JSON;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

import easyshop.html.HTMLInfoSupplier;
import es.download.flow.DownloadContext;
import es.util.FileUtil;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

public class SiteTermProcessor implements PageProcessor{
	protected Logger  log = Logger.getLogger(getClass());
    private HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	int queryCount;
	public String startUrl;
	JiebaSegmenter segmenter;
	String domainName;
	Set<String>	stoplistWords;
	Set<String>	filterlistWords;
	private String siteTermsPath;
	private String sitePagesPath;
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
		
		siteTermsPath = "data/terms/"+domainName;
		sitePagesPath = "data/pages/"+domainName;
		
		List<File> files = FileUtil.getFiles(siteTermsPath);
		queryCount = files.size();
		
		allDownloadUrls = new HashSet<String>();
		notDownloadurls = new HashSet<String>();
		doneDownloadurls = new HashSet<String>();
		urlPath = siteTermsPath+".urls";
		String urlcontent = FileUtil.readFile(urlPath);
		if (urlcontent!=null&&urlcontent.trim().length()>0){
			List<String> urls = (List<String>)JSON.parse(urlcontent);
			allDownloadUrls.addAll(urls);
			for (String url:urls){
				String code = DigestUtils.md5Hex(url);
				File f = new File(siteTermsPath+"/"+code+".json");
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
		String url = "http://www.iheima.com";
		
		SiteTermProcessor processor = new SiteTermProcessor(url,-1);
//		Set<String> urls = processor.doneDownloadurls;
//		for (String url1:urls){
//			processor.parseFromPage(url,"gb2312");
//		}
		
		processor.processTrainingDataTerms();
		
	}
	
	//对下所有目录下的所有文件切词，并统计词频,排序:
	public void processTrainingDataTerms(){
		String rootPath = "data/training/";
		List<File> folders = FileUtil.getFolders(rootPath);
		for (File folder:folders){
			Map<String,Integer> termsMap = new HashMap<String,Integer>();
			String termPath = rootPath+folder.getName();
			List<File> files = FileUtil.getFiles(folder.getAbsolutePath());
			for (File f:files){
				String content = FileUtil.readFile(f);
				getWordTerms(content,termsMap);
			}
			
			  //通过比较器实现比较排序 
				List<Map.Entry<String,Integer>> mappingList = new ArrayList<Map.Entry<String,Integer>>(termsMap.entrySet()); 
			  Collections.sort(mappingList, new Comparator<Map.Entry<String,Integer>>(){ 
			   public int compare(Map.Entry<String,Integer> mapping1,Map.Entry<String,Integer> mapping2){ 
				   return mapping2.getValue().compareTo(mapping1.getValue()); 
			   } 
			  }); 
			  
			String fileName = termPath +".terms";
			FileUtil.writeFile(fileName, JSON.toJSONString(mappingList));
			log.warn(folder.getName()+" get terms "+termsMap.size());
		}
	}

	@Override
	public void process(Page page) {
		
		page.putField("MaxPageCount", maxpagecount);
		page.putField("Charset", page.getCharset());
		
		doneDownloadurls.add(page.getRequest().getUrl());
		
		//保存网页:
		String fileName = page.getRequest().getUrl().hashCode()+".html";
		String pagePath = sitePagesPath +"/"+fileName;
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
		
		//取urls:
		Map<String,WebUrl> urls = getUrls(page);
		
		//取词:
		Map<String,Integer> termsMap = getTerms(page.getHtml().getDocument());
		
		page.putField("PageUrls", urls);
		page.putField("PageTerm", termsMap);
		page.putField("DomainName", domainName);
		log.warn("get terms "+termsMap.size()+",pageCount:"+queryCount);
//		
	}

	public void parseFromPage(String urlstr,String charset){
		String fileName = urlstr.hashCode()+".html";
		String pagePath = sitePagesPath +"/"+fileName;	
		String content = FileUtil.readFile(pagePath, charset);
		Request request = new Request(urlstr);
		Page page = new Page();
        page.setRawText(content);
        page.setRequest(request);
        page.setUrl(new PlainText(urlstr));
        Map<String,Integer> terms = getTerms(page.getHtml().getDocument());
        log.warn(terms.toString());
	}
	
	public Map<String,WebUrl> getUrls(Page page){
		Map<String,WebUrl> urls = new HashMap<String,WebUrl>();
		htmlHelper.init(page.getRequest().getUrl(),page.getRawText().getBytes(), page.getCharset());
		List<PageRef> refs = htmlHelper.getUrls(domainName);
		
		
		for (PageRef ref:refs){
			WebUrl url = new WebUrl();
			url.setText(ref.getRefWord());
			url.setUrl(ref.getUrlStr());
			urls.put(url.getUrl(),url);
		}
		return urls;
	}
	
	public void getWordTerms(String word,Map<String,Integer> termsMap){
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
	
	public Map<String,Integer> getTerms(Document doc){
		Map<String,Integer> termsMap = new HashMap<String,Integer>();
		
		//取页面所有文本:
		List<String> words = HTMLInfoSupplier.getMainTerms(doc);
		
		for (String word:words){
//			log.warn("words "+word);
			if (word==null||word.trim().length()<=0) continue;
			getWordTerms(word,termsMap);
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

	//对某个目录下的所有文件切词，并统计词频,排序:
		public void processFilesTerms(){
			List<File> files = FileUtil.getFiles(sitePagesPath);
	//		for (File f:files){
	//			String content = FileUtil.readFile(f);
	//			DomPage domPage = HtmlBot.getDomPageByHtml(content);
	//			Document doc = domPage.getDoc();
	//			Map<String,Integer>  mapTerms = getTerms(doc);
	//			String str = f.getName().substring(0,f.getName().indexOf(".html"));
	//			String fileName = siteTermsPath + "/"+ str+".json";
	//			FileUtil.writeFile(fileName, JSON.toJSONString(mapTerms));
	//			log.warn("get terms "+mapTerms.size());
	//		}
			
			Map<String,Integer> termsMap = new HashMap<String,Integer>();
			files = FileUtil.getFiles(siteTermsPath);
			for (File f:files){
				String content = FileUtil.readFile(f);
				Map<String,Integer> json = (Map<String,Integer>)JSON.parse(content);
				for (String key:json.keySet()){
					if (termsMap.containsKey(key))
						termsMap.put(key, termsMap.get(key)+json.get(key));
					else
						termsMap.put(key, json.get(key));
				}
			}		
			
		  //通过比较器实现比较排序 
			List<Map.Entry<String,Integer>> mappingList = new ArrayList<Map.Entry<String,Integer>>(termsMap.entrySet()); 
		  Collections.sort(mappingList, new Comparator<Map.Entry<String,Integer>>(){ 
		   public int compare(Map.Entry<String,Integer> mapping1,Map.Entry<String,Integer> mapping2){ 
			   return mapping2.getValue().compareTo(mapping1.getValue()); 
		   } 
		  }); 
			  
	//	  for(Map.Entry<String,Integer> mapping:mappingList){ 
	//		   System.out.println(mapping.getKey()+":"+mapping.getValue()); 
	//		  }	  
			int count  = 50;
			String fileHot = siteTermsPath+"_"+count+".json";
			List<Map.Entry<String,Integer>> toplist = new ArrayList<Map.Entry<String,Integer>>();
			for (int i=0;i<mappingList.size();i++){
				Map.Entry<String,Integer> item = mappingList.get(i);
				if (item.getKey().length()>1){
					log.warn("keyword: "+JSON.toJSONString(item));
					toplist.add(item);
				}if (toplist.size()>count)break;
			}
			FileUtil.writeFile(fileHot, JSON.toJSONString(toplist));
			String fileName = siteTermsPath+".json";
			FileUtil.writeFile(fileName, JSON.toJSONString(mappingList));
		}

}
