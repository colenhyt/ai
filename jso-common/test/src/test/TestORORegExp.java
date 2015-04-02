package test;


//import gnu.regexp.RE;
//import gnu.regexp.REException;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

public class TestORORegExp extends TestRegular {
	
	public void testORO(){
		/*
		 * //p1: 10k:47;100k:156
		 * p2:10k:290;100k:2450;1m:24031
		 */
        Perl5Compiler compiler=new Perl5Compiler();
        Perl5Matcher matcher=new Perl5Matcher();
        try {
			Pattern pattern=compiler.compile(p2);
	        long start=System.currentTimeMillis();
	        for (int i=0;i<1000000;i++){
//	        	boolean b=matcher.matches(key,pattern);
	        	boolean b=matcher.matches(url,pattern);
	        }
	        System.out.println("cost time is "+(System.currentTimeMillis()-start));
		} catch (MalformedPatternException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		
		
	}
//	public void testGNU(){
//        try {
//            RE compiler=new RE(p2);
//	        long start=System.currentTimeMillis();
//	        for (int i=0;i<10000;i++){//before:547 ;after:4781
//	        	compiler.isMatch(url);
//	        }
//	        System.out.println("cost time is "+(System.currentTimeMillis()-start));
//		} catch (REException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//		
//		
//	}
//	public void testReg(){
//		/*
//		 * p1:10k:47;100k:235
//		 * p2:10k:365;100K:3300;1m:32656
//		 */
//            RE compiler=new RE(p2);
//	        long start=System.currentTimeMillis();
//	        for (int i=0;i<1000000;i++){//47
//	        	compiler.match(url);
//	        }
//	        System.out.println("cost time is "+(System.currentTimeMillis()-start));
//		
//		
//	}	
}
