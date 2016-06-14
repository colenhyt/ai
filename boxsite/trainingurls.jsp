<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String typestr = request.getParameter("type");
String sitekey = request.getParameter("sitekey");
String siteurls = request.getParameter("siteurls");
String isallflag = request.getParameter("isall");
boolean isAll = false;
if (isallflag!=null)
 isAll = true;
int typeid = 0;
if (typestr!=null){
 typeid = Integer.valueOf(typestr);;
}

PageManager.getInstance().init();

String jsonstr = "";
if (typeid==0)
 jsonstr = PageManager.getInstance().getSiteNotTrainingUrls(sitekey,isAll);
else if (typeid==1)
 jsonstr = PageManager.getInstance().addTrainingurls(sitekey,siteurls);
else if (typeid==2)
 jsonstr = PageManager.getInstance().getSitekeys();

response.getWriter().print(jsonstr);
%>