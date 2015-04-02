/*
 * Created on 2005-11-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.url;

import junit.framework.TestCase;

/**
 * @author Allenhuang
 *
 * created on 2005-11-22
 */
public class URLStrFormattorTest extends TestCase {

    public void testDecode() {
        String url="http://grocery.joyo.com/shop/shop_bazar_fenlei.asp%3fpages=4%26sortby=%3Fsorttype=bh0206";
        String url2="http://grocery.joyo.com/shop/shop_bazar_fenlei.asp?pages=4&sortby=?sorttype=bh0206";
        assertEquals(url2,URLStrFormattor.decode(url));
        
        url="http://grocery.joyo.com/shop/shop_bazar_fenlei.asp%3fpages=4%26sortby=%3Fsorttype=bh0206";
        url2="http://grocery.joyo.com/shop/shop_bazar_fenlei.asp?pages=4&sortby=?sorttype=bh0206";
        assertEquals(url2,URLStrFormattor.decode(url));
    }

    public void testEncodeAll() {
        String url="http://www.joyo.com/book/detail.asp?mlid=bkbk310110";
        String url2="http%3A%2F%2Fwww.joyo.com%2Fbook%2Fdetail.asp%3Fmlid=bkbk310110";        
        assertEquals(url2,URLStrFormattor.encodeAll(url));
    }

	public void testEncode() {
	    String url="http://www.joyo.com/book/detail.asp?mlid=bkbk310110";
	    String url2="http://www.joyo.com/book/detail.asp%3Fmlid=bkbk310110";        
	    assertEquals(url2,URLStrFormattor.encode(url));
	}

}
