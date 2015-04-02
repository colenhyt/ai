/*
 * 创建日期 2006-12-30
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package es.parser;

/**
 * @author Allen Huang
 *
 * 2006-12-30
 */
public class ParserPage {
    private int uid;
    private String siteId;
    private String specId;
    private String charSet;
    private byte[] content;
    private String urlStr;
    /**
     * @return 返回 charSet。
     */
    public String getCharSet() {
        return charSet;
    }
    /**
     * @param charSet 要设置的 charSet。
     */
    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }
    /**
     * @return 返回 content。
     */
    public byte[] getContent() {
        return content;
    }
    /**
     * @param content 要设置的 content。
     */
    public void setContent(byte[] content) {
        this.content = content;
    }
    /**
     * @return 返回 siteId。
     */
    public String getSiteId() {
        return siteId;
    }
    /**
     * @param siteId 要设置的 siteId。
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    /**
     * @return 返回 uid。
     */
    public int getUid() {
        return uid;
    }
    /**
     * @param uid 要设置的 uid。
     */
    public void setUid(int uid) {
        this.uid = uid;
    }
    /**
     * @return 返回 urlStr。
     */
    public String getUrlStr() {
        return urlStr;
    }
    /**
     * @param urlStr 要设置的 urlStr。
     */
    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }
	public String getSpecId() {
		return specId;
	}
	public void setSpecId(String specId) {
		this.specId = specId;
	}
}
