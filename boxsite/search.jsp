<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String wordstr = request.getParameter("word");
String wordidstr = request.getParameter("wordid");
String sitejsons = "";
if (wordstr!=null){
 sitejsons = SiteManager.getInstance().getSites(wordstr);
}else {
int wordid = Integer.valueOf(wordidstr);
sitejsons = SiteManager.getInstance().getSites(wordid);
}
response.getWriter().print(sitejsons);
%>