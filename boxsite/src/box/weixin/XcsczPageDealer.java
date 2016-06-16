package box.weixin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import box.db.Wxpublic;
import box.db.WxpublicService;
import box.db.WxtitleService;
import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import easyshop.html.jericho.Attribute;
import easyshop.html.jericho.Element;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

public class XcsczPageDealer implements IPageDealer{
	private OriHttpPage page;
	int tick;
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();

	public XcsczPageDealer()
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
			//newurls.addAll(refs);
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
		keys.add("深圳");
		//htmlHelper.init(page.getUrlStr(), page.getContent(), "utf-8");
		
		for (int i=0; i<keys.size();i++)
		{
			String strKey = URLStrHelper.toUtf8String(keys.get(i));
			String url = "http://www.5118.com/weixin/officials/search/"+strKey;
			PageRef ref = new PageRef(url,"findwpname");
			ref.setRefId(10);
			refs.add(ref);
		}
		
		return refs;
	}
	
	public List<PageRef> buildWxpublicUrls()
	{
		List<PageRef> refs = new ArrayList<PageRef>();
		htmlHelper.init(page.getContent());
		List<Element> es = htmlHelper.getDivElementsByPropValueLike("style","width: 100%; height: 50px; border");
		for (int i=0;i<es.size();i++)
		{
			Element e = es.get(i);
			Attribute atr = e.getAttributes().get("onclick");
			String vv = atr.getValue();
			vv = vv.substring(vv.indexOf("href='")+6);
			vv = vv.substring(0,vv.length()-2);
			
			String url = "http://www.xcscz.com.cn/"+vv;
			PageRef ref = new PageRef(url,"findwptitle");
			ref.setRelWord(vv);
			refs.add(ref);			
		}
		return refs;
	}
	
	public void findPublicNames()
	{
		List<Wxpublic> wxps = new ArrayList<Wxpublic>();
		htmlHelper.init(page.getContent());
		String[] namesContent = htmlHelper.getDivsByClassValue("weixin-officialslist-info clearfix");
		for (int i=0;i<namesContent.length;i++)
		{
			htmlHelper.init(namesContent[i].getBytes());
			String desc = htmlHelper.getBlock("p");
			String div = htmlHelper.getBlock("h5");
			if (div==null) continue;
			String[] spans = htmlHelper.getBlocksByTagName("span");
			htmlHelper.init(div.getBytes());
			
			String key1 = "</span>";
			String key2 = "<small>";
			String name0 = htmlHelper.getBlock("span");
			String name = null;
			if (div.indexOf("<span")>0)
			 name = div.substring(0,div.indexOf("<span"));
			name += name0 + div.substring(div.indexOf(key1)+key1.length(),div.indexOf(key2));
			name = name.replace("\"", "");
			name = name.replace("null", "");
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
			wp.setStatus(0);
			wp.setCrdate(new Date());
			wp.setType(page.getRefId());
			wp.setWxhao(haoStr);
			wp.setWpdesc(desc);
			
			wxps.add(wp);
		}
		WxpublicService  wpService = new WxpublicService();
		wpService.init();
		wpService.addWxpublic(wxps);
	}
	
	public void dealPublicTitles()
	{
		WxtitleService  wtService = new WxtitleService();
		wtService.init();
		htmlHelper.init(page.getContent());
		String str = new String(page.getContent());
		tick++;
		System.out.println("内容("+tick+"):"+str.length());;
	}
	
	@Override
	public String getSiteId() {
		// TODO Auto-generated method stub
		return "xcscz";
	}

	@Override
	public String getFirstUrl() {
		// TODO Auto-generated method stub
		return "http://www.5118.com/weixin/officials/search/%E6%B7%B1%E5%9C%B3";
	}

	@Override
	public List<PageRef> getFirstRefs() {
		List<PageRef> refs = new ArrayList<PageRef>();
		List<String> keys = new ArrayList<String>();
		keys.add("xinli");		
		keys.add("rec");		
		keys.add("xingge");		
		keys.add("aiqing");		
		keys.add("nengli");		
		keys.add("quwei");		
		
		for (int i=0; i<keys.size();i++)
		{
			String strUrl = "http://www.xcscz.com.cn/"+URLStrHelper.toUtf8String(keys.get(i))+".htm";
			PageRef ref = new PageRef(strUrl,"first");
			ref.setRefId(10);
			refs.add(ref);
		}
//		String strUrl = "http://www.xcscz.com.cn/xinli.htm";
//		PageRef ref = new PageRef(strUrl,"first");
//		ref.setRefId(10);
//		refs.add(ref);		
		
		// TODO Auto-generated method stub
		return refs;
	}

	@Override
	public void pushSearchWord(String word) {
		// TODO Auto-generated method stub
		
	}
}
