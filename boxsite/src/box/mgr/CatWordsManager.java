package box.mgr;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

import es.util.FileUtil;

public class CatWordsManager extends MgrBase {
	private static CatWordsManager uniqueInstance = null;

	public static CatWordsManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new CatWordsManager();
		}
		return uniqueInstance;
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
		if (typestr==null){
			String catstr = req.getParameter("catstr");
			String content = FileUtil.readFile(traniningpath+catstr+".data");
			List<String> words = new ArrayList<String>();
			String[] strs = content.split(",");
			for (String str:strs){
				if (str.trim().length()<0)continue;
				words.add(str);
			}
			return JSON.toJSONString(words);			
		}else if (typestr.equals("2")){
			String cat = req.getParameter("cat");
			String level = req.getParameter("level");
			String wordstr = req.getParameter("catwords");
			log.warn(wordstr);
		}
		return null;
	}
}
