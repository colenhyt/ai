/*
 * Created on 2005-9-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.webref.model;

/**
 * @author Allenhuang
 *
 * created on 2005-9-16
 */
public class WebReferer extends WebLink {
    private long docId;

    public WebReferer(){
    	super();
    }
    public WebReferer(String urlStr){
    	super(urlStr);
    }
    
    public WebReferer(String urlStr,String refWord){
    	super(urlStr,refWord);
    }
    
    public long getDocId() {
        return docId;
    }
    public void setDocId(long docId) {
        this.docId = docId;
    }
}
