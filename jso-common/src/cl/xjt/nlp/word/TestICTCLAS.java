/*
 * 创建日期 2006-8-30
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package cl.xjt.nlp.word;

import junit.framework.TestCase;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-30
 */
public class TestICTCLAS extends TestCase {
	ICTCLAS test=new ICTCLAS();
	
	public void testFenCi(){
		String a="社会哲学：现代实践哲学视野中的社会生活——哲学理论创新丛书";
		String b=null;
		try {
			b = test.paragraphProcess(a);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		System.out.println(""+b);
	}

}
