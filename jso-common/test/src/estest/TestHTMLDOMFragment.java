/* 
 * (C) Copyright 2002-2004, Andy Clark.  All rights reserved.
 *
 * This file is distributed under an Apache style license. Please
 * refer to the LICENSE file for specific details.
 */

package estest;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.apache.html.dom.HTMLDocumentImpl;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLDocument;
import org.xml.sax.InputSource;

import easyshop.html.jericho.Source;
import es.util.io.FileContentHelper;


/**
 * This program tests the NekoHTML parser's use of the HTML DOM
 * implementation to parse document fragments by printing the
 * class names of all the nodes in the parsed document.
 *
 * @author Andy Clark
 *
 * @version $Id: TestHTMLDOMFragment.java,v 1.1 2007/10/25 09:29:48 Administrator Exp $
 */
public class TestHTMLDOMFragment {

    //
    // MAIN
    //

    /** Main. */
    public static void main(String[] argv) throws Exception {
        File file=new File("D:\\ajecvs\\Workspace\\huangyingtian\\SearchEngine\\bookitems\\cp\\browse_result.htm");
        byte[] content=FileContentHelper.getContent(file);
        Source source=new Source(new String(content));
        InputSource input=new InputSource(new ByteArrayInputStream(content));
        input.setEncoding("gbk");
        DOMFragmentParser parser = new DOMFragmentParser();
        HTMLDocument document = new HTMLDocumentImpl();
            DocumentFragment fragment = document.createDocumentFragment();
            parser.parse(input, fragment);
            print(fragment, "");
    } // main(String[])

    //
    // Public static methods
    //

    /** Prints a node's class name. */
    public static void print(Node node, String indent) {
//        System.out.println(indent+node.getClass().getName());
        System.out.println(indent+node.getClass().getName()+node.getNodeName()+":"+node.getNodeValue());
        Node child = node.getFirstChild();
        while (child != null) {
            print(child, indent+" ");
            child = child.getNextSibling();
        }
    } // print(Node)

} // class TestHTMLDOMFragment