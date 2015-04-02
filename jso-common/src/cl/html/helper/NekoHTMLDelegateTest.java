/*
 * 创建日期 2006-8-27
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package cl.html.helper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import es.Constants;
import es.util.io.CollectionHelper;
import es.util.io.FileContentHelper;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-27
 */
public class NekoHTMLDelegateTest extends TestCase {
	NekoHTMLUtils test=new NekoHTMLUtils();

	public void testa(){
//		String c="<div id=\"product-pic\"><a href=\"/detail/product.asp?prodid=bkbk606571&ref=BR&uid=9ssgeoust5gwaw7ugagg7sg5m\"><img src=\"http://images.joyo.com/t/tn_bkbk606571.jpg\"  width=\"70\" height=\"90\"  border=\"0\" alt=\"中国农村人口的收入与养老(中国人民大学\"211工程\"建设成果)\"/></a></div><div id=\"product-button\"><div><a href=\"/cart/upsellcart.asp?prodidstr=bkbk606571&ref=BR&uid=9ssgeoust5gwaw7ugagg7sg5m\"><img src=\"http://images.joyo.com/a/az-add-to-shopping-cart-sm-pri.gif\" border=0 /></a></div><div><a href=\"/cart/cart.asp?prodid=bkbk606571&action=AddFavorite&uid=9ssgeoust5gwaw7ugagg7sg5m/#Favorite\"><img src=\"http://images.joyo.com/a/az-add-to-favorites-sm-pri.gif\" border=0 /></a></div></div><div id=\"product-content\"><div> <a href=\"/detail/product.asp?prodid=bkbk606571&ref=BR&uid=9ssgeoust5gwaw7ugagg7sg5m\" class="medium">中国农村人口的收入与养老(中国人民大学"211工程"建设成果)</a><span class="author">宋健 </span></div><div class="HackBox1"></div><div id="Price">市场价：<s>￥16.80</s> 元</div><div id="JoyoPrice">卓越价：<span class="our-price">￥13.10</span> <span class="PriceText">元</span><span class="SaleNumberText">折扣：<span class="SaleNumber">78折</span></span><span class="SalePriceText">节省：<span class="SalePrice">3.70</span>元</span></div><div class="HackBox1"></div><div>华北地区1-2天发货 华东地区1-2天发货 华南地区1-2天发货 </div><div class=\"HackBox1\"></div></div>";
//		String c="<img alt=\"大学\"211工程建设成果)\">aaa";
		String c="<img alt=\"中国农村人口的收入与养老(中国人民大学211工程\"建设成果)\">aaa";
		String texts;
		try {
			texts = test.getPureText(c.getBytes(), Constants.CHARTSET_DEFAULT);
			System.out.println(texts);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testTable(){
		String table="<table><tbody><tr><table><tr><td>abd</td></tr></table><td>ddd</td></tr><tr></tr></table>";
		NekoHTMLDelegate test=new NekoHTMLDelegate(table);
		System.out.println(test.findTableSruct());
	}
	
	
	public void testToString(){
		String table="<table width=10 height=30><tbody><tr align=center> <table><tr><td>abd<a href='dd'>testa </a> </td></tr> </table><td>ddd</td></tr><tr></tr></table>";
		NekoHTMLDelegate test=new NekoHTMLDelegate(table);
		System.out.println(test.toAllString());
	}	
	public void testGetTextNodeValue() {
		
        File file=new File("D:\\SearchEngine\\ShoppingSites\\dangdangitems\\booktable1.txt");
        byte[] content=FileContentHelper.getContent(file);
        List texts;
		try {
			texts = test.getTextValues(content,"gbk");
		String pre="作者：";
		String next="（美）布朗 著，朱振武 等译";
		assertEquals(next,CollectionHelper.getListNextElement(pre,texts));
		
		pre="出版日期：";
		next="2004-2-1";
		assertEquals(next,CollectionHelper.getListNextElement(pre,texts));	
		
		pre="字数：";
		next="400000";
		assertEquals(next,CollectionHelper.getListNextElement(pre,texts));	
		
		pre="ISBN：";
		next="7208050031";
		assertEquals(next,CollectionHelper.getListNextElement(pre,texts));			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
