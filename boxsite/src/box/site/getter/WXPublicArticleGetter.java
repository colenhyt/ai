package box.site.getter;


public class WXPublicArticleGetter extends BasicSiteContentGetter{
	public String wxHao;
	
	@Override
	public boolean parseItem(String url,String pageContent) {

		return true;
	}

}
