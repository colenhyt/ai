package box.main;

import java.util.Set;

import org.apache.log4j.Logger;

import es.download.finditemurls.StraightUrlStrsFinder;
import es.download.helper.OriginalsHelper;

public class Main {
    static Logger log = Logger.getLogger(StraightUrlStrsFinder.class
            .getName());

     OriginalsHelper helper=new OriginalsHelper();
    private int perCount=0, fullActiveCount=5000,threadCount=0;
    
	public void processSites(Set<String> startUrls)
	{

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = new int[1]; 
		String[] sites= new String[1];
		sites[0]= "jd";
//		sites[1]= "http://www.t.com";
		SitesContainer con = new SitesContainer(a,sites);
		con.runningPages();
	}

}
