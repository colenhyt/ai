/*
 * Created on 2005-10-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.url;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

/**
 * @author Allenhuang
 * 
 * created on 2005-10-30
 */
public class URLGeneratorTest extends TestCase {

    public void testURL() {
        String parentURL = "http://abc.com/ddd/";
        Set urls = new HashSet();
        urls.add("/www/a.asp");
        urls.add("http://ww.com/www/b.asp");
        urls.add("/ddd/c.asp");

        for (Iterator it = urls.iterator(); it.hasNext();) {
            String urlStr = (String) it.next();
            URL url = null;
            try {
                url = new URL(new URL(null, parentURL), urlStr);
                urlStr = URLStrFormattor.decode(url.toExternalForm());
                System.out.println(urlStr);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void testGenerate() {
        
        //parent 为路径:
        String parentURL = "http://abc.com/ddd";        
        String url;
        String result;
        
        url="a/b/a.jsp";
        result=parentURL+"/"+url;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="../a/b/a.jsp";
        result="http://abc.com"+"/a/b/a.jsp";
        assertEquals(result,URLGenerator.generate(parentURL,url));
     
        
        url="/a/b/a.jsp";
        result="http://abc.com"+url;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="./a/b/a.jsp";
        result=parentURL+"/a/b/a.jsp";
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="?a=b";
        result=parentURL+url;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        
        //parent为无参数文件:
        parentURL = "http://abc.com/ddd/parent.jsp";        
        
        url="a/b/a.jsp";
        result="http://abc.com/ddd/"+url;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="../a/b/a.jsp";
        result="http://abc.com"+"/a/b/a.jsp";
        assertEquals(result,URLGenerator.generate(parentURL,url));
     
        
        url="/a/b/a.jsp";
        result="http://abc.com"+url;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="./a/b/a.jsp";
        result="http://abc.com/ddd/"+"a/b/a.jsp";
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="?a=b";
        result=parentURL+url;
        assertEquals(result,URLGenerator.generate(parentURL,url));       
        
        //parent为有参数文件:
        parentURL = "http://abc.com/ddd/parent.jsp?allen=tracy&a=b";        
        
        url="a/b/a.jsp";
        result="http://abc.com/ddd/"+url;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="../a/b/a.jsp";
        result="http://abc.com"+"/a/b/a.jsp";
        assertEquals(result,URLGenerator.generate(parentURL,url));
     
        
        url="/a/b/a.jsp";
        result="http://abc.com"+url;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="./a/b/a.jsp";
        result="http://abc.com/ddd/"+"a/b/a.jsp";
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="?a=b";
        result=parentURL+url;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="?";
        result=parentURL;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="#abcde";
        result=parentURL;
        assertEquals(result,URLGenerator.generate(parentURL,url));
        
        url="/";
        result=parentURL;
        assertEquals(result,URLGenerator.generate(parentURL,url));
         }

}