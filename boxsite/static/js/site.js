function gettitles()
{
    var dataParam = "wxtitle.status=1";
	try    {
		$.ajax({type:"post",url:"/boxsite/show_titles.do",data:dataParam,success:function(data){
		 getcallback(data);
		}});
	}   catch  (e)   {
	    logerr(e.name  +   " :  "   +  dataParam);
	   return false;
	}	
}

function getcallback(data)
{
 var obj = cfeval(data);
 var tag = document.getElementById("titles");
 var content = ""
 for (var i=0;i<obj.length;i++)
 {
  var item = obj[i];
  content += "<tr>"
  content += "<td><a href='"+item.titleurl+"' target=_blank>"+item.title+"</a></td>"
  content += "<td>"+item.pubdate+"</td>"
  content += "<td>"+item.viewcount+"</td>"
  content += "<td>"+item.zancount+"</td>"
  content += "<td>"+item.srcflag+"</td>"
  content += "<td>"+item.useflag+"</td>"
  content += "<td>"+item.status+"</td>"
  content += "</tr>"
 }
 
 tag.innerHTML=content;

}
