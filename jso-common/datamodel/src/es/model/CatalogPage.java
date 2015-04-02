/*
 * Created on 2005-4-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.model;

import java.util.List;
import java.util.Set;

import cl.cat.content.CatalogBlock;
import easyshop.model.Item;
import easyshop.model.ProductItem;
import es.webref.model.PageRef;



/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CatalogPage extends OriginalPage  {
	private List<Item> items;
	private List<ProductItem> productItems;
	private Set<PageRef> itemRefs;
	private Set<CatalogBlock> blocks;
	private int bizType;
	private String tradeType,catRoot;
	private List<PageRef> urlSorts;
	private List<PageRef> catUrlList,itemUrlList;
	
	public List<PageRef> getUrlSorts() {
		return urlSorts;
	}

	public void setUrlSorts(List<PageRef> urlSorts) {
		this.urlSorts = urlSorts;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Set<CatalogBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(Set<CatalogBlock> blocks) {
		this.blocks = blocks;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public Set<PageRef> getItemRefs() {
		return itemRefs;
	}

	public void setItemRefs(Set<PageRef> itemRefs) {
		this.itemRefs = itemRefs;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getCatRoot() {
		return catRoot;
	}

	public void setCatRoot(String catRoot) {
		this.catRoot = catRoot;
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

	public List<ProductItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}


	
}
