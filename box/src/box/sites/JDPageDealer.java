package box.sites;

import box.util.IPageDealer;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.model.ProductItem;
import es.model.ItemPage;

public class JDPageDealer implements IPageDealer{
	private OriHttpPage page;
	private JDGoodsAnalyser analyser = new JDGoodsAnalyser();
	private JDCharactorsDictionary dict = new JDCharactorsDictionary();
	
	public void deal(OriHttpPage _page)
	{
		int a = 10;
		page = _page;
		//处理 目录
		if (dict.isCatalog(page.getUrlStr()))
		{
			parseCat_findItemUrls();
			parseCat_findCatUrls();
			parseCat_getGoodsItems();
		}else if (dict.isItem(page.getUrlStr()))		//处理内容页
		{
			parseItem();
		
		}
	}
	
	private void parseCat_getGoodsItems()
	{
		
	}
	
	private void parseCat_findItemUrls()
	{
		
	}
	
	private void parseCat_findCatUrls()
	{
		
	}
	
	private void parseItem()
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

}
