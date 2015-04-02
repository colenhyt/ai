/*
 * 创建日期 2006-8-12
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package es.util.url;

import junit.framework.TestCase;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-12
 */
public class URLConstructorTest extends TestCase {

	public void testParse() {
		String url;
		url="http://www.dangdang.com/product/9128/9128203.shtml";
		assertTrue(URLConstructor.parse(url).size()==4);

		url="http://www.dangdang.com/book.shtml";
		assertTrue(URLConstructor.parse(url).size()==2);

		url="http://www.dearbook.com.cn/book/SearchBook.aspx";
		assertTrue(URLConstructor.parse(url).size()==3);

		url="http://www.dearbook.com.cn";
		assertTrue(URLConstructor.parse(url).size()==1);

		url="http://www.dearbook.com.cn/";
		assertTrue(URLConstructor.parse(url).size()==1);

		url="http://28842185.mall.dangdang.com/products/G00000021053.html";
		assertTrue(URLConstructor.parse(url).size()==3);

		url="http://51anycall.mall.dangdang.com/aboutus.html";
		assertTrue(URLConstructor.parse(url).size()==2);

		url="https://51anycall.mall.dangdang.com/";
		assertTrue(URLConstructor.parse(url)==null);
	}

}
