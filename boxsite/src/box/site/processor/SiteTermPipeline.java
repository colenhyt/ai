package box.site.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import com.alibaba.fastjson.JSON;

import es.util.FileUtil;

public class SiteTermPipeline extends FilePersistentBase  implements Pipeline {
    private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void process(ResultItems resultItems, Task task) {
		String site = (String)resultItems.get("DomainName");
		int maxCount = resultItems.get("MaxPageCount");
        String path = "data/terms" + PATH_SEPERATOR;
        List<File> files = FileUtil.getFiles(path+site + PATH_SEPERATOR);
		int pageCount = files.size();
        try {
        	Map<String,Integer> termsMap = null;
        	String fileName = null;
    		if (pageCount>maxCount){
    			//合并terms:
    			fileName = path+ site;
    			termsMap = new HashMap<String,Integer>();
    			for (int i=0;i<files.size();i++){
    				String content = FileUtil.readFile(files.get(i));
    				Map<String,Integer> json = (Map<String,Integer>)JSON.parse(content);
    				log.warn("aa :"+content);
    				for (String key:json.keySet()){
    					if (termsMap.containsKey(key))
    						termsMap.put(key, termsMap.get(key)+json.get(key));
    					else
    						termsMap.put(key, json.get(key));
    				}
    			}
    			
    		}else {
        		Object obj = resultItems.get("PageTerm");
        		if (obj!=null){
        			termsMap = (Map<String,Integer>)obj;
        			path += site + PATH_SEPERATOR;
        			fileName = path + DigestUtils.md5Hex(resultItems.getRequest().getUrl());
        		}   			
    		}
    		
            PrintWriter printWriter = new PrintWriter(new FileWriter(getFile(fileName + ".json")));
            printWriter.write(JSON.toJSONString(termsMap));
            printWriter.close();
        } catch (IOException e) {
            log.warn("write file error", e);
        }		
	}

}
