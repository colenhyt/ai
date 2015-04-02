/*
 * 创建日期 2006-8-27
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package cl.html.helper;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.log4j.Logger;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLDocument;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.Constants;
import es.util.string.StringHelper;


/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-27
 */
public class NekoHTMLDelegate {
    private DOMFragmentParser parser = new DOMFragmentParser();
    static Logger log = Logger.getLogger(NekoHTMLDelegate.class
            .getName());
    DocumentFragment fragment;
	
    public NekoHTMLDelegate(){
    	
    }
    public NekoHTMLDelegate(String context){
	    InputSource input;
		try {
			input = new InputSource(new ByteArrayInputStream(context.getBytes(Constants.CHARTSET_DEFAULT)));
		
	    input.setEncoding(Constants.CHARTSET_DEFAULT);
	    HTMLDocument document = new HTMLDocumentImpl();
	    fragment = document.createDocumentFragment();
			try {
				parser.parse(input, fragment);
			} catch (SAXException e) {
				// log error here
				log.error(e.getMessage());
			} catch (IOException e) {
				// log error here
				log.error(e.getMessage());
			}   
		} catch (UnsupportedEncodingException e1) {
			// log error here
			log.error(e1.getMessage());
		}			
    }
    
    public NekoHTMLDelegate(byte[] content,String charSet){
	    InputSource input=new InputSource(new ByteArrayInputStream(content));
	    input.setEncoding(charSet);
	    HTMLDocument document = new HTMLDocumentImpl();
	    fragment = document.createDocumentFragment();
			try {
				parser.parse(input, fragment);
			} catch (SAXException e) {
				// log error here
				log.error(e.getMessage());
			} catch (IOException e) {
				// log error here
				log.error(e.getMessage());
			}    	
    }
//    private String parseTableStruct(Node node){
//    	
//    }
    
    public String findTableSruct(){
    	StringBuffer struct=new StringBuffer();
    	parseTableStructs(fragment,struct);
    	System.err.println("-----------"+struct.toString()+"'''''''''");
    	return struct.toString();
    }
    
    public static String[] tableEs={"TABLE","TR","TBODY","TD"};//只记录table元素
	private void parseTableStructs(Node node,StringBuffer tableStruct){
		System.err.println(node.getNodeName());
//		if (node.getNodeName().startsWith("#"))
//			System.out.println("ddd");
//		if (node.getNodeName()!=null&&!node.getNodeName().startsWith("#"))
		if (StringHelper.contains1atLeast(node.getNodeName(),tableEs))
			tableStruct.append(node.getNodeName()+";");
//		if (node instanceof TextImpl){
//			TextImpl tNode=(TextImpl)node;
//			if (tNode.getNodeValue()!=null){
//				String v=tNode.getNodeValue().trim().replaceAll("\n", "");
//				v=v.replaceAll(" ", "");
//				if (v.length()>0){
////					System.out.println("a"+v);
//					textNodes.add(v.trim());
//				}
//			}
//		}		
		
		NodeList list=node.getChildNodes();
	    for (int i=0;i<list.getLength();i++) {
	    	Node n=list.item(i);
	    	parseTableStructs(n,tableStruct);
	    }	
	}  
	
	public String toAllString(){
		StringBuffer buffer=new StringBuffer();
		toString(fragment.getFirstChild(),buffer);
		return buffer.toString();
	}
//	返回标签所有属性(包括属性值),所有子标签(包括子标签的属性和属性值),还包括文本标签:
	private void toString(Node node,StringBuffer buffer){
		if (node.getNodeName()!=null){
			if (node.getNodeName().equalsIgnoreCase("#text")){
				if (node.getNodeValue()!=null&&node.getNodeValue().trim().length()>0)
						buffer.append(node.getNodeName()+node.getNodeValue().trim());
			}else{
				buffer.append(node.getNodeName());
				if (node.getNodeValue()!=null)
					buffer.append(node.getNodeValue().trim());
			}

		if (node.getAttributes()!=null){
			NamedNodeMap map=node.getAttributes();
			int len=map.getLength();
			for (int i=0;i<len;i++){
				Node attri=map.item(i);
				if (attri.getNodeName()!=null)
					buffer.append(attri.getNodeName());
				if (attri.getNodeValue()!=null)
					buffer.append(attri.getNodeValue().trim());
				
			}
		}
		}
	
		
		NodeList list=node.getChildNodes();
	    for (int i=0;i<list.getLength();i++) {
	    	Node n=list.item(i);
	    	toString(n,buffer);
	    }	
	}  	

}
