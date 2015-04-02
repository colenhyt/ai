/*
 * Created on 2005-9-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.download.collection;

import java.util.HashSet;

import es.webref.model.JSPageRef;


/**
 * @author Allenhuang
 *
 * created on 2005-9-17
 */
public class PageRefSet extends HashSet {
    public boolean add(Object ref){
        if (!(ref instanceof JSPageRef)){
            throw new EasyCollectionException("input type is not PageRef");
        }
        return super.add(ref);
    }
}
