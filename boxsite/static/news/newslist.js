
var g_currcat = 1;
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
     
    if (g_currcat<=0||g_currcat>g_newscats.length){
     g_currcat = 1;
    }
    
    var from = getPar("from");
    
   var content = "";
   for (var i=0;i<g_newscats.length;i++){
    var index = i+1;
    var list = g_newscats[i];
   content += "<li id='navlist"+index+"' ";
   if (index==g_currcat)
    content += " style='display:inline-block;background-color:#0095BB;height:60px;'";
   content += "><a href='javascript:g_newslist.viewlist("+index+")'>"+list+"</a></li> "
   }
   var tag = document.getElementById("nav");
   tag.innerHTML = content;
   
   var d = new Date();
   
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
	        g_newslist.viewlist(g_currcat+1);
	       }else if (moveDir==3){
	        g_newslist.viewlist(g_currcat-1);
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
	        g_newslist.viewlist(g_currcat+1);
	       }else if (moveDir==3){
	        g_newslist.viewlist(g_currcat-1);
	       }	 
	 
	 g_moveflag = false;
	};	
	
	obj.onmousemove = function(event){
	 g_moveflag = true;
	};	
  },
  viewlist: function (catid,dir) {
    if (catid<=0) return;
    
    if (!g_currcat)
      g_currcat = 1;
    var lasttag = document.getElementById('navlist'+g_currcat);
    if (lasttag!=null)
    lasttag.style.backgroundColor = "";
    
    var currtag = document.getElementById('navlist'+catid);
    if (currtag!=null){
     currtag.style.display = "inline-block";
     currtag.style.backgroundColor = "#0095BB";
    }
    
    g_currcat = catid;
    
    var ul= document.getElementById('thelist');
    if (ul!=null)
     ul.innerHTML = "";
    
    var items = this.data[catid];
    if (items!=null&&items.length>0){
     g_newslistview.renderlist(1,items);
     //this.querynewscount();
    }
    
    var m_dir = 1;
    if (dir!=null)
      m_dir = dir;
    
    if (m_dir==1){
		var pullDownEl = document.getElementById('pullDown');
		pullDownEl.className = 'loading';
		pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';				
		pullDownAction();
    }
 
  },
  
  viewfavors: function (catid) {
    var userdata = store.get("newsuser");
    var items = userdata[catid];
    if (items!=null)
     g_newslistview.renderlist(1,items);
  },  
  
  queryfavors:function(){
  	var userdata = g_user.userdata;
  	var sessionid = -1;
  	if (userdata!=null)
  	  sessionid = userdata.sessionid;
  	    
	var dataParam = "type=1&sessionid="+sessionid;
	try    {
		$.ajax({type:"post",url:"favor.jsp",data:dataParam,success:function(data){
		var jsonstr = cfeval(data);
		g_newslist.appendlist(catid,jsonstr.news,jsonstr.dir);
		}});
	}   catch  (e)   {
	}      
  },
  
  querylist:function(catid,dir){
  var itemid = 0;
  var items = this.data[catid];
  if (items!=null&&items.length>0){
   if (dir==1)
     itemid = items[0].id;
   else
     itemid = items[items.length-1].id;
  }
  if (itemid==0)
  	dir = -1;
  	
  	var userdata = g_user.userdata;
  	var sessionid = -1;
  	if (userdata!=null)
  	  sessionid = userdata.sessionid;
  	    	
//    var ul= document.getElementById('thelist');
//    ul.innerHTML = "<div style='width:100px;height:100px;align:center' id='orderlist_wait'><img src='static/img/w1.gif'></div>";
    
	var dataParam = "sessionid="+sessionid+"&cat="+catid+"&itemid="+itemid+"&dir="+dir;
	try    {
		$.ajax({type:"post",url:"newslist.jsp",data:dataParam,success:function(data){
		var jsonstr = cfeval(data);
		g_newslist.appendlist(catid,jsonstr.news,jsonstr.dir);
		}});
	}   catch  (e)   {
	}     
  },

  appendlist:function(catid,newlist,dir){
    if (newlist==null||newslist.length<=0){
      return;
    }
    
    var list = this.data[catid];
   if (list==null||list.length==0)
    list = [];
    
    if (dir==1)	{
    	for (var i=newlist.length-1;i>=0;i--){
    	  list.splice(0,0,newlist[i]);
    	}
    }else {			//放到后面
	    for (var i=0;i<newlist.length;i++){
	     list.push(newlist[i]);
	    }
    }
    
	this.data[catid] = list;
	store.set(this.name,this.data);
	
	g_newslistview.addlist(dir,list);
   
  },
    
  querynewscount: function () {
	var dataParam = "type=2&cat="+g_currcat;
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
 	addlist:function(dir,newslist){
        var ul= document.getElementById('thelist');
        //头插入
        if (dir==1){
          var e = ul.firstChild();
          for (var i=newslist.length-1;i>=0;i++){
           var newElement = this.itemElement(newslist[i]);
           ul.insertBefore(newElement,e);
           e = newElement;
          }
        }else {
        	for (var i=0;i<newslist.length;i++){
        	 var newElement = this.itemElement(newslist[i]);
        	 ul.appendChild(newElement);
        	}
        }
 	},
 	
    renderlist:function(id,newslist) {  
      
        var ul= document.getElementById('thelist');
        ul.innerHTML = "";
        for (var i=0;i<newslist.length;i++){
            var e = this.itemElement(newslist[i]);  
            ul.appendChild(e);           
        }
 
    },
    
    viewitem:function(item) {  
    	//alert(item.content);
    },    
	
 	itemElement:function(item){
       	var dd = new Date(item.contentTime);
    	var timeStr = (dd.getMonth()+1)+"-"+dd.getDate();
    	var shorttitle = item.ctitle;
    	var link = "news.html?itemid="+item.id+"&catid="+item.cat;
    	  if (shorttitle.length>20)
    	    shorttitle = shorttitle.substring(0,20)+"..";   
    	var content = "";
    	content += "<div onclick=\"window.open('"+link+"','_self')\">";
    	content += "<span style='font-size:150%;font-weight:bold'>"
    	content += "<a href='"+link+"'>"+shorttitle+"</a>"
    	content += "</span><br>";
    	content += "<span style='font-size:50%'>";
    	content += g_sitekeys[item.sitekey]+" "+timeStr;
    	content += "</span>"
    	content += "</div>"; 	
    	var li= document.createElement("li"); 
        li.innerHTML= content;  
        li.style.height = "90px";
        li.id = item.id;  
    	return li;
 	},
 }

var g_newslist = new newslist();

var g_newslistview = new newslistview();

g_newslist.init();
g_newslist.viewlist(g_currcat);
