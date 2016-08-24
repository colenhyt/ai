package box.mgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import box.site.model.CatWord;
import box.site.model.Website;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.FileUtil;

public class CatWordsManager extends MgrBase {
	private static CatWordsManager uniqueInstance = null;
	private Map<String,Map<String,CatWord>> catWordMaps = new HashMap<String,Map<String,CatWord>>();

	public static CatWordsManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new CatWordsManager();
		}
		return uniqueInstance;
	}
	
	public CatWordsManager(){
		
	}
	public static void main(String[] args) {
	
//		List<File> dnafiles = FileUtil.getFiles("dna/");
//		for (File file:dnafiles){
//			String content = FileUtil.readFile(file);
//			List<String> urls = (List<String>)JSON.parse(content);
//			String url = "http://www."+file.getName().substring(0,file.getName().indexOf(".json"));
//			SiteDNAManager.getInstance().addSiteItemUrlReg(url, urls.get(0));
//		}
	}
	
	public String queryCatWords(HttpServletRequest req){
		String typestr = req.getParameter("type");
		String catstr = req.getParameter("cat");
		Map<String,CatWord> maps = catWordMaps.get(catstr);
		String wordFileName = traniningpath+catstr+".words";
		if (maps==null){
			String mapstr = FileUtil.readFile(wordFileName);
			maps = new HashMap<String,CatWord>();
			if (mapstr.trim().length()>0){
				Map<String,JSONObject> data = (Map<String,JSONObject>)JSON.parse(mapstr);
				for (String key:data.keySet()){
					JSONObject itemd = data.get(key);
					maps.put(key, (CatWord)JSON.parseObject(itemd.toJSONString(), CatWord.class));
				}
			}
			catWordMaps.put(catstr, maps);
		}
		if (typestr!=null&&typestr.equals("2")){
			String level = req.getParameter("level");
			String wordstr = req.getParameter("catwords");
			List<String> wordlist = (List<String>)JSON.parse(wordstr);
			for (String w:wordlist){
				CatWord cw = new CatWord();
				cw.setCatstr((catstr));
				cw.setLevel(Integer.valueOf(level));
				cw.setWord(w);
				maps.put(w, cw);
			}
			log.warn(catstr+":"+level+":"+wordstr);
			String mapStr = JSON.toJSONString(maps);
			FileUtil.writeFile(wordFileName, mapStr);
		}
		
		List<String> words = new ArrayList<String>();
		String content = FileUtil.readFile(traniningpath+catstr+".data");
		String[] strs = content.split(",");
		for (String str:strs){
			if (str.trim().length()<0)continue;
			if (maps.containsKey(str.trim()))continue;
			words.add(str);
		}
		
		return JSON.toJSONString(words);	
	}
}
