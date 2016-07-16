package box.site.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import easyshop.html.TagDNA;

public class ContentDNA implements Serializable{
	public Set<String> getItemUrlRegs() {
		return itemUrlRegs;
	}
	public void setItemUrlRegs(Set<String> itemUrlRegs) {
		this.itemUrlRegs = itemUrlRegs;
	}
	public void addItemUrlReg(String urlReg){
		this.itemUrlRegs.add(urlReg);
	}
	
	public List<TagDNA> getTagDNAs() {
		return tagDNAs;
	}
	public void setTagDNAs(List<TagDNA> tagDNAs) {
		this.tagDNAs = tagDNAs;
	}
	
	public void addTagDNA(TagDNA tagDNA){
		this.tagDNAs.add(tagDNA);
	}
	
	protected Set<String> itemUrlRegs = new HashSet<String>();
	protected List<TagDNA> tagDNAs = new ArrayList<TagDNA>();
	protected String sitekey;
	public String getSitekey() {
		return sitekey;
	}
	public void setSitekey(String sitekey) {
		this.sitekey = sitekey;
	}
}
