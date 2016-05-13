<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String typestr = request.getParameter("type");
int ret = 0;
if (typestr.equals("1")){
 String rankstr = request.getParameter("rankstr"); 
 ret = SiteManager.getInstance().updateMyranks(rankstr);
}else {
String wordidstr = request.getParameter("wordid");
String siteidstr = request.getParameter("siteid");
int siteid = Integer.valueOf(siteidstr);
int wordid = Integer.valueOf(wordidstr);
ret = SiteManager.getInstance().deleteWordid(wordid,siteid);
}

response.getWriter().print(ret);
%>