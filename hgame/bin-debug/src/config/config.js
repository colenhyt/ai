var g_res = ["preload","soldier","enemy","fm"];

var g_page = {
  "FirstPage":"StartupPage",
  "Pages":{
    "StartupPage":{
		path:"common",
		armature:"fengmian",
		onOK:"",
		pos:{x:400,y:240,width:600,height:400},
		touches:{touchEnabled:true,touchChildren:false}
	}
  }
};

var g_sprite = {
 s1:{name:"soldier1",type:0,scaleX:-1,pos:{x:60,y:250}},
 e1:{name:"enemy_01",type:0},
 e2:{name:"enemy_03",type:0},
 e3:{name:"enemy_04",type:0},
 monkey:{name:"monkey",type:1,pos:{x:60,y:250},frameRate:20},
};

var g_scene = {
  "First":"MainScene",
  "Data":{
    "MainScene":{
	  "map":"fighter.BgMap",
	  "sprites":["s1","e1","e2","e3","monkey",]
	},
  }
}