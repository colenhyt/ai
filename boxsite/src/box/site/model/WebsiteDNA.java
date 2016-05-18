package box.site.model;

import java.util.Map;
import java.util.Vector;

public class WebsiteDNA {
	private String catUrlDNA;
	private Vector<String> catUrlKeys;
	private Vector<String> itemUrlKeys;
	private Map<String,Integer>  worrdCountMap;
	
	public Map<String, Integer> getWorrdCountMap() {
		return worrdCountMap;
	}
	public void setWorrdCountMap(Map<String, Integer> worrdCountMap) {
		this.worrdCountMap = worrdCountMap;
	}
	public String getCatUrlDNA() {
		return catUrlDNA;
	}
	public void setCatUrlDNA(String catUrlDNA) {
		this.catUrlDNA = catUrlDNA;
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
