package es.bsites;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cl.thread.ThreadsWorker;
import easyshop.download.collection.SiteStringConverter;
import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.HttpPageGetter;
import easyshop.html.HTMLInfoSupplier;
import easyshop.model.ProductItem;
import es.util.html.HTMLContentHelper;
import es.util.pattern.ESPattern;
import es.util.string.StringHelper;

public class SearchResultInfoFinder {
	protected HTMLInfoSupplier getter;
	protected String context;
	protected String urlStr;
	protected SearchSiteResults results;
	
	public SearchResultInfoFinder(){
		
	}
	public SearchResultInfoFinder(String urlStr){
		this.urlStr=urlStr;
		HttpPageGetter pgetter=new HttpPageGetter();
		HttpPage page=pgetter.getWebPageWithHttpClient(urlStr);
		this.context=page.getStringContent();
//		System.out.println(context);
		a0FindInfo();
	}

	public SearchResultInfoFinder(String siteStr,int type,String word){
		this(GetSiteSearchUrl.get(siteStr, type, word));
	}
	
	public SearchResultInfoFinder(HttpPage page){
		this.urlStr=page.getUrlStr();
		this.context=page.getStringContent();
		a0FindInfo();
	}
	
	private void a0FindInfo(){
		results=new SearchSiteResults();
		results.setSearchStr(urlStr);
		
		if (context==null)return;
		
		getter=new HTMLInfoSupplier(urlStr,context);
		results.setCount(a1FindCount());
	}
	
	private int a1FindCount(){
		if (urlStr.indexOf("dangdang.com")>0)
			return mFindDDCount();
		else  if (urlStr.indexOf("amazon.cn")>0||urlStr.indexOf("joyo.com")>0)
			return mFindJOYOCount();
		else  if (urlStr.indexOf("china-pub.com")>0)
			return mFindCPCount();
		else  if (urlStr.indexOf("jingqi.com")>0)
			return mFindJINGQICount();
		else  if (urlStr.indexOf("wl.cn")>0||urlStr.indexOf("welan.com")>0)
			return mFindWeLanCount();
		else  if (urlStr.indexOf("99read.com")>0)
			return mFind99Count();
		else  if (urlStr.indexOf("bookschina.com")>0)
			return mFindBCChinaCount();
		else  if (urlStr.indexOf("dearbook.com")>0)
			return mFindDearCount();
		else  if (urlStr.indexOf("sinoshu.com")>0)
			return mFindSinoShuCount();
		else  if (urlStr.indexOf("wfjsd.com")>0)
			return mFindWFJCount();
		else  if (urlStr.indexOf("golden-book.com")>0)
			return mFindGBCount();
		else  if (urlStr.indexOf("2688.com")>0)
			return mFind2688Count();
		else  if (urlStr.indexOf("dayoo.com")>0)
			return mFindDYCount();
		else  if (urlStr.indexOf("chaoyishudian.com")>0)
			return mFindChaoYiCount();
		else  if (urlStr.indexOf("buildbook.com.cn")>0)
			return mFindBuildBookCount();
		else  if (urlStr.indexOf("jqcq.com")>0)
			return mFindBJQCQCount();
			
		return -2;
	}

	private int mFindDDCount(){
			String str="在 <label id=\"lblNavDangDang\"><font color='#FF6F02'>当当网</font></label> 中搜";
		int start=context.indexOf(str);
		if (start>0){
			int end1=context.indexOf("共有",start+str.length());
		int end2=context.indexOf("条结果",start+str.length());
		if (end1>0&&end2>end1+2){
			String countStr=context.substring(end1+2,end2);
			countStr=HTMLContentHelper.getPureText(countStr);
			if (countStr!=null&&ESPattern.isNumber(countStr.trim()))
				return new Integer(countStr).intValue();			
		}
		}else 	{	
		String block=getter.getBlockByOneProp("div","class","1");
		String[] sKeys={"在","当当网","中搜","条结果","共有"};
		if (block!=null&&StringHelper.containsAll(block,sKeys)){
			int start2=block.indexOf("共有")+2;
			int end2=block.indexOf("条结果");
			if (start2>0&&end2>start2){
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
			}
		}
		}
		return -2;
	}

	private int mFindJOYOCount(){
		String block=getter.getBlockByOneProp("div","class","result");
		String[] sKeys={"显示","条","共"};
		if (block!=null&&StringHelper.containsAll(block,sKeys)){
			int start2=block.indexOf("共")+1;
			int end2=block.lastIndexOf("条");
			if (start2>0&&end2>start2){
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
			}
		}
		return -2;
	}

	private int mFind99Count(){
		String block=getter.getBlockByOneProp("span","id","LabelInforamtion");
		String[] sKeys={"显示","条(搜索","共"};
		if (block!=null&&StringHelper.containsAll(block,sKeys)){
			int start2=block.indexOf("共")+1;
			int end2=block.lastIndexOf("条(搜索");
			if (start2>0&&end2>start2){
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
			}
		}
		return -2;
	}

	private int mFindBCChinaCount(){
		String block=getter.getBlockByOneProp("div","id","books");
		String[] sKeys={"检索到","种现货商品","共"};
		if (block!=null&&StringHelper.containsAll(block,sKeys)){
			int start2=block.indexOf("共")+1;
			int end2=block.indexOf("种现货商品");
			if (start2>0&&end2>start2){
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
			}
		}
		return -2;
	}

	private int mFindCPCount(){
		String block=getter.getBlockByOneProp("div","id","xianshi1");
		String[] sKeys={"显示","条","共"};
		if (block!=null&&StringHelper.containsAll(block,sKeys)){
			int start2=block.indexOf("共")+1;
			int end2=block.lastIndexOf("条");
			if (start2>0&&end2>start2){
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
			}
		}
		return -2;
	}

	private int mFindDearCount(){
		String block=getter.getBlockByOneProp("div","id","divPage1");
		String[] sKeys={"页码","本","共"};
		if (block!=null&&StringHelper.containsAll(block,sKeys)){
			int start2=block.indexOf("共")+1;
			int end2=block.lastIndexOf("本");
			if (start2>0&&end2>start2){
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
			}
		}
		return -2;
	}

	private int mFindSinoShuCount(){
		String block=getter.getBlockByOnePropAndKey("div","id","divNaviTop","条结果");
		String[] sKeys={"销量","排列"};
		if (block!=null&&StringHelper.containsAll(block,sKeys)){
			int end=block.indexOf("条结果");
			if (end>0){
			String countStr=HTMLContentHelper.getPureText(block.substring(0,end));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
			}
		}
		return -2;
	}

	private int mFind2688Count(){
		String block=getter.getBlockByOnePropAndKey("span","id","LabelSummary","项符合");
		if (block!=null){
			int start2=block.indexOf("有")+1;
			int end2=block.lastIndexOf("项符合");
			if (start2>0&&end2>start2){
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
			}
		}
		return -2;
	}

	private int mFindGBCount(){
		String block=getter.getBlockByTagNameAndKeys("td",new String[]{"共","条","页","第"});
		if (block!=null){
			int start2=block.indexOf("共")+1;
			int end2=block.indexOf("条");
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
		}
		return -2;
	}

	private int mFindDYCount(){
		int i=context.indexOf("搜索不到数据");
		if (i<0)
			return -1;
		return -2;
	}
	
	private int mFindWFJCount(){
		int i=context.indexOf("对不起，没有查讯到您需要的商品");
		if (i<0)
			return -1;
		return -2;
	}

	private int mFindJINGQICount(){
		String str="找到相关结果约";
		int start=context.indexOf(str);
		if (start>0){
			String endStr="项. (";
			int end1=context.indexOf(endStr,start+str.length());
		if (end1>0){
			String countStr=context.substring(start+str.length(),end1);
			countStr=HTMLContentHelper.getPureText(countStr);
			if (countStr!=null&&ESPattern.isNumber(countStr.trim()))
				return new Integer(countStr).intValue();			
		}
		}
		return -2;
	}

	private int mFindWeLanCount(){
		String str="以下 <span id=";
		int start=context.indexOf(str);
		if (start>0){
			String endStr="条搜索结果";
			int end1=context.indexOf(endStr,start+str.length());
		if (end1>0){
			String countStr=context.substring(start+str.length(),end1);
			countStr=HTMLContentHelper.getPureText("<span id="+countStr);
			if (countStr!=null&&ESPattern.isNumber(countStr.trim()))
				return new Integer(countStr).intValue();			
		}
		}
		return -2;
	}

	private int mFindChaoYiCount(){
		String str="共查讯到";
		int start=context.indexOf(str);
		if (start>0){
			String endStr="种商品";
			int end1=context.indexOf(endStr,start+str.length());
		if (end1>0){
			String countStr=context.substring(start+str.length(),end1);
			countStr=HTMLContentHelper.getPureText(countStr);
			if (countStr!=null&&ESPattern.isNumber(countStr.trim()))
				return new Integer(countStr).intValue();			
		}
		}
		return -2;
	}

	private int mFindBuildBookCount(){
		String block=getter.getBlockByTagNameAndKeys("td",new String[]{"共","记录","<strong class=\"FontBig\">图书搜索</strong>"});
		if (block!=null){
			int start2=block.indexOf("共")+1;
			int end2=block.indexOf("记录");
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
		}
		return -2;
	}	
	

	private int mFindBJQCQCount(){
		String block=getter.getBlockByOnePropAndKeys("td","class","subBar",new String[]{"显示","共","商品"});
		if (block!=null){
			int start2=block.indexOf("共")+1;
			int end2=block.indexOf("商品");
			String countStr=HTMLContentHelper.getPureText(block.substring(start2,end2));
			if (countStr!=null&&ESPattern.isNumber(countStr))
				return new Integer(countStr).intValue();
		}
		return -2;
	}	
	public SearchSiteResults outResults(){
		return results;
	}
}
