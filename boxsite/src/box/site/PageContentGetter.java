package box.site;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.htmlbot.DomPage;
import cn.edu.hfut.dmic.htmlbot.HtmlBot;
import cn.edu.hfut.dmic.htmlbot.contentextractor.ContentExtractor;
import easyshop.html.HTMLInfoSupplier;
import es.util.FileUtil;
import es.util.url.URLStrHelper;

public class PageContentGetter {
	static HTMLInfoSupplier infoSupp = new HTMLInfoSupplier();
	Set<String> sitekeys = new HashSet<String>();
	
	public PageContentGetter(){
		sitekeys.add("163.com");
		sitekeys.add("sina.com.cn");
		sitekeys.add("qq.com");
		sitekeys.add("sohu.com");
		sitekeys.add("huxiu.com");
//		sitekeys.add("iheima.com");
	}
	
	public static void main(String[] args){
		String sitekey = "iheima.com";
		String path = "data/pages/"+sitekey+"/2043054237.html";
		String content = FileUtil.readFile(path);
		PageContentGetter getter = new PageContentGetter();
		String cc = getter.getSpecSiteContent(sitekey, content);
//		System.out.println(cc);
		List<String> cc2 = getter.getHtmlContent("http://"+sitekey, content);
		System.out.println(cc2.toString());
	}
	
	public static String getTitle(String pageContent){
		infoSupp.init(pageContent.getBytes());
		return infoSupp.getTitleContent();
	}
	
	public String getSpecSiteContent(String sitekey,String content){
		infoSupp.init(content.getBytes());
		if (sitekey.indexOf("163.com")>=0)
			return infoSupp.getDivByClassValue("post_text");
		else if (sitekey.indexOf("qq.com")>=0)
			return infoSupp.getBlockByOneProp("div","id","Cnt-Main-Article-QQ");
		else if (sitekey.indexOf("sina.com")>=0)
			return infoSupp.getDivByClassValue("content");
		else if (sitekey.indexOf("sohu.com")>=0)
			return infoSupp.getBlockByOneProp("div","id","contentText");
		else if (sitekey.indexOf("huxiu.com")>=0)
			return infoSupp.getBlockByOneProp("div","id","article_content");
		else if (sitekey.indexOf("iheima.com")>=0)
			return infoSupp.getNextBlock("div", "class","outline","p");
							
		return null;
	}
	
	public static String getContent(String pageContent){
		try {
			return ContentExtractor.getContentByHtml(pageContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<String> getHtmlContent(String url,String pageContent){
		String sitekey = URLStrHelper.getHost(url).toLowerCase();
		if (sitekeys.contains(sitekey)){
			String textContent = getSpecSiteContent(sitekey,pageContent);
			if (textContent!=null){
				List<String> content = new ArrayList<String>();
				textContent = textContent.trim();
				content.add(textContent);
				content.add(textContent);
				return content;
			}		
			return null;
		}

		
		String textContent = PageContentGetter.getContent(pageContent);
		if (textContent==null||textContent.trim().length()<30)
			return null;
		
		Set<String> cTagNames = new HashSet<String>();
		cTagNames.add("div");
		cTagNames.add("p");
		DomPage domPage = HtmlBot.getDomPageByHtml(pageContent);
		Document doc = domPage.getDoc();
		String startText = textContent.substring(0,10);
		Elements els = doc.getElementsContainingText(startText);
		List<Element> startDivEs = new ArrayList<Element>();
		for (Element e:els){
			if (cTagNames.contains(e.tagName().toLowerCase())){
				String str = e.toString();
				startDivEs.add(e);
			}
		}
		String endText = textContent.substring(textContent.length()-20);
		List<Element> endDivEs = new ArrayList<Element>();
		for (Element e:startDivEs){
			DomPage domPage2 = HtmlBot.getDomPageByHtml(e.toString());
		    ContentExtractor contentExtractor = new ContentExtractor(domPage2);
			String cc = contentExtractor.getText();
			String end2 = cc.substring(cc.length()-20);
			if (endText.equals(end2)){
				endDivEs.add(e);
			}
		}
		if (endDivEs.size()<=0)
			return null;
		
		//选长度最小的一个:
		Element contentElement = null;
		for (Element e:endDivEs){
			if (contentElement==null||contentElement.toString().length()>e.toString().length())
				contentElement = e;
		}
		//去除无用tags:script
		String htmlContent = contentElement.toString();
		int sindex = htmlContent.indexOf("<script");
		int endindex = htmlContent.indexOf("/script>",sindex);
		while (sindex>0&&endindex>sindex){
			String scrStr = htmlContent.substring(sindex,endindex+"/script>".length());
			htmlContent = htmlContent.replace(scrStr, "");
			sindex = htmlContent.indexOf("<script",sindex);
			endindex = htmlContent.indexOf("/script>",endindex);
		}
		if (htmlContent!=null)
			htmlContent = htmlContent.trim();
		List<String> content = new ArrayList<String>();
		content.add(textContent);
		content.add(htmlContent);
		return content;
	}	
}
