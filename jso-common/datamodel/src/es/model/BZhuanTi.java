package es.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import easyshop.model.ProductItem;

public class BZhuanTi extends SItem{
	private final String name;
	private String summ,typeDesc,branchDesc;
	private String oriUrlStr;
	private int active=-1,pri=200;
	
	public BZhuanTi(String name){
		this.name=name;
	}
	
	private List<ProductItem> items=new ArrayList<ProductItem>();
	private Set<String> isbns=new HashSet<String>();
	private Set<Long> bids=new HashSet<Long>();	
	
	public List<ProductItem> getItems() {
		return items;
	}
	public String getName() {
		return name;
	}
	public String getSumm() {
		return summ;
	}
	public void setSumm(String desc) {
		this.summ = desc;
	}
	public Set<String> getIsbns() {
		return isbns;
	}
	public Set<Long> getBids() {
		return bids;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getOriUrlStr() {
		return oriUrlStr;
	}
	public void setOriUrlStr(String oriUrlStr) {
		this.oriUrlStr = oriUrlStr;
	}
	public String getBranchDesc() {
		return branchDesc;
	}
	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getPri() {
		return pri;
	}
	public void setPri(int pri) {
		this.pri = pri;
	}
	
}
