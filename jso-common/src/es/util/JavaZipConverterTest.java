package es.util;

import junit.framework.TestCase;

public class JavaZipConverterTest extends TestCase {
	JavaZipConverter test=new JavaZipConverter();
	public void testCompress() {
		String bids="123456;789011;182736;789011;182736;789011;182736;789011;182736;789011;182736;789011;182736;789011;182736;789011;182736;789011;182736;789011;182736";
		System.out.println(bids.length());
		byte[] ids=test.compress(bids.getBytes());
		String ides=new String(ids);
		System.out.println(ides.length());
		byte[] tt=test.decompress(ids);
		String bides2=new String(tt);
		System.out.println(bides2);
		
	}

}
