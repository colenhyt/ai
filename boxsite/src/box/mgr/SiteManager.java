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
	
	public static String getHotwords(){
		SiteService service = new SiteService();
		List<Websitewords> list = service.getDonewords();
		return JSON.toJSONString(list);
	}
	
	
	public static String getSiteWordlist(){
		SiteService service = new SiteService();
		List<Websitewords> list = service.getDonewords();
		return JSON.toJSONString(list);
	}
	
	public String getSites(int wordid){
		SiteService service = new SiteService();
		List<Website> list = service.getWebsites(wordid);
		return JSON.toJSONString(list);
	}
	
	public String getSites(String word){
		SiteService service = new SiteService();
		List<Website> sites = service.getSites(word);
		if (sites==null){
			//startup spider:
		}
		return JSON.toJSONString(sites);
	}
	
	public int deleteWordid(int wordid,int siteid){
		Websitekeys key = new Websitekeys();
		key.setWordid(wordid);
		key.setSiteid(siteid);
		dataThread.addDeleteKeys(key);
		return 0;
	}
}
