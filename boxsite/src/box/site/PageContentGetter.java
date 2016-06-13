package box.site;

import cn.edu.hfut.dmic.htmlbot.contentextractor.ContentExtractor;

public class PageContentGetter {
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
