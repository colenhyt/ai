
function queryTestUrls(){
 var url = getPar("url");
 
	var dataParam = "url="+url;
	try    {
		$.ajax({type:"post",url:"favor.jsp",data:dataParam,success:function(data){
		queryUrlsRst(cfeval(data));
		}});
	}   catch  (e)   {
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
	 var url = data[i];
	 var urlText = url;
	 content += "<tr style='font-size:23px;padding-top:5px;background:"+color+"'>"
	 content += "<td width=680><a href='"+data[i]+"' target=_blank>"+urlText+"</a></td>"
	 content += "<td><div style='font-size:21px'>"
	 
	 //url reg:
	 	content += "正则表达式: <input type='text' name='url_"+url+"' id='0_"+url+"' value='' size=20>";
	 	content += "<input type='button' onclick=\"addItemUrlReg('"+url+"')\"> ";
	 //tag dna:
	 	content += "tag: <input type='text' name='tag_"+url+"' value='' size=20>";
	 	content += "<input type='button' onclick=\"addTagDna('"+url+"')\"> ";


	 content += "</div></td>"
	 content += "</tr>"
	}
	content += "</table>";
	tag.innerHTML = content;
	
	g_currurls = data;
}


function addItemUrlReg(url)
{
	var tag = document.getElementsByName("url_"+url);
	var reg = tag.value;

	var dataParam = "url="+url+"&reg="+reg;
	
	try    {
		$.ajax({type:"post",url:"dna.jsp",data:dataParam,success:function(data){
		 var json = cfeval(data);
		}});
	}   catch  (e)   {
	   return false;
	}
}


function addTagDna(url)
{
	var tag = document.getElementsByName("tag_"+url);
	var dnaStr = tag.value;

	var dataParam = "url="+url+"&tagdna="+dnaStr;
	
	try    {
		$.ajax({type:"post",url:"dna.jsp",data:dataParam,success:function(data){
		 var json = cfeval(data);
		}});
	}   catch  (e)   {
	   return false;
	}
}