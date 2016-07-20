package box.site.parser.sites;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import box.site.classify.NewsClassifier;
import box.site.getter.ISiteContentGetter;
import box.site.getter.SiteContentGetterFactory;
import box.site.model.TopItem;
import box.site.parser.ITopItemParser;
import cn.hd.util.FileUtil;

import com.alibaba.fastjson.JSON;

import easyshop.html.HTMLInfoSupplier;
import es.util.url.URLStrHelper;

public class BaseTopItemParser implements ITopItemParser {
	protected Logger  log = Logger.getLogger(getClass());
	protected HTMLInfoSupplier infoSupp = new HTMLInfoSupplier();
	protected NewsClassifier newsClassifier = new NewsClassifier();
	private Map<String,String> siteTitleEndWord = new HashMap<String,String>();
	private Map<String,ISiteContentGetter> siteDNAMap = Collections.synchronizedMap(new HashMap<String,ISiteContentGetter>());
    Random random = new Random();
	
	public BaseTopItemParser(String dnaPath){
		//"_网易科技"
		//cyzone: - 
		//geekpark:| 极客公园
		//ikancai: 企业和个人应如何搭上全民直播这趟顺风车？_观点_砍柴网
		//huxiu:-虎嗅网
		//ifanr:| 爱范儿
		//广东发布分级诊疗实施方案，2016年全面开展分级诊疗_亿欧网_驱动创业创新
		//Wifi 万能钥匙李磊：你的流量被我包了！| 硬创公开课演讲实录 | 雷锋网
		//苹果严重失信的后果有多严重？ | 雷锋网
		//［宛约］40岁创业新人王胜江：胜败由心力而生|品途商业评论
		//图说虚拟现实发展简史 始于20世纪60年代_科技_腾讯网
		//高德推出“双生态”战略 欲盘活交通体系|俞永福|高德|百度_新浪科技_新浪网
		//法国丽人的寂静革命:?从独行学霸到CRISPR女皇-搜狐科技
		//民企抢食移动通信和宽带市场会怎样-移动通信 宽带 苏宁云商 移动互联网-速途网
		//Uber首次涉足杠杆贷款市场 募集至多20亿美元资金-uber 互联网资讯-速途网
		//Facebook 发动消息应用“核战争”：推出个人助手 M | TechCrunch 中国
		//Mac QQ 5.0版试用体验 到底有哪些新变化呢？_Techweb
		//【钛晨报】iOS 10终于揭开真面目：Siri支持第三方应用，对开发者开放-钛媒体官方网站				
		siteTitleEndWord.put("163.com", "_网易科技");
		siteTitleEndWord.put("huxiu.com", "-虎嗅网");
		siteTitleEndWord.put("ifanr.com", "| 爱范儿");
		siteTitleEndWord.put("cyzone.cn", " - ");
		siteTitleEndWord.put("geekpark.net", "| 极客公园");
		siteTitleEndWord.put("iheima.com", "_i黑马");
		siteTitleEndWord.put("ikanchai.com", "_");
		siteTitleEndWord.put("iyiou.com", "_");
		siteTitleEndWord.put("leiphone.com", "|");
		siteTitleEndWord.put("pintu360.com", "|");
		siteTitleEndWord.put("qq.com", "_科技_腾讯网");
		siteTitleEndWord.put("sina.com.cn", "_新浪科技_新浪网");
		siteTitleEndWord.put("sohu.com", "-搜狐科技");
		siteTitleEndWord.put("sootoo.com", "-速途网");
		siteTitleEndWord.put("techcrunch.cn", "|");
		siteTitleEndWord.put("techweb.com.cn", "_Techweb");
		siteTitleEndWord.put("tmtpost.com", "-钛媒体官方网站");
		siteTitleEndWord.put("ifeng.com", "_凤凰科技");
		siteTitleEndWord.put("36kr.com", "_36氪");
		
		List<File> files = FileUtil.getFiles(dnaPath);
		try {		
		for (File file:files){
			String sitekey = file.getName().substring(0,file.getName().lastIndexOf(".dna"));
			ISiteContentGetter getter = SiteContentGetterFactory.createGetter(sitekey,dnaPath);
			siteDNAMap.put(sitekey, getter);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
		
	}
	public long _formateTime(String yyyyMMddStr){
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		try {
			if (yyyyMMddStr!=null){
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
				Date d = simpleDateFormat.parse(yyyyMMddStr);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(d);
				c.set(Calendar.YEAR, c2.get(Calendar.YEAR));
				c.set(Calendar.MONTH, c2.get(Calendar.MONTH));
				c.set(Calendar.DAY_OF_MONTH, c2.get(Calendar.DAY_OF_MONTH));				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return c.getTimeInMillis();
	}
	
	//fix create time:http://tech.163.com/16/0621/07/
	//time:http://www.cyzone.cn/r/20150704/17210.html
	//http://www.iheima.com/top/2016/0420/155310.shtml"
	//http://www.ikanchai.com/2015/1217/41285.shtml
	//http://www.leiphone.com/news/201512/DbeuHjTfhYXISMu0.html
//	/http://tech.qq.com/a/20160622/009067.htm
	//http://tech.sina.com.cn/i/2016-06-21/doc-ifxtfrrc3991474.shtml
	//http://it.sohu.com/20160621/n455545381.shtml
	//http://techcrunch.cn/2016/06/01/recently-confirmed-myspace-hack-could-be-the-largest-yet/#disqus_thread
	//http://www.techweb.com.cn/news/2015-11-26/2232572.shtml
	public String getSiteUrlDate(String url){
		String[] parts = url.replace("//", "").split("/");
		try {
		if (url.indexOf("163.com")>=0)
			return "20"+parts[1]+parts[2];
		else if (url.indexOf("qq.com")>=0)
			return parts[2];
		else if (url.indexOf("sina.com")>=0)
			return parts[2].replace("-", "");
		else if (url.indexOf("ikanchai.com")>=0)
			return parts[1]+parts[2];
		else if (url.indexOf("sohu.com")>=0)
			return parts[1];
		else if (url.indexOf("ifeng.com")>=0)
			return parts[2];
		else if (url.indexOf("techweb.com.cn")>=0)
			return parts[2].replace("-", "");
		else if (url.indexOf("techcrunch.cn")>=0)
			return parts[1]+parts[2]+parts[3];
		else if (url.indexOf("cyzone.cn")>=0)
			return parts[2];
		else if (url.indexOf("leiphone.com")>=0){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			int monthday = c.get(Calendar.DAY_OF_MONTH);
			String dd = String.valueOf(monthday);
			if (monthday<10)
				dd = "0"+dd;
			return parts[2]+dd;
		}else if (url.indexOf("iheima.com")>=0){
			return parts[2]+parts[3];
		}
		}catch (Exception e){
			log.warn("get url date wrong "+e.getMessage());
		}
		return null;
						
	}
	
	@Override
	public TopItem parse(String url,String pageContent) {
		String sitekey = URLStrHelper.getHost(url).toLowerCase();
		
		ISiteContentGetter getter = siteDNAMap.get(sitekey);
		boolean parsed = getter.parseItem(url,pageContent);
		if (!parsed){
			log.warn("could not get html content "+url.hashCode());
			return null;
		}
		
		infoSupp.init(pageContent);
		String title = infoSupp.getTitleContent();
		if (title==null){
			log.debug("could not get page title:"+url.hashCode());
			return null;
		}
		if (siteTitleEndWord.containsKey(sitekey)){
			String endKey = siteTitleEndWord.get(sitekey);
			if (title.indexOf(endKey)>0)
				title = title.substring(0,title.indexOf(endKey));
		}
		
		TopItem titem = new TopItem();
		titem.setCtitle(title.trim());
		titem.setSitekey(sitekey);
		titem.setUrl(url);
		titem.setId(url.hashCode());
		titem.setContent(getter.getItemPureContent());
		titem.setHtmlContent(filterContext(sitekey,getter.getItemHtmlContent()));
		List<Integer> catids = new ArrayList<Integer>();
//		catids.add(1);
//		catids.add(11);
//		catids.add(21);
//		catids.add(31);
//		catids.add(41);
//		catids.add(51);
//		int r1 = random.nextInt(6);
		int catid = 0;
//		catid = newsClassifier.testClassify(titem);
//		if (catid<=0){
//		}
//		log.warn("classify catid: "+catid+":"+url);
		
		titem.setCat(catid);
		titem.setCrDate(new Date());
		String urlDate = getSiteUrlDate(url);
		long  ctime = _formateTime(urlDate);
		titem.setContentTime(ctime);
		
		return titem;
	}

	public static void main(String[] args){
		BaseTopItemParser parser = new BaseTopItemParser("data/");
		List<String> urls = new ArrayList<String>();
		urls.add("http://tech.163.com/16/0626/09/BQFOIBRU00097U81.html");
//		urls.add("http://www.cyzone.cn/a/20080729/39758.html");
//		urls.add("http://www.huxiu.com/article/112978/1.html?f=member_article");
//		urls.add("http://www.ifanr.com/118139");
//		urls.add("http://www.iheima.com/analysis/2015/1227/153491.shtml");
//		urls.add("http://www.ikanchai.com/2014/1205/1959.shtml");
//		urls.add("http://med.iyiou.com/p/14033");
//		urls.add("http://www.leiphone.com/news/201406/facebook.html");
//		urls.add("http://www.pintu360.com/article/10439.html");
//		urls.add("http://tech.qq.com/a/20160120/009670.htm");
//		urls.add("http://tech.sina.com.cn/d/2016-06-13/doc-ifxszmaa1937784.shtml");
//		urls.add("http://it.sohu.com/20160617/n454977133.shtml");
//		urls.add("http://www.sootoo.com/content/458819.shtml");
//		urls.add("http://app.techweb.com.cn/android/2015-12-28/2248034.shtml");
//		urls.add("http://www.tmtpost.com/1451151.html");
//		urls.add("http://www.geekpark.net/topics/215888");
//		urls.add("http://techcrunch.cn/2016/06/23/mrelief-is-helping-ensure-low-income-kids-get-access-to-meals-this-summer/");
		
		Calendar c = Calendar.getInstance();
		for (String url:urls){
			String sitekey = URLStrHelper.getHost(url).toLowerCase();
			String filepath = "data/pages/"+sitekey+"/"+url.hashCode()+".html";
			String content = FileUtil.readFile(filepath);
			TopItem item = parser.parse(url, content);
			System.out.println(item.getHtmlContent());
			System.out.println(url);
			int a = 10;
//			System.out.println(item.getCtitle());
//			c.setTimeInMillis(item.getContentTime());
//			System.out.println(c.getTime());
		}
	}
	
	private String filterContext(String sitekey,String htmlContext){
		String realContext = htmlContext;
		int index = -1;
		if (sitekey.indexOf("iyiou.com")>=0){
			index = realContext.indexOf("</span></p>");
		}else if (sitekey.indexOf("ikanchai.com")>=0){
			index = realContext.indexOf("<script");
		}else if (sitekey.indexOf("geekpark.net")>=0){
			index = realContext.indexOf("<script");
		}else if (sitekey.indexOf("techweb.com")>=0){
			index = realContext.indexOf("<b>您可能也感兴趣");
		}else if (sitekey.indexOf("tmtpost.com")>=0){
			int start = realContext.indexOf("<p class=\"post-abstract\">");
		} 
		
		if (index>0)
			realContext = realContext.substring(0,index);
		return realContext;
	}
	
	public void save(String rootPath,TopItem item){
		String fileName = item.getUrl().hashCode()+".item";
		
		if (item.getCat()<=0){
			String path = rootPath+"wrongitems/"+item.getCat()+"/"+item.getSitekey()+"/"+fileName;
			FileUtil.writeFile(path, JSON.toJSONString(item));
			return;
		}
		
		//根据时间item落地:
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(item.getContentTime());
//		String dateStr = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH);
		String path = rootPath+"items/"+item.getCat()+"/"+fileName;
		FileUtil.writeFile(path, JSON.toJSONString(item));		
	}
}
