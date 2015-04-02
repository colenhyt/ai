/*
 * Created on 2005-8-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cl;

/**
 * @author Allenhuang
 *
 * created on 2005-8-29
 */
public class EasyRuntimeException extends RuntimeException {
    
    public EasyRuntimeException(){
        
    }

	public EasyRuntimeException(String string, Throwable root) {
		super(string, root);
	}
	public EasyRuntimeException(String s) {
		super(s);
	}
	

	public EasyRuntimeException(Exception e) {
		super(e);
	}
}
