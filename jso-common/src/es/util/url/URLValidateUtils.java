package es.util.url;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import es.util.string.StringHelper;



/*
 ** JSO1.0, by Allen Huang,2007-5-30
 */
public class URLValidateUtils {
	static Logger log = Logger.getLogger("URLValidateUtils.java");
	
	//url前缀完全相等,文件类型相同, 文件名中含有相同的数字:
	public static boolean sameDigitsAndPreAndType(String urlStr1,String urlStr2){
			if (samePreAndType(urlStr1,urlStr2)){//前缀完全相同,文件类型完全相同
				String name1=URLStrHelper.getFileName(urlStr1);
				String name2=URLStrHelper.getFileName(urlStr2);
				String key1=String.valueOf(StringHelper.getFirstDigitStr(name1));
				String key2=String.valueOf(StringHelper.getFirstDigitStr(name2));
				if ((key1!=null&&name2.indexOf(key1)>=0)||(key2!=null&&name1.indexOf(key2)>=0))
					return true;
				}
			return false;
	}
	
	public static boolean samePreAndType(String urlStr1,String urlStr2){
		String type1=URLStrHelper.getFilePostfix(urlStr1);
		String type2=URLStrHelper.getFilePostfix(urlStr2);
		String pre1=URLStrHelper.getURLDir(urlStr1.trim());
		String pre2=URLStrHelper.getURLDir(urlStr2.trim());
		return (pre1!=null&&pre2!=null&&pre1.equalsIgnoreCase(pre2)&&type1.equals(type2));
	}
	
	//url前缀完全相等,文件类型相同, 其中一个文件名完全包含另外一个文件名:
	public static boolean sameKeysAndPreAndType(String urlStr1,String urlStr2){
			if (samePreAndType(urlStr1,urlStr2)){//前缀完全相同,文件类型完全相同
				String name1=URLStrHelper.getFileName(urlStr1);
				String name2=URLStrHelper.getFileName(urlStr2);
				if (name1!=null&&name2!=null&&(name1.indexOf(name2)>=0||name2.indexOf(name1)>=0))
					return true;
				}
			return false;
	}
	
	/*文件名字符的相似百分比:
	 * 相似比较规则:取文件名长度短的一方,逐个字符比较,总相同字符数量除以文件名长的一方的length
	 * 另外，字符串太短时，比较结果误差就比较大,所以太短的不进行比较,同时,两个长度相差太远的比较也没有意义
	 * 这里注意,不能够单独用这个方法判断两个urlstr的相似性,否则会带来极大的误差
	 */
	public static float sNameSamePercent(String urlStr1,String urlStr2){
			String name1=URLStrHelper.getFileName(urlStr1.trim());
			String name2=URLStrHelper.getFileName(urlStr2.trim());
			if (name1.length()>0&&name2.length()>0){
			String bigger,little;
			if (name1.length()>=name2.length()){
				bigger=name1;
				little=name2;
			}else{
				bigger=name2;
				little=name1;
			}
			
			//就以5作为分界点:
			if (little.length()<5||(bigger.length()-little.length())>5)
				return 0;
			
			char[] lcs=little.toCharArray();
			char[] bcs=bigger.toCharArray();
			float equalCount=0;
			for (int i=0;i<lcs.length;i++){
				if (lcs[i]==bcs[i])
					equalCount++;
			}
			
				return (equalCount/new Float(bcs.length).floatValue())*100;
			}
		return 0;
	}
	
	//90分以上可以算alike
	public static boolean aAlike(String urlStr1,String urlStr2){
		if (aAlikeScore(urlStr1,urlStr2)>=90)
			return true;
		else
			return false;
	}
	
	//相似度得分:
	public static float aAlikeScore(String urlStr1,String urlStr2){
		Map p1=URLStrHelper.getParams(urlStr1);
		Map p2=URLStrHelper.getParams(urlStr2);
		String name1=URLStrHelper.getFullFileName(urlStr1);
		String name2=URLStrHelper.getFullFileName(urlStr2);
		if (samePreAndType(urlStr1,urlStr2)){//只有前缀和文件类型一样时,才有比较的意义:
		//有参数时,除了比较参数的异同外，还需要比较文件名(包括类型)是否相同:
		if (p1.size()>0|p2.size()>0){
			if (name1.equalsIgnoreCase(name2)&&sameParaNamesAndDiffValue(p1,p2))
					return 98;//连名字都相同，真的没话说了！
			else if (sameParaNamesAndValues(p1,p2)){//还有一种情况:参数名和值完全相同,文件(包括类型)相似度高
				if (sNameSamePercent(urlStr1,urlStr2)>=80)
					return 90;
			}
		}else{//url没有参数，只比较文件的相似百分比,这个分数需要不断训练才能更逼真:
			return sNameSamePercent(urlStr1,urlStr2);
			
		}
		}
		return 0;
	}
	
	//	都有url参数,对于参数少的一方,其参数名与多的一方完全相同,并且,至少有一个参数值是不同的:
	public static boolean sameParaNamesAndDiffValue(String urlStr1, String urlStr2){
    	Map oriParams=URLStrHelper.getParams(urlStr1);
    	Map refParams=URLStrHelper.getParams(urlStr2);		
		return sameParaNamesAndDiffValue(oriParams,refParams);
	}
	
	//都有url参数,对于参数少的一方,其参数名与多的一方完全相同,并且,全部的参数值都是相同的:
	public static boolean sameParaNamesAndValues(Map oriParams, Map refParams){
    	if (oriParams!=null&&refParams!=null&&oriParams.size()>0&&refParams.size()>0){
        	Map bmap,smap;
    		if (oriParams.size()>refParams.size()){
    			bmap=oriParams;
    			smap=refParams;
    		}else {
    			bmap=refParams;
    			smap=oriParams;
    		}
    		
    		boolean same=true;
    		
    		for (Iterator it=smap.keySet().iterator();it.hasNext();){
    			String name=(String)it.next();
    			String value=(String)smap.get(name);
    			if (!bmap.containsKey(name)||!value.equalsIgnoreCase((String)bmap.get(name))){
    				same=false;
    				break;
    			}
    		}
   		
    		return same;
    		
    	}
    	return false;
	}

	//都有url参数,对于参数少的一方,其参数名与多的一方完全相同,并且,至少有一个参数值是不同的:
	public static boolean sameParaNamesAndDiffValue(Map oriParams, Map refParams){
		if (oriParams!=null&&refParams!=null&&oriParams.size()>0&&refParams.size()>0){
	    	Map bmap,smap;
			if (oriParams.size()>refParams.size()){
				bmap=oriParams;
				smap=refParams;
			}else {
				bmap=refParams;
				smap=oriParams;
			}
			
			boolean sameParams=true;
			
			for (Iterator it=smap.keySet().iterator();it.hasNext();){
				String name=(String)it.next();
				String value=(String)smap.get(name);
				if (!bmap.containsKey(name)){
					sameParams=false;
					break;
				}
			}
			
			boolean oneDiffValue=false;
			
			if (sameParams){
			for (Iterator it=smap.keySet().iterator();it.hasNext();){
				String name=(String)it.next();
				String value=(String)smap.get(name);
				if (!value.equalsIgnoreCase((String)bmap.get(name))){
					oneDiffValue=true;
					break;
				}
			}    		
			}
			
			return sameParams&&oneDiffValue;
			
		}
		return false;
	}
}

