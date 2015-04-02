package es.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.text.html.parser.DTD;
import javax.swing.text.html.parser.DocumentParser;

import easyshop.download.collection.PageRefSet;
import easyshop.download.collection.SiteStringConverter;
import es.analyser.PageReviseAnalyser;
import es.datamodel.AnalysersFactory;
import es.model.OriginalPage;
import es.webref.model.JSPageRef;


public class SwingHTMLParser {
	public static Set findPageRefs(OriginalPage page) {
		// String content=new String(bytes);
		PageRefSet refs = new PageRefSet();
		DTD dtd;
		try {
			dtd = DTD.getDTD("html");
			DocumentParser parser = new DocumentParser(dtd);
			Reader reader = new InputStreamReader(new ByteArrayInputStream(page
					.getContent()));
			SwingParserCallback htmlParser = new SwingParserCallback(page
					.getUrlStr());
	        long start=System.currentTimeMillis();
			parser.parse(reader, htmlParser, false);
//	        System.out.println("parse1 time: "+(System.currentTimeMillis()-start));		
	        start=System.currentTimeMillis();
			Vector links = htmlParser.getLinks();
			Vector names=htmlParser.getLinkName();
//	        System.out.println("parse2 time: "+(System.currentTimeMillis()-start));		

			Map analyserMap = AnalysersFactory.get().findReviseMap();
			PageReviseAnalyser analyser = (PageReviseAnalyser) analyserMap
					.get(page.getSpecId());
			// PageClassifyviaURLAnalyser
			// classer=(PageClassifyviaURLAnalyser)(AnalysersFactory.get().findClassifyMap()).get(siteId);

	        start=System.currentTimeMillis();
			for (Iterator it = links.iterator(); it.hasNext();) {
				String urlStr=(String)it.next();
				
				if (urlStr != null
						&& urlStr.indexOf(SiteStringConverter
								.getSiteString(page.getSpecId())) < 0)
					continue;

				String url = analyser.reviseURL(urlStr);
				JSPageRef ref = new JSPageRef(url);

				ref.setDocId(page.getUId());
				ref.setSpecId(page.getSpecId());

				refs.add(ref);
			}
			links.clear();
//	        System.out.println("parse3 time: "+(System.currentTimeMillis()-start));		
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return refs;
	}

}
