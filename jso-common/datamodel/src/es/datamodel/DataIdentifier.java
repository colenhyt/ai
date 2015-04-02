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
public class DataIdentifier {
    private int type;
    private String urlKey;
    private int classify=-1;

    public String getUrlKey() {
        return urlKey;
    }
    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }
    public int getType() {
        return type;
    }
    public void setType(int classify) {
        this.type = classify;
    }
	public int getClassify() {
		return classify;
	}
	public void setClassify(int classify) {
		this.classify = classify;
	}
}
