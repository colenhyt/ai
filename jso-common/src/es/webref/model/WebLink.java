/*
 * Created on 2005-9-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.webref.model;

import easyshop.model.ModelConstants;
import es.util.url.URLStrHelper;

/**
 * @author Allenhuang
 *
 * created on 2005-9-16
 */
public class WebLink {
	private int linkId;
	private int typeId=-1;
	final String urlStr,refWord;
	public static final int TYPE_HTML=0;
	public static final int TYPE_IMG=1;
	public static final int TYPE_XML=2;
	public static final int TYPE_SOURCE=3;	
	private String urlKey=null;
    protected String siteId;
	
    public WebLink(){
    	this(null,null);
    }
    public WebLink(String _urlStr){
    	this(_urlStr,"");
    }
    
    public WebLink(String _urlStr,String _refWord){
    	urlStr=_urlStr;
    	refWord=_refWord;
    }    
    
	public String getSiteId() {
		return siteId;
	}

    public int getLinkId() {
        return linkId;
    }
    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }
    public String getRefWord() {
        return refWord;
    }

    public int getTypeId() {
    	if (typeId==-1){
    	int type=URLStrHelper.getFileType(urlStr);
    	if (type<=ModelConstants.PAGE_MAX_RESOURCE)
    		typeId= TYPE_SOURCE;
    	else 
    		typeId=TYPE_HTML;
    	}
        return typeId;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public String getUrlKey() {
        return urlKey;
    }
    public void setUrlKey(String linkKey) {
        this.urlKey = linkKey;
    }
    
    public boolean equals(Object obj){
    	return obj.toString().hashCode()==toString().hashCode();
    }
    
	public String toString(){
		return urlStr+":"+refWord;
	}

	public String getFileName() {
		return URLStrHelper.getFullFileName(getUrlStr());
	}

}
