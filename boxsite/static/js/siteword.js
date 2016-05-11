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