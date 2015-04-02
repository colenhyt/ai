/*
 * Created on 2005-10-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cl.html.helper;

import junit.framework.TestCase;

/**
 * @author Allenhuang
 * 
 * created on 2005-10-16
 */
public class HTMLTagResolverTest extends TestCase {

    public void testCutTag() {
        String a = "<FONT title=Hello Kitty毛绒零钱包（HKT238）>日本</FONT>";
        String b = "日本";
        String tag = "font";
        assertEquals(HTMLTagResolver.cutTag(a, tag), b);

        a = "<FONT title=Hello Kitty毛绒零钱包（HKT238）><font abc>日本</FONT>";
        b = "日本";
        tag = "font";
        assertEquals(HTMLTagResolver.cutTag(a, tag), b);

        a = "<FONT title=Hello Kitty毛绒零钱包（HKT238）><font abc>日本</font></font></FONT>";
        b = "日本";
        tag = "font";
        assertEquals(HTMLTagResolver.cutTag(a, tag), b);

        a = "<FONT title=Hello Kitty毛绒零钱包（HKT238）日本";
        tag = "font";
        assertEquals(HTMLTagResolver.cutTag(a, tag), a.toLowerCase());

        a = "日本</font";
        tag = "font";
        assertEquals(HTMLTagResolver.cutTag(a, tag), a.toLowerCase());
    }
}