<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String wordidstr = request.getParameter("wordid");
String jsonstr = SiteManager.getInstance().getHotwords2();
response.getWriter().print(jsonstr);
%>