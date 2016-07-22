package cn.hd.util;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import box.db.Wxpublic;
import box.weixin.SougouPageDealer;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import es.util.url.URLStrHelper;

public class HtmlUnitTest {
	private SougouPageDealer sogouDealer = new SougouPageDealer();
	
	public void testWeibo(){
	       final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
		try {
			String url = "http://weibo.com/leijun";
        final HtmlPage page = webClient.getPage(url);
		System.out.println(page.asXml());
       
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        webClient.close(); 		
		
	}
	public void testWeixin(){
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
		try {
			String url = "http://weixin.sogou.com/weixin?type=1&query=jokecn&ie=utf8";
        final HtmlPage page = webClient.getPage(url);
		System.out.println(page.asXml());
       
		String wxhao = URLStrHelper.getParamValue(url, "query");
		List<Wxpublic> wps = sogouDealer.findWxPublics(page.asXml().getBytes());
		for (Wxpublic pb:wps){
			HtmlPage page2 = webClient.getPage(pb.getSogouUrl());
			System.out.println(page2.asXml());
		}        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        webClient.close(); 		
	}
	
	public static void main(String[] args){
		HtmlUnitTest t = new HtmlUnitTest();
		t.testWeibo();
//		try {
//			t.homePage();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	   public void homePage() throws Exception {  
	        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
//	        final HtmlPage page = webClient.getPage("http://yangshangchuan.iteye.com");  
//	        Assert.assertEquals("杨尚川的博客 - ITeye技术网站", page.getTitleText());  
//	        final String pageAsXml = page.asXml();  
	        final HtmlPage page = webClient.getPage("http://36kr.com");  
	       System.out.println(page.asText());
	        webClient.close();  
	    }  
	    @Test  
	    public void homePage_Firefox() throws Exception {  
	        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
	        final HtmlPage page = webClient.getPage("http://yangshangchuan.iteye.com");  
	        Assert.assertEquals("杨尚川的博客 - ITeye技术网站", page.getTitleText());  
	        webClient.close();  
	    }  
	    @Test  
	    public void getElements() throws Exception {  
	        final WebClient webClient = new WebClient(BrowserVersion.CHROME);  
	        final HtmlPage page = webClient.getPage("http://yangshangchuan.iteye.com");  
	        final HtmlDivision div = page.getHtmlElementById("blog_actions");  
	        //获取子元素  
	        Iterator<DomElement> iter = div.getChildElements().iterator();  
	        while(iter.hasNext()){  
	            System.out.println(iter.next().getTextContent());  
	        }  
	        //获取所有输出链接  
	        for(HtmlAnchor anchor : page.getAnchors()){  
	            System.out.println(anchor.getTextContent()+" : "+anchor.getAttribute("href"));  
	        }  
	        webClient.close();
	    }  
	    @Test  
	    public void xpath() throws Exception {  
	        final WebClient webClient = new WebClient();  
	        final HtmlPage page = webClient.getPage("http://yangshangchuan.iteye.com");  
	        //获取所有博文标题  
	        final List<HtmlAnchor> titles = (List<HtmlAnchor>)page.getByXPath("/html/body/div[2]/div[2]/div/div[16]/div/h3/a");  
	        for(HtmlAnchor title : titles){  
	            System.out.println(title.getTextContent()+" : "+title.getAttribute("href"));  
	        }  
	        //获取博主信息  
	        final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@id='blog_owner_name']").get(0);  
	        System.out.println(div.getTextContent());  
	        webClient.close();  
	    }  
	    @Test  
	    public void submittingForm() throws Exception {  
	        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);  
	        final HtmlPage page = webClient.getPage("http://www.oschina.net");  
	        // Form没有name和id属性  
	        final HtmlForm form = page.getForms().get(0);  
	        final HtmlTextInput textField = form.getInputByName("q");  
	        final HtmlButton button = form.getButtonByName("");  
	        textField.setValueAttribute("APDPlat");  
	        final HtmlPage resultPage = button.click();  
	        final String pageAsText = resultPage.asText();  
	        Assert.assertTrue(pageAsText.contains("找到约"));  
	        Assert.assertTrue(pageAsText.contains("条结果"));  
	        webClient.close();  
	    }  
}
