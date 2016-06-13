var g_currurls = {};
var g_sitekey = "";

function queryNews(catid)
{
	var dataParam = "cat="+catid;
	g_cat = catid;
	try    {
		$.ajax({type:"post",url:"/boxsite/news.jsp",data:dataParam,success:function(data){
		 queryUrlsRst(cfeval(data));
		}});
	}   catch  (e)   {
	   return false;
	}
}

function queryNewsRst(data)
{
	alert('aaa');
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