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
        function StartupPage() {
            _super.call(this);
           
        };
		/**关闭页面*/
		StartupPage.prototype.close = function () {
			_super.prototype.close.call(this);
			g_game.enterScene("main");
		};
        return StartupPage;
    })(utils.PageUtil);
    common.StartupPage = StartupPage;
    StartupPage.prototype.__class__ = "common.StartupPage";
})(common || (common = {}));
