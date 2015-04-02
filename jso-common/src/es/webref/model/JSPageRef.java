/*
 * Created on 2005-9-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.webref.model;

import cl.site.util.SiteStrConverter;
import easyshop.model.ModelConstants;
import es.util.pattern.ESPattern;
import es.util.url.URLStrHelper;



/**
 * @author Allenhuang
 *
 * created on 2005-9-16
 */
public class JSPageRef extends PageRef {
    public static final int STATUS_NORMAL=0;
    public static final int STATUS_NEW=1;
    public static final int STATUS_OLD=-1;
    public static final int STATUS_DEAD=-2;
    private int refId;
    private String specId="";
    private int classId=-1;
    private int directId=-1;
    private int parentClassId=-1;
    private int parentUid=-1;
    private String relUrl;
    private String rel;
    private Object param;
    private int param2,paramSortId;
    private String paramTradeType;
    private boolean isNeedDownload=true;
    private String catRoot;
    
    public JSPageRef(String urlStr,String refWord){
    	super(urlStr,refWord);
    }
    
    public JSPageRef(String urlStr){
    	super(urlStr);
    }
    
	public String getPageName() {
		int filetype=URLStrHelper.getFileType(urlStr);
		if (filetype==ModelConstants.PAGE_TYPE_HTML){
			String filename=URLStrHelper.getFileName(urlStr);
			if (filename.indexOf(".")>0&&ESPattern.isNumber(filename.substring(0,filename.indexOf("."))))
				return "d*.htm";
			else
				return "xx.htm";
		}else
			return getFileName();
		
	}
	
	
	public String getSiteId() {
		if (siteId==null)
			siteId=SiteStrConverter.getSiteId(urlStr);
		return siteId;
	}
	
	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
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
    public String getSpecId() {
        return specId;
    }
    public void setSpecId(String siteId) {
        this.specId = siteId;
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
	public int getDirectId() {
		return directId;
	}
	public void setDirectId(int sortId) {
		this.directId = sortId;
	}

	public int getParam2() {
		return param2;
	}

	public void setParam2(int param2) {
		this.param2 = param2;
	}

	public boolean isNeedDownload() {
		return isNeedDownload;
	}

	public void setNeedDownload(boolean isNeedDownload) {
		this.isNeedDownload = isNeedDownload;
	}

	public String getParamTradeType() {
		return paramTradeType;
	}

	public void setParamTradeType(String paramTradeType) {
		this.paramTradeType = paramTradeType;
	}

	public String getCatRoot() {
		return catRoot;
	}

	public void setCatRoot(String catRoot) {
		this.catRoot = catRoot;
	}

	public int getParamSortId() {
		return paramSortId;
	}

	public void setParamSortId(int paramSortId) {
		this.paramSortId = paramSortId;
	}
}
