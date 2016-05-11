Webpage = function(){
 this.name = "webpage";
}

Webpage.prototype.init = function(){

}

var g_webpage = new Webpage();

function deleteWord(wordid,siteid)
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