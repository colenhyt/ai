package box.site.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	public static ContentDNA read(String sitekey,String basicPath){
		ContentDNA  dna = null;
		try {
			String file = basicPath+"dna/"+sitekey+".dna";
			FileInputStream fis = new FileInputStream(file);
           ObjectInputStream ois = new ObjectInputStream(fis);  
           dna = (ContentDNA) ois.readObject(); 
           ois.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return dna;
	}
	
	public void toSave(String basicPath) {
		String fileName = basicPath+sitekey+".dna";
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream
				(new FileOutputStream (fileName));
			oos.writeObject (this);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException ("Couldn't write classifier to filename "+
					fileName);
		}	
	}	
}
