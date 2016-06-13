package box.site.processor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;
import box.site.model.WebUrl;
import cn.hd.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.FileUtil;

public class SiteURLsPipeline extends FilePersistentBase  implements Pipeline {
    private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void process(ResultItems resultItems, Task task) {
		String site = (String)resultItems.get("DomainName");
		String charset = (String)resultItems.get("Charset");
        String fileName = "data/pages" + PATH_SEPERATOR+site+"_urls.json";
        try {
        	//urls 处理:
        	Object obj = resultItems.get("PageUrls");
    		Map<String,WebUrl> allitems = new HashMap<String,WebUrl>();
    		Map<String,WebUrl> urls = (Map<String,WebUrl>)obj;
    		String content = FileUtil.readFile(fileName,charset);
    		
    		if (content!=null&&content.trim().length()>0){
    			Map<String,JSONObject> jsonstr = (Map<String,JSONObject>)JSON.parseObject(content, HashMap.class);
    			for (String url:jsonstr.keySet()){
    				JSONObject jsonobj = jsonstr.get(url);
    				WebUrl item = (WebUrl)JSON.parseObject(jsonobj.toJSONString(),WebUrl.class);
    				allitems.put(url, item);
    			}
    		}
    		allitems.putAll(urls);    		
            PrintWriter printWriter = new PrintWriter(new FileWriter(getFile(fileName)));
            String str = new String(JSON.toJSONString(allitems).getBytes(),charset);
            printWriter.write(str);
            printWriter.close();
        } catch (IOException e) {
            log.warn("write file error", e);
        }		
	}

	public Set<WebUrl> getUrls(String pageContent){
		Set<WebUrl> urls = new HashSet<WebUrl>();
		
		return urls;
	}

}
