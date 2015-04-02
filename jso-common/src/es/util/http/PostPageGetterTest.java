package es.util.http;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import easyshop.downloadhelper.HttpPage;
import easyshop.log.LogPropertiesHelper;
import es.Constants;



/*
 ** JSO1.0, by Allen Huang,2007-6-13
 */
public class PostPageGetterTest extends TestCase {
	static {
        PropertyConfigurator.configure(LogPropertiesHelper
                .getConfigProperties(Constants.LOG_FILE));		
	}
	static Logger log = Logger.getLogger("PostPageGetterTest.java");
	PostPageGetter getter=new PostPageGetter();
	public void testGetHttpPagePageRefHttpClientMap() {
		Map params=new HashMap();
		params.put("f", "D9_5_1");
		params.put("commend", "all");
		params.put("prop", "");
		params.put("ppath", "");
		params.put("promote", "");
		params.put("_promote", "");
		params.put("isnew", "");
		params.put("user_action", "initiative");
		params.put("at_topsearch", "1");
		params.put("search_type", "auction");
		params.put("q", "autocad");
		HttpPage p=getter.getHttpPage("http://search1.taobao.com/browse/search_auction.htm?at_topsearch=1", params);
		System.out.println(p.getStringContent());
	}
	
    private String getTaoBaoUrl(String bname){
    	return "http://search1.taobao.com/browse/search_auction.htm?at_topsearch=1&search_type=auction&q="+bname+"&cat=33";
    	
    }	
}

