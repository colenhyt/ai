package box.site.getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;

public class SiteContentGetterFactory {
	protected Logger  log = Logger.getLogger(getClass());

	public static ISiteContentGetter createGetter(String sitekey){
		ISiteContentGetter getter = null;
		sitekey = sitekey.replace(".", "_");
		try {
			Class clazz = Class.forName("box.site.getter."+sitekey+"Getter");
			if (clazz!=null){
				//动态创建对象
				Object obj=clazz.newInstance();
				getter = (ISiteContentGetter)obj;
			}else {
				getter = new BasicSiteContentGetter();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getter;
	}
	
	public static void main(String[] args) {
		SiteContentGetterFactory.createGetter("BasicSiteContent");

	}

}
