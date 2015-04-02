package easyshop.lucene.analysis;

import junit.framework.TestCase;

public class BookWordChaFilterTest extends TestCase {
	BookWordChaFilter test=new BookWordChaFilter();
	public void testReviseWordsByKeys() {
		String aa="〔法〕蒙田著";
		String[] bb=new String[]{"著","cc","aa"};
		assertEquals(test.reviseWordByKeys(aa,bb),"〔法〕蒙田");
		
		aa="bcc";
		assertEquals(test.reviseWordByKeys(aa,bb),"b");
	}

}
