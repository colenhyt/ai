var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};

var  g_textures = [];
g_textures.push("soldier1");
g_textures.push("enemy_01");
g_textures.push("enemy_03");
g_textures.push("enemy04");

/**
 * Created by shaorui on 14-6-7.
 */
var fighter;
(function (fighter) {
    /**
     * 飞机，利用对象池
     */
    var Monster = (function (_super) {
        __extends(Monster, _super);
        function Monster(resName, fireDelay,factory,isMe) {
            _super.call(this);
            this.tick = 0;
            this.isMe = isMe;
            /**飞机生命值*/
            this.blood = 10;
            this.actionType = -1;
            this.resName = resName;
            this.fireDelay = fireDelay;
			var armature = factory.buildArmature(resName);
			this.armature = armature;
			 
			var armatureDisplay = armature.getDisplay();
			if (isMe){
			 armatureDisplay.scaleX = -1;
			 armature.animation.gotoAndPlay("daiji");
			}
			this.addChild(armatureDisplay);
			dragonBones.WorldClock.clock.add(armature);
            this.fireTimer = new egret.Timer(fireDelay);
            this.armature.aa = 43;
            this.armature.addEventListener(dragonBones.AnimationEvent.LOOP_COMPLETE, this.loopComplete, this);
            
            this.fireTimer.addEventListener(egret.TimerEvent.TIMER, this.createBullet, this);
        }
        /**生产*/
        Monster.produce = function (resType, fireDelay,factory,isMe) {
        	var resName = g_textures[resType];
            if (fighter.Monster.cacheDict[resName] == null)
                fighter.Monster.cacheDict[resName] = [];
            var dict = fighter.Monster.cacheDict[resName];
            var theFighter;
            if (dict.length > 0) {
                theFighter = dict.pop();
            }
            else {
                theFighter = new fighter.Monster(resName, fireDelay,factory,isMe);
            }
            theFighter.blood = 10;
            return theFighter;
        };
        /**回收*/
        Monster.reclaim = function (theFighter) {
            if (fighter.Monster.cacheDict[theFighter.resName] == null)
                fighter.Monster.cacheDict[theFighter.resName] = [];
            var dict = fighter.Monster.cacheDict[theFighter.resName];
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
            if (!this.isMe)
            this.armature.animation.gotoAndPlay("move");
            else
            this.armature.animation.gotoAndPlay("daiji");
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
    fighter.Monster = Monster;
    Monster.prototype.__class__ = "fighter.Monster";
})(fighter || (fighter = {}));
