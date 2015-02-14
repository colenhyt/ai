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
			 return false;
			}
			if (cfg.page)
			{
			  var page = CreatePage(factory,cfg.page);
			  this.addChild(page);
			}
			if (cfg.map)
			{
			  var map = eval("new "+cfg.map+"()");
			  this.addChild(map);
			}
			return this;
           
        };
        return GameScene;
    })(egret.Sprite);
    common.GameScene = GameScene;
    GameScene.prototype.__class__ = "common.GameScene";
})(common || (common = {}));
