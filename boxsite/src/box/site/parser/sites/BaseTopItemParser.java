package box.site.parser.sites;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import box.site.PageContentGetter;
import box.site.classify.NewsClassifier;
import box.site.model.TopItem;
import box.site.parser.ITopItemParser;
import easyshop.html.HTMLInfoSupplier;
import es.util.url.URLStrHelper;

public abstract class BaseTopItemParser implements ITopItemParser {
	protected Logger  log = Logger.getLogger(getClass());
	protected PageContentGetter contentGetter = new PageContentGetter();
	protected HTMLInfoSupplier infoSupp = new HTMLInfoSupplier();
	protected NewsClassifier newsClassifier = new NewsClassifier();

	public long getSpecTime(String yyyyMMddStr){
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date d = simpleDateFormat.parse(yyyyMMddStr);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(d);
			c.set(Calendar.YEAR, c2.get(Calendar.YEAR));
			c.set(Calendar.MONTH, c2.get(Calendar.MONTH));
			c.set(Calendar.DAY_OF_MONTH, c2.get(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return c.getTimeInMillis();
	}
	
	@Override
	public TopItem parse(String url,String pageContent) {
		infoSupp.init(pageContent.getBytes());
		List<String> contents = contentGetter.getHtmlContent(url,pageContent);
		if (contents==null||contents.size()<=0){
			log.warn("could not get html content "+url.hashCode());
			return null;
		}
		
		String title = infoSupp.getTitleContent();
		if (title==null){
			log.debug("could not get page title:"+url.hashCode());
			return null;
		}
		TopItem titem = new TopItem();
		titem.setCtitle(title.trim());
		titem.setUrl(url);
		titem.setId(url.hashCode());
		titem.setContent(contents.get(0));
		titem.setHtmlContent(contents.get(1));
		int catid = newsClassifier.testClassify(titem);
		if (catid<=0)
			return null;
		
		
		titem.setCrDate(new Date());
		titem.setContentTime(System.currentTimeMillis());
		String sitekey = URLStrHelper.getHost(url).toLowerCase();
		titem.setSitekey(sitekey);
		
		this.fixItemContext(titem);
		return titem;
	}

}
