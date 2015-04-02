/*
 * Created on 2005-4-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.model;

import java.util.List;

import es.webref.model.PageRef;


/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BranchPage extends OriginalPage {
	private int _variety=-1;
	private PageRef[] leftSubSorts;
	private List<PageRef> catUrlList,itemUrlList;
	public PageRef[] getLeftSubSorts() {
		return leftSubSorts;
	}

	public void setLeftSubSorts(PageRef[] leftSubSorts) {
		this.leftSubSorts = leftSubSorts;
	}

	public int getVariety(){
		return _variety;
		
	}
	
	public void setVariety(int var){
		_variety=var;
	}

	public List<PageRef> getCatUrlList() {
		return catUrlList;
	}

	public void setCatUrlList(List<PageRef> catUrlList) {
		this.catUrlList = catUrlList;
	}

	public List<PageRef> getItemUrlList() {
		return itemUrlList;
	}

	public void setItemUrlList(List<PageRef> itemUrlList) {
		this.itemUrlList = itemUrlList;
	}
	
	
	
}
