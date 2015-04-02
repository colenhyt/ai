/*
 * Created on 2005-9-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.model;

import java.io.UnsupportedEncodingException;

import cl.site.util.SiteStrConverter;
import es.Constants;
import es.datamodel.ContentInfo;


/**
 * @author Allenhuang
 *
 * created on 2005-9-15
 */
public class WebElement  implements ContentInfo{
    private String specId;
    private String siteId;
    private String urlStr;
    private int typeId=-1;
    private byte[] content;
    private String charSet=Constants.CHARTSET_GBK;
    
	public String getEncoding() {
        return charSet;
    }
    public void setCharSet(String charSet) {
        if (charSet!=null&&charSet.trim().length()>0)
            this.charSet = charSet;
    }
    
    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }
    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getUrlStr() {
        return urlStr;
    }
    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public String getSpecId() {
        return specId;
    }
    public void setSpecId(String siteId) {
        this.specId = siteId;
    }
    
    private String context;
    public String toStringContent(){
        try {
            if (context==null&&content!=null&&content.length>0&&content.length<10000000){
                
            	context=new String(content,charSet);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return context;
    }
	public String getSiteId() {
		if (siteId==null)
			return SiteStrConverter.getShopSiteId(urlStr);
		
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public int getItemActionType() {
		// TODO Auto-generated method stub
		return -2;
	}
}
