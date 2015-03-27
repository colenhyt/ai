/*
 * Created on 2005-10-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package box.dianshang;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.HttpPageGetter;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import easyshop.html.jericho.Source;
import easyshop.model.ProductItem;
import easyshop.model.ReviewItem;
import es.datamodel.ICharactorsDictionary;
import es.util.date.JavaDateHelper;
import es.util.html.HTMLContentHelper;
import es.util.pattern.ESPattern;
import es.util.string.StringHelper;
import es.webref.model.PageRef;

/**
 * @author Allenhuang
 *
 * created on 2005-10-5
 */
public class JDReviewContentAnalyser {
    public static final String VIP_PRICE = "PVIPPrice";
    
    public static Logger log = Logger.getLogger("CPReviewContentAnalyser");
    private String urlKey;    
    private ProductItem item;
    public Source source;
    
    private String content;
    HTMLInfoSupplier supplier;
    
    public String itemUrl;
    
    List<ReviewItem> reviews;
    public void sendContentInfo(String urlStr,String strContent) {        
        item=new ProductItem();
        itemUrl=urlStr;
        content=strContent;
        supplier=new HTMLInfoSupplier(itemUrl,content);
        source = new Source(content);
        b0analyse();        
    }

    public ProductItem receive() {
    	return item;
    }

	HttpPageGetter getter=new HttpPageGetter();
	private void b1AllFindReviews(){
	    //reviews:
		String reviewInfo=supplier.getBlockByTagNameAndKey("div", "【评 论】");
		if (reviewInfo!=null){
		int in=reviewInfo.indexOf("共有");
		int ee=reviewInfo.indexOf("人开贴评论");
		if (ee>in&&in>0){
			String aa=reviewInfo.substring(in+"共有".length(), ee);
			int paging=0;
			if (ESPattern.isNumber(aa)){
				int count=new Integer(aa).intValue();
				if (count%10==0)
					paging=count/20;
				else
					paging=count/20+1;

				if (count>0){
					List<ReviewItem> reviews=new ArrayList<ReviewItem>();
					reviews.addAll(findPagingReviews(itemUrl,content));
					
					if (paging>1){
						for (int i=2;i<=paging;i++){
							PageRef ref=new PageRef(itemUrl+"&Curpage="+i);
//							System.err.println(ref.getUrlStr());
							HttpPage page=getter.getHttpPageWithDefaultHttpClient(ref);
							reviews.addAll(findPagingReviews(ref.getUrlStr(),page.getStringContent()));
						}
					}
				    item.setReviews(reviews);
				    
				    if (count!=reviews.size()){
				    	System.err.println("计划有"+count+"个评论,仅找到"+reviews.size()+"个:"+itemUrl);
				    }				    
				}
			}
			
		}
		}
	}


	private void b0analyse() {
	    	b1AllFindReviews();
	
	}

	private List<ReviewItem> findPagingReviews(String urlStr,String strContent){
		List<ReviewItem> reviews=new ArrayList<ReviewItem>();
		if (strContent!=null){
		HTMLInfoSupplier thisgetter=new HTMLInfoSupplier(urlStr, strContent);
	
	String[] keys={"读者","送支鲜花支持"};
	String[] comms=thisgetter.getBlocksByTagNameAndKeys("table", keys);
	for (int i=0;i<comms.length;i++){
		ReviewItem review=new ReviewItem();
		HTMLInfoSupplier thisSupp=new HTMLInfoSupplier(itemUrl,comms[i]);
		List<PageRef> list=thisSupp.getUrls();
		String preStr="读者</font>：";
		int authorStart=comms[i].indexOf(preStr);
		if (authorStart>0){
			int end=comms[i].indexOf("&nbsp;",authorStart);
			String str=comms[i].substring(authorStart+preStr.length(),end);
			if (str.length()>0)
				review.setAuthor(str);
			int end2=comms[i].indexOf("&nbsp;<img",end);
			if (end2>0){
				String dateStr=comms[i].substring(end,end2).replace("&nbsp;", "");
				String pattern=JavaDateHelper.SQLSERVER_SM_PATTERN;
				if (dateStr.indexOf(":")>0)
					pattern=JavaDateHelper.SQLSERVER_SM_PATTERN4;
				Date date=JavaDateHelper.getDate(dateStr.trim(),pattern);
				review.setCrDate(date);
			}
		}
		
		String contextStr=thisSupp.getBlockByOneProp("td", "class","padding_td");
		if (contextStr!=null){
			contextStr=HTMLContentHelper.getPureText(contextStr);
			review.setContent(contextStr);
		}
		
		//
		String[] imgs=thisSupp.getImgUrlLists();
		if (imgs!=null&&imgs.length>0){
			int starts=0;
			for (int k=0;k<imgs.length;k++){
				if (imgs[k].endsWith("art1.gif"))
					starts++;
			}
	    	review.setStars(starts);
	}    		
	
		if (review.getContent()!=null&&review.getCrDate()!=null&&!reviews.contains(review)){
//			System.out.println(urlKey+":"+review.getAuthor()+";"+review.getContent());
			review.setOriUrlStr(urlStr);
			reviews.add(review);
		}
	}		
		}
		return reviews;
	}

}
