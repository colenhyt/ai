/**
 * Created by shaorui on 14-6-6.
 */
var fighter;
(function (fighter) {
    var GameUtil = (function () {
        function GameUtil() {
        }
        /**基于矩形的碰撞检测*/
        GameUtil.hitTest = function (obj1, obj2) {
            var rect1 = obj1.getBounds();
            var rect2 = obj2.getBounds();
            rect1.x = obj1.x;
            rect1.y = obj1.y;
            rect2.x = obj2.x;
            rect2.y = obj2.y;
            return rect1.intersects(rect2);
        };
        return GameUtil;
    })();
    fighter.GameUtil = GameUtil;
    GameUtil.prototype.__class__ = "fighter.GameUtil";
    /**
     * 根据name关键字创建一个Bitmap对象。name属性请参考resources/resource.json配置文件的内容。
     */
    function createBitmapByName(name) {
        var result = new egret.Bitmap();
        var texture = RES.getRes(name);
        result.texture = texture;
        return result;
    }
    fighter.createBitmapByName = createBitmapByName;
})(fighter || (fighter = {}));

//创建页面:
function CreatePage(factory,pageName){
 var cfg = g_page.Pages[pageName];
 var page = eval("new "+cfg.path+"()");
 page.init(cfg,factory);
 return page;
}
//创建场景:
function CreateMap(sceneName){
 var name = sceneName?sceneName:g_scene.First;
 var cfg = g_scene["Data"][name];
 var map = eval("new "+cfg.map+"()");
 return map;
}
//创建动画
function CreateAnimation(factory,armaName,reUse)
{
 var skeletonData = factory.getSkeletonData(armaName);
 if (!skeletonData)
  {
   skeletonData = RES.getRes(armaName+"_skeleton");
   var textureData = RES.getRes(armaName+"_texture");
   var texture = RES.getRes(armaName+"_png");
   factory.addSkeletonData(dragonBones.DataParser.parseDragonBonesData(skeletonData));
   factory.addTextureAtlas(new dragonBones.EgretTextureAtlas(texture, textureData));
  }

 var armature = factory.buildArmature(armaName);
 var armatureDisplay = armature.getDisplay();
 dragonBones.WorldClock.clock.add(armature);
 return armature;
}