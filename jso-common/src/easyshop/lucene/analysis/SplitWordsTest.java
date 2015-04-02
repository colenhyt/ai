/*
 * 创建日期 2006-11-18
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package easyshop.lucene.analysis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class SplitWordsTest extends TestCase {

	public void testTokenWordsAll() {
		Set strings=new HashSet();
		strings.add("我们American总共1.2亿我们1人+");
		strings.add("+American我们2234总共总共1.2亿人");
		strings.add("我们+American总共亿人1我们1.2");
		strings.add("我们+1.2American我们1总共亿人");
		strings.add("American+1.2总共亿abcn我们人我们");
		strings.add("总共亿人1亿人我+们1.2American");
		
		for (Iterator it=strings.iterator();it.hasNext();){
			String str=(String)it.next();
			assertTrue(SplitWords.findIndexes(str).size()==12);
		}
	}
}
