package box.site.processor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;
import box.site.model.WebUrl;

import com.alibaba.fastjson.JSON;

import easyshop.html.HTMLInfoSupplier;
import es.util.FileUtil;
import es.util.string.StringHelper;

public class SiteURLsPipeline extends FilePersistentBase  implements Pipeline {
    private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void process(ResultItems resultItems, Task task) {
		String site = (String)resultItems.get("DomainName");
        String fileName = "data/pages" + PATH_SEPERATOR+site+"_urls.json";
        try {
        	//urls 处理:
        	Object obj = resultItems.get("PageUrls");
    		Set<WebUrl> allitems = new HashSet<WebUrl>();
        	if (obj!=null){
        		Set<WebUrl> urls = (Set<WebUrl>)obj;
        		String content = FileUtil.readFile(fileName);
        		if (content!=null&&content.trim().length()>0){
        			StringHelper.json2Set(content, allitems);
        		}
        		allitems.addAll(urls);        		
        	}
            PrintWriter printWriter = new PrintWriter(new FileWriter(getFile(fileName)));
            printWriter.write(JSON.toJSONString(allitems));
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
