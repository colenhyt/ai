package cl.cat.content;

import java.util.List;
import java.util.Set;

import easyshop.downloadhelper.OriHttpPage;
import easyshop.model.Item;
import es.model.RefRelation;
import es.webref.model.PageRef;


public interface ICatalogContextAnalyser {

	public void setHttpPage(OriHttpPage page,int threads);
	
	public Set<PageRef> outChildRefs();
	
	public Set<PageRef> outAllRefs();
	
	public Set<PageRef> outPagingRefs();
	
	public Set<PageRef> outItemRefs();
	
	public Set<CatalogBlock> outBlocks();
	
	public RefRelation outOtherRef();
	
	public List<Item> outItems();
	
	public List<PageRef> outDirectory();
	
	
}
