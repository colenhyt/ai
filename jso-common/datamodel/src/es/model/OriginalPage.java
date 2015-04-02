/*
 * Created on 2005-9-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.model;

import java.util.Set;

import es.webref.model.JSPageRef;
import es.webref.model.PageRef;




/**
 * @author Allenhuang
 *
 * created on 2005-9-15
 */
public class OriginalPage extends DownloadPage{

    private int varId=-1;
    private int classId=-1;
    private int refStatus=-1;
    private boolean valid;
    private Set<PageRef> brotherRefs,catalogRefs;
    private Set<JSPageRef> parentRefs;
    private Set<PageRef> childRefs;
    private String purityContentStr;
    private RefRelation otherRef;
    private int sortId=-1;
	public static final int CAT_NOTFOUND_DIVS=-2;
	public static final int CAT_NOTFOUND_ITEMS=-3;
	public static final int CAT_FINDITEMS=-1;
	public static final int CAT_FINDURLS=0;
	public static final int DATA_UPDATEPS=1;
	public static final int DATA_UPDATEPS_need=-11;
	public static final int DATA_UPDATEPS_DEALED=11;
	public static final int DATA_UPDATEALL=2;
	public static final int DATA_W_PAGE=-2;
	public static final int DATA_W_PS=-3;
	public static final int DATA_W_NAME=-4;
	public static final int DATA_UPDATEALL_need=-22;
	public static final int DATA_UPDATEALL_DEALED=22;
	public static final int DATA_UPDATEALL_NEW=23;
	public static final int DATA_FINDREVIEWS=3;
	public static final int DATA_FINDREVIEWS_need=-33;
	public static final int DATA_FINDREVIEWS_DEALED=33;
	public static final int DATA_FINDPICTURE=4;
	public static final int DATA_FINDPICTURE_need=-44;
	public static final int DATA_FINDPICTURE_DEALED=44;
	public static final int DATA_FINDSHOPBOOKS=5;
	public static final int DATA_FINDSHOPBOOKS_need=-55;
	public static final int DATA_FINDSHOPBOOKS_DEALED=55;
	public static final int DATA_UPDATESUMM=6;
	public static final int DATA_UPDATESUMM_need=-66;
	public static final int DATA_UPDATESUMM_DEALED=66;
	public static final int DATA_FINDALLREVIEWS=7;
	public static final int DATA_FINDALLREVIEWS_need=-77;
	public static final int DATA_FINDALLREVIEWS_DEALED=77;
	
    public boolean isValid() {
        return valid;
    }
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    public int getRefStatus() {
        return refStatus;
    }
    public void setRefStatus(int refStatus) {
        this.refStatus = refStatus;
    }
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public int getVarId() {
        return varId;
    }
    public void setVarId(int varId) {
        this.varId = varId;
    }
	public Set<PageRef> getBrotherRefs() {
		return brotherRefs;
	}
	public void setBrotherRefs(Set<PageRef> brotherRefs) {
		this.brotherRefs = brotherRefs;
	}
	public Set<PageRef> getChildRefs() {
		return childRefs;
	}
	public void setChildRefs(Set<PageRef> childRefs) {
		this.childRefs = childRefs;
	}
	public Set<JSPageRef> getParentRefs() {
		return parentRefs;
	}
	public void setParentRefs(Set<JSPageRef> parentRefs) {
		this.parentRefs = parentRefs;
	}
	public String getPurityContentStr() {
		return purityContentStr;
	}
	public void setPurityContentStr(String purityContentStr) {
		this.purityContentStr = purityContentStr;
	}
	public Set<PageRef> getCatalogRefs() {
		return catalogRefs;
	}
	public void setCatalogRefs(Set<PageRef> catalogRefs) {
		this.catalogRefs = catalogRefs;
	}
	public RefRelation getOtherRef() {
		return otherRef;
	}
	public void setOtherRef(RefRelation otherRef) {
		this.otherRef = otherRef;
	}
	public int getSortId() {
		return sortId;
	}
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	
}
