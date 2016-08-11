package box.site.getter;

import java.util.List;

import us.codecraft.webmagic.Page;

public interface ISiteContentGetter {

	public List<String> findItemUrls(Page page);
	
	public String getItemHtmlContent();
	
	public String getItemPureContent();
	
	public void setSitekey(String _sitekey);
	
	public boolean parseItem(String sitekey,String pageContent);
	
}
