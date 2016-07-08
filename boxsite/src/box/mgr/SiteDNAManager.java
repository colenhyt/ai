package box.mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import us.codecraft.webmagic.Spider;
import box.site.model.ContentDNA;
import box.site.model.TagDNA;
import box.site.processor.SiteUrlGetProcessor;
import cl.util.FileUtil;
import cn.hd.util.PageDownloader;

import com.alibaba.fastjson.JSON;

import es.util.url.URLStrHelper;

public class SiteDNAManager extends MgrBase {
	Map<String,ContentDNA> siteContentDNAMap = new HashMap<String,ContentDNA>();
	private PageDownloader downloader = new PageDownloader();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		List<String> urls = new ArrayList<String>();
		Spider spider;
		SiteUrlGetProcessor p1 = new SiteUrlGetProcessor(siteStartUrl,100);
	     spider = Spider.create(p1);
	     p1.setSpider(spider);
	     spider.run();
	     Set<String> urlSet = p1.doneDownloadurlSet;
	     urls.addAll(urlSet);
		return JSON.toJSONString(urls);
	}
	
	//增加item url正则表达式
	public void testAndAddSiteItemUrlReg(String url,String regStr){
		if (!url.matches(regStr)){
			log.warn("wrong reg: "+regStr+",url:"+url);
			return;
		}
		String sitekey = URLStrHelper.getHost(url).toLowerCase();
		ContentDNA dna = siteContentDNAMap.get(sitekey);
		if (dna==null){
			dna = new ContentDNA();
			siteContentDNAMap.put(sitekey, dna);
		}	
		dna.addItemUrlReg(regStr);
		toSave(dna);
	}

	public boolean testDna(String content,TagDNA tagDna){
		
		return false;
	}
	
	//增加item 内容页 tag特征
	public void testAndAddItemTagDNA(String testUrl,String dnaStr){
		String content = downloader.download(testUrl);
		if (content==null)
			return;
		
		String sitekey = URLStrHelper.getHost(testUrl).toLowerCase();
		TagDNA tagDna = (TagDNA)JSON.parseObject(dnaStr,TagDNA.class);
		boolean test = testDna(content,tagDna);
		if (!test)
			return;
		
		ContentDNA dna = siteContentDNAMap.get(sitekey);
		if (dna==null){
			dna = new ContentDNA();
			siteContentDNAMap.put(sitekey, dna);
		}
		dna.addTagDNA(tagDna);
		toSave(dna);
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
