package box.site.parser.sites;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import box.site.model.TopItem;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;

public class TopItemParser_163 extends BaseTopItemParser {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fixItemContext(TopItem item) {
		//fix title:
		item.setCtitle(StringHelper.subString(item.getCtitle(),"_网易科技"));
		
		//fix create time:http://tech.163.com/16/0621/07/
		String url = item.getUrl();
		String[] parts = URLStrHelper.getParts(url);
		String timestr = "20"+parts[1]+parts[2];
		long contentTime = super.getSpecTime(timestr);
		item.setContentTime(contentTime);
		 
		
		//cyzone:
		item.setCtitle(item.getCtitle().substring(0,item.getCtitle().indexOf(" - ")));
		//time:http://www.cyzone.cn/r/20150704/17210.html
		parts = URLStrHelper.getParts(url);
		timestr = parts[2];
		contentTime = super.getSpecTime(timestr);
		item.setContentTime(contentTime);
		
		//geekpark:
		item.setCtitle(item.getCtitle().substring(0,item.getCtitle().indexOf("| 极客公园")));
		
		//huxiu:-虎嗅网
		
		//ifanr:| 爱范儿
		
		//iheima:
		item.setCtitle(item.getCtitle().replace("_i黑马",""));
		item.setCtitle(item.getCtitle().substring(0,item.getCtitle().indexOf("_")));
		//http://www.iheima.com/top/2016/0420/155310.shtml"
		parts = URLStrHelper.getParts(url);
		timestr = parts[2]+parts[3];
		contentTime = super.getSpecTime(timestr);
		item.setContentTime(contentTime);
		
		//ikancai: 企业和个人应如何搭上全民直播这趟顺风车？_观点_砍柴网
		item.setCtitle(item.getCtitle().replace("_砍柴网",""));
		item.setCtitle(item.getCtitle().substring(0,item.getCtitle().indexOf("_")));
		//http://www.ikanchai.com/2015/1217/41285.shtml
		parts = URLStrHelper.getParts(url);
		timestr = parts[1]+parts[2];
		contentTime = super.getSpecTime(timestr);
		item.setContentTime(contentTime);
		
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
		
		//http://www.leiphone.com/news/201512/DbeuHjTfhYXISMu0.html
//		/http://tech.qq.com/a/20160622/009067.htm
		//http://tech.sina.com.cn/i/2016-06-21/doc-ifxtfrrc3991474.shtml
		//http://it.sohu.com/20160621/n455545381.shtml
		//http://techcrunch.cn/2016/06/01/recently-confirmed-myspace-hack-could-be-the-largest-yet/#disqus_thread
		//http://www.techweb.com.cn/news/2015-11-26/2232572.shtml
	}

}
