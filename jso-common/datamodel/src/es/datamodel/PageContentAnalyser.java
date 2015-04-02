/*
 * Created on 2005-10-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.datamodel;

import easyshop.model.Item;

/**
 * @author Allenhuang
 *
 * created on 2005-10-5
 */
public interface PageContentAnalyser {
    
    public void sendContentInfo(ContentInfo info);
    
    public Item receive();
    
    public ICharactorsDictionary getDictionary();
    
}
