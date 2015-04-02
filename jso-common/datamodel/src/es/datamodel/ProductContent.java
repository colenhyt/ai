/*
 * Created on 2005-10-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.datamodel;

import easyshop.model.StatusConstants;

/**
 * @author Allenhuang
 *
 * created on 2005-10-5
 */
public class ProductContent extends PageContent {
    private float price;
    private float commonPs;
    private String pName;
    private float vipPs;
    private String imgUrl;
    private String priceImg,commonPsImg,vipPsImg;
    private String author,isbn,publisher;
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
		this.isbn = isbn;
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
    private int errorStatus=StatusConstants.ORIGINAL_STATUS_UNKNOWN;
    
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public float getCommonPs() {
        return commonPs;
    }
    public void setCommonPs(float commonPs) {
        this.commonPs = commonPs;
    }
    public String getPName() {
        return pName;
    }
    public void setPName(String name) {
        pName = name;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public float getVipPs() {
        return vipPs;
    }
    public void setVipPs(float vipPs) {
        this.vipPs = vipPs;
    }
    public String getCommonPsImg() {
        return commonPsImg;
    }
    public void setCommonPsImg(String commonPsImg) {
        this.commonPsImg = commonPsImg;
    }
    public String getPriceImg() {
        return priceImg;
    }
    public void setPriceImg(String priceImg) {
        this.priceImg = priceImg;
    }
    public String getVipPsImg() {
        return vipPsImg;
    }
    public void setVipPsImg(String vipPsImg) {
        this.vipPsImg = vipPsImg;
    }
    public int getErrorStatus() {
        return errorStatus;
    }
    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }
}
