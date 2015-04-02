/*
 * Created on 2005-9-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.parser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import easyshop.download.collection.LinkSet;
import easyshop.download.collection.PageRefSet;
import easyshop.download.collection.SiteStringConverter;
import easyshop.html.jericho.Source;
import easyshop.html.jericho.StartTag;
import es.analyser.PageReviseAnalyser;
import es.datamodel.AnalysersFactory;
import es.model.OriginalPage;
import es.util.url.URLGenerator;
import es.util.url.URLStrHelper;
import es.webref.model.JSPageRef;


/**
 * @author Allenhuang
 *
 * created on 2005-9-17
 */
public class DefaultHTMLParser {
    /**
     * @author Allenhuang
     *
     * created on 2005-9-17
     */
        public static Set findPageRefs(OriginalPage page){
//            String content=new String(bytes);
            PageRefSet refs=new PageRefSet();
	        long start=System.currentTimeMillis();
            Set links=findWebLinks(page.getUrlStr(),page.toStringContent());
	        System.out.println("parse1 time: "+(System.currentTimeMillis()-start));		
            Map analyserMap=AnalysersFactory.get().findReviseMap();
            PageReviseAnalyser analyser=(PageReviseAnalyser)analyserMap.get(page.getSpecId());
//            PageClassifyviaURLAnalyser classer=(PageClassifyviaURLAnalyser)(AnalysersFactory.get().findClassifyMap()).get(siteId);
            
	        start=System.currentTimeMillis();
            for(Iterator it=links.iterator();it.hasNext();){
            	JSPageRef link=(JSPageRef)it.next();
                
                String urlStr=link.getUrlStr();
                
                urlStr=URLGenerator.generate(page.getUrlStr(),urlStr);
                
               
                if (urlStr!=null&&urlStr.indexOf(SiteStringConverter.getSiteString(page.getSpecId()))<0)
                    continue;
                    
                String url=analyser.reviseURL(urlStr);
                JSPageRef ref=new JSPageRef(url,link.getRefWord());
                ref.setDocId(page.getUId());
                ref.setSpecId(page.getSpecId());
                refs.add(ref);
            }
            links.clear();
            return refs;
        }
        
        public static Set findWebLinks(String base,String content){
            if (content==null)
                return new HashSet();
            
            LinkSet linkSet=new LinkSet();
            Source source=new Source(content);
            {
                List list=source.findAllStartTags("a");
                for (Iterator it=list.iterator();it.hasNext();){
            		StartTag e=(StartTag)it.next();
            		String hrefValue=e.getAttributes().getValue("href");

        			if (URLStrHelper.legalUrl(base,hrefValue)!=null)continue;
        			
//            			List sgs=e.getElement().findAllStartTags();
//                		String img=null,word="";
//            			word=((StartTag)sgs.get(sgs.size()-1)).getElement().getContentText();
//                		if (sgs.size()>1){
////                			System.out.println("allen: "+sgs.get(sgs.size()));
//                			//find the link word:
//                			//find the link image:
//                			List imgs=e.getElement().findAllStartTags("img");
//                			if(imgs.size()>0){
//                			    StartTag t=(StartTag)imgs.get(0);
//                				img=t.getAttributes().getValue("src");
//                				String alt=t.getAttributes().getValue("alt");
//                				if (word==null&&alt!=null&&alt.trim().length()>0)
//                				    word=t.getAttributes().getValue("alt").trim();                				    
//                			}
//                			
//                		}
//                		
//            			if (word!=null&&word.trim().length()>300)
//            			    continue;              
 
//            			if (word!=null&&word.toLowerCase().indexOf("<font")>=0)
//            			    word=HTMLTagResolver.cutTag(word,"font");
           			           			
        			JSPageRef link=new JSPageRef(hrefValue);
//                		link.setRefWord(word==null?null:word.trim());
//                		if (img!=null)
//                		    link.setTypeId(WebLink.TYPE_IMG);

                 		linkSet.add(link);
            				
            			
            		
            	}      
                
                
            }        
            return linkSet;
        }
        

}
