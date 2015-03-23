package box.sites;
/*
 * Created on 2006-2-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import easyshop.downloadhelper.HttpPageGetter;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.HTMLInfoSupplier;
import easyshop.html.jericho.Element;
import easyshop.html.jericho.Source;
import easyshop.html.jericho.StartTag;
import easyshop.model.Item;
import easyshop.model.ProductItem;
import easyshop.model.ReviewItem;
import es.datamodel.ContentInfo;
import es.datamodel.ICharactorsDictionary;
import es.datamodel.PageContentAnalyser;
import es.datamodel.util.RetrPriceHelper;
import es.model.OriginalPage;
import es.util.date.JavaDateHelper;
import es.util.html.HTMLContentHelper;
import es.util.io.CollectionHelper;
import es.util.io.FileUtils;
import es.util.pattern.ESPattern;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;

/**
 * @author Allenhuang
 *
 * created on 2006-2-26
 */
public class USAmazonProductContentAnalyser implements PageContentAnalyser {
	public static Logger log = Logger.getLogger("ChinaPubProductContentAnalyser");
	
	ContentInfo info;
	
	 public String content;
	 
    public Source source;

    public HTMLInfoSupplier getter;
    
    public String itemUrl;    
    public static final String VIP_PRICE = "PVIPPrice";

    private float _price2;

    private String urlKey;
    String[] commonPsKeywords = {"市 场 价 ："};
    String[] psKeywords = {"普通会员","会员价"};
    String[] vipPsKeywords = {"4-5星会员：","校园特惠价："};;
	String cPsKeyword,psKeyword,vipPskeyword;
    String div1;
    USAmazonCharactorsDictionary pageCha=USAmazonCharactorsDictionary.getInstance();
    ProductItem item;
    public void sendContentInfo(ContentInfo page) {   
    	item=new ProductItem();
        this.info=page;
        urlKey=pageCha.getItemKey(info.getUrlStr());
        itemUrl=info.getUrlStr();
        content = info.toStringContent();
        source = new Source(content);
		getter = new HTMLInfoSupplier(info.getUrlStr(), content.getBytes(), info
				.getEncoding());        
		b0analyse();
    }

    public Item receive() {
        return item;
    }

	public ICharactorsDictionary getDictionary() {
		// TODO Auto-generated method stub
		return USAmazonCharactorsDictionary.getInstance();
	}

	private void b1FindPsAndStock(){
			if (content.indexOf("缺货")>0)
				item.setHasStock(false);
			else
				item.setHasStock(true);
			
		long start=System.currentTimeMillis();
	      
	      if (psKeyword==null){
	          System.err.println("can not find any price keyword:"+itemUrl);
	          return;
	      }
	      
	      //analyse product name:       
	      String name=null;
	      start=System.currentTimeMillis();
	      if (name == null) {
	          name = findNameByTitleAndMeta();
	      }//        System.out.println("cost time1 is "+(System.currentTimeMillis()-start));
	      if (name==null||name.trim().length()<=0)
	          return;
	      
	      item.setName(name);
	      
	      //analyse price img or price string:
	      start=System.currentTimeMillis();
	      item.setPrice(getPs(div1,psKeyword,vipPskeyword));////
	//      System.out.println("cost time2 is "+(System.currentTimeMillis()-start));
	      if (item.getPrice()<=0)
	          return;
	      
	      
	      start=System.currentTimeMillis();
	      item.setCommonPrice(getPs(div1,cPsKeyword,psKeyword));//////////////////////////////
	      
	      start=System.currentTimeMillis();
	      if (vipPskeyword!=null)
	      item.setVipPrice(sGetPs(div1,new String[]{vipPskeyword}));//////////////////////////////////
	//      System.out.println("cost time4 is "+(System.currentTimeMillis()-start));
	
	      start=System.currentTimeMillis();
		
		}

	private void b1FindReviews(){
			item.setReviews(new ArrayList<ReviewItem>(mFindReviews()));
	}

	USAmazonReviewContentAnalyser analyser=new USAmazonReviewContentAnalyser();
	
	private void b1AllFindAllReviews(){
	    //reviews:
		analyser.sendContentInfo(info.getUrlStr(),content);
	    item.setReviews(analyser.receive().getReviews()); 
	}
	
	private void b1FindPicture(){
		
	        analyseImgURL();//////////////////////////////////
		}

	private void b1FindAll(){
		b1FindPsAndStock();
	
        analyseImgURL();//////////////////////////////////
	//        System.out.println("cost time7 is "+(System.currentTimeMillis()-start));
	        
	        //get summary:
	   b1FindSummary();
	        
	        //get outline:
			
		item.setReviews(new ArrayList<ReviewItem>(mFindReviews()));
	}

	private void b1FindSummary(){
		try {
        	String summStr=getter.getBlockByOnePropAndKey("div", "class","margin13","【内容简介】");
        	if (summStr!=null){
			if (summStr.indexOf("<div class=")>0)
	        	summStr=summStr.substring(0,summStr.indexOf("<div class="));
			item.setSummary(HTMLContentHelper.getPureText(summStr));
			item.setSummary(item.getSummary().replace("【内容简介】", ""));
        	}
			String outKey="【目录信息】</b></h3>";
			int outStart=content.indexOf(outKey);
			if (outStart>0){
				int end=content.indexOf("<span ",outStart);
				if (end>outStart)
					item.setOutline(content.substring(outStart+outKey.length(),end).trim());
			}
		} catch (Exception e) {
			// log error here
			FileUtils ser=new FileUtils();
			ser.setRoot("C:\\bikeedata\\errorpage\\");
//			ser.serialize(content.getBytes(),siteId,urlKey,info.getUrlStr(),-1000);
			log.error(e.getMessage()+"; site:"+info.getSiteId()+";urlstr"+info.getUrlStr());
		} 		
	}
	
	private void analyseImgURL(){
	    String img="";
	    if (urlKey!=null)
	        img=urlKey+"/zcover.gif";
	    
		HTMLInfoSupplier getter = new HTMLInfoSupplier(info.getUrlStr(), content.getBytes(), info
				.getEncoding());
	    String[] imgValues=getter.getImgPropValuesByEndValueKey("mysrc");
	    if (imgValues!=null&&imgValues.length>0){
	    	for (int i=0;i<imgValues.length;i++){
	    		if (imgValues[i].toLowerCase().endsWith(urlKey)){
	    			item.setImgUrl(imgValues[i]);
	        		break;
	    	}
	    	}
	    }
	}

	private void analyseKeyword(){
	        if (div1!=null){
	//        	div1=ItemInfoBlockFilter.basicFilter(div1);
	        //common:
	        for (int i = 0; i < commonPsKeywords.length; i++) {
	            int in=content.indexOf(commonPsKeywords[i]);
	                if (in>0){
	                    cPsKeyword=commonPsKeywords[i];
	                break;
	            }
	        }        
	        
	        //price:
	        for (int i = 0; i < psKeywords.length; i++) {
	            int in=content.indexOf(psKeywords[i]);
	                if (in>0){
	                    psKeyword=psKeywords[i];
	                break;
	            }
	        }        
	        
	        //vip price:
	        for (int i = 0; i < vipPsKeywords.length; i++) {
	            int in=content.indexOf(vipPsKeywords[i]);
	                if (in>0){
	                    vipPskeyword=vipPsKeywords[i];
	                break;
	            }
	        }        
	        }
	    }

    public static String isbnKey="【书      号】";
    public static String authorKey="【作　　者】";
    public static String pubKey="【出 版 社】";
	private void analyseOthers(){
	    	
	    	String[] divs=getter.getBlocksByTagNameAndKey("table", isbnKey);
	    	if (divs!=null&&divs.length>0){
	    		byte[] content=divs[0].getBytes();    
	    		
	    		HTMLContentHelper helper=new HTMLContentHelper();
	    		List<String> texts=null;
	    			texts = helper.getTextValues(content,info.getEncoding());
	    			if (texts!=null){
	    			String author=CollectionHelper.getListNextElement(authorKey,texts);
	    			if (author!=null&&author.endsWith("["))
	    				author=author.substring(0,author.length()-1);
	    			String iS="【书号】";
	    			String isbn=CollectionHelper.getListNextElement(iS,texts);
	    			if (isbn==null){
	    				String iStr=StringHelper.findContainsStrFF(iS, texts);
	    				if (iStr!=null)
	    				isbn=iStr.substring(iStr.indexOf(iS)+iS.length()).trim();
	    			}
	    			if (isbn!=null)
	    				isbn=isbn.replaceAll("-","");
	    			String publisher=CollectionHelper.getListNextElement("【出版社】",texts);
	    			if (publisher==null){
	    				String iStr=StringHelper.findContainsStrFF("【出版社】", texts);
	    				if (iStr!=null)
	    				publisher=iStr.substring(iStr.indexOf("【出版社】")+"【出版社】".length()).replace('　', ' ').trim();
	    			}	    			
	    			
	    			item.setAuthor(author);
	    			item.setIsbn(isbn);
	    			item.setPublisher(publisher);
	    			item.setOriName(CollectionHelper.getListNextElement("【原书名】",texts));
	    			item.setOriPubName(CollectionHelper.getListNextElement("【原出版社】",texts));
	    			item.setTranslateAuthor(CollectionHelper.getListNextElement("【译　　者】",texts));
	    			item.setSeriesName(CollectionHelper.getListNextElement("【丛书名】",texts));
	    			String kaiben=CollectionHelper.getListNextElement("【开本】",texts);
	    			if (kaiben==null){
	    				String iStr=StringHelper.findContainsStrFF("【开本】", texts);
	    				if (iStr!=null)
	    				kaiben=StringHelper.findString(iStr, "【开本】", "【").replace('　', ' ').trim();
	    			}		    			
	    			item.setKaiben(kaiben);
	    			String version=CollectionHelper.getListNextElement("【版次】",texts);
	    			if (version==null){
	    				String iStr=StringHelper.findContainsStrFF("【开本】", texts);
	    				if (iStr!=null)
	    				version=iStr.substring(iStr.indexOf("【版次】")+"【版次】".length());
	    			}	    			
	    			item.setVersion(version);
	    			
	    			String dateStr=CollectionHelper.getListNextElement("【出版日期】",texts);
	    			if (dateStr==null){
	    				String iStr=StringHelper.findContainsStrFF("【出版日期】", texts);
	    				if (iStr!=null)
	    				dateStr=iStr.substring(iStr.indexOf("【出版日期】")+"【出版日期】".length());
	    			}		    			
	    			if (dateStr!=null){
	    			dateStr=dateStr.replace("年", "-");
	    			dateStr=dateStr.replace("月", "");
	    			Date date=JavaDateHelper.getDate(dateStr,JavaDateHelper.PATTERN_YYMM);
	    			item.setPubDate(date);
	    			}
	    			String countStr=CollectionHelper.getListNextElement("【页码】",texts);
	    			if (countStr==null){
	    				String iStr=StringHelper.findContainsStrFF("【开本】", texts);
	    				if (iStr!=null&&iStr.trim().length()>0){
	    					countStr=StringHelper.findString(iStr, "【页码】", "【");
	    					if (countStr!=null)
	    					countStr=countStr.replace('　', ' ').trim();
	    						
	    				}
	    			}		    			
	    			if (ESPattern.isNumber(countStr))
	    				item.setPageCount((new Integer(countStr).intValue()));
	    	}
	    	}
	    }

	
	private String findNameByTitleAndMeta() {
	    List list = source.findAllStartTags("TITLE");
	    Iterator it = list.iterator();
	    String title = null;
	    String pname=null;
	    String start="《";
	    String end="》";
	    while (it.hasNext()) {
	        StartTag tag = (StartTag) it.next();
	        title = tag.getElement().getContentText();
	        if (title!=null)
	        	title=title.trim();
	        if (title!=null&&title.length()>0&&title.indexOf(start)==0&&title.lastIndexOf(end)>0){
	            pname=title.substring(title.indexOf(start)+1,title.lastIndexOf(end));
	        }
	    }
	    
	    
	    return pname;
	
	}

	public float getPrice2() {
	    return _price2;
	}

	private String getProdId() {
	    String key = "prodid=";
	    int index = info.getUrlStr().indexOf(key);
	    if (index > 0 && info.getUrlStr().length() >= index + key.length() + 10)
	        return info.getUrlStr().substring(index, index + key.length() + 10);
	    return "";
	
	}

	private float getPs(String content,String preStr,String endStr){
	   return RetrPriceHelper.findPrice(content,preStr,endStr);
	}

	private float sGetPs(String content,String[] preStrs){
	    float ps=0;
	    String str=RetrPriceHelper.CHAR_MONEYCHAR;
	    for (int i = 0; i < preStrs.length; i++) {
	        int j=content.indexOf(preStrs[i]);
	        if (j>0){
	            int in=content.indexOf(str,j);
	            if (in>0){
	                int beginIndex=in+str.length();
	                String substr=content.substring(beginIndex);
	            ps = RetrPriceHelper.getPriceByPreString(content,beginIndex);
	            break;
	            }
	        }
	    }       
	    return ps;        
	}

	protected String getUrlKey(){
		return urlKey;
	}

	public void setPrice2(float _price2) {
	    this._price2 = _price2;
	}

	private HttpPageGetter pageGetter=new HttpPageGetter();
	private Set<ReviewItem> mFindReviews(){
        Set<ReviewItem> reviews=new HashSet<ReviewItem>();
    	if (urlKey!=null){
        	String moreUrlStr="http://www.china-pub.com/member/bookpinglun/viewpinglun.asp?id="+urlKey;
        	OriHttpPage page=pageGetter.getOriHttpPage(new PageRef(moreUrlStr));
        	if (page!=null){
        	
        	findPagingReviews(page,reviews);
        	
        	HTMLInfoSupplier thisgetter=new HTMLInfoSupplier(page.getUrlStr(), page.getContent(), page.getCharSet());
        	List<PageRef> refs=thisgetter.getUrls();
        	for (Iterator it2=refs.iterator();it2.hasNext();){
        		PageRef ref=(PageRef)it2.next();
        		if (StringHelper.containsAll(ref.getUrlStr(),new String[]{"viewpinglun.asp","Curpage="})){
                	OriHttpPage page2=pageGetter.getOriHttpPage(ref);
                	findPagingReviews(page2,reviews);
        		}
        	}
        		
        	}
	}
		
		return reviews;
	}
	
	private void findPagingReviews(OriHttpPage page,Set<ReviewItem> reviews){
    	if (page!=null){
    	HTMLInfoSupplier thisgetter=new HTMLInfoSupplier(page.getUrlStr(), page.getContent(), page.getCharSet());
    
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
				Date date=JavaDateHelper.getDate(dateStr.trim(),JavaDateHelper.SQLSERVER_SM_PATTERN);
				review.setCrDate(date);
			}
		}
		
		String contextStr=thisSupp.getBlockByOneProp("td", "style","word-break:break-all");
		if (contextStr!=null)
			review.setContent(HTMLContentHelper.getPureText(contextStr));
		
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

    	if (review.getContent()!=null&&review.getCrDate()!=null&&!reviews.contains(review))
    		reviews.add(review);
	}		
    	}
	}

	private void b0analyse() {
		div1=getter.getBlockByOneProp("div", "class", "info-book-left");
	      analyseKeyword();
		
	      b1FindParents();
	      analyseOthers();
	      
		if (info.getItemActionType()==OriginalPage.DATA_UPDATEPS)
			b1FindPsAndStock();
		
		if (info.getItemActionType()==OriginalPage.DATA_UPDATESUMM)
			b1FindSummary();
		
	    if (info.getItemActionType()==OriginalPage.DATA_FINDREVIEWS){
	    	b1FindReviews();
	    }
		
	    if (info.getItemActionType()==OriginalPage.DATA_FINDALLREVIEWS){
	    	b1AllFindAllReviews();
	    }		
	    if (info.getItemActionType()==OriginalPage.DATA_FINDPICTURE){
	    	b1FindPicture();
	    }
	    
	    if (info.getItemActionType()==OriginalPage.DATA_UPDATEALL){
	    	b1FindAll();
	    }
	
	}

	private void b1FindParents(){
	
	   String content=getter.getBlockByTagNameAndKeys("div", new String[]{"您的位置：","iframe"});
	   if (content!=null){
		   HTMLInfoSupplier supp=new HTMLInfoSupplier(itemUrl,content);
		   Element e=supp.getElementByKey("iframe", "computers");
		   String src=e.getAttributes().getValue("src");
		   
		   if (src!=null){
			   String href=URLStrHelper.legalUrl(itemUrl,src);
			   HttpPageGetter gg=new HttpPageGetter();
			   OriHttpPage page=gg.getOriHttpPage(new PageRef(href));
			   List<PageRef> parent=HTMLContentHelper.getSiteListRefsInBlock(href,page.getStringContent());
		   
		   List<PageRef> ps=new ArrayList();
		   for (Iterator it=parent.iterator();it.hasNext();){
			   PageRef p=(PageRef)it.next();
			   if (!item.getParents().contains(p))
				   ps.add(p);
		   }
		   
		   PageRef[] refs=(PageRef[])ps.toArray(new PageRef[ps.size()]);
		   for (int i=refs.length-1;i>=0;i--){
			   if (i==refs.length-1)
				   item.setParentUrl(refs[i].getUrlStr());
			   if (i-1>=0)
				   refs[i].setParentUrl(refs[i-1].getUrlStr());
			   item.getParents().add(refs[i]);
		   }
	   }
	   }
	}
}
