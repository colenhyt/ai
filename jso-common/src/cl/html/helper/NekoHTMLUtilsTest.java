package cl.html.helper;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/*
 ** JSO1.0, by Allen Huang,2007-5-29
 */
public class NekoHTMLUtilsTest extends TestCase {
	static Logger log = Logger.getLogger("NekoHTMLUtilsTest.java");
	NekoHTMLUtils test=new NekoHTMLUtils();
	
	public void testNodes(){
		String a="<div>abc<img src='aa'></div><div>www</div>";
		DocumentFragment root=test.getFragment(a.getBytes(), "gb2312");
		System.out.println(root.getChildNodes().getLength());
		test.removeChildNodes("img", root);
		System.out.println(root.getChildNodes().getLength());
		
	}
	public void test1(){
		String a="<div>abc<div>ddd<div>ttt</div></div><div>www</div></div>";
		Node root;
			root = (Node)(test.getFragment(a.getBytes(), "utf-8"));
			NodeList list=root.getChildNodes();
	}
}

