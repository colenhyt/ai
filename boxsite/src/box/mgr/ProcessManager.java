package box.mgr;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

import box.site.PageContentGetter;
import box.site.classify.NewsClassifier;
import box.site.model.TopItem;
import box.site.model.WebUrl;
import box.site.processor.MultiPageTask;
import es.util.FileUtil;

public class ProcessManager extends MgrBase {
	private static ProcessManager uniqueInstance = null;
	private Set<String>	sitekeys = new HashSet<String>();
	private boolean inited = false;
	private boolean running = false;
	private int runningSpiderCount = 0;
	private int processStep = 0;
	private NewsClassifier newsClassifier = new NewsClassifier();
	private Map<String,Map<String,WebUrl>> allSiteUrlsMap = new HashMap<String,Map<String,WebUrl>>();
	private Map<Integer,TopItem> processItemsMap = new HashMap<Integer,TopItem>();
	private Map<String,Set<String>> savedUrlsMap = new HashMap<String,Set<String>>();
	private PageContentGetter contentGetter = new PageContentGetter();

	public static ProcessManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ProcessManager();
		}
		return uniqueInstance;
	}

	public void init(){
		if (inited) return;
		
		inited = true;	
		running = true;
		
		List<File> folders = FileUtil.getFolders(pagesPath);
		for (File folder:folders){
			sitekeys.add(folder.getName());
			String urlspath = (pagesPath+folder.getName()+"_urls.json");
			Map<String,WebUrl> siteurls2 = _getFileUrls(urlspath,folder.getName());
			if (siteurls2!=null) {
				allSiteUrlsMap.put(folder.getName(), siteurls2);
			}
			String savedPath = (pagesPath+folder.getName()+"_done_urls.json");
			Set<String> siteurls3 = _getFileUrls2(savedPath);
			if (siteurls3!=null) {
				savedUrlsMap.put(folder.getName(), siteurls3);
			}			
		}		
	}
	
	public Map<Integer,List<TopItem>> processSaveItems(List<TopItem> newItems){

		Map<Integer,List<TopItem>> mapitems = new HashMap<Integer,List<TopItem>>();
		//topitem 入map库:
		for (TopItem item:newItems){
			List<TopItem> catItems = mapitems.get(item.getCat());
			if (catItems==null){
				catItems = new ArrayList<TopItem>();
			}
			catItems.add(item);
			mapitems.put(item.getCat(), catItems);
		}
		
		Date currDate = new Date();
		//按日期入库,一天一个json:
		Calendar c = Calendar.getInstance();
		for (int catid:mapitems.keySet()){
			c.setTime(currDate);
			List<TopItem> citems = mapitems.get(catid);
			
			//topitem 落地
			String datePath = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);
			String itempath = itemPath+catid+"/"+datePath+"/"+currDate.getTime()+"_items.json";			
			FileUtil.writeFile(itempath, JSON.toJSONString(citems));
			
			for (TopItem item2:citems){
			//page移到历史库,删除当前网页 :
			String fileName = item2.getUrl().hashCode()+".html";
			String fileP = pagesPath+item2.getSitekey()+"/"+fileName;
			String pageC = FileUtil.readFile(fileP);
//			FileUtil.writeFile(hispath+item2.getSitekey()+"/"+fileName,pageC);
//			FileUtil.del(fileP);		
			}
		}
		
		return mapitems;
		
	}
	
	public void spiderFinished(String sitekey){
		runningSpiderCount--;
		if (runningSpiderCount>0) return;
		
		processStep = 1;
//		Thread.currentThread().notifyAll();			
	}
	
	public void processSpiders(){
	
		//spider:
		Set<String> sites = new HashSet<String>();
//		sites.add("http://www.tmtpost.com");
//		sites.add("http://www.leiphone.com");
//		sites.add("http://www.huxiu.com");
//		sites.add("http://www.iheima.com/");
//		sites.add("http://www.pintu360.com/");
//		sites.add("http://www.ikanchai.com/");
//		sites.add("http://www.iyiou.com/");
//		sites.add("http://www.techweb.com.cn/");
//		sites.add("http://www.ifanr.com/");
//		sites.add("http://www.cyzone.cn/");
//		sites.add("http://www.sootoo.com/");
		
//		sites.add("http://tech.163.com");
//		sites.add("http://tech.qq.com/");
//		sites.add("http://tech.sina.com.cn/");
		sites.add("http://it.sohu.com");
		
		runningSpiderCount = sites.size();
		for (String site:sites){
			MultiPageTask task = new MultiPageTask(this,site,20);
			Thread t2=new Thread(task);
			t2.start();
		}
		
	
	}
	
	@Override
	public void update(){
		//运转间隔
		final int PROCESS_DURATION = 60 * 60 * 1000;		//1小时
		
		try {
			while (running){
				if (processStep==0){
					this.processSpiders();
					Thread.currentThread().wait();
				}else if (processStep==1){
					int a = 10;
					a++;
//					//文章分类:
//					List<TopItem> newitems = processClassfiy();
//					//save:
//					Map<Integer,List<TopItem>> mapitems = processSaveItems(newitems);
//					//放入当前内存:
//					PageManager.getInstance().pushNewItems(mapitems);					
				}
				
				Thread.sleep(PROCESS_DURATION);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
//		this.process();
	}
	
	//获取正文，入库
	public List<TopItem> processClassfiy(){
		List<File> folders = FileUtil.getFolders(pagesPath);
		
		List<TopItem> newItems = new ArrayList<TopItem>();
		for (File folder:folders){
			String sitekey = folder.getName();
			//获取该站点所有网页正文
			Map<String,WebUrl> urls = allSiteUrlsMap.get(sitekey);
			Set<String> saves = savedUrlsMap.get(sitekey);
			for (String url:urls.keySet()){
				WebUrl item = urls.get(url);
				if (saves!=null&&saves.contains(url)) continue;
				String filePath = pagesPath + sitekey + "/"+ url.hashCode()+".html";
				String pageContent = FileUtil.readFile(filePath);
				//获取正文:
				List<String> contents = contentGetter.getHtmlContent(url,pageContent);
				if (contents==null||contents.size()<=0){
					log.warn("could not get content "+filePath);
					continue;
				}
				//获取标题:
				String title = PageContentGetter.getTitle(pageContent);
				if (title==null||title.trim().length()<=0){
					log.warn("could not get title: "+filePath);
					continue;
				}
				TopItem titem = new TopItem();
				titem.setUrl(url);
				titem.setId(url.hashCode());
				titem.setContent(contents.get(0));
				titem.setHtmlContent(contents.get(1));
				titem.setCtitle(title);
				//尚未分类，即可分类:
				if (item.getCat()<=0) {
					int catid = newsClassifier.testClassify(titem);
					if (catid<=0) continue;
					titem.setCat(catid);
					continue;
				};
				titem.setCat(item.getCat());
				titem.setCrDate(new Date());
				titem.setSitekey(sitekey);
				File f = new File(filePath);
				if (f.exists()){
					titem.setContentTime(f.lastModified());
				}
				newItems.add(titem);
				processItemsMap.put(titem.getId(),titem);
			}
			
			if (newItems.size()<=0){
				continue;
			}
			
			if (saves==null){
				saves = new HashSet<String>();
				
			}
			//入库后处理:
			for (TopItem item2:newItems){
				//放到已完成列表:
				saves.add(item2.getUrl());
				//移除当前url:
				urls.remove(item2.getUrl());
			}
			savedUrlsMap.put(sitekey, saves);
			
			FileUtil.writeFile(pagesPath+sitekey+"_urls.json", JSON.toJSONString(urls));
			FileUtil.writeFile(pagesPath+sitekey+"_done_urls.json", JSON.toJSONString(saves));		
		}
		
		return newItems;

	}

	private Set<String> _getFileUrls2(String filePath){
		File urlfile = new File(filePath);
		if (!urlfile.exists()) return null;
		
		String content = FileUtil.readFile(filePath);
		List<String> urls = (List<String>)JSON.parse(content);
		Set<String> siteurls2 = new HashSet<String>();
		siteurls2.addAll(urls);
		return siteurls2;
	}

	public static void main(String[] args) {
		ProcessManager.getInstance().init();
		
//		ProcessManager.getInstance().update();
		ProcessManager.getInstance().processSpiders();
	}

}
