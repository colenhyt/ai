/*
 * Created on 2005-10-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.string;

import junit.framework.TestCase;

/**
 * @author Allenhuang
 *
 * created on 2005-10-26
 */
public class StringHelperTest extends TestCase {
    StringHelper test=new StringHelper();

    public void testTell(){
    	String a="电话15-9537-37771... 查看和发布个人生活信息，请到客齐集！";
    	System.out.println(test.findTeleNumber(a));
    	System.out.println(test.findNextDigitStr(a));
    }
    public void testTextReplace() {
        String a="亲安全别针(9's小)";
        String b="亲安全别针(9s小)";
        assertEquals(StringHelper.textReplace("'","",a),b);
    }

    public void testFindCommonStr(){
        String str1="abcde:eee";
        String str2="abcde:eee:wwwww";
        assertEquals(str1,StringHelper.findCommonStr(str1,str2));

        str1="abcde:eeewrerq";
        str2="abcde:ee";
        assertEquals(str2,StringHelper.findCommonStr(str1,str2));

        str1="亲安全别针(9'小)fafefsfds";
        str2="亲安全别针(9'";
        assertEquals(str2,StringHelper.findCommonStr(str1,str2));
     }
    
    public void testReverseString(){
        String str="abcdefg";
        String str2="gfedcba";
        assertEquals(StringHelper.reverseString(str),str2);

        str="http://www.joyo.com/shop/shop_product.asp%3Fprodid=bkbk412111";
        str2="111214kbkb=didorpF3%psa.tcudorp_pohs/pohs/moc.oyoj.www//:ptth";
        assertEquals(StringHelper.reverseString(str),str2);
    }
}
