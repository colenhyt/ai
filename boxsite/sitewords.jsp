<%@ page import="box.mgr.*"%> 
<%@ page import="java.util.*"%> 
<%@ page import="box.site.model.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
List<Websitewords> list = SiteManager.getInstance().getSiteWordlist();
response.getWriter().print("<table>");
int i = 0;
for (Websitewords item:list){
 if (i%5==0)
  response.getWriter().print("<tr>");
  
  response.getWriter().print("<td><a href='./siteurls.jsp?wordid="+item.getWordid()+"&word="+item.getWord()+"'>"+item.getWord()+"</a></td>");
  
 if (i%5==0)
  response.getWriter().print("</tr>");
  
  i++;
}
response.getWriter().print("</table>");
%>