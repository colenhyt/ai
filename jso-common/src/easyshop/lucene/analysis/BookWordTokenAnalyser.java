/*
 * 创建日期 2006-8-31
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package easyshop.lucene.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import es.util.UnicodeConverter;


/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-31
 */
public class BookWordTokenAnalyser {

	  /** An array containing some common English words that are not usually useful 
	    for searching. */ 
	  //可以在此扩展English stop words和Chinese stop words 
	  public static final String[] ILLEGAL_WORDS = { 
	    "'"
	  }; 

	  /** Builds an analyzer which removes words in ENGLISH_STOP_WORDS. */ 
	  public BookWordTokenAnalyser() { 
	  } 

	  public void splitString(String input,Set results){
	  	input=UnicodeConverter.SBCchange(input.toLowerCase());
	  	BookWordTokenizer tokener=new BookWordTokenizer();
	  	input=BookWordChaFilter.replaceChars(input,new String[]{"<<","《"},"<");
	  	input=BookWordChaFilter.replaceChars(input,new String[]{">>","》"},">");
	  	tokener.spiltString(input,results);
	  }
	  public void tokenString(String input,Set results){
	  	//预处理:转为小写,将全角转为半角:
	  	input=UnicodeConverter.SBCchange(input.toLowerCase());
	  	//filter:
	  	input=BookWordChaFilter.replaceChars(input,ILLEGAL_WORDS,"");
	  	input=BookWordChaFilter.replaceChars(input,new String[]{"<<","《"},"<");
	  	input=BookWordChaFilter.replaceChars(input,new String[]{">>","》"},">");
	  	BookWordTokenizer tokener=new BookWordTokenizer();
	  	//按照分段标志拆开:
	  	Set splits=new HashSet();
	  	tokener.spiltString(input,splits);
	  	
	  	//last step: token string:
	  	for (Iterator it=splits.iterator();it.hasNext();){
	  		String word=(String)it.next();
	  		results.addAll(SplitWords.getAllWords(word));
	  	}
	  }

}
