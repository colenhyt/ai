/*
 * Created on 2005-9-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.download.collection;

import java.util.HashSet;

import es.webref.model.WebLink;


/**
 * @author Allenhuang
 *
 * created on 2005-9-17
 */
public class LinkSet extends HashSet {
    
    public boolean add(Object link){
        if (!(link instanceof WebLink)){
            throw new EasyCollectionException("input type is not WebLink");
        }
        return super.add(link);
    }
    
    
    

}
