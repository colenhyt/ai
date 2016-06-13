<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String catstr = request.getParameter("cat");
int catid = 0;
if (catstr!=null){
 catid = Integer.valueOf(catstr);;
}

PageManager.getInstance().init();

String jsonstr = "";
 jsonstr = PageManager.getInstance().getCatNews(catid);

response.getWriter().print(jsonstr);
%>