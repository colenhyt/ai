
var g_currcat = 1;
var g_lastqueryup = {};
var g_lastquerydown = {};
var g_movestart = 0;
var g_moveend = 0;
var g_move = false;

var g_newscats = [
[1,'热点'],
[2,'市场'],
[3,'产品'],
[4,'公司'],
[5,'人物'],
[6,'创投'],
];

var g_sitekeys = {
"huxiu.com":"虎嗅",
"iwacai.com":"爱挖柴",
"iheima.com":"i黑马"
};

newslist = function(options){
}

newslist.prototype = {
 loaded:true,
 
  init: function(){
    
   var content = "";
   for (var i=0;i<g_newscats.length;i++){
    var list = g_newscats[i];
   content += "<li id='navlist"+(i+1)+"'";
   if (i==0)
    content += " style='display:inline-block;background-color:#0095BB;height:60px'";
   content += "><a href='javascript:g_newslist.getlist("+list[0]+")'>"+list[1]+"</a></li> "
   }
   var tag = document.getElementById("nav");
   tag.innerHTML = content;
   
   var obj = document.getElementById('wrapper');
	obj.addEventListener('touchstart', function(event) {
	     // 如果这个元素的位置内只有一个手指的话
	    if (event.targetTouches.length == 1) {	        	        
	　　　　 event.preventDefault();// 阻止浏览器默认事件，重要
	     g_movestart = event.touches[0].clientX;
	        }
	}, false);   
	obj.addEventListener('touchend', function(event) {
	     // 如果这个元素的位置内只有一个手指的话
	　　　　 event.preventDefault();// 阻止浏览器默认事件，重要
	     g_moveend = event.changedTouches[0].clientX;
	     var margin = (g_moveend-g_movestart);
	     if (g_move==true){
	       if (margin>0){
	        g_newslist.getlist(g_currcat-1);
	       }else if (margin<0){
	        g_newslist.getlist(g_currcat+1);
	       }
	     }
	       
	     g_move = false;
	}, false);   
	obj.addEventListener('touchmove', function(event) {
	     // 如果这个元素的位置内只有一个手指的话
	    if (event.targetTouches.length == 1) {
	        
	        
	　　　　 event.preventDefault();// 阻止浏览器默认事件，重要 
	        var touch = event.targetTouches[0];
	        g_move = true;
	        // 把元素放在手指所在的位置
	       // obj.style.left = touch.pageX-50 + 'px';
	        //obj.style.top = touch.pageY-50 + 'px';
	        }
	}, false);
  },
  getlist: function (catid,starttime) {
    if (catid<=0||catid>6) return;
    
    var lasttag = document.getElementById('navlist'+g_currcat);
    lasttag.style.backgroundColor = "";
    
    var currtag = document.getElementById('navlist'+catid);
    currtag.style.display = "inline-block";
    currtag.style.backgroundColor = "#0095BB";
    
    g_currcat = catid;
    
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