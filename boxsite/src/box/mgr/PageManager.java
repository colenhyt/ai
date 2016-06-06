package box.mgr;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import box.site.model.WebUrl;
import cn.hd.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.FileUtil;

public class PageManager extends MgrBase{
	private static PageManager uniqueInstance = null;
	private Set<String>	sitekeys;
	private Map<String,Map<String,WebUrl>> siteUrls = new HashMap<String,Map<String,WebUrl>>();
	protected Logger  log = Logger.getLogger(getClass()); 
	String path = "c:/boxsite/data/pages/";
	private boolean inited = false;

	public static void main(String[] args) {
		PageManager.getInstance().init();
		String a = PageManager.getInstance().getSiteNotTradingUrls("51cto.com");
		WebUrl w1 = new WebUrl();
		w1.setUrl("aaa");
		WebUrl w2 = new WebUrl();
		w2.setUrl("aaa");
		int c1 = w1.hashCode();
		int c2 = w2.hashCode();
		Set<WebUrl> set = new HashSet<WebUrl>();
		set.add(w1);
		boolean t = w1.hashCode()==w2.hashCode();
		System.out.println(a);
		PageManager.getInstance().addTradingurls("51cto.com", a);

	}

	public static PageManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new PageManager();
		}
		return uniqueInstance;
	}

	public void init(){
		if (inited) return;
		
		inited = true;
		
		sitekeys = new HashSet<String>();
		
		List<File> folders = FileUtil.getFolders(path);
		for (File folder:folders){
			sitekeys.add(folder.getName());
			File urlfile = new File(path+folder.getName()+"_urls.json");
			if (!urlfile.exists()) continue;
			String content = FileUtil.readFile(urlfile);
			Map<String,JSONObject> urls = JSON.parseObject(content,HashMap.class);
			Map<String,WebUrl> siteurls2 = new HashMap<String,WebUrl>();
			for (String url:urls.keySet()){
				JSONObject json = urls.get(url);
				WebUrl item = JSON.parseObject(json.toJSONString(),WebUrl.class);
				if (item.getText()==null||item.getText().trim().length()<=0) continue;
				siteurls2.put(item.getUrl(),item);
				
			}
			siteUrls.put(folder.getName(), siteurls2);
		}
		log.warn("aaa "+siteUrls.toString());
	}
	
	public String getSiteNotTradingUrls(String sitekey){
		Map<String,WebUrl> urls = siteUrls.get(sitekey);
		if (urls!=null){
			Set<WebUrl> notUrls = new HashSet<WebUrl>();
			for (WebUrl url:urls.values()){
				if (url.getCat()<=0)
					notUrls.add(url);
			}
			return JSON.toJSONString(notUrls);
		}
		return null;
	}
	
	public String addTradingurls(String sitekey,String tradingUrlsStr){
		Map<String,WebUrl> urls = siteUrls.get(sitekey);
		if (urls==null){
			urls = new HashMap<String,WebUrl>();
			siteUrls.put(sitekey,urls);
		}
		Set<WebUrl> urls2 = new HashSet<WebUrl>();
		StringUtil.json2Set(tradingUrlsStr, urls2,WebUrl.class);
		for (WebUrl url:urls2){
			urls.put(url.getUrl(),url);
		}
		
		File urlfile = new File(path+sitekey+"_urls.json");
		FileUtil.writeFile(urlfile, JSON.toJSONString(urls));
		return "";
	}
	
}
