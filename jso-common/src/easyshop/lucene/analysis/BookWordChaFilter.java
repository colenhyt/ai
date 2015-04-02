/*
 * 创建日期 2006-9-4
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package easyshop.lucene.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-9-4
 */
public class BookWordChaFilter {
	
	public static String replaceChars(String input,String[] chars,String newChar){
		
		for (int i=0;i<chars.length;i++){
			if (input.indexOf(chars[i])>=0)
				input=input.replaceAll(chars[i],newChar);
		}
		return input;
		
	}

	  /** Filters LowerCaseTokenizer with StopFilter. */ 
	  public void removeWords(Set words,String[] stopWs){
	  		for (int i=0;i<stopWs.length;i++){
	  			if (words.contains(stopWs[i]))
	  				words.remove(stopWs[i]);
	  		}
	  }	
	  
	  public Set reviseWordsByKeys(Set words,String[] keys){
		  Set newWords=new HashSet();
			for(Iterator it=words.iterator();it.hasNext();){
  				String word=(String)it.next();
				newWords.add(reviseWordByKeys(word,keys));
	  		}
	  		return newWords;
	  }
	  
	  public Set reviseWordsByLen(Set words,int len){
		  Set newWords=new HashSet();
			for(Iterator it=words.iterator();it.hasNext();){
  				String word=(String)it.next();
				if (word.trim().length()>len)
					newWords.add(word);
	  		}
	  		return newWords;
	  }
	  
	public String reviseWordByKeys(String word,String[] keys){
			for (int i=0;i<keys.length;i++){
  				if (word.endsWith(keys[i]))
  					word=word.substring(0,word.length()-keys[i].length());
  			}		
			return word;
	}
	
	public static void main(String[] args){
		String[] a={","};
		String input=BookWordChaFilter.replaceChars(",a,b,,cd,e,fg,,",a,"");
		System.out.println(input);
	}

}
