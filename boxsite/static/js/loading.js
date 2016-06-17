var loadingTag;

var RES_START = 0;
var RES_DISTJS = 3;
var RES_DISTJS1 = 8;
var RES_DISTJS2 = 12;
var RES_CSS	= 20;
var RES_CSS2 = 23;
var RES_DATAJS = 27;
var RES_JS1 = 30;
var RES_JS2 = 35;
var RES_JS3 = 40;
var RES_INIT = 50;
var RES_FINISH = 100;

Loading = function(){
	this.name = 'loading'
	this.reset();
}

Loading.prototype.init = function(){
	setInterval(function(){
	   g_loading.update();
	  },1500
	);	
}

Loading.prototype.reset = function(){
	this.currPer = 0;
	this.nextPer = 0;
	if (loadingTag!=null) return;
	
	var div = document.createElement('div');
	div.id = 'loading';
	var left = window.screen.width/2;
	var top = window.screen.height/2-100;
	div.style.position='absolute';
	div.style.fontSize = "20px";
	div.style.left= left+'px';
	div.style.color = "black";
	div.style.top=top+'px';
	document.body.appendChild(div);
	loadingTag = document.getElementById('loading');
}

Loading.prototype.update = function(){
//alert(this.nextPer+","+this.currPer);
	if (this.nextPer>this.currPer&&this.currPer>=0){
		this.add(1);
	}
}

Loading.prototype.clear = function(){
	$('#loading').remove();
	loadingTag = null;
}

Loading.prototype.add = function(per,nextPer){
	this.set(this.currPer+per,nextPer);
}

Loading.prototype.set = function(per,nextPer){
 if (loadingTag==null) return;
 if (per>=RES_FINISH) return;
 
 loadingTag.innerHTML = per+'%';
 this.currPer = per;
 if (nextPer!=null)
 	this.nextPer = nextPer;
 
}

var g_loading = new Loading();
g_loading.init();