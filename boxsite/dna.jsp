<%@ page import="box.mgr.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String urlstr = request.getParameter("url");
String regstr = request.getParameter("reg");
String tagdnastr = request.getParameter("tagdna");

SiteDNAManager.getInstance().init();

String jsonstr = "";
if (regstr==null&&tagdnastr==null)
 jsonstr = SiteDNAManager.getInstance().queryTestUrls(urlstr);
else if (tagdnastr==null)
 jsonstr = SiteDNAManager.getInstance().addSiteItemUrlReg(urlstr,regstr);
else {
 int tagtype = -1;
 String typestr = request.getParameter("tagtype");
 if (typestr!=null)
  tagtype = Integer.valueOf(typestr);
 jsonstr = SiteDNAManager.getInstance().addItemTagDNA(urlstr,tagdnastr,tagtype);
}

response.getWriter().print(jsonstr);
%>