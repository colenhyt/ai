package cl.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ISBNUtilTest extends TestCase {
	ISBNUtil test=new ISBNUtil();
	public void testTo13() {

		String[] isbn10s={"7807010002","7807014636","7542622242","7506416417","7501222894",
				"7501222924","7538617434","7538617434"};
		String[] isbn13s={"9787807010005","9787807014638","9787542622242"
				,"9787506416412","9787501222896","9787501222926",
				"9787538617436","9787538617436"};
		
		for (int i=0;i<isbn10s.length;i++){
			assertTrue(isbn13s[i].equals(test.to13(isbn10s[i])));
		}
	}

}
