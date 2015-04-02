package test;

import java.util.regex.Pattern;

public class TestRegExp extends TestRegular {
	
	public void testJDKRegExp(){       
//		p1: 10k:125;100k:500
//		p2:10k:500;100k:3980;1m:36593
        long start=System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
//        	Pattern.matches(p1, key);
        	Pattern.matches(p2, url);
        }
        System.out.println("cost time is "+(System.currentTimeMillis()-start));
		
	}


}
