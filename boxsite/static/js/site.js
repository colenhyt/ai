function getcon(id,srcflag)
{
	var cont =  "<input type='button' value='加入' onclick='changeTitle("+id+",1,1)'>"
	if (srcflag==1)
	 cont =  "<input type='button' style='background:blue' value='移出' onclick='changeTitle("+id+",1,0)'>"
	return cont;
	
}
function gettitles(wxhao)
{
$('#tr'+wxhao).css("backgroundColor","pink");

    var dataParam = "wxtitle.srcflag=-1&wxtitle.type=-1&wxtitle.wxhao="+wxhao;
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
  content += "        </tr>"
  content += "        </thead>"
 if (obj.length<=0)
  content += "收集推文数量为零";
 for (var i=0;i<obj.length;i++)
 {
  var item = obj[i];
  content += "<tr style='font-size:18px;border:2px solid' id='tr"+item.id+"'>"
  content += "<td><a href='"+item.titleurl+"' target=_blank>"+item.title+"</a></td>"
  content += "<td width=160>"+strtime(item.pubdate)+"</td>"
  content += "<td width=120>"+item.viewcount+"</td>"
  content += "<td width=30>"+item.zancount+"</td>"
  content += "<td width=100><a href='http://www.5118.com/weixin/officials/search/"+item.wxname+"' target=_blank>"+item.wxname+"</a></td>"
  content += "<td><div id='bb"+item.id+"'>"
  content += getcon(item.id,item.srcflag);
  content += "<input type='button' value='删除' onclick='changeTitle("+item.id+",1,"+item.srcflag+",-1)'>"
  content += "</div></td>"
  content += "</tr>"
 }
 
 tag.innerHTML=content;

}

function changePublic(wxHao,status)
{
alert('ddd');
 var dataParam = "wxpublic.wxhao="+wxHao+"&wxpublic.status="+status;
 $.ajax({type:"post",url:"/boxsite/show_updatewp.do",data:dataParam,async:false});
 $('#tr'+wxHao).remove();
}

function changeTitle(id,flag,flagValue,status)
{
 var flagname = "srcflag"
 if (flag==2)
  flagname = "useflag"
 var dataParam = "wxtitle.id="+id+"&wxtitle."+flagname+"="+flagValue;
 if (status!=null)
  dataParam += "&wxtitle.status="+status;
 var data = $.ajax({type:"post",url:"/boxsite/show_updatetitle.do",data:dataParam,async:false});
var obj = cfeval(data.responseText);
if (obj.code==0&&status==null)
{
 var tag = document.getElementById("bb"+id);
 	tag.innerHTML = getcon(id,flagValue);
}else{
 //flush
	$('#tr'+id).remove();
}
}

function loadwps(type,status)
{
 var tagtitle = document.getElementById("titles");
 tagtitle.innerHTML = "";
 
 var ss = 1;
 if (status!=null)
  ss = status;
  
var dataParam="wxpublic.status="+ss+"&wxpublic.type="+type;
var data = $.ajax({type:"post",url:"/boxsite/show_wps.do",data:dataParam,async:false});
var obj = cfeval(data.responseText);
 var content = ""
   content += "         <thead><tr style='background:#128171;color:#ffffff;font-size:20px'>"
  content += "        <td style='border:1px solid'>公众名称</td>"
  content += "        <td style='border:1px solid'>公众号</td>"
  content += "        <td style='border:1px solid'>搜狗最近文章</td>"
  content += "        <td style='border:1px solid'>相似公众号</td>"
  content += "        <td style='border:1px solid'>操作</td>"
  content += "        <td style='border:1px solid'>阅读数</td>"
  content += "        <td style='border:1px solid'>排名估计</td>"
  content += "        <td style='border:1px solid'>查看</td>"
  content += "        </tr>"
  content += "        </thead>"
 for (var i=0;i<obj.length;i++)
 {
   content += "<tr style='font-size:18px;border:2px solid' id='tr"+obj[i].wxhao+"'>"
  content += "<td><a href='http://www.5118.com/weixin/detail?name="+obj[i].wxname+"' target=_blank>"+obj[i].wxname+"</a></td> "
  content += "<td><a href='http://www.newrank.cn/public/info/detail.html?account="+obj[i].wxhao+"' target=_blank>"+obj[i].wxhao+"</a></td> "
  if (obj[i].openid.length>0)
  content += "<td><a href='http://weixin.sogou.com/gzh?openid="+obj[i].openid+"' target=_blank>最近文章</a></td>";
  else
  content += "<td></td>";
  content += "<td><a href='http://weixin.sogou.com/weixin?fr=sgsearch&type=1&query="+obj[i].wxname+"' target=_blank>"+obj[i].wxhao+"</a></td>";
  content += "<td><input type='button' value='删除' onclick=\"changePublic('"+obj[i].wxhao+"',-1)\"></td> "
  content += "<td>"+obj[i].viewcount+"</td> "
  content += "<td>"+obj[i].topcount+"</td> "
  content += "<td><input type='button' value='查看' onclick=\"gettitles('"+obj[i].wxhao+"')\"></td>"
  content += "</tr>"  
 }
 var tag = document.getElementById("wps");
  tag.innerHTML=content;
}

function loadtypes(status)
{
	var data = $.ajax({type:"post",url:"/boxsite/show_types.do",async:false});
	var obj = cfeval(data.responseText);
 var content = ""
 for (var i=0;i<obj.length;i++)
 {
  content += "<input type='button' style='font-size:20px' value='"+obj[i].name+"' onclick='loadwps("+obj[i].type;
  if (status!=null)
   content += ","+status;
  content += ")'> "
 }
 
 var tag = document.getElementById("wptype");
 if (status!=null)
  tag = document.getElementById("wptypenotsearch");
  tag.innerHTML=content;
}

loadtypes();
loadtypes(0);
loadwps(1);

function testajax()
{

}