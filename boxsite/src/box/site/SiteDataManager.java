package box.site;

import org.apache.http.client.HttpClient;

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
//		HttpClient httpClient = new HttpClient();  
		//设置代理服务器的ip地址和端口  
//		httpClient.getHostConfiguration().setProxy("192.168.101.1", 5608);  
		//使用抢先认证  
//		httpClient.getParams().setAuthenticationPreemptive(true); 
	}

}
