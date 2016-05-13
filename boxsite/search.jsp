<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String wordstr = request.getParameter("word");
String pagestr = request.getParameter("page");
int page = 0;
if (pagestr!=null){
 page = Integer.valueOf(pagestr);;
}
String sitejsons = "";
 sitejsons = SiteManager.getInstance().querySites(wordstr,page);

response.getWriter().print(sitejsons);
%>