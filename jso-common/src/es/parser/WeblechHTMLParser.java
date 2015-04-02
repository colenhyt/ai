package es.parser;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import easyshop.download.collection.PageRefSet;
import es.webref.model.JSPageRef;


public class WeblechHTMLParser {
	public static Set<JSPageRef> findPageRefs(String context) {
		// String content=new String(bytes);
		PageRefSet refs = new PageRefSet();
			 HTMLParser parser=new HTMLParser();
	        long start=System.currentTimeMillis();
	        start=System.currentTimeMillis();
			List links = parser.parseLinksInDocument(null, context);

	        start=System.currentTimeMillis();
			for (Iterator it = links.iterator(); it.hasNext();) {
				URL url = (URL) it.next();
				JSPageRef ref = new JSPageRef(url.toExternalForm());
				refs.add(ref);
			}
			links.clear();
		return refs;
	}

}
