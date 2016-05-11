<%@ page import="box.mgr.*"%> 
<%@ page import="java.util.*"%> 
<%@ page import="box.site.model.*"%> 
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
	<head>
 <script src="dist/js/jquery-2.0.3.min.js"></script>
  <script src="static/js/siteword.js"></script>
	</head>	
<body>
<%
request.setCharacterEncoding("UTF-8"); 
String wordstr = request.getParameter("word");
String wordidstr = request.getParameter("wordid");
int wordid = Integer.valueOf(wordidstr);

List<Website> list = SiteManager.getInstance().getSites(wordid);
%>
<%=wordstr%>;
<table>
<%
String url = "";
String name = "";
for (Website item:list){
  url = item.getUrl();
  if (url!=null&&url.indexOf("http")<0)
   url = "http://"+url;
  name = item.getName();
  if (name==null)
    name = item.getUrl();
%>
<tr>
  <td><a href='<%=url%>' target='blank'><%=name%></a></td>
  <td>alexa:<%=item.getAlexa()%></td>
  <td>bdrank<%=item.getBdrank()%></td>
  <td><a href='javascript:deleteWord(<%=wordid%>,<%=item.getSiteid()%>)'>删除该关键字关联</a></td>
  </tr>
<%}%>  
</table>

</body>