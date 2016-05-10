package box.mgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import box.site.db.SiteService;
import box.site.model.Websitekeys;
import cn.hd.util.RedisClient;
import cn.hd.util.RedisConfig;

public class DataThread extends Thread {
	private RedisClient		jedisClient;
	private int updateDuration = 2000;
	private List<Websitekeys>			deleteWebsitekeys;
	protected Logger  log = Logger.getLogger(getClass()); 
	
	public DataThread(){

		deleteWebsitekeys = Collections.synchronizedList(new ArrayList<Websitekeys>());		
	}
	
	/**
	 * 存储任务
	 * @param int playerid
	 * @return 无
	 * */
	public synchronized void addDeleteKeys(Websitekeys key){
		deleteWebsitekeys.add(key);
	}
	
	/**
	 * 异步数据落地
	 * @return 无
	 * */
	public void run() {
		Jedis jedis = null;
		while (1==1){
        	try {
				synchronized(this)
				{				
	        	
	    		if (deleteWebsitekeys.size()>0){
	    			SiteService service2= new SiteService();
		    		service2.deleteWebsitekeys(deleteWebsitekeys);
		    		log.warn("batch delete keys:"+deleteWebsitekeys.size());
		    		deleteWebsitekeys.clear();    	    			
	    		}
	    		
				}
				
//	        		System.out.println("size :"+DataManager.getInstance().playerMaps.size());
				super.sleep(updateDuration);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
	        	jedisClient.returnResource(jedis);
					
				e.printStackTrace();
			}catch (Exception e) {
	        	jedisClient.returnResource(jedis);
				log.error(e.getMessage());
//				if (e instanceof JedisConnectionException) {
//					JedisConnectionException new_name = (JedisConnectionException) e;
//				}else
//					e.printStackTrace();
			}
		}

    }	
}
