package box.site.getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

public class SiteContentGetterFactory {

	public static ISiteContentGetter findGetter(String sitekey,String basicFilePath){
		File file = new File(basicFilePath+"dna/"+sitekey+".dna");
		ISiteContentGetter getter = null;
		if (file.exists()){
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
		        ObjectInputStream ois = new ObjectInputStream(fis);  
		        getter = (ISiteContentGetter) ois.readObject(); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			getter = new BasicSiteContentGetter();
			getter.setSitekey(sitekey);
		}
		return getter;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
