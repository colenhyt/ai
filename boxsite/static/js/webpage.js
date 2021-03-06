Webpage = function(){
 this.name = "webpage";
}

Webpage.prototype.init = function(){

}

function rankonfocus(index){
 var tags = document.getElementsByName("myranks");
 tags[index].focus();
 tags[index].select();
}

Webpage.prototype.fillItems2 = function(sitelist){
 var content = "<table>";
 for (var i=0;i<sitelist.length;i++){
  var url = sitelist[i];
  if (url.indexOf("http")<0)
   url = "http://"+url;
  content += "<tr>";
  content += "<td><a href='"+url+"' target=blank>"+url+"</a></td>";
  content += "</tr>";
  }
 content += "</table>";
 var tag = document.getElementById("searchrst");
 tag.innerHTML = content;
}

Webpage.prototype.fillItems = function(sitelist)
{
 var content = "<table>";
 for (var i=0;i<sitelist.length;i++){
  var item = sitelist[i];
  var url = item.url;
  if (url!=null&&url.indexOf("http")<0)
   url = "http://"+item.url;
  var name = item.name;
  content += "<tr>";
  if (name==null)
   name = item.url;
  var myrank = item.myrank;
  if (myrank==null)
   myrank = -1;
   if (name.length>60)
    name = name.substring(0,60);
  var alexa = item.alexa;
  if (alexa==null)
   alexa = -1;
  var bdrank = item.bdrank;
  if (bdrank==null)
   bdrank = -1;
   
  content += "<td><a href='"+url+"' target=blank>"+name+"</a></td>";
  content += "<td>alexa:"+alexa+"</td>";
  content += "<td>bdrank:"+bdrank+"</td>";
//  content += "<td><a href='javascript:g_webpage.deleteWord("+item.wordid+","+item.siteid+")'>删除该关键字关联</a></td>";
//  content += "<td><input name='myranks' type='input' size=10 value='"+myrank+"' onfocus='rankonfocus("+i+")'/></td>";
  content += "<td><a href='dnagetter.html?url="+url+"' target=_blank>定义特征</a></td>";
  content += "<td><input name='siteids' type='hidden' value='"+item.siteid+"' /></td>";
  
  content += "</tr>"
 }
 content += "<tr><td><input type='button' onclick='g_webpage.updateMyrank()' value='更改'/>"
 content += "</table>";
 var tag = document.getElementById("searchrst");
 tag.innerHTML = content;
}

Webpage.prototype.load = function(){
 var word = getPar("word");
 var wordlist = getPar("wordlist");
 var dataParam = "word="+word;
 if (wordlist!=null)
  dataParam = "wordlist="+wordlist;
  
 this.queryData(dataParam);
 
 var tag = document.getElementById("searchword");
 tag.value=word;
}

Webpage.prototype.queryData = function(dataParam){
	try    {
		$.ajax({type:"post",url:"/boxsite/search.jsp",data:dataParam,success:function(data){
		var itemlist = cfeval(data);
//		if (itemlist==null||itemlist.length<=0){
//		 g_webpage.queryPerTime(dataParam);
//		}else 
		{
		 g_webpage.fillItems(itemlist);
		 }
		}});
	}   catch  (e)   {
	   return false;
	} 
}

Webpage.prototype.updateMyrank = function()
{
 var tags = document.getElementsByName("myranks");
 var siteidtags = document.getElementsByName("siteids");
 var myranks = [];
 for (var i=0;i<tags.length;i++){
  var rank = tags[i].value;	
  var site = {siteid:siteidtags[i].value,myrank:rank};
  myranks.push(site);
 }

 var dataParam = "type=1&rankstr="+JSON.stringify(myranks);
 alert(dataParam);
 
	try    {
		$.ajax({type:"post",url:"/boxsite/data_update.jsp",data:dataParam,success:function(data){
		{
		 var ret = cfeval(data);
		}
		}});
	}   catch  (e)   {
	   return false;
	} 
}

Webpage.prototype.queryPerTime = function(dataParam)
{

  	var content = "<div style='width:100px;height:100px;align:center' id='orderlist_wait'><img src='static/img/w1.gif'></div>";
  	content +="<div style='text-align:center' id='orderlist_msg'><br>搜索中....</div>";
  	
 var tag = document.getElementById("searchrst");
 tag.innerHTML = content;
   	
// this.dataParam = dataParam;
//	setInterval(function(){
//	   g_webpage.queryData(g_webpage.dataParam);
//	  },2000
//	); 
}

Webpage.prototype.deleteWord = function(wordid,siteid)
{
	var dataParam = "siteid="+siteid+"&wordid="+wordid;
	try    {
		$.ajax({type:"post",url:"/boxsite/data_update.jsp",data:dataParam,success:function(data){
		 location.reload();
		}});
	}   catch  (e)   {
	   return false;
	}
}

var g_webpage = new Webpage();
g_webpage.load();
