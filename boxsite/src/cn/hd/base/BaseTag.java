package cn.hd.base;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;



/**
 * 标签公用
 * 2011-4-15
 * 
 * @author hd
 */
public class BaseTag extends TagSupport{

	public HttpServletRequest getRequest(){
		return (HttpServletRequest)pageContext.getRequest();
	}
	public HttpSession getSession(){
		return getRequest().getSession();
	}
}