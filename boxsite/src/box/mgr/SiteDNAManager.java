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
import cn.hd.util.PageDownloader;

import com.alibaba.fastjson.JSON;

import easyshop.html.HTMLInfoSupplier;
import easyshop.html.TagDNA;
import es.util.FileUtil;
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
	
//		List<File> dnafiles = FileUtil.getFiles("dna/");
//		for (File file:dnafiles){
//			String content = FileUtil.readFile(file);
//			List<String> urls = (List<String>)JSON.parse(content);
//			String url = "http://www."+file.getName().substring(0,file.getName().indexOf(".json"));
//			SiteDNAManager.getInstance().addSiteItemUrlReg(url, urls.get(0));
//		}
//		SiteDNAManager.getInstance().queryTestUrls("http://www.56135.com");
		String url = "http://weixin.qq.com";
		String dnastr = "div;class;rich_media_content";
		int tagtype = TagDNA.TAG_TYPE_TAG_AND_PROP;
		SiteDNAManager.getInstance().addItemTagDNA(url, dnastr, tagtype);
	}
	
	public void init(){
		
		List<File> files = FileUtil.getFiles(dnaPath);
		for (File file:files){
			String sitekey = file.getName().substring(0,file.getName().indexOf(".dna"));
			String content = FileUtil.readFile(file); 
           ContentDNA  dna = JSON.parseObject(content,ContentDNA.class);
           siteContentDNAMap.put(sitekey, dna);
		}
	}
	
	//获取某个网站某个数量站内链接，为parse dna提供数据
	public String queryTestUrls(String siteStartUrl){
		String sitekey = URLStrHelper.getHost(siteStartUrl).toLowerCase();
		List<String> urls = new ArrayList<String>();
		String content = FileUtil.readFile(pagesPath+sitekey+".urls");
		if (content.trim().length()>0){
			List<String> urls2 = (List<String>)JSON.parseObject(content);
			int c = urls2.size()>20?20:urls2.size();
			int cc = 0;
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
		if (dna==null)
		{
			dna = new ContentDNA();
			dna.setSitekey(sitekey);
			siteContentDNAMap.put(sitekey, dna);
		}	
		dna.addItemUrlReg(regStr);
		dna.toSave(dnaPath);
		return "true";
	}

	//增加item 内容页 tag特征
	public String addItemTagDNA(String testUrl,String dnaStr,int tagType){
		String sitekey = URLStrHelper.getHost(testUrl).toLowerCase();
		String content = null;
		String path = pagesPath+sitekey+"/"+testUrl.hashCode()+".html";
		path = "data/-242464285.html";
		content = FileUtil.readFile(path);
		if (content==null||content.trim().length()<0){
			content = downloader.download(testUrl);
		}
		
		if (content==null)
			return null;
		
		String[] strs = dnaStr.split(";");
		
		TagDNA tagDna = new TagDNA();
		tagDna.setType(tagType);
		if (tagType==TagDNA.TAG_TYPE_TAG){
			tagDna.setTag(strs[0]);
		}else if (tagType==TagDNA.TAG_TYPE_TAG_KEY){
			tagDna.setTag(strs[0]);
			tagDna.setKeyword(strs[1]);
		}else if (tagType==TagDNA.TAG_TYPE_TAG1START_AND_TAG2END){
			tagDna.setStartTagStr(strs[0]);
			tagDna.setEndTagStr(strs[1]);
		}else if (tagType==TagDNA.TAG_TYPE_TAG_AND_PROP||tagType==TagDNA.TAG_TYPE_TAG_AND_PROP_KEY){
			tagDna.setTag(strs[0]);
			tagDna.setPropName(strs[1]);
			tagDna.setPropValue(strs[2]);
			if (strs.length>3)
				tagDna.setKeyword(strs[3]);
		}
		infoSupp.init(content);
		String context = infoSupp.getContentByTagDna(tagDna);		
		if (context==null||context.trim().length()<=0){
			log.warn("could not find context "+testUrl+",dna:"+dnaStr);
			return null;
		}
		
		ContentDNA dna = siteContentDNAMap.get(sitekey);
		if (dna==null){
			dna = new ContentDNA();
			dna.setSitekey(sitekey);
			siteContentDNAMap.put(sitekey, dna);
		}
		
		dna.addTagDNA(tagDna);
		dna.toSave(dnaPath);
		
		return "true";
	}
	
	//parse获取相近tag DNAs,获取成功返回true
	public String parseSimContentTagDNAs(String url){
		String content = downloader.download(url);
		if (content==null)
			return null;
		
		
		
		return null;
	}
}
