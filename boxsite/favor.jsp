<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String sessionidstr = request.getParameter("sessionid");
 long sessionid = -1;
if (sessionidstr!=null) 
 sessionid = Long.valueOf(sessionidstr);
 
String typestr = request.getParameter("type");
int typeid = -1;
if (typestr!=null)
 typeid = Integer.valueOf(typestr);
 
String itemstr = request.getParameter("itemid");
int itemid = -1;
if (itemstr!=null&&!itemstr.equalsIgnoreCase("false")){
 itemid = Integer.valueOf(itemstr);
}
PageManager.getInstance().init();

String jsonstr = "";
if (typeid==1){
 jsonstr = PageManager.getInstance().getFavors(sessionid);
}else{
 jsonstr = PageManager.getInstance().addFavo(sessionid,itemid);
}

response.getWriter().print(jsonstr);
%>