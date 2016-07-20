package box.site.getter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.htmlbot.DomPage;
import cn.edu.hfut.dmic.htmlbot.HtmlBot;
import cn.edu.hfut.dmic.htmlbot.contentextractor.ContentExtractor;
import cn.hd.util.FileUtil;
import easyshop.html.HTMLInfoSupplier;
import easyshop.html.TagDNA;
import es.util.url.URLStrHelper;

public class BasicContentGetter {
	HTMLInfoSupplier infoSupp = new HTMLInfoSupplier();
	protected Logger  log = Logger.getLogger(getClass()); 

	public static String getContent(String pageContent){
		try {
			return ContentExtractor.getContentByHtml(pageContent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//预测页面正文html内容块
	public TagDNA predictItemTagDNA(String pageContent){
        DomPage domPage = HtmlBot.getDomPageByHtml(pageContent);
        ContentExtractor contentExtractor = new ContentExtractor(domPage);
        List<String> textList = contentExtractor.getContentTextList();
		if (textList==null||textList.size()<=0){
			System.out.println("could not find context ");
			return null;
		}
		
		Document doc = domPage.getDoc();
		Elements els = doc.getElementsByTag("div");
		
		//获取这些文本节点所在的div,
		infoSupp.init(pageContent);
		List<org.jsoup.nodes.Element> eles = infoSupp.getElementsByKeyTexts(doc,"div", textList);
		//div没找到，找p:
		if (eles.size()<=0){
			eles = infoSupp.getElementsByKeyTexts(doc,"p", textList);
			//仍没找到，抓全部:
			eles = infoSupp.getElementsByKeyTexts(doc,null,textList);
		}
		if (eles.size()<=0)
			return null;
		
		
		Element e = eles.get(0);
		for (Element ee:eles){			////找最短长度的一个:
			if (ee.html().trim().length()<e.html().trim().length())
				e = ee;
		}
		TagDNA tagDNA = new TagDNA();
		tagDNA.setTag(e.nodeName());
		//class
		String classValue = e.attr("class");
		String idValue = e.attr("id");
		//两个值都有:
		if (classValue.trim().length()>0&&idValue.trim().length()>0){
			//唯一性回归确认:
			Elements tels = doc.select(e.nodeName()+"[class="+classValue+"]"+e.nodeName()+"[id="+idValue+"]");
			//唯一
			if (tels.size()==1){
				tagDNA.setType(TagDNA.TAG_TYPE_TAG_AND_PROPS);
				String props = "class:"+classValue+",id:"+idValue;
				tagDNA.setProps(props);
				return tagDNA;
			}else {
				log.error("find more than 1 tag DNA "+tels.size());
				return null;
			}
		}
		//唯一性回归确认:
		String propName = "class";
		String propValue = classValue;
		int size = 0;
		if (propValue.trim().length()>0){
			Elements tels = doc.select(e.nodeName()+"[class="+propValue+"]");
			size = tels.size();
		}
		if (propValue.trim().length()<=0||size>1){
			propName = "id";
			propValue = e.attr(propName);
			if (propValue.trim().length()>0){
				Elements tels = doc.select(e.nodeName()+"["+propName+"="+propValue+"]");
				size = tels.size();
			}
		}
		//唯一一个
		if (size==1){
			tagDNA.setType(TagDNA.TAG_TYPE_TAG_AND_PROP);
			tagDNA.setPropName(propName);
			tagDNA.setPropValue(propValue);
			return tagDNA;
		}else {
			log.error("find dna error, tag DNA size: "+size);
			return null;
		}
	}

	public static void main(String[] args){
			List<String> urls = new ArrayList<String>();
//			urls.add("http://tech.163.com/16/0626/09/BQFOIBRU00097U81.html");
//			urls.add("http://www.ifanr.com/118139");
//			urls.add("http://tech.sina.com.cn/d/2016-06-13/doc-ifxszmaa1937784.shtml");
//			urls.add("http://www.ikanchai.com/2014/1205/1959.shtml");
//			urls.add("http://www.leiphone.com/news/201406/facebook.html");

			urls.add("http://www.cyzone.cn/a/20080729/39758.html");
			urls.add("http://www.huxiu.com/article/112978/1.html?f=member_article");
			urls.add("http://www.iheima.com/analysis/2015/1227/153491.shtml");
			urls.add("http://med.iyiou.com/p/14033");
			urls.add("http://www.pintu360.com/article/10439.html");
			urls.add("http://tech.qq.com/a/20160120/009670.htm");
			urls.add("http://it.sohu.com/20160617/n454977133.shtml");
//			urls.add("http://www.sootoo.com/content/458819.shtml");
//			urls.add("http://app.techweb.com.cn/android/2015-12-28/2248034.shtml");
//			urls.add("http://www.tmtpost.com/1451151.html");
//			urls.add("http://www.geekpark.net/topics/215888");
//			urls.add("http://techcrunch.cn/2016/06/23/mrelief-is-helping-ensure-low-income-kids-get-access-to-meals-this-summer/");
			
			BasicContentGetter g = new BasicContentGetter();
			
			HTMLInfoSupplier infoSupp = new HTMLInfoSupplier();
			for (String url:urls){
				String sitekey = URLStrHelper.getHost(url).toLowerCase();
				String filepath = "data/pages/"+sitekey+"/"+url.hashCode()+".html";
				String content = FileUtil.readFile(filepath);
				System.out.println(filepath);
				infoSupp.init(content);
//				List<String> urls22 = infoSupp.getUrls2(sitekey);
//				Map<String,Vector<String>> urlsMap = infoSupp.findRegUrls(urls22);
//				for (String reg:urlsMap.keySet()){
//					System.out.println(reg+":"+urlsMap.get(reg).size());
////					System.out.println(urlsMap.get(reg));
//				}
				TagDNA dna = g.predictItemTagDNA(content);
				System.out.println(sitekey+":"+dna);
				int a = 10;
			}
		}

}
