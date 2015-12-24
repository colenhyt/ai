package box.site;

import box.site.model.Website;
import es.webref.model.PageRef;

public class SiteContentGetter {
	private String alexaapi = "http://data.alexa.com/data?cli=10&url=%s";
	private String baiduRank = "http://baidurank.aizhan.com/baidu/%s/position/";
	private String googlePr = "http://toolbarqueries.google.com/search?client=navclient-auto&features=Rank&ch=8&q=info:";
	
	public Website findContent(PageRef pageRef){
		Website site = new Website();
		site.setAlexa(this.getAlexa(site.getUrl()));
		site.setBdrank(this.getBdRank(site.getUrl()));
		return site;
	}
	
	public int getBdRank(String weburl){
		int rank=0;
		return rank;
	}
	
	public int getAlexa(String weburl){
		int alexa = 0;
		return alexa;
	}
	
}
