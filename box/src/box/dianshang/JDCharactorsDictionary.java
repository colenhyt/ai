/*
 * Created on 2005-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package box.dianshang;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import es.datamodel.ICharactorsDictionary;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JDCharactorsDictionary implements ICharactorsDictionary {


    private static JDCharactorsDictionary pageChas;

    public static JDCharactorsDictionary getInstance() {
        if (pageChas == null)
            pageChas = new JDCharactorsDictionary();
        return pageChas;
    }

    public boolean isUseLessURL(String url) {
        return false;

    }

    private final static Set catKeyset=new HashSet();
    private final static List<String> endKeys=new ArrayList<String>();
   public JDCharactorsDictionary(){
	   
    	catKeyset.add(catKeys2);
    	catKeyset.add(catKeys3);
//    	url:
//    		url:
//    		url:
//    		url:
//    		url:
//    		url:
//    		url:
//    		url:
//    		url:
//    		url:http://www.china-pub.com/law/
//    		url:http://www.china-pub.com/wenxue/
//    		url:http://www.china-pub.com/yishu/
//    		url:http://www.china-pub.com/shaoer/
//    		url:http://www.china-pub.com/sheke/
//    		url:http://www.china-pub.com/shenghuoxiuxian/
//    		url:http://www.china-pub.com/zhongxiaoxuejiaoyu/
//    		url:http://www.china-pub.com/zirankexue/
//    		url:http://www.china-pub.com/sale/
//    		url:http://www.china-pub.com/exam/
//    	endKeys.add("http://www.china-pub.com/itbook/");
//    	endKeys.add("http://www.china-pub.com/tx/");
//    	endKeys.add("http://www.china-pub.com/tx/");
//    	endKeys.add("http://www.china-pub.com/math/");
//    	endKeys.add("http://www.china-pub.com/machine/");
//    	endKeys.add("http://www.china-pub.com/jz/");
//    	endKeys.add("http://www.china-pub.com/yixue/");
//    	endKeys.add("http://www.china-pub.com/nongye/");
//    	endKeys.add("http://www.china-pub.com/jingguan/");
//    	endKeys.add("http://www.china-pub.com/lang/");
//    	endKeys.add("");
//    	endKeys.add("");
//    	endKeys.add("");
//    	endKeys.add("");
//    	endKeys.add("");
//    	endKeys.add("");
//    	endKeys.add("");
//    	endKeys.add("");
//    	endKeys.add("");
//    	endKeys.add("");
    }
   /*
     * (non-Javadoc)
     * 
     * @see com.microsky.shopping.spider.links.IPageCharactorsDictionary#isCatalog(java.lang.String)
     */
    private String[] catalogChas={"/power_search.asp","index/index.html","mall/","epub_main.asp","browse_result.asp",
            "browse_result.jsp","all_type.asp"};
    private final static String[] branchChas = { "jingguan" };    
    private final static String[] branchChas2 = { "browse_index.asp","math/","tx/","jz/","lang/","law/",
            "machine/","itbook/","exam/default.asp","ebooknew/main.asp","minishopping/default.asp" }; 
    private final static String[] catKeys2={"/search/rank.asp","typeid"}; 
    private final static String[] catKeys3={"browse_result.asp","typeid"}; 
    public boolean isCatalog(String url) {
	    url = url.toLowerCase();
		
	    if (isItem(url))
	        return false;
	
	    
	    if (StringHelper.containsAll1AtLstIgnoreCase(url, catKeyset)){
	        return true;
	    }
	
	    if (StringHelper.end1atLeast(url, endKeys))
	    	return true;
	    
	    return false;
	}
    
    public boolean isSearchCat(String url)
    {
    	return url.indexOf("search.jd.com/Search?")>0;
    }
    
	public boolean isCatalog2(String url) {
        if (StringHelper.contains1atLeast(url, catalogChas))
            return true;
        
        if (url.indexOf("www.china-pub.com") < 0
                && (url.endsWith("joyo.com") || url.endsWith("china-pub.com/"))) {
            if (StringHelper.contains1atLeast(url, branchChas))
                return true;
        }

        if (url.indexOf("china-pub.com")>0&&StringHelper.contains1atLeast(url, branchChas2))
            return true;

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.microsky.shopping.spider.links.IPageCharactorsDictionary#isItem(java.lang.String)
     */
    private String[] itemChas={"/info.asp","/foreigninfo.asp"};
    public final static String itemKeyReg = "[\\d]{4,20}";
    public boolean isItem(String url) {

        if (isGeneral(url)||isUseLessURL(url))
            return false;

		String reg = "item.jd.com/";
		boolean isItemUrl = url.indexOf(reg)>=0;
		if (!isItemUrl) return false;
		
        String ikey = getItemKey(url);

        boolean containKey = (ikey != null && ikey.trim().length() > 0&&Pattern.matches(itemKeyReg,ikey));


        
        return containKey;

    }

    public String getItemKey(String url) {
		String url2 = cutHttpPre(url);
		String[] strs = url2.split("/");
		if (strs.length>2)
			return null;
		String itemStr = strs[strs.length-1];
	    if (Pattern.matches("[\\d]*.html", itemStr))
	    {
	    	return itemStr.substring(0,itemStr.indexOf("."));
	    }
	    return null;
	}

    static String cutHttpPre(String url)
    {
    	String pre = URLStrHelper.URL_HTTP_PREFIX;
    	int index = url.indexOf(pre);
    	if (index==0)
    	return url.substring(pre.length());
    	else
    		return url;
    }
    
	public String getCatKey(String url) {
    	String key=null;
        key=URLStrHelper.getParamValue(url, "typeid");
        if (key != null)
        	return key;
        return null;
    }

    //无法分辨的网页统称为common:
    public boolean isOther(String url) {
        return !isItem(url)&&!isCatalog(url)&&!isGeneral(url);
        }


    /*
     * (non-Javadoc)
     * 
     * @see com.microsky.shopping.spider.links.IPageCharactorsDictionary#isGeneral(java.lang.String)
     */
    private String[] genChas={"beian","faq","cart_tg","bbs","bookpinglun","search_result.asp"};
    public boolean isGeneral(String url) {

        if (StringHelper.contains1atLeast(url, genChas))
            return true;
        return false;
    }

	public boolean isItemSmallImg(String imgUrl, String urlkey) {
		// TODO Auto-generated method stub
		boolean b=imgUrl.endsWith(urlkey+"/cover.gif");
		return b;
		
	}

    //	private
}