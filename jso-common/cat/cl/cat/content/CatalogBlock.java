package cl.cat.content;

import es.webref.model.PageRef;

public class CatalogBlock {
	protected final String context;
	protected final ACatalogBlockGetter blockGetter;

	public CatalogBlock(String _context,ACatalogBlockGetter _getter){
		context=_context;
		blockGetter=_getter;
	}
	
	public String getContext(){
		return context;
	}
	
	public PageRef outTitleRef(){
		return blockGetter.getTitleRef();
	}
	
	public String outSImgUrlStr(){
		return blockGetter.getSImgUrlStr();
	}
	
	public String getUrlStr(){
		return blockGetter.getUrlStr();
	}
	
	public String getCharSet(){
		return blockGetter.getCharSet();
	}
	
	public String toString(){
		return context;
	}
}
