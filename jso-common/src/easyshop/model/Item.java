/*
 * Created on 2005-3-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.model;

import java.io.Serializable;

import bk.biz.memory.util.Cacheable;



/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Item extends Page implements Serializable,Cacheable{
	
	public static final String[] USELESS_WORDS_BUY={"回收","供应","诚聘","招聘","常年求购","出售","收购"};
	public static final String[] USELESS_WORDS_SELL={"求购"};
	public static final String[] USELESS_WORDS_ENTER={};
	
	public final static String TRADETYPE_YEJIN="冶金";
	public final static String TRADETYPE_JIXIE="机械";
	public final static String TRADETYPE_JIANCAI="建材";
	public final static String TRADETYPE_HUAGONG="化工";
	public final static String TRADETYPE_WUJIN="五金";
	public final static String TRADETYPE_XIANGSU="橡塑";
	
	public final static int BIZ_TYPE_NONE=-1;
	public final static int BIZ_TYPE_BUY=0;
	public final static int BIZ_TYPE_SELL=1;
	public final static int BIZ_TYPE_COMPANY=2;
	public final static int BIZ_TYPE_PRODUCT=3;
	public final static int BIZ_TYPE_INFO=4;
	public final static int BIZ_TYPE_QUOTATION=5;
	public final static int BIZ_TYPE_ITEMPS=6;
	public final static int BIZ_TYPE_NEWS=7;
	private int bizType=-1;
	private String itemUrl;
	private int infoType=-1;
	

	public final static int MONEY_TYPE_PAY=0;
	public final static int MONEY_TYPE_FREE=1;
	private long docId;
	private int variety;
	private String parentUrl;
	private String fromSiteId;
	protected String name,summary;

	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Item(){
		
	}
	public Item(String _name){
		name=_name;
	}
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
	public long getDocId() {
		return docId;
	}
	public void setDocId(long docId) {
		this.docId = docId;
	}

	public String getFromSiteId() {
		return fromSiteId;
	}

	public void setFromSiteId(String fromSiteId) {
		this.fromSiteId = fromSiteId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getSummary() {
		if (summary!=null&&summary.length()>2000)
			summary=summary.substring(0,2000)+"....";
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public int getInfoType() {
		return infoType;
	}

	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}


	
}
