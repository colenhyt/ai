package cn.hd.util;

import java.util.HashSet;
import java.util.Set;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import es.util.SpiderConfigProxy;

public class PageDownloader implements Task{
	HttpClientDownloader clientDownloader = new HttpClientDownloader();
	private Site site;

	public PageDownloader(){
		site = new Site();
		String userAgent = SpiderConfigProxy.getSpiderContext().getUserAgent();
		site.addHeader("User-Agent", userAgent);
		Set<Integer> codes = new HashSet<Integer>();
		codes.add(200);
		codes.add(404);
		site.setAcceptStatCode(codes);
	}
	
	public String download(String url){
		Request request = new Request(url);
		Page page = clientDownloader.download(request, this);
		return page.getRawText();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
