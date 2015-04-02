/*
 * Created on 2005-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cl;

/**
 * @author Allenhuang
 *
 * created on 2005-11-28
 */
public class EasyException extends Exception {
    public EasyException(){
        
    }

	public EasyException(String string, Throwable root) {
		super(string, root);
	}
	public EasyException(String s) {
		super(s);
	}
	

	public EasyException(Exception e) {
		super(e);
	}
}
