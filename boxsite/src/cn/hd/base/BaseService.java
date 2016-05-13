package cn.hd.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import cn.hd.util.DataThread;
import cn.hd.util.FileUtil;
import cn.hd.util.MybatisSessionFactory;
import cn.hd.util.RedisClient;
import cn.hd.util.RedisConfig;

import com.alibaba.fastjson.JSON;


/**
 * Service的父类，提供通用方法
 * @author hd
 * 2011-6-14
 */
public class BaseService extends Base{
	public RedisConfig redisCfg;
	public JSONObject cfgObj;
	public RedisClient jedis;
	public DataThread dataThread;
	public static byte DATA_STATUS_INACTIVE = 0;
	public static byte DATA_STATUS_ACTIVE = 1;
	
	public BaseService(){
		String path = "/root/";
		URL  res = Thread.currentThread().getContextClassLoader().getResource("/");
		String cfgstr = FileUtil.readFile(path + "config.properties");
		if (cfgstr == null || cfgstr.trim().length() <= 0) {
			if (res==null){
				return;
			}
			cfgstr = FileUtil.readFile(res.getPath() + "config.properties");
			if (cfgstr==null|| cfgstr.trim().length() <= 0){
			return;
			}
		}
		cfgObj = JSONObject.fromObject(cfgstr);		
		String cfgstr3 = cfgObj.getString("redisCfg");
		redisCfg = JSON.parseObject(cfgstr3, RedisConfig.class);
		jedis = new RedisClient(redisCfg);
		Jedis jj = jedis.getJedis();
		dataThread = new DataThread(redisCfg);
		
	}
	/**
	 * 初始化指定变量
	 * @param objs 需要初始化变量列表
	 */
	protected  void initMapper(String...objs){
		try {
			for(String obj:objs){
				if(obj!=null && obj.trim().length()>0){
					//获取变量的get方法
					Method method=getClass().getMethod("get"+varMethodName(obj), null);
					//调用get方法，判断对象是否已初始化
					if (method.invoke(this, null)==null) {
						//获取get方法返回类型，即变量类型,然后动态创建对象
						initMapperObj(method.getReturnType().toString(), obj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化指定变量initMapperObj
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
	protected  void initMapperObj(String className,String varName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		//获取名全名
		Class clazz=Class.forName(className.replaceFirst("class ", "").replaceFirst("interface ", ""));
		//创建对象
		Object obj=MybatisSessionFactory.getSession().getMapper(clazz);
		//获取变量的set方法
		Method method=getClass().getMethod("set"+varMethodName(varName), clazz);
		//调用set方法
		method.invoke(this, obj);
	}
	
    /**  
     * 从json对象集合表达式中得到一个java对象列表  
     *
     * @param jsonString  
     * @param beanClass  
     * @return  
     */
	public <T> List<T> jsonToBeanList(String jsonString, Class<T> beanClass) {
    	 
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        T bean;
        int size = jsonArray.size();
        List<T> list = new ArrayList<T>(size);
 
        for (int i = 0; i < size; i++) {
 
            jsonObject = jsonArray.getJSONObject(i);
            bean = (T) JSONObject.toBean(jsonObject, beanClass);
            list.add(bean);
 
        }
         
        return list;
 
    }  
	
	
    /**  
     * 把java对象列表转成json 字符串  
     *
     * @param jsonString  
     * @param beanClass  
     * @return  
     */
	public <T> String beanListToJson(List<T> list, Class<T> beanClass) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        T bean;
 
        for (int i = 0; i < list.size(); i++) {
        	bean = list.get(i);
           jsonObject = JSONObject.fromObject(bean);
           jsonArray.add(jsonObject); 
        }
        return jsonArray.toString();
    }   	
	
	
    /**  
     * 把java对象队列转成json 字符串  
     *
     * @param jsonString  
     * @param beanClass  
     * @return  
     */
	public <T> String beanQueueToJson(Queue<T> queue, Class<T> beanClass) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        T bean;
 
        while (queue.size()>0){
        	bean = queue.poll();
           jsonObject = JSONObject.fromObject(bean);
           jsonArray.add(jsonObject); 
        }
        return jsonArray.toString();
    } 	
	
}
