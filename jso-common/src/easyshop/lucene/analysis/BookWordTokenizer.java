/*
 * 创建日期 2006-9-5
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package easyshop.lucene.analysis;

import java.util.List;
import java.util.Set;

import cl.xjt.nlp.word.SplitWord;
import es.util.string.StringHelper;


/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-9-5
 */
public class BookWordTokenizer {

	public void tokenAllString(String inputString,Set words) {
	  	for (int i=0;i<inputString.length();i++){
	  		if (i==inputString.length()-1)
	  			break;
	  		
	  		char c=inputString.charAt(i);
	  		
	  		int j=i+1;
			int k=i+1;
			//获取整个拉丁文字符串,拉丁文字符串是不需要拆分的:
	  		if (Character.isLowerCase(c)){
	  			for (;k<inputString.length();k++){
	  				if (!Character.isLowerCase(inputString.charAt(k)))
	  						break;
	  			}
		  		//k就是最后一个字符,并且是Letter
	  			if (k==inputString.length()&&Character.isLowerCase(inputString.charAt(k=1))){
	  				if (inputString.substring(i)!=null)
	  				words.add(inputString.substring(i).trim());
	  				break;
	  			}
	  			else
	  				j=k+1;
	  		}else if (Character.isDigit(c)){
	  			for (;k<inputString.length();k++){
	  				if (!Character.isDigit(inputString.charAt(k)))
	  						break;
	  			}
		  		//k就是最后一个字符index,并且是数字
	  			if (k==inputString.length()&&Character.isDigit(inputString.charAt(k-1))){
	  				if (inputString.substring(i)!=null)
	  				words.add(inputString.substring(i).trim());
	  				break;
	  			}
	  			else
	  				j=k+1;
	  		}
	  		
	  		for (;j<=inputString.length();j++){
	  			if (j==inputString.length()){
	  				if (inputString.substring(i,j)!=null)
		  			words.add(inputString.substring(i,j).trim());
		  			break;
	  			}
	  				
		  		char cj=inputString.charAt(j);
		  		
		  		int kj=j+1;
		  		if (Character.isLowerCase(cj)){
		  			for (;kj<inputString.length();kj++){
		  				if (!Character.isLowerCase(inputString.charAt(kj)))
		  						break;
		  			}
			  		//k就是最后一个字符,并且是Letter
		  			if (kj==inputString.length()&&Character.isLowerCase(inputString.charAt(kj-1))){
		  				if (inputString.substring(i)!=null)
		  				words.add(inputString.substring(i).trim());
		  				break;
		  			}
		  			else
		  				j=kj+1;
		  		}else if (Character.isDigit(cj)){
		  			for (;kj<inputString.length();kj++){
		  				if (!Character.isDigit(inputString.charAt(kj)))
		  						break;
		  			}
			  		//k就是最后一个字符,并且是数字
		  			if (kj==inputString.length()&&Character.isDigit(inputString.charAt(kj-1))){
		  				if (inputString.substring(i)!=null)
		  				words.add(inputString.substring(i).trim());
		  				break;
		  			}
		  			else
		  				j=kj+1;
		  		}
		  		if (j==i+1)
		  			continue;
		  		if (j<0)
		  			System.out.println(inputString);
		  		if (0<i&&i<j&&j<inputString.length())
	  				if (inputString.substring(i,j)!=null)
	  			words.add(inputString.substring(i,j).trim());
	  		}
	  	}
	  }

	  String[] separate_Regs={"　"," ",",",";","-","(",")","—",":","、",
	  		"“","”","!","*","&","/"," ","[","]","<",">","·","?",".","\\"};
	public void tokenString(String inputString,Set words) {
	  	int i=0;
	  	for (;i<separate_Regs.length;){
	  		int index=inputString.indexOf(separate_Regs[i]);
	  		if (index>0){
	  			String subW=inputString.substring(0,index);
	  			if (StringHelper.contains1atLeast(subW,separate_Regs))
	  				tokenString(subW,words);
	  			else
	  				words.add(subW);
	  			inputString=inputString.substring(index);
	  			i=0;
	  		}else if(index==0){
	  			inputString=inputString.substring(1);
	  			i=0;	  			
	  		}else
	  			i++;
	  	}
	  	if (inputString.length()>0&&!StringHelper.contains1atLeast(inputString,separate_Regs))
	  		words.add(inputString);
	  }

	public void spiltString(String inputString,Set splitWords){
	  	int i=0;
	  	for (;i<separate_Regs.length;){
	  		int index=inputString.indexOf(separate_Regs[i]);
	  		if (index>0){
	  			//Latin字母关联:
	  			if (Character.isSpace(separate_Regs[i].charAt(0))&&index>=(inputString.length()+1)){
	  				char c=inputString.charAt(index-1);
	  				char c2=inputString.charAt(index+1);
					if (Character.isLowerCase(c)&&Character.isLowerCase(c2)){
						i++;
						continue;
					}
	  			}
	  			String subW=inputString.substring(0,index);
	  			if (subW!=null){
	  			if (StringHelper.contains1atLeast(subW,separate_Regs))
	  				spiltString(subW.trim(),splitWords);
	  			else 
	  				splitWords.add(subW.trim());
	  			}
	  			inputString=inputString.substring(index);
	  			i=0;
	  		}else if(index==0){
	  			inputString=inputString.substring(1);
	  			i=0;	  			
	  		}else
	  			i++;
	  	}
	  	if (inputString.length()>0&&!StringHelper.contains1atLeast(inputString,separate_Regs))
	  		splitWords.add(inputString.trim());
		
	}
	
	/** Filters LowerCaseTokenizer with StopFilter. */ 
	  public List tokenStringByICTCLAS(String inputString) { 
	  	SplitWord splitWord = new SplitWord(); ; 
	     return splitWord.splitWord(inputString).getSentence(); 
	  }
	  
	  public static void main(String[] args){
//	  	BookWordTokenizer ana=new BookWordTokenizer();
//		String a="园林与庭院设计——全国高职高专艺术设计类专业规划教材（附cd-rom光盘一张）";
//		List stream=ana.tokenStringByICTCLAS(a);
//			for (Iterator it=stream.iterator();it.hasNext();) {
//				Word word=(Word)it.next();
//			System.out.println(""+word.getWord());
//			}
			
	  	int i=0;
			for (;i<5;i++){
				System.out.println(""+i);
			}
			System.out.println(""+i);
			
	  }
}
