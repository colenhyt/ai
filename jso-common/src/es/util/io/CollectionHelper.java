/*
 * 创建日期 2006-8-28
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package es.util.io;

import java.util.Iterator;
import java.util.List;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-28
 */
public class CollectionHelper {
	
	public static List filter(String[] strs,List list){
		for (int i=0;i<strs.length;i++){
			while (list.contains(strs[i]))
				list.remove(strs[i]);				
		}
		return list;
	}
	public static String getListNextElement(String pre,List list){
		String next=null;
		for (Iterator it=list.iterator();it.hasNext();){
			String obj=(String)it.next();
			if (obj.equals(pre)&&it.hasNext()){
				next=(String)it.next();
				break;
			}
		}
		return next;
	}
	
	public static String getListElement(String beginKey,List list){
		String next=null;
		for (Iterator it=list.iterator();it.hasNext();){
			String obj=(String)it.next();
			if (obj.startsWith(beginKey)){
				next=obj;
				break;
			}
		}
		return next;
	}	
	public static String getListElement(String pre,List list,int plus){
		String next=null;
		if (pre!=null&&list!=null&&list.size()>0){
		String[] texts=(String[])list.toArray(new String[list.size()]);
		for (int i=0;i<texts.length;i++){
			if (texts[i].equalsIgnoreCase(pre)&&i+plus<texts.length-1)
				next=texts[i+plus];
		}
		}
		return next;
	}
}
