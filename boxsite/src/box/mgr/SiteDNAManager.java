package box.mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import us.codecraft.webmagic.Spider;
import box.site.model.ContentDNA;
import box.site.processor.SiteUrlGetProcessor;
import cn.hd.util.FileUtil;
import cn.hd.util.PageDownloader;

import com.alibaba.fastjson.JSON;

import easyshop.html.HTMLInfoSupplier;
import easyshop.html.TagDNA;
import es.util.url.URLStrHelper;

public class SiteDNAManager extends MgrBase {
	Map<String,ContentDNA> siteContentDNAMap = new HashMap<String,ContentDNA>();
	private PageDownloader downloader = new PageDownloader();
	private static SiteDNAManager uniqueInstance = null;
	static HTMLInfoSupplier infoSupp = new HTMLInfoSupplier();

	public static SiteDNAManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new SiteDNAManager();
		}
		return uniqueInstance;
	}
	
	public static void main(String[] args) {
		SiteDNAManager.getInstance().init();
		String url = "http://tech.163.com/16/0626/09/BQFOIBRU00097U81.html";
		String dnastr = "{'tag':'div','propName':'class','propValue':'post_text'}";
		int tagtype = 2;
		SiteDNAManager.getInstance().addItemTagDNA(url, dnastr, tagtype);
	}
	
	public void init(){
		
		List<File> files = FileUtil.getFiles(dnaPath);
		for (File file:files){
			String content = FileUtil.readFile(file);
			String sitekey = file.getName().substring(0,file.getName().lastIndexOf(".dna"));
			ContentDNA dna = (ContentDNA)JSON.parseObject(content, ContentDNA.class);
			siteContentDNAMap.put(sitekey, dna);
		}
	}
	
	//获取某个网站某个数量站内链接，为parse dna提供数据
	public String queryTestUrls(String siteStartUrl){
		String sitekey = URLStrHelper.getHost(siteStartUrl).toLowerCase();
		List<String> urls = new ArrayList<String>();
		String content = FileUtil.readFile(pagesPath+sitekey+".urls");
		if (content.trim().length()>0){
			List<String> urls2 = (List<String>)JSON.parse(content);
			int c = urls2.size()>10?10:urls2.size();
			for (int i=0;i<c;i++){
				urls.add(urls2.get(i));
			}
			return  JSON.toJSONString(urls);
		}
		Spider spider;
		SiteUrlGetProcessor p1 = new SiteUrlGetProcessor(siteStartUrl,3);
	     spider = Spider.create(p1);
	     p1.setSpider(spider);
	     spider.run();
	     Set<String> urlSet = p1.doneDownloadurlSet;
	     urls.addAll(urlSet);
		return JSON.toJSONString(urls);
	}
	
	//增加item url正则表达式
	public String addSiteItemUrlReg(String url,String regStr){
		regStr = regStr.replace(" ", "+");
		if (!url.matches(regStr)){
			log.warn("wrong reg: "+regStr+",url:"+url);
			return null;
		}
		String sitekey = URLStrHelper.getHost(url).toLowerCase();
		ContentDNA dna = siteContentDNAMap.get(sitekey);
		if (dna==null){
			dna = new ContentDNA();
			dna.setSitekey(sitekey);
			siteContentDNAMap.put(sitekey, dna);
		}	
		dna.addItemUrlReg(regStr);
		toSave(dna);
		return "true";
	}

	//增加item 内容页 tag特征
	public String addItemTagDNA(String testUrl,String dnaStr,int tagType){
		String sitekey = URLStrHelper.getHost(testUrl).toLowerCase();
		String content = null;
		content = FileUtil.readFile(pagesPath+sitekey+"/"+testUrl.hashCode()+".html");
		if (content==null||content.trim().length()<0){
			content = downloader.download(testUrl);
		}
		
		if (content==null)
			return null;
		
		TagDNA tagDna = (TagDNA)JSON.parseObject(dnaStr,TagDNA.class);
		tagDna.setType(tagType);
		infoSupp.init(content);
		String context = infoSupp.getContentByTagDna(tagDna);		
		if (context==null||context.trim().length()<=0){
			log.warn("could not find context "+testUrl+",dna:"+dnaStr);
			return null;
		}
		
		ContentDNA dna = siteContentDNAMap.get(sitekey);
		if (dna==null){
			dna = new ContentDNA();
			siteContentDNAMap.put(sitekey, dna);
		}
		dna.addTagDNA(tagDna);
		toSave(dna);
		
		return "true";
	}
	
	//parse获取相近tag DNAs,获取成功返回true
	public String parseSimContentTagDNAs(String url){
		String content = downloader.download(url);
		if (content==null)
			return null;
		
		
		
		return null;
	}
	
	//落地保存:
	public void toSave(ContentDNA dna){
		String content = JSON.toJSONString(dna);
		String sitekey = dna.getSitekey();
		FileUtil.writeFile(dnaPath+sitekey+".dna", content);
	}
}
