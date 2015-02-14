var g_res = ["preload","soldier","enemy","fm"];

var g_page = {
  "FirstPage":"StartupPage",
  "Pages":{
    "StartupPage":{
		path:"common.StartupPage",
		onOpen:{armature:"fengmian",pos:{}},
		onClose:{armature:"fengmian",play:{name:"kaiqi",loop:1},},
		touch:{target:"onClose",touchChild:""},
		pos:{x:400,y:240,width:600,height:400},
	},
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