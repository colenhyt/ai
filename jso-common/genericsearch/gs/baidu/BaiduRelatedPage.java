package gs.baidu;

import easyshop.html.HTMLInfoSupplier;
import easyshop.html.jericho.Element;
import es.util.lang.CharTools;
import es.util.pattern.ESPattern;
import es.webref.model.PageRef;
import gs.GSBlock;
import gs.GSKeyword;
import gs.GSPage;

import java.util.ArrayList;
import java.util.List;

public class BaiduRelatedPage extends GSPage{
public static final String PREFIX="http://www.baidu.com/s?wd=";
private static final String BAIDU_ENCODING="gb2312";
private long searchCount;
private List<GSKeyword> gsWords=new ArrayList<GSKeyword>();
private HTMLInfoSupplier getter;
static CharTools tool=new CharTools();

	public BaiduRelatedPage(String urlStr,byte[] _content){
		super(urlStr,_content,BAIDU_ENCODING);
		a1Analyse();
	}

	public BaiduRelatedPage(String urlStr,byte[] _content,int _searchType,String[] qWords){
		super(urlStr,_content,BAIDU_ENCODING,_searchType,qWords);
		a1Analyse();
	}
	
	/*
	 * <ul><li class=ls>1</li><li class=kwc>
	 * <a target=_blank href=http://www.baidu.com/s?cl=3&f=1&wd=tracy+mcgrady&tn=>tracy mcgrady</a>
	 * </li>
	 * <li class=bar><img src=http://img.baidu.com/img/bar_1.gif height=6 width=148 align=absmiddle vspace=5></li>
	 * </ul>
	 */
	private void a1Analyse(){
		String urlStr=getUrlStr();
		getter = new HTMLInfoSupplier(urlStr,content, encoding);
		
		String div=getter.getBlockByOneProp("div", "id", "con");
		
		HTMLInfoSupplier spuu=new HTMLInfoSupplier(urlStr,div);
		//find blocks:
		String[] divs=spuu.getBlocksByTagNameAndKey("ul", "baidu.com/s?");
		if (divs!=null&&divs.length>0){
			for (int j=0;j<divs.length;j++){
				GSKeyword word=sFindWordInBlock(divs[j]);
				if (word!=null)
					gsWords.add(word);
			}
			
		}
	}

	private GSKeyword sFindWordInBlock(String block){
		HTMLInfoSupplier spuu=new HTMLInfoSupplier(urlStr,block);
		List<PageRef> refs=spuu.getUrlsByLinkKey("baidu.com/s?");
		GSKeyword keyword=null;
		if (refs.size()==1){
			PageRef ref=(PageRef)refs.get(0);
			keyword=new GSKeyword(ref);
			Element imgE=spuu.getElementByKey("img", "height=6");
			if (imgE!=null){
				String width=imgE.getAttributes().getValue("width");
				if (ESPattern.isNumber(width)){
					keyword.setSearchCount(new Long(width).longValue());
				}
			}
		}
		return keyword;
	}
	public static String createUrl(String[] queryWords,int page){
		String url=generatUrl(queryWords,PREFIX);
		url=url+"&pn="+(page*10);
		tool.setCharSet(BAIDU_ENCODING);
		return tool.Utf8URLencode(url);		
	}
	
	public String getUrlStr(){
		return urlStr;
	}
	
	public GSBlock[] outBlocks() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<GSKeyword> outRelateWords() {
		// TODO Auto-generated method stub
		return gsWords;
	}

	@Override
	public long outSearchCount() {
		// TODO Auto-generated method stub
		return searchCount;
	}

}
