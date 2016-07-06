
news = function(options){
 this.data = {};
 this.name = "news";
 var data = store.get(this.name);
 if (data!=null){
   this.data = data;
 }else {
   store.set(this.name,this.data);
 }
}

var g_catid = 1;
var g_currItemId = 0;

news.prototype = {

  init:function(){
    var catid = getPar("catid");
   if (catid!=null)
     g_catid = catid;
   var itemid = getPar("itemid");
   g_currItemId = itemid;
   
    var url = getPar("url");
  	 g_news.getnews(itemid,url);
  	 
  	 var tag = document.getElementById("backnav");
  	 tag.href="index-app.html?catid="+g_catid+"&from=1";
  },
 
  getnews: function (itemid,url) {
  	var item = this.data[itemid];
  	if (item!=null){
  	  this.viewitem(item);
  	  return;
  	}
  	
	var dataParam = "itemid="+itemid;
	if (!itemid&&url)
	  dataParam = "url="+url;
	  
	try    {
		$.ajax({type:"post",url:"news.jsp",data:dataParam,success:function(data){
		var item = cfeval(data);
		g_news.getnewsCallback(item);
		}});
	}   catch  (e)   {
	}   
  },
     
  getnewsCallback: function (item) {
  	if (item==null) {
  		return;
  	}
  	
  		this.data[item.id] = item;
  		store.set(this.name,this.data);
  		
		this.viewitem(item); 
  },
  
  add2Favor:function(){
  	var userdata = g_user.userdata;
  	var sessionid = -1;
  	if (userdata!=null)
  	  sessionid = userdata.sessionid;
	  
	  
    var data = store.get("newslist");
    var list = data[g_catid];
    if (list!=null){
      for (var i=0;i<list.length;i++){
        if (list[i].id==g_currItemId){
         g_user.addFavor(list[i]);
         break;
        }
      }
    }	  
	
  	  
 	  dataParam = "itemid="+g_currItemId+"&sessionid="+sessionid;
	try    {
		$.ajax({type:"post",url:"favor.jsp",data:dataParam,success:function(data){
		var item = cfeval(data);
		}});
	}   catch  (e)   {
	}  
  },
  
  viewitem: function (item) {
    var content = "";
    var dd = new Date(item.contentTime);
    var timeStr = dd.Format("yyyy-MM-dd hh:mm:ss");
    content += "<h style='font-size:200%;font-weight:bold;padding-top:10px;padding-left:10px'>"+item.ctitle+"</h>";
    content += "<div style='font-size:60%;padding-top:12px;padding-left:10px'>来源: "+g_sitekeys[item.sitekey]+" 时间: "+timeStr+"</div>";
    content += "<div style='padding-left:10px;font-size:100%'>"+item.htmlContent+"</div>";
    
    var tag = document.getElementById("content");
    tag.innerHTML = content;
  },
  
  
}



var g_news = new news();
g_news.init();
