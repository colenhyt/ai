package httptest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import junit.framework.TestCase;

public class HostCacheTest extends TestCase {
	
	static{
    	Properties sys = System.getProperties(); 
//    	sys.put("proxySet","true"); 
//    	
//    	
//    	    	sys.put("proxyHost","192.0.0.88"); 
//    	
//    	
//    	    	sys.put("proxyPort","80"); 
    	
    	
//    	        InetSocketAddress sockAddr= new InetSocketAddress("www.apache.org", 80);
//    	        Socket socket = new Socket(sockAddr);        
//    	        try {
//					socket.connect(sockAddr);
//				} catch (IOException e) {
//					// TODO 自动生成 catch 块
//					e.printStackTrace();
//				}
//    	        sockAddr.getAddress().getHostAddress();
//		
	}
	
	public void testHttpConnection(){
		try {
//			URL url=new URL("http://www.apache.org");//500
			long start=System.currentTimeMillis();
			for (int i=0;i<1000;i++){
				URL url=new URL("http://192.0.2.53:8080/wiki");//500
				HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.connect();
            BufferedInputStream remoteBIS = new BufferedInputStream(conn.getInputStream());
            conn.getInputStream().close();
			conn.disconnect();
			}
			System.out.println("connection time is "+(System.currentTimeMillis()-start));
		} catch (MalformedURLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

}
