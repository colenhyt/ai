package box.site;

import easyshop.downloadhelper.HttpPageGetter;
import easyshop.downloadhelper.OriHttpPage;
import es.webref.model.PageRef;

public class SiteDataManager {
	HttpPageGetter pageGetter;
	public static final int WEBSITE_STATUS_INIT = 0;
	public static final int WEBSITE_STATUS_DONEURL = 1;
	String bdSearchUrl =  "http://www.baidu.com/s?wd=%s&pn=%d";
	
	private static SiteDataManager uniqueInstance = null;

	public static SiteDataManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new SiteDataManager();
		}
		return uniqueInstance;
	}
	
	public synchronized OriHttpPage queryBdPage(String url){
		PageRef ref = new PageRef(url);
		return pageGetter.getOriHttpPage(ref);
	}
	
	public HttpPageGetter getGetter(){
		return pageGetter;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
