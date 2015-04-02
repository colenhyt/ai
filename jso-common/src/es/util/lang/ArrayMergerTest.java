package es.util.lang;

import junit.framework.TestCase;

public class ArrayMergerTest extends TestCase {

	public void testLong(){
		ArrayMerger test=new ArrayMerger();
		long[] a1={1,2,3,4};
		long[] a2={2,3,5,6};
		assertTrue(test.mergeLong(a1, a2).length==6);

		long[] a3={1,2,3,4};
		assertTrue(test.mergeLong(a3, null).length==4);

		assertTrue(test.mergeLong(null, null).length==0);
	}
}
