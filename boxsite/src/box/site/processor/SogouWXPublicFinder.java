package box.site.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.utils.FilePersistentBase;
import box.db.Wxpublic;
import box.db.WxpublicService;
import box.weixin.SougouPageDealer;

import com.alibaba.fastjson.JSON;

import es.util.FileUtil;
import es.util.url.URLStrHelper;


public class SogouWXPublicFinder implements IItemFinder {
	protected Logger  log = Logger.getLogger(getClass());
	private SougouPageDealer sogouDealer = new SougouPageDealer();
	private WxpublicService service = new WxpublicService();
	private FilePersistentBase filePer = new FilePersistentBase();
	
	@Override
	public void process(Page page) {
		Set<String>	items = new HashSet<String>();
		//1. find wx public:
		List<Wxpublic> wps = sogouDealer.findWxPublics(page.getRawText().getBytes());
		if (wps.size()<=0){
			String str = new String(page.getRawText().getBytes());
			if (str.indexOf("您的访问过于频繁")>0){
				String urlkey = DigestUtils.md5Hex(page.getRequest().getUrl());
				String path="data/error/sogou/"+urlkey+".html";
				File f = filePer.getFile(path);
				FileUtil.writeFile(f, str);
		        try {
		        	log.warn("频繁访问，暂时等待...");
		            Thread.sleep(1000*60*10);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
			}
		}
		service.addWxpublic(wps);
		for (Wxpublic wp:wps){
			items.add(JSON.toJSONString(wp));
		}
		page.putField("items", items);
		
		String param = URLStrHelper.getParamValue(page.getRequest().getUrl(), "query");
		//2. find paging urls:
		List<String> pagingurls = new ArrayList<String>();
		List<String> urls = page.getHtml().links().all();
		for (String url:urls){
			if (url.indexOf("query="+param)>0&&url.indexOf("page=")>0){
				url = url.replace("utf8#", "utf8");
				pagingurls.add(url);
			}
		}
		page.putField("pagingurls", pagingurls);
	}

	@Override
	public String getStartUrl(String word) {
		String strKey = URLStrHelper.toUtf8String(word);
		return "http://weixin.sogou.com/weixin?type=1&ie=utf8&query="+strKey;
	}


}
