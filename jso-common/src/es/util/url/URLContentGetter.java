/*
 * Created on 2006-3-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.url;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;


/**
 * Tracy&Allen.EasyShop 0.9
 *
 * @author Allenhuang
 * 
 * created on 2006-3-20
 */
public class URLContentGetter {
	static Logger log=Logger.getLogger(URLContentGetter.class.getName());
    private int failureCount = 0;
    
    public byte[] getContent(URL url){
        return getContent(url,null);
    }
    public byte[] getContent(URL url, URL referer){
        return getContent(url,referer,null);
    }
    
    public byte[] getContent(URL url, URL refer,String userAgent){
        if(failureCount > 10)
        {
            log.warn("Lots of failures recently, waiting 5 seconds before attempting download");
            try { Thread.sleep(5 * 1000); } catch(InterruptedException e) {
                };
            failureCount = 0;
        }
        
        if (url==null)
            return null;
        
        URL requestedURL = url;
        URL referer = refer;
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

            conn.setUseCaches(false);

            log.debug("Opening URL");
            long startTime = System.currentTimeMillis();
            conn.connect();

            String resp = conn.getResponseMessage();
            log.debug("Remote server response: " + resp);

            String respStr = conn.getHeaderField(0);
//            log.info("Server response: " + respStr);

            for(int i = 1; ; i++)
            {
                String key = conn.getHeaderFieldKey(i);
                if(key == null)
                {
                    break;
                }
                String value = conn.getHeaderField(key);
                log.debug("Received header " + key + ": " + value);
            }

            log.debug("Getting buffered input stream from remote connection");
            BufferedInputStream remoteBIS = new BufferedInputStream(conn.getInputStream());
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
            long timeTaken = System.currentTimeMillis() - startTime;
            if(timeTaken < 100) timeTaken = 500;

            int bytesPerSec = (int) ((double) content.length / ((double)timeTaken / 1000.0));
            log.info("Downloaded " + content.length + " bytes, " + bytesPerSec + " bytes/sec");
            if(content.length < conn.getContentLength())
            {
                log.warn("Didn't download full content for URL: " + url);
                failureCount++;
                return null;
            }
            String type=conn.getContentType();        
            return content;
        }
	catch(FileNotFoundException fnfe) {
	    log.warn("File not found: " + fnfe.getMessage());
	    return null;
	}
        catch(IOException ioe)
        {
            log.warn("Caught IO Exception: " + ioe.getMessage(), ioe);
            failureCount++;
            return null;
        }
        catch(Exception e)
        {
            log.warn("Caught Exception: " + e.getMessage(), e);
            failureCount++;
            return null;
        }    }

}
