package es.util.http;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import easyshop.downloadhelper.ConnResponse;
import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.OriHttpPage;



/*
 ** JSO1.0, by Allen Huang,2007-6-13
 */
public class PostPageGetter extends PageGetter{
	static Logger log = Logger.getLogger("FormGetter.java");
	private Map params;
	private CloseableHttpClient  httpClient;
    private int maxConnPerHost=2;
    private int timeOut=6000;
    private int maxTotalConn=20;
    public final static String HTTP_USER_AGENT="Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)";
	
	
//	public HttpPage getPage(){
//		
//	}

	public static void main(String[] args){
		CloseableHttpClient httpClient = null;
		if (httpClient==null){
	      	httpClient = HttpClients.createDefault();
	     }		
		
		PostPageGetter getter = new PostPageGetter();
		String urlStr = "http://www.baidu.com/link?url=uuN0CrjlJodI_24JBncL7ckR8yMhLQskJV4ykXtZDYhASFtBG9rFyrI0pVMn7xn9";
		getter.getHttpPage(urlStr, httpClient);
	}
	
	public HttpPage getHttpPage(String urlStr,Map params){
		return getHttpPage(urlStr,defaultHttpClient(),null,params);
	}
	
	public HttpPage getHttpPage(String urlStr,HttpClient client,Map params){
		return getHttpPage(urlStr,client,null,params);
	}

	public HttpPage getHttpPage(String urlStr,HttpClient client){
            HttpGet get= new HttpGet(urlStr);
	        
            HttpContext httpContext = new BasicHttpContext();
            
	        try {
	            get.addHeader("User-Agent", HTTP_USER_AGENT);
	            RequestConfig defaultRequestConfig = RequestConfig.custom()
	                    .setSocketTimeout(10000).build();
	            get.setConfig(defaultRequestConfig);
	            CloseableHttpResponse rep = (CloseableHttpResponse)client.execute(get,httpContext);
	            HttpHost host = (HttpHost) httpContext
	                    .getAttribute(ExecutionContext.HTTP_TARGET_HOST);
	            HttpEntity repEntity = rep.getEntity();
	            String context = EntityUtils.toString(repEntity);
	            long startTime = System.currentTimeMillis();
	        
	            byte[] content = context.getBytes();
	//            byte[] content=get.getResponseBody();
	            
	            
	            long timeTaken = System.currentTimeMillis() - startTime;
	           if(timeTaken < 100) timeTaken = 500;
	            int bytesPerSec = (int) ((double) content.length / ((double)timeTaken / 1000.0));
	            log.info("Downloaded " + content.length + " bytes, " + bytesPerSec + " bytes/sec");
	        	ConnResponse conRes=new ConnResponse(null,null,0,0,rep.getStatusLine().getStatusCode());
	        	return new HttpPage(urlStr, content,conRes,"gbk");            
	            
	        } catch(IOException ioe)
	        {
	            log.warn("Caught IO Exception: " + ioe.getMessage(), ioe);
	            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
	            return new OriHttpPage(-1,urlStr, null,null,conRes,null);
	        }
	        catch(Exception e)
	        {
	            log.warn("Caught Exception: " + e.getMessage(), e);
	            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
	            return new OriHttpPage(-1,urlStr, null,null,conRes,null);
	        }  finally {
	        	get.releaseConnection();
	        }/**/        
	    }

	public HttpPage getHttpPage(String urlStr,HttpClient client,String charSet,Map params){
		        
		        HttpPost post = new HttpPost(urlStr);
		        if (params.size()>0){
		        Object[] names=params.keySet().toArray();
		        
		        NameValuePair[] data=new NameValuePair[names.length];
		        for (int i=0;i<names.length;i++){
		        	String value=(String)params.get(names[i]);
		        	post.addHeader((String)names[i], value);
		        }
		        }
		            
		        try {
		            
		            post.addHeader("User-Agent", HTTP_USER_AGENT);
		            long startTime = System.currentTimeMillis();
		            CloseableHttpResponse rep = (CloseableHttpResponse)client.execute(post);
		            HttpEntity repEntity = rep.getEntity();
		            String context = EntityUtils.toString(repEntity);
		        
		            byte[] content = context.getBytes();
		            
		            if (context.length()>=2024000){
		                log.info("content is too large, can't download!");
		            	ConnResponse conRes=new ConnResponse(null,null,0,0,0);
		                return new OriHttpPage(-1,urlStr, null,null,conRes,null);
		            }
		                
		            long timeTaken = System.currentTimeMillis() - startTime;
		           if(timeTaken < 100) timeTaken = 500;
		            int bytesPerSec = (int) ((double) content.length / ((double)timeTaken / 1000.0));
		            log.info("Downloaded " + content.length + " bytes, " + bytesPerSec + " bytes/sec");
		        	ConnResponse conRes=new ConnResponse(null,null,0,0,rep.getStatusLine().getStatusCode());
		        	return new HttpPage(urlStr, content,conRes,charSet);            
		            
		        } catch(IOException ioe)
		        {
		            log.warn("Caught IO Exception: " + ioe.getMessage(), ioe);
		            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
		            return new OriHttpPage(-1,urlStr, null,null,conRes,null);
		        }
		        catch(Exception e)
		        {
		            log.warn("Caught Exception: " + e.getMessage(), e);
		            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
		            return new OriHttpPage(-1,urlStr, null,null,conRes,null);
		        }  finally {
		        	post.releaseConnection();
		        }/**/        
		    }
	
}

