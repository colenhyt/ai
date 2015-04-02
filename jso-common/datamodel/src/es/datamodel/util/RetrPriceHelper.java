/*
 * Created on 2005-6-25
 *
 * XXX To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.datamodel.util;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import easyshop.html.jericho.EndTag;
import easyshop.html.jericho.Source;
import easyshop.html.jericho.StartTag;


/**
 * @author Administrator
 * 
 * XXX To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RetrPriceHelper {
	static Logger log=Logger.getLogger(RetrPriceHelper.class.getName());
    private static final String PS_CLASS = "class";

    private static final String PS_KEY = "price";

    public static final String CHAR_MONEYCHAR = "￥";

    public static final String CHAR_YUAN = "元";

    private static final String[] dummyStrs = { "size=" };
  
    
    //取得从某个字符开始,某个字符结束, 在两个标签之间存在字符串并且该字符串是数字,则认为该字符串是价格
    public static float getPriceBet2Tags(Source source, int beginIndex,int endIndex) throws IllegalPosException{
        if (beginIndex<0)
            return 0;
        
        String content = source.toString();
        float ps=0;
            int startPos = beginIndex;
            int endPos=source.length();
            if(endIndex>0){
                if (endIndex>startPos)
                    endPos=endIndex;
                else{
                    new IllegalPosException("endIndex is illegal for it is:"+endIndex+", but startPos is:"+startPos);
                    return 0;
                }
            }

            //从startPos开始, 找到下一个在两个标签之间的非空格非PS_KEY2的字符,不能够在任何标签的里面,
           //如果下一个的在两个标签的字符既非空格也非PS_KEY2，则停止搜索,无法找到这个价格,
           String innerText;//两个标签之间的字符串
            for (int i = startPos; i <endPos ; i++) {
                //找从i开始的第一个标签的结束位置
                EndTag preTag0=source.findNextEndTag(i);                
                StartTag preTag1=source.findNextStartTag(i);                
                int sPos,ePos0;//寻找下一个标签的开始位置,
                if (preTag0.getEnd()<preTag1.getEnd()){//结束标签在开始标签后面,
                    sPos=preTag0.getEnd();
                    ePos0=preTag0.getBegin();
            }
                else {
                    sPos=preTag1.getEnd();
                    ePos0=preTag1.getBegin();
                }
                
                //第1个tag跟startIndex有字符串, 则价格可能在这个字符串中:
                if (ePos0-i>0){
                    String  tt=content.substring(i,ePos0).trim().replaceAll(CHAR_YUAN,"");
                    ps=retrNumeric(tt);
                    if (ps>0)
                    	break;
                	
                }
                //找从sPos开始的第一个标签的开始位置
                EndTag endTag0=source.findNextEndTag(sPos);                
                StartTag endTag1=source.findNextStartTag(sPos);                
                int ePos;//位置值,
                if (endTag0.getBegin()<endTag1.getBegin())//结束标签在开始标签后面,
                    ePos=endTag0.getBegin();
                else
                    ePos=endTag1.getBegin();
                
                if (ePos>sPos+1){//它们之间有字符串
                    String aa=content.substring(sPos);
                    innerText=content.substring(sPos,ePos);
                    if (innerText.trim().length()>0){
                        ps=retrNumeric(innerText.trim());
                        break;//无论找到的是否有值,只有找到了就退出
                    }
                }else{//跳到当前的前面那个标签，重新开始:
                    i=sPos-1;
                }

            }
        return ps;
    }

    public static float retrNumeric(String str){
        str=str.trim().replaceAll("￥","");
        str=str.trim().replaceAll(",","");
        float digit=0;
        int start=0,end=0;
        for (int i=0;i<str.length();i++){
            char c=str.charAt(i);
            if (Character.isDigit(c)) {
                start=i;
                	//i后面存在非数字字符串:
                    for (int j = i + 1; j < str.length(); j++) {
                        char c2=str.charAt(j);
                        if (Character.toString(c2).equals(".")){
                            if ((j+1)<=str.length()&&!Character.isDigit(str.charAt(j+1))){
                                end=-1;
                                break;
                            }else
                                continue;
                        }
                        if (!Character.isDigit(c2)) {
                            end = j;
                            break;
                        }
                    }
                    //整个字符串都是数字字符:
                    if (end==0){
                        end=str.length();
                        break;
                    }
                    break;
            }            
        }
        if (end>start&&start>=0){
        String word = str.substring(start, end);
        if (word.trim().length() > 0){
            try{
                digit=Float.parseFloat(word);
            }catch (NumberFormatException e){
                e.printStackTrace();
                digit=0;
            }
        }     
        }
        return digit;
    }
    
    public static float findPrice(Source source,int preIndex,int endIndex){
        if (preIndex<=0)
            return 0;
        
        String content=source.toString();
        String str=RetrPriceHelper.CHAR_MONEYCHAR;
        int moneyCharIndex=-1;//找到在preIndex之后的moneyChar的位置;
                    moneyCharIndex=content.indexOf(str,preIndex);
                    if (moneyCharIndex>endIndex)
                    	moneyCharIndex=-1;

                    return getPrice(source,moneyCharIndex,preIndex,endIndex);
    }
    
    public static float findPrice(String content,String preStr,String endStr){
        if (content==null||preStr==null)
            return 0;
        
        Source s=new Source(content.toLowerCase());
        String str=RetrPriceHelper.CHAR_MONEYCHAR;
        int preIndex=-1;//存在的当前字符串的位置
        int moneyCharIndex=-1;//找到在preIndex之后的moneyChar的位置;
            int in=content.indexOf(preStr);
                if (in>0){
                    preIndex=in+preStr.length();
                    moneyCharIndex=content.indexOf(str,preIndex);
                }
                int endIndex=-1;
                if (endStr!=null&&content.indexOf(endStr)>0)
                    endIndex=content.indexOf(endStr);
                return getPrice(s,moneyCharIndex,preIndex,endIndex);
    }
    
    private static float getPrice(Source source, int moneyCharIndex,int preIndex,int endIndex){
        float ps=0;
        //两种开始索引(preIndex和moneyCharIndex)与两种形态(紧跟在字符串后面和在两个tag之间)的两两配对, 
        if (moneyCharIndex>0)
            ps=getPriceByPreString(source,moneyCharIndex);//从moneyChar开始找
        if (preIndex>0)
            ps=getPriceByPreString(source,preIndex);//从moneyChar开始找
        if (ps>0)
        	return ps;
        
			try {
		        if (moneyCharIndex>0)
		        	ps=getPriceBet2Tags(source,moneyCharIndex,endIndex);//从moneyChar后的两个tag之间找            
		        if (preIndex>0)
		            ps=getPriceBet2Tags(source,preIndex,endIndex);//从没有moneyChar的两个tag之间找
			} catch (IllegalPosException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
            
        return ps;          
        
    }
    //综合条件搜索:
    public static float findPrice(Source source,String[] preStrs){
        String content=source.toString();
        String str=RetrPriceHelper.CHAR_MONEYCHAR;
        int preIndex=-1;//存在的当前字符串的位置
        int moneyCharIndex=-1;//找到在preIndex之后的moneyChar的位置;
        for (int i = 0; i < preStrs.length; i++) {
            int in=content.indexOf(preStrs[i]);
                if (in>0){
                    preIndex=in+preStrs[i].length();
                    moneyCharIndex=content.indexOf(str,preIndex);
                break;
            }
        }
        
        return getPrice(source,moneyCharIndex,preIndex,-1);
        
    }
    
    public static float findPriceByClass(Source source, String tagName,String propValue) {
        List list = source.findAllStartTags(tagName);
        String value = null;
        for (Iterator it=list.iterator();it.hasNext();) {
            StartTag tag = (StartTag) it.next();
            value = tag.getAttributes().getValue(PS_CLASS);
            if (value != null && value.equalsIgnoreCase(propValue)) {
                String text = tag.getElement().getContentText();
                if (text.startsWith(CHAR_MONEYCHAR) && text.endsWith(CHAR_YUAN))
                    return retrNumeric(text.substring(CHAR_MONEYCHAR.length(),text.indexOf(CHAR_YUAN)));
            }
        }
        return 0;
    }

    //该方法取得紧跟在某个字符串后面的价格,字符串与价格字符串两者之间没有人员其他字符，特别是HTML标签,如果是空格,需要过滤掉:
    public static float getPriceByPreString(String content,int preStrIndex){
        if (preStrIndex<0)
            return 0;
        
        Source source=new Source(content);
        float ps=0;
            int startPos = preStrIndex;

            String innerText;//两个标签之间的字符串
             //取得从startPos开始的第一个标签:
                //找下一个结束标签以及其位置
                EndTag next0=source.findNextEndTag(startPos);
                int tagB0=next0.getBegin();
                
                StartTag next1=source.findNextStartTag(startPos);
                int tagB1=next1.getBegin();
                
                if(next0.getName().equalsIgnoreCase(next1.getName())&&tagB0>tagB1){//刚好是一对结对标签:
                    innerText=content.substring(tagB1,tagB0);
                    ps=retrNumeric(innerText);                       	
                }else {
                int thisPos;
                if (tagB0<tagB1)//结束标签在开始标签前面,
                    thisPos=tagB0;
                else
                    thisPos=tagB1;
                
                if ((thisPos-startPos)>0) {//从startPos开始到第一个标签之间有字符串
                    innerText=content.substring(startPos,thisPos);
                    ps=retrNumeric(innerText);                        
                }
                }

        return ps;        
    }

	//该方法取得紧跟在某个字符串后面的价格,字符串与价格字符串两者之间没有人员其他字符，特别是HTML标签,如果是空格,需要过滤掉:
	public static float getPriceByPreString(Source source,int preStrIndex){
	    if (preStrIndex<0)
	        return 0;
	    
	    String content = source.toString();
	    float ps=0;
	        int startPos = preStrIndex;
	
	        String innerText;//两个标签之间的字符串
	         //取得从startPos开始的第一个标签:
	            //找下一个结束标签以及其位置
	            EndTag next0=source.findNextEndTag(startPos);
	            int tagB0=next0.getBegin();
	            
	            StartTag next1=source.findNextStartTag(startPos);
	            int tagB1=next1.getBegin();
	            
	            if(next0.getName().equalsIgnoreCase(next1.getName())&&tagB0>tagB1){//刚好是一对结对标签:
	                innerText=content.substring(tagB1,tagB0);
	                ps=retrNumeric(innerText);                       	
	            }else {
	            int thisPos;
	            if (tagB0<tagB1)//结束标签在开始标签前面,
	                thisPos=tagB0;
	            else
	                thisPos=tagB1;
	            
	            if ((thisPos-startPos)>0) {//从startPos开始到第一个标签之间有字符串
	                innerText=content.substring(startPos,thisPos);
	                ps=retrNumeric(innerText);                        
	            }
	            }
	
	    return ps;        
	}

}