package box.weixin;

import java.util.ArrayList;
import java.util.List;

import box.db.WxService;
import box.db.Wxpublic;
import box.db.Wxtitle;
import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import es.webref.model.PageRef;

public class A5118PageDealer implements IPageDealer{
	private WxService wxService = new WxService();
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
			
			findPublicNames();
			List<PageRef> refs = buildSearchWxpublicUrls();
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
		List<PageRef> refs = new ArrayList<PageRef>();
		List<Wxpublic> wxps = wxService.findpublics(page.getSiteId());
		for (int i=0;i<wxps.size();i++)
		{
			String url = ""+wxps.get(i).getWxname();
			PageRef ref = new PageRef(url,"findwptitle");
			refs.add(ref);
		}
		return refs;
	}
	
	public void findPublicNames()
	{
		List<Wxpublic> wxps = new ArrayList<Wxpublic>();
		htmlHelper.init(page.getUrlStr(), page.getContent(), "utf-8");
		String[] namesContent = htmlHelper.getBlocksByOneProp("div", "class", "weixin-officialslist-info clearfix");
		for (int i=0;i<namesContent.length;i++)
		{
			htmlHelper.init(page.getUrlStr(), namesContent[i].getBytes(), page.getCharSet());
			String name = htmlHelper.getBlock("h3");
			String haoStr = htmlHelper.getBlockByOneProp("p", "class","wxh");
		}
		wxService.addWxpublic(wxps);
	}
	
	public void dealPublicTitles()
	{
		List<Wxtitle> newtitles = new ArrayList<Wxtitle>();
		
		wxService.addNewwxtitle(newtitles);
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
