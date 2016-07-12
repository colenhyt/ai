
function queryTestUrls(){
 var url = getPar("url");
 
	var dataParam = "url="+url;
	try    {
		$.ajax({type:"post",url:"dna.jsp",data:dataParam,success:function(data){
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
	 content += "<td width=480><a href='"+data[i]+"' target=_blank>"+urlText+"</a></td>"
	 content += "<td><div style='font-size:21px'>"
	 
	 //url reg:
	 	content += "urlReg:<input type='text' id='url_"+url+"' value='"+url+"' size=20>";
	 	content += "<input type='button' onclick=\"addItemUrlReg('"+url+"')\" value='提交'> ";
	 //tag dna:
	 	content += "tagKey:";
	 	content += "<select id='tagtype_"+url+"' style='width:10x'>";
	 	content += "<option value='0'>TAG</option>"
	 	content += "<option value='1'>TAG_KEY</option>"
	 	content += "<option value='2' selected>TAG_PROP</option>"
	 	content += "<option value='3'>TAG_PROP_KEY</option>"
	 	content += "<option value='4'>STARTTAG_ENDTAG</option>"
	 	content += "</select>";
	 	content += "<input type='text' id='tag_"+url+"' size=20>";
	 	content += "<input type='button' onclick=\"addTagDna('"+url+"')\" value='提交'> ";


	 content += "</div></td>"
	 content += "</tr>"
	}
	content += "</table>";
	tag.innerHTML = content;
	
	g_currurls = data;
}


function addItemUrlReg(url)
{
	
	var tag = document.getElementById("url_"+url);
	var reg = tag.value;
	var patt1=new RegExp(reg);
	var isReg = patt1.test(url);
	if (!isReg){
	 alert("表达式不匹配");
	 return;
	}

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
	var tag0 = document.getElementById("tagtype_"+url);
	
	var tagtype = tag0.options.selectedIndex;
	var tag = document.getElementById("tag_"+url);
	var str= tag.value.split(",");
	var dna = {};
	for (var i=0;i<str.length;i++){
	  var tt = str[i].split(":");
	  dna[tt[0]] = tt[1]; 
	}
	var dnaStr = JSON.stringify(dna);
	var dataParam = "url="+url+"&tagtype="+tagtype+"&tagdna="+dnaStr;
	
	try    {
		$.ajax({type:"post",url:"dna.jsp",data:dataParam,success:function(data){
		 var json = cfeval(data);
		}});
	}   catch  (e)   {
	   return false;
	}
}

queryTestUrls();