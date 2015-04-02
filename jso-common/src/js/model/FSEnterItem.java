/*
 * Created on 2005-4-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package js.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import easyshop.model.Item;


/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FSEnterItem extends Item  {
	private String cName;
    private String owner,contactor;
    private String summary,outline;
    private String mobile=null;
    private int income,csize;
    private String fund,fax,tel,postCode;
    private Set<FSTrust> trusts=new HashSet<FSTrust>();
    private String webSite,logoName,address,email,operation,city;
    private String urlKey;
    private String biz,mode;
    private Date borned;
    private boolean openEmail;
    
	public FSEnterItem(){
		
	}
	
	public FSEnterItem(String contactor,String tel,String fax){
		this(null,contactor,tel,fax,null,null,null);
	}
	
	public FSEnterItem(String name,String _contactor,String _tel,String _fax,String address,String post,String site){
		this(name,_contactor,_tel,_fax,address,post,site,null,null,null);
	}

	public FSEnterItem(String _name,String _contactor,String _tel,String _fax,String _address,String _postCode,String _webSite,String _biz,String _mode,Date _borned){
		cName=_name;
		contactor=_contactor;
		tel=_tel;
		fax=_fax;
		address=_address;
		postCode=_postCode;
		webSite=_webSite;
		biz=_biz;
		mode=_mode;
		borned=_borned;		
	}
	
	
	public String toString(){
//		if (cName!=null){
//			if (tel!=null)
//				return cName+tel;
//			else if (mobile!=null)
//				return cName+mobile;
//			else
//				return cName;
//		}
		return cName;
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
	public String getOwner() {
		return owner;
	}
	/**
	 * @param author 要设置的 author。
	 */
	public void setOwner(String author) {
		this.owner = author;
	}

	/**
	 * @return 返回 publisher。
	 */
	public String getContactor() {
		return contactor;
	}
	/**
	 * @param publisher 要设置的 publisher。
	 */
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	private Properties _props;
	private String imgUrl;
	

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
		return cName;
	}
	
	public void setName(String name){
		cName=name;
	}

	public String getOutline() {
		if (outline!=null&&outline.length()>3000)
			outline=outline.substring(0,3000)+"....";
		return outline;
	}
	public void setOutline(String outline) {
		this.outline = outline;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCsize() {
		return csize;
	}
	
	public int getBizType(){
		return BIZ_TYPE_COMPANY;
	}
	
	public void setCsize(int csize) {
		this.csize = csize;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	public String getLogoName() {
		return logoName;
	}
	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getTel() {
		if (tel!=null&&tel.startsWith("+86"))
			tel=tel.substring(1);
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Date getBorned() {
		return borned;
	}
	public void setBorned(Date bornedYear) {
		this.borned = bornedYear;
	}
	public String getFund() {
		return fund;
	}
	public void setFund(String fund) {
		this.fund = fund;
	}
	public Set<FSTrust> getTrusts() {
		return trusts;
	}

	public String getBiz() {
		return biz;
	}

	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTrustsString() {
		StringBuffer str=new StringBuffer();
			for (Iterator it=trusts.iterator();it.hasNext();){
				FSTrust trust=(FSTrust)it.next();
				str.append(trust.getTypeName()+":"+trust.getValue()+";");
		}
			
		return str.toString();
	}

	public boolean isOpenEmail() {
		return openEmail;
	}

	public void setOpenEmail(boolean openEmail) {
		this.openEmail = openEmail;
	}


}
