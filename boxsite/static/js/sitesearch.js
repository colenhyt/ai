Sitesearch = function(){
 this.name = "Sitesearch";
}

Sitesearch.prototype.init = function(){

}

Sitesearch.prototype.searchrst = function(sitelist)
{
 var content = "<table>";
 for (var i=0;i<sitelist.length;i++){
  var item = sitelist[i];
  var url = item.url;
  if (url.indexOf("http")<0)
   url = "http://"+item.url;
  var name = item.name;
  if (name==null)
   name = item.url;
  content += "<a href='"+url+"' target=blank>"+name+"</a><br>";
 }
 content += "</table>";
 var tag = document.getElementById("searchrst");
 tag.innerHTML = content;
}

Sitesearch.prototype.search = function()
{
      var tag = document.getElementById("searchword");
	  var word = tag.value;	
	var dataParam = "word="+word;
	
	try    {
		$.ajax({type:"post",url:"/boxsite/search.jsp",data:dataParam,success:function(data){
		 g_sitesearch.searchrst(cfeval(data));
		}});
	}   catch  (e)   {
	   return false;
	}
}

Sitesearch.prototype.searchByid = function(wordid)
{
	var dataParam = "wordid="+wordid;
	
	try    {
		$.ajax({type:"post",url:"/boxsite/search.jsp",data:dataParam,success:function(data){
		 g_sitesearch.searchrst(cfeval(data));
		}});
	}   catch  (e)   {
	   return false;
	}
}

var g_sitesearch = new Sitesearch();
