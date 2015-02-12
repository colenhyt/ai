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
    var StartupPage = (function (_super) {
        __extends(StartupPage, _super);
        function StartupPage(factory) {
            _super.call(this);
           
            var armature = factory.buildArmature("fengmian");
			this.armature = armature;
			var armatureDisplay = armature.getDisplay();
			this.addChild(armatureDisplay);
			this.width=600;
			this.height=400;
            this.x =400;
            this.y = 240;
           this.addEventListener(egret.TouchEvent.TOUCH_BEGIN, this.touchHandler, this);
           this.touchChildren = false;
            this.touchEnabled = true;
        }

        /**响应Touch*/
        StartupPage.prototype.touchHandler = function (evt) {
            if (evt.type == egret.TouchEvent.TOUCH_BEGIN) {
				alert('start');
			}
        };
        StartupPage.prototype.showScore = function (value) {
            var msg = "您的成绩是:\n" + value + "\n再来一次吧";
            this.txt.text = msg;
        };
        return StartupPage;
    })(egret.Sprite);
    common.StartupPage = StartupPage;
    StartupPage.prototype.__class__ = "common.StartupPage";
})(common || (common = {}));
