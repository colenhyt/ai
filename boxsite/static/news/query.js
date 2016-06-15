var g_currurls = {};
var g_sitekey = "";



alert(g_sitekeys['huxiu.com']);

function queryNews(catid)
{
	var dataParam = "cat="+catid;
	g_cat = catid;
	try    {
		$.ajax({type:"post",url:"/boxsite/news.jsp",data:dataParam,success:function(data){
		 queryNewsRst(cfeval(data));
		}});
	}   catch  (e)   {
	   return false;
	}
}

function queryNewsRst(data)
{
  var news = data;
	alert('aaa'+news.cat);
}
