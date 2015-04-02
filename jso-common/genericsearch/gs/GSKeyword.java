package gs;

import es.webref.model.PageRef;

public class GSKeyword {
	private final PageRef ref;
	private long searchCount=0;
	
	public GSKeyword(PageRef ref){
		this.ref=ref;
	}
	
	public PageRef getRef() {
		return ref;
	}
	public long getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(long searchCount) {
		this.searchCount = searchCount;
	}

}
