package cn.hd.base;

/**
 * 共用的属性和方法 
 * @author hd
 * 2011-4-5
 */
public class BaseExample {

    protected int currPage;
    protected int pageSize;
    protected int skip;
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getSkip() {
		return (currPage-1)*pageSize;
	}
	public void setSkip(int skip) {
		this.skip = skip;
	}
}
