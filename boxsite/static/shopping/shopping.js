
shopping = function(options){
}

shopping.prototype = {
 //alert（'www');
 loaded:true,
 
  getdata: function (typeid) {
	var dataParam = "typeid="+typeid;
	try    {
		$.ajax({type:"post",url:"/boxsite/shopping_get.jsp",data:dataParam,success:function(data){
		var jsonstr = cfeval(data);
		g_shoppingview.additem(1,jsonstr);
		}});
	}   catch  (e)   {
	}   
  }
}


shoppingview = function(options){
}

shoppingview.prototype = {
 
    additem:function(id,jsonText) {    
        var ul= document.getElementById('thelist');
        for (var i=0;i<jsonText.length;i++){
        	var item = jsonText[i];
        	var li= document.createElement("li");    
        	var content = "<a href='http://"+item.urlstr+"'>"+item.ctitle+"</a>";
            li.innerHTML=content;  
            li.id=id;  
            ul.appendChild(li);           
        }
 
    }
}

var g_shopping = new shopping();

var g_shoppingview = new shoppingview();
g_shopping.getdata(1);

