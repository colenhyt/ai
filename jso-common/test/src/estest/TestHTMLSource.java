/*
 * 创建日期 2006-8-26
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package estest;

import easyshop.html.jericho.Source;
import es.util.io.FileContentHelper;


/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-26
 */
public class TestHTMLSource extends TestCase{
	
	public void testParseStr(){
        String fileName="D:/SearchEngine/ShoppingSites/dangdangitems/booktable1.txt";
        String content=FileContentHelper.getStringContent(fileName).toLowerCase();
        
        String fileName2="D:/SearchEngine/ShoppingSites/dangdangitems/book2.htm";
        String content2=FileContentHelper.getStringContent(fileName2).toLowerCase();
		Source source2=new Source(content2);
		String table1=source2.findEnclosingElement(content2.indexOf("isbn"),"table").getContentText();
		
		assertEquals(content,table1.trim());
	}

}
