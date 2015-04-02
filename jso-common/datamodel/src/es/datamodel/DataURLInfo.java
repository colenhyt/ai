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
public class DataURLInfo {
    private String siteId;
    private String urlStr;
    
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }
    public String getSiteId() {
        return siteId;
    }
    public String getUrlStr() {
        return urlStr;
    }
}
