package box.mgr;


import java.net.URL;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hd.util.FileUtil;
import cn.hd.util.RedisConfig;

public class MgrBase extends java.util.TimerTask {
	public static final String DATA_WEBSITE_DNA = "data_website_dna";
	private Boolean isStart;

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
	protected String rootPath = "c:/boxsite/data/";
	protected String userFilePath = rootPath+"users.json";
	protected String pagesPath = rootPath+"pages/";
	protected String traniningpath = rootPath+"training/";
	protected String itemPath = rootPath+"items/";

	public MgrBase(){
		String path = "src/";
		URL  res = Thread.currentThread().getContextClassLoader().getResource("/");
		String cfgstr = FileUtil.readFile(path + "config.properties");
		if (cfgstr == null || cfgstr.trim().length() <= 0) {
			if (res==null){
				log.warn("boxsite config file could not find: "+cfgstr);
				return;
			}
			cfgstr = FileUtil.readFile(res.getPath() + "config.properties");
			if (cfgstr==null|| cfgstr.trim().length() <= 0){
			log.warn("boxsite start failed: "+path);
			return;
			}
		}
		cfgObj = JSON.parseObject(cfgstr);
		String cfgstr0 = cfgObj.getString("redisCfg");
		redisCfg = JSON.parseObject(cfgstr0, RedisConfig.class);
		
	}

	public void update(){
		
	}
	
	@Override
	public void run() {
		this.update();
		
	}


	public void start(){
		if (isStart) return;
		
		log.info("Manager start....");
		
		isStart = true;
		
		java.util.Timer timer = new java.util.Timer(true);  
		timer.schedule(this, 3000,3000);   		
	}}
