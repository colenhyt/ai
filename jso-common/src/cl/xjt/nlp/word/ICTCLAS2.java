/*
 * 创建日期 2006-9-2
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package cl.xjt.nlp.word;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-9-2
 */
public class ICTCLAS2 {
	  public ICTCLAS2() {
	    init(0,2);
	  }
	  public synchronized native boolean init(int i, int j);
	  public synchronized native String paragraphProcess(String sParagraph);
	  public synchronized native boolean fileProcess(String source,String target);

	  static{
	    System.loadLibrary("ICTCLAS");
	  }
	  
	  public static void main(String[] args){
	  	ICTCLAS2 test=new ICTCLAS2();
	  	String ss="";
	  	
	  	
	  }
}
