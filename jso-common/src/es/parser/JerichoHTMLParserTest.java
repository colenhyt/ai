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
import es.model.OriginalPage;
import es.util.io.FileContentHelper;

/**
 * Tracy&Allen.EasyShop 1.0
 *
 * @author Allenhuang
 * 
 * created on 2006-8-5
 */
public class JerichoHTMLParserTest extends TestCase {

    public void testFindPageRefs() {
//        File file=new File("D:\\SearchEngine\\ShoppingSites\\dangdang\\index.htm");//200: 97188;500:240609
    	//50:8800;
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
        urls=DefaultHTMLParser.findPageRefs(page);
        }
        long end=System.currentTimeMillis();
        System.out.println("parse time: "+(end-start));

    }

}
