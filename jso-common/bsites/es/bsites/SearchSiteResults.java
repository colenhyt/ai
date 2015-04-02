package es.bsites;

import java.util.ArrayList;
import java.util.List;

import easyshop.model.ProductItem;
import easyshop.model.ShoppingItem;

public class SearchSiteResults {
	private int count=-2;
	private float msPrice;
	private String searchStr;
	private String searchWord,siteStr;
	private List<ShoppingItem> items=new ArrayList<ShoppingItem>();
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public float getMsPrice() {
		return msPrice;
	}
	public void setMsPrice(float msPrice) {
		this.msPrice = msPrice;
	}
	public String getSearchStr() {
		return searchStr;
	}
	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	public String getSiteStr() {
		return siteStr;
	}
	public void setSiteStr(String siteStr) {
		this.siteStr = siteStr;
	}
	public List<ShoppingItem> getItems() {
		return items;
	}

}
