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

// $Header: /cvs/jso-common/src/easyshop/downloadhelper/URLGetterForBOL.java,v 1.1 2007/10/25 09:29:50 Administrator Exp $

package easyshop.downloadhelper;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;


public class URLGetterForBOL
{
    public static String HTTP_USER_AGENT="Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)";
	static Logger log=Logger.getLogger(URLGetterForBOL.class.getName());

    private int failureCount = 0;
    
    private String userAgent;

    public URLGetterForBOL(){
        this.userAgent=HTTP_USER_AGENT;
    }
    
    public URLGetterForBOL(String userAgent)
    {
        this.userAgent = userAgent;

    }

    public OriHttpPage getURL(URLToDownload url){
        log.debug("getURL(" + url + ")");

        if(failureCount > 10)
        {
            log.warn("Lots of failures recently, waiting 5 seconds before attempting download");
            try { Thread.sleep(5 * 1000); } catch(InterruptedException e) { };
            failureCount = 0;
        }

        URL requestedURL = url.getURL();
        URL referer = url.getReferer();

        try
        {
            log.debug("Creating HTTP connection to " + requestedURL);
            HttpURLConnection conn = (HttpURLConnection) requestedURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("ContentType","text/html");
//            conn.setRequestProperty("Accept","text/html; */*");
//            conn.setRequestProperty("Host","www.bolchina.com");
//            conn.setRequestProperty("Referer","http://www.bol.com.cn/cgi-bin/bol/bol/B-1.jsp?BV_UseBVCookie=yes");
            if(referer != null)
            {
                log.debug("Setting Referer header to " + referer);
                conn.setRequestProperty("Referer", referer.toExternalForm());
            }

            if(HTTP_USER_AGENT != null)
            {
                log.debug("Setting User-Agent to " + HTTP_USER_AGENT);
                conn.setRequestProperty("User-Agent", HTTP_USER_AGENT);
            }

            conn.setUseCaches(false);

            log.debug("Opening URL");
            long startTime = System.currentTimeMillis();
            conn.connect();

            String resp = conn.getResponseMessage();
            log.debug("Remote server response: " + resp);

            String respStr = conn.getHeaderField(0);
            log.info("Server response: " + respStr);

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
            return new OriHttpPage(-1,requestedURL.toExternalForm(), content,null,null,conn.getContentType());
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
    }
}
