package box.site.processor;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;
import cn.hd.util.StringUtil;

import com.alibaba.fastjson.JSON;

import es.util.FileUtil;

public class ListSearchPipeline extends FilePersistentBase  implements Pipeline{
	protected Logger  log = Logger.getLogger(getClass());
	String sitepath = "data/items/";
	String path = "data/list/";
	private int reTry = 0;

	@Override
	public void process(ResultItems resultItems, Task task) {
		String searchWord = (String)resultItems.get("searchWord");
		Object maxCountStr = resultItems.get("maxCount");
		String siteKey = (String)resultItems.get("siteKey");
		int maxCount = -1;
		if (maxCountStr!=null)
			maxCount = ((Integer)maxCountStr).intValue();
		
		if (path.indexOf(siteKey)<0)
			path += siteKey+"/";
		File folder = new File(path);
		if (!folder.exists())
			FileUtil.mkdir(path);
		
		if (sitepath.indexOf(siteKey)<0)
			sitepath += siteKey+"/";
		
		folder = new File(sitepath);
		if (!folder.exists())
			FileUtil.mkdir(sitepath);
		
		//save sites:
		Set<String> items = (Set<String>)resultItems.get("items");
		log.warn("找到新sites: "+items.size());
		
		Set<String> allitems = new HashSet<String>();
		if (items.size()>0){
			String spath = sitepath +searchWord+".items";
			String content = FileUtil.readFile(spath);
			if (content!=null&&content.trim().length()>0){
				List<String> durls = (List<String>)JSON.parse(content);
				allitems.addAll(durls);			
			}
			
			allitems.addAll(items);
			if (allitems.size()>0)
				FileUtil.writeFile(spath, JSON.toJSONString(allitems));
		}else {
			reTry++;
			if (reTry>20){
				log.warn("尝试次数 20,已无法找到新item");
				System.exit(0);
			}
		}
		
		if (allitems.size()>maxCount&&maxCount>0){
			log.warn("规定数量达到  "+maxCount);
			System.exit(0);
		}
		
		//save search urls:
		Set<String> searchUrls = (Set<String>)resultItems.get("searchUrls");
		if (searchUrls!=null&&searchUrls.size()>0){
			String searchpath = path + searchWord+".json";
			FileUtil.writeFile(searchpath, JSON.toJSONString(searchUrls));
		}
		
		//save done urls:
		Set<String> doneurls = (Set<String>)resultItems.get("doneUrls");
		if (doneurls!=null&&doneurls.size()>0){
			String donepath = path + searchWord+"_done.json";
			FileUtil.writeFile(donepath, JSON.toJSONString(doneurls));
		}
		
		//save related word:
		Set<String> relateWords = (Set<String>)resultItems.get("relateWords");
		if (relateWords!=null&&relateWords.size()>0){
			String relatepath = path + searchWord+"_relate.json";
			FileUtil.writeFile(relatepath, JSON.toJSONString(relateWords));		
		}
	}

}
