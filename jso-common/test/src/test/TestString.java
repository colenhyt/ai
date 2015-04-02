package test;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import es.util.io.FileContentHelper;


public class TestString extends TestCase {
	
	public void testIndexof(){
        String fileName="D:/ajecvs/Workspace/huangyingtian/SearchEngine/bookitems/cp/bookitem1.htm";
        String content=FileContentHelper.getStringContent(fileName);
        String img="C++程序设计语言（特别版）";
        long start=System.currentTimeMillis();
        for (int i=0;i<1000000;i++){//1m:2703
         boolean b=content.indexOf(img)>0;
        }
        System.out.println("total time is "+(System.currentTimeMillis()-start));
		
	}
	public void testContains(){
        String fileName="D:/ajecvs/Workspace/huangyingtian/SearchEngine/bookitems/cp/bookitem1.htm";
        String content=FileContentHelper.getStringContent(fileName);
        String img="C++程序设计语言（特别版）";
        long start=System.currentTimeMillis();
        for (int i=0;i<1000000;i++){//1m:2734
         boolean b=StringUtils.indexOf(content,img)>0;
        }
        System.out.println("total time is "+(System.currentTimeMillis()-start));
		
	}

}
