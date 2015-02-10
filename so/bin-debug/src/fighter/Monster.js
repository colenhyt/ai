var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
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
        function Monster(resName, fireDelay,factory) {
            _super.call(this);
            this.tick = 0;
            /**飞机生命值*/
            this.blood = 10;
            this.actionType = -1;
            this.resName = resName;
            this.fireDelay = fireDelay;
			var armature = factory.buildArmature(resName);
			this.armature = armature;
			var armatureDisplay = armature.getDisplay();
			this.armatureDisplay = armatureDisplay;
			this.addChild(armatureDisplay);
			dragonBones.WorldClock.clock.add(armature);
            this.fireTimer = new egret.Timer(fireDelay);
            this.fireTimer.addEventListener(egret.TimerEvent.TIMER, this.createBullet, this);
        }
        /**生产*/
        Monster.produce = function (resName, fireDelay,factory) {
            if (fighter.Monster.cacheDict[resName] == null)
                fighter.Monster.cacheDict[resName] = [];
            var dict = fighter.Monster.cacheDict[resName];
            var theFighter;
            if (dict.length > 0) {
                theFighter = dict.pop();
            }
            else {
                theFighter = new fighter.Monster(resName, fireDelay,factory);
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
        Monster.prototype.start = function () {
            this.fireTimer.start();
        };
        /*动作播放*/
        Monster.prototype.gotoAndPlay = function (actionName) {
            this.armature.animation.gotoAndPlay(actionName);
            //if (actionName=="death")
            // dragonBones.WorldClock.clock.remove(this.armature);
        };
        /**行走*/
        Monster.prototype.move = function () {
            //this.fireTimer.start();
            this.armature.animation.gotoAndPlay("move");
        };
        /**停火*/
        Monster.prototype.stop = function () {
            this.fireTimer.stop();
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
            this.armature.animation.gotoAndPlay("move");
        	}else
        	{
        	 this.armature.animation.gotoAndPlay("fire");
        	}  
        	
           // this.dispatchEventWith("createBullet");
        };
        /**创建子弹*/
        Monster.prototype.createBullet = function (evt) {
           // this.dispatchEventWith("createBullet");
        };
        Monster.cacheDict = {};
        return Monster;
    })(egret.DisplayObjectContainer);
    fighter.Monster = Monster;
    Monster.prototype.__class__ = "fighter.Monster";
})(fighter || (fighter = {}));
