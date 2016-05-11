Main = function(){
 this.name = "Main";
}

Main.prototype.init = function(){

}

Main.prototype.fillWords = function(wordlist)
{
 var content = "";

 for (var i=0;i<wordlist.length;i++){
  var item = wordlist[i];
  content += "<a href='javascript:g_sitesearch.searchByid("+item.wordid+")'>"+item.word+"</a>,";
  if (i%8==0)
    content += "<br>";
 }
      var tag = document.getElementById("hotwords");
	  tag.innerHTML=content;	
}

Main.prototype.findWords = function()
{
	var dataParam = "type=1";
	try    {
		$.ajax({type:"post",url:"/boxsite/data_get.jsp",data:dataParam,success:function(data){
		var datas = cfeval(data);
		g_main.fillWords(datas);
		}});
	}   catch  (e)   {
	   return false;
	}
}

var g_main = new Main();
g_main.findWords();

