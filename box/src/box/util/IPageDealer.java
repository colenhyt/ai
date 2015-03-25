package box.util;

import easyshop.downloadhelper.OriHttpPage;
import es.webref.model.PageRef;

public interface IPageDealer {
	public static String SITEID_JD = "jd";

	public void deal(OriHttpPage page);
	
	public String getSiteId();
}
