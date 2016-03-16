/*
 * This is the MIT license, see also http://www.opensource.org/licenses/mit-license.html
 *
 * Copyright (c) 2001 Brian Pitcher
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// $Header: /cvs/jso-common/src/easyshop/downloadhelper/HttpPageGetter.java,v 1.2 2007/12/16 03:16:37 Administrator Exp $

package easyshop.downloadhelper;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import es.Constants;
import es.util.http.PageGetter;
import es.util.lang.CharTools;
import es.webref.model.PageRef;
import es.webref.model.WebLink;



public class HttpPageGetter extends PageGetter
{
	static Logger log=Logger.getLogger(HttpPageGetter.class.getName());

    private int failureCount = 0;
    
    private int count=0;
    static CharTools tool=new CharTools();
    public HttpPageGetter(){
    	super();
    }
    
    public static void main(String[] args){
    	HttpPageGetter getter=new HttpPageGetter();
    	HttpClient client=getter.defaultHttpClient();
    	OriHttpPage page=getter.getPost(client,"3745");
    	String context=page.getStringContent();
    	System.out.println(context);
    	HttpPage page2=getter.getWebPage("http://www.netsun.com/member/Action.cgi?t=mjk&id=4234123",client);
    	String c2=page2.getStringContent();
    	System.out.println(c2);
    }
    public HttpPageGetter(String userAgent)
    {
        this.userAgent = userAgent;

    }

    public OriHttpPage getPost(HttpClient client,String code){
        NameValuePair[] data = {
//                new NameValuePair("username", "jan"),
//                new NameValuePair("password", "197675"),
//                new NameValuePair("returl", "http://www.netsun.com/member/Action.cgi?t=mjk&id=4234123"),
//                new NameValuePair("f", "login"),
//                new NameValuePair("v_id", "161073"),
//                new NameValuePair("v_secret", code),
//                new NameValuePair("v_digest", "6bfd3517e5bd86ed1d24520600f62ff4")
              }; 	
        String urlstr="http://www.netsun.com/member/index.cgi";
        
        return getPost(client,urlstr,data);
    }
    public OriHttpPage getPost(String action,NameValuePair[] data){
    	return getPost(this.defaultHttpClient(),action,data);
    }
    
    public OriHttpPage getPost(HttpClient client,String action,NameValuePair[] data){
    	HttpPost post=new HttpPost(action);

        try {
        	
        	HttpResponse rep=client.execute(post);
            HttpEntity repEntity = rep.getEntity();
            String context = EntityUtils.toString(repEntity);
            
        byte[] content = context.getBytes();
//        byte[] content=get.getResponseBody();
        
        
    	ConnResponse conRes=new ConnResponse(null,null,0,0,rep.getStatusLine().getStatusCode());
    	return new OriHttpPage(action,content,conRes,Constants.CHARTSET_DEFAULT);            
        } catch(IOException ioe)
        {
            log.warn("Caught IO Exception: " + ioe.getMessage(), ioe);
            ioe.printStackTrace();
            failureCount++;
            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
            return new OriHttpPage(null,null);
        }
    }
    
    public OriHttpPage getOriHttpPage(PageRef thisRef,HttpClient client){
    	return getOriHttpPage(thisRef,client,null);
    }    
    
    public OriHttpPage getOriHttpPage(PageRef thisRef,HttpClient client,String charSet){
    	HttpPage hpage=getHttpPage(thisRef,client,charSet);
    	return new OriHttpPage(thisRef.getRefId(),thisRef.getUrlStr(), hpage.getContent(),null,hpage.getResponse(),hpage.getCharSet());
    }  
    
    public HttpPage getHttpPageWithDefaultHttpClient(PageRef thisRef){
    	return getHttpPage(thisRef,this.defaultHttpClient(),null);
    }    
    
    public HttpPage getAuthHttpPage(String urStr,String userName,String password){
    	return getAuthWebPage(urStr,this.defaultHttpClient(),userName,password);
    }
    
    public HttpPage getHttpPage(PageRef thisRef,HttpClient client){
    	return getHttpPage(thisRef,client,null);
    }
    
    public HttpPage getHttpPage(PageRef thisRef,HttpClient client,String charSet){
    	String urlStr=thisRef.getUrlStr();

    	HttpGet get =null;
            
        try {
            
            get = new HttpGet(urlStr);
            long startTime = System.currentTimeMillis();
            HttpResponse rep = client.execute(get);
            HttpEntity repEntity = rep.getEntity();
            String context = EntityUtils.toString(repEntity);
            
            if (context.length()>=2024000){
                log.info("content is too large, can't download!");
            	ConnResponse conRes=new ConnResponse(null,null,0,0,0);
                return new OriHttpPage(-1,urlStr, null,null,conRes,null);
            }
                
            byte[] content = context.getBytes();
//            byte[] content=get.getResponseBody();
            
            
            long timeTaken = System.currentTimeMillis() - startTime;
           if(timeTaken < 100) timeTaken = 500;
            int bytesPerSec = (int) ((double) content.length / ((double)timeTaken / 1000.0));
//            log.info("Downloaded " + content.length + " bytes, " + bytesPerSec + " bytes/sec");
//            log.info("urlstr:"+urlStr);
        	ConnResponse conRes=new ConnResponse(null,null,0,0,rep.getStatusLine().getStatusCode());
        	String charset=conRes.getCharSet();
        	if (charset==null){
        		String cc=new String(content);
        		if (cc.indexOf("content=\"text/html; charset=gb2312")>0)
        			charset="gb2312";
        		else if (cc.indexOf("content=\"text/html; charset=utf-8")>0)
        			charset="utf-8";
        		else if (cc.indexOf("content=\"text/html; charset=gbk")>0)
        			charset="gbk";        		
        	}
        	return new HttpPage(urlStr, content,conRes,charset);            
            
        } catch(IOException ioe)
        {
            log.warn("Caught IO Exception: " + ioe.getMessage(), ioe);
            failureCount++;
            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
            return new OriHttpPage(-1,urlStr, null,null,conRes,null);
        }
        catch(Exception e)
        {
            log.warn("Caught Exception: " + e.getMessage(), e);
            failureCount++;
            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
            return new OriHttpPage(-1,urlStr, null,null,conRes,null);
        }  finally {
            get.releaseConnection();
        }/**/        
    }
    
    public OriHttpPage getOriHttpPage(PageRef ref){
    	HttpPage page=getDHttpPage(ref,"utf-8");
    	return new OriHttpPage(ref.getRefId(),page.getUrlStr(),page.getContent(),null,page.getResponse(),page.getCharSet());
    }
    
    public OriHttpPage getOriHttpPage(PageRef ref,String charSet){
    	HttpPage page=getDHttpPage(ref,charSet);
    	return new OriHttpPage(ref.getRefId(),page.getUrlStr(),page.getContent(),null,page.getResponse(),page.getCharSet());
    }    
    
    public HttpPage getURLOnly(WebLink url){
        
        log.debug("getURL(" + url + ")");
        
        if (url.getUrlStr()==null){
        	ConnResponse conRes=new ConnResponse(null,null,0,0,0);
            return new HttpPage(null, null,conRes,null);
        }
        
        URL requestedURL = null;
        URL referer = null;
        try
        {
	        requestedURL = new URL(url.getUrlStr());
           log.debug("Creating HTTP connection to " + requestedURL);
            HttpURLConnection conn = (HttpURLConnection) requestedURL.openConnection();
            if(referer != null)
            {
                log.debug("Setting Referer header to " + referer);
                conn.setRequestProperty("Referer", referer.toExternalForm());
            }

            if(userAgent != null)
            {
                log.debug("Setting User-Agent to " + userAgent);
                conn.setRequestProperty("User-Agent", userAgent);
            }

            conn.setUseCaches(false);

            log.debug("Opening URL");
            long startTime = System.currentTimeMillis();
            conn.connect();

            String resp = conn.getResponseMessage();
            log.debug("Remote server response: " + resp);

            int code=conn.getResponseCode();
            
            if (code!=200){
            	log.error("Could not get connection for code="+code);
            	System.err.println("Could not get connection for code="+code);
            }
        	ConnResponse conRes=new ConnResponse(conn.getContentType(),null,0,0,code);
        	conn.disconnect();
        	return new HttpPage(requestedURL.toExternalForm(), null,conRes,conRes.getCharSet());            
        }
        
        catch(IOException ioe)
        {
            log.warn("Caught IO Exception: " + ioe.getMessage(), ioe);
            failureCount++;
            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
            return new HttpPage(requestedURL.toExternalForm(), null,conRes,null);
        }
        catch(Exception e)
        {
            log.warn("Caught Exception: " + e.getMessage(), e);
            failureCount++;
            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
            return new HttpPage(requestedURL.toExternalForm(), null,conRes,null);
        }        
    }    
    
    public HttpPage getWebPage(String urlStr){
		PageRef ref=new PageRef(urlStr);
		return getDHttpPage(ref,null);
	}

	public HttpPage getWebPageWithHttpClient(String urlStr){
    	return getWebPage(urlStr,defaultHttpClient());
    }
    
    public HttpPage getWebPage(String urlStr,HttpClient client){
    	PageRef ref=new PageRef(urlStr);
    	return getHttpPage(ref,client);
    }
    
    public HttpPage getAuthWebPage(String urlStr,HttpClient client,String userName,String password){
    	PageRef ref=new PageRef(urlStr);
//    	if (client!=null){
//    		client.getParams().setAuthenticationPreemptive(true);
//    		Credentials defaultcreds = new UsernamePasswordCredentials(userName, password);
//    		client.getState().setCredentials(new AuthScope("taobao.com", 80, AuthScope.ANY_REALM), defaultcreds);
//    	}
    	return getHttpPage(ref,client);
    }
    
    public HttpPage getDHttpPage(PageRef url){
        return getDHttpPage(url,"gbk");      
    }

	public HttpPage getDHttpPage(PageRef url,String charSet){
	        count++;
	        log.debug("getURL(" + count + ")");
	
	       
	        if (url.getUrlStr()==null){
	        	ConnResponse conRes=new ConnResponse(null,null,0,0,0);
	            return new OriHttpPage(-1,null, null,null,conRes,null);
	        }
	        URL requestedURL=null;
			try {
				requestedURL = new URL(url.getUrlStr());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				log.error("wrong urlstr"+url.getUrlStr());
	        	ConnResponse conRes=new ConnResponse(null,null,0,0,0);
	            return new OriHttpPage(-1,null, null,null,conRes,null);
			};
	//        System.out.println(""+requestedURL.toExternalForm());
	        URL referer = null;
	        try
	        {
	            log.debug("Creating HTTP connection to " + requestedURL);
	            HttpURLConnection conn = (HttpURLConnection) requestedURL.openConnection();
	            if(referer != null)
	            {
	                log.debug("Setting Referer header to " + referer);
	                conn.setRequestProperty("Referer", referer.toExternalForm());
	            }
	
	            if(userAgent != null)
	            {
	                log.debug("Setting User-Agent to " + userAgent);
	                conn.setRequestProperty("User-Agent", userAgent);
	            }
	
	//            DateFormat dateFormat=DateFormat.getDateInstance();
	//            conn.setRequestProperty("If-Modlfied-Since",dateFormat.parse("2005-08-15 20:18:30").toGMTString());
	            conn.setUseCaches(false);
	            
	//            conn.setRequestProperty("connection","keep-alive");
	            for(Iterator it=conn.getRequestProperties().keySet().iterator(); it.hasNext();)
	            {
	                String key = (String)it.next();
	                if(key == null)
	                {
	                    break;
	                }
	                String value = conn.getHeaderField(key);
	//                System.out.println("Request header " + key + ": " + value);
	            }
	            log.debug("Opening URL");
	            long startTime = System.currentTimeMillis();
	            conn.connect();
	
	            String resp = conn.getResponseMessage();
	            log.debug("Remote server response: " + resp);
	
	            int code=conn.getResponseCode();
	            
	            if (code!=200){
	            	log.error("Could not get connection for code="+code);
	            	System.err.println("Could not get connection for code="+code);
	            	ConnResponse conRes=new ConnResponse(null,null,0,0,code);
	            	return new HttpPage(requestedURL.toExternalForm(), null,conRes,null);
	            }
	            
	//            if (conn.getContentLength()<=0||conn.getContentLength()>10000000){
	//            	log.error("Content length==0");
	//            	System.err.println("Content length==0");
	//            	ConnResponse conRes=new ConnResponse(null,null,null,0,0,-100);
	//            	return new URLObject(-1,requestedURL, null,null,conRes);
	//            }
	            
	            String respStr = conn.getHeaderField(0);
	            long serverDate=conn.getDate();
	//            log.info("Server response: " + respStr);
	
	            for(int i = 1; i<conn.getHeaderFields().size(); i++)
	            {
	                String key = conn.getHeaderFieldKey(i);
	                if(key == null)
	                {
	                    break;
	                }
	                String value = conn.getHeaderField(key);
	//                System.out.println("Received header " + key + ": " + value);
	//               log.debug("Received header " + key + ": " + value);
	            }
	
	//            log.debug("Getting buffered input stream from remote connection");
	            log.debug("start download(" + count + ")");
	            BufferedInputStream remoteBIS = new BufferedInputStream(conn.getInputStream());
	            ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
	            byte[] buf = new byte[1024];
	            int bytesRead = 0;
	            while(bytesRead >= 0)
	            {
	                baos.write(buf, 0, bytesRead);
	                bytesRead = remoteBIS.read(buf);
	            }
	//            baos.write(remoteBIS.read(new byte[conn.getContentLength()]));
	//            remoteBIS.close();
	            byte[] content = baos.toByteArray();
	            long timeTaken = System.currentTimeMillis() - startTime;
	            if(timeTaken < 100) timeTaken = 500;
	
	            int bytesPerSec = (int) ((double) content.length / ((double)timeTaken / 1000.0));
	//            log.info("Downloaded " + content.length + " bytes, " + bytesPerSec + " bytes/sec");
	            if(content.length < conn.getContentLength())
	            {
	                log.warn("Didn't download full content for URL: " + url);
	//                failureCount++;
	            	ConnResponse conRes=new ConnResponse(conn.getContentType(),null,content.length,serverDate,code);
	                return new HttpPage(requestedURL.toExternalForm(), null,conRes,conn.getContentType());
	            }
	            
	            log.debug("download(" + count + ")");
	
	            ConnResponse conRes=new ConnResponse(conn.getContentType(),null,conn.getContentLength(),serverDate,code);
	            String c=charSet;
	            if (c==null)
	            	c=conRes.getCharSet();
	            HttpPage obj=new HttpPage(requestedURL.toExternalForm(),content,conRes,c);
	            return obj;
	        }
	        catch(IOException ioe)
	        {
	            log.warn("Caught IO Exception: " + ioe.getMessage(), ioe);
	            failureCount++;
	            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
	            return new HttpPage(requestedURL.toExternalForm(), null,conRes,null);
	        }
	        catch(Exception e)
	        {
	            log.warn("Caught Exception: " + e.getMessage(), e);
	            failureCount++;
	            ConnResponse conRes=new ConnResponse(null,null,0,0,0);
	            return new HttpPage(requestedURL.toExternalForm(), null,conRes,null);
	        }        
	    }
}
