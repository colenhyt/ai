package httptest;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.log4j.Logger;


/*
 ** JSO1.0, by Allen Huang,2007-5-25
 */
public class URLTest extends TestCase{
	static Logger log = Logger.getLogger("URLTest.java");
	
	public void test1(){
		String url="http://list.b2b.hc360.com/companytrade/002/001.html";
		try {
			URL u=new URL(url);
			URL u2=new URL(u,"/companytrade/002/001001.html");
			System.out.println(u2.toExternalForm());
		} catch (MalformedURLException e) {
			// log error here
			log.error(e.getMessage());
		}
	}
}

