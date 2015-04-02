package es.util.http;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;


/*
 ** JSO1.0, by Allen Huang,2007-6-13
 */
public class PageGetter {
    public static String HTTP_USER_AGENT="Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)";
    public static String AGENT_GOOGLE="GoogleBot";
    public static String AGENG_BAIDU="BaiDuSpider";
    public static String AGENT_YAHOO="Inktomi Slurp";
    public static String AGENT_DEFAULT=AGENT_YAHOO;
	static Logger log = Logger.getLogger("PageGetter.java");
    protected String userAgent;
  	MultiThreadedHttpConnectionManager connectionManager =new MultiThreadedHttpConnectionManager();
    HttpClient httpClient=new HttpClient(connectionManager);;
	
	public PageGetter(){
        this.userAgent=AGENG_BAIDU;
	}
	
	protected HttpClient defaultHttpClient(){
        httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, HTTP_USER_AGENT);  //让服务器认为是IE     	
        return httpClient;
	}
}

