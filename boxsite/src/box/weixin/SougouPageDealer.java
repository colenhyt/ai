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
import es.webref.model.PageRef;

public class SougouPageDealer implements IPageDealer{
	WxtitleService  wtService = new WxtitleService();
	WxpublicService  wpService = new WxpublicService();
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

	private List<PageRef> buildSearchWxpublicUrls()
	{
		List<PageRef> refs = new ArrayList<PageRef>();
		List<String> keys = new ArrayList<String>();
		//keys.add("%E6%B7%B1%E5%9C%B3");
		//htmlHelper.init(page.getUrlStr(), page.getContent(), "utf-8");
		
		for (int i=0; i<keys.size();i++)
		{
			String url = "http://weixin.sogou.com/weixin?query="+keys.get(i);
			PageRef ref = new PageRef(url,"findwpname");
			refs.add(ref);
		}
		
		return refs;
	}
	
	private List<PageRef> buildWxpublicUrls()
	{
		List<PageRef> refs = new ArrayList<PageRef>();
		List<Wxpublic> wxps = wpService.findNotSearchWp();
		for (int i=0;i<wxps.size();i++)
		{
			String url = ""+wxps.get(i).getWxname();
			PageRef ref = new PageRef(url,"findwptitle");
			refs.add(ref);
		}
		return refs;
	}
	
	private void findPublicNames()
	{
		List<Wxpublic> wxps = new ArrayList<Wxpublic>();
		htmlHelper.init(page.getContent());
		String[] namesContent = htmlHelper.getBlocksByOneProp("div", "class", "wx-rb bg-blue wx-rb_v1 _item");
		for (int i=0;i<namesContent.length;i++)
		{
			String divImg = htmlHelper.getBlockByOneProp("div", "class", "img-box");
			String div = htmlHelper.getBlockByOneProp("div", "class", "txt-box");
			
			htmlHelper.init(divImg.getBytes());
			Element imge = htmlHelper.getElementByKey("img", null);
			String imgSrc = imge.getAttributes().getValue("src");
			
			htmlHelper.init(div.getBytes());
			String nameStr = htmlHelper.getBlock("h3");
			String haoStr = htmlHelper.getBlock("h4");
			htmlHelper.init(haoStr.getBytes());
			haoStr = htmlHelper.getBlock("span");
			haoStr = haoStr.replaceAll("微信号：", "");
			
			Wxpublic wp = new Wxpublic();
			wp.setWxhao(haoStr);
		}
		wpService.addWxpublic(wxps);
	}
	
	private void dealPublicTitles()
	{
		List<Wxtitle> newtitles = new ArrayList<Wxtitle>();
		
		wtService.addNewwxtitle(newtitles);
	}
	
	@Override
	public String getSiteId() {
		// TODO Auto-generated method stub
		return "sougou";
	}

	@Override
	public String getFirstUrl() {
		// TODO Auto-generated method stub
		return "http://weixin.sogou.com/weixin?query=%E6%B7%B1%E5%9C%B3";
	}

}
