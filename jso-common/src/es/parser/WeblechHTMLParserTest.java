/*
 * Created on 2006-8-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.parser;

import java.io.File;
import java.util.Set;

import junit.framework.TestCase;
import easyshop.downloadhelper.HttpPage;
import es.util.io.FileContentHelper;

/**
 * Tracy&Allen.EasyShop 1.0
 *
 * @author Allenhuang
 * 
 * created on 2006-8-5
 */
public class WeblechHTMLParserTest extends TestCase {

    public void testFindPageRefs() {
//        File file=new File("D:\\SearchEngine\\ShoppingSites\\dangdang\\index.htm");//200: 97188;500:240609
    	//50:14907;输入lowerCase并且只分析a href后:8844;
        File file=new File("D:/ajecvs/Workspace/huangyingtian/SearchEngine/bookitems/cp/categories.htm");
        String content=FileContentHelper.getStringContent(file);
        HttpPage page=new HttpPage(content.getBytes(),"gb2312");
        HTMLParser parser=new HTMLParser();
        Set urls=null;
        long start=System.currentTimeMillis();
        for (int i=0;i<50;i++){
        	
			urls=WeblechHTMLParser.findPageRefs(page.getStringContent());
        }
        long end=System.currentTimeMillis();
        System.out.println("parse time: "+(end-start));
		}


}
