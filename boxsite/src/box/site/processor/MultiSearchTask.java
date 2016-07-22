package box.site.processor;

import us.codecraft.webmagic.Spider;
import box.mgr.SiteSearchManager;

public class MultiSearchTask implements Runnable,ProcessCallback {
	private String siteStr;
	private int count = 0;
	private String searchWord;
	private Spider spider;

	public MultiSearchTask(String site,String sWord,int c){
		siteStr = site;
		searchWord = sWord;
		count = c;
		ListSearchProcessor p1 = new ListSearchProcessor();
		p1.init(site, searchWord, c,this);
		spider = Spider.create(p1);
		spider.addPipeline(new ListSearchPipeline());
	}

	@Override
	public void run() {
		spider.run();
	}

	@Override
	public void onSpiderDone(String sitekey) {
		spider.stop();
		spider.close();
		SiteSearchManager.getInstance().onSpiderDone(sitekey);
		
	}

}
