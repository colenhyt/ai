package box.main;

import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import box.util.DateHelper;
import box.util.IPageDealing;
import box.weixin.WxPageDealing;
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
		int t = 190;
		t = t%10;
		IPageDealing dealing = new WxPageDealing();
		
		//Date dd = DateHelper.formatDate("3", "23", "19:10:10");
		SitesContainer con = new SitesContainer(a,dealing);
		con.runningPages();
	}

}
