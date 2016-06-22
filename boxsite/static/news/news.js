
news = function(options){
}

news.prototype = {

  init:function(){
   var itemid = getPar("itemid");
    var url = getPar("url");
  	 g_news.getnews(itemid,url);
  },
 
  getnews: function (itemid,url) {
	var dataParam = "item="+itemid;
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
    content += "<h>"+item.ctitle+"</h>";
    content += "<div>来源:"+g_sitekeys[item.sitekey]+"</div>";
    content += "<div>"+item.htmlContent+"</div>";
    
    var tag = document.getElementById("content");
    tag.innerHTML = content;
  },
  
  
}



var g_news = new news();
g_news.init();
