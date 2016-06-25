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

PageManager.getInstance().init();

String jsonstr = "";
int catid = Integer.valueOf(catstr);
if (typestr==null)
  jsonstr = PageManager.getInstance().getNewslist(catid,startsec);
 else
  jsonstr = PageManager.getInstance().getNewsCount(catid,startsec);

response.getWriter().print(jsonstr);
%>