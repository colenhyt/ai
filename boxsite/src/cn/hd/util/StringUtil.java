package cn.hd.util;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class StringUtil {
	
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!(*^&)%$";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }   
	
	
	public static <T> void json2List(String jsonStr,List<T> strSet,Class cls){
		Set<JSONObject> objs = JSON.parseObject(jsonStr,HashSet.class);
		for (JSONObject obj:objs){
			T t = (T)JSON.parseObject(obj.toJSONString(),cls);
			strSet.add(t);
		}
	}	
	
	public static <T> void json2Set(String jsonStr,Set<T> strSet,Class cls){
		Set<JSONObject> objs = JSON.parseObject(jsonStr,HashSet.class);
		for (JSONObject obj:objs){
			T t = (T)JSON.parseObject(obj.toJSONString(),cls);
			strSet.add(t);
		}
	}	

}
