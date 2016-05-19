package box.site.model;

import java.util.Map;
import java.util.Vector;

public class WebsiteDNA {
	private String domainName;
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	private Vector<String> mainUrls;
	public Vector<String> getMainUrls() {
		return mainUrls;
	}
	public void setMainUrls(Vector<String> mainUrls) {
		this.mainUrls = mainUrls;
	}

	private Vector<String> listUrlDNAs;
	private Vector<String> itemUrlDNAs;	
	public Vector<String> getListUrlDNAs() {
		return listUrlDNAs;
	}
	public void setListUrlDNAs(Vector<String> listUrlDNAs) {
		this.listUrlDNAs = listUrlDNAs;
	}
	private Vector<String> catUrlKeys;
	private Vector<String> itemUrlKeys;
	private Map<String,Integer>  worrdCountMap;
	
	public WebsiteDNA(){
		listUrlDNAs = new Vector<String>();
		itemUrlDNAs = new Vector<String>();
	}
	
	public Vector<String> getItemUrlDNAs() {
		return itemUrlDNAs;
	}
	public void setItemUrlDNAs(Vector<String> itemUrlDNAs) {
		this.itemUrlDNAs = itemUrlDNAs;
	}
	public void addItemUrlDNA(String itemUrl){
		itemUrlDNAs.add(itemUrl);
	}
	
	public void addlistUrlDNA(String listUrl){
		listUrlDNAs.add(listUrl);
	}
	public Map<String, Integer> getWorrdCountMap() {
		return worrdCountMap;
	}
	public void setWorrdCountMap(Map<String, Integer> worrdCountMap) {
		this.worrdCountMap = worrdCountMap;
	}
	public Vector<String> getCatUrlKeys() {
		return catUrlKeys;
	}
	public void setCatUrlKeys(Vector<String> catUrlKeys) {
		this.catUrlKeys = catUrlKeys;
	}
	public Vector<String> getItemUrlKeys() {
		return itemUrlKeys;
	}
	public void setItemUrlKeys(Vector<String> itemUrlKeys) {
		this.itemUrlKeys = itemUrlKeys;
	}
	public String getItemUrlDNA() {
		return itemUrlDNA;
	}
	public void setItemUrlDNA(String itemUrlDNA) {
		this.itemUrlDNA = itemUrlDNA;
	}
	private String itemUrlDNA;
}
