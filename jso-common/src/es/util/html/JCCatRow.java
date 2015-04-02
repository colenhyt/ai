package es.util.html;
import java.util.List;

import org.apache.log4j.Logger;



/*
 ** JSO1.0, by Allen Huang,2007-7-16
 */
public class JCCatRow {
	static Logger log = Logger.getLogger("HTMLTR.java");
	final String context;
	String[] texts;
	
	public JCCatRow(String context,String tagName){
		this.context=context;
		HTMLContentHelper neko=new HTMLContentHelper();
		List<String> list=neko.getChildrenTexts(context, tagName);
		texts=list.toArray(new String[list.size()]);
	}
	
	public JCCatRow(String context){
		this(context,"td");
	}
	
	public String getColText(int index){
		if (index>=0&&index<texts.length)
			return texts[index];
		else
			return null;
	}
	
	public int getColCount(){
		return texts.length;
	}
	
	public String getContext() {
		return context;
	}
	
	public String toString(){
		return context+":"+texts.length;
	}
}

