var g_currurls = {};
var g_sitekey = "";
var g_type = 0;

var g_newscats = [
"综合","业界","热点","政策","八卦","观点","访谈","专题","创新","产品","设计","运营","大公司",
"股价","人物","公司","项目","移动","O2O","电商","大数据","风投","融资","创业","无效"
];

function querySitekeys()
{
	var dataParam = "type=2";
	
	var data = $.ajax({type:"post",url:"/boxsite/trainingurls.jsp",data:dataParam,async:false});
	
	var keystr = cfeval(data.responseText);
	
	var content = ""
	for (var i=0;i<keystr.length;i++){
	var key = keystr[i];
	 content += "<a href=\"javascript:queryUrls('"+key+"',0)\">"+key+"</a> ";
	  if (i>0&&i%8==0)
	   content += "<br>";
	}
	var tag = document.getElementById("keysdiv");
	tag.innerHTML = content;
	
	content = "";
	for (var i=0;i<keystr.length;i++){
	var key = keystr[i];
	 content += "<a href=\"javascript:queryUrls('"+key+"',-1)\">"+key+"</a> ";
	  if (i>0&&i%8==0)
	   content += "<br>";
	}
	tag = document.getElementById("trainingkeysdiv");
	tag.innerHTML = content;
	
}

querySitekeys();

function queryUrls(sitekey,type)
{
	var dataParam = "sitekey="+sitekey;
	if (type!=null) {
	  g_type = type;
	 }
	  
	dataParam += "&type="+g_type;
	  
	g_sitekey = sitekey;
	try    {
		$.ajax({type:"post",url:"/boxsite/trainingurls.jsp",data:dataParam,success:function(data){
		 queryUrlsRst(cfeval(data));
		}});
	}   catch  (e)   {
	   return false;
	}
}

function queryUrlsRst(data)
{
	var tag = document.getElementById("siteurls");
	var content = "<table>";
	for (var i=0;i<data.length;i++){
	  var color = "#DDDDDD";
	  if (i%2==0)
	    color = "white";
	 content += "<tr style='font-size:23px;padding-top:5px;background:"+color+"'>"
//	 content += "<td width=680><a href='news.html?url="+data[i].url+"' target=_blank>"+data[i].text+"</a></td>"
	 content += "<td width=680><a href='dnagetter.html?url="+data[i].url+"' target=_blank>"+data[i].text+"</a></td>"
	 content += "<td><div style='font-size:21px'>"
	 
	 if (data[i].cat==0)
	 	content += "<input type='radio' name='url_"+data[i].id+"' id='0_"+data[i].id+"' value='0' checked><label for='0_"+data[i].id+"'>复位</lable>";
	 else
	 	 content += "<input type='radio' name='url_"+data[i].id+"' id='0_"+data[i].id+"' value='0'><label for='0_"+data[i].id+"'>复位</lable>";
	 
	 content += "<br>";
	 
	 for (var k=0;k<g_newscats.length;k++){
 	     var cat = k+1;
	     var id = cat+"_"+data[i].id;
	     
		 if (cat==data[i].cat)
		  content += "<input type='radio' name='url_"+data[i].id+"' id='"+id+"' value='"+cat+"' checked><label for='"+id+"'>"+g_newscats[k]+"</label>";
		 else
		  content += "<input type='radio' name='url_"+data[i].id+"' id='"+id+"' value='"+cat+"'><label for='"+id+"'>"+g_newscats[k]+"</label> ";
		  
		  if (cat%8==0)
		   content += "<br>";
	 }

	 content += "</div></td>"
	 content += "</tr>"
	}
	content += "</table>";
	tag.innerHTML = content;
	
	g_currurls = data;
}

function defineUrlCat()
{
	var catUrls = [];
	for (var i=0;i<g_currurls.length;i++){
	 var id = g_currurls[i].id;
	 var tags = document.getElementsByName("url_"+id);
	 var cat = 0;
	 for (var j=0;j<tags.length;j++){
		 if (tags[j].checked)
		   cat = tags[j].value;
	  }
	  if (cat>0){
		  g_currurls[i].cat = cat;
		  var item = {cat:g_currurls[i].cat,url:g_currurls[i].url};
		  catUrls.push(item);
	  }
	}
	
	var strurls = JSON.stringify(catUrls);
	var dataParam = "type=1&sitekey="+g_sitekey+"&siteurls="+strurls;
	
	try    {
		$.ajax({type:"post",url:"/boxsite/trainingurls.jsp",data:dataParam,success:function(data){
		 queryUrls(g_sitekey);
		}});
	}   catch  (e)   {
	   return false;
	}
}