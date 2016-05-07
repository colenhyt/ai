<%@ page import="box.mgr.*"%> 
<%@ page import="java.util.*"%> 
<%@ page import="box.site.model.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
request.setCharacterEncoding("UTF-8"); 
String wordstr = request.getParameter("word");
String wordidstr = request.getParameter("wordid");
int wordid = Integer.valueOf(wordidstr);

List<Website> list = SiteManager.getInstance().getSites(wordid);
response.getWriter().print(wordstr);
response.getWriter().print("<table>");
for (Website item:list){
  response.getWriter().print("<tr>");
  String url = item.getUrl();
  if (url.indexOf("http")<0)
   url = "http://"+url;
  String name = item.getName();
  if (name==null)
    name = item.getUrl();
  response.getWriter().print("<td><a href='"+url+"' target='blank'>"+name+"</a></td>");
  response.getWriter().print("<td>desc:"+item.getCdesc()+"</td>");
  response.getWriter().print("<td>keys:"+item.getKeywords()+"</td>");
  response.getWriter().print("<td>alexa:"+item.getAlexa()+"</td>");
  response.getWriter().print("<td>bdrank:"+item.getBdrank()+"</td>");
  
  response.getWriter().print("</tr>");
}
response.getWriter().print("</table>");
%>