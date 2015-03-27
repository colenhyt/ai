package cn.hd.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import cn.hd.util.MybatisSessionFactory;
import org.apache.log4j.Logger;  


/**
 * cn.hd.base包下共同方法
 * @author hd
 * 2011-6-14
 */
public class Base {
	protected Logger  log = Logger.getLogger(getClass()); 
	
	/**
	 * 初始化指定变量
	 * @param objs 需要初始化变量列表
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException 
	 */
	public  void init(String...objs){
		try {
			for(String obj:objs){
				if(obj!=null && obj.trim().length()>0){
					//获取变量的get方法
					Method method=getClass().getMethod("get"+varMethodName(obj), null);
					//调用get方法，判断对象是否已初始化
					if (method.invoke(this, null)==null) {
						//获取get方法返回类型，即变量类型,然后动态创建对象
						initObj(method.getReturnType().toString(), obj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化指定变量
	 * @param className 要创建对象的类名
	 * @param varName 变量名
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public  void initObj(String className,String varName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		
		//获取名全名
		Class clazz=Class.forName(className.replaceFirst("class ", "").replaceFirst("interface ", ""));
		//动态创建对象
		Object obj=clazz.newInstance();
		//获取变量的set方法
		Method method=getClass().getMethod("set"+varMethodName(varName), clazz);
		//调用set方法
		method.invoke(this, obj);
	}
	/**
	 * 把变量名第一个字设置为大写
	 * @return
	 */
	public String varMethodName(String varName){
		if (varName!=null && varName.trim().length()>0) {
			return varName.replaceFirst(varName.substring(0, 1),varName.substring(0, 1).toUpperCase());
		}
		return varName;
	}
	/**
	 * 数据库提交
	 */
	public void DBCommit() {
		MybatisSessionFactory.getSession().commit();
	}
	/**
	 * 数据库连接关闭
	 */
	public void DBConnClose() {
		MybatisSessionFactory.getSession().close();
	}	
	/**
	 * 数据库操作出现异常时的处理
	 * @param e
	 */
	public void DBProException(Exception e) {
		MybatisSessionFactory.proException(e);
	}

	/**
	 * 获得两个时间相差天数
	 * @param type:0:分钟，1:小时,2:天
	 */	
	public static float findDayMargin(long l2,long l1,int type){
		int t = 1000;
		if (type==0)	//minute
			t *= 60;
		else if (type==1)	//hour
			t *= 60*60;
		else if (type==2)	//day
			t *= 60*60*24;
		
		//System.out.println("时间相差: "+l2+","+l1+","+t);
		return (float)((l2 - l1)) / t;
	}
}
