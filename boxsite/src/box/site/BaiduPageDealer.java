package box.site;

import java.util.List;

import easyshop.downloadhelper.OriHttpPage;
import es.webref.model.PageRef;
import box.util.IPageDealer;

public class BaiduPageDealer implements IPageDealer {
	private static final String siteId = "baidu";

	@Override
	public List<PageRef> deal(OriHttpPage page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSiteId() {
		// TODO Auto-generated method stub
		return siteId;
	}

	@Override
	public String getFirstUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PageRef> getFirstRefs() {
		// TODO Auto-generated method stub
		return null;
	}

}
