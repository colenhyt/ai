
news = function(options){
}

news.prototype = {

  init:function(){
   var itemid = getPar("itemid");
   
   g_news.getnews(itemid);
  },
 
  getnews: function (itemid) {
	var dataParam = "item="+itemid;
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
    content += "<div>"+item.content+"</div>";
    
    var tag = document.getElementById("content");
    tag.innerHTML = content;
  },
  
  
}



var g_news = new news();
g_news.init();
