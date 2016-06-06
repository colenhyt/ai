package box.site.processor;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;
import cn.hd.util.StringUtil;

import com.alibaba.fastjson.JSON;

import es.util.FileUtil;

public class ListSearchPipeline extends FilePersistentBase  implements Pipeline{
	String sitepath = "data/items/";
	String path = "data/list/";

	@Override
	public void process(ResultItems resultItems, Task task) {
		String searchWord = (String)resultItems.get("searchWord");
		String siteKey = (String)resultItems.get("siteKey");
		
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
		
		String spath = sitepath +searchWord+".items";
		Set<String> allitems = new HashSet<String>();
		String content = FileUtil.readFile(spath);
		if (content!=null&&content.trim().length()>0){
			StringUtil.json2Set(content, allitems,String.class);
		}
		
		allitems.addAll(items);
		if (allitems.size()>0)
			FileUtil.writeFile(spath, JSON.toJSONString(allitems));
		
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
