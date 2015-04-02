/*
 * 创建日期 2006-8-12
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package es.util.url;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Allen Huang
 * 
 * EasyShop 1.0
 * 
 * Date: 2006-8-12
 */
public class URLConstructor {
	public static final String HTTP_START = "http://";

	//把items按照现有的顺序组装成url:
	public static String compose(String[] urlitems){
		StringBuffer str=new StringBuffer();
		if (urlitems!=null){
		for (int i=0;i<urlitems.length;i++){
			str.append(urlitems[i]);
			if (i<urlitems.length-1)
				str.append("/");
		}
		}
		return str.toString();
	}
	//把urlstr按照"/"进行split,第一个item去掉了http,最后一个item包含了文件名和所带的参数:
	public static List parse(String urlStr) {
		String url=urlStr;
		List items = new ArrayList();
		if (url==null)
			return null;
		
		if (!url.startsWith(HTTP_START)&&url.startsWith("/"))
			url=url.substring(1);
		else if (url.startsWith(HTTP_START))
			url = url.substring(HTTP_START.length());

		if (url.indexOf("/")<0){
			items.add(0,url);
			return items;
		}
		
		int i = 0;
		do {
			int index;
			index = url.indexOf("/");
			items.add(i, url.substring(0, index));
			if (url.length()>index)
				url = url.substring(index + 1);
			i++;
		} while (url.indexOf("/") > 0);
		if (url.length()>0)
			items.add(i,url);
		
		return items;
	}

}