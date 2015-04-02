/*
 * Created on 2005-6-25
 *
 * XXX To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.string;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import es.Constants;
import es.util.html.HTMLContentHelper;


/**
 * @author Administrator
 *
 * XXX To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StringHelper {
	
	
	//字符串中至少包含一个list中的词:
	public static boolean contains1atLeast(String str,List<String> strs){
		String[] ss=strs.toArray(new String[strs.size()]);
		return contains1atLeast(str,ss);		
	}	
	
	//字符串中至少包含一个数组中的词:
	public static boolean contains1atLeast(String str,String[] strs){
		for (int i=0;i<strs.length;i++){
			if (str.indexOf(strs[i])>=0) 
				return true;
		}
		return false;		
	}
	
	//全部包含list中的词
	public static boolean containsAll(String str,List<String> strs){
		String[] ss=strs.toArray(new String[strs.size()]);
		return containsAll(str,ss);
	}	
	
	//全部包含数组中的词
	public static boolean containsAll(String str,String[] strs){
		for (int i=0;i<strs.length;i++){
			if (str.indexOf(strs[i])<0) 
				return false;
		}
		return true;		
	}	
	
	//全部包含数组中的词,不管大小写
	public static boolean containsAllIgnoreCase(String str,String[] strs){
		str=str.toLowerCase();
		for (int i=0;i<strs.length;i++){
			strs[i]=strs[i].toLowerCase();
			if (str.indexOf(strs[i])<0) 
				return false;
		}
		return true;		
	}	
	
	//是否数字
	public static boolean isNumber(String str)
	{
		return Pattern.matches("[\\d]*", str);
	}
	
	//全部包含数组中的词, 至少一个全保护
	public static boolean containsAll1AtLst(String str,Set arrays){
		for (Iterator it=arrays.iterator();it.hasNext();){
			String[] strs=(String[])it.next();
			if (containsAll(str,strs)) 
				return true;
		}
		return false;		
	}	
	
	public static boolean containsAll1AtLstIgnoreCase(String str,Set arrays){
		for (Iterator it=arrays.iterator();it.hasNext();){
			String[] strs=(String[])it.next();
			if (containsAllIgnoreCase(str,strs)) 
				return true;
		}
		return false;		
	}	
	//返回字符串中至少包含一个数组中的那个词:
	public static String findContainsStr(String str,String[] strs){
		for (int i=0;i<strs.length;i++){
			if (str.indexOf(strs[i])>=0) 
				return strs[i];
		}
		return null;		
	}

	//返回字符串中至少包含一个数组中的那个词:
	public static String findContainsStrFF(String str,String[] strs){
		for (int i=0;i<strs.length;i++){
			if (strs[i].indexOf(str)>=0) 
				return strs[i];
		}
		return null;		
	}
	
	//返回字符串中至少包含一个数组中的那个词:
	public static String findContainsStrFF(String str,List<String> list){
		return findContainsStrFF(str,list.toArray(new String[list.size()]));		
	}
	
	public static boolean contains1atLeast(String str,char[] strs){
		for (int i=0;i<strs.length;i++){
			if (str.indexOf(strs[i])>=0) 
				return true;
		}
		return false;		
	}	
	
	public static boolean inChars(char c,char[] chars){
		for (int i=0;i<chars.length;i++){
			if (chars[i]==c) 
				return true;
		}
		return false;			
	}
	
	public static boolean match1atLeast(String str,String[] pats){
		for (int i=0;i<pats.length;i++){
			if (Pattern.matches(pats[i],str)) 
				return true;
		}
		return false;			    
	}
	public static boolean equals1atLeast(String str,String[] strs){
		for (int i=0;i<strs.length;i++){
			if (str.equalsIgnoreCase(strs[i])) 
				return true;
		}
		return false;		
	}	
	
	public static boolean start1atLeast(String str,String[] strs){
		for (int i=0;i<strs.length;i++){
			if (str.startsWith(strs[i])) 
				return true;
		}	    
		
		return false;
	}
	
	public static String findString(String context,String preStr,String afterStr){
		int start=context.indexOf(preStr);
		int end=-1;
		if (start>=0){
		end=context.indexOf(afterStr,start+1);
		if (end>start){
		String cc=context.substring(start+preStr.length(), end);
		cc=HTMLContentHelper.getPureText(cc);
		if (cc!=null&&cc.length()>0)
			return cc;
		}
		}
		return context;
	}
	public static String findString(String context,String[] pres,String[] afters){
		int start=-1,end=-1;
		if (pres!=null&&pres.length>0){
			String preStr=null;
			for (int i=0;i<pres.length;i++){
				start=context.indexOf(pres[i]);
				if (start>=0){
					preStr=pres[i];
					break;
				}
			}
			
		if (start>=0){
			if (afters!=null&&afters.length>0){
			for (int i=0;i<afters.length;i++){
				end=context.indexOf(afters[i],start);
				if (end>=0){
					break;
				}
			}		
			}else
				end=context.length();
		}
		if (end>start)
			return context.substring(start+preStr.length(),end);
		}
		return null;
	}
	
	public static boolean end1atLeast(String str,String[] strs){
		for (int i=0;i<strs.length;i++){
			if (str.endsWith(strs[i])) 
				return true;
		}	    
		
		return false;
	}

	public static boolean end1atLeast(String str,List<String> list){
		String[] strs=list.toArray(new String[list.size()]);
		return end1atLeast(str,strs);
	}
	
	public static String findCommonStr(String str1,String str2){
	    if (str1==null||str2==null)
	        return null;
	    
	    if (str1.indexOf(str2)>=0)
	        return str2;
	    if (str2.indexOf(str1)>=0)
	        return str1;
	    
	    char[] char1=str1.toCharArray();
	    int len=0;
	    for (int i=0;i<char1.length;i++){
	        char c=str2.charAt(i);
	        if (c!=char1[i]){
	            len=i;
	            break;
	        }
	    }
	    
	    if (len>0)
	        return str1.substring(0,len);
	    return null;
	}
	
    
	//翻转字符串:
    public static String reverseString(String oStr){
        char[] o=oStr.toCharArray();
        char[] newo=new char[o.length];
        for (int i=0;i<o.length;i++){
            newo[i]=o[o.length-i-1];
        }
        return new String(newo);
    }
    
    
    public static String getFirstDigitStr(String str){
        char[] o=str.toCharArray();
        int index=0;
        for (int i=0;i<o.length;i++){
            if (Character.isDigit(o[i]))
            	continue;
            else {
            	index=i;
            	break;
            }
        }    
        if (index>0)
        	return str.substring(0,index);
        else
        	return null;
    }
	public static String getUnicodeStr(String str){
	    try {
	        String charset=System.getProperty("file.encoding");
            return new String(str.getBytes(charset),Constants.CHARTSET_UTF8);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
	}
	
	public static String removeStrs(String str,String[] removed){
		for (int i=0;i<removed.length;i++)
			str=str.replace(removed[i], "");
		return str;
	}
	public static String findNextDigitStr(String context){
		int startIndex=-1;
		char[] chars=context.toCharArray();
		for (int i=0;i<chars.length;i++){
			char c=chars[i];
			if (Character.isDigit(c)){
				startIndex=i;
				break;
			}
		}
		int endIndex=-1;
		if (startIndex>=0){
			for (int i=startIndex;i<chars.length;i++){
				char c=chars[i];
				if (!Character.isDigit(c)){
					endIndex=i;
					break;
				}
			}			
		}
		
		if (startIndex>=0&&endIndex>0&&startIndex<endIndex)
			return context.substring(startIndex,endIndex);
		return null;
	}	
	public static String findTeleNumber(String context){
		int startIndex=-1;
		char[] chars=context.toCharArray();
		for (int i=0;i<chars.length;i++){
			char c=chars[i];
			if (Character.isDigit(c)){
				startIndex=i;
				break;
			}
		}
		int endIndex=-1;
		if (startIndex>=0){
			for (int i=startIndex;i<chars.length;i++){
				char c=chars[i];
				if (!Character.isDigit(c)&&!String.valueOf(c).equalsIgnoreCase("-")){
					endIndex=i;
					break;
				}
			}			
		}
		
		if (startIndex>=0&&endIndex>0&&startIndex<endIndex)
			return context.substring(startIndex,endIndex);
		return null;
	}
    public static String textReplace(String find, String replace, String input)
    {
        int startPos = 0;
        while(true)
        {
            int textPos = input.indexOf(find, startPos);
            if(textPos < 0)
            {
                break;
            }
            input = input.substring(0, textPos) + replace + input.substring(textPos + find.length());
            startPos = textPos + replace.length();
        }
        return input;
    }	

}
