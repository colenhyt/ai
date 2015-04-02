/*
 * Created on 2005-6-11
 *
 * XXX To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package estest;
import junit.framework.TestSuite;
import es.util.string.StringHelperTest;
import es.util.url.URLStrHelperTest;


/**
 * @author Administrator
 *
 * XXX To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UnitTests {
	public static TestSuite suit(){
		TestSuite suite=new TestSuite("Web Unit Test");
//		suite.addTestSuite(DataTypeConverterTest.class);
		suite.addTestSuite(URLStrHelperTest.class);
		suite.addTestSuite(StringHelperTest.class);
		return suite;
		
	}
}
