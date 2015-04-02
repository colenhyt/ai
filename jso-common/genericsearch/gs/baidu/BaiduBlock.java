package gs.baidu;

import easyshop.downloadhelper.HttpPage;
import easyshop.downloadhelper.HttpPageGetter;
import easyshop.html.HTMLInfoSupplier;
import es.webref.model.JSPageRef;
import gs.GSBlock;


public class BaiduBlock extends GSBlock {
	private JSPageRef titleRef,screenRef;
	private JSPageRef[] refs;
	private String innerContext;
	private HttpPage screenPage,targetPage;

	public BaiduBlock(String _context,BaiduCatalogBlockGetter getter){
		super(_context,getter);
		a1Analyse();
	}

	private void a1Analyse(){
		HTMLInfoSupplier getter=new HTMLInfoSupplier(blockGetter.getUrlStr(),context);	
		
		//find refs:
		refs=getter.getUrls().toArray(new JSPageRef[getter.getUrls().size()]);
		
		//find titleRef and screenRef:
		if (refs.length>1){
			titleRef=refs[0];
			screenRef=refs[1];
		}else if(refs.length==1){
			titleRef=refs[0];			
		}
		
		//find innerText:
		if (titleRef!=null){
			String endKey="<font color=#008000";
			String title=titleRef.getRefWord();
			int endIndex=context.indexOf(endKey);
			if (endIndex>title.length());
				innerContext=context.substring(title.length(),endIndex);
		}
	}
	
	@Override
	public String outInnerContext() {
		// TODO Auto-generated method stub
		return innerContext;
	}

	public JSPageRef[] outRefs() {
		// TODO Auto-generated method stub
		return refs;
	}

	@Override
	public JSPageRef outScreenRef() {
		// TODO Auto-generated method stub
		return screenRef;
	}

	@Override
	public JSPageRef outTitleRef() {
		// TODO Auto-generated method stub
		return titleRef;
	}

	@Override
	public HttpPage outScreenPage() {
		// TODO Auto-generated method stub
		if (screenPage==null&&screenRef!=null){
			HttpPageGetter getter=new HttpPageGetter();
			screenPage=getter.getOriHttpPage(screenRef);
		}
		return screenPage;
	}	

	@Override
	public HttpPage outTargetPage() {
		// TODO Auto-generated method stub
		if (targetPage==null&&titleRef!=null){
			HttpPageGetter getter=new HttpPageGetter();
			targetPage=getter.getOriHttpPage(titleRef);
		}
		return targetPage;
	}		
	
}
