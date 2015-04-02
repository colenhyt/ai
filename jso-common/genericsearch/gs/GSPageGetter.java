package gs;

import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.HttpPageGetter;
import es.webref.model.JSPageRef;
import gs.baidu.BaiduPage;
import gs.google.GooglePage;

public class GSPageGetter {
public static final int GS_BAIDU=0;
public static final int GS_GOOGLE=1;
public static final int GS_YAHOO=2;

public static final int S_TYPE_NORMAL=0;
private final int gs;
private final String[] queryWords;
HttpPageGetter getter=new HttpPageGetter();

	public GSPageGetter(int _gs,String[] qWords){
		gs=_gs;
		queryWords=qWords;
	}
	
	public GSPage getFirstPage(){
		return getPage(1);
	}
	
	public GSPage getPage(int pageNumber){
		switch (gs){
		case GS_BAIDU:{
			String urlstr=BaiduPage.createUrl(queryWords,pageNumber-1);
			HttpPage p=downloadPage(urlstr);
			if (p.getContent()!=null)
				return new BaiduPage(urlstr,p.getContent(),S_TYPE_NORMAL,queryWords);
			break;
		}
		case GS_GOOGLE:{
			String urlstr=GooglePage.createUrl(queryWords,pageNumber-1);
			HttpPage p=downloadPage(urlstr);
			if (p.getContent()!=null)
				return new GooglePage(urlstr,p.getContent(),S_TYPE_NORMAL,queryWords);
			break;
		}
		}
		return null;
	}
	
	private HttpPage downloadPage(String urlStr){
		return getter.getHttpPageWithDefaultHttpClient(new JSPageRef(urlStr));
	}
}
