package cl.site.util;

import junit.framework.TestCase;

public class SiteStrConverterTest extends TestCase {
	public void testToSite(){
//		assertEquals("http://www.abc.com",SiteStrConverter.toSite("hwww.abc.com"));
		assertEquals("hs.abc.com",SiteStrConverter.getSiteId("https://www.abc.com"));
		assertEquals("f.abc.com",SiteStrConverter.getSiteId("ftp://www.abc.com"));
		assertEquals("h.abc.com",SiteStrConverter.getSiteId("http://www.abc.com"));
		assertEquals("fs.abc.com",SiteStrConverter.getSiteId("ftps://www.abc.com"));
		assertEquals("h.hc360.com",SiteStrConverter.getSiteId("http://list.b2b.hc360.com/companytrade/074/001.html?ProvinceID=&CityID=&SortName=&Sort=&TimeScope=&RunMode=&word=&class=%C6%F3%D2%B5%BF%E2&supermkt=&ind=&price=&zone=&model=&page=15&sortit=default&displaymode=default&seleprovince=false&selecity=false&seltimes"));
	}
	public void testToSiteKey(){
//		assertEquals("http://www.abc.com",SiteStrConverter.toSite("hwww.abc.com"));
		assertEquals("abc.com",SiteStrConverter.toSiteKey("hs.abc.com"));
		assertEquals("abc.com",SiteStrConverter.toSiteKey("f.abc.com"));
		assertEquals("abc.com",SiteStrConverter.toSiteKey("h.abc.com"));
		assertEquals("abc.com",SiteStrConverter.toSiteKey("fs.abc.com"));
		assertEquals("hc360.com",SiteStrConverter.toSiteKey("h.hc360.com"));
	}
	
}
