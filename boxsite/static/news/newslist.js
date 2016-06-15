
var g_currcat = 0;
var g_lastqueryup = {};
var g_lastquerydown = {};

var g_newscats = [
[1,'热点'],
[2,'业界'],
[3,'产品'],
[4,'创投'],
];

var g_sitekeys = {
"huxiu.com":"虎嗅",
"iwacai.com":"爱挖柴",
"iheima.com":"i黑马"
};

newslist = function(options){
}

newslist.prototype = {
 //alert（'www');
 loaded:true,
 
  init: function(){
   var content = "";
   for (var i=0;i<g_newscats.length;i++){
    var list = g_newscats[i];
   content += "<li><a href='javascript:g_newslist.getlist("+list[0]+")'>"+list[1]+"</a></li> "
   }
   var tag = document.getElementById("nav");
   tag.innerHTML = content;
  },
  getlist: function (catid,starttime) {
    var stime = 0;
    if (starttime!=null&&starttime>0)
     stime = starttime;
	var dataParam = "cat="+catid+"&starttime="+stime;
	try    {
		$.ajax({type:"post",url:"/boxsite/news.jsp",data:dataParam,success:function(data){
		var jsonstr = cfeval(data);
		g_newslistview.additem(1,jsonstr);
		}});
	}   catch  (e)   {
	}   
  },
  
 
  getnews: function (itemid) {
	var dataParam = "item="+itemid;
	try    {
		$.ajax({type:"post",url:"/boxsite/news.jsp",data:dataParam,success:function(data){
		var jsonstr = cfeval(data);
		g_newslistview.viewitem(jsonstr);
		}});
	}   catch  (e)   {
	}   
  }  
}


newslistview = function(options){
}

newslistview.prototype = {
 
    additem:function(id,jsonText) {  
    	var newslist = jsonText.news;  
        var ul= document.getElementById('thelist');
        for (var i=0;i<newslist.length;i++){
        	var item = newslist[i];
        	var li= document.createElement("li"); 
        	var shorttitle = item.content.substring(0,10);   
        	var content = "";
        	//content += "<div>";
        	content += "<a href='javascript:g_newslist.getnews("+item.id+")'>"+shorttitle+"</a><br>";
        	//content += "来源:"+g_sitekeys[item.sitekey];
        	//content += "</div>";
            li.innerHTML=content;  
            li.id=id;  
            ul.appendChild(li);           
        }
 
    },
    
    viewitem:function(item) {  
    	//alert(item.content);
    },    
}

var g_newslist = new newslist();

var g_newslistview = new newslistview();

g_newslist.init();