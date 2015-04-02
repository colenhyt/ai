package cl.util;

import es.util.pattern.ESPattern;

public class ISBNUtil {
	final static String PATTERN_ISBN10="[\\d]{9}[a-zA-Z0-9]{1}" ;
	final static String PATTERN_ISBN13="[\\d]{13}" ;
	private final int i13=9;
	private final int i12=7;
	private final int i11=8;

	/*
	 * function get13isbn(isbnid) ''由10位ISBN获知13位ISBN
if len(isbnid)=10 then ''若原ISBN为10位，则谋求转换
i13=9
i12=7
i11=8
i10=mid(isbnid,1,1)
i9=mid(isbnid,2,1)
i8=mid(isbnid,3,1)
i7=mid(isbnid,4,1)
i6=mid(isbnid,5,1)
i5=mid(isbnid,6,1)
i4=mid(isbnid,7,1)
i3=mid(isbnid,8,1)
i2=mid(isbnid,9,1)
thissum=i13+i11+i9+i7+i5+i3+(i12+i10+i8+i6+i4+i2)*3
thissum=i10*10+i9*9+i8*8+i7*7+i6*6+i5*5+i4*4+i3*3+i2*2
i1=10- thissum mod 10
get13isbn=i13&i12&i11&i10&i9&i8&i7&i6&i5&i4&i3&i2&i1
else
get13isbn=isbnid
end if
end function
	 */
	public String to13(String isbn10){
		if (!isISBN10(isbn10))
			return "-1";
		
		isbn10=isbn10.trim().replace("-", "");
		StringBuffer buffer=new StringBuffer();
		buffer.append(i13);
		buffer.append(i12);
		buffer.append(i11);
		buffer.append(isbn10.substring(0,9));
		return buffer.toString()+getCheckDigit13(buffer.toString());
	}

	public String to10(String isbn13){
		if (!isISBN13(isbn13))
			return "-1";
		
		isbn13=isbn13.trim().replace("-", "");
		StringBuffer buffer=new StringBuffer();
		buffer.append(isbn13.substring(3,12));
		return buffer.append(getCheckDigit10(buffer.toString())).toString();
	}	
	public boolean isISBN10(String string){
		if (string==null||string.trim().length()<=0)
			return false;
		
		string=string.replace("-", "");
		if (!ESPattern.matches(PATTERN_ISBN10, string))
			return false;
		String i1=string.substring(9);
		return i1.equalsIgnoreCase(getCheckDigit10(string));
	}
	
	public boolean isISBN(String string){
		return isISBN10(string)||isISBN13(string);
	}
	
	public boolean isISBN13(String string){
		if (string==null||string.trim().length()<=0)
			return false;
		
		string=string.replace("-", "");
		if (!ESPattern.matches(PATTERN_ISBN13, string))
			return false;
		String i1=string.substring(12);
		return i1.equals(getCheckDigit13(string));
	}	
	
	public String getCheckDigit10(String isbn10){
		int i10=new Integer(isbn10.substring(0,1));
		int i9=new Integer(isbn10.substring(1,2));
		int i8=new Integer(isbn10.substring(2,3));
		int i7=new Integer(isbn10.substring(3,4));
		int i6=new Integer(isbn10.substring(4,5));
		int i5=new Integer(isbn10.substring(5,6));
		int i4=new Integer(isbn10.substring(6,7));
		int i3=new Integer(isbn10.substring(7,8));
		int i2=new Integer(isbn10.substring(8,9));
		
		int total=i10*10+i9*9+i8*8+i7*7+i6*6+i5*5+i4*4+i3*3+i2*2;
		int i1= 11-total%11;
		if (i1==10)
			return "x";
		else if (i1==11)
			return "0";
		else
			return String.valueOf(i1);
	}
	public String getCheckDigit13(String isbn13){
		int i10=new Integer(isbn13.substring(3,4));
		int i9=new Integer(isbn13.substring(4,5));
		int i8=new Integer(isbn13.substring(5,6));
		int i7=new Integer(isbn13.substring(6,7));
		int i6=new Integer(isbn13.substring(7,8));
		int i5=new Integer(isbn13.substring(8,9));
		int i4=new Integer(isbn13.substring(9,10));
		int i3=new Integer(isbn13.substring(10,11));
		int i2=new Integer(isbn13.substring(11));
		
		int total=i13+i11+i9+i7+i5+i3+(i12+i10+i8+i6+i4+i2)*3;
		int i1=10-total%10;
		if (i1==10)
			return "0";
		else
			return String.valueOf(i1);
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ISBNUtil converter=new ISBNUtil();

		System.out.println(converter.getCheckDigit13(""));
		
	}

}
