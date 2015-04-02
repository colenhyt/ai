package es.bsites;

import es.util.lang.CharTools;

public class GetSiteSearchUrl {
	static CharTools tool=new CharTools();

	public static String getNormal(String siteStr,int type,String word){
			String word2=tool.Utf8URLencode(word);
			String url=null;
	//		dangdang open:
			if(siteStr.equals("dangdang")){
			String param,key;
			if (type==1){
			param="selbook=1";
			key="key1";
			}
			else if (type==2){
			param="selbook=2";
			key="key2";
			}
			else if (type==3){
			param="selbook=3";
			key="key3";
			}
			else if (type==4){
			key="key";
			}
			url="http://search.dangdang.com/search.aspx?key="+word2;
			
			}
	
	//		joyo open:
			else if(siteStr.equals("joyo")){
			String key=null;
			if (type==1)
			key="name";
			else if (type==2)
			key="author";
			else if (type==3)
			key="pubcomp";
			else if (type==4)
			key="content";
			url="http://www.joyo.com/search/search.asp?searchType=1&searchWord="+word2+"&searchKind="+key;
			}
	
	//		china-pub open:
			else if(siteStr.equals("chinapub")){
			String key=null;
			if (type==1)
			url="http://www.china-pub.com/search/search_result.asp?shuming="+word2;
			else if (type==2)
			url="http://www.china-pub.com/search/search_result.asp?zuozhe="+word2;
			else if (type==3)
			url="http://www.china-pub.com/search/search_result.asp?pub="+word2;
			else if (type==4)
			url="http://www.china-pub.com/search/power_search/power_search.asp?key1="+word2;
			}
	
	//		welan open:
			else if(siteStr.equals("welan")){
			String key=null;
			if (type==1)
			key="1";
			else if (type==2)
			key="2";
			else if (type==3)
			key="3";
			else if (type==4)
			key="0";
			url="http://www.welan.com/Search/Search.aspx?index="+key+"&q="+word2;
			}
	
	//		jingqi open:
			else if(siteStr.equals("jingqi")){
			String key=null;
			if (type==1)
			key="1";
			else if (type==2)
			key="3";
			else if (type==3)
			key="2";
			url="http://www.jingqi.com/search.aspx?bookname="+word2+"&type="+key;
			}
	
	//		99 open:
			else if(siteStr.equals("b99")){
			url="http://search.99read.com/index.aspx?book_search=book&main_str="+word2;
			}
	
	//		bookschina open:
			else if(siteStr.equals("bookschina")){
			String key=null;
			if (type==1)
			key="bookname";
			else if (type==2)
			key="author";
			else if (type==3)
			key="publish";
			else if (type==4)
			key="isbn";
			url="http://www.bookschina.com/book_find/goodsfind.aspx?book="+word2+"&Str_Search="+key;
			}
	
	//		dearbook open:
			else if(siteStr.equals("dearbook")){
			url="http://www.dearbook.com/Book/SearchBook.aspx?keyword="+word2;;
			}
	
	//		jqcq open:
			else if(siteStr.equals("jqcq")){
			url="http://book.jqcq.com/search.php?q="+word2;
			}
	
	//		sinoshu open:
			else if(siteStr.equals("sinoshu")){
			String key=null;
			if (type==1)
			key="mc";
			else if (type==2)
			key="author";
			else if (type==3)
			key="press";
			else if (type==4)
			key="isbn";
			url="http://www.sinoshu.com/cx/search.asp?catalog="+key+"&keyword="+word2;
			}
	
	//		wfj open:
			else if(siteStr.equals("wfj")){
			String key=null;
			if (type==1)
			key="bookname";
			else if (type==2)
			key="author";
			else if (type==3)
			key="publisher";
			else if (type==4)
			key="isbn";
			url="http://www.wfjsd.com/research.asp?searchkey="+word2+"&selectname="+key;
			}
	
	//		gb open:
			else if(siteStr.equals("gb")){
			url="http://www.golden-book.com/search/Search.asp?key1="+word2;
			}
	
	//		2688 open:
			else if(siteStr.equals("2688")){
			url="http://search.2688.com/search.aspx?q="+word2+"&mt=BOOK&fd=1&tp=";
			}
	
	//		dy open:
			else if(siteStr.equals("dy")){
			String key=null;
			if (type==1)
			key="0";
			else if (type==2)
			key="2";
			else if (type==3)
			key="1";
			else if (type==4)
			key="3";
			url="http://bookcity.dayoo.com/index.php?typeHandler=&module=product&app=product&action=search&type="+key+"&text="+word2;
			}
	
	
	//		chaoyi open:
			else if(siteStr.equals("chaoyi")){
			String key=null;
			if (type==1)
			key="1";
			else if (type==2)
			key="2";
			else if (type==3)
			key="3";
			else if (type==4)
			key="4";
			url="http://www.chaoyishudian.com/research.asp?action="+key+"&searchkey="+word2;
			}
	
	
	//		buildbook open:
			else if(siteStr.equals("buildbook")){
			String key=null;
			if (type==1)
			key="bookname";
			else if (type==2)
			key="writer";
			else if (type==3)
			key="press";
			else if (type==4)
			key="isbn";
			url="http://www.buildbook.com.cn/search/search-result.asp?kf="+key+"&kw="+word2;
			}
			return url;
	
			
		}

	public static String get(String siteStr,int type,String word){
			String word2=tool.Utf8URLencode(word);
			String url=null;
	//		dangdang open:
			if(siteStr.equals("dd")){
			String param,key;
			if (type==1){
			param="selbook=1";
			key="key1";
			}
			else if (type==2){
			param="selbook=2";
			key="key2";
			}
			else if (type==3){
			param="selbook=3";
			key="key3";
			}
			else if (type==4){
			key="key";
			}
			url="http://www.dangdang.com/league/leagueref.asp?from=P-242112&backurl=http://search.dangdang.com/search.aspx?key="+word2;
			
			}
	
	//		joyo open:
			else if(siteStr.equals("joyo")){
			String key=null;
			if (type==1)
			key="name";
			else if (type==2)
			key="author";
			else if (type==3)
			key="pubcomp";
			else if (type==4)
			key="content";
			url="http://www.joyo.com/search/search.asp?source=allenhyt&searchType=1&searchWord="+word2+"&searchKind="+key;
			}
	
	//		china-pub open:
			else if(siteStr.equals("cp")){
			String key=null;
			if (type==1)
			url="http://www.china-pub.com/search/search_result.asp?shuming="+word2;
			else if (type==2)
			url="http://www.china-pub.com/search/search_result.asp?zuozhe="+word2;
			else if (type==3)
			url="http://www.china-pub.com/search/search_result.asp?pub="+word2;
			else if (type==4)
			url="http://www.china-pub.com/search/power_search/power_search.asp?key1="+word2;
			}
	
	//		welan open:
			else if(siteStr.equals("welan")){
			String key=null;
			if (type==1)
			key="1";
			else if (type==2)
			key="2";
			else if (type==3)
			key="3";
			else if (type==4)
			key="0";
			url="http://www.welan.com/Search/Search.aspx?id=12471&index="+key+"&q="+word2;
			}
	
	//		jingqi open:
			else if(siteStr.equals("jingqi")){
			String key=null;
			if (type==1)
			key="1";
			else if (type==2)
			key="3";
			else if (type==3)
			key="2";
			url="http://www.jingqi.com/search.aspx?bookname="+word2+"&type="+key;
			}
	
	//		99 open:
			else if(siteStr.equals("b99")){
			url="http://search.99read.com/index.aspx?book_search=book&main_str="+word2;
			}
	
	//		bookschina open:
			else if(siteStr.equals("bookschina")){
			String key=null;
			if (type==1)
			key="bookname";
			else if (type==2)
			key="author";
			else if (type==3)
			key="publish";
			else if (type==4)
			key="isbn";
			url="http://www.bookschina.com/book_find/goodsfind.aspx?adservice=353141&book="+word2+"&Str_Search="+key;
			}
	
	//		dearbook open:
			else if(siteStr.equals("dearbook")){
			url="http://www.dearbook.com/Book/SearchBook.aspx?keyword="+word2;;
			}
	
	//		jqcq open:
			else if(siteStr.equals("jqcq")){
			url="http://book.jqcq.com/search.php?q="+word2;
			}
	
	//		sinoshu open:
			else if(siteStr.equals("sinoshu")){
			String key=null;
			if (type==1)
			key="mc";
			else if (type==2)
			key="author";
			else if (type==3)
			key="press";
			else if (type==4)
			key="isbn";
			url="http://www.sinoshu.com/cx/search.asp?dl_userid=easybikee&catalog="+key+"&keyword="+word2;
			}
	
	//		wfj open:
			else if(siteStr.equals("wfj")){
			String key=null;
			if (type==1)
			key="bookname";
			else if (type==2)
			key="author";
			else if (type==3)
			key="publisher";
			else if (type==4)
			key="isbn";
			url="http://www.wfjsd.com/research.asp?searchkey="+word2+"&selectname="+key;
			}
	
	//		gb open:
			else if(siteStr.equals("gb")){
			url="http://www.golden-book.com/search/Search.asp?key1="+word2;
			}
	
	//		2688 open:
			else if(siteStr.equals("2688")){
			url="http://search.2688.com/search.aspx?q="+word2+"&mt=BOOK&fd=1&tp=";
			}
	
	//		dy open:
			else if(siteStr.equals("dy")){
			String key=null;
			if (type==1)
			key="0";
			else if (type==2)
			key="2";
			else if (type==3)
			key="1";
			else if (type==4)
			key="3";
			url="http://bookcity.dayoo.com/index.php?typeHandler=&module=product&app=product&action=search&type="+key+"&text="+word2;
			}
	
	
	//		chaoyi open:
			else if(siteStr.equals("chaoyi")){
			String key=null;
			if (type==1)
			key="1";
			else if (type==2)
			key="2";
			else if (type==3)
			key="3";
			else if (type==4)
			key="4";
			url="http://www.chaoyishudian.com/research.asp?action="+key+"&searchkey="+word2;
			}
	
	
	//		buildbook open:
			else if(siteStr.equals("buildbook")){
			String key=null;
			if (type==1)
			key="bookname";
			else if (type==2)
			key="writer";
			else if (type==3)
			key="press";
			else if (type==4)
			key="isbn";
			url="http://www.buildbook.com.cn/search/search-result.asp?kf="+key+"&kw="+word2;
			}
			return url;
	
			
		}

}
