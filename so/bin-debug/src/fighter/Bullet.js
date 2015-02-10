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
     * 子弹，利用对象池
     */
    var Bullet = (function (_super) {
        __extends(Bullet, _super);
        function Bullet(texture) {
            _super.call(this, texture);
        }
        /**生产*/
        Bullet.produce = function (textureName) {
            if (fighter.Bullet.cacheDict[textureName] == null)
                fighter.Bullet.cacheDict[textureName] = [];
            var dict = fighter.Bullet.cacheDict[textureName];
            var bullet;
            if (dict.length > 0) {
                bullet = dict.pop();
            }
            else {
            	var tt = RES.getRes(textureName);
                bullet = new fighter.Bullet(tt);
                bullet.rotate = -90;
            }
            bullet.textureName = textureName;
            return bullet;
        };
        /**回收*/
        Bullet.reclaim = function (bullet, textureName) {
            if (fighter.Bullet.cacheDict[textureName] == null)
                fighter.Bullet.cacheDict[textureName] = [];
            var dict = fighter.Bullet.cacheDict[textureName];
            if (dict.indexOf(bullet) == -1)
                dict.push(bullet);
        };
        Bullet.cacheDict = {};
        return Bullet;
    })(egret.Bitmap);
    fighter.Bullet = Bullet;
    Bullet.prototype.__class__ = "fighter.Bullet";
})(fighter || (fighter = {}));
