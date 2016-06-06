<%@ page import="box.mgr.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
String typestr = request.getParameter("typeid");
int typeid = -1;
if (typestr!=null){
 typeid = Integer.valueOf(typestr);;
}
String jsonstr = ShoppingManager.getInstance().getDataStr(typeid);

response.getWriter().print(jsonstr);
%>