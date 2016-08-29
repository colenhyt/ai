package box.site.parser.sites;

import us.codecraft.webmagic.Spider;
import box.mgr.ProcessManager;
import box.site.processor.SitePageGetProcessor;
import box.site.processor.SiteURLsPipeline;

public class MultiPageTask implements Runnable {
	private String url;
	private int count = 0;
	private Spider spider;

	public MultiPageTask(String urlstr,int c){
		count = c;
		url = urlstr;
		
		SitePageGetProcessor p1 = new SitePageGetProcessor(this,url,count);
      spider = Spider.create(p1);
      spider.addPipeline(new SiteURLsPipeline());
//      spider.addPipeline(new SiteTopItemsPipeline());
//		spider = Spider.create(p1).addPipeline(new SiteURLsPipeline());		
	}

	public void finishCallback(String sitekey){
		spider.stop();
		spider.close();
		ProcessManager.getInstance().spiderFinished(sitekey);
//		Thread.currentThread().stop();
	}
	
	@Override
	public void run() {
			spider.run();
	}

}
