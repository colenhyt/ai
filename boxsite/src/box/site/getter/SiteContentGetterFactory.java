package box.site.getter;

import org.apache.log4j.Logger;

public class SiteContentGetterFactory {
	protected Logger  log = Logger.getLogger(getClass());

	private static String convert(String sitekey){
		sitekey = sitekey.replace("1", "a");
		sitekey = sitekey.replace("2", "b");
		sitekey = sitekey.replace("3", "c");
		sitekey = sitekey.replace("4", "d");
		sitekey = sitekey.replace("5", "e");
		sitekey = sitekey.replace("6", "f");
		sitekey = sitekey.replace("7", "g");
		sitekey = sitekey.replace("8", "h");
		sitekey = sitekey.replace("9", "i");
		sitekey = sitekey.replace("0", "j");
		sitekey = sitekey.replace("-", "_");
		sitekey = sitekey.replace(".", "_");
		return sitekey;
	}
	
	public static ISiteContentGetter createGetter(String sitekey){
		return SiteContentGetterFactory.createGetter(sitekey,null);
	}
	
	public static ISiteContentGetter createGetter(String sitekey,String dnaPath){
		ISiteContentGetter getter = null;
		sitekey = convert(sitekey);
		try {
			Class clazz = Class.forName("box.site.getter."+sitekey+"Getter");
			//动态创建对象
			Object obj=clazz.newInstance();
			getter = (ISiteContentGetter)obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("could not find class for "+sitekey);
		}
		if (getter==null){
			getter = new BasicSiteContentGetter(dnaPath);
		}
		return getter;
	}
	
	public static void main(String[] args) {
		SiteContentGetterFactory.createGetter("BasicSiteContent");

	}

}
