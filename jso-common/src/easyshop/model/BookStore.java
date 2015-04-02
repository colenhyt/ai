package easyshop.model;

import java.io.Serializable;

public class BookStore implements Serializable{
	protected long bid;
	protected String siteId;
	protected float price;
	protected float commonPrice;
	protected float vipPrice;
	protected String urlStr;
	protected long bsid;
	protected boolean hasStock=false;
	public long getBid() {
		return bid;
	}
	public void setBid(long bid) {
		this.bid = bid;
	}
	public long getBsid() {
		return bsid;
	}
	public void setBsid(long bsid) {
		this.bsid = bsid;
	}
	public float getCommonPrice() {
		return commonPrice;
	}
	public void setCommonPrice(float commonPrice) {
		this.commonPrice = commonPrice;
	}
	public boolean isHasStock() {
		return hasStock;
	}
	public void setHasStock(boolean hasStock) {
		this.hasStock = hasStock;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	public float getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(float vipPrice) {
		this.vipPrice = vipPrice;
	}
}
