package box.sites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import easyshop.html.nutch.NutchHTMLProxy;
import easyshop.model.ProductItem;
import es.model.ItemPage;
import es.util.url.URLStrFormattor;
import es.webref.model.PageRef;

public class JDPageDealer implements IPageDealer{
	private String					strFirstUrl;
	private String					strKeyWord;
	private Map<String,Boolean>		mapItemKeys;
	private OriHttpPage page;
	HTMLInfoSupplier htmlHelper;
	private JDGoodsAnalyser analyser = new JDGoodsAnalyser();
	private JDCharactorsDictionary dict = new JDCharactorsDictionary();
	
	public JDPageDealer()
	{
		strKeyWord = "%E6%97%85%E8%A1%8C%E7%AE%B1";
		strFirstUrl = "http://search.jd.com/Search?keyword="+strKeyWord+"&enc=utf-8";
		mapItemKeys = new HashMap<String,Boolean>();
		htmlHelper = new HTMLInfoSupplier();
	}
	public List<PageRef> deal(OriHttpPage _page)
	{
		page = _page;
		List<PageRef> newurls = new ArrayList<PageRef>();
		if (page.getRefWord().indexOf("first")>=0){
			List<PageRef> refs = buildCatUrls();
			newurls.addAll(refs);
		}
		//处理 目录
		if (dict.isSearchCat(page.getUrlStr()))
		{
			List<PageRef> refs = parseCat_findItemUrls();
			newurls.addAll(refs);
			
			parseCat_dealGoodsItems();
		}else if (dict.isItem(page.getUrlStr()))		//处理内容页
		{
			String itemKey = dict.getItemKey(page.getUrlStr());
			if (!mapItemKeys.containsKey(itemKey))
				mapItemKeys.put(itemKey, true);
			
			parseItempage();
		
		}else		//find helpful urls:cat,item
		{
			List<PageRef> refs = parseCat_findItemUrls();
			newurls.addAll(refs);
		}
		
		return newurls;
	}
	
	private void parseCat_dealGoodsItems()
	{
		
	}
	
	private List<PageRef> parseCat_findItemUrls()
	{
		PageRef[] links=NutchHTMLProxy.get().gePageRefs(page.getUrlStr(), page.getContent(), page.getCharSet());
		List<PageRef> usefulUrls = new ArrayList<PageRef>();
		for (int i=0;i<links.length;i++)
		{
			String url = links[i].getUrlStr();
			String itemKey = dict.getItemKey(url);
			if (!mapItemKeys.containsKey(itemKey)&&dict.isItem(url)){
				usefulUrls.add(links[i]);
			}
		}
		return usefulUrls;
	}
	
	//根据初始url特征和paging自行构造剩下的catUrls
	public List<PageRef> buildCatUrls()
	{
		int paging = 0;
		htmlHelper.init(page.getUrlStr(),page.getContent(),page.getCharSet());
		String[] strPagingDivs = htmlHelper.getBlocksByOneProp("span", "class","page-skip");
		if (strPagingDivs.length>0)
		{
			String div = strPagingDivs[0];
			int startIndex = div.indexOf("共");
			int endIndex = div.indexOf("页");
			String strPaging = div.substring(startIndex,endIndex);
			if (Pattern.matches("[\\d]*", strPaging))
				paging = Integer.valueOf(strPaging);
		}
		List<PageRef> refs = new ArrayList<PageRef>();
		for (int i=1;i<=paging;i++)
		{
			String strUrl = "http://search.jd.com/Search?keyword="+strKeyWord+"&enc=utf-8#keyword="+strKeyWord+"&enc=utf-8&qrst=1&ps=addr&rt=1&stop=1&sttr=1&click=&psort=&page="+i;
			PageRef ref = new PageRef(URLStrFormattor.decode(strUrl));
			ref.setPaging(i);
			refs.add(ref);
		}
		return null;
	}
	
	private void parseItempage()
	{
		ItemPage ipage = new ItemPage();
		ipage.setContent(page.getContent());
		ipage.setItemActionType(page.getTypeId());
		analyser.sendContentInfo(ipage);	
		
        ProductItem pc = (ProductItem) analyser.receive();			
	}
	
	public String getSiteId()
	{
		return SITEID_JD;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public String getFirstUrl() {
		// TODO Auto-generated method stub
		return strFirstUrl;
	}

}
