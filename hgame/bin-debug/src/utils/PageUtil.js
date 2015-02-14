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

        };

		//页面初始化:cfg:config/config.js页面配置, factory:armature资源工厂
		PageUtil.prototype.init = function (cfg,factory) {
			var pos = cfg.pos?cfg.pos:{x:0,y:0,width:640,height:480};
			this.cfg = cfg;
			this.factory = factory;
			this.width=pos.width;
			this.height=pos.height;
            this.x = pos.x;
            this.y = pos.y;
 
			this.onOpen();

		  var touches = cfg.touch;
		  if (touches&&touches.target)
		  {
			  if (touches.touchChild&&touches.touchChild.length>0)
			  {
            this.touchChildren = true;
            this.touchEnabled = false;
			  }else
			  {
            this.touchChildren = false;
            this.touchEnabled = true;
			  }

           this.addEventListener(egret.TouchEvent.TOUCH_TAP, this.touchTAP, this);
		  }

		};

		/**打开页面*/
		PageUtil.prototype.onOpen = function () {
 			this.doCfg(this.cfg.onOpen);

		};

		/**关闭页面*/
		PageUtil.prototype.onClose = function () {
 			this.doCfg(this.cfg.onClose);

		};

		/**配置执行*/
		PageUtil.prototype.doCfg = function (cfg) {
			if (!cfg)
			{
				return;
			}
			//remove last sprite:
			if (this.contains(this.lastSprite))
			{
			 this.removeChild(this.lastSprite);
			}

			var sprite;

			//armature cfg:
			var arma = cfg.armature;
			if (arma&&arma.length>0&&this.factory)
			{
			 var armature = CreateAnimation(this.factory,arma);
			 if (cfg.play)
			 {
			  armature.animation.gotoAndPlay(cfg.play.name,-1,-1,cfg.play.loop);
			   armature.target='ddd';
               armature.addEventListener(dragonBones.AnimationEvent.COMPLETE, this.anicomplete, this);
			 }
			 sprite = armature.getDisplay();
			 this.lastSprite = sprite;
			}

			if (sprite)
			{
			 var pos = cfg.pos;
			 if (pos)
			 {
			  sprite.x = pos.x?pos.x:0;
			  sprite.y = pos.y?pos.y:0;
			  sprite.width = pos.width?pos.width:0;
			  sprite.height = pos.height?pos.height:0;
			 }
			 this.addChild(sprite);
			}
			return sprite;

		};

		/**页面点击*/
		PageUtil.prototype.touchTAP = function (evt) {
			var touch = this.cfg.touch;
			if (touch&&touch.target)
			{
				if (touch.target=="onClose")
				{
					this.onClose();
				}
				return;
			}
		};
        /**播放结束*/
        PageUtil.prototype.anicomplete = function (evt) {
        alert('this:'+evt.target.ddd);
           dragonBones.WorldClock.clock.remove(this.armature);
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
