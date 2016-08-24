<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@ page import="org.jsoup.*"%>
<%@ page import="org.jsoup.nodes.*"%>
<%@ page import="org.jsoup.select.*"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String jsonstr = CatWordsManager.getInstance().queryCatWords(request);

response.getWriter().print(jsonstr);
%>