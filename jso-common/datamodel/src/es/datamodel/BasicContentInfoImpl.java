/*
 * Created on 2006-3-23
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
 * created on 2006-3-23
 */
public class BasicContentInfoImpl implements ContentInfo {
    private final String urlStr,content,siteId;
    private final String charSet;
    
    public BasicContentInfoImpl(String _urlStr,String _content,String _siteId,String cSet){
        urlStr=_urlStr;
        content=_content;
        siteId=_siteId;
        charSet=cSet;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public String toStringContent() {
        return content;
    }

    public String getSpecId() {
        return null;
    }

    public String getSiteId() {
        return siteId;
    }
    
    public String getEncoding(){
    	return charSet;
    }

	public String getContext() {
		// TODO Auto-generated method stub
		return content;
	}

	public byte[] getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getItemActionType() {
		// TODO Auto-generated method stub
		return -2;
	}
}
