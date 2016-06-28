package box.site.parser.sites;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;
import box.mgr.PageManager;
import box.mgr.ProcessManager;
import box.site.model.TopItem;
import cl.util.FileUtil;

import com.alibaba.fastjson.JSON;

public class SiteTopItemsPipeline extends FilePersistentBase  implements Pipeline {
    private Logger log = LoggerFactory.getLogger(getClass());
    private BaseTopItemParser parser = new BaseTopItemParser();
	ImgGetterThread imgGetter = new ImgGetterThread();
    private String rootPath = null;

	public SiteTopItemsPipeline(){
		rootPath = PageManager.getInstance().getRootPath();
		Thread thread = new Thread(imgGetter);
		thread.start();
	}
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		String sitekey = (String)resultItems.get("DomainName");
		String pageContent = (String)resultItems.get("PageContent");
		String url = (String)resultItems.get("Url");
		String charset = (String)resultItems.get("Charset");
		TopItem item = parser.parse(url, pageContent);
		if (item==null){
			log.warn("page parse failed:"+url);
			return;
		}
	
		parser.save(rootPath, item);
		
		if (item.getCat()>0){
			
			//异步下载正文中的img资源
			if (item.getHtmlContent()!=null){
				imgGetter.push(url, item.getHtmlContent());
			}
			
			ProcessManager.getInstance().pushNewItem(item);
			//PageManager.getInstance().pushNewItem(item);
		}
	}

}
