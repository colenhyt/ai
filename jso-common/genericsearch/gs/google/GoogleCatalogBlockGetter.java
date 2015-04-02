package gs.google;

import java.util.List;

import cl.cat.content.ACatalogBlockGetter;
import es.webref.model.PageRef;


public class GoogleCatalogBlockGetter extends ACatalogBlockGetter{
	
	public GoogleCatalogBlockGetter(String _urlstr,byte[] content,String encoding){
		super(_urlstr,content,encoding);
	}

	@Override
	public String getSImgUrlStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageRef getTitleRef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PageRef> getAllRefs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getInnerContext(){
		return null;
	}

}
