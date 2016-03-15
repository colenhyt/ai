package es.util.http;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
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
	private HttpClient httpClient;
	
	
//	public HttpPage getPage(){
//		
//	}

	public HttpPage getHttpPage(String urlStr,Map params){
		return getHttpPage(urlStr,defaultHttpClient(),null,params);
	}
	
	public HttpPage getHttpPage(String urlStr,HttpClient client,Map params){
		return getHttpPage(urlStr,client,null,params);
	}

	public HttpPage getHttpPage(String urlStr,HttpClient client){
	    	client.getParams().setParameter(HttpMethodParams.USER_AGENT, HTTP_USER_AGENT);  //让服务器认为是IE
	        
	        GetMethod post = new GetMethod(urlStr);
            
	        try {
	            
	            long startTime = System.currentTimeMillis();
	            int iGetResultCode = client.executeMethod(post);
	            post.setFollowRedirects(false);
	            Header locationHeader = post.getResponseHeader("location");
	        client.getParams().setParameter("http.socket.timeout",new Integer(60000 * 10));
	        post = new GetMethod(locationHeader.getValue());
//	        post.setFollowRedirects(true);
	        iGetResultCode = client.executeMethod(post);
	        
	            if (post.getResponseContentLength()>=2024000){
	                log.info("content is too large, can't download!");
	            	ConnResponse conRes=new ConnResponse(null,null,0,0,0);
	                return new OriHttpPage(-1,urlStr, null,null,conRes,null);
	            }
	                
	            BufferedInputStream remoteBIS = new BufferedInputStream(post.getResponseBodyAsStream());
	            ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
	            byte[] buf = new byte[1024];
	            int bytesRead = 0;
	            while(bytesRead >= 0)
	            {
	                baos.write(buf, 0, bytesRead);
	                bytesRead = remoteBIS.read(buf);
	            }
	            remoteBIS.close();
	            byte[] content = baos.toByteArray();
	//            byte[] content=get.getResponseBody();
	            
	            
	            long timeTaken = System.currentTimeMillis() - startTime;
	           if(timeTaken < 100) timeTaken = 500;
	            int bytesPerSec = (int) ((double) content.length / ((double)timeTaken / 1000.0));
	            log.info("Downloaded " + content.length + " bytes, " + bytesPerSec + " bytes/sec");
	        	ConnResponse conRes=new ConnResponse(post.getResponseHeader("Content-type").getValue(),null,0,0,post.getStatusCode());
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
	        	post.releaseConnection();
	        }/**/        
	    }

	public HttpPage getHttpPage(String urlStr,HttpClient client,String charSet,Map params){
		    	client.getParams().setParameter(HttpMethodParams.USER_AGENT, HTTP_USER_AGENT);  //让服务器认为是IE
		        
		        PostMethod post = new PostMethod(urlStr);
		        if (params.size()>0){
		        Object[] names=params.keySet().toArray();
		        
		        NameValuePair[] data=new NameValuePair[names.length];
		        for (int i=0;i<names.length;i++){
		        	String value=(String)params.get(names[i]);
		        	data[i]=new NameValuePair((String)names[i],value);
		        }
		        post.setRequestBody(data);
		        }
		            
		        try {
		            
		            long startTime = System.currentTimeMillis();
		            int iGetResultCode = client.executeMethod(post);
		            post.setFollowRedirects(false);
		            Header locationHeader = post.getResponseHeader("location");
		        client.getParams().setParameter("http.socket.timeout",new Integer(60000 * 10));
		        post = new PostMethod(locationHeader.getValue());
	//	        post.setFollowRedirects(true);
		        iGetResultCode = client.executeMethod(post);
		        
		            if (post.getResponseContentLength()>=2024000){
		                log.info("content is too large, can't download!");
		            	ConnResponse conRes=new ConnResponse(null,null,0,0,0);
		                return new OriHttpPage(-1,urlStr, null,null,conRes,null);
		            }
		                
		            BufferedInputStream remoteBIS = new BufferedInputStream(post.getResponseBodyAsStream());
		            ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
		            byte[] buf = new byte[1024];
		            int bytesRead = 0;
		            while(bytesRead >= 0)
		            {
		                baos.write(buf, 0, bytesRead);
		                bytesRead = remoteBIS.read(buf);
		            }
		            remoteBIS.close();
		            byte[] content = baos.toByteArray();
		//            byte[] content=get.getResponseBody();
		            
		            
		            long timeTaken = System.currentTimeMillis() - startTime;
		           if(timeTaken < 100) timeTaken = 500;
		            int bytesPerSec = (int) ((double) content.length / ((double)timeTaken / 1000.0));
		            log.info("Downloaded " + content.length + " bytes, " + bytesPerSec + " bytes/sec");
		        	ConnResponse conRes=new ConnResponse(post.getResponseHeader("Content-type").getValue(),null,0,0,post.getStatusCode());
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

