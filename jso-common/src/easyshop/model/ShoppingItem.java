/*
 * Created on 2005-3-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.webref.model.PageRef;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ShoppingItem extends Item{
	private float _price;
	private String _name;
	private float _commonPrice;
	private float vipPrice;
    private String author,publisher,pkgDesc;
    private String isbn="-1";
    private String urlKey;
    private boolean hasStock=false;
    private List<ReviewItem> reviews;
    private String summary,outline,authorSumm,editorRecommend;
	private String imgUrl;
	private Set<BookStore> stores=new HashSet<BookStore>();
    private int errorStatus;
    private List<PageRef> parents=new ArrayList();
    private List<PageRef> alsoBuys=new ArrayList();
    
	public List<PageRef> getParents() {
		return parents;
	}    
    public String getUrlKey() {
		return urlKey;
	}
	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}
	public String getSummary() {
		if (summary!=null&&summary.length()>2000)
			summary=summary.substring(0,2000)+"....";
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}	
	private int variety;
	private String parentUrl;
	
    public String getParentUrl() {
		return parentUrl;
	}
	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}
	public int getVariety() {
        return variety;
    }
    public void setVariety(int variety) {
        this.variety = variety;
    }
	/**
	 * @return 返回 author。
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author 要设置的 author。
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return 返回 isbn。
	 */
	public String getIsbn() {
		return isbn;
	}
	/**
	 * @param isbn 要设置的 isbn。
	 */
	public void setIsbn(String isbn) {
		if (isbn!=null&&isbn.trim().length()>0)
			this.isbn = isbn;
	}
	public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }	
	/**
	 * @return 返回 publisher。
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher 要设置的 publisher。
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
 
    public float getVipPrice() {
        return vipPrice;
    }
    public void setVipPrice(float vipPrice) {
        this.vipPrice = vipPrice;
    }
	
	public String getName(){
		return _name;
	}
	
	public void setName(String name){
		_name=name;
	}
	
	public void setPrice(float price){
		_price=price;
	}

	public Set<BookStore> getStores() {
		return stores;
	}
	
	public void setCommonPrice(float cPrice){
		_commonPrice=cPrice;
	}

	public float getPrice(){
		return _price;
	}

	public float getCommonPrice(){
		return _commonPrice;
	}
	public String getOutline() {
		if (outline!=null&&outline.length()>3000)
			outline=outline.substring(0,3000)+"....";
		return outline;
	}
	public void setOutline(String outline) {
		this.outline = outline;
	}
	public String getAuthorSumm() {
		return authorSumm;
	}
	public void setAuthorSumm(String authorSumm) {
		this.authorSumm = authorSumm;
	}
	public String getEditorRecommend() {
		return editorRecommend;
	}
	public void setEditorRecommend(String editorRecommend) {
		this.editorRecommend = editorRecommend;
	}
	public List<ReviewItem> getReviews() {
		return reviews;
	}
	public void setReviews(List<ReviewItem> reviews) {
		this.reviews = reviews;
	}
	public boolean isHasStock() {
		return hasStock;
	}
	public void setHasStock(boolean hasStock) {
		this.hasStock = hasStock;
	}	
	public String getPkgDesc() {
		return pkgDesc;
	}
	public void setPkgDesc(String pkgDesc) {
		this.pkgDesc = pkgDesc;
	}	
	
    /**
     * @return 返回 errorStatus。
     */
    public int getErrorStatus() {
        return errorStatus;
    }
    /**
     * @param errorStatus 要设置的 errorStatus。
     */
    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }
	public List<PageRef> getAlsoBuys() {
		return alsoBuys;
	}	
}
