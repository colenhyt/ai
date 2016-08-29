var g_sitekey = "";
var g_type = 0;

var g_newscats = [
"政策",
"汽车","VR","直播","电脑","手机","硬件","极客","移动","游戏","O2O","电商","大数据","软件",
"风投","融资","股价","创业","创新","互联网金融","互联网设计","互联网运营",
"经营","专题","互联网","运营商","公司","人物",
"效率","管理","科技",
"无效","综合"
];

var g_cat = null;

function printCatstrs()
{
	//分类名:
	content = "";
	 for (var k=0;k<g_newscats.length;k++){
	  var cat = k+1;
	  content += "<a href=\"javascript:queryWords('"+g_newscats[k]+"')\">"+g_newscats[k]+"</a> ";
	  if (k>0&&k%10==0)
	   content += "<br>";
	 }
	 tag = document.getElementById("catstr");
	tag.innerHTML = content;
	
	tag = document.getElementById("catselect");
	for (var k=0;k<g_newscats.length;k++){
    tag.options.add(new Option(g_newscats[k],g_newscats[k])); //这个兼容IE与firefox  
    }
}

printCatstrs();

function queryWords(catstr)
{
	var dataParam = "&cat="+catstr;
	  
	var tag = document.getElementById("catselect");
	for (var i=0;i<tag.options.length;i++){
	  if (tag.options[i].value==catstr){
	   tag.options[i].selected = true;
	   break;
	  }
	}
	
	g_cat = catstr;
	
	try    {
		$.ajax({type:"post",url:"trainingwords.jsp",data:dataParam,success:function(data){
		 queryWordsRst((data));
		}});
	}   catch  (e)   {
	   return false;
	}
}

function queryWordsRst(data)
{
	var tag = document.getElementById("words");
	var content = "<table width=900>";
	 content += "<tr style='font-size:25px;padding-top:5px;'>";
	 content += "<td>";
	 var titlewords = data.split("\n");
	 for (var j=0;j<titlewords.length;j++){
	   var words = titlewords[j].split(",");
	for (var i=0;i< words.length;i++){
	var kk = j*i+i;
	 content += "<input type='checkbox' name='wordword' id='"+kk+"_"+words[i]+"' value='"+words[i]+"'><label for='"+kk+"_"+words[i]+"'>"+words[i]+"</label> ";
	}
	   content += "<br>";
	 }
	 content += "</td>";
	 content += "<tr>";
	content += "</table>";
	tag.innerHTML = content;
	
}

function defineWordLevel(wordLevel)
{
	var catWords = [];
	 var tags = document.getElementsByName("wordword");
	 for (var j=0;j<tags.length;j++){
		 if (tags[j].checked){
		   catWords.push(tags[j].value);
		  }
	  }
	
	var tag = document.getElementById("catselect");
	var ii = tag.selectedIndex;
	var scatstr = tag.options[ii].value;
	
	var strurls = JSON.stringify(catWords);
	var dataParam = "type=2&cat="+scatstr+"&level="+wordLevel+"&catwords="+strurls;
	
	try    {
		$.ajax({type:"post",url:"/boxsite/trainingwords.jsp",data:dataParam,success:function(data){
		 queryWords(g_cat);
		}});
	}   catch  (e)   {
	   return false;
	}
}