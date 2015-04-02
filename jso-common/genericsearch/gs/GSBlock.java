package gs;

import cl.cat.content.ACatalogBlockGetter;
import cl.cat.content.CatalogBlock;
import easyshop.downloadhelper.HttpPage;
import es.webref.model.JSPageRef;

public abstract class GSBlock extends CatalogBlock{
	
	public GSBlock(String _context,ACatalogBlockGetter _getter){
		super(_context,_getter);
	}
	
	
	public abstract String outInnerContext();
	public abstract JSPageRef outScreenRef();
	public abstract HttpPage outTargetPage();
	public abstract HttpPage outScreenPage();
}
