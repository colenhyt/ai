package box.weixin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import box.db.Wxpublic;
import box.db.WxpublicService;
import box.db.Wxtitle;
import box.db.WxtitleService;
import box.util.DateHelper;
import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import easyshop.html.jericho.Element;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

public class SougouPageDealer implements IPageDealer{
	WxtitleService  wtService = new WxtitleService();
	private OriHttpPage page;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();

	public SougouPageDealer()
	{
		
	}
	
	@Override
	public List<PageRef> deal(OriHttpPage _page) {
		page = _page;
		List<PageRef> newurls = new ArrayList<PageRef>();
		if (page.getRefWord().indexOf("first")>=0){
			List<PageRef> refs = new ArrayList<PageRef>();
//			htmlHelper.init(page.getContent());
//			String[] mm = htmlHelper.getBlocksByOneProp("resnum", "id", "scd_num");
//			findPublicNames();
//			if (mm.length>0&&StringHelper.isNumber(mm[0]))
//			{
//				int paging = Integer.valueOf(mm[0]);
//				if (paging%10>0)
//				 paging = paging/10+1;
//				else
//				 paging = paging/10;
//				refs.addAll(buildPagingWxpublicUrls(page.getUrlStr(),paging,page.getRefId()));
//			}
			
			refs.addAll(buildWxpublicUrls());
			
			newurls.addAll(refs);
		}else if (page.getRefWord().indexOf("findwpname")>=0)		//find wp name
		{
			findPublicNames();
		}else if (page.getRefWord().indexOf("findwptitle")>=0)		//find wp title
		{
			dealPublicTitles();
		}else
		{
			List<PageRef> refs = buildWxpublicUrls();
			newurls.addAll(refs);
		}
		return newurls;
	}

	private List<PageRef> buildPagingWxpublicUrls(String strUrl,int page,int type)
	{
		List<PageRef> refs = new ArrayList<PageRef>();
		for (int j=2;j<=page;j++)
		{
			String url = strUrl+"&page="+j;
			PageRef ref = new PageRef(url,"findwpname");
			ref.setRefId(type);				
			refs.add(ref);
		}
		
		return refs;
	}
	
	private List<PageRef> buildWxpublicUrls()
	{
		List<PageRef> refs = new ArrayList<PageRef>();
		WxpublicService  wpService = new WxpublicService();
		List<Wxpublic> wxps = wpService.findNotSearchWp();
		for (int i=0;i<wxps.size();i++)
		{
			String url = "http://weixin.sogou.com/gzh?openid="+wxps.get(i).getOpenid();
			PageRef ref = new PageRef(url,"findwptitle");
			refs.add(ref);
		}
		return refs;
	}
	
	private void findPublicNames()
	{
		List<Wxpublic> wxps = new ArrayList<Wxpublic>();
		htmlHelper.init(page.getContent());
		String openUrlKey = "gzh?openid=";
		List<Element> list = htmlHelper.getDivElementsByClassValue("wx-rb bg-blue wx-rb_v1 _item");
		for (int i=0;i<list.size();i++)
		{
			Element e = list.get(i);
			String strOpenid = e.getAttributes().getValue("href");
			if (strOpenid.length()>0)
				strOpenid = strOpenid.substring(strOpenid.indexOf(openUrlKey)+openUrlKey.length());
			String content = e.getContentText();
			///gzh?openid=
			htmlHelper.init(content.getBytes());
			String div = htmlHelper.getBlockByOneProp("div", "class", "txt-box");
			if (div==null) continue;
			String imgSrc = "";
			String divImg = htmlHelper.getBlockByOneProp("div", "class", "img-box");
			if (divImg!=null)
			{
				htmlHelper.init(divImg.getBytes());
				Element imge = htmlHelper.getElementByKey("img", null);
				imgSrc = imge.getAttributes().getValue("src");
			}
			
			htmlHelper.init(div.getBytes());
			Element nameE = htmlHelper.getElementByKey("h3", null);
			
			String nameStr = nameE.getContentText();
			nameStr = nameStr.replace("<!--red_beg-->", "");
			nameStr = nameStr.replace("<!--red_end-->", "");
			nameStr = nameStr.replace("<em>", "");
			nameStr = nameStr.replace("</em>", "");
			String haoStr = htmlHelper.getBlock("h4");
			htmlHelper.init(haoStr.getBytes());
			haoStr = htmlHelper.getBlock("span");
			haoStr = haoStr.replaceAll("微信号：", "");
			
			Wxpublic wp = new Wxpublic();
			wp.setWxhao(haoStr);
			wp.setOpenid(strOpenid);
			wp.setWxname(nameStr);
			wp.setStatus(0);
			wp.setImgurl(imgSrc);
			wp.setCrdate(new Date());
			wp.setType(page.getRefId());
			wxps.add(wp);
		}
		if (wxps.size()>0)
		{
			System.out.println("类型"+page.getRefId()+" 找到公众号: "+wxps.size());
			WxpublicService  wpService = new WxpublicService();
			wpService.init();
			wpService.addWxpublic(wxps);
		}
	}
	
	private void dealPublicTitles()
	{
		WxpublicService  wpService22 = new WxpublicService();
		String str = new String(page.getContent());
		int ill = str.length();
		htmlHelper.init(page.getContent());
		String[] lis = htmlHelper.getDivsByClassValue("wx-rb wx-rb3");
		if (lis.length<=0)
		{
			System.out.println("该公众号没没找到:"+page.getUrlKey()+",名称:"+page.getRelWord());
			wpService22.updateStatusByHao(page.getUrlKey(), -1);
			return;
		}
		WxtitleService  wtService = new WxtitleService();
		wtService.init();
		List<Wxtitle> newtitles = new ArrayList<Wxtitle>();
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		String strYear = Integer.toString(c.get(Calendar.YEAR));
		String strTime = "00:00:00";
		for (int i=0;i<lis.length;i++)
		{
			htmlHelper.init(lis[i].getBytes());
			String content = htmlHelper.getDivByClassValue("txt-box");
			if (content==null)continue;
			
			htmlHelper.init(content.getBytes());
			Element e = htmlHelper.getElementByKey("a", null);
			String titleUrl = e.getAttributes().getValue("href");
			
			String titleKey1 = URLStrHelper.getParamValue(titleUrl, "__biz");
			String titleKey2 = URLStrHelper.getParamValue(titleUrl, "mid");
			String titleKey3 = URLStrHelper.getParamValue(titleUrl, "sn");
			String key = titleKey1+"_"+titleKey2+"_"+titleKey3;
			
			if (wtService.existTitle(key)) continue;
			
			Wxtitle wt = new Wxtitle();
			wt.setTitlekey(key);
			wt.setTitleurl(titleUrl);
			wt.setWxhao(page.getUrlKey());
			wt.setWxname(page.getRelWord());
			wt.setType(page.getRefId());
			wt.setSrcflag(0);
			wt.setUseflag(0);
			wt.setStatus(0);
			wt.setTitle(e.getContentText());

			int maxcount = 100000;
			
			String strD = htmlHelper.getBlockByOneProp("p", "class", "s-p");
			if (strD.length()>0)
			{
				int ss1 = strD.indexOf("月");
				String strMonth = strD.substring(0,ss1);
				String strDay = strD.substring(ss1,strD.indexOf("日"));
				if (strYear!=null&&strMonth!=null&&strDay!=null&&strTime!=null)
				wt.setPubdate(DateHelper.formatDate(strYear, strMonth, strDay, strTime));
			}
			
			wtService.addTitlekey(key);
			newtitles.add(wt);
		}

		wpService22.updateStatusByHao(page.getUrlKey(), 1);
		wtService.addNewwxtitle(newtitles);
		System.out.println("类型"+page.getRefId()+",公众号"+page.getRelWord()+" 增加推文:"+newtitles.size());

	}
	
	@Override
	public String getSiteId() {
		// TODO Auto-generated method stub
		return "sougou";
	}

	@Override
	public String getFirstUrl() {
		// TODO Auto-generated method stub
		String strKey = URLStrHelper.toUtf8String("深圳");
		return "http://weixin.sogou.com/weixin?type=1&ie=utf8&query="+strKey;
	}

	@Override
	public List<PageRef> getFirstRefs() {
		List<PageRef> refs = new ArrayList<PageRef>();
		String searchUrl = "http://weixin.sogou.com/weixin?type=1&ie=utf8&query=";
			String strUrl = null;
			PageRef ref = null;
			
//			strUrl = searchUrl + URLStrHelper.toUtf8String("深圳");
//			ref = new PageRef(strUrl,"first");
//			ref.setRefId(20);
//			refs.add(ref);
			
			strUrl = searchUrl + URLStrHelper.toUtf8String("购物");
			ref = new PageRef(strUrl,"first");
			ref.setRefId(21);
			refs.add(ref);
		// TODO Auto-generated method stub
		return refs;
	}
	
}
