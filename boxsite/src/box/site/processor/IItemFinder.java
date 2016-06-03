package box.site.processor;

import java.util.List;
import java.util.Set;

import us.codecraft.webmagic.Page;

public interface IItemFinder {

	String			getStartUrl(String word);
	
	void			process(Page page);
	
}
