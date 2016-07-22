package box.mgr;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import box.site.SitePageDealing;
import box.site.db.ShoppingService;
import box.site.model.Shoppingdata;
import box.site.model.Website;
import box.site.model.Websitekeys;
import box.site.model.Websitewords;
import cn.hd.util.RedisClient;
import cn.hd.util.RedisConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.FileUtil;

public class ShoppingManager extends MgrBase{
	public static final String DATA_WEBSITE_DNA = "data_website_dna";
	
	protected Logger  log = Logger.getLogger(getClass()); 
	DataThread dataThread;
	private int nextWordId = 0;
	private int pageCount = 20;
	private int nextWebsiteId = 0;

	private Map<String,Website>			siteMap;
	private Map<Integer,Website>			siteMapById;
	private Map<String,Websitewords>		wordsMap;
	private Map<String,Websitekeys>		keysMap;
	private Set<String> 	wordRelationKeys;
	private SitePageDealing dealing;
	private RedisClient client;
	
	private static ShoppingManager uniqueInstance = null;

	public static ShoppingManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ShoppingManager();
		}
		return uniqueInstance;
	}
	

	
	public ShoppingManager(){
		String path = "/root/";
		URL  res = Thread.currentThread().getContextClassLoader().getResource("/");
		String cfgstr = FileUtil.readFile(path + "config.properties");
		if (cfgstr == null || cfgstr.trim().length() <= 0) {
			if (res==null){
				log.warn("game start failed: "+path);
				return;
			}
			cfgstr = FileUtil.readFile(res.getPath() + "config.properties");
			if (cfgstr==null|| cfgstr.trim().length() <= 0){
			log.warn("game start failed: "+path);
			return;
			}
		}
		JSONObject cfgObj = JSON.parseObject(cfgstr);
		String cfgstr0 = cfgObj.getString("redisCfg");
		RedisConfig redisCfg = JSON.parseObject(cfgstr0, RedisConfig.class);
		
		dataThread = new DataThread(redisCfg);
		dataThread.start();
		
		client = new RedisClient(redisCfg);
		
	}
	
	public String getDataStr(int typeid){
		ShoppingService service = new ShoppingService();
		List<Shoppingdata> items = service.getData(typeid);
		return JSON.toJSONString(items);
	}
}
