package cn.hd.filter;


import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpSession;
import cn.hd.util.MybatisSessionFactory;




public class MybatisSessionFilter implements Filter {
	protected FilterConfig filterConfig;

	/**
	 * 过滤处理的方法
	 */
	public void doFilter(final ServletRequest req, final ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(req, res);
		//关闭链接前的数据库操作
		//访问记录

		//关闭链接
		MybatisSessionFactory.closeSession(req);
	}

	public void setFilterConfig(final FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	/**
	 * 销毁过滤器
	 */
	public void destroy() {
		this.filterConfig = null;
	}

	/**
	 * 初始化过滤器,和一般的Servlet一样，它也可以获得初始参数。
	 */
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}

}
