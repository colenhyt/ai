/*
 */


package easyshop.downloadhelper;

import java.net.URL;

public class URLToDownload implements java.io.Serializable
{
    private final URL url;
    private final URL referer;
    private String urlStr;
    private String siteId;
    private int docId;
    private String urlKey;
    private int classify;

	/**
	 * @return 返回 urlKey。
	 */
	public String getUrlKey() {
		return urlKey;
	}
	/**
	 * @param urlKey 要设置的 urlKey。
	 */
	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}
    public String getSiteId() {
        return siteId;
    }
    
    public URLToDownload(URL url)
    {
        this(url, null, 0,null);
    }
    
    public URLToDownload(URL url, int dId)
    {
        this(url, null, dId,null);
    }
    
    public URLToDownload(URL url, URL referer, int dId,String uKey)
    {
        this.url = url;
        this.referer = referer;
        this.docId = dId;
        urlKey=uKey;
    }

    public URL getURL()
    {
        return this.url;
    }

    public URL getReferer()
    {
        return referer;
    }

    public int getDocId(){
        return docId;
    }
    
    public String toString()
    {
        return this.url + ", referer " + referer + ", docId " + docId;
    }
}
