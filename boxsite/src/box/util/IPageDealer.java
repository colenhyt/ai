package box.util;

import java.util.List;

import easyshop.downloadhelper.OriHttpPage;
import es.webref.model.PageRef;

public interface IPageDealer {
	public static String SITEID_JD = "jd";
	public static String SITEID_TMALL = "tmall";

	public List<PageRef> deal(OriHttpPage page);
	
	public void pushSearchWord(String word);
	
	public String getSiteId();
	
	public String getFirstUrl();
	
	public List<PageRef> getFirstRefs();
	
}
