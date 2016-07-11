package box.site.processor;

import box.mgr.ProcessManager;
import us.codecraft.webmagic.Spider;

public class MultiSearchTask implements Runnable {
	private String siteStr;
	private int count = 0;
	private String searchWord;

	public MultiSearchTask(String site,String sWord,int c){
		siteStr = site;
		searchWord = sWord;
		count = c;
	}

	@Override
	public void run() {
		ListSearchProcessor p1 = new ListSearchProcessor();
//		p1.init(siteStr, searchWord, count);
//        Spider.create(p1).addPipeline(new SiteURLsPipeline()).run();
	}

}
