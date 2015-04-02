/*
 * 创建日期 2006-8-14
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package easyshop.downloadhelper;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-14
 */
public class HttpConnectionHelper {
	public static final String charKey="charset=";
	
	public static String getContentType(String httpContentType){
		String type=null;
		if (httpContentType!=null&&httpContentType.length()>0&&httpContentType.toLowerCase().indexOf(";")>0){
			type=httpContentType.substring(0,httpContentType.toLowerCase().indexOf(";")).trim();
		}
		return type;
		
	}
	public static String getCharSet(String httpContentType){
		String charSet=null;
		if (httpContentType!=null){
			String str=httpContentType.toLowerCase();
		if (str.length()>0&&str.indexOf(charKey)>0){
			charSet=str.substring(str.indexOf(charKey)+charKey.length()).trim();
		}
	}
		return charSet;
	}

}
