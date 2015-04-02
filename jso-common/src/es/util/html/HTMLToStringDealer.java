package es.util.html;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import easyshop.html.jericho.Element;
import easyshop.html.jericho.Source;
import es.Constants;



/*
 ** JSO1.0, by Allen Huang,2007-6-12
 */
public class HTMLToStringDealer {
	static Logger log = Logger.getLogger("HTMLToStringDealer.java");
	Source jerio;
	private String context;
	
	public HTMLToStringDealer(String _context){
		try {
			context=_context;
			jerio=new Source(new String(context.getBytes(Constants.CHARTSET_DEFAULT),Constants.CHARTSET_DEFAULT));
		} catch (UnsupportedEncodingException e) {
			// log error here
			log.error(e.getMessage());
		}
	}
	
//	public String toAllString(){
//		
//	}
	
	private String mToString(Element element,StringBuffer buffer){
		
		return null;
	}	
}

