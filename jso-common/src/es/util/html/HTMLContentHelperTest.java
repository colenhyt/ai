package es.util.html;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import easyshop.html.jericho.Element;
import easyshop.html.jericho.Source;



/*
 ** JSO1.0, by Allen Huang,2007-6-9
 */
public class HTMLContentHelperTest extends TestCase {
	static Logger log = Logger.getLogger("HTMLContentHelperTest.java");
	
	public void test1(){
		String a="<table><tr> <td> ddddd</td> </tr> </table>";
		try {
			Source jerio=new Source(new String(a.getBytes(),"gb2312"));
			List list=jerio.findAllElements("table");
			for (Iterator it=list.iterator();it.hasNext();){
				Element e=(Element)it.next();
				System.out.println(e.toString());
				System.out.println(e.getContentText());
			}
		} catch (UnsupportedEncodingException e) {
			// log error here
			log.error(e.getMessage());
		}
	}
	
}

