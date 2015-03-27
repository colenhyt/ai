package cn.hd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 工具类
 * @author hd
 *
 */
public class PropertiesUtil {

	//获取配置文件的配置
	public static String getConfig(String path,String name){
		FileInputStream sins=null;
		String value="";
		try {
			Properties loginprop = new Properties();
			sins = new FileInputStream(path);
			loginprop.load(sins);
			value=loginprop.getProperty(name);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (sins!=null) {
		        try {
					sins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return value;
		}
	}
}
