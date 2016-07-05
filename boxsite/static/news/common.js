var SiteUrl = "http://202.69.27.238:8081/boxsite"

var Share_Img = SiteUrl+"/static/img/app_icon_share.png"

var g_currcat = 0;

var Share_Title = "科技早报每日分享"

var Share_Text = "科技早报"


var g_newscats = [
[1,"综合"],[11,"动态"],[21,"产品"],[31,"分析"],[41,"运营"],[51,"创投"]
];


var g_sitekeys = {
"huxiu.com":"虎嗅",
"ikanchai.com":"爱砍柴",
"iheima.com":"i黑马",
"163.com":"网易科技",
"cyzone.cn":"创业邦",
"ifanr.com":"爱范儿",
"ifeng.com":"凤凰网",
"iyiou.com":"亿欧网",
"leiphone.com":"雷锋网",
"pintu360.com":"品图评论",
"qq.com":"腾讯科技",
"sina.com.cn":"新浪科技",
"sohu.com":"搜狐科技",
"sootoo.com":"速途图",
"techweb.com.cn":"",
"tmtpost.com":"钛媒体",
"geekpark":"极客公园",

};


var SCREENKEY = 640;
var SIZEPER = 1;

 var browser={
    versions:function(){ 
           var u = navigator.userAgent, app = navigator.appVersion; 
           //alert(u);
           return {//移动终端浏览器版本信息 
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
                iPhone5: u.indexOf('iPhone OS 6_1') > -1 , //os版本
                iPhone6: u.indexOf('iPhone OS 9_1') > -1 , //os6版本
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
            };
         }(),
         language:(navigator.browserLanguage || navigator.language).toLowerCase()
} 

function initScreen(){
//alert(window.screen.width)
	var versions = browser.versions
	g_versions = versions;
	g_versions.width = window.screen.width;
 	 var width = window.screen.width;
	if (versions.iPhone||versions.iPad){
		if (width<=320||width==375||width==414){
			metas = window.parent.document.getElementsByTagName("meta");
			for(i=0;i<metas.length;i++)
		     {
		      if (metas[i].getAttribute("name")=="viewport"){
		       //alert(metas[i].getAttribute("name"))
		       metas[i].setAttribute("content","width=320");
		       break;  
		      }
			}
			if (width<480&&width>=360)
			 SCREENKEY = 400;
			else
			 SCREENKEY = 320;
		 }else
			 SCREENKEY = 640;
		 
	}else{
	  if (width>=1000)
		SCREENKEY = 640;
	  else if (width<1000&&width>=800)
		SCREENKEY = 800;
	  else if (width<800&&width>=720)
		SCREENKEY = 720;
	  else if (width<720&&width>=640)
		SCREENKEY = 640;
	  else if (width<640&&width>=600)
		SCREENKEY = 600;
	  else if (width<600&&width>=540)
		SCREENKEY = 540;
	  else if (width<540&&width>=480)
		SCREENKEY = 480;
	  else if (width<480&&width>=420)
		SCREENKEY = 440;
	  else if (width<420&&width>=380)
		SCREENKEY = 400;
	  else if (width<380&&width>=340)
		SCREENKEY = 360;
	  else
	    SCREENKEY = 320;
	}
	 //  SCREENKEY = 320;	  
//
//alert(browser.versions.iPhone)
	////--note:393,SCREENKEY = 640;
	//--6s:414, 正常, screekey = 640;
//alert(width);
//alert(SCREENKEY);
//SCREENKEY = 360;
 
	g_screenkey = SCREENKEY;
	SIZEPER = SCREENKEY/640;
	resetCssStyles(SIZEPER);		
}

initScreen();