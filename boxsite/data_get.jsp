<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String typestr = request.getParameter("type");
int type = 1;
if (typestr!=null){
 type = Integer.valueOf(typestr);
}
String jsonstr = null;
if (type==1)
 jsonstr = SiteManager.getInstance().getHotwords2();
else if (type==2)
 jsonstr = SiteManager.getInstance().getHotwordlist();
else {
 String wordlist = request.getParameter("wordlist");
 jsonstr = SiteManager.getInstance().searchWordGroupSites(wordlist);
}
 
response.getWriter().print(jsonstr);
%>