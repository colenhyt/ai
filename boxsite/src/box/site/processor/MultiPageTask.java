package box.site.processor;

import us.codecraft.webmagic.Spider;

public class MultiPageTask implements Runnable {
	private String url;

	public MultiPageTask(String urlstr){
		url = urlstr;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		SitePageGetProcessor p1 = new SitePageGetProcessor(url,300);
        Spider.create(p1).addPipeline(new SiteURLsPipeline()).run();
	}

}
