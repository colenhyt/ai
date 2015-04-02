package es.util.url;
import junit.framework.TestCase;

import org.apache.log4j.Logger;


/*
 ** JSO1.0, by Allen Huang,2007-5-30
 */
public class URLValidateUtilsTest extends TestCase {
	static Logger log = Logger.getLogger("URLValidateUtilsTest.java");

	public void testAAlike() {
		String s1="http://list.china.alibaba.com/buyer/companylist/10353-p2.html?trade_type=1";
		String s2="http://list.china.alibaba.com/buyer/companylist/10353-p3.html?trade_type=1";
		float score=URLValidateUtils.aAlikeScore(s1, s2);
		System.out.println(score);
		
	}
}

