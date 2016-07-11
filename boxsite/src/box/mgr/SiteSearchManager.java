package box.mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import us.codecraft.webmagic.Spider;
import box.site.processor.ListSearchProcessor;
import box.site.processor.ProcessCallback;
import cl.util.FileUtil;

import com.alibaba.fastjson.JSON;

public class SiteSearchManager extends MgrBase implements ProcessCallback{
	Map<String,List<String>> wordsMap = Collections.synchronizedMap(new HashMap<String,List<String>>());
	Queue<String> wordsQueue = new LinkedBlockingQueue<String>();
	Set<String> serchEngines = Collections.synchronizedSet(new HashSet<String>());
	Set<String> doneWordSet = Collections.synchronizedSet(new HashSet<String>());
	Map<String,Spider> searchSpiders = Collections.synchronizedMap(new HashMap<String,Spider>());

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void init(){
		List<File> wordFiles = FileUtil.getFiles(listPath);
		for (File f:wordFiles){
			int index = f.getName().lastIndexOf(".wordgroup");
			if (index<0) continue;
			String key = f.getName().substring(0,index);
			String content = FileUtil.readFile(f);
			List<String> wordList = (List<String>)JSON.parse(content);
			wordsMap.put(key, wordList);
			wordsQueue.addAll(wordList);
		}
		
		serchEngines.add("baidu");
		serchEngines.add("bing");
		serchEngines.add("sogou");
	}
	
	//两两拆分到wordsMap
	public void addNewWords(String key,String wordlist){
		Set<String> words =new HashSet<String>();
		String[] strs = wordlist.split(",");
		for (int i=0;i<strs.length;i++){
			for (int j=i+1;j<strs.length;j++){
				words.add(strs[i]+","+strs[j]);
			}
		}
		
		List<String> currstrs = wordsMap.get(key);
		if (currstrs==null){
			String pageContent = JSON.toJSONString(words);
			FileUtil.writeFile(listPath+key+".wordgroup",pageContent);
			currstrs = new ArrayList<String>();
			currstrs.addAll(words);
			wordsMap.put(key, currstrs);
			wordsQueue.addAll(words);
		}
		
	}
	
	public void onSpiderDone(String sitekey){
		Spider spider = searchSpiders.get(sitekey);
		if (spider!=null){
			spider.stop();
			spider.close();
			searchSpiders.remove(sitekey);
		}
		
		//next word search:
		if (searchSpiders.size()<=0){
			process();
		}
	}
	
	public int querySiteCount(String searchWord,String sitekey){
		String content = FileUtil.readFile(sitePath + sitekey+"/"+searchWord+".sites");
		if (content!=null&&content.trim().length()>0){
			List<String> durls = (List<String>)JSON.parse(content);
			return durls.size();		
		}	
		
		return 0;
	}
	
	public void process(){
		String searchWord = null;
		while (searchWord==null){
			searchWord = wordsQueue.poll();
			doneWordSet.add(searchWord);
			wordsQueue.peek();
		}
		
		if (searchWord==null){
			log.warn("no word found,system exit ");
			System.exit(0);
			return;
		}
		
		for (String engine:serchEngines){
			System.out.println("spider :"+engine);
			ListSearchProcessor p1 = new ListSearchProcessor();
			p1.init(engine, searchWord, 5,this);
			Spider spider = Spider.create(p1);
			searchSpiders.put(engine, spider);
			spider.runAsync();
		}
		
	}
}
