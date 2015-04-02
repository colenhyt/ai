/*
 * Created on 2005-9-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.datamodel;

/**
 * @author Allenhuang
 *
 * created on 2005-9-24
 */
public interface PageClassifyviaURLAnalyser extends PageClassifyAnalyser{
    
	public void setSiteId(String siteId);
	
    public void sendInfo(DataURLInfo info);

}
