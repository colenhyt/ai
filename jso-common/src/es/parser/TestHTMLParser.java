package es.parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.Set;

import javax.xml.transform.Templates;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;

import es.model.OriginalPage;
import es.util.io.FileContentHelper;


public class TestHTMLParser extends TestCase {
	
	public void testSwingParserPerform(){
		//50:5000
        File file=new File("D:/ajecvs/Workspace/huangyingtian/SearchEngine/bookitems/cp/categories.htm");
        String content=FileContentHelper.getStringContent(file);
        OriginalPage page=new OriginalPage();
        page.setContent(content.getBytes());
        page.setUrlStr("http://www.china-pub.com");
        page.setSpecId("chinapub");
        page.setUId(1);
        Set urls=null;
        long start=System.currentTimeMillis();
        for (int i=0;i<50;i++){
        urls=SwingHTMLParser.findPageRefs(page);
        }
        long end=System.currentTimeMillis();
        System.out.println("parse time: "+(end-start));		
	}
	private Templates template;
	
	private Tidy tidy= new Tidy();
	 private void initTidy(){
		   tidy.setXmlOut(true);    // 输出格式 xml
		   tidy.setDropFontTags(true);   // 删除字体节点
		   tidy.setDropEmptyParas(true);  // 删除空段落
		   tidy.setFixComments(true);   // 修复注释
		   tidy.setFixBackslash(true);   // 修复反斜杆
		   tidy.setMakeClean(true);   // 删除混乱的表示
		   tidy.setQuoteNbsp(false);   // 将空格输出为 &nbsp; 
		   tidy.setQuoteMarks(false);   // 将双引号输出为 &quot;
		   tidy.setQuoteAmpersand(true);  // 将 &amp; 输出为 &
		   tidy.setShowWarnings(false);  // 不显示警告信息
		   tidy.setCharEncoding(Configuration.UTF8); // 文件编码为 UTF8
	 }
	 /**
	  * 解析网页，转换为 W3C Document 文档对象
	  * @param fileName HTML 网页的文件名
	  * @return   utf-8 W3C Document 文档对象
	  */
	 private Document parser(String fileName) {
	  Document doc = null;
	  try{
	   FileInputStream in = new FileInputStream( fileName ); // 打开文件，转换为 UTF-8 编码  
//	   InputStreamReader isr = new InputStreamReader(in, "GB2312"); // 源文件编码为 gb2312
//	   
//	   File tmpNewFile = File.createTempFile("GB2312",".html"); // 转换后的文件，设定编码为 utf-8
//	   FileOutputStream out = new FileOutputStream( tmpNewFile ); // 需要将文件转换为字符流
//	   OutputStreamWriter osw = new OutputStreamWriter( out , "UTF-8");// 指定目标编码为 utf-8
//	   osw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
//	   
//	   char[] buffer = new char[10240];      // 文件缓冲区
//	   int len = 0;           // 使用字符读取方式，循环读取源文件内容
//	   while( (len = isr.read(buffer)) !=-1 )     // 转换后写入目标文件中
//	   {
//	    osw.write( buffer, 0, len);
//	   }
//	   osw.close();           // 转换完成
//	   isr.close();
//	   out.close();
//	   in.close();
	   
	   //  设置 tidy ，准备转换
	   
//	   FileInputStream src = new FileInputStream( tmpNewFile ); // 
	   doc = tidy.parseDOM( in ,null ); // 通过 JTidy 将 HTML 网页解析为
//	   src.close();           // W3C 的 Document 对象
//	   tmpNewFile.delete();         // 删除临时文件
	   
	   NodeList list = doc.getChildNodes();     // 页面中 DOCTYPE 中可能问题
	   for(int i=0; i<list.getLength(); i++)     // 删除 DOCTYPE 元素
	   {
	    Node node = list.item(i);
	    if( node.getNodeType() == Node.DOCUMENT_TYPE_NODE) // 查找类型定义节点
	    {
	     node.getParentNode().removeChild( node );
	    }
	   }
	   
	   list = doc.getElementsByTagName("div");    // 脚本中的注释有时有问题
	   list = doc.getElementsByTagName("script");    // 脚本中的注释有时有问题
	   for(int i=0; i<list.getLength(); i++){     // 清理 script 元素
	    Element script = (Element) list.item(i);
	    if( script.getFirstChild() != null){
	     script.removeChild( script.getFirstChild());
	    }
	   }
	   
	  }
	  catch(Exception e)
	  {
	   e.printStackTrace();
	  }finally
	  {
	   
	  }
	  return doc;
	 }

	public void testJTidyHTMLParser(){
		//50：8218
	    String file="D:/ajecvs/Workspace/huangyingtian/SearchEngine/bookitems/cp/categories.htm";
	    initTidy();
	        long start=System.currentTimeMillis();
	        for (int i=0;i<50;i++){
				parser(file);
	        }
	        long end=System.currentTimeMillis();
	        System.out.println("parse time: "+(end-start));
	}

}
