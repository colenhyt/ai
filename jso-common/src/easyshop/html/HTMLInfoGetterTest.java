package easyshop.html;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import cl.site.util.SiteStrConverter;
import easyshop.common.dao.DbConnectionManager;
import easyshop.downloadhelper.HttpPageGetter;
import easyshop.downloadhelper.OriHttpPage;
import easyshop.html.jericho.Element;
import es.Constants;
import es.util.io.FileContentHelper;
import es.util.url.URLStrFormattor;
import es.webref.model.PageRef;

public class HTMLInfoGetterTest extends TestCase {
	HttpPageGetter getter=new HttpPageGetter();
	
	public void testTables(){
		PageRef ref=new PageRef("http://www.hxwjw.net/");
		OriHttpPage page=getter.getOriHttpPage(ref);		
		HTMLInfoSupplier test=new HTMLInfoSupplier(page.getUrlStr(),page.getStringContent());
		Collection<ArrayList<Element>> aa=test.mGetTableEsByStruct();
//		for (Iterator it=aa.iterator();it.hasNext();){
//			ArrayList e2=(ArrayList)it.next();
//			System.err.println("--------------------");
//			for (Iterator it2=e2.iterator();it2.hasNext();){
//				Element e=(Element)it2.next();
//				System.out.println(e.getContentText());
//				System.out.println("----------------------");
//			}
//			System.err.println("--------------end----------------------");
//		}
	
		
	}
	public void testCatalogDom(){
        File file=new File("D:/cvsjarso/sites/baidu/s.htm");
        String urlstr="http://www.china-pub.com/law/";
        byte[] content=FileContentHelper.getContent(file);
        HTMLInfoSupplier test=new HTMLInfoSupplier(urlstr,content,"gb2312");
        test.getMaxCountBlock("table",20,-1);
	}
	public void testPerformance2(){
        File file=new File("D:/SHOPPING/dd/catalog.htm");
        String urlstr="http://www.china-pub.com/law/";
        byte[] content=FileContentHelper.getContent(file);
        
		HTMLInfoSupplier test=new HTMLInfoSupplier(urlstr,content,Constants.CHARTSET_DEFAULT);
		long start=System.currentTimeMillis();
		for (int i=0;i<10;i++){//563ms
			String tags=test.getBlockByOnePropAndKey("div","id","divNaviBottom","到第");
		}
		System.out.println("cost time is :"+(System.currentTimeMillis()-start));
		
	}
	
	public void testMaxBranch(){
		PageRef ref=new PageRef("http://www.hardwarenet.net/seller/index.html",null);
		OriHttpPage page=getter.getOriHttpPage(ref);		
		page.setSiteId(SiteStrConverter.getSiteId(page.getUrlStr()));
		HTMLInfoSupplier test=new HTMLInfoSupplier(page.getUrlStr(),page.getStringContent());
		Collection ess=test.bGetMaxRefsByUrlBranch();
		for (Iterator it=ess.iterator();it.hasNext();){
				PageRef e2=(PageRef)it.next();
				System.out.println(e2.getUrlStr()+e2.getRefWord());
		}
		
	}
	
	public void testUrlBranch(){
		PageRef ref=new PageRef("http://www.hxwjw.net/",null);
		OriHttpPage page=getter.getOriHttpPage(ref);		
		page.setSiteId(SiteStrConverter.getSiteId(page.getUrlStr()));
		HTMLInfoSupplier test=new HTMLInfoSupplier(page.getUrlStr(),page.getStringContent());
		Collection ess=test.mGetRefsByUrlBranch();
		for (Iterator it=ess.iterator();it.hasNext();){
				Collection cc=(Collection)it.next();
				System.out.println("begin-----------");
				for (Iterator it2=cc.iterator();it2.hasNext();){
						PageRef e2=(PageRef)it2.next();
						System.out.println(e2.getUrlStr()+e2.getRefWord());
				}
				System.out.println("end-----------------");
		}
		
	}
	
	public void testToAllString(){
		PageRef ref=new PageRef("http://www.cntool.net/searchgs.asp");
		OriHttpPage page=getter.getOriHttpPage(ref);		
		HTMLInfoSupplier test=new HTMLInfoSupplier(page.getUrlStr(),page.getStringContent());
		Map map=test.mIncludeEsByAllStr("table");
		Set keySet=map.keySet();
		for (Iterator kit=keySet.iterator();kit.hasNext();){
			String key=(String)kit.next();
			System.err.println("start: "+key+"----------------------");
			
			Set e2=(Set)map.get(key);
			for (Iterator it2=e2.iterator();it2.hasNext();){
				Element e=(Element)it2.next();
				System.out.println("---"+e.toString());
//				System.out.println("--------------------------------------------------------");
			}
			System.err.println("--------------end---------------------------------");
		}
	}
	private Set<PageRef> getRefs(String sql){
	    Set<PageRef> urls = new HashSet<PageRef>();
	    PreparedStatement pstmt = null;
	    Connection con = DbConnectionManager.getConnection();
	    ResultSet rs = null;
	    try {
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            PageRef ref = new PageRef(URLStrFormattor.decode(rs.getString(1)));
	            ref.setUrlKey(rs.getString(2));
	            urls.add(ref);
	        }
	        return urls;
	    } catch (SQLException sqlex) {
	        sqlex.printStackTrace();
	    } finally {
	        try {
	            rs.close();
	            rs = null;
	            pstmt.close();
	            pstmt = null;
	            con.close();
	            con = null;
	        } catch (Exception e) {
	            System.out.print(e.getMessage());
	        }
	    }
	    return urls;		
	}
	public void testWuJinMaxBranch(){
		Set<PageRef> refs=getRefs("select urlstr,urlkey from b2burls where status=-1");
		for (Iterator it33=refs.iterator();it33.hasNext();){
		
		PageRef ref=(PageRef)it33.next();
		OriHttpPage page=getter.getOriHttpPage(ref);		
		if (page.getContent()!=null){
		System.out.println("--------------"+ref.getUrlStr()+"----------------------");
		page.setSiteId(SiteStrConverter.getSiteId(page.getUrlStr()));
		HTMLInfoSupplier test=new HTMLInfoSupplier(page.getUrlStr(),page.getStringContent());
		Collection ess=test.bGetMaxRefsByUrlBranch();
		for (Iterator it=ess.iterator();it.hasNext();){
				PageRef e2=(PageRef)it.next();
				System.out.println(e2.getUrlStr()+e2.getRefWord());
		}
		System.out.println("--------------end----------------------");
		
		}}
		
	}	
	
	public void testSortedBranch(){
		PageRef ref=new PageRef("http://www.hardwarenet.net/seller/index.html",null);
		OriHttpPage page=getter.getOriHttpPage(ref);		
		page.setSiteId(SiteStrConverter.getSiteId(page.getUrlStr()));
		HTMLInfoSupplier test=new HTMLInfoSupplier(page.getUrlStr(),page.getStringContent());
		Collection ess=test.mGetRefsByUrlBranch();
		for (Iterator it=ess.iterator();it.hasNext();){
			Set<PageRef> e=(Set<PageRef>)it.next();
			System.out.println("------------");
			for (Iterator it2=e.iterator();it2.hasNext();){
				PageRef e2=(PageRef)it2.next();
				System.out.println(e2.getUrlStr()+e2.getRefWord());
			}
			System.out.println("---end---------");
		}
		
	}	
	public void testSortedEsByLinkCount(){
		PageRef ref=new PageRef("http://www.hxwjw.net/",null);
		OriHttpPage page=getter.getOriHttpPage(ref);		
		page.setSiteId(SiteStrConverter.getSiteId(page.getUrlStr()));
		HTMLInfoSupplier test=new HTMLInfoSupplier(page.getUrlStr(),page.getStringContent());
		List<Element> ess=test.bGetAllSortedEsByLinkCount();
		for (Iterator it=ess.iterator();it.hasNext();){
			Element e=(Element)it.next();
			System.out.println("------------"+e.getName());
			List links=e.findAllElements("a");
			for (Iterator it2=links.iterator();it2.hasNext();){
				Element e2=(Element)it2.next();
				System.out.println(e2.getContentText());
			}
			System.out.println("---end---------"+e.getName());
		}
		
	}
	public void testGetUrls(){
        File file=new File("D:/SHOPPING/wl/catalog.htm");
        String urlstr="http://www.china-pub.com/law/";
        byte[] content=FileContentHelper.getContent(file);
        
		HTMLInfoSupplier test=new HTMLInfoSupplier(urlstr,content,"GBK");
		PageRef[] refs=test.getUrls("class", "buttom_txt");
		assertTrue(refs.length>0);
		
	}
	public void testPerformance1(){
        File file=new File("D:/SHOPPING/dd/catalog.htm");
        String urlstr="http://www.china-pub.com/law/";
        byte[] content=FileContentHelper.getContent(file);
        
		HTMLInfoSupplier test=new HTMLInfoSupplier(urlstr,content,"GBK");
		long start=System.currentTimeMillis();
		for (int i=0;i<10;i++){//563ms
			String[] tags=test.getBlocksByOneProp("div", "class", "left-div");
		}
		System.out.println("cost time is :"+(System.currentTimeMillis()-start));
		
	}
	public void testFindTags(){
        File file=new File("D:/ajecvs/Workspace/huangyingtian/SearchEngine/bookitems/cp/branch.htm");
        String urlstr="http://www.china-pub.com/law/";
        byte[] content=FileContentHelper.getContent(file);
        
		HTMLInfoSupplier test=new HTMLInfoSupplier(urlstr,content,"GBK");
		String[] tags=test.getBlocksByOneProp("div", "class", "left-div");
		assertTrue(tags.length>0);
	}

}
