package easyshop.helper;
import org.apache.log4j.Logger;

import es.util.pattern.ESPattern;
import es.util.string.StringHelper;



/*
 ** JSO1.0, by Allen Huang,2007-5-28
 */
public class GetCJKValueHelper {
	static Logger log = Logger.getLogger("GetValueHelper.java");
	public final static char[] PRES={':','：'};
	public final static String[] KEYWORDS_EMAIL={"邮箱"};
	public final static String[] KEYWORDS_TEL={"联系电话","电话","联系方式"};
	public final static String[] KEYWORDS_POSTCODE={"邮编","邮政编号"};
	public final static String[] KEYWORDS_FAX={"传真"};
	public final static String[] KEYWORDS_MOBILE={"手机"};
	
	public final static String PATTERN_EMAIL="^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+[.]([a-zA-Z0-9_-])+";
	public final static String PATTERN_TEL_ALL="([+]{0,1}[\\d]{2,3}[-\\s]{0,1}){0,1}[\\d]{2,4}[-\\s][\\d]{7,11}";
	public final static String PATTERN_TEL_SINGLE="d{7,8}";
	public final static String PATTERN_MOBILE="(13[\\d]{9})|(15[89]{1}[\\d]{8})";
	public final static String PATTERN_POSTCODE="[\\d]{6}";
	
	/*
	 * 取得紧跟在name后面的字符串,如果该字符串不符合pattern,则返回null:
	 * 比如: "木材加工, 石门门窗制作厂 电话:68887827 湖南省衡阳市 木材加工.石门门窗",
	 * 输入电话pattern,并且name="电话",则返回68887827
	 * 取字符串规则: 碰到第一个空格前的非PRES非空格字符串,PRES左右的空格也省略
	 * 字符集:暂为gb2312
	 */	
	public String getValue(String context,String name,String pattern){
		String value=getValue(context,name);
		if (value!=null&&ESPattern.matches(pattern,value))
			return value;
		return null;
		
	}
	
	public String findTelStr(String context){
		for (int i=0;i<KEYWORDS_TEL.length;i++){
			String result=getValue(context,KEYWORDS_TEL[i],PATTERN_TEL_ALL);
			if (result!=null)
				return result;
		}
		
		for (int i=0;i<KEYWORDS_TEL.length;i++){
			String result=getValue(context,KEYWORDS_TEL[i],PATTERN_TEL_SINGLE);
			if (result!=null)
				return result;
		}		
		
		//有些电话，区号与电话之间用空格隔开:
		String cityPattern="\\d{3,4}";
		String cityNum=null,subContext=null;
		for (int i=0;i<KEYWORDS_TEL.length;i++){
			String result=getValue(context,KEYWORDS_TEL[i],cityPattern);
			if (result!=null){
				cityNum=result;
				int k=context.indexOf(KEYWORDS_TEL[i]);
				int n=context.indexOf(result, k);
				if (n>0)
					subContext=context.substring(n+result.length());
				
				break;
			}
		}	
		if (subContext!=null){//寻找剩下部分电话号码:
			String tel=getValue(subContext,PATTERN_TEL_SINGLE);
			if (tel!=null&&cityNum!=null)
				return cityNum+"-"+tel;
		}
		
		return null;
	}

	public String findFaxStr(String context){
		for (int i=0;i<KEYWORDS_FAX.length;i++){
			String result=getValue(context,KEYWORDS_FAX[i],PATTERN_TEL_ALL);
			if (result!=null)
				return result;
		}
		
		for (int i=0;i<KEYWORDS_FAX.length;i++){
			String result=getValue(context,KEYWORDS_FAX[i],PATTERN_TEL_SINGLE);
			if (result!=null)
				return result;
		}		
		
		//有些电话，区号与电话之间用空格隔开:
		String cityPattern="\\d{3,4}";
		String cityNum=null,subContext=null;
		for (int i=0;i<KEYWORDS_FAX.length;i++){
			String result=getValue(context,KEYWORDS_FAX[i],cityPattern);
			if (result!=null){
				cityNum=result;
				int k=context.indexOf(KEYWORDS_FAX[i]);
				int n=context.indexOf(result, k);
				if (n>0)
					subContext=context.substring(n+result.length());
				
				break;
			}
		}	
		if (subContext!=null){//寻找剩下部分电话号码:
			String tel=getValue(subContext,PATTERN_TEL_SINGLE);
			if (tel!=null&&cityNum!=null)
				return cityNum+"-"+tel;
		}
		
		return null;
	}
	
	public String findTelStr(String[] contexts){
		String tel=null;
		for (int i=0;i<contexts.length;i++){
			tel=findTelStr(contexts[i]);
			if (tel!=null)break;
		}
		return tel;
	}
	
	public String findFaxStr(String[] contexts){
		String tel=null;
		for (int i=0;i<contexts.length;i++){
			tel=findFaxStr(contexts[i]);
			if (tel!=null)break;
		}
		return tel;
	}
	
	public String findEmailStr(String context){
		for (int i=0;i<KEYWORDS_EMAIL.length;i++){
			String result=getValue(context,KEYWORDS_EMAIL[i],PATTERN_EMAIL);
			if (result!=null)
				return result;
		}
		return null;
	}
	
	public String findPostCodeStr(String context){
		for (int i=0;i<KEYWORDS_POSTCODE.length;i++){
			String result=getValue(context,KEYWORDS_POSTCODE[i],PATTERN_POSTCODE);
			if (result!=null)
				return result;
		}
		return null;
	}
	
	public String findMobileStr(String context){
		for (int i=0;i<KEYWORDS_MOBILE.length;i++){
			String result=getValue(context,KEYWORDS_MOBILE[i],PATTERN_MOBILE);
			if (result!=null)
				return result;
		}
		
		for (int i=0;i<KEYWORDS_TEL.length;i++){
			String result=getValue(context,KEYWORDS_TEL[i],PATTERN_MOBILE);
			if (result!=null)
				return result;
		}		
		return null;
	}	
	
	public static final char WHITESPACE=' ';
	public String getValue(String context,String name){
		if (context.indexOf(name)>=0){
			String subCont=context.substring(name.length()).trim();
			char[] chars=subCont.toCharArray();
			int start=-1;
			for (int i=0;i<chars.length;i++){
				//找到开始位置:
				if (chars[i]!=WHITESPACE&&!StringHelper.inChars(chars[i],PRES)){
					start=i;
					break;
				}
			}
			
			if(start>=0){
				int end=subCont.indexOf(start,WHITESPACE);
				if (start<end)
					return subCont.substring(start,end);
				else
					return subCont.substring(start);
			}
		}
		
		return null;
		
	}	
}

