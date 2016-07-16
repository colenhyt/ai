package box.site.getter;

import java.util.List;

import easyshop.html.TagDNA;
import us.codecraft.webmagic.Page;

public interface ISiteContentGetter {

	public List<String> findItemUrls(Page page);
	
	public void setSitekey(String sitekey);
	
	public void addItemUrlReg(String urlReg);
	
	public void addTagDNA(TagDNA tagDNA);
	
	public String getItemHtmlContent();
	
	public String getItemPureContent();
	
	public boolean itemParse(Page page);
	
	public void toSave(String basicPath);
	
}
