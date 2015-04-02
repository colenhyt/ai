/*
 * Created on 2005-9-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.model;

import java.util.Set;

/**
 * @author Allenhuang
 *
 * created on 2005-9-24
 */
public class Page extends ShopElement {
    private Set pageRefs;
    public Set getPageRefs() {
        return pageRefs;
    }
    public void setPageRefs(Set pageRefs) {
        this.pageRefs = pageRefs;
    }
}
