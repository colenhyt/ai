/*
 * Created on 2005-7-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.variety;




/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class DirectoryVarietyAnalyserImpl extends BasicVarietyAnalyser implements DirectoryVarietyAnalyser {
	
//	public Result doDirectory(CatalogPage page){
//		int var=getVariety(page);
//		if (var>=0)
//			return new Result(page.getDocID(),var,-1,page.getSiteID());
//		
//		return null;
//	}
	
//	int getVariety(CatalogPage page){
//		int variety=-1;
//		
//		//get Variety by branch vote:
//    	variety=getVarByBranchVote(page); 	
//    	
//    	//get Variety by page title word:
//		if (variety<0)
//			variety=getVarietyByTitle(page.getSourceText());		
//		
//		//get variety by url charactor:
//		if (variety<0)
//			variety=getVarByURL(page.getURLString());		
//		
//		return variety;
//	}
	
	public abstract int getVarByURL(String url);


}
