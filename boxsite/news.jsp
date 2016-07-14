<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String itemstr = request.getParameter("itemid");
String sessionstr = request.getParameter("sessionid");
long sessionid = -1;
if (sessionstr!=null)
  sessionid = Long.valueOf(sessionstr);
  
int itemid = -1;
if (itemstr!=null&&!itemstr.equalsIgnoreCase("false")){
 itemid = Integer.valueOf(itemstr);
}
PageManager.getInstance().init();

String jsonstr = "";
if (itemstr!=null&&!itemstr.equalsIgnoreCase("false")){
 jsonstr = PageManager.getInstance().getNews(sessionid,itemid);
}else{
String url = request.getParameter("url");

 jsonstr = PageManager.getInstance().getNews2(url);
}

response.getWriter().print(jsonstr);
%>