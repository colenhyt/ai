/*
 * 创建日期 2006-8-14
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package easyshop.downloadhelper;

import junit.framework.TestCase;
import es.Constants;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-14
 */
public class HttpConnectionHelperTest extends TestCase {

	public void testGetCharSet() {
		String  a="text/html;CharSet=gbk";
		assertEquals(Constants.CHARTSET_DEFAULT,HttpConnectionHelper.getCharSet(a));
		
		a="text/html;CharSet=gbk   ";
		assertEquals(Constants.CHARTSET_DEFAULT,HttpConnectionHelper.getCharSet(a));		
		
		a="text/html; charset=gb2312";
		assertEquals(Constants.CHARTSET_DEFAULT,HttpConnectionHelper.getCharSet(a));		
		
		a="text/html;ChardSet=gbk";
		assertEquals(null,HttpConnectionHelper.getCharSet(a));
		
	}

	public void testGetContentType() {
		String  a="text/html;CharSet=gbk";
		assertEquals("text/html",HttpConnectionHelper.getContentType(a));
		
		a="text/htmlCharSet=gbk   ";
		assertEquals(null,HttpConnectionHelper.getContentType(a));		

		
	}
}
