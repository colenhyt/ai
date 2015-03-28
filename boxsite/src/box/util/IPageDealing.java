package box.util;

import java.util.List;

import es.webref.model.PageRef;

public interface IPageDealing {

	public String[] getSetupSites();
	
	public List<PageRef> add(Object newPage);
	
	public String getFirstUrl(String siteId);
}
