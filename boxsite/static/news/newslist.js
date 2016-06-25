
var g_currcat = 1;
var g_lastqueryup = {};
var g_lastquerydown = {};
var g_movestartx = 0;
var g_moveendx = 0;
var g_movestarty = 0;
var g_moveendy = 0;
var g_moveflag = false;

function moveDirection(startX,startY,endX,endY){
	//阀值:
  var validLen = 80;
  var invalidLen = 50;
  if (endY-startY>validLen&&Math.abs(endX-startX)<invalidLen)
  	return 0;		//up;
  else if (startY-endY>validLen&&Math.abs(endX-startX)<invalidLen)
  	return 1;		//down;
  else if (startX-endX>validLen&&Math.abs(endY-startY)<invalidLen)
  	return 2;		//left;
 else if (endX-startX>validLen&&Math.abs(endY-startY)<invalidLen)
  	return 3;		//right;
}

user = function(options){

}

user.prototype = {
	userdata:{},
	login:function(){
	  var sessionid = 0;
	  var userdata = store.get("newsuser");
	  if (userdata!=null){
	    sessionid = userdata.sessionid;
	  };
	  
	var dataParam = "sessionid="+sessionid;
	try    {
		$.ajax({type:"post",url:"user.jsp",data:dataParam,success:function(data){
		var jsonstr = cfeval(data);
		g_user.userdata = jsonstr;
		store.set("newsuser",jsonstr);
		}});
	}   catch  (e)   {
	} 	  
	},
}

newslist = function(options){
 this.data = {};
 this.name = "newslist";
 var data = store.get(this.name);
 if (data!=null){
   this.data = data;
 }else {
   store.set(this.name,this.data);
 }
}

newslist.prototype = {
 loaded:true,
 
  init: function(){
   var catid = getPar("catid");
   if (catid){
     g_currcat = parseInt(catid);
   }else
     g_currcat = 1;
     
    var from = getPar("from");
    if (from){
     g_lastqueryup[g_currcat] = 0;
    }
    
   var content = "";
   for (var i=0;i<g_newscats.length;i++){
    var list = g_newscats[i];
   content += "<li id='navlist"+list[0]+"'";
   if (list[0]==g_currcat)
    content += " style='display:inline-block;background-color:#0095BB;height:60px'";
   content += "><a href='javascript:g_newslist.getlist("+list[0]+")'>"+list[1]+"</a></li> "
   }
   var tag = document.getElementById("nav");
   tag.innerHTML = content;
   
   var d = new Date();
   //g_lastqueryup[g_currcat] = d.getTime();
   
   var obj = document.getElementById('wrapper');
	obj.addEventListener('touchstart', function(event) {
	     // 如果这个元素的位置内只有一个手指的话
	    if (event.targetTouches.length == 1) {	        	        
	　　　　 event.preventDefault();// 阻止浏览器默认事件，重要
	     g_movestartx = event.touches[0].clientX;
	     g_movestarty = event.touches[0].clientY;
	        }
	}, false);   
	obj.addEventListener('touchend', function(event) {
		if (!g_moveflag) return;
		
	     // 如果这个元素的位置内只有一个手指的话
	　　　　 event.preventDefault();// 阻止浏览器默认事件，重要
	     g_moveendx = event.changedTouches[0].clientX;
	     g_moveendy = event.changedTouches[0].clientY;
	       var moveDir = moveDirection(g_movestartx,g_moveendx,g_movestarty,g_moveendy);
	       if (moveDir==2){
	        g_newslist.getlist(g_currcat+1);
	       }else if (moveDir==3){
	        g_newslist.getlist(g_currcat-1);
	       }
	       
	     g_moveflag = false;
	}, false);   
	obj.addEventListener('touchmove', function(event) {
	     // 如果这个元素的位置内只有一个手指的话
	    if (event.targetTouches.length == 1) {
	　　　　 event.preventDefault();// 阻止浏览器默认事件，重要 
	        var touch = event.targetTouches[0];
	        g_moveflag = true;
	        }
	}, false);
	
	obj.onmousedown = function(event){
	 g_movestartx = event.clientX;
	 g_movestarty = event.clientY;
	};	
	
	obj.onmouseup = function(event){
	 g_moveendx = event.clientX;
	 g_moveendy = event.clientY;	
	       var moveDir = moveDirection(g_movestartx,g_moveendx,g_movestarty,g_moveendy);
	       if (moveDir==2){
	        g_newslist.getlist(g_currcat+1);
	       }else if (moveDir==3){
	        g_newslist.getlist(g_currcat-1);
	       }	 
	 
	 g_moveflag = false;
	};	
	
	obj.onmousemove = function(event){
	 g_moveflag = true;
	};	
  },
  getlist: function (catid,starttime) {
    if (catid<=0) return;
    
    if (!g_currcat)
      g_currcat = 1;
    var lasttag = document.getElementById('navlist'+g_currcat);
    lasttag.style.backgroundColor = "";
    
    var currtag = document.getElementById('navlist'+catid);
    currtag.style.display = "inline-block";
    currtag.style.backgroundColor = "#0095BB";
    
    g_currcat = catid;
    
    var ul= document.getElementById('thelist');
    ul.innerHTML = "<div style='width:100px;height:100px;align:center' id='orderlist_wait'><img src='static/img/w1.gif'></div>";
    
    var stime = 0;
    if (starttime!=null&&starttime>0)
     stime = starttime;
	var dataParam = "cat="+catid+"&starttime="+stime;
	try    {
		$.ajax({type:"post",url:"newslist.jsp",data:dataParam,success:function(data){
		var jsonstr = cfeval(data);
		g_newslistview.additem(1,jsonstr);
		}});
	}   catch  (e)   {
	}   
  },
  
  getcurrlist: function () {
   g_newslist.getlist(g_currcat,g_lastqueryup[g_currcat]);
  },  
 
  getnewscount: function () {
	var dataParam = "type=2&cat="+g_currcat+"&starttime="+g_lastqueryup[g_currcat];
	try    {
		$.ajax({type:"post",url:"newslist.jsp",data:dataParam,success:function(data){
		var jsonstr = cfeval(data);
		var count = parseInt(jsonstr);
		}});
	}   catch  (e)   {
	}   
  },
     
  getnews: function (itemid) {
	var dataParam = "item="+itemid;
	try    {
		$.ajax({type:"post",url:"news.jsp",data:dataParam,success:function(data){
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
        ul.innerHTML = "";
        for (var i=0;i<newslist.length;i++){
        	var item = newslist[i];
        	var dd = new Date(item.contentTime);
        	var timeStr = (dd.getMonth()+1)+"-"+dd.getDate();
        	var li= document.createElement("li"); 
        	var shorttitle = item.ctitle;
        	var link = "news.html?itemid="+item.id+"&catid="+item.cat;
        	  if (shorttitle.length>20)
        	    shorttitle = shorttitle.substring(0,20)+"..";   
        	var content = "";
        	content += "<div onclick=\"window.open('"+link+"','_self')\">";
        	content += "<span>"
//        	content += "<a href='javascript:g_newslist.getnews("+item.id+")'>"+shorttitle+"</a>"
        	content += "<a href='"+link+"'>"+shorttitle+"</a>"
        	content += "</span><br>";
        	content += "<span style='font-size:10px'>";
        	content += g_sitekeys[item.sitekey]+" "+timeStr;
        	content += "</span>"
        	content += "</div>";
            li.innerHTML=content;  
            li.style.height = "90px";
            li.id=id;  
            ul.appendChild(li);           
        }
 
    },
    
    viewitem:function(item) {  
    	//alert(item.content);
    },    
}

var g_user = new user();
g_user.login();

var g_newslist = new newslist();

var g_newslistview = new newslistview();

g_newslist.init();
g_newslist.getcurrlist();
g_newslist.getnewscount();