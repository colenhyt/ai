package es.bsites;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;

import easyshop.log.LogPropertiesHelper;
import easyshop.model.ProductItem;
import es.Constants;

public class SearchResultInfoFinderTest extends TestCase {
	SearchResultInfoFinder test;
	static{
        PropertyConfigurator.configure(LogPropertiesHelper
                .getConfigProperties(Constants.LOG_FILE));		
	}
	public void testOutResults() {
			String url;
			url="http://search.dangdang.com/search.aspx?key=aa";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://www.amazon.cn/search/search.asp?source=allenhyt&searchType=1&searchWord=管理&searchKind=name";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://www.china-pub.com/search/search_result.asp?shuming=管理";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://search.jingqi.com/search.aspx?q=%e7%ae%a1%e7%90%86";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://search.wl.cn/search.aspx?q=aa&index=1";
			test=new SearchResultInfoFinder(url);
			System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://search.99read.com/index.aspx?book_search=book&main_str=管理";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://www.bookschina.com/book_find/goodsfind.aspx?adservice=353141&book=管理&Str_Search=bookname";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://www.dearbook.com/Book/SearchBook.aspx?keyword=管理";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://www.sinoshu.com/cx/search.asp?dl_userid=easybikee&catalog=mc&keyword=管理";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://www.wfjsd.com/research.asp?searchkey=管理&selectname=bookname";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://www.golden-book.com/search/Search.asp?key1=管理";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
	//		
			url="http://search.2688.com/search.aspx?q=管理&mt=BOOK&fd=1&tp=";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
					
			url="http://bookcity.dayoo.com/index.php?typeHandler=book&module=product&app=product&action=search&products_name=%B9%DC%C0%ED&book_author_name=&book_publish_name=&products_model=&orderType=id_desc&Submit.x=0&Submit.y=0";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
			
			url="http://www.chaoyishudian.com/research.asp?action=1&searchkey=管理";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
	
			url="http://www.buildbook.com.cn/search/search-result.asp?kf=bookname&kw=管理";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
	
			url="http://book.jqcq.com/search.php?q=管理";
	//		test=new SearchResultInfoFinder(url);
	//		System.out.println("找到搜索结果"+test.outResults().getCount());
		}

}
