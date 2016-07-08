<%@ page import="box.mgr.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String urlstr = request.getParameter("url");
String regstr = request.getParameter("reg");
String tagdnastr = request.getParameter("tagdna");

SiteDNAManager.getInstance().init();

String jsonstr = "";
if (regstr==null&&tagdnastr==null)
 jsonstr = PageManager.getInstance().queryTestUrls(urlstr);
else if (tagdnastr==null)
 jsonstr = PageManager.getInstance().testAndAddSiteItemUrlReg(urlstr,regstr);
else
 jsonstr = PageManager.getInstance().testAndAddItemTagDNA(urlstr,tagdnastr);

response.getWriter().print(jsonstr);
%>