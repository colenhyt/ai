var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};

function logg(msg,append){
    var logdiv = document.getElementById('testlog');
    if (append)
     logdiv.innerHTML = parseInt(logdiv.innerHTML)+msg;
    else
     logdiv.innerHTML = msg;
}

var common;
(function (common) {
    /**
     * 主游戏容器
     */
    var GameContainer = (function (_super) {
        __extends(GameContainer, _super);
        function GameContainer() {
            _super.call(this);
            /**我的子弹*/
            this.myBullets = [];
            /**敌人的飞机*/
            this.enemyFighters = [];
            /**触发创建敌机的间隔*/
            this.enemyFightersTimer = new egret.Timer(1000);
            /**敌人的子弹*/
            this.enemyBullets = [];
            /**我的成绩*/
            this.myScore = 0;
            this._lastTime = egret.getTimer();
            this.addEventListener(egret.Event.ADDED_TO_STAGE, this.onAddToStage, this);
        }
        /**初始化*/
        GameContainer.prototype.onAddToStage = function (event) {
            this.removeEventListener(egret.Event.ADDED_TO_STAGE, this.onAddToStage, this);
            this.createGameScene();
        };
        /**创建游戏场景*/
        GameContainer.prototype.createGameScene = function () {
            this.stageW = this.stage.stageWidth;
            this.stageH = this.stage.stageHeight;

        var factory = new dragonBones.EgretFactory();
        this.factory = factory;
 
			//场景加载:
			var scene = new common.GameScene(factory,g_scene.first);
			if (scene)
			{
			 this.currScene = scene;
             this.addChild(this.currScene);
			}

            this.preCreatedInstance();
        };
        /**预创建一些对象，减少游戏时的创建消耗*/
        GameContainer.prototype.preCreatedInstance = function () {
            var i = 0;
            var objArr = [];
            for (i = 0; i < 20; i++) {
                var bullet = fighter.Bullet.produce("b1");
                objArr.push(bullet);
            }
            for (i = 0; i < 20; i++) {
                bullet = objArr.pop();
                fighter.Bullet.reclaim(bullet, "b1");
            }
            for (i = 0; i < 20; i++) {
                var bullet = fighter.Bullet.produce("b2");
                objArr.push(bullet);
            }
            for (i = 0; i < 20; i++) {
                bullet = objArr.pop();
                fighter.Bullet.reclaim(bullet, "b2");
            }
            for (i = 0; i < 20; i++) {
                var enemyFighter = fighter.Airplane.produce("f2", 1000);
                objArr.push(enemyFighter);
            }
            for (i = 0; i < 20; i++) {
                enemyFighter = objArr.pop();
                fighter.Airplane.reclaim(enemyFighter, "f2");
            }


		var factory = this.factory;

		var bullet = fighter.Bullet.produce("b1");
		bullet.x = 360;
		bullet.y = 185;
		bullet.anchorOffsetY = 30;
		//bullet.rotation += 90;
		this.addChild(bullet);
		
        egret.Ticker.getInstance().register(function (advancedTime) {
            dragonBones.WorldClock.clock.advanceTime(advancedTime / 1000);
        }, this);

        };
        /**游戏开始*/
        GameContainer.prototype.enterScene = function (sceneName) {
			if (this.contains(this.currScene))
			{
			 this.removeChild(this.currScene);
			}

			var scene = new common.GameScene(this.factory,sceneName);
			if (scene)
			{
			 this.currScene = scene;
             this.addChild(this.currScene);
			}

            this.myScore = 0;
            this.touchEnabled = true;
            this.addEventListener(egret.Event.ENTER_FRAME, this.update, this);
            this.addEventListener(egret.TouchEvent.TOUCH_BEGIN, this.touchHandler, this);
            this.addEventListener(egret.TouchEvent.TOUCH_END, this.touchHandler, this);
            this.addEventListener(egret.TouchEvent.TOUCH_MOVE, this.touchHandler, this);
			this.addEventListener(egret.TouchEvent.TOUCH_OVER, this.touchHandler, this);

		var player = model.Monster.produce(g_sprite["monkey"],this.factory);
		this.addChild(player);
		this.player = player;


		//this.player.addEventListener("createBullet", this.createBulletHandler, this);

            //this.myFighter.x = (this.stageW - this.myFighter.width) / 2;
           // this.enemyFightersTimer.addEventListener(egret.TimerEvent.TIMER, this.createEnemyFighter, this);
          //  this.enemyFightersTimer.start();

        };
        /**响应Touch*/
        GameContainer.prototype.touchHandler = function (evt) {
			var evt2 = {target:this.player,localX:evt.localX,localY:evt.localY};
            if (evt.type == egret.TouchEvent.TOUCH_MOVE) {
                var tx = evt.localX;
                tx = Math.max(0, tx);
                tx = Math.min(this.stageW - this.myFighter.width, tx);
               // this.myFighter.x = tx;
               //var evt2 = {target:this.player,localX:evt.localX,localY:evt.localY};
              // this.createBulletHandler(evt2);
            }else if (evt.type == egret.TouchEvent.TOUCH_BEGIN) {
				this.player.start(evt2);
				//this.createBulletHandler(evt2);
			}else if (evt.type == egret.TouchEvent.TOUCH_END) {
				this.player.stop();
			}else if (evt.type == egret.TouchEvent.TOUCH_OVER) {
				this.createBulletHandler(evt2);
			}
        };
        /**创建子弹(包括我的子弹和敌机的子弹)*/
        GameContainer.prototype.createBulletHandler = function (evt) {
            var bullet;
            if (evt.target!=null&&evt.target == this.player) {
                for (var i = 0; i < 1; i++) {
                    bullet = fighter.Bullet.produce("b1");
                    bullet.x = (this.player.x + this.player.width+32);
                    bullet.y = this.player.y - 65;
                    bullet.tx = evt.localX;
                    bullet.ty = evt.localY;
                    bullet.step = (evt.localY-bullet.y)/(evt.localX-bullet.x);
					bullet.rotate = parseInt(rotate);
					var rotate = Math.atan(bullet.step)*180/Math.PI
					bullet.rotation += parseInt(rotate);
					//logg(parseInt(bullet.rotation));
                   logg(rotate);
                    this.addChildAt(bullet, this.numChildren - 1 - this.enemyFighters.length);
                    this.myBullets.push(bullet);
                }
            }
            else {
                var theFighter = evt.target;
                bullet = fighter.Bullet.produce("b2");
                bullet.x = theFighter.x - 28;
                bullet.y = theFighter.y;
                this.addChildAt(bullet, this.numChildren - 1 - this.enemyFighters.length);
                this.enemyBullets.push(bullet);
            }
        };
        /**创建敌机*/
        GameContainer.prototype.createEnemyFighter = function (evt) {
        	var resType = Math.floor(Math.random() * 3 + 1);
            var enemyFighter = fighter.Monster.produce(resType, 300,this.factory);
            enemyFighter.x = enemyFighter.width + 700;
            enemyFighter.y = Math.random() * (this.stageH - enemyFighter.height)+100;            
            enemyFighter.addEventListener("createBullet", this.createBulletHandler, this);
            this.addChildAt(enemyFighter, this.numChildren - 1);
            this.enemyFighters.push(enemyFighter);
        };
        /**游戏画面更新*/
        GameContainer.prototype.update = function (evt) {
            //为了防止FPS下降造成回收慢，生成快，进而导致DRAW数量失控，需要计算一个系数，当FPS下降的时候，让运动速度加快
            var nowTime = egret.getTimer();
            var fps = 1000 / (nowTime - this._lastTime);
            this._lastTime = nowTime;
            var speedOffset = 60 / fps;
            //我的子弹运动
            var i = 0;
            var bullet;
            var myBulletsCount = this.myBullets.length;
            var delArr = [];
            var step = 12 * speedOffset;
            for (; i < myBulletsCount; i++) {
                bullet = this.myBullets[i];
                bullet.x += step;
                bullet.y += step*bullet.step;
                if (bullet.x > 800||bullet.y<0||bullet.y>500)
                    delArr.push(bullet);
            }
            for (i = 0; i < delArr.length; i++) {
                bullet = delArr[i];
                this.removeChild(bullet);
                fighter.Bullet.reclaim(bullet, "b1");
                this.myBullets.splice(this.myBullets.indexOf(bullet), 1);
            }
            delArr = [];
            //敌人飞机运动
            var theFighter;
            var enemyFighterCount = this.enemyFighters.length;
            for (i = 0; i < enemyFighterCount; i++) {
                theFighter = this.enemyFighters[i];
                theFighter.update(evt,fps);
                if (theFighter.x <0)
                    delArr.push(theFighter);
            }
            for (i = 0; i < delArr.length; i++) {
                theFighter = delArr[i];
                this.removeChild(theFighter);
                fighter.Monster.reclaim(theFighter);
                theFighter.removeEventListener("createBullet", this.createBulletHandler, this);
                theFighter.stop();
                this.enemyFighters.splice(this.enemyFighters.indexOf(theFighter), 1);
            }
            delArr = [];
            //敌人子弹运动
            var enemyBulletsCount = this.enemyBullets.length;
            for (i = 0; i < enemyBulletsCount; i++) {
                bullet = this.enemyBullets[i];
                bullet.x -= 8 * speedOffset;
                if (bullet.x < 0 )
                    delArr.push(bullet);
            }
            for (i = 0; i < delArr.length; i++) {
                bullet = delArr[i];
                this.removeChild(bullet);
                fighter.Bullet.reclaim(bullet, "b2");
                this.enemyBullets.splice(this.enemyBullets.indexOf(bullet), 1);
            }
            this.gameHitTest();
        };
        /**游戏碰撞检测*/
        GameContainer.prototype.gameHitTest = function () {
            var i, j;
            var bullet;
            var theFighter;
            var myBulletsCount = this.myBullets.length;
            var enemyFighterCount = this.enemyFighters.length;
            var enemyBulletsCount = this.enemyBullets.length;
            //将需消失的子弹和飞机记录
            var delBullets = [];
            var delFighters = [];
            for (i = 0; i < myBulletsCount; i++) {
                bullet = this.myBullets[i];
                for (j = 0; j < enemyFighterCount; j++) {
                    theFighter = this.enemyFighters[j];
                    if (fighter.GameUtil.hitTest(theFighter, bullet)) {
                    	var isShotHead = (Math.random()<0.4);
                    	if (isShotHead)
                         theFighter.blood -= 10;
                        else
                         theFighter.blood -= 2;
                        if (delBullets.indexOf(bullet) == -1)
                          delBullets.push(bullet);
                        if (theFighter.blood <= 0){
                          theFighter.death(isShotHead);
                        if (delFighters.indexOf(theFighter) == -1)
                            delFighters.push(theFighter);
                        }
                    }
                }
            }
            for (i = 0; i < enemyBulletsCount; i++) {
                bullet = this.enemyBullets[i];
//                if (fighter.GameUtil.hitTest(this.myFighter, bullet)) {
//                    this.myFighter.blood -= 1;
//                    if (delBullets.indexOf(bullet) == -1)
//                        delBullets.push(bullet);
//                }
            }
            for (i = 0; i < enemyFighterCount; i++) {
                theFighter = this.enemyFighters[i];
//                if (fighter.GameUtil.hitTest(this.myFighter, theFighter)) {
//                    this.myFighter.blood -= 10;
//                }
            }
            if (this.player.blood <= 0) {
                this.gameStop();
            }
            else {
                while (delBullets.length > 0) {
                    bullet = delBullets.pop();
                    this.removeChild(bullet);
                    if (bullet.textureName == "b1")
                        this.myBullets.splice(this.myBullets.indexOf(bullet), 1);
                    else
                        this.enemyBullets.splice(this.enemyBullets.indexOf(bullet), 1);
                    fighter.Bullet.reclaim(bullet, bullet.textureName);
                }
                this.myScore += delFighters.length;
                while (delFighters.length > 0) {
                    theFighter = delFighters.pop();
                    theFighter.stop();
                    //theFighter.removeEventListener("createBullet", this.createBulletHandler, this);
                    //this.removeChild(theFighter);
                    this.enemyFighters.splice(this.enemyFighters.indexOf(theFighter), 1);
                    //fighter.Monster.reclaim(theFighter);
                }
            }
        };
        /**游戏结束*/
        GameContainer.prototype.gameStop = function () {
            this.addChild(this.btnStart);
            this.bg.pause();
            this.removeEventListener(egret.Event.ENTER_FRAME, this.update, this);
            this.removeEventListener(egret.TouchEvent.TOUCH_MOVE, this.touchHandler, this);
            this.myFighter.stop();
            this.myFighter.removeEventListener("createBullet", this.createBulletHandler, this);
            this.enemyFightersTimer.removeEventListener(egret.TimerEvent.TIMER, this.createEnemyFighter, this);
            this.enemyFightersTimer.stop();
            //清理子弹
            var i = 0;
            var bullet;
            while (this.myBullets.length > 0) {
                bullet = this.myBullets.pop();
                this.removeChild(bullet);
                fighter.Bullet.reclaim(bullet, "b1");
            }
            while (this.enemyBullets.length > 0) {
                bullet = this.enemyBullets.pop();
                this.removeChild(bullet);
                fighter.Bullet.reclaim(bullet, "b2");
            }
            //清理飞机
            var theFighter;
            while (this.enemyFighters.length > 0) {
                theFighter = this.enemyFighters.pop();
                theFighter.stopFire();
                theFighter.removeEventListener("createBullet", this.createBulletHandler, this);
                this.removeChild(theFighter);
                fighter.Monster.reclaim(theFighter);
            }
            //显示成绩
            this.scorePanel.showScore(this.myScore);
            this.scorePanel.x = (this.stageW - this.scorePanel.width) / 2;
            this.scorePanel.y = 100;
            this.addChild(this.scorePanel);
        };
        return GameContainer;
    })(egret.DisplayObjectContainer);
    common.GameContainer = GameContainer;
    GameContainer.prototype.__class__ = "common.GameContainer";
})(common || (common = {}));
