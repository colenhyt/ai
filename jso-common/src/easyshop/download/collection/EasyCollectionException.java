/*
 * Created on 2005-9-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.download.collection;

import es.EasyRuntimeException;

/**
 * @author Allenhuang
 *
 * created on 2005-9-17
 */
public class EasyCollectionException extends EasyRuntimeException {
    public EasyCollectionException(){
        
    }

	public EasyCollectionException(String string, Throwable root) {
		super(string, root);
	}
	public EasyCollectionException(String s) {
		super(s);
	}
	

	public EasyCollectionException(Exception e) {
		super(e);
	}   
}
