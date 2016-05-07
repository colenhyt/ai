package box.site.parser;

import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class HtmlParser2 {
	 public static Vector<String> getPageWords(String content)  
			  throws Exception  
			{  
			  
		 Vector<String> words = new Vector<String>(); 
			  
			  Parser parser = Parser.createParser(content, "utf-8");  
			  
			  Node node = null;  
			  NodeList nodeList = parser.extractAllNodesThatMatch(new TagNameFilter("body"));  
			  for (int i = 0; i < nodeList.size(); ++i) {  
			    node = nodeList.elementAt(i);  
			    words.add(node.toPlainTextString());  
			    System.out.println(node.toPlainTextString());
			  }  
			  
			  return words;  
			} 
}
