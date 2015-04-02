package gs.google;

import easyshop.html.HTMLInfoSupplier;
import es.util.lang.CharTools;
import gs.GSPage;

public class GooglePage extends GSPage{
public static final String PREFIX="http://www.google.cn/search?complete=1&hl=zh-CN&q=";
private static final String GOOGLE_ENCODING="utf-8";
private long searchCount;
private GoogleBlock[] blocks;
private HTMLInfoSupplier getter;
static CharTools tool=new CharTools();

	public GooglePage(String urlStr,byte[] _content,int _searchType,String[] qWords){
		super(urlStr,_content,GOOGLE_ENCODING,_searchType,qWords);
		a1Analyse();
	}
	
	private void a1Analyse(){
		String urlStr=getUrlStr();
		getter = new HTMLInfoSupplier(urlStr,content, encoding);
		
		//find searchCount:
//		String resultCountKey="百度一下，找到相关网页约";
//		int i=context.indexOf(resultCountKey);
//		if (i>0){
//			int i2=context.indexOf("篇",i);
//			if (i2>i){
//			String countStr=context.substring(i+resultCountKey.length(),i2);
//			countStr=countStr.replaceAll(",", "");
//			if (ESPattern.matches("[\\d]*",countStr))
//				searchCount=new Long(countStr).longValue();
//			}
//		}
		
		//find blocks:
		String[] divs=getter.getBlocksByTagNameAndKey("div", "类似网页");
		if (divs!=null&&divs.length>5){
			blocks=new GoogleBlock[divs.length];
			for (int j=0;j<divs.length;j++){
				blocks[j]=new GoogleBlock(divs[j],new GoogleCatalogBlockGetter(urlStr,divs[j].getBytes(),GOOGLE_ENCODING));
			}
		}
	}

	public static String createUrl(String[] queryWords,int page){
		String url=generatUrl(queryWords,PREFIX);
		url=url+"&start="+(page*10);
		tool.setCharSet(GOOGLE_ENCODING);
		return tool.Utf8URLencode(url);		
	}
	
	public String getUrlStr(){
		return urlStr;
	}
	
	@Override
	public GoogleBlock[] outBlocks() {
		// TODO Auto-generated method stub
		return blocks;
	}

	@Override
	public long outSearchCount() {
		// TODO Auto-generated method stub
		return searchCount;
	}

}
