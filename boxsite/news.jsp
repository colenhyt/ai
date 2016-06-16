<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String catstr = request.getParameter("cat");
String startsecstr = request.getParameter("starttime");
String typestr = request.getParameter("type");

long startsec = 0;
if (startsecstr!=null){
 startsec = Long.valueOf(startsecstr);
}

String itemstr = request.getParameter("item");
int itemid = -1;
if (itemstr!=null){
 itemid = Integer.valueOf(itemstr);
}
PageManager.getInstance().init();

String jsonstr = "";
int catid = -1;
if (catstr != null){
 catid = Integer.valueOf(catstr);
 if (typestr==null)
  jsonstr = PageManager.getInstance().getNewslist(catid,startsec);
 else if (typestr.equals("2"))
  jsonstr = PageManager.getInstance().getNewsCount(catid,startsec);
}else {
 jsonstr = PageManager.getInstance().getNews(itemid);
}

response.getWriter().print(jsonstr);
%>