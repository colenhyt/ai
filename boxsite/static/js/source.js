function getcon(id,srcflag)
{
	var cont =  "<input type='button' value='加入素材库' onclick='changeTitle("+id+",1,1)'>"
	if (srcflag==1)
	 cont =  "<input type='button' style='background:blue' value='移出素材库' onclick='changeTitle("+id+",1,0)'>"
	return cont;
}

function getconUse(id,useflag)
{
	var cont =  "<input type='button' value='选用' onclick='changeTitle("+id+",2,1)'>"
	if (useflag==1)
	 cont =  "<input type='button' style='background:blue' value='未选用' onclick='changeTitle("+id+",2,0)'>"
	return cont;
}

function gettitles(type)
{
    var dataParam = "wxtitle.srcflag=1&wxtitle.type="+type;
	var data =	$.ajax({type:"post",url:"/boxsite/show_titles.do",data:dataParam,async:false});
 var obj = cfeval(data.responseText);
 var tag = document.getElementById("titles");
 var content = ""
  content += "         <thead><tr style='background:#128171;color:#ffffff;font-size:20px'>"
  content += "        <td>文章标题</td>"
  content += "        <td>发布时间</td>"
  content += "        <td>阅读数</td>"
  content += "        <td>赞数</td>"
  content += "        <td>推文公众号</td>"
  content += "        <td>操作</td>"
  content += "        <td>标记</td>"
  content += "        </tr>"
  content += "        </thead>"
 if (obj.length<=0)
  content += "收集推文数量为零";
 for (var i=0;i<obj.length;i++)
 {
  var item = obj[i];
  content += "<tr style='font-size:18px;border:2px solid'>"
  content += "<td><a href='"+item.titleurl+"' target=_blank>"+item.title+"</a></td>"
  content += "<td width=200>"+strtime(item.pubdate)+"</td>"
  content += "<td width=130>"+item.viewcount+"</td>"
  content += "<td width=40>"+item.zancount+"</td>"
  content += "<td width=100><a href='http://www.5118.com/weixin/officials/search/"+item.wxname+"' target=_blank>"+item.wxname+"</a></td>"
  content += "<td><div id='bb"+item.id+"'>"
  content += getcon(item.id,item.srcflag);
  content += "</div></td>"
  content += "<td><div id='usebb"+item.id+"'>"
  content += getconUse(item.id,item.useflag);
  content += "</div></td>"
  content += "</tr>"
 }
 
 tag.innerHTML=content;

}

function changeTitle(id,flag,flagValue)
{
 var flagname = "srcflag"
 var tagid = "bb"+id;
 var tagvalue = getcon(id,flagValue);
 if (flag==2) {
  flagname = "useflag"
  tagid = "usebb"+id;
  tagvalue = getconUse(id,flagValue);
 }
 var dataParam = "wxtitle.id="+id+"&wxtitle."+flagname+"="+flagValue;
 var data = $.ajax({type:"post",url:"/boxsite/show_updatetitle.do",data:dataParam,async:false});
var obj = cfeval(data.responseText);
if (obj.code==0)
{
 var tag = document.getElementById(tagid);
 	tag.innerHTML = tagvalue;
}

}

function loadtypes()
{
	var data = $.ajax({type:"post",url:"/boxsite/show_types.do",async:false});
	var obj = cfeval(data.responseText);
 var content = ""
 for (var i=0;i<obj.length;i++)
 {
  content += "<input type='button' style='font-size:20px' value='"+obj[i].name+"' onclick='gettitles("+obj[i].type+")'> "
 }
 var tag = document.getElementById("wptype");
  tag.innerHTML=content;
}

loadtypes();