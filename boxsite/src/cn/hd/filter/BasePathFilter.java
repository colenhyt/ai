package cn.hd.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class BasePathFilter implements Filter {

	protected FilterConfig filterConfig = null;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			//判断是否有basePath
			if (filterConfig.getServletContext().getAttribute("basePath")==null ||
					filterConfig.getServletContext().getAttribute("basePath").toString().trim().length()==0) {
				HttpServletRequest httpServletRequest=(HttpServletRequest)request;
				String basePath=httpServletRequest.getScheme()+"://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath()+"/";
				filterConfig.getServletContext().setAttribute("basePath", basePath);
			}
			chain.doFilter(request, response);

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

}
