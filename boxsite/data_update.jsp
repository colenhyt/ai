<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String wordidstr = request.getParameter("wordid");
String siteidstr = request.getParameter("siteid");
int siteid = Integer.valueOf(siteidstr);
int wordid = Integer.valueOf(wordidstr);
int ret = SiteManager.getInstance().deleteWordid(wordid,siteid);
response.getWriter().print(ret);
%>