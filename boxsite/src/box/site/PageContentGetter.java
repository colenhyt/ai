package box.site;

import cn.edu.hfut.dmic.htmlbot.contentextractor.ContentExtractor;
import easyshop.html.HTMLInfoSupplier;

public class PageContentGetter {
	static HTMLInfoSupplier infoSupp = new HTMLInfoSupplier();
	
	public static String getTitle(String pageContent){
		infoSupp.init(pageContent.getBytes());
		return infoSupp.getTitleContent();
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
}
