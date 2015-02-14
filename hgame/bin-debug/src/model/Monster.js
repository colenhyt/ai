var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};

/**
 * Created by shaorui on 14-6-7.
 */
var model;
(function (model) {
    /**
     * 飞机，利用对象池
     */
    var Monster = (function (_super) {
        __extends(Monster, _super);
        function Monster(cfg,factory) {
            _super.call(this);
            this.tick = 0;
            /**飞机生命值*/
            this.blood = 10;
            this.actionType = -1;

			var sname = cfg.name;
            this.resName = sname;
			var sprite;
		 if (cfg.type==0)
		  {
			 var skename = sname+"_skeleton";
			 var skeletonData = factory.getSkeletonData(skename);
			 if (!skeletonData)
			 {
			  skeletonData = RES.getRes(skename);
			  var textureData = RES.getRes(sname+"_texture");
			  var texture = RES.getRes(sname+"_png");
			  factory.addSkeletonData(dragonBones.DataParser.parseDragonBonesData(skeletonData));
			  factory.addTextureAtlas(new dragonBones.EgretTextureAtlas(texture, textureData));
			 }


			var armature = factory.buildArmature(sname);
			armature.animation.play();			
			dragonBones.WorldClock.clock.add(armature);

			sprite = armature.getDisplay();
		  }else if(cfg.type==1)
		  {
           var data = RES.getRes(sname+"_json");
           var texture = RES.getRes(sname+"_png");
           sprite = new egret.MovieClip(data, texture);
		   if (cfg.frameRate)
		   {
		   sprite.frameRate = cfg.frameRate;
		   }
		   sprite.gotoAndPlay("attack");
		  }

			var pos = cfg.pos;
			this.x = pos.x?pos.x:0;
			this.y = pos.y?pos.y:0;

			this.addChild(sprite);
			if (cfg.scaleX!=null)
			{
			 sprite.scaleX = -1;
			 //armature.animation.gotoAndPlay("daiji");
			}

            var fireDelay = cfg.fireDelay?cfg.fireDelay:150;
            this.fireTimer = new egret.Timer(fireDelay);
            
            this.fireTimer.addEventListener(egret.TimerEvent.TIMER, this.createBullet, this);
        }
        /**生产*/
        Monster.produce = function (cfg,factory) {
			var resName = cfg.name;
            if (model.Monster.cacheDict[resName] == null)
                model.Monster.cacheDict[resName] = [];
            var dict = model.Monster.cacheDict[resName];
            var theFighter;
            if (dict.length > 0) {
                theFighter = dict.pop();
            }
            else {
                theFighter = new model.Monster(cfg,factory);
            }
            theFighter.blood = 10;
            return theFighter;
        };
        /**回收*/
        Monster.reclaim = function (theFighter) {
            if (model.Monster.cacheDict[theFighter.resName] == null)
                model.Monster.cacheDict[theFighter.resName] = [];
            var dict = model.Monster.cacheDict[theFighter.resName];
            if (dict.indexOf(theFighter) == -1)
                dict.push(theFighter);
        };
        /**开始*/
        Monster.prototype.start = function (evt) {
            this.fireTimer.start();
            this.evt = evt;
            this.armature.animation.gotoAndPlay("fire");
        };
        /*动作播放*/
        Monster.prototype.gotoAndPlay = function (actionName) {
            this.armature.animation.gotoAndPlay(actionName);
            // dragonBones.WorldClock.clock.remove(this.armature);
        };
        /**行走*/
        Monster.prototype.move = function () {
            this.armature.animation.gotoAndPlay("move");
        };
        /**停火*/
        Monster.prototype.stop = function () {
        	if (this.blood <= 0) return;
        	
            this.fireTimer.stop();
            this.armature.animation.gotoAndPlay("move");
        };
        /**停火*/
        Monster.prototype.death = function (isShotHead) {
            this.fireTimer.stop();
            if (isShotHead&&this.armature.animation.hasAnimation("shothead"))
             this.armature.animation.gotoAndPlay("shothead",-1,-1,1);
            else
             this.armature.animation.gotoAndPlay("death",-1,-1,1);
        };
        /**开始*/
        Monster.prototype.loopComplete = function (evt) {
        //alert('this'+evt.target.aa);
        	if (this.isDeath==1){
         alert('dddhee');
        	}
             dragonBones.WorldClock.clock.remove(this.armature);
        };
        /**monster ai*/
        Monster.prototype.update = function (evt,fps) {
        	this.tick++;
        	if (this.blood <= 0) return;
        	
        	if (this.actionType<=6)
        	 this.x -= 4 * 30/fps;
        	 
        	if (this.tick%30!=0) return;
        	
         	 this.actionType = Math.random()*10;
        	if (this.actionType<=6)
        	{
            this.stop();
        	}else
        	{
        	 this.start();
        	}  
        	
           // this.dispatchEventWith("createBullet");
        };
        /**创建子弹*/
        Monster.prototype.createBullet = function (evt) {
            if (this.evt)
             this.parent.createBulletHandler(this.evt);
            else
             this.dispatchEventWith("createBullet");
        };
        Monster.cacheDict = {};
        return Monster;
    })(egret.DisplayObjectContainer);
    model.Monster = Monster;
    Monster.prototype.__class__ = "model.Monster";
})(model || (model = {}));
