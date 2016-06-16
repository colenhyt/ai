<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String sessionidstr = request.getParameter("sessionid");
String startsecstr = request.getParameter("starttime");
String typestr = request.getParameter("type");

long sessionid = 0;
if (sessionidstr!=null){
 sessionid = Long.valueOf(sessionidstr);
}

PageManager.getInstance().init();

String jsonstr = "";
  jsonstr = PageManager.getInstance().login(sessionid);

response.getWriter().print(jsonstr);
%>