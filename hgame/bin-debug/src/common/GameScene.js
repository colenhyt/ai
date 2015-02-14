var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
/**
 * Created by shaorui on 14-6-8.
 */
var common;
(function (common) {
    /**
     * 成绩显示
     */
    var GameScene = (function (_super) {
        __extends(GameScene, _super);
        function GameScene(factory,sceneName) {
            _super.call(this);
			var cfg = g_scene["data"][sceneName];
			if (!cfg)
			{
				return null;
			}
			this.cfg = cfg;
			this.factory = factory;

         this.loadingView = new LoadingUI();
         this.addChild(this.loadingView);
         //初始化Resource资源加载库
         RES.addEventListener(RES.ResourceEvent.CONFIG_COMPLETE, this.onConfigComplete, this);
         RES.loadConfig("resource/"+cfg["config"], "resource/");

		 return this;
           
        };
    /**场景初始化*/
    GameScene.prototype.init = function () {
		var cfg = this.cfg;
		if (cfg.page)
		{
		  var page = CreatePage(this.factory,cfg.page);
		  this.addChild(page);
		}
		if (cfg.map)
		{
		  var map = eval("new "+cfg.map+"()");
		  this.addChild(map);
		}
		alert('init create');
		if (cfg.sprites)
		{
//		var player = model.Monster.produce(g_sprite["monkey"],this.factory);
//		this.addChild(player);
//		this.player = player;
		}

	}
    /**配置文件加载完成,开始预加载preload资源组。*/
    GameScene.prototype.onConfigComplete = function (event) {
        RES.removeEventListener(RES.ResourceEvent.CONFIG_COMPLETE, this.onConfigComplete, this);
        RES.addEventListener(RES.ResourceEvent.GROUP_COMPLETE, this.onResourceLoadComplete, this);
        RES.addEventListener(RES.ResourceEvent.GROUP_PROGRESS, this.onResourceProgress, this);
		RES.loadGroup("preload");
        
    };
    /** preload资源组加载完成*/
    GameScene.prototype.onResourceLoadComplete = function (event) {
        if (event.groupName == "preload") {
            this.removeChild(this.loadingView);
            RES.removeEventListener(RES.ResourceEvent.GROUP_COMPLETE, this.onResourceLoadComplete, this);
            RES.removeEventListener(RES.ResourceEvent.GROUP_PROGRESS, this.onResourceProgress, this);
			this.init();
        }
    };
    /**preload资源组加载进度*/
    GameScene.prototype.onResourceProgress = function (event) {
        if (event.groupName == "preload") {
            this.loadingView.setProgress(event.itemsLoaded, event.itemsTotal);
        }
    };
     return GameScene;
    })(egret.Sprite);
    common.GameScene = GameScene;
    GameScene.prototype.__class__ = "common.GameScene";
})(common || (common = {}));
