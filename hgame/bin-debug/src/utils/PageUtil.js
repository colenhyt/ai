var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
/**
 * Created by shaorui on 14-6-8.
 */
var utils;
(function (utils) {
    /**
     * 成绩显示
     */
    var PageUtil = (function (_super) {
        __extends(PageUtil, _super);
        function PageUtil() {
            _super.call(this);

           this.addEventListener(egret.TouchEvent.TOUCH_TAP, this.startUpAnimation, this);
        };

		//页面初始化:cfg:config/config.js页面配置, factory:armature资源工厂
		PageUtil.prototype.init = function (cfg,factory) {
			var pos = cfg.pos?cfg.pos:{x:0,y:0,width:640,height:480};
			this.width=pos.width;
			this.height=pos.height;
            this.x = pos.x;
            this.y = pos.y;
			var touches = cfg.touches;
           this.touchChildren = touches.touchChildren;
            this.touchEnabled = touches.touchEnabled;

			if (cfg.armature&&cfg.armature.length>0&&factory)
			{
				var skename = cfg.armature+"_skeleton";
			var skeletonData = factory.getSkeletonData(skename);
				if (!skeletonData)
				{
			skeletonData = RES.getRes(skename);
			var textureData = RES.getRes(cfg.armature+"_texture");
			var texture = RES.getRes(cfg.armature+"_png");
			factory.addSkeletonData(dragonBones.DataParser.parseDragonBonesData(skeletonData));
			factory.addTextureAtlas(new dragonBones.EgretTextureAtlas(texture, textureData));
				}

            var armature = factory.buildArmature(cfg.armature);
			this.armature = armature;
			var armatureDisplay = armature.getDisplay();
			this.addChild(armatureDisplay);
			this.armature.animation.play();			
			}
		};

		/**页面动画*/
		PageUtil.prototype.startUpAnimation = function (evt) {
			this.armature.animation.gotoAndPlay("kaiqi");
		};

        PageUtil.prototype.touchHandler = function (evt) {
			g_game.gameStart();
        };
        PageUtil.prototype.showScore = function (value) {
            var msg = "您的成绩是:\n" + value + "\n再来一次吧";
            this.txt.text = msg;
        };
        return PageUtil;
    })(egret.Sprite);
    utils.PageUtil = PageUtil;
    PageUtil.prototype.__class__ = "utils.PageUtil";
})(utils || (utils = {}));
