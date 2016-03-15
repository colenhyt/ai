package cn.hd.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class DataThread extends Thread {
	private RedisClient		jedisClient;
	private Map<Integer,String>		updateSavingMap;
	
	private Map<Integer,Vector<String>>		playerLogs;
	private Map<Integer,String>		updateInsureMap;
	private Map<Integer,String>		updateStockMap;
	private int updateDuration = 500;
	public void setUpdateDuration(int updateDuration) {
		this.updateDuration = updateDuration;
	}
	
	private Vector<String>			signinVect;
	private Vector<Integer>			doneQuestVect;
	protected Logger  log = Logger.getLogger(getClass()); 
	
	public DataThread(RedisConfig cfg){
		jedisClient = new RedisClient(cfg);
		
		
		updateSavingMap  = new HashMap<Integer,String>();
		
		updateInsureMap = new HashMap<Integer,String>();
		
		updateStockMap = new HashMap<Integer,String>();
		
		playerLogs = new HashMap<Integer,Vector<String>>();
		
		signinVect = new Vector<String>();
		doneQuestVect = new Vector<Integer>();
	}
	
	/**
	 * 存储存款
	 * @param int playerid
	 * @param String 玩家保险数据
	 * @return 无
	 * */
	public synchronized void updateInsure(int playerid,String jsonInsures){
		updateInsureMap.put(playerid, jsonInsures);
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
//					System.out.println(jedisClient.jedis.isConnected());
//					if (!jedisClient.jedis.isConnected())
//						jedisClient.jedis.connect();
					jedis = jedisClient.getJedis();
					if (jedis==null){
						continue;
					}
	        		Pipeline p = jedis.pipelined();

        		p.sync();
        		jedisClient.returnResource(jedis);
	    		
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
