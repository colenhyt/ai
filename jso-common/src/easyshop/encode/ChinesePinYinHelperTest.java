package easyshop.encode;

import junit.framework.TestCase;

public class ChinesePinYinHelperTest extends TestCase {

	public void testGetFirstLetter() {
		ChinesePinYinHelper test=new ChinesePinYinHelper();
		System.out.println(test.getFirstLetter("黄颖天"));
		System.out.println(test.getFirstLetter("abc黄颖天"));
		System.out.println(test.getFirstLetter("2006黄颖天"));
	}

}
