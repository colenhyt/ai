<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@ page import="org.jsoup.*"%>
<%@ page import="org.jsoup.nodes.*"%>
<%@ page import="org.jsoup.select.*"%>
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
String catstr = request.getParameter("catid");
int catid = -1;
if (catstr!=null){
 catid = Integer.valueOf(catstr);
}

PageManager.getInstance().init();

String jsonstr = "";
if (typeid==0)
 jsonstr = PageManager.getInstance().getSiteNotTrainingUrls(sitekey,isAll);
else if (typeid==-1)
 jsonstr = PageManager.getInstance().getSiteTrainingUrls(sitekey);
else if (typeid==1)
 jsonstr = PageManager.getInstance().addTrainingurls(sitekey,siteurls);
else if (typeid==2)
 jsonstr = PageManager.getInstance().getSitekeys();
else if (typeid==3)
 jsonstr = PageManager.getInstance().getSiteTrainingUrls(sitekey,catid);

response.getWriter().print(jsonstr);
%>