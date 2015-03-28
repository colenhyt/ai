package box.weixin;

import java.util.ArrayList;
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

public class A5118PageDealer implements IPageDealer{
	private OriHttpPage page;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();

	public A5118PageDealer()
	{
		
	}
	
	@Override
	public List<PageRef> deal(OriHttpPage _page) {
		page = _page;
		List<PageRef> newurls = new ArrayList<PageRef>();
		if (page.getRefWord().indexOf("first")>=0){
			
			//findPublicNames();
			List<PageRef> refs = new ArrayList<PageRef>();
			//refs = buildSearchWxpublicUrls();
			newurls.addAll(refs);
			refs = buildWxpublicUrls();
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

	public List<PageRef> buildSearchWxpublicUrls()
	{
		List<PageRef> refs = new ArrayList<PageRef>();
		List<String> keys = new ArrayList<String>();
		//keys.add("%E6%B7%B1%E5%9C%B3");
		//htmlHelper.init(page.getUrlStr(), page.getContent(), "utf-8");
		
		for (int i=0; i<keys.size();i++)
		{
			String url = "http://www.5118.com/weixin/officials/search/"+keys.get(i);
			PageRef ref = new PageRef(url,"findwpname");
			refs.add(ref);
		}
		
		return refs;
	}
	
	public List<PageRef> buildWxpublicUrls()
	{
		WxpublicService  wpService = new WxpublicService();
		List<PageRef> refs = new ArrayList<PageRef>();
		List<Wxpublic> wxps = wpService.findActiveWP();
		for (int i=0;i<wxps.size();i++)
		{
			Wxpublic wp = wxps.get(i);
			String wxhao = wp.getWxhao();
			String url = "http://www.5118.com/weixin/detail?name="+wxhao;
			PageRef ref = new PageRef(url,"findwptitle");
			ref.setUrlKey(wxhao);
			ref.setRelWord(wp.getWxname());
			ref.setRefId(wp.getType());
			refs.add(ref);
		}
		return refs;
	}
	
	public void findPublicNames()
	{
		WxpublicService  wpService = new WxpublicService();
		wpService.init();
		List<Wxpublic> wxps = new ArrayList<Wxpublic>();
		htmlHelper.init(page.getContent());
		String[] namesContent = htmlHelper.getDivsByClassValue("weixin-officialslist-info clearfix");
		for (int i=0;i<namesContent.length;i++)
		{
			htmlHelper.init(namesContent[i].getBytes());
			String desc = htmlHelper.getBlock("p");
			String div = htmlHelper.getBlock("h5");
			String[] spans = htmlHelper.getBlocksByTagName("span");
			htmlHelper.init(div.getBytes());
			
			String key1 = "</span>";
			String key2 = "<small>";
			String name0 = htmlHelper.getBlock("span");
			String name = name0 + div.substring(div.indexOf(key1)+key1.length(),div.indexOf(key2));
			String haoStr = htmlHelper.getBlock("small");
			
			int[] counts = new int[3];
			if (spans.length>6)
			{
				counts[0] = Integer.valueOf(spans[2]);
				counts[1] = Integer.valueOf(spans[4]);
				counts[2] = Integer.valueOf(spans[6]);
			}
			
			Wxpublic wp = new Wxpublic();
			wp.setWxname(name);
			wp.setViewcount(counts[0]);
			wp.setZancount(counts[1]);
			wp.setTopcount(counts[2]);
			wp.setWxhao(haoStr);
			wp.setWpdesc(desc);
			
			wxps.add(wp);
		}
		wpService.addWxpublic(wxps);
	}
	
	public void dealPublicTitles()
	{
		WxtitleService  wtService = new WxtitleService();
		wtService.init();
		WxpublicService  wpService22 = new WxpublicService();
		List<Wxtitle> newtitles = new ArrayList<Wxtitle>();
		htmlHelper.init(page.getContent());
		String strYear = htmlHelper.getBlockByOneProp("li", "class", "active");
		String strList = htmlHelper.getBlockByOneProp("ul", "class", "weixin-officials-article-tmtimeline");
		if (strList==null)
		{
			System.out.println("该公众号没在5118找到:"+page.getUrlKey()+",名称:"+page.getRelWord());
			wpService22.updateStatusByHao(page.getUrlKey(), -1);
			return;
		}
		
		htmlHelper.init(strList.getBytes());
		String[] lis = htmlHelper.getBlocksByTagName("li");
		String strDay=null;
		String strMonth=null;
		for (int i=0;i<lis.length;i++)
		{
			htmlHelper.init(lis[i].getBytes());
			String timeDiv = htmlHelper.getDivByClassValue("weixin-officials-article-tmicon");
			if (timeDiv!=null)
			{
				strDay = timeDiv.substring(0,timeDiv.lastIndexOf("<p>"));
				strMonth = timeDiv.substring(timeDiv.lastIndexOf("<p>")+3,timeDiv.lastIndexOf("</p>")-1);
			}
			String content = htmlHelper.getDivByClassValue("item");
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
			wt.setPubdate(new Date());

			int maxcount = 100000;
			
			String[] spans = htmlHelper.getBlocksByTagName("span");
			if (spans.length==4)
			{
				String strTime = StringHelper.textReplace("发布","",spans[0]);
				String strZancount = spans[1].substring(spans[1].lastIndexOf("</span>")+7);
				String strViewcount = spans[3].substring(3,spans[3].indexOf("）"));
				
				if (strYear!=null&&strMonth!=null&&strDay!=null&&strTime!=null)
				wt.setPubdate(DateHelper.formatDate(strYear, strMonth, strDay, strTime));
				
				
				if (Pattern.matches("[\\d]*", strZancount))
				 wt.setZancount(Integer.valueOf(strZancount));
				else if (strZancount.indexOf("100000")>=0){
					wt.setZancount(maxcount);
				}else
				{
					System.out.println("错误点赞数:"+strZancount);
				}
				if (Pattern.matches("[\\d]*", strViewcount))
					 wt.setViewcount(Integer.valueOf(strViewcount));
				else if (strViewcount.indexOf("100000")>=0){
					wt.setViewcount(maxcount);
				}else {
					System.out.println("错误阅读数:"+strViewcount);
				}
			}
			
			wtService.addTitlekey(key);
			newtitles.add(wt);
		}

		wpService22.updateStatusByHao(page.getUrlKey(), 1);
		wtService.addNewwxtitle(newtitles);
		System.out.println("增加推文:"+newtitles.size());
	}
	
	@Override
	public String getSiteId() {
		// TODO Auto-generated method stub
		return "5118";
	}

	@Override
	public String getFirstUrl() {
		// TODO Auto-generated method stub
		return "http://www.5118.com/weixin/officials/search/%E6%B7%B1%E5%9C%B3";
	}

}
