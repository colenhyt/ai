package gs.baidu;

import java.util.List;

import junit.framework.TestCase;
import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.HttpPageGetter;
import es.webref.model.PageRef;

public class BaiduRelatedPageTest extends TestCase {
	HttpPageGetter getter=new HttpPageGetter();
	
	public void testOutRelateWords() {
		HttpPage page=getter.getDHttpPage(new PageRef("http://d.baidu.com/rs.php?q=tracy&wd=&cl=3&tn="));
		BaiduRelatedPage pp=new BaiduRelatedPage(page.getUrlStr(),page.getContent());
		List aa=pp.outRelateWords();
	}

}
