
news = function(options){
}

var g_catid = 1;

news.prototype = {

  init:function(){
    var catid = getPar("catid");
   if (catid!=null)
     g_catid = catid;
   var itemid = getPar("itemid");
    var url = getPar("url");
  	 g_news.getnews(itemid,url);
  	 
  	 var tag = document.getElementById("backnav");
  	 tag.href="index-app.html?catid="+g_catid;
  },
 
  getnews: function (itemid,url) {
	var dataParam = "itemid="+itemid;
	if (!itemid&&url)
	  dataParam = "url="+url;
	  
	try    {
		$.ajax({type:"post",url:"/boxsite/news.jsp",data:dataParam,success:function(data){
		var jsonstr = cfeval(data);
		g_news.viewitem(jsonstr);
		}});
	}   catch  (e)   {
	}   
  },
     
  viewitem: function (item) {
    var content = "";
    var dd = new Date(item.contentTime);
    var timeStr = dd.Format("yyyy-MM-dd hh:mm:ss");
    content += "<h style='font-size:35px;font-weight:bold;'>"+item.ctitle+"</h>";
    content += "<div style='font-size:12px;padding-top:7px'>来源:"+g_sitekeys[item.sitekey]+" 时间:"+timeStr+"</div>";
    content += "<div>"+item.htmlContent+"</div>";
    
    var tag = document.getElementById("content");
    tag.innerHTML = content;
  },
  
  
}



var g_news = new news();
g_news.init();
