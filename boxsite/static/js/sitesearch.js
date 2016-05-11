Sitesearch = function(){
 this.name = "Sitesearch";
}

Sitesearch.prototype.init = function(){

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
