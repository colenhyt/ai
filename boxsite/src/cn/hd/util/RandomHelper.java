package cn.hd.util;

public class RandomHelper {
	public static String lowerChars = "abcdefghijklmnopqrstuvwxyz";
	public static String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String numberChars = "0123456789";
	public static String specialChars = "`~!@#$%^&*()';:][/.,><?";
	
	public static String randomChars(int lowerCount,int upperCount,int numCount,int specialCount){
		String str = "";
		for (int i=0;i<lowerCount;i++){
		char a = lowerChars.charAt((int)(Math.random() * lowerChars.length()));
			str += a;
		}
		for (int i=0;i<upperCount;i++){
		char a = upperChars.charAt((int)(Math.random() * upperChars.length()));
			str += a;
		}		
		for (int i=0;i<numCount;i++){
		char a = numberChars.charAt((int)(Math.random() * numberChars.length()));
			str += a;
		}	
		for (int i=0;i<specialCount;i++){
		char a = specialChars.charAt((int)(Math.random() * specialChars.length()));
			str += a;
		}		
		System.out.println(str);
		return str;
	}
	
	public static void main(String[] args) {
		RandomHelper.randomChars(3,2,11,0);
		// TODO Auto-generated method stub

	}

}
