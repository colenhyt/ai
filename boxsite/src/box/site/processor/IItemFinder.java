package box.site.processor;

import us.codecraft.webmagic.Page;

public interface IItemFinder {

	String			getStartUrl(String word);
	
	void			process(Page page);
	
}
