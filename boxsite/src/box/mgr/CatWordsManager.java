package box.mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import box.site.model.CatWord;

import com.alibaba.fastjson.JSON;

import es.util.FileUtil;

public class CatWordsManager extends MgrBase {
	private static CatWordsManager uniqueInstance = null;
	private Map<String,Set<String>> catWordMaps = new HashMap<String,Set<String>>();

	public static CatWordsManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new CatWordsManager();
		}
		return uniqueInstance;
	}
	
	public CatWordsManager(){
		List<File> wordFiles = FileUtil.getFiles(traniningpath,"txt");
		for (File f:wordFiles){
			String mapstr = FileUtil.readFile(f);
			Set<String> wordset = new HashSet<String>();
			if (mapstr.trim().length()>0){
				String[] words = mapstr.split(",");
				for (String word:words){
					if (word.trim().length()<=0) continue;
					wordset.add(word);
				}
			}	
			String name = f.getName().substring(0,f.getName().indexOf(".txt"));
			catWordMaps.put(name, wordset);
		}
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
	
	public String queryPagesSites(HttpServletRequest req){
		String typestr = req.getParameter("type");
		List<String> strs = new ArrayList<String>();
		List<File> files = FileUtil.getFiles(rootPath+"pages2/", "titles");
		if (typestr.equals("1")){
			for (File f:files){
				strs.add(f.getName());
			}
		}else if (typestr.equals("2")){
			String sitekey = req.getParameter("sitekey");
			String content = FileUtil.readFile(rootPath+sitekey+".titles");
			String[] titles = content.split("\n");
			for (String t:titles){
				strs.add(t);
			}
		}
		return JSON.toJSONString(strs);
	}
	
	public String queryCatWords(HttpServletRequest req){
		String typestr = req.getParameter("type");
		String catstr = req.getParameter("cat");
		Set<String> catwordSet = catWordMaps.get(catstr);
		String wordFileName = traniningpath+catstr+".txt";
		if (catwordSet==null){
			catwordSet = new HashSet<String>();
			catWordMaps.put(catstr, catwordSet);
		}
		if (typestr!=null&&typestr.equals("2")){
			String level = req.getParameter("level");
			String wordstr = req.getParameter("catwords");
			List<String> wordlist = (List<String>)JSON.parse(wordstr);
			catwordSet.addAll(wordlist);
			log.warn(catstr+":"+level+":"+wordstr);
			String mapStr = "";
			for (String word:catwordSet){
				mapStr += word+",";
			}
			FileUtil.writeFile(wordFileName, mapStr);
		}
		
		List<String> words = new ArrayList<String>();
		String content = FileUtil.readFile(traniningpath+catstr+".tdata");
		String[] strs = content.split(",");
		for (String str:strs){
			if (str.trim().length()<0)continue;
			boolean hasCat = false;
			for (String catkey:catWordMaps.keySet()){
				Set<String> mmaps = catWordMaps.get(catkey);
				if (mmaps.contains(str.trim())){
					hasCat = true;
					break;
				}
			}
			if (hasCat)continue;
			words.add(str);
		}
		
		return JSON.toJSONString(words);	
	}
}
