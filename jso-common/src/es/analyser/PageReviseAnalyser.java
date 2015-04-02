/*
 * Created on 2005-10-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.analyser;

/**
 * @author Allenhuang
 *
 * created on 2005-10-11
 */
public interface PageReviseAnalyser {
	
	public String filter(String urlStr);
    
    public String reviseURL(String oldURL);
    
    public String receiveKey(String urlStr);
    
    public void setSiteKey(String siteKey);
    
}
