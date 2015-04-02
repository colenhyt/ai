/*
 * Created on 2005-10-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.url;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

/**
 * @author Allenhuang
 *
 * created on 2005-10-20
 */
public class URLStrHelperTest extends TestCase {
	
	public void testCutParam(){
		String urlstr="http://www.amazon.cn/browse/browse.asp?ref=cn&pageletid=xizang&nodeid=1184";
		System.out.println(URLStrHelper.cutParameters(urlstr, new String[]{"ref","pageletid"}));
		urlstr="http://www.amazon.cn/store/sh.asp?pageletid=popuptop";
		System.out.println(URLStrHelper.cutParameter(urlstr, "pageletid"));
		urlstr="http://www.amazon.cn/store/tech.asp?uid=";
		System.out.println(URLStrHelper.cutParameter(urlstr, "uid"));
		urlstr="http://www.amazon.cn/browse/browse.asp?nodeID=57387&sortType=market&showType=1&uid=168-3618481-0835447&pageNow=6";
		System.out.println(URLStrHelper.cutParameter(urlstr, "sorttype"));
	}
	public void testLegal(){
		System.out.println(URLStrHelper.legalUrl("http://www.jc.net.cn/usm_jgzx.asp?area=","?page=2&area=&key="));
		System.out.println(URLStrHelper.legalUrl("http://www.china-pub.com/search/rank.asp?typeid=c","?typeid=C01"));
	}
	
	public void testBranch(){
		System.out.println(URLStrHelper.getUrlBran("http://www.hardwarenet.net/seller/trade/1-60.html"));
		System.out.println(URLStrHelper.getUrlBran("http://www.hardwarenet.net/seller/trade/1_60.html"));
		System.out.println(URLStrHelper.getUrlBran("http://www.hardwarenet.net/info/community/abcde.html"));
		System.out.println(URLStrHelper.getUrlBran("http://www.hardwarenet.net/info/community/4.html"));
		System.out.println(URLStrHelper.getUrlBran("http://www.hardwarenet.net/info/community/a.asp?a=b&b=d"));
		System.out.println(URLStrHelper.getUrlBran("http://www.hardwarenet.net/info/community/b.php"));
	}
	public void testHost(){
		String urlStr;
		urlStr="http://b2bdl.kvov.cn";
		System.out.println(URLStrHelper.getHost(urlStr));
		urlStr="http://www.aifm.com.cn";
		System.out.println(URLStrHelper.getHost(urlStr));
	}
	public void testDir(){
		String urlStr,urlStr2;
		urlStr="http://www.fsjiaju.com";
		System.out.println(URLStrHelper.getURLDir(urlStr));
		urlStr="http://www.fsjiaju.com/";
		System.out.println(URLStrHelper.getURLDir(urlStr));
		
		urlStr="http://www.fsjiaju.com/HuiyInfo/link.asp?hidd=30997";
		urlStr2="http://www.fsjiaju.com";
		assertTrue(URLStrHelper.isSameURLDir(urlStr, urlStr2));
	}
	public void testIslink(){
		String base,link;
		base="http://www.777f.com/search/trade.asp?result=yes&class=1";
		link="tradeInfo.asp?Id=13009&__AFCHIDDEN=result%3Dyes%26class%3D1";
		assertTrue(URLStrHelper.legalUrl(base, link)!=null);
	}
	public void testGetURLDir(){
	       String url1="http://classic.joyo.com/shop/shop_product.asp?prodid=zjbk137109,zjbk137109,zjbk137109";
	       String url2="http://classic.joyo.com/shop";
		    assertEquals(URLStrHelper.getURLDir(url1),url2);
		
		       url1="http://www.sinoshu.com/CATE/211/1/index_6.shtml";
		       url2="http://www.sinoshu.com/CATE/211/1";
			    assertEquals(URLStrHelper.getURLDir(url1),url2);
				
			       url1="http://www.sinoshu.com/CATE/211/1/";
			       url2="http://www.sinoshu.com/CATE/211/1";
				    assertEquals(URLStrHelper.getURLDir(url1),url2);
	}
	
	public void testGetFileName(){
	       String url1="http://classic.joyo.com/shop/shop_product.asp?prodid=zjbk137109,zjbk137109,zjbk137109";
	    assertEquals(URLStrHelper.getFileName(url1),"shop_product");
	    
        url1="http://www.dearbook.com/big/1.htm";
        assertEquals(URLStrHelper.getFileName(url1),"1");        
		
	}
	
	public void testGetHostStrWithPre(){
	       String url1="http://classic.joyo.com/shop/shop_product.asp?prodid=zjbk137109,zjbk137109,zjbk137109";
	    assertEquals(URLStrHelper.getHostWithPre(url1),"http://classic.joyo.com");
	    
        url1="http://www.dearbook.com/";
        assertEquals(URLStrHelper.getHostWithPre(url1),"http://www.dearbook.com");        
		
	}	
	
	
	public void testGetHostStr(){
	       String url1="http://classic.joyo.com/shop/shop_product.asp?prodid=zjbk137109,zjbk137109,zjbk137109";
	    assertEquals(URLStrHelper.getHostStr(url1),"classic.joyo.com");
	    
        url1="http://www.dearbook.com/";
        assertEquals(URLStrHelper.getHostStr(url1),"www.dearbook.com");        
	    
		
	}		
	public void testGetFullFileName(){
	       String url1="http://classic.joyo.com/shop/shop_product.asp?prodid=zjbk137109,zjbk137109,zjbk137109";
	    assertEquals(URLStrHelper.getFullFileName(url1),"shop_product.asp");
	    
     url1="http://www.dearbook.com/big/1.htm";
     assertEquals(URLStrHelper.getFullFileName(url1),"1.htm");        
	    
     url1="http://www.dearbook.com/big/a.jsp";
     assertEquals(URLStrHelper.getFullFileName(url1),"a.jsp");        
	    
     url1="http://www.dearbook.com/big/";
     assertEquals(URLStrHelper.getFullFileName(url1),"big");        
	    
     url1="http://www.dearbook.com/big";
     assertEquals(URLStrHelper.getFullFileName(url1),"big");        
	    
     url1="http://www.bokee.com";
     assertEquals(URLStrHelper.getFullFileName(url1),URLStrHelper.URL_INDEX);        
	    
     url1="http://www.bokee.com/images/picnav_blogchina.jpg";
     assertEquals(URLStrHelper.getFullFileName(url1),"picnav_blogchina.jpg");        
	    
     url1="http://ad.bokee.com/ads/adclick.php?bannerid=2750&zoneid=1332&source=&dest=http%3a%2f%2fbokee.allyes.com%2fmain%2fadfclick?db%3dbokee&bid%3d208%2c1141%2c1507&cid%3d0%2c0%2c0&sid%3d12600&advid%3d116&camid%3d228&show%3dignore&url%3dht";
     assertEquals(URLStrHelper.getFullFileName(url1),"adclick.php");        
	    
     url1="http://ad.bokee.com?bannerid=2750&zoneid=1332&source=&dest=http%3a%2f%2fbokee.allyes.com%2fmain%2fadfclick?db%3dbokee&bid%3d208%2c1141%2c1507&cid%3d0%2c0%2c0&sid%3d12600&advid%3d116&camid%3d228&show%3dignore&url%3dht";
     assertEquals(URLStrHelper.getFullFileName(url1),URLStrHelper.URL_INDEX);        
		
	}
	
    public void testNormalRevise() {
        String url1="http://classic.joyo.com/shop/shop_product.asp?prodid=zjbk137109,zjbk137109,zjbk137109";
        String url2="http://classic.joyo.com/shop/shop_product.asp?prodid=zjbk137109";
        assertEquals(URLStrHelper.cutUselessChas(URLStrHelper.basicFilter(url1)),url2);
        
        url1="http://classic.joyo.com/shop/shop_product.asp?prodid=zjbk137109;zjbk137109;zjbk137109";
        url2="http://classic.joyo.com/shop/shop_product.asp?prodid=zjbk137109";
        assertEquals(URLStrHelper.cutUselessChas(URLStrHelper.basicFilter(url1)),url2);        
        
        }

    public void testCutUselessChas() {
        Set urls=new HashSet();
        urls.add("http://www.joyo.com/static/hyc030113_ct.asp/");
        urls.add("http://www.joyo.com/static/hyc030113_ct.asp?");
        urls.add("http://www.joyo.com/static/hyc030113_ct.asp/?");
//        urls.add("http://www.joyo.com/static/hyc030113_ct.asp?/");
        urls.add("http://www.joyo.com/static/hyc030113_ct.asp#");
        urls.add("http://www.joyo.com/static/hyc030113_ct.asp#abc");
        urls.add("http://www.joyo.com/static/hyc030113_ct.asp/#");
//        urls.add("http://www.joyo.com/static/hyc030113_ct.asp?#/");               
        
        String revisedURL="http://www.joyo.com/static/hyc030113_ct.asp";
        for (Iterator it=urls.iterator();it.hasNext();){
            String url=(String)it.next();
            assertEquals(URLStrHelper.cutUselessChas(url),revisedURL);
        }

     }

    public void testCutParameter() {
        String url,url2;
        
        url="http://www.joyo.com/static/a.asp?ttt=eee";
        url2="http://www.joyo.com/static/a.asp?ttt=eee";
        assertEquals(URLStrHelper.cutParameter(url,"abc"),url2);
        
        url="http://www.joyo.com/static/a.asp?abc=eee";
        url2="http://www.joyo.com/static/a.asp";
        assertEquals(URLStrHelper.cutParameter(url,"abc"),url2);
        
        url="http://www.joyo.com/static/a.asp?abc=eee&ttt=a";
        url2="http://www.joyo.com/static/a.asp?ttt=a";
        assertEquals(URLStrHelper.cutParameter(url,"abc"),url2);
        
        url="http://www.joyo.com/static/a.asp?www=eee&abc=mok";
        url2="http://www.joyo.com/static/a.asp?www=eee";
        assertEquals(URLStrHelper.cutParameter(url,"abc"),url2);
        
        url="http://www.joyo.com/static/a.asp?www=eee&abc=mok&emme=qoe";
        url2="http://www.joyo.com/static/a.asp?www=eee&emme=qoe";
        assertEquals(URLStrHelper.cutParameter(url,"abc"),url2);
        
        url="http://book.joyo.com/book/browse.asp?goodsid=bkbk508686&pages=1&sortby&sorttype=bk18";
        url2="http://book.joyo.com/book/browse.asp?pages=1&sortby&sorttype=bk18";
        assertEquals(URLStrHelper.cutParameter(url,"goodsid"),url2);
    }
    
    public void testGetParameter(){
        String url="http://www.joyo.com/shop/shop_product.asp?prodid=bkbk404250";
        String url2="bkbk404250";
        assertEquals(URLStrHelper.getParamValue(url,"prodid"),url2);
        
        url="http://www.joyo.com/book/detail.asp?mlid=bkbk208279";
        url2="bkbk208279";
        assertEquals(URLStrHelper.getParamValue(url,"mlid"),url2);
        
        url="http://www.joyo.com/book/detail.asp?a=b&mlid=bkbk208279";
        url2="bkbk208279";
        assertEquals(URLStrHelper.getParamValue(url,"mlid"),url2);        
        
        url="http://www.joyo.com/book/detail.asp?mlid=bkbk208279&e=2fdaf";
        url2="bkbk208279";
        assertEquals(URLStrHelper.getParamValue(url,"mlid"),url2);    
        
        url="http://www.joyo.com/book/detail.asp?new_id=2fdaf";
        url2=null;
        assertEquals(URLStrHelper.getParamValue(url,"id"),url2);    
        
        url="http://www.dangdang.com/dd2001/bargin/yearsale.asp?spid=100&kindid=01";
        url2=null;
        assertEquals(URLStrHelper.getParamValue(url,"id"),url2);           
    }

}
