package cl.util;

import junit.framework.TestCase;
import es.util.lang.CharTools;

public class HexStringConverter extends TestCase {
	
	public void testConvert(){
		String a="电话";
		CharTools cT=new CharTools();
		System.out.println(cT.Utf8URLencode(a));
	}

}
