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
  content += "<a href='/boxsite/webpage.html?word="+item.word+"'>"+item.word+"</a>,";
  if (i%8==0)
    content += "<br>";
 }
      var tag = document.getElementById("hotwords");
	  tag.innerHTML=content;	
}

Main.prototype.fillWords2 = function(wordlist)
{
 var content = "";

 for (var i=0;i<wordlist.length;i++){
  var item = wordlist[i];
  content += "<a href='/boxsite/webpage.html?word="+item+"'>"+item+"</a>,";
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
		g_main.fillWords2(datas);
		}});
	}   catch  (e)   {
	   return false;
	}
}


Main.prototype.fillWordlist = function(wordlist)
{
 var content = "";

 for (var i=0;i<wordlist.length;i++){
  var item = wordlist[i];
  content += "<a href='/boxsite/webpage.html?wordlist="+item+"'>"+item+"</a>,";
  if (i%8==0)
    content += "<br>";
 }
      var tag = document.getElementById("hotwordlist");
	  tag.innerHTML=content;	
}

Main.prototype.findWordlist = function()
{
	var dataParam = "type=2";
	try    {
		$.ajax({type:"post",url:"/boxsite/data_get.jsp",data:dataParam,success:function(data){
		var datas = cfeval(data);
		g_main.fillWordlist(datas);
		}});
	}   catch  (e)   {
	   return false;
	}
}

var g_main = new Main();
g_main.findWords();
g_main.findWordlist();

