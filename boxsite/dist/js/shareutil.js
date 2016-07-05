
function initShareSDK()
{
    $sharesdk.open("76300569a724", true);
    
    var sinaConf = {};
    sinaConf["app_key"] = "568898243";
    sinaConf["app_secret"] = "38a4f8204cc784f81f9f0daaf31e02e3";
    sinaConf["redirect_uri"] = "http://www.sharesdk.cn";
    $sharesdk.setPlatformConfig($sharesdk.platformID.SinaWeibo, sinaConf);
    
    var wechatConf = {};
    wechatConf["app_key"] = "wxe00b327e7a0d2a4a";
    wechatConf["app_secret"] = "292ca06883a56602a8b9009acae96f7d";
    wechatConf["redirect_uri"] = "http://www.sharesdk.cn";
    
    $sharesdk.setPlatformConfig($sharesdk.platformID.WeChatSession, wechatConf);    
    
    $sharesdk.setPlatformConfig($sharesdk.platformID.WeChatTimeline, wechatConf);    

}

        function authBtnClickHandler()
        {
            $sharesdk.authorize($sharesdk.platformID.SinaWeibo, function (platform, state, error){

                alert("state = " + state + "\nerror = " + error);

            });
        }

        function hasAuthBtnClickHandler()
        {
            $sharesdk.hasAuthorized($sharesdk.platformID.SinaWeibo, function (platform, hasAuth) {

                alert("hasAuth = " + hasAuth);

            });
        }

        function userInfoBtnClickHandler()
        {
            $sharesdk.getUserInfo($sharesdk.platformID.SinaWeibo, function (platform, state, user, error) {

                alert("state = " + state + "\nuser = " + user + "\nerror = " + error);

            });
        }

        function shareContentClickHandler()
        {
            var params = {
                "text" : Share_Text,
                "imageUrl" : Share_Img,
                "title" : "科技早报",
                "titleUrl" : "www.boxsite.com",
                "description" : "测试的描述",
                "site" : "ShareSDK",
                "url" : "www.boxsite.com",
                "type" : $sharesdk.contentType.WebPage
            };

            var isSSO = false;
            $sharesdk.shareContent($sharesdk.platformID.WeChatTimeline, params, isSSO,function (platform, state, shareInfo, error) {
 
                shareCallInfo(platform, state, shareInfo, error);

            });
        }

        function oneKeyShareContentClickHandler()
        {
            var params = {
                "text" : Share_Text,
                "imageUrl" : Share_Img,
                "title" : Share_Text,
                "titleUrl" : "http://sharesdk.cn",
                "description" : "测试的描述",
                "site" : "ShareSDK",
                "siteUrl" : Dl_Url_android,
                "url" : Dl_Url_android,  
                "type" : $sharesdk.contentType.WebPage
            };
           var isSSO = true;

            $sharesdk.oneKeyShareContent([$sharesdk.platformID.SinaWeibo], params, isSSO, function (platform, state, shareInfo, error) {
				shareCallInfo(platform, state, shareInfo, error);
            });
        }

        function showShareMenuClickHandler()
        {
            var params = {
                "text" : Share_Title,
                "imageUrl" : Share_Img,
                "title" : Share_Title,
                "titleUrl" : "http://boxsite.com",
                "description" : "科技早报",
                "site" : "boxsite.com",
                "siteUrl" : "http://boxsite.com",
                "url" : Share_Url,  
                "type" : $sharesdk.contentType.WebPage
            };
           var isSSO = false;

            $sharesdk.showShareMenu(null, params, 100, 100, $sharesdk.shareMenuArrowDirection.Any,isSSO,   function (platform, state, shareInfo, error) {

                shareCallInfo(platform, state, shareInfo, error);

            });
        }

        function showShareViewClickHandler()
        {
            var params = {
                "text" : Share_Text,
                "imageUrl" : Share_Img,
                "title" : "测试的标题",
                "titleUrl" : "http://sharesdk.cn",
                "description" : "测试的描述",
                "site" : "ShareSDK",
                "siteUrl" : "http://sharesdk.cn",
                "type" : 1
            };
				
            $sharesdk.showShareView($sharesdk.platformID.SinaWeibo, params, function (platform, state, shareInfo, error) {
				shareCallInfo(platform, state, shareInfo, error);

            });
        }

        function shareCallInfo(platform, state, shareInfo, error){
				var aa = JSON.stringify(shareInfo);
				var bb = JSON.stringify(error);
				if (state==1){
				// g_msg.tip('分享成功');
				 g_share.shareComplete();
				}else if (state==2){
        		 g_msg.tip("分享失败: 你的手机版本不支持分享");
        		}else if (state==3){
         		 g_msg.tip('分享已取消');
        		}
        }
