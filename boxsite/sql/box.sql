/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : box

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2015-03-27 19:10:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `goodsitem`
-- ----------------------------
DROP TABLE IF EXISTS `goodsitem`;
CREATE TABLE `goodsitem` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(200) NOT NULL,
  `ename` varchar(200) default NULL,
  `ps` int(11) default NULL,
  `eps` int(11) default NULL,
  `sid` varchar(100) default NULL,
  `sid2` varchar(100) default NULL,
  `sid3` varchar(100) default NULL,
  `cdesc` varchar(300) default NULL,
  `edesc` varchar(300) default NULL,
  `status` int(11) NOT NULL default '0',
  `crdate` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goodsitem
-- ----------------------------
INSERT INTO `goodsitem` VALUES ('1', '是的', 'yes', null, null, null, null, null, null, null, '0', null);

-- ----------------------------
-- Table structure for `goodsurl`
-- ----------------------------
DROP TABLE IF EXISTS `goodsurl`;
CREATE TABLE `goodsurl` (
  `id` int(11) NOT NULL auto_increment,
  `site` varchar(100) default NULL,
  `goodsurl` varchar(250) NOT NULL,
  `goodstype` int(11) default NULL,
  `name` varchar(200) default NULL,
  `qty` int(11) default NULL,
  `sid` varchar(100) default NULL,
  `ps` int(11) default NULL,
  `newps` int(11) default NULL,
  `imgurl` varchar(300) default NULL,
  `status` int(11) NOT NULL default '0',
  `crdate` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `goodsurl` (`goodsurl`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goodsurl
-- ----------------------------

-- ----------------------------
-- Table structure for `siteurl`
-- ----------------------------
DROP TABLE IF EXISTS `siteurl`;
CREATE TABLE `siteurl` (
  `id` int(11) NOT NULL auto_increment,
  `site` varchar(300) default NULL,
  `caturl` varchar(250) NOT NULL,
  `status` int(11) NOT NULL default '0',
  `crdate` datetime default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `u1` (`caturl`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of siteurl
-- ----------------------------

-- ----------------------------
-- Table structure for `wxpublic`
-- ----------------------------
DROP TABLE IF EXISTS `wxpublic`;
CREATE TABLE `wxpublic` (
  `id` int(11) NOT NULL auto_increment,
  `wxname` varchar(100) NOT NULL,
  `wxhao` varchar(100) default NULL,
  `openid` varchar(200) default NULL,
  `wpdesc` varchar(200) default NULL,
  `viewcount` int(11) default NULL,
  `zancount` int(11) default NULL,
  `topcount` int(11) default NULL,
  `imgurl` varchar(300) default NULL,
  `type` int(11) default NULL,
  `status` int(11) default '0',
  `crdate` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wxpublic
-- ----------------------------
INSERT INTO `wxpublic` VALUES ('1', '欢乐小游戏', 'happy_games', null, null, null, null, null, null, null, '0', null);
INSERT INTO `wxpublic` VALUES ('2', 'BTV北京你早', 'bjnzbtv', null, null, null, null, null, null, null, '0', null);

-- ----------------------------
-- Table structure for `wxsite`
-- ----------------------------
DROP TABLE IF EXISTS `wxsite`;
CREATE TABLE `wxsite` (
  `id` int(11) NOT NULL auto_increment,
  `siteid` varchar(100) NOT NULL,
  `searchurl` varchar(200) NOT NULL,
  `status` int(11) NOT NULL default '0',
  `crdate` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wxsite
-- ----------------------------

-- ----------------------------
-- Table structure for `wxtitle`
-- ----------------------------
DROP TABLE IF EXISTS `wxtitle`;
CREATE TABLE `wxtitle` (
  `id` int(11) NOT NULL auto_increment,
  `wxhao` varchar(100) default NULL,
  `wxname` varchar(100) default NULL,
  `openid` varchar(200) default NULL,
  `title` varchar(300) NOT NULL,
  `titleurl` varchar(300) NOT NULL,
  `titlekey` varchar(100) NOT NULL,
  `viewcount` int(11) unsigned zerofill default NULL,
  `zancount` int(11) unsigned zerofill default NULL,
  `status` int(11) default '0',
  `crdate` datetime default NULL,
  `udate` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wxtitle
-- ----------------------------
INSERT INTO `wxtitle` VALUES ('13', null, null, null, '太过分了,居然在餐厅里这样对待自己的妻子!', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206754582&idx=1&sn=788eee4577deba78f321982877639aa3&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206754582', null, null, null, '2015-03-26 19:32:14', null);
INSERT INTO `wxtitle` VALUES ('14', null, null, null, '活了十六年 性别突然变了 是男是女自己说了不算了', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206731539&idx=1&sn=a7f63a9d09db30fa11ab3f1f1a0d3df9&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206731539', null, null, null, '2015-03-25 20:13:33', null);
INSERT INTO `wxtitle` VALUES ('15', null, null, null, '恭喜香港实现愿望,近期赴港旅行团下跌60%,数万港人失业', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206706629&idx=1&sn=86151cb4e7fef35739cef39598df9a50&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206706629', null, null, null, '2015-03-24 20:03:45', null);
INSERT INTO `wxtitle` VALUES ('16', null, null, null, '给你两个老婆,你选哪个?', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206681376&idx=1&sn=92df867f1220e50497e0ab4eea1cdf8f&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206681376', null, null, null, '2015-03-23 20:12:02', null);
INSERT INTO `wxtitle` VALUES ('17', null, null, null, '她从一个富婆到扫厕所,74岁从头再来,翻身创业,资产过...', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206634443&idx=1&sn=1e843f55d9e5b4664cca65c4af8856a0&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206634443', null, null, null, '2015-03-21 19:57:55', null);
INSERT INTO `wxtitle` VALUES ('18', null, null, null, '16岁女孩:我这么漂亮,吸毒当然不要钱!', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206612437&idx=1&sn=203879b223c79043ea346dc3089333f6&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206612437', null, null, null, '2015-03-20 19:58:14', null);
INSERT INTO `wxtitle` VALUES ('19', null, null, null, '没想到赵丽蓉奶奶竟然骗了全国的观众27年', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206587942&idx=1&sn=00b455bbdf8096003849e90f68d72d4b&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206587942', null, null, null, '2015-03-19 19:41:16', null);
INSERT INTO `wxtitle` VALUES ('20', null, null, null, '记者冒死偷拍的温泉真相!你还敢约泡吗!?', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206541746&idx=1&sn=12a6bb44f66b0d5d604e80fde09c967e&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206541746', null, null, null, '2015-03-17 19:55:12', null);
INSERT INTO `wxtitle` VALUES ('21', null, null, null, '看了五遍,哈哈, 已笑抽!', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206517752&idx=3&sn=a5cd249873c2e94dc010d02c2d6d8f12&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206517752', null, null, null, '2015-03-16 19:47:34', null);
INSERT INTO `wxtitle` VALUES ('22', null, null, null, '有一张图,绝对是在说你,你信吗?', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206494610&idx=5&sn=a11551e5da02a160d52d9ddc953aaa57&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206494610', null, null, null, '2015-03-15 20:00:04', null);
INSERT INTO `wxtitle` VALUES ('23', null, null, null, '车上6个美女瞬间被活埋! 那些被大车压成饼的轿车!', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206452561&idx=1&sn=350fa1e7a4993cd2aa00ed942b2d57dd&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206452561', null, null, null, '2015-03-13 20:03:54', null);
INSERT INTO `wxtitle` VALUES ('24', null, null, null, '166万元车牌,浙c88888为何挂在面包车上?', 'http://mp.weixin.qq.com/s?__biz=MzA5OTg3MTcyOA==&mid=206429738&idx=1&sn=c13c6ff081b7ed9ea28c5fc76c8b3c28&3rd=MzA3MDU4NTYzMw==&scene=6#rd', 'MzA5OTg3MTcyOA==_206429738', null, null, null, '2015-03-12 19:45:36', null);
