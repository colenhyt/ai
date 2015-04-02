/*
 * Created on 2005-7-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package es.variety;

import easyshop.model.ModelConstants;
import easyshop.model.Page;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ProductVarietyAnalyserImpl  extends BasicVarietyAnalyser implements ProductVarietyAnalyser,URLVarietyAnalyser {
	
	public int findVariety(Page page){
		int variety=ModelConstants.VARIETY_UNKNOWN;
		
		if (variety<0&&page.getUrlStr()!=null)
			variety=getVarByURL(page.getUrlStr());		
    	
    	//get Variety by title key:
		if (variety<0&&page.toStringContent()!=null)
			variety=getVarietyByTitle(page.toStringContent());		
		
		return variety;

	}
	
	public abstract int getVarByURL(String url);

}
