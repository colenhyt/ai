package box.site.getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import us.codecraft.webmagic.Page;
import box.site.model.ContentDNA;
import cn.edu.hfut.dmic.htmlbot.DomPage;
import cn.edu.hfut.dmic.htmlbot.HtmlBot;
import cn.edu.hfut.dmic.htmlbot.contentextractor.ContentExtractor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import es.util.url.URLStrHelper;

public class cfkr_comGetter extends BasicSiteContentGetter {

	@Override
	public List<String> findItemUrls(Page page) {
		String url = page.getRequest().getUrl();
		String pageContent = page.getRawText();
		sitekey = URLStrHelper.getHost(url).toLowerCase();
		List<String> links = new ArrayList<String>();
		
		//feedPostsLatest|post
		int start = pageContent.indexOf("props=");
		int end = pageContent.indexOf(",locationnal=",start);
		if (end<0)
			end = pageContent.indexOf("</script>", start);
		String listContent = pageContent.substring(start+"props=".length(),end);
		JSONObject json = JSON.parseObject(listContent);
		
		Object obj = json.get("feedPostsLatest|post");
		if (obj==null)
			return links;
		
		List<KrItem> items = JSON.parseArray(obj.toString(),KrItem.class);
		if (items.size()>0){
			for (KrItem item:items){
				String itemUrl = "http://36kr.com/p/"+item.getId()+".html";
				links.add(itemUrl);
			}
		}
		log.warn(items.size());
	
		return links;
	}

	@Override
	public boolean parseItem(Page page) {
		String url = page.getRequest().getUrl();
		String pageContent = page.getRawText();
		sitekey = URLStrHelper.getHost(url).toLowerCase();
		
		String startKey = "props=";
		int start = pageContent.indexOf(startKey);
		int end = pageContent.indexOf(",locationnal=",start);
		if (end<0)
			end = pageContent.indexOf("</script>", start);
		String content = pageContent.substring(start+startKey.length(),end);
		JSONObject json = JSON.parseObject(content);
		Object obj = json.get("detailArticle|post");
		if (obj==null)
			return false;
		
		KrItem item = JSON.parseObject(obj.toString(),KrItem.class);
		if (item==null||item.content.length()<=0)
			return false;
		
		itemHtmlContent = item.getContent();
		DomPage domPage2 = HtmlBot.getDomPageByHtml(itemHtmlContent);
	    ContentExtractor contentExtractor = new ContentExtractor(domPage2);
	    itemPureContent = contentExtractor.getText();		
	    return true;
	    
	}
}
