package box.mgr;


import java.net.URL;
import java.util.Vector;

import org.apache.log4j.Logger;

import cn.hd.util.FileUtil;
import cn.hd.util.RedisConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MgrBase {
	public static final String DATA_WEBSITE_DNA = "data_website_dna";

	protected Logger  log = Logger.getLogger(getClass()); 
	protected int tick = 0;
	protected boolean startPlayerRedis = false;
	protected final int UPDATE_PERIOD = 20*30;		//20*60: 一小时
	protected final int BATCH_COUNT = 200;
	protected final int UPDATE_PERIOD_BATCH = 40;	//2分钟
	protected Vector<DataThread>	dataThreads;
	protected long toplistTime = 600;
	public RedisConfig redisCfg;
	public JSONObject cfgObj;
	public int maxStockAmount = 100000000;

	public MgrBase(){
		String path = "/root/";
		URL  res = Thread.currentThread().getContextClassLoader().getResource("/");
		String cfgstr = FileUtil.readFile(path + "config.properties");
		if (cfgstr == null || cfgstr.trim().length() <= 0) {
			if (res==null){
				log.warn("boxsite start failed: "+path);
				return;
			}
			cfgstr = FileUtil.readFile(res.getPath() + "config.properties");
			if (cfgstr==null|| cfgstr.trim().length() <= 0){
			log.warn("boxsite start failed: "+path);
			return;
			}
		}
		cfgObj = JSON.parseObject(cfgstr);
		
	}
}
