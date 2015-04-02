/*
 * Created on 2005-4-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package js.model;

import java.util.Properties;

import easyshop.model.Item;


/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HSIItem extends Item  {
	private float _price;
	private String _name;
	private float _commonPrice;
	private float vipPrice;
    private String author,producer;
    private String isbn="-1";
    private String summary,outline;
    private String urlKey;
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

    private int errorStatus;
    
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
	/**
	 * @return 返回 publisher。
	 */
	public String getProducer() {
		return producer;
	}
	/**
	 * @param publisher 要设置的 publisher。
	 */
	public void setProducer(String publisher) {
		this.producer = publisher;
	}
    public float getVipPrice() {
        return vipPrice;
    }
    public void setVipPrice(float vipPrice) {
        this.vipPrice = vipPrice;
    }
	private Properties _props;
	private String imgUrl;
	private String smallImgUrl;
	

    public String getSmallImgUrl() {
		return smallImgUrl;
	}
	public void setSmallImgUrl(String smallImgUrl) {
		this.smallImgUrl = smallImgUrl;
	}
	public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
	public Properties getProperties() {
		return _props;
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


}
