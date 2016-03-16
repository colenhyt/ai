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
  
   }
}
