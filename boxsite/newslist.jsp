<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String typestr = request.getParameter("type");

String jsonstr = "";
if (typestr==null)
  jsonstr = PageManager.getInstance().getNewslist(request);
 else
  jsonstr = PageManager.getInstance().getNewsCount(request);

response.getWriter().print(jsonstr);
%>