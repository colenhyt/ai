package cl.bizinfo.helper;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;


/*
 ** JSO1.0, by Allen Huang,2007-5-23
 */
public class StandardInfoGetter {
	static Logger log = Logger.getLogger("StandardInfoGetter.java");
	
	private String context;
	private String pureText;
	public StandardInfoGetter(byte[] content,String charset){
		try {
			context=new String(content,charset);
		} catch (UnsupportedEncodingException e) {
			// log error here
			log.error(e.getMessage());
		}
	}
	
	
	
}

