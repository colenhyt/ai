/*
 * Created on 2006-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.datamodel;

/**
 * Tracy&Allen.EasyShop 0.9
 *
 * @author Allenhuang
 * 
 * created on 2006-3-21
 */
public interface ContentInfo {
	
	public String getEncoding();
    
    public String getUrlStr();
    
    public byte[] getContent();
    
    public String toStringContent();
    
    public String getSpecId();
    
    public String getSiteId();

    public int getItemActionType();
}
