package box.site.parser;

import java.util.Vector;

import easyshop.html.HTMLInfoSupplier;

public class PageKeysParser {
	HTMLInfoSupplier htmlHelper = new HTMLInfoSupplier();
	private ModelParser modelParser = new ModelParser();
	private PagingTagParser pagingParser = new PagingTagParser();
	
	public PageKeysParser(int itemCount){
		
	}
	
	public void parse(String htmlContent,String baseUrl,String[] keys){
		String[] tagNames = {"div","li","p"};
		for (String tagName:tagNames){
			Vector<String> strs = this.parse_itemskey(tagName);
		}
		
		//分页url获取:
		boolean found = pagingParser.parse(htmlContent, baseUrl);
		if (found){
			
		}
	}
	public void getitems(){
		
	}
	
	public Vector<String> parse_itemskey(String tagName){
		Vector<String> keys = new Vector<String>();
		return keys;
	}
	
	public void _judgeMultikeys(){
		
	}
	
	public void judgeCat(){
		
	}
}
