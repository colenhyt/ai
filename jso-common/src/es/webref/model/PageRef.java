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
public class PageRef extends WebReferer {
    public static final int STATUS_NORMAL=0;
    public static final int STATUS_NEW=1;
    public static final int STATUS_OLD=-1;
    public static final int STATUS_DEAD=-2;
    private int refId,paging=-1,relPaging=-1;
    protected String siteId="";
    private int classId=-1;
    private int parentClassId=-1;
    private int parentUid=-1,pri=0;
    private String relUrl,relWord;
    private String rel;
    private int classify;
    private String rootUrlStr=null,parentUrl=null;
    public PageRef(){
    	super();
    }
    public PageRef(String urlStr){
    	super(urlStr);
    }

    public PageRef(String urlStr,String refWord){
    	super(urlStr,refWord);
    }
    
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getRelUrl() {
		return relUrl;
	}
	public void setRelUrl(String parentUrl) {
		this.relUrl = parentUrl;
	}
	/**
	 * @return 返回 parentClassId。
	 */
	public int getParentClassId() {
		return parentClassId;
	}
	/**
	 * @param parentClassId 要设置的 parentClassId。
	 */
	public void setParentClassId(int parentClassId) {
		this.parentClassId = parentClassId;
	}
	/**
	 * @return 返回 parentUid。
	 */
	public int getParentUid() {
		return parentUid;
	}
	/**
	 * @param parentUid 要设置的 parentUid。
	 */
	public void setParentUid(int parentUid) {
		this.parentUid = parentUid;
	}
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public String getSiteId() {
        return siteId;
    }
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    public int getRefId() {
        return refId;
    }
    public void setRefId(int refId) {
        this.refId = refId;
    }
    private int status=STATUS_NEW;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
	public int getClassify() {
		return classify;
	}
	public void setClassify(int classify) {
		this.classify = classify;
	}
	public int getPaging() {
		return paging;
	}
	public void setPaging(int paging) {
		this.paging = paging;
	}
	public String getRootUrlStr() {
		return rootUrlStr;
	}
	public void setRootUrlStr(String rootUrlStr) {
		this.rootUrlStr = rootUrlStr;
	}
	public int getPri() {
		return pri;
	}
	public void setPri(int pri) {
		this.pri = pri;
	}
	public int getRelPaging() {
		return relPaging;
	}
	public void setRelPaging(int relPaging) {
		this.relPaging = relPaging;
	}
	public String getRelWord() {
		return relWord;
	}
	public void setRelWord(String relWord) {
		this.relWord = relWord;
	}
	public String getParentUrl() {
		return parentUrl;
	}
	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}
    
    public boolean equals(Object obj){
    	if (obj instanceof PageRef){
    		PageRef ref=(PageRef)obj;
    		return ref.getUrlStr().equalsIgnoreCase(urlStr);
    	}
    	return super.equals(obj);
    		
    }
}
