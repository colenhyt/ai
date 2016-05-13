package es.util.html;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.w3c.dom.DocumentFragment;
import org.xml.sax.SAXException;

import cl.html.helper.NekoHTMLUtils;
import easyshop.html.jericho.Attribute;
import easyshop.html.jericho.Attributes;
import easyshop.html.jericho.Element;
import easyshop.html.jericho.Source;
import easyshop.html.jericho.StartTag;
import es.Constants;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;



/*
 ** JSO1.0, by Allen Huang,2007-5-23
 */
public class HTMLContentHelper {
	static Logger log = Logger.getLogger("HTMLContentHelper.java");
	NekoHTMLUtils neko=new NekoHTMLUtils();

	public static List<PageRef> getIFrameUrls(String context){
		Source jerio=new Source(context);
		List<Element> list=jerio.findAllElements("iframe");
		List<PageRef> refs=new ArrayList<PageRef>();
		for (Iterator it=list.iterator();it.hasNext();){
			Element e=(Element)it.next();
			Attribute src=e.getAttributes().get("src");
			if (src!=null&&src.getValue()!=null)
				refs.add(new PageRef(src.getValue()));
		}
		return refs;
	}
	
	public static Map getTableTextValues(String tableBlock,boolean pure){
		tableBlock=tableBlock.replaceAll("&nbsp;","");
		Map map=Collections.synchronizedMap(new HashMap());
			Source jerio=new Source(tableBlock);
	        List list = jerio.findAllElements("tr");
	        for (Iterator it=list.iterator();it.hasNext();){
	        	Element e=(Element)it.next();
        		List<Element> hlist=e.findAllElements("th");
	        	List<Element> dlist=e.findAllElements("td");
	        	if (hlist.size()<=0&&dlist.size()>0){//成双的出现:
	        		Element[] es=dlist.toArray(new Element[dlist.size()]);
	        		for (int i=0;i<es.length;i++){
	        			if (i%2==0&&i+1<es.length){
	        				if (pure)
	        					map.put(getPureText(es[i].getContentText()), getPureText(es[i+1].getContentText()));
	        				else
		        				map.put(getPureText(es[i].getContentText()), es[i+1].getContentText());
	        			}
	        		}
	        	}else {
	        		//th和td搭配:
	        		Element[] ths=hlist.toArray(new Element[hlist.size()]);
	        		Element[] thds=dlist.toArray(new Element[dlist.size()]);
	        		for (int i=0;i<ths.length;i++){
	        			if (ths[i].getContentText()!=null&&thds[i].getContentText()!=null){
	        				if (getPureText(ths[i].getContentText())!=null){
	        				if (pure&&getPureText(thds[i].getContentText())!=null)
	        					map.put(getPureText(ths[i].getContentText()).trim(), getPureText(thds[i].getContentText()).trim());
	        				else
		        				map.put(getPureText(ths[i].getContentText()).trim(), thds[i].getContentText().trim());
	        			}
	        			}
	        		}	        		
	        	}
	        }
	   return map;
	}
	public static String[] findImgUrls(byte[] c,String charSet){
		String[] imgs=null;
		try {
			Source jerio=new Source(new String(c,charSet));
	        List list = jerio.findAllStartTags("img");
	        imgs=new String[list.size()];
	        for (int i=0;i<list.toArray().length;i++) {
	            StartTag tag = (StartTag) (list.toArray(new StartTag[list.size()]))[i];
	            imgs[i]=tag.getAttributes().getValue("src");
	        }	
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgs;
	}

	public static String[] getTagText(String tagName,byte[] c,String charSet){
		String[] texts=null;
		try {
			texts=getTagText(tagName,new String(c,charSet));	
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texts;		
	}

	public static String[] getTagText(String tagName,String context){
		String[] texts=null;
			Source jerio=new Source(context);
	        List list = jerio.findAllElements(tagName);
	        texts=new String[list.size()];
	        for (int i=0;i<list.toArray().length;i++) {
	            Element e = (Element) (list.toArray(new Element[list.size()]))[i];
	            texts[i]=e.getContentText();
	        }	
		return texts;		
	}

	public static List<PageRef> getSiteListRefsInBlock(String base,String block){
		Source ss=new Source(block);
		String host=URLStrHelper.getHost(base);
		
	        List list = ss.findAllStartTags("a");
	        List<PageRef> urls=new ArrayList<PageRef>();
	        Set links=new HashSet();
	        for (Iterator it=list.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            String urlStr=URLStrHelper.legalUrl(base,tag.getAttributes().getValue("href"));
	            if (urlStr==null||(host!=null&&urlStr.indexOf(host)<0)) continue;
	            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
	            	urls.add(new PageRef(urlStr,refWord));	   	            
	            	
	        }
	        links.clear();
		return urls;		
	}
	
	public static Set<PageRef> getSiteRefsInBlock(String base,String block){
		Source ss=new Source(block);
		String host=URLStrHelper.getHost(base);
		
	        List list = ss.findAllStartTags("a");
	        Set<PageRef> urls=new HashSet<PageRef>();
	        Set links=new HashSet();
	        for (Iterator it=list.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            String urlStr=URLStrHelper.legalUrl(base,tag.getAttributes().getValue("href"));
	            if (urlStr==null||(host!=null&&urlStr.indexOf(host)<0)) continue;
	            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
	            	urls.add(new PageRef(urlStr,refWord));	   	            
	            	
	        }
	        links.clear();
		return urls;		
	}
	public static List<PageRef> getSiteRefs(String base,Element e){
		String host=URLStrHelper.getHost(base);
		List<PageRef> urls=new ArrayList<PageRef>();
        List list = e.findAllStartTags("a");
        for (Iterator it=list.iterator();it.hasNext();) {
        	StartTag tag = (StartTag)it.next();
        	String urlStr=URLStrHelper.legalUrl(base,tag.getAttributes().getValue("href"));
        	if (urlStr==null||(host!=null&&urlStr.indexOf(host)<0)) continue;
        	
        	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
        	urls.add(new PageRef(urlStr,refWord));	   
            	
        }
        return urls;
	}
	
	//取第一个有这个refWord的ref
	public static PageRef getPageRefByWord(List<PageRef> refs,String refWord){
		for (Iterator it=refs.iterator();it.hasNext();){
			PageRef ref=(PageRef)it.next();
			if (refWord!=null&&ref.getRefWord().equalsIgnoreCase(refWord))
				return ref;
		}
		return null;
	}
	public static Set<String> getSiteUrlsInBlock(String base,String block){
		Source ss=new Source(block);
		String host=URLStrHelper.getHost(base);
		
	        List list = ss.findAllStartTags("a");
	        Set<String> urls=new HashSet<String>();
	        for (Iterator it=list.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            String urlStr=URLStrHelper.legalUrl(base,tag.getAttributes().getValue("href"));
	            if (urlStr==null||(host!=null&&urlStr.indexOf(host)<0)) continue;
	            	urls.add(urlStr);	  
	        }
		return urls;		
	}
	
	public static List<PageRef> getSiteUrlListInBlock(String base,String block){
		Source ss=new Source(block);
		String host=URLStrHelper.getHost(base);
		
	        List list = ss.findAllStartTags("a");
	        List<PageRef> urls=new ArrayList<PageRef>();
	        for (Iterator it=list.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            String urlStr=URLStrHelper.legalUrl(base,tag.getAttributes().getValue("href"));
            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
	            if (urlStr==null||(host!=null&&urlStr.indexOf(host)<0)) continue;
	            	urls.add(new PageRef(urlStr,refWord));	  
	        }
		return urls;		
	}	
	
	public static Set<PageRef> getRefsInBlock(String urlStr,String block){
		Source ss=new Source(block);
	        List list = ss.findAllStartTags("a");
	        Set<PageRef> urls=new HashSet<PageRef>();
	        Set links=new HashSet();
	        for (Iterator it=list.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            Attributes attrs=tag.getAttributes();
	            String href=tag.getAttributes().getValue("href");
	            if (href!=null&&href.startsWith("www"))
	            	href="http://"+href;
	            String url=URLStrHelper.legalUrl(urlStr,href);
	            if (url!=null){
	            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
	            	urls.add(new PageRef(url,refWord));	   	            }

	            	
	        }
	        links.clear();
		return urls;		
	}

	public static String getPureTextWithSpace(String context){
		String pureText=null;
		if (context != null) {
			NekoHTMLUtils delegate = new NekoHTMLUtils();
			context=removeHTMLChars(context);
			try {
				pureText = delegate.getPureTextWithSpace(context.getBytes(Constants.CHARTSET_DEFAULT),Constants.CHARTSET_DEFAULT);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return pureText;
	}
	public static String getPureText(String context,String chartset){
		try {
			if (context!=null){
				context=removeHTMLChars(context);
				return getPureText(context.getBytes(chartset),chartset);
			}
		} catch (UnsupportedEncodingException e) {
			// log error here
			log.error(e.getMessage());
		} catch (Exception e) {
			// log error here
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPureText(String context){
		return getPureText(context,Constants.CHARTSET_DEFAULT);
	}
	
	public static String getPureText(byte[] content,String encoding) throws Exception{
		String pureText=null;
		if (content != null) {
			NekoHTMLUtils delegate = new NekoHTMLUtils();
			String str=new String(content,encoding);
			str=removeHTMLChars(str);
			pureText = delegate.getPureText(str.getBytes("gbk"), encoding);
		}
		return pureText;		
	}

	public List<String> getTextValues(String context){
		try {
			context=removeHTMLChars(context);
			return getTextValues(context.getBytes(Constants.CHARTSET_DEFAULT),Constants.CHARTSET_DEFAULT);
		} catch (UnsupportedEncodingException e) {
			// log error here
			log.error(e.getMessage());
			return null;
		}		
	}	

	public List<String> getTextValues(byte[] content,String charSet){
		String str;
		try {
			str=new String(content,charSet);
			str=removeHTMLChars(str);
			return neko.getTextValues(str.getBytes(charSet),charSet);
		}catch (Exception e) {
			// log error here
			log.error(e.getMessage());
		}
		return null;
	}	
	
	public DocumentFragment getFragment(byte[] content,String encoding){
		return neko.getFragment(content,encoding);	
	}
	
	public List getChildrenTexts(String context,String tagName){
		return neko.getChildren(removeHTMLChars(context),tagName);		
	}	
	
	private static String[] htmlChars={"&nbsp;","\n","\t"};
	public static String removeHTMLChars(String context){
		if (context==null)
			return null;
		
		for (int i=0;i<htmlChars.length;i++){
			context=context.replaceAll(htmlChars[i], "");
		}
		return context.trim();
	}
	public static String getNextText(String pure,String preText) {
		int s = pure.toLowerCase().indexOf(preText.toLowerCase());
		if (s > 0) {
			int i=s + preText.length();
			int j=pure.indexOf(" ",i);
			if (i>0&&j>0)
			return pure.substring(i,j).trim()
					.replace(" ", "");
	
		}
		return null;
	}

	public static String getMiddleText(String pure,String preText, String nextText) throws Exception {
		int s = pure.indexOf(preText);
		int e = pure.indexOf(nextText,s);
		if (s > 0 && e > 0 && s + preText.length() < e) {
			return pure.substring(s + preText.length(), e).trim()
					.replace(" ", "");
	
		}
		return null;
	}
}

