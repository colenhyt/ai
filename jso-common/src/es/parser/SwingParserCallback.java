package es.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

public class SwingParserCallback extends ParserCallback {
	 protected String base;
	 protected boolean isLink = false;
	 protected boolean isParagraph = false;
	 protected boolean isTitle = false;
	 protected String htmlbody = new String();
	 protected String urlTitle = new String();
	 protected Vector links = new Vector();
	 protected Vector linkname = new Vector();
	 protected String paragraphText = new String();
	 protected String linkandparagraph = new String();
	 protected String encode = new String();  
	 
	 public SwingParserCallback(String baseurl){
	  base=baseurl;
	 }
	 public String getEncode() {
	  return encode;
	 }
	 public String getURLtille(){
	  return urlTitle;
	 }
	 public Vector getLinks()
	 {
	     return links;
	 }
	 public Vector getLinkName()
	 {
	    return linkname;
	 }
	 public String getParagraphText()
	 {
	   return paragraphText;
	 }
	 public String getLinknameAndParagraph()
	 {
	    return linkandparagraph;
	 }
	 public void handleComment(char[] data, int pos) {
	 }
	public void handleEndTag(HTML.Tag t, int pos) {
	  if (t == HTML.Tag.A) {
	   if (isLink) {
	    isLink = false;
	   }
	  } else if (t == HTML.Tag.P) {
	   if (isParagraph) {
	    isParagraph = false;
	   }
	  } else if (t == HTML.Tag.TITLE) {
	   isTitle = false;
	  } else if (t == HTML.Tag.AREA) {
	   isLink = false;
	  }
	 }
	 public void handleError(String errorMsg, int pos) {
	 }
	 public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
	  handleStartTag(t, a, pos);
	 }
	 public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
	  // is it some sort of a link
	  String href = "";
	  if ((t == HTML.Tag.A) && (t != HTML.Tag.BASE)) {
	   href = (String) a.getAttribute(HTML.Attribute.HREF);
	   if (href != null) {
	    try {
	     URL url = new URL(new URL(base), href);
	     links.addElement(url.toString());
	     isLink = true;
	    } catch (MalformedURLException e) {
	     //System.out.println(e.getMessage());
	    }
	   }
	  } else if (t == HTML.Tag.AREA) {
	   href = (String) a.getAttribute(HTML.Attribute.HREF);
	   if (href != null) {
	    String alt = (String) a.getAttribute(HTML.Attribute.ALT);
	    try {
	     URL url = new URL(new URL(base), href);
	     links.addElement(url.toString());
	     if (alt != null) {
	      linkname.addElement(alt);
	      linkandparagraph += alt;
	     }
	     isLink = true;
	    } catch (MalformedURLException e) {
	     //System.out.println(e.getMessage());
	    }
	   }
	  } else if (t == HTML.Tag.TITLE) {
	   isTitle = true;
	  } else if (t == HTML.Tag.P) {
	   isParagraph = true;
	  } else if (t == HTML.Tag.BASE) {
	   href = (String) a.getAttribute(HTML.Attribute.HREF);
	   if (href != null)
	    base = href;
	  }
	 }
	 public void handleText(char[] data, int pos) {
	  if (isLink) {
	   String urlname = new String(data);
	   if (urlname != null&&urlname.trim().replaceAll("[\\s]", "").length()>0) {
	    linkname.addElement(urlname.trim().replaceAll(" ", ""));
	    linkandparagraph += urlname;
	   }
	  }
	  if (isTitle) {
	   String temptitle = new String(data);
	   urlTitle = temptitle;
	  }
	  if (isParagraph) {
	   String tempParagraphText = new String(data);
	   if (paragraphText != null) {
	    paragraphText += tempParagraphText;
	    linkandparagraph += tempParagraphText;
	   }
	  }
	 }
}
