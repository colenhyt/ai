var g_currurls = {};
var g_sitekey = "";

function queryUrls(sitekey)
{
	var dataParam = "sitekey="+sitekey;
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
	 content += "<tr style='font-size:25px;padding-top:5px;background:"+color+"'>"
	 content += "<td width=500><a href='"+data[i].url+"' target=_blank>"+data[i].text+"</a></td>"
	 content += "<td>"
	 if (data[i].cat==1)
	  content += "<input type='radio' name='url_"+data[i].id+"' value='1' checked>热点 ";
	 else
	  content += "<input type='radio' name='url_"+data[i].id+"' value='1'>热点 ";
	content += "&nbsp;&nbsp;";
	
	 if (data[i].cat==2)
	 content += "<input type='radio' name='url_"+data[i].id+"' value='2' checked>业界";
	 else
	 content += "<input type='radio' name='url_"+data[i].id+"' value='2'>业界";
	content += "&nbsp;&nbsp;";

	 if (data[i].cat==3)
	 content += "<input type='radio' name='url_"+data[i].id+"' value='3' checked>产品";
	 else
	 content += "<input type='radio' name='url_"+data[i].id+"' value='3'>产品";
	content += "&nbsp;&nbsp;";

	 if (data[i].cat==4)
	 content += "<input type='radio' name='url_"+data[i].id+"' value='4' checked>创投";
	 else
	 content += "<input type='radio' name='url_"+data[i].id+"' value='4'>创投";
	content += "&nbsp;&nbsp;";

	 if (data[i].cat==5)
	 content += "<input type='radio' name='url_"+data[i].id+"' value='5' checked>八卦";
	 else
	 content += "<input type='radio' name='url_"+data[i].id+"' value='5'>八卦";
	content += "&nbsp;&nbsp;";
	 content += "</td>"
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
		  catUrls.push(g_currurls[i]);
	  }
	}
	
	var strurls = JSON.stringify(catUrls);
	var dataParam = "type=1&sitekey="+g_sitekey+"&siteurls="+strurls;
	alert(dataParam);
	
	try    {
		$.ajax({type:"post",url:"/boxsite/trainingurls.jsp",data:dataParam,success:function(data){
		 
		}});
	}   catch  (e)   {
	   return false;
	}
}