package box.main;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import box.mgr.PageManager;

public class InitServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Logger  log = Logger.getLogger(getClass()); 
	
	public void init() throws ServletException {
		PageManager.getInstance().init();
		EventManager.getInstance().start();
		log.warn("boxsite startup successful!");
	}
}
