package box.mgr;

import java.util.List;

import box.site.db.SiteService;
import box.site.model.Website;
import box.site.model.Websitekeys;
import box.site.model.Websitewords;

import com.alibaba.fastjson.JSON;

public class SiteManager {
	DataThread dataThread;

	private static SiteManager uniqueInstance = null;

	public static SiteManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new SiteManager();
		}
		return uniqueInstance;
	}
	
	public SiteManager(){
		dataThread = new DataThread();
		dataThread.start();
	}
	
	public static String getSiteWords(){
		SiteService service = new SiteService();
		List<Websitewords> words = service.getDonewords();
		return JSON.toJSONString(words);
	}
	
	public static List<Websitewords> getSiteWordlist(){
		SiteService service = new SiteService();
		return service.getDonewords();
	}
	
	public static List<Website> getSites(int wordid){
		SiteService service = new SiteService();
		return service.getWebsites(wordid);
	}
	
	public int deleteWordid(int wordid,int siteid){
		Websitekeys key = new Websitekeys();
		key.setWordid(wordid);
		key.setSiteid(siteid);
		dataThread.addDeleteKeys(key);
		return 0;
	}
}
