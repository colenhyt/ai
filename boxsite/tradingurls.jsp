<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String typestr = request.getParameter("type");
String sitekey = request.getParameter("sitekey");
String siteurls = request.getParameter("siteurls");
int typeid = 0;
if (typestr!=null){
 typeid = Integer.valueOf(typestr);;
}

PageManager.getInstance().init();

String jsonstr = "";
if (typeid==0)
 jsonstr = PageManager.getInstance().getSiteNotTradingUrls(sitekey);
else if (typeid==1)
 jsonstr = PageManager.getInstance().addTradingurls(sitekey,siteurls);

response.getWriter().print(jsonstr);
%>