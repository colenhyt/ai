<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String wordstr = request.getParameter("word");
String wordlist = request.getParameter("wordlist");
String pagestr = request.getParameter("page");
int paging = 0;
if (pagestr!=null){
 paging = Integer.valueOf(pagestr);;
}
String sitejsons = "tt";
if (wordstr!=null)
 sitejsons = SiteManager.getInstance().querySites2(wordstr,paging);
else
 sitejsons = SiteManager.getInstance().queryWordGroupSites(wordlist);

response.getWriter().print(sitejsons);
%>