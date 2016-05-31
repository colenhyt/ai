<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String wordstr = request.getParameter("word");
String pagestr = request.getParameter("page");
int paging = 0;
if (pagestr!=null){
 paging = Integer.valueOf(pagestr);;
}
String sitejsons = "tt";
 sitejsons = SiteManager.getInstance().querySites(wordstr,paging);

response.getWriter().print(sitejsons);
%>