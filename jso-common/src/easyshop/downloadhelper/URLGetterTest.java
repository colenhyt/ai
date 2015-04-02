/*
 * Created on 2005-10-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.downloadhelper;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.PropertyConfigurator;

import easyshop.log.LogPropertiesHelper;
import es.Constants;


/**
 * @author Allenhuang
 *
 * created on 2005-10-21
 */
public class URLGetterTest extends TestCase {
    
    public URLGetterTest() {
        super();
        PropertyConfigurator.configure(LogPropertiesHelper
                .getConfigProperties(Constants.LOG_FILE));
    }    

    public void wtestGetURL2(){
        URLGetterForBOL test=new URLGetterForBOL(HttpPageGetter.HTTP_USER_AGENT);
        URLToDownload page=null;
        try {
            page = new URLToDownload(new URL("http://www.bol.com.cn/cgi-bin/bol/bol/A-5.jsp?BV_UseBVCookie=yes&okxu=touts&linkname=book_slot1_3&OID=954677"),0);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        OriHttpPage a=test.getURL(page);
        
        if (a==null)
            System.out.println("return is null");
        System.out.println("it did after exception thrown!");
        
    }
    
    public static void main(String[] args) {
        HttpClient client;
        client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
       // client.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)");  //让服务器认为是IE

        GetMethod get = new GetMethod("http://www.bol.com.cn/cgi-bin/bol/bol/A-5.jsp?BV_UseBVCookie=yes&okxu=touts&linkname=book_slot1_3&OID=954677");
        
//                    get.setFollowRedirects(false); //禁止自动重定�?
            
        try {
            int iGetResultCode = client.executeMethod(get);
            System.out.println(get.getResponseCharSet());
//            String context=new String(get.getResponseBody(),Constants.CHARTSET_DEFAULT);
//            System.out.println(context);
            String type=get.getResponseHeader("Content-type").getValue();
            Header[] headers=get.getResponseHeaders();
            for (int i=0;i<headers.length;i++){
                System.out.println(headers[i].getValue());
            }
//            System.out.println("====" + get.getResponseHeader("location").getValue()); //打印地址
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            get.releaseConnection();
        }/**/
   }
}
