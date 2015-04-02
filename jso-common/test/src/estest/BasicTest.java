/*
 * Created on 2006-3-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package estest;

import junit.framework.TestCase;
import easyshop.downloadhelper.URLGetterTest;

/**
 * Tracy&Allen.EasyShop 0.9
 *
 * @author Allenhuang
 * 
 * created on 2006-3-26
 */
public class BasicTest extends TestCase {
    
    public void testArray(){
        byte[] content=new byte[12000000];
        String a=new String(content);
    }
    
    public void testStringGetIndex(){
        String str="aaaabbb";
        assertEquals(str.indexOf("aa"),0);
        assertEquals(str.indexOf("bb"),4);
        assertEquals(str.indexOf("cc"),-1);
        assertEquals(str.indexOf("aa",0),0);
        assertEquals(str.indexOf("bb",-1),4);
    }
    public static void main(String[] args) {
    	System.out.println("hehehe");
    	URLGetterTest test=new URLGetterTest();
    }
    public void testChar(){
        char c=' ';
        assertTrue(Character.isSpace(c));
        assertTrue(Character.isWhitespace(c));
        c='.';
        assertFalse(Character.isSpace(c));
        assertFalse(Character.isSpace(c));
        c='l';
        assertTrue(Character.isLetter(c));
        assertTrue(Character.isLowerCase(c));
        c='A';
        assertFalse(Character.isWhitespace(c));
        
    }
}
