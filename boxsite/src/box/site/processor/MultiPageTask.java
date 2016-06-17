package box.site.processor;

import box.mgr.ProcessManager;
import us.codecraft.webmagic.Spider;

public class MultiPageTask implements Runnable {
	private String url;
	private int count = 0;
	private ProcessManager mgr;

	public MultiPageTask(ProcessManager _mgr,String urlstr,int c){
		count = c;
		url = urlstr;
		mgr = _mgr;
	}

	public void finishCallback(String sitekey){
		mgr.spiderFinished(sitekey);
		Thread.currentThread().stop();
	}
	
	@Override
	public void run() {
		SitePageGetProcessor p1 = new SitePageGetProcessor(this,url,count);
        Spider.create(p1).addPipeline(new SiteURLsPipeline()).run();
	}

}
