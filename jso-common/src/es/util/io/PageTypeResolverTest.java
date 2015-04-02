/*
 * Created on 2005-9-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util.io;

import junit.framework.TestCase;
import easyshop.model.ModelConstants;

/**
 * @author Allenhuang
 *
 * created on 2005-9-29
 */
public class PageTypeResolverTest extends TestCase {

    public void testGetType() {
        String url="http://www.fdafdaf.jpg";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_JPG);
        assertTrue(PageTypeResolver.isStaticFile(url));
        url="http://www.fdafdaf.gif";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_GIF);
        assertTrue(PageTypeResolver.isStaticFile(url));
        url="http://www.fdafdaf.htm";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_HTML);
        url="http://www.fdafdaf.html";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_HTML);
        url="http://www.fdafdaf.asp";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_ASP);
        url="http://www.fdafdaf.asp?abc=w";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_ASP);
        url="http://www.fdafdaf.xml";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_XML);
        url="http://www.fdafdaf.xml";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_XML);
        url="http://www.fdafdaf.com/";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_DIRECTORY);
        url="http://www.fdafdaf.org";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_DIRECTORY);
        url="http://www.fdafdaf.dll?w=a";
        assertTrue(PageTypeResolver.getType(url)==ModelConstants.PAGE_TYPE_DLL);
   }

}
