<%@ page import="box.mgr.*"%> 
<%@ page import="com.alibaba.fastjson.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String catstr = request.getParameter("cat");
String itemidstr = request.getParameter("itemid");
String typestr = request.getParameter("type");
String dirstr = request.getParameter("dir");
String countstr = request.getParameter("count");

int dir = 1;
if (dirstr!=null)
 dir = Integer.valueOf(dirstr);
 
int itemid = 0;
System.out.println(itemidstr);
if (itemidstr!=null&&!itemidstr.equals("undefined")){
 itemid = Integer.valueOf(itemidstr);
}

int count = -1;
if (countstr!=null)
 count = Integer.valueOf(countstr);

PageManager.getInstance().init();

String jsonstr = "";
int catid = Integer.valueOf(catstr);
if (typestr==null)
  jsonstr = PageManager.getInstance().getNewslist(catid,itemid,dir,count);
 else
  jsonstr = PageManager.getInstance().getNewsCount(catid,itemid,dir,count);

response.getWriter().print(jsonstr);
%>