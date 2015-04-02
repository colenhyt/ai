package cl.cat.content;

import java.util.List;

import es.webref.model.PageRef;


public abstract class ACatalogBlockGetter {
	protected final byte[] content;
	protected final String encoding;
	protected final String urlStr;
	
	public ACatalogBlockGetter(String _urlStr,byte[] _content,String _encoding){
		urlStr=_urlStr;
		content=_content;
		encoding=_encoding;
	}	
	
	public String getUrlStr(){
		return urlStr;
	}
	
	public String getCharSet(){
		return encoding;
	}
	
	public abstract PageRef getTitleRef();
	
	public abstract String getSImgUrlStr();
	
	public abstract List<PageRef> getAllRefs();
}
