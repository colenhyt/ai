package box.site.getter;

import java.util.List;

import easyshop.html.TagDNA;
import us.codecraft.webmagic.Page;

public interface ISiteContentGetter {

	public List<String> findItemUrls(Page page);
	
	public String getItemHtmlContent();
	
	public String getItemPureContent();
	
	public boolean parseItem(String url,String pageContent);
	
}
