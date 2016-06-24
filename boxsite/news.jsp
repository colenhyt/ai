<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String catstr = request.getParameter("cat");
String startsecstr = request.getParameter("starttime");
String typestr = request.getParameter("type");

long startsec = 0;
if (startsecstr!=null&&!startsecstr.equalsIgnoreCase("undefined")){
 startsec = Long.valueOf(startsecstr);
}

String itemstr = request.getParameter("itemid");
int itemid = -1;
if (itemstr!=null&&!itemstr.equalsIgnoreCase("false")){
 itemid = Integer.valueOf(itemstr);
}
PageManager.getInstance().init();

String jsonstr = "";
int catid = -1;
if (catstr != null&&!catstr.equalsIgnoreCase("false")){
 	catid = Integer.valueOf(catstr);
	if (typestr==null)
	  jsonstr = PageManager.getInstance().getNewslist(catid,startsec);
	 else
	  jsonstr = PageManager.getInstance().getNewsCount(catid,startsec);
}else if (itemstr!=null&&!itemstr.equalsIgnoreCase("false")){
 jsonstr = PageManager.getInstance().getNews(itemid);
}else{
String url = request.getParameter("url");

 jsonstr = PageManager.getInstance().getNews2(url);
}

response.getWriter().print(jsonstr);
%>