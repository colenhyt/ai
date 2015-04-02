package es.util.pattern;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import easyshop.helper.GetCJKValueHelper;



public class ESPattern {
	final static String PATTERN_ISBN10="[\\d]{9}[a-zA-Z0-9]{1}" ;
	final static String PATTERN_ISBN13="[\\d]{13}" ;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a="1-2838-3818-2";
		System.out.println(ESPattern.isISBN(a));
		System.out.println(a);
	}	
	public static boolean isISRC(String input){
		return true;	
	}
	
	public static boolean isISBN(String input){
		if (input==null||input.length()<=0)
			return false;
		input=input.trim().replace("-", "");
		return OROMatches(PATTERN_ISBN10,input)||OROMatches(PATTERN_ISBN13,input);		
	}
	public static boolean matches(String regex, String input){
		if (input==null||input.length()<=0)
			return false;
		return OROMatches(regex,input);
	}
	
	
	public static boolean isTelephone(String str){
		return matches(GetCJKValueHelper.PATTERN_TEL_ALL,str);
	}
	
	public static boolean isMobile(String str){
		return matches(GetCJKValueHelper.PATTERN_MOBILE,str);
	}
	
	public static boolean isFax(String str){
		return matches(GetCJKValueHelper.PATTERN_TEL_ALL,str);
	}
	
	public static boolean isMail(String str){
		return matches(GetCJKValueHelper.PATTERN_EMAIL,str);
	}
	
	public static boolean matches1atLst(String[] regexs, String input){
		for (int i=0;i<regexs.length;i++){
			if (OROMatches(regexs[i],input))
				return true;
		}
		return false;
	}
	
	public static boolean isNumber(String input){
		return matches("[\\d]*",input);
	}
	
	public static boolean isDecimal(String input){
		return matches("[\\d]*[.]*[\\d]*",input);
	}
	
	private static boolean OROMatches(String regex,String input){
		boolean b=false;
        Perl5Compiler compiler=new Perl5Compiler();
        Perl5Matcher matcher=new Perl5Matcher();
        try {
			Pattern pattern=compiler.compile(regex);
	        	b=matcher.matches(input,pattern);
		} catch (MalformedPatternException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}		
		return b;		
	}

}
