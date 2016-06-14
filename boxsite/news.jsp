<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String catstr = request.getParameter("cat");
String startsecstr = request.getParameter("startsec");
int catid = 0;
if (catstr!=null){
 catid = Integer.valueOf(catstr);
}
int startsec = 0;
if (startsecstr!=null){
 startsec = Integer.valueOf(startsecstr);
}

PageManager.getInstance().init();

String jsonstr = "";
 jsonstr = PageManager.getInstance().getCatNews(catid,startsec);

response.getWriter().print(jsonstr);
%>