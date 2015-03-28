function gettitles(wxhao)
{
    var dataParam = "wxtitle.type=-1&wxtitle.wxhao="+wxhao;
	var data =	$.ajax({type:"post",url:"/boxsite/show_titles.do",data:dataParam,async:false});
 var obj = cfeval(data.responseText);
 var tag = document.getElementById("titles");
 var content = ""
  content += "         <thead><tr>"
  content += "        <td>文章标题</td>"
  content += "        <td>发布时间</td>"
  content += "        <td>阅读数</td>"
  content += "        <td>赞数</td>"
  content += "        <td>推文公众号</td>"
  content += "        <td>操作</td>"
  content += "        <td>状态</td>"
  content += "        </tr>"
  content += "        </thead>"
 if (obj.length<=0)
  content += "收集推文数量为零";
 for (var i=0;i<obj.length;i++)
 {
  var item = obj[i];
  content += "<tr>"
  content += "<td><a href='"+item.titleurl+"' target=_blank>"+item.title+"</a></td>"
  content += "<td>"+item.pubdate+"</td>"
  content += "<td>"+item.viewcount+"</td>"
  content += "<td>"+item.zancount+"</td>"
  content += "<td><a href='http://www.5118.com/weixin/officials/search/"+item.wxname+"' target=_blank>"+item.wxname+"</a></td>"
  if (item.srcflag==1)
   content += "<td><input type='button' value='移出素材库' onclick='changeTitle("+item.id+",1,0)'></td>"
  else
   content += "<td><input type='button' value='加入素材库' onclick='changeTitle("+item.id+",1,1)'></td>"
  content += "<td>"+item.useflag+","+item.status+"</td>"
  content += "</tr>"
 }
 
 tag.innerHTML=content;

}

function changeTitle(id,flag,flagValue)
{
 var flagname = "srcflag"
 if (flag==2)
  flagname = "useflag"
 var dataParam = "wxtitle.id="+id+"&wxtitle."+flagname+"="+flagValue;
 var data = $.ajax({type:"post",url:"/boxsite/show_updatetitle.do",data:dataParam,async:false});
var obj = cfeval(data.responseText);
}

function loadwps(type)
{
var dataParam="wxpublic.status=1&wxpublic.type="+type;
var data = $.ajax({type:"post",url:"/boxsite/show_wps.do",data:dataParam,async:false});
var obj = cfeval(data.responseText);
 var content = ""
   content += "         <thead><tr>"
  content += "        <td>公众名称</td>"
  content += "        <td>公众号</td>"
  content += "        <td>openid</td>"
  content += "        <td>阅读数</td>"
  content += "        <td>收集推文</td>"
  content += "        <td>查看</td>"
  content += "        </tr>"
  content += "        </thead>"
 for (var i=0;i<obj.length;i++)
 {
   content += "<tr>"
  content += "<td><a href='http://www.5118.com/weixin/detail?name="+obj[i].wxname+"' target=_blank>"+obj[i].wxname+"</a></td> "
  content += "<td>"+obj[i].wxhao+"</td> "
  content += "<td>"+obj[i].openid+"</td> "
  content += "<td>"+obj[i].viewcount+"</td> "
  content += "<td>0</td> "
  content += "<td><input type='button' value='查看推文' onclick=\"gettitles('"+obj[i].wxhao+"')\"></td> "
  content += "</tr>"  
 }
 var tag = document.getElementById("wps");
  tag.innerHTML=content;
}

function loadtypes()
{
	var data = $.ajax({type:"post",url:"/boxsite/show_types.do",async:false});
	var obj = cfeval(data.responseText);
 var content = ""
 for (var i=0;i<obj.length;i++)
 {
  content += "<input type='button' value='"+obj[i].name+"' onclick='loadwps("+obj[i].type+")'> "
 }
 var tag = document.getElementById("wptype");
  tag.innerHTML=content;
}

loadtypes();