package cl.html.helper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.log4j.Logger;
import org.apache.xerces.dom.TextImpl;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLDocument;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import es.Constants;



/*
 ** JSO1.0, by Allen Huang,2007-5-29
 */
public class NekoHTMLUtils {
	static Logger log = Logger.getLogger("NekoHTMLUtils.java");
	
	private DOMFragmentParser parser = new DOMFragmentParser();

	private static void findNodeByText(Node node,List<String> textNodes){
		if (node instanceof TextImpl){
			TextImpl tNode=(TextImpl)node;
			if (tNode.getNodeValue()!=null){
				String v=tNode.getNodeValue().trim().replaceAll("\n", "");
				v=v.replaceAll(" ", "");
				if (v.length()>0){
//					System.out.println("a"+v);
					textNodes.add(v.trim());
				}
			}
		}		
		
		NodeList list=node.getChildNodes();
	    for (int i=0;i<list.getLength();i++) {
	    	Node n=list.item(i);
	    	findNodeByText(n,textNodes);
	    }	
	}

	public void removeChildNodes(String tagName,Node node){
		NodeList list=node.getChildNodes();
	    for (int i=0;i<list.getLength();i++) {
	    	Node n=list.item(i);
	    	if (n.getNodeName().toLowerCase().indexOf(tagName.toLowerCase())>=0){
	    		node.removeChild(n);
	    		continue;
	    	}	    		
	    	removeChildNodes(tagName,n);	    	
	    }			
	}
	public DocumentFragment getFragment(byte[] content,String charSet){
	    InputSource input=new InputSource(new ByteArrayInputStream(content));
	    input.setEncoding(charSet);
	    HTMLDocument document = new HTMLDocumentImpl();
	    DocumentFragment fragment = document.createDocumentFragment();
			try {
				parser.parse(input, fragment);
			} catch (SAXException e) {
				// log error here
				log.error(e.getMessage());
			} catch (IOException e) {
				// log error here
				log.error(e.getMessage());
			}
		return fragment;
		
	}

	private Node getNextTextNodeFromBrother(Node node){
		Node child=getNextTextNodeFromChild(node);
		if (child!=null)
			return child;
		Node next=node.getNextSibling();
		if (next!=null)
		return getNextTextNodeFromBrother(next);
		return null;
			
	}

	private Node getNextTextNodeFromChild(Node node){
	    Node child = node.getFirstChild();
	    while (child != null) {
	    	if (child instanceof TextImpl){
	    		TextImpl textNode=(TextImpl)child;
	    		if (textNode.getNodeValue()!=null&&textNode.getNodeValue().trim().length()>0)
	    			return child;
	    	}
	    	getNextTextNodeFromChild(child);
	        child = child.getNextSibling();
	    }	
	    return null;
	}

	public String getPureText(byte[] content,String cSet) throws SAXException, IOException{
		List texts=getTextValues(content,cSet);
		StringBuffer buffer=new StringBuffer();
		if (texts!=null){
			String[] ts=(String[])texts.toArray(new String[texts.size()]);
			for (int i=0;i<ts.length;i++){
				buffer.append(ts[i].trim());
			}
		}
		return buffer.toString();
		
	}

	public String getPureTextWithSpace(byte[] content,String cSet) throws SAXException, IOException{
		List texts=getTextValues(content,cSet);
		StringBuffer buffer=new StringBuffer();
		if (texts!=null){
			String[] ts=(String[])texts.toArray(new String[texts.size()]);
			for (int i=0;i<ts.length;i++){
				buffer.append(ts[i].trim()+" ");
			}
		}
		return buffer.toString();
		
	}
	public List<String> getChildren(String context,String childTagName){
		List<String> nodes=new ArrayList();
		try {
				DocumentFragment frag=getFragment(context.getBytes(Constants.CHARTSET_DEFAULT),Constants.CHARTSET_DEFAULT);
				
				Node node=frag.getFirstChild();
				NodeList children=node.getChildNodes();
				for (int i=0;i<children.getLength();i++){
					if (children.item(i).getNodeName().equalsIgnoreCase(childTagName))
						nodes.add(children.item(i).getTextContent());
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return nodes;		
	}
	
	public List<String> getTextValues(byte[] content,String cSet) throws SAXException, IOException{
		List<String> textNodes=new ArrayList();
	try {
			String str=new String(content,cSet);
//			int index=str.toLowerCase().indexOf("alt");
//			if (index>0){
//				if (str.indexOf(">", index)>0)
//				str=str.substring(0,index)+str.substring(str.indexOf(">", index));
//				else if (str.indexOf("/>", index)>0)
//				str=str.substring(0,index)+str.substring(str.indexOf("/>", index));
//			}
				
			DocumentFragment frag=getFragment(str.getBytes(cSet),cSet);
			findNodeByText(frag,textNodes);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return textNodes;
		
	}	
}

