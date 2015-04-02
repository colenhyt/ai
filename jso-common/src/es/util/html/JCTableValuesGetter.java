package es.util.html;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import easyshop.html.jericho.Element;
import easyshop.html.jericho.Source;



/*
 ** JSO1.0, by Allen Huang,2007-7-16
 */
public class JCTableValuesGetter {
	static Logger log = Logger.getLogger("JCTableValuesGetter.java");
	private Source totalJerio;
	private int fixColCount=-1;
	List<JCCatRow> rows=new ArrayList<JCCatRow>();
	private String rowTagName="tr";
	private String colTagName="td";
	public JCTableValuesGetter(String context,int fixColCount,String rowTagName,String colTagName){
			this.rowTagName=rowTagName;
			this.colTagName=colTagName;
		
			this.totalJerio=new Source(context);
			this.fixColCount=fixColCount;
			init();
	}
	
	public JCTableValuesGetter(String context,int fixColCount){
			this(context,fixColCount,"tr","td");
	}
	
	private void init(){
		HTMLContentHelper neko=new HTMLContentHelper();
		List<Element> trs=totalJerio.findAllElements(rowTagName);
		for (Iterator it=trs.iterator();it.hasNext();){
			Element e=(Element)it.next();
			if (e.getContentText()!=null){
				if (fixColCount>0){
					List<String> tdList=neko.getChildrenTexts(e.toString(), colTagName);
					if (tdList.size()==fixColCount)
						rows.add(new JCCatRow(e.toString().replaceAll("&nbsp;", "")));
				}else
					rows.add(new JCCatRow(e.toString()));
			}
		}				
	}
	
	public List<JCCatRow> getRows(){
		return rows;
	}
	
	public JCCatRow getRow(int index){
		JCCatRow[] trs=rows.toArray(new JCCatRow[rows.size()]);
		if (index>=0&&index<trs.length)
			return trs[index];
		else
			return null;
	
	}
}

