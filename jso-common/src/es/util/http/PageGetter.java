package es.util.http;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
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
    HttpClient httpClient= HttpClients.createDefault();
	
	public PageGetter(String userAgent){
        this.userAgent=userAgent;
	}
	
	public PageGetter(){
        this.userAgent=HTTP_USER_AGENT;
	}
	
	protected HttpClient defaultHttpClient(){
        return httpClient;
	}
}

