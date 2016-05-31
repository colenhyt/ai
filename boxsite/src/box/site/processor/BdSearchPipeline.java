package box.site.processor;

import java.util.HashSet;
import java.util.Set;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;
import box.site.model.Website;

import com.alibaba.fastjson.JSON;

import es.util.FileUtil;
import es.util.string.StringHelper;

public class BdSearchPipeline extends FilePersistentBase  implements Pipeline{
	String sitepath = "data/sites/";
	String path = "data/baidu/";

	@Override
	public void process(ResultItems resultItems, Task task) {
		String searchWord = (String)resultItems.get("searchWord");
		
		//save sites:
		Set<Website> sites = (Set<Website>)resultItems.get("sites");
		
		String spath = sitepath+searchWord+".sites";
		Set<Website> allsites = new HashSet<Website>();
		String content = FileUtil.readFile(spath);
		if (content!=null&&content.trim().length()>0){
			StringHelper.json2Set(content, allsites);
		}
		allsites.addAll(sites);
		if (allsites.size()>0)
			FileUtil.writeFile(spath, JSON.toJSONString(allsites));
		
		//save search urls:
		Set<String> searchUrls = (Set<String>)resultItems.get("searchUrls");
		String searchpath = path + searchWord+".json";
		FileUtil.writeFile(searchpath, JSON.toJSONString(searchUrls));
		
		//save done urls:
		Set<String> doneurls = (Set<String>)resultItems.get("doneUrls");
		String donepath = path + searchWord+"_done.json";
		FileUtil.writeFile(donepath, JSON.toJSONString(doneurls));
		
		//save related word:
		Set<String> relateWords = (Set<String>)resultItems.get("relateWords");
		String relatepath = path + searchWord+"_relate.json";
		FileUtil.writeFile(relatepath, JSON.toJSONString(relateWords));		
	}

}
