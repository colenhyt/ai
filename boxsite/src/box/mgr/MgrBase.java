package box.mgr;


import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import box.site.model.WebUrl;
import cn.hd.util.RedisConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.FileUtil;

public class MgrBase {
	public static final String DATA_WEBSITE_DNA = "data_website_dna";
	private Boolean isStart = false;

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
	public String rootPath = "d:/boxsite/data/";
	protected String userFilePath = rootPath+"users.json";
	protected String pagesPath = rootPath+"pages/";
	public String traniningpath = rootPath+"training/";
	protected String itemPath = rootPath+"items/";
	protected String listPath = rootPath+"list/";
	protected String viewPath = rootPath+"views/";
	protected String dictPath = rootPath+"dict/";
	protected String imgPath = rootPath+"pics/";
	protected String termPath = rootPath+"terms/";
	public String dnaPath = rootPath+"dna/";
	public String sourceFile = "source.sites";
	public int pageCount = 0;
	public int processThreadWaitTime = 0;
	protected String sitePath = rootPath+"sites/";

	public MgrBase(){
		String path = "config/";
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
		if (cfgObj.containsKey("sourceFile"))
			sourceFile = cfgObj.getString("sourceFile");
		if (cfgObj.containsKey("pageCount"))
			pageCount = cfgObj.getInteger("pageCount");
		if (cfgObj.containsKey("processThreadWaitTime"))
			processThreadWaitTime = cfgObj.getIntValue("processThreadWaitTime");
		String rpath = cfgObj.getString("rootPath");
		if (rpath!=null){
			rootPath = rpath;
			userFilePath = rootPath+"users.json";
			pagesPath = rootPath+"pages/";
			traniningpath = rootPath+"training/";
			itemPath = rootPath+"items/";
			listPath = rootPath+"list/";	
			dictPath = rootPath+"dict/";
			imgPath = rootPath+"pics/";
			dnaPath = rootPath+"dna/";
			sitePath = rootPath+"sites/";
			viewPath = rootPath+"views/";
			termPath = rootPath+"terms/";
		}
		String cfgstr0 = cfgObj.getString("redisCfg");
		if (cfgstr0!=null)
		redisCfg = JSON.parseObject(cfgstr0, RedisConfig.class);
		
	}

	public String getRootPath(){
		return rootPath;
	}
	
	public void process(){
		
	}
	
	protected Map<String,WebUrl> _getFileUrls(String filePath,String sitekey){
		File urlfile = new File(filePath);
		if (!urlfile.exists()) return null;
		
		String content = FileUtil.readFile(filePath);
		Map<String,JSONObject> urls = (Map<String,JSONObject>)JSON.parse(content);
		Map<String,WebUrl> siteurls2 = new HashMap<String,WebUrl>();
		for (JSONObject json:urls.values()){
			WebUrl item = JSON.parseObject(json.toJSONString(), WebUrl.class);
			if (item==null||item.getText()==null||item.getText().trim().length()<=0) continue;
			String ppath = pagesPath + sitekey + "/"+ item.getUrl().hashCode()+".html";
			File ff = new File(ppath);
			if (!ff.exists()) continue;
			
			siteurls2.put(item.getUrl(),item);
		}		
		return siteurls2;
	}}
