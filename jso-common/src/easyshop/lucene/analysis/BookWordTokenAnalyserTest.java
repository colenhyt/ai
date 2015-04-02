/*
 * 创建日期 2006-9-2
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package easyshop.lucene.analysis;

import java.util.Iterator;
import java.util.TreeSet;

import junit.framework.TestCase;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-9-2
 */
public class BookWordTokenAnalyserTest extends TestCase {
	BookWordTokenAnalyser test=new BookWordTokenAnalyser();
	public void testTokenString() {
		BookWordChaFilter token=new BookWordChaFilter();
		String a="王贵成  主编";
		TreeSet b=new TreeSet();
//		test.tokenString(a,b);
//		for (Iterator it=b.iterator();it.hasNext();){
//			System.out.println(it.next());
//		}
//		a="沈一向，沈超英  编译";
//		b.clear();
//		test.tokenString(a,b);
//		for (Iterator it=b.iterator();it.hasNext();){
//			System.out.println(it.next());
//		}			
		a="迟子建论,方守金  主编";
		b.clear();
		test.tokenString(a,b);
		String[] stops={"主编"};
		token.removeWords(b,stops);
		for (Iterator it=b.iterator();it.hasNext();){
			System.out.println(it.next());
		}			
	}
	
	public void testToeknAllString(){
		String a="“崩溃的逻辑”的历史建构：阿多诺早中期哲学思想的文本学解读";
		TreeSet b=new TreeSet();
		test.tokenString(a,b);
		for (Iterator it=b.iterator();it.hasNext();){
			System.out.println(it.next());
		}			
		
		a="　7　中文版完全自学手册";
		System.out.println(a.indexOf("　"));
		b=new TreeSet();
		test.tokenString(a,b);
		for (Iterator it=b.iterator();it.hasNext();){
			System.out.println(it.next());
		}		
		
		a=".ab类/最新版国家公务员录用考试专用";
		b=new TreeSet();
		test.tokenString(a,b);
		for (Iterator it=b.iterator();it.hasNext();){
			System.out.println(it.next());
		}		
		
		a=".80元《职场百宝囊》一";
		System.out.println(a.indexOf("《"));
		b=new TreeSet();
		test.tokenString(a,b);
		for (Iterator it=b.iterator();it.hasNext();){
			System.out.println(it.next());
		}		
		
		a="·布局·陈列技巧·pop·最强有力的营销";
		b=new TreeSet();
		test.tokenString(a,b);
		for (Iterator it=b.iterator();it.hasNext();){
			System.out.println(it.next());
		}		
		
		a="/面向21世";
		b=new TreeSet();
		test.tokenString(a,b);
		for (Iterator it=b.iterator();it.hasNext();){
			System.out.println(it.next());
		}		
		
		a="·布局·陈列技巧·pop·最强有力的营销";
		b=new TreeSet();
		test.tokenString(a,b);
		for (Iterator it=b.iterator();it.hasNext();){
			System.out.println(it.next());
		}		
		
		a="·布局·陈列技巧·pop·最强有力的营销";
		b=new TreeSet();
		test.tokenString(a,b);
		for (Iterator it=b.iterator();it.hasNext();){
			System.out.println(it.next());
		}		
	}

}
