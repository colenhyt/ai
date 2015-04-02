/*
 * Created on 2005-3-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cl.util;

import java.util.Properties;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SimpleProperties {
	private Properties props=new Properties();
	
	public void put(int key,int value){
		props.put(String.valueOf(key),String.valueOf(value));
	}
	
	public int get(int key){
		return Integer.valueOf((String)props.getProperty(String.valueOf(key))).intValue();
	}
	
	public void put(String key,int value){
		props.put(key,String.valueOf(value));
	}
	
	public void put(String key,String value){
		props.put(key,value);		
	}
	
	public int get(String key){
		return Integer.valueOf((String)props.getProperty(key)).intValue();		
	}

}
