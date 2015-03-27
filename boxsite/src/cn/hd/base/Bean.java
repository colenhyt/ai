package cn.hd.base;

import net.sf.json.JSONObject;

public class Bean {
	public String toString(){
		JSONObject obj = JSONObject.fromObject(this);	
		return obj.toString();
	}
	
	public static Object toBean(String jsonObj,Class beanClass){
		JSONObject ppObj = JSONObject.fromObject(jsonObj);
		return JSONObject.toBean(ppObj,beanClass);
	}
}
