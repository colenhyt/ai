//$Id: AllTests.java,v 1.1 2007/10/25 09:29:48 Administrator Exp $
package estest;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;


/**
 * @author Gavin King
 */
public class AllTests {
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(UnitTests.suit());
		return suite;
	}



	public static void main(String args[]) {
		TestRunner.run( suite() );
	}

}