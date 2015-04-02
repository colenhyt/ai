/*
 * Created on 2005-9-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.download.collection;

import java.util.Collection;
import java.util.HashSet;


/**
 * @author Allenhuang
 *
 * created on 2005-9-15
 */
public class DownloadPageSet extends HashSet {
    
    public DownloadPageSet(){
        
    }
    
    public DownloadPageSet(Collection c){
        super(c);
    }
    
//    public boolean add(Object ref){
////        if (!(ref instanceof DownloadPageSet)){
////            throw new EasyCollectionException("input type is not DownloadPageSet");
////        }
//        return super.add(ref);
//    }
}
