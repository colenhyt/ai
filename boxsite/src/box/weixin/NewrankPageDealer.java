package box.weixin;

import java.util.ArrayList;
import java.util.List;

import box.db.Wxpublic;
import box.db.WxpublicService;
import box.db.Wxtitle;
import box.db.WxtitleService;
import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import easyshop.html.jericho.Element;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

public class NewrankPageDealer implements IPageDealer{
	WxtitleService  wtService = new WxtitleService();
	WxpublicService  wpService = new WxpublicService();
	private OriHttpPage page;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();

	public NewrankPageDealer()
	{
		
	}
	
	@Override
	public List<PageRef> deal(OriHttpPage _page) {
		page = _page;
		List<PageRef> newurls = new ArrayList<PageRef>();
		if (page.getRefWord().indexOf("first")>=0){
			List<PageRef> refs = new ArrayList<PageRef>();
//			refs.addAll(buildSearchWxpublicUrls());
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

	public List<PageRef> buildSearchWxpublicUrls()
	{
		List<PageRef> refs = new ArrayList<PageRef>();
		List<String> keys = new ArrayList<String>();
		keys.add("%u6DF1%u5733");
		//htmlHelper.init(page.getUrlStr(), page.getContent(), "utf-8");
		
		for (int i=0; i<keys.size();i++)
		{
			String url = "http://www.5118.com/weixin/officials/search/%E6%B7%B1%E5%9C%B3";
			PageRef ref = new PageRef(url,"findwpname");
			refs.add(ref);
		}
		
		return refs;
	}
	
	public List<PageRef> buildWxpublicUrls()
	{
		List<PageRef> refs = new ArrayList<PageRef>();
		List<Wxpublic> wxps = wpService.findNotSearchWp();
		for (int i=0;i<wxps.size();i++)
		{
			String url = "http://www.newrank.cn/public/info/detail.html?account="+wxps.get(i).getWxhao();
			PageRef ref = new PageRef(url,"findwptitle");
			refs.add(ref);
		}
		return refs;
	}
	
	public void findPublicNames()
	{
		List<Wxpublic> wxps = new ArrayList<Wxpublic>();
		htmlHelper.init(page.getUrlStr(), page.getContent(), "utf-8");
		String[] namesContent = htmlHelper.getBlocksByOneProp("div", "class", "wx_detail");
		for (int i=0;i<namesContent.length;i++)
		{
			htmlHelper.init(page.getUrlStr(), namesContent[i].getBytes(), page.getCharSet());
			String name = htmlHelper.getBlock("h3");
			String haoStr = htmlHelper.getBlockByOneProp("p", "class","wxh");
		}
		wpService.addWxpublic(wxps);
	}
	
	public void dealPublicTitles()
	{
		WxtitleService  wtService = new WxtitleService();
		wtService.init();
		WxpublicService  wpService22 = new WxpublicService();
		List<Wxtitle> newtitles = new ArrayList<Wxtitle>();
		String str = new String(page.getContent());
		System.out.println(str);
		htmlHelper.init(page.getContent());
		String[] list = htmlHelper.getBlocksByOneProp("ul", "class", "article_list");
		for (int i=0;i<list.length;i++)
		{
			htmlHelper.init(list[i].getBytes());
			Element e = htmlHelper.getElementByKey("a",null);
			if (e!=null){
				Wxtitle wt = new Wxtitle();
				newtitles.add(wt);
			}
		}
		wtService.addNewwxtitle(newtitles);
	}
	
	@Override
	public String getSiteId() {
		// TODO Auto-generated method stub
		return "newrank";
	}

	@Override
	public String getFirstUrl() {
		// TODO Auto-generated method stub
		return "http://www.newrank.cn";
	}

	@Override
	public List<PageRef> getFirstRefs() {
		List<PageRef> refs = new ArrayList<PageRef>();
		List<String> keys = new ArrayList<String>();
		keys.add("深圳");		
		
		for (int i=0; i<keys.size();i++)
		{
			String strUrl = "http://www.newrank.cn/public/info/detail.html?account=YBSJ55";
			PageRef ref = new PageRef(strUrl,"first");
			ref.setRefId(12);
			refs.add(ref);
		}
		// TODO Auto-generated method stub
		return refs;
	}

}
