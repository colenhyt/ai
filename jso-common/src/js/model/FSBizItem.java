package js.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import easyshop.model.Item;


public class FSBizItem extends Item {
	private String addStr,tel,mobile,sImgUrl;
	
	private Date pubDate,deadDate;
	private int units,count;
	private FSEnterItem enter;
	private String bizDesc;
	private String validDays;
	private String countStr;
	private String tradeType,contactor;
	private Set<FSTrust> trusts=new HashSet<FSTrust>();
	private Set<FSTrust> trustsBuy=new HashSet<FSTrust>();
	private int validateDays=-1;
	
	public FSBizItem(){
		super(null);
	}
	public String getAddStr() {
		return addStr;
	}

	public void setAddStr(String addStr) {
		this.addStr = addStr;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getTel() {
		if (tel!=null&&tel.startsWith("+86"))
			tel=tel.substring(1);
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public FSEnterItem getEnter() {
		return enter;
	}

	public void setEnter(FSEnterItem enter) {
		this.enter = enter;
	}

	public String toString(){
		if (name!=null){
			if (pubDate!=null)
				return name+pubDate;
			else
				return name;
		}
		return null;
	}
	public String getSImgUrl() {
		return sImgUrl;
	}
	public void setSImgUrl(String imgUrl) {
		sImgUrl = imgUrl;
	}
	public int getUnits() {
		return units;
	}
	public void setUnits(int queryCount) {
		this.units = queryCount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getBizDesc() {
		return bizDesc;
	}
	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}
	public String getValidDays() {
		return validDays;
	}
	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}
	
	public String getCountStr() {
		return countStr;
	}
	public void setCountStr(String countStr) {
		this.countStr = countStr;
	}
	public Set<FSTrust> getTrusts() {
		return trusts;
	}
	public Set<FSTrust> getTrustsBuy() {
		return trustsBuy;
	}
	public void setTrustsBuy(Set<FSTrust> trustsBuy) {
		this.trustsBuy = trustsBuy;
	}
	public Date getDeadDate() {
		return deadDate;
	}
	public void setDeadDate(Date deadDate) {
		this.deadDate = deadDate;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public int getValidateDays() {
		return validateDays;
	}
	public void setValidateDays(int validateDays) {
		this.validateDays = validateDays;
	}
	

	public String getItemKey() {
		if (getItemUrl()!=null){
			if (pubDate!=null)
			return "name:"+name+"$itemUrl:"+getItemUrl()+"$pubDate:"+pubDate;
			else if (deadDate!=null)
				return "name:"+name+"$itemUrl:"+getItemUrl()+"$deadDate:"+deadDate;
		}else if (getEnter()!=null){
			if (pubDate!=null)
				return "name:"+name+"$enter:"+getEnter().getName()+"$pubDate:"+pubDate;
			else if (deadDate!=null)
				return "name:"+name+"$enter:"+getEnter().getName()+"$deadDate:"+deadDate;
		}
		return null;
	}	
}
