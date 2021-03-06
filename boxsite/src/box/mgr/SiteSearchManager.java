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

import box.site.processor.MultiSearchTask;
import box.site.processor.ProcessCallback;

import com.alibaba.fastjson.JSON;

import es.util.FileUtil;
import es.util.string.StringHelper;

public class SiteSearchManager extends MgrBase implements ProcessCallback{
	Map<String,List<String>> wordsMap = Collections.synchronizedMap(new HashMap<String,List<String>>());
	Queue<String> wordsQueue = new LinkedBlockingQueue<String>();
	Set<String> serchEngines = Collections.synchronizedSet(new HashSet<String>());
	Set<String> doneWordSet = Collections.synchronizedSet(new HashSet<String>());
	Map<String,String> searchSpiders = Collections.synchronizedMap(new HashMap<String,String>());
	Set<String> wordlistSet = Collections.synchronizedSet(new HashSet<String>());
	private boolean isStart = false;
	private boolean isInit = false;
	private int MIN_SEARCH_SITE_COUNT = 1;		//最少查找数量

	private static SiteSearchManager uniqueInstance = null;

	public static SiteSearchManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new SiteSearchManager();
		}
		return uniqueInstance;
	}
	
	public static void main(String[] args) {
		SiteSearchManager.getInstance().init();
		SiteSearchManager.getInstance().process();
	}
	
	public void init(){
		if (isInit) return;
		
		isInit = true;
		
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
		
		String content = FileUtil.readFile(listPath+"sites.wordlist");
		if (content.trim().length()>0){
			List<String> words = (List<String>)JSON.parse(content);
			wordlistSet.addAll(words);
		}
		
		serchEngines.add("baidu");
		serchEngines.add("bing");
//		serchEngines.add("sogou");
	}
	
	//两两拆分到wordsMap
	public void addNewWords(String key,String wordlist,boolean startSearch){
		init();
		
		Set<String> words = StringHelper.getStrArray(wordlist, ",");
		
		List<String> currstrs = wordsMap.get(key);
		if (currstrs==null){
			String pageContent = JSON.toJSONString(words);
			FileUtil.writeFile(listPath+key+".wordgroup",pageContent);
			
			
			currstrs = new ArrayList<String>();
			currstrs.addAll(words);
			wordsMap.put(key, currstrs);
			wordsQueue.addAll(words);
		}
		wordlistSet.add(wordlist);
		FileUtil.writeFile(listPath+"sites.wordlist", JSON.toJSONString(wordlistSet));
		
		if (!isStart&&startSearch){
			isStart = true;
			process();
		}
	}
	
	public void onSpiderDone(String sitekey){
		searchSpiders.remove(sitekey);
		log.warn("spider("+sitekey+") search done,currsize:"+searchSpiders.size());
		
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
			searchSpiders.put(engine, engine);
			System.out.println("spider("+engine+") search start for:"+searchWord);
			MultiSearchTask task = new MultiSearchTask(engine,searchWord,MIN_SEARCH_SITE_COUNT);
			Thread t2=new Thread(task);
			t2.start();
		}
		
	}
}
