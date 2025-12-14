/*
 Navicat Premium Data Transfer

 Source Server         : Local
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : trip_hub

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 10/12/2025 12:58:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '轮播图',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `relation_id` int NULL DEFAULT NULL COMMENT '关联跳转id',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '轮播图' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of carousel
-- ----------------------------
INSERT INTO `carousel` VALUES (8, '敦煌自由行', 'http://localhost:9000/img/c769a730e87c48e0b8ce8904694df6f3.jpg', 5, 51, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `carousel` VALUES (9, '大理古城', 'http://localhost:9000/img/c51f9613dc54409e9fb2053e2ea891fd.png', 4, 52, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `carousel` VALUES (10, '连云港', 'http://localhost:9000/img/6628887c4d09461fa8d25aa7968465f0.jpg', 3, 53, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `carousel` VALUES (11, '鼓浪屿', 'http://localhost:9000/img/7609e34239644fb38ef26376c10feef2.png', 2, 54, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `carousel` VALUES (12, '广州长隆', 'http://localhost:9000/img/ae900c4f31124b73b8b7464e8e81e92a.jpg', 1, 56, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NULL DEFAULT NULL COMMENT '用户',
  `relation_id` int NULL DEFAULT NULL COMMENT '关联id',
  `type` int NULL DEFAULT NULL COMMENT '收藏类型，1 景点、2 美食、3 酒店',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES (20, NULL, 65, 1, 0, '董庆磊', '2025-12-10 12:58:18', '董庆磊', '2025-12-10 12:58:18');
INSERT INTO `collect` VALUES (21, NULL, 60, 1, 0, '董庆磊', '2025-12-10 12:58:18', '董庆磊', '2025-12-10 12:58:18');
INSERT INTO `collect` VALUES (22, NULL, 57, 1, 0, '董庆磊', '2025-12-10 12:58:18', '董庆磊', '2025-12-10 12:58:18');
INSERT INTO `collect` VALUES (23, NULL, 54, 1, 0, '董庆磊', '2025-12-10 12:58:18', '董庆磊', '2025-12-10 12:58:18');
INSERT INTO `collect` VALUES (24, NULL, 52, 1, 0, NULL, '2025-12-10 12:58:18', NULL, '2025-12-10 12:58:18');
INSERT INTO `collect` VALUES (25, 113, 51, 1, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');
INSERT INTO `collect` VALUES (26, 113, 6, 2, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');
INSERT INTO `collect` VALUES (27, 113, 4, 3, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面',
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `price` decimal(10, 0) NULL DEFAULT NULL COMMENT '价格',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `inventory` int NULL DEFAULT NULL COMMENT '库存',
  `short_recommend` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简短推荐',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '详情内容',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES (4, 'http://localhost:9000/img/5a17d2fd53b4479c93061903305265bc.jpg', '香菇干', 39, '云南滇欣', 98, '干蘑菇特产煲汤食材', '<p><img src=\"http://localhost:9000/img/e0ec79db798b4e9d9fac3e5ac2b996a8.jpg\"></p>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `food` VALUES (5, 'http://localhost:9000/img/ea0f34997a934db7a0273a60824e8584.jpg', '菌汤包', 15, '云南滇欣', 99, '西云珍坊菌菇汤料包煲汤炖鸡', '<p><img src=\"http://localhost:9000/img/c66a6c40fda143a9999eb586dc5234b6.jpg\"></p>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `food` VALUES (6, 'http://localhost:9000/img/b87aa966558e4243b4a04c8c80f12e55.jpg', '长白山无糖红参丁', 22, '亳州市', 99, '红参段吉林人参', '<p><img src=\"http://localhost:9000/img/8a168ac555394ef8860fb78a558c436f.jpg\"></p>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `food` VALUES (7, 'http://localhost:9000/img/1dce73cf20a746c79c52f834c33fe45e.jpg', '红参人参须', 88, '亳州市', 99, '西洋参须粗细须', '<p><img src=\"http://localhost:9000/img/68a44dc983f74d52bb6719abbb1a569e.jpg\"></p>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `food` VALUES (8, 'http://localhost:9000/img/7bbec69ff7f04c2288489b9a383a57bc.jpg', '无糖红参', 323, '东北吉林', 99, '人参个白参无须红参个无糖片', '<p><img src=\"http://localhost:9000/img/8a1261884570456fbe048368b84febb2.jpg\"></p>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `food` VALUES (9, 'http://localhost:9000/img/e839990568bb40cd87395a8f984f3770.jpg', '野生黄芪', 45, '吉林省百源', 99, '长白山道地黄芪片批发中药材无硫黄芪片', '<p><img src=\"http://localhost:9000/img/2ed6b6fb6bd64b42be76adca01ab44b1.jpg\"></p>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for hotel
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面',
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `price` decimal(10, 0) NULL DEFAULT NULL COMMENT '价格',
  `short_recommend` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简要推荐',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `inventory` int NULL DEFAULT NULL COMMENT '库存',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '详情内容',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` VALUES (4, 'http://localhost:9000/img/0783d7c2fd7a4cc298b34140e5d1e59e.jpg', '美景客栈', 120, '周边餐饮、娱乐设施完备，交通便利', '朝阳区弘善东路弘善家园213号楼', 99, '<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dd class=\"cont\" tabindex=\"0\">基本信息</dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dd class=\"cont\" tabindex=\"0\"><span class=\"item\" tabindex=\"0\">17间客房</span></dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">酒店简介</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<div class=\"words\" tabindex=\"0\">\n<p tabindex=\"0\">北京美景客栈位于朝阳区弘善家园，与弘善东路毗邻，周边餐饮、娱乐设施完备，交通便利。<br tabindex=\"0\">客栈客房整洁干净，房内设有24小时热水、独立淋浴间等，设施齐全，服务热情周到，是你出差、旅行的便捷之所。</p>\n</div>\n</dd>\n</dl>\n<p class=\"line\" tabindex=\"0\">&nbsp;</p>\n<div tabindex=\"0\">\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">入离时间</dt>\n<dd class=\"cont\" tabindex=\"0\">入住时间：入住当天12:30-23:00<br tabindex=\"0\">离店时间：离店当天12:00前</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">宠物</dt>\n<dd class=\"cont\" tabindex=\"0\">不可携带宠物。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">外宾</dt>\n<dd class=\"cont\" tabindex=\"0\">不接待外宾，该酒店只开放给持中国身份证的用户入住。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">膳食安排</dt>\n<dd class=\"cont\" tabindex=\"0\">不提供早餐。</dd>\n</dl>\n</div>\n</div>\n<p class=\"line\" tabindex=\"0\">&nbsp;</p>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">停车服务</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">停车场</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">无充电桩</span></p>\n</dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">网络设施</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">公共区WIFI</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">宽带上网</span></p>\n</dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">前台服务</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">行李寄存</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">部分时段前台服务</span></p>\n</dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">通用设施</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">电梯</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">空调</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">24小时热水</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">吹风机</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">所有场所禁烟</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">无烟房</span></p>\n</dd>\n</dl>\n</div>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `hotel` VALUES (5, 'http://localhost:9000/img/72d8a34767b04661accf47e573811ee5.jpg', '速8酒店', 287, '地处亚运村、国际展览中心的商圈辐射范围', '朝阳区和平西街小黄庄路1号', 99, '<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">基本信息</dt>\n<dd class=\"cont\" tabindex=\"0\"><span class=\"item\" tabindex=\"0\">76间客房</span></dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">酒店简介</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<div class=\"words collapse\" tabindex=\"0\">\n<p tabindex=\"0\">速8酒店（北京国展店）坐落在北三环和平西桥南侧繁华地带，地处亚运村、国际展览中心的商圈辐射范围，紧邻雍和宫、长安街、地坛、北海公园等旅游景点。<br tabindex=\"0\">速8酒店（北京国展店）地理位置十分优越，交通便利，经过市中心的地铁5号线和平西桥站近在咫尺，十余条公交线路纵横交错，四通八达，从酒店驱车前往国际会展中心仅需8分钟车程，距首都国际机场30分钟即可到达，距西客站25公里，令您的出行快速便捷。<br tabindex=\"0\">酒店拥有温馨、干净、舒适的客房，现代化的设施，优质、友好的服务是您在北京商务活动、休闲度假的理想选择。</p>\n</div>\n<p class=\"more\" tabindex=\"0\">展开全部<span class=\"iconfont\" tabindex=\"0\"></span></p>\n</dd>\n</dl>\n<p class=\"line\" tabindex=\"0\">&nbsp;</p>\n<div tabindex=\"0\">\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">入离时间</dt>\n<dd class=\"cont\" tabindex=\"0\">入住时间：入住当天14:00后<br>离店时间：离店当天12:00前</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">儿童政策</dt>\n<dd class=\"cont\" tabindex=\"0\">每间客房最多容纳0名0至17岁儿童与成人共用现有床铺</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">宠物</dt>\n<dd class=\"cont\" tabindex=\"0\">不可携带宠物。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">外宾</dt>\n<dd class=\"cont\" tabindex=\"0\">不接待外宾，该酒店只开放给持中国身份证的用户入住。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">膳食安排</dt>\n<dd class=\"cont\" tabindex=\"0\">提供早餐<br>早餐类型：中式。</dd>\n</dl>\n</div>\n</div>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `hotel` VALUES (6, 'http://localhost:9000/img/51031d45ce59446293a27e4b6b96fdd3.jpg', '相约青年旅舍', 88, ' 不接待外宾，该酒店只开放给持中国身份证的用户入住', '丰台区南街恒悦城15号地块1号楼', 99, '<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">基本信息</dt>\n<dd class=\"cont\" tabindex=\"0\"><span class=\"item\" tabindex=\"0\">8间客房</span></dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">酒店简介</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<div class=\"words\" tabindex=\"0\">\n<p tabindex=\"0\">&nbsp;</p>\n</div>\n</dd>\n</dl>\n<p class=\"line\" tabindex=\"0\">&nbsp;</p>\n<div tabindex=\"0\">\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">入离时间</dt>\n<dd class=\"cont\" tabindex=\"0\">入住时间：入住当天14:00-22:30<br>离店时间：离店当天12:00前</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">宠物</dt>\n<dd class=\"cont\" tabindex=\"0\">不可携带宠物。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">外宾</dt>\n<dd class=\"cont\" tabindex=\"0\">不接待外宾，该酒店只开放给持中国身份证的用户入住。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">膳食安排</dt>\n<dd class=\"cont\" tabindex=\"0\">不提供早餐。</dd>\n</dl>\n</div>\n</div>\n<p class=\"line\" tabindex=\"0\">&nbsp;</p>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">停车服务</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">无充电桩</span></p>\n</dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">网络设施</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">公共区WIFI</span></p>\n</dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">前台服务</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">行李寄存</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">部分时段前台服务</span></p>\n</dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">通用设施</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">电梯</span></p>\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">空调</span></p>\n</dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">清洁服务</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<p class=\"device\" tabindex=\"0\"><span class=\"iconfont\" tabindex=\"0\"></span><span tabindex=\"0\">洗衣房</span></p>\n</dd>\n</dl>\n</div>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `hotel` VALUES (7, 'http://localhost:9000/img/5db2287800214c1e8d12070dc44daa95.jpg', '北京佳和祥瑞宾馆', 98, ' 宾馆位于密云县康居南区综合楼甲，地理位置优越', '密云区康居南区综合楼甲15号', 99, '<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">基本信息</dt>\n<dd class=\"cont\" tabindex=\"0\"><span class=\"item\" tabindex=\"0\">21间客房</span></dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">酒店简介</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<div class=\"words\" tabindex=\"0\">\n<p tabindex=\"0\">宾馆位于密云县康居南区综合楼甲，地理位置优越，内设大床，双床，三人间，环境干净整洁，欢迎您的入住。</p>\n</div>\n</dd>\n</dl>\n<p class=\"line\" tabindex=\"0\">&nbsp;</p>\n<div tabindex=\"0\">\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">入离时间</dt>\n<dd class=\"cont\" tabindex=\"0\">入住时间：入住当天12:00后<br>离店时间：离店当天12:00前</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">儿童政策</dt>\n<dd class=\"cont\" tabindex=\"0\">每间客房最多容纳0名0至12岁儿童与成人共用现有床铺</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">宠物</dt>\n<dd class=\"cont\" tabindex=\"0\">不可携带宠物。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">外宾</dt>\n<dd class=\"cont\" tabindex=\"0\">接待外宾。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">膳食安排</dt>\n<dd class=\"cont\" tabindex=\"0\">不提供早餐。</dd>\n</dl>\n</div>\n</div>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `hotel` VALUES (8, 'http://localhost:9000/img/aa556e49339d469d9605b931967f6bd1.jpg', '化工大学招待所', 256, '紧邻中国国际展览中心，毗邻亚奥商圈', '朝阳区北三环东路15号北京化工大学西门内', 99, '<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">基本信息</dt>\n<dd class=\"cont\" tabindex=\"0\"><span class=\"item\" tabindex=\"0\">59间客房</span></dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">酒店简介</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<div class=\"words collapse\" tabindex=\"0\">\n<p tabindex=\"0\">北京化工大学招待所座落于北京化工大学校园内，位于北三环东路繁华地段，紧邻中国国际展览中心，毗邻亚奥商圈，交通便利。<br tabindex=\"0\">　　北京化工大学招待所共有豪华套间、标准间、大床间、单人间等多种房型，另设有商务中心、会议室、中式餐厅、KTV包间等有接待大型团体及VIP贵宾的能力，2004年通过了国际标准质量管理体系认证，具有良好的社会声誉和信誉，是国际、国内宾客来京学术交流、商务会议、探亲旅游的理想住所。</p>\n</div>\n<p class=\"more\" tabindex=\"0\">展开全部<span class=\"iconfont\" tabindex=\"0\"></span></p>\n</dd>\n</dl>\n<p class=\"line\" tabindex=\"0\">&nbsp;</p>\n<div tabindex=\"0\">\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">入离时间</dt>\n<dd class=\"cont\" tabindex=\"0\">入住时间：入住当天14:00后<br>离店时间：离店当天12:00前</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">儿童政策</dt>\n<dd class=\"cont\" tabindex=\"0\">每间客房最多容纳0名12至17岁儿童与成人共用现有床铺</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">宠物</dt>\n<dd class=\"cont\" tabindex=\"0\">不可携带宠物。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">外宾</dt>\n<dd class=\"cont\" tabindex=\"0\">不接待外宾，该酒店只开放给持中国身份证的用户入住。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">膳食安排</dt>\n<dd class=\"cont\" tabindex=\"0\">提供单点早餐<br>早餐时间：周一至周日 07:00-09:00。</dd>\n</dl>\n</div>\n</div>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `hotel` VALUES (9, 'http://localhost:9000/img/8743f79e600c455b80111647bbaed956.jpg', '兴和居酒店', 198, '坐落在密云风景区', '密云区季庄小区21号', 99, '<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">基本信息</dt>\n<dd class=\"cont\" tabindex=\"0\"><span class=\"item\" tabindex=\"0\">30间客房</span></dd>\n</dl>\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">酒店简介</dt>\n<dd class=\"cont\" tabindex=\"0\">\n<div class=\"words\" tabindex=\"0\">\n<p tabindex=\"0\">坐落在密云风景区，北京兴和居宾馆在北京占尽地理之宜。<br tabindex=\"0\">对于入住酒店的旅客来说，闲暇时间去附近的密云奥林匹克公园和冶仙塔游玩是一个不错的选择。<br tabindex=\"0\">旅客可以在闲暇时间去酒店的休闲区，提升健康幸福感。</p>\n</div>\n</dd>\n</dl>\n<p class=\"line\" tabindex=\"0\">&nbsp;</p>\n<div tabindex=\"0\">\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">入离时间</dt>\n<dd class=\"cont\" tabindex=\"0\">入住时间：入住当天14:00后<br>离店时间：离店当天12:00前</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">儿童政策</dt>\n<dd class=\"cont\" tabindex=\"0\">每间客房最多容纳0名0至17岁儿童与成人共用现有床铺</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">宠物</dt>\n<dd class=\"cont\" tabindex=\"0\">不可携带宠物。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">外宾</dt>\n<dd class=\"cont\" tabindex=\"0\">不接待外宾，该酒店只开放给持中国身份证的用户入住。</dd>\n</dl>\n</div>\n<div tabindex=\"0\">\n<dl class=\"desc_item clearfix\" tabindex=\"0\">\n<dt class=\"name\" tabindex=\"0\">膳食安排</dt>\n<dd class=\"cont\" tabindex=\"0\">不提供早餐。</dd>\n</dl>\n</div>\n</div>', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for icon
-- ----------------------------
DROP TABLE IF EXISTS `icon`;
CREATE TABLE `icon`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'icon图标',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `relation_id` int NULL DEFAULT NULL COMMENT '关联跳转id',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'icon图标' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of icon
-- ----------------------------
INSERT INTO `icon` VALUES (29, '连', 'http://localhost:9000/img/f21b31f5f7e0417885dbf687c94b0aef.png', 1, 51, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `icon` VALUES (30, '大', 'http://localhost:9000/img/97f58d6c90ce412cade858398c7248fb.png', 2, 52, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `icon` VALUES (31, '丽', 'http://localhost:9000/img/a5406f0edd3248b48b123905ca99c7ec.png', 3, 53, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `icon` VALUES (32, '美', 'http://localhost:9000/img/0c1e68a07bbf4500abc8466f10322e08.png', 4, 54, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `icon` VALUES (33, '游', 'http://localhost:9000/img/8159b222c78348a5b6e2b9ecde3def5c.png', 5, 55, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `icon` VALUES (34, '景', 'http://localhost:9000/img/746c70cca64443d8b00511c31e34a3d3.png', 6, 56, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `icon` VALUES (35, '风', 'http://localhost:9000/img/970e07f39f9c4b6486fac30a8ae743a9.png', 7, 57, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `icon` VALUES (36, '画', 'http://localhost:9000/img/412610b4769e4cb788d8519fbc96e7f3.png', 8, 61, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `icon` VALUES (37, '如', 'http://localhost:9000/img/89a9ac8f72994b7fa4e6b7a2745f98ca.png', 9, 65, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `icon` VALUES (38, '看', 'http://localhost:9000/img/f7642743aed24053ab16a9c715530dee.png', 10, 66, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int NULL DEFAULT NULL COMMENT '订单类型，1 景点、2 美食、3 酒店',
  `user_id` int NULL DEFAULT NULL COMMENT '用户',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态：1 待发货、2 待收货、3 已收货、4 退货中、5 已退货',
  `relation_id` int NULL DEFAULT NULL COMMENT '关联id',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (44, 2, 113, '2', 4, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');
INSERT INTO `order` VALUES (45, 3, 113, '3', 4, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');
INSERT INTO `order` VALUES (46, 1, 113, '5', 51, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');
INSERT INTO `order` VALUES (47, 1, 113, '5', 51, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');
INSERT INTO `order` VALUES (48, 1, 113, '5', 51, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');
INSERT INTO `order` VALUES (49, 1, 113, '5', 51, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');
INSERT INTO `order` VALUES (50, 1, 113, '5', 51, 0, '樟洋', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for scenic_spot
-- ----------------------------
DROP TABLE IF EXISTS `scenic_spot`;
CREATE TABLE `scenic_spot`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '景点封面图',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '景点导航图',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '景点地址',
  `introduce` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '景点介绍',
  `price` int NULL DEFAULT NULL COMMENT '门票价格',
  `inventory` int NULL DEFAULT NULL COMMENT '库存',
  `short_recommend` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简单推荐',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '景点' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scenic_spot
-- ----------------------------
INSERT INTO `scenic_spot` VALUES (51, '敦煌自由行', 'http://localhost:9000/img/2c367e789c1b4575b473f38ed2f1c259.jpg', 'http://localhost:9000/img/4f098411e24f4bbeb7ff087545a88417.jpg', '敦煌', '<p>产品特色</p>\n<p>#来敦煌的N个理由#<br><br>【历史厚重】作为西北的一颗明珠，这里有莫高窟飞天壁画、鸣沙山的黄沙海洋、月牙泉的清韵、阳关玉门关的历史沧桑，魔鬼城的大气磅礴......<br><br>【价格优惠】敦煌物价较低，特产物美价廉，价格超划算！<br><br>【玩法轻松】敦煌地区景点相对集中，路况非常好，到了当地可以选择打车或租车自驾即可，价格便宜，玩法轻松！<br><br>【清肺避霾】当严重的雾霾席卷着中国北方的时候，敦煌俨然成为远离雾霾的一片净土。这里的天很蓝，云很白，空气很清新，来到这里，畅快地呼吸，自由地奔跑</p>\n<p><img src=\"http://localhost:9000/img/276ee5df5ea744cf89ff80f3c1eeef27.png\"></p>\n<p><img src=\"http://localhost:9000/img/bf4e8651fd56483d9009698746995349.png\"></p>', 235, 101, '莫高窟飞天壁画、鸣沙山骑骆驼、阳关玉门关', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (52, '大理古城', 'http://localhost:9000/img/3fc8e6708c18447d9fc3ae78a6369ed1.png', 'http://localhost:9000/img/68c2ece082b94a4791ed47df451df307.jpg', '大理市', '<p><img src=\"http://localhost:9000/img/15c466d31404497c9800aeae75217af2.jpg\"></p>\n<p><img src=\"http://localhost:9000/img/3148c8bd4c90439fbdb224dcb529d161.jpg\"></p>', 1500, 99, '深度游玩云南南北精华景点', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (53, '连云港', 'http://localhost:9000/img/5be62cc730bf45b4b853b02299d4f1ba.jpg', 'http://localhost:9000/img/97ce578148384a7ab33a23c3eb8a63cb.jpg', '连云港', '<p class=\"detail_journey_title\">产品特色</p>\n<p>海港山城<strong>连云港</strong>，集&ldquo;海&rdquo;（连岛海滨浴场）、&ldquo;古&rdquo;（海州古城、民主路老街、连云老街、六朝一条街）、&ldquo;神&rdquo;（花果山）、&ldquo;幽&rdquo;（海上云台山）、&ldquo;奇&rdquo;（渔湾）、&ldquo;泉&rdquo;（东海温泉），是一座山、海、港、城相依相拥的城市。素有&ldquo;东海第一胜境&rdquo;之称。</p>\n<p><img src=\"http://localhost:9000/img/7758954467f5484f932e01582dbe1339.jpg\"></p>\n<p><img src=\"http://localhost:9000/img/7bb4d2c349644d02b0d416d68c4957d4.jpg\"></p>', 2399, 99, '走进 “孙悟空的降生之地” 花果山，观奇异山水', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (54, '鼓浪屿', 'http://localhost:9000/img/f81c25dd1f5f4426a948725f9bdf211c.png', 'http://localhost:9000/img/4d027d4beb5a49fc83a7f78547befea7.jpg', '厦门', '<p><img src=\"http://localhost:9000/img/981572e81ccd4bc9894d38d8d8a3d16d.jpg\"></p>\n<p><img src=\"http://localhost:9000/img/eefe4de0d23849eea3d6cdaa35192464.jpg\"></p>', 1200, 99, '鼓浪屿旅拍体验，留下鼓浪屿大片', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (55, '哈尔滨', 'http://localhost:9000/img/32ce4012646e4b958c87158c1d50d9c6.png', 'http://localhost:9000/img/62a5513463c843609b5b2edbe5dad0b8.jpg', '哈尔滨', '<p>产品特色</p>\n<p>探寻西洋老建筑</p>\n<p>漫步在弥漫着欧洲风的中央大街、果戈里大街、老道外等古老街区中，看着一座座色调明快的哥特式、巴洛克式、拜占庭式等各种风格的教堂和楼宇，是否迷了你的眼，错觉地以为自己站在欧洲某城市的街道中？这也正是哈尔滨被称为&ldquo;东方小巴黎&rdquo;的原因。</p>\n<p><img src=\"http://localhost:9000/img/bf20253bc31d4c9aaaa70d6f8e711fe1.jpg\"></p>\n<p><img src=\"http://localhost:9000/img/8078aef6588b472ba145c986f9af2c07.jpg\"></p>', 1488, 99, '体验室内滑雪，打卡极地馆，静享温馨的亲子时光', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (56, '广州长隆', 'http://localhost:9000/img/38368f64486848e09d6ee16dd6d36006.jpg', 'http://localhost:9000/img/aa9d9cebc4114bb1b8c1d44a91a586c6.jpg', '广州长隆', '<p><img src=\"http://localhost:9000/img/c539b58fc7394acfaf421fea112d12f8.png\"></p>', 18888, 99, '一票游3园+大马戏+赠送自助晚餐', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (57, '长江三峡', 'http://localhost:9000/img/ed85948019aa4a7db2c1b1fb984811ff.jpg', 'http://localhost:9000/img/d5355d544f1c471c8f39597c04c85f7f.jpg', '重庆市', '<p><img src=\"http://localhost:9000/img/37c7b20164854f23ad92d0219f92e8f2.jpg\"></p>\n<p><img src=\"http://localhost:9000/img/7d8f6856b7104d9f9076526c42ffd506.jpg\"></p>', 1, 99, '住黄金游轮阳台江景房+1晚市区5钻酒店', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (58, '广西桂林', 'http://localhost:9000/img/d0e2a7ba3f014fa881497bd6b39b9a22.jpg', 'http://localhost:9000/img/26d1f778f88942b5a4a1b3250dee3b74.jpg', '广西桂林', '<p>产品特色</p>\n<p>&nbsp;</p>\n<p>精华景点：l</p>\n<p>v【正航四星船大漓江】全程约4小时360度欣赏漓江，20元人民币l</p>\n<p>v【古东瀑布】原生态瀑布群、&nbsp;CCTV新闻报道&ldquo;这是可触摸的瀑布l</p>\n<p>v【银子岩】世界岩洞奇观，喀斯特地貌代表之一l</p>\n<p>v【世外桃源】央视著名广告《康美之恋》主要采景地</p>\n<p>v【象鼻山】桂林城徽，2017年南方春晚分会场</p>\n<p>v【梦幻或山水间】游览大型魔幻歌舞秀，欣赏桂林特色</p>\n<p>v【遇龙河】这如诗如画的风光，青山、绿水、古桥、小树，无处不让人魂牵梦绕。</p>\n<p><img src=\"http://localhost:9000/img/fabc5d9dfb114d9c97f459e99291b4a1.jpg\"></p>\n<p><img src=\"http://localhost:9000/img/4c07eca4e7834f0c9e4e68139f2c06c0.jpg\"></p>', 3500, 99, '央视著名广告《康美之恋》主要采景地', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (59, '广西南宁', 'http://localhost:9000/img/593d0718b5bf4839a4c9356242926a82.jpg', 'http://localhost:9000/img/8bcd827b4f63448b882519f297ce7762.jpg', '广西南宁', '<p>行程亮点：</p>\n<p>● 游览&ldquo;天下一滩&rdquo;【北海银滩】；</p>\n<p>● 漫步北海百年历史老城【百年老街】感受历史文化，品尝舌尖上的中国；</p>\n<p>● 游览&ldquo;中国美丽的十大海岛之一&rdquo;【涠洲岛】；</p>\n<p>● 游览涠洲岛之魂，观赏岛上极美极壮观的火山岩层【火山地质公园】；</p>\n<p><img src=\"http://localhost:9000/img/cc7f7ecd99494711b98e1805b52af6ed.jpg\"></p>\n<p><img src=\"http://localhost:9000/img/df8930338ba049ab94dc275c50c69edc.jpg\"></p>', 4460, 99, '天跨国大瀑布、北海银滩、百年老街、网红涠洲岛', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (60, '海南三亚', 'http://localhost:9000/img/34f39329717d4347b514a934709071de.jpg', 'http://localhost:9000/img/48791f8ce7db4c7186b42c36e58e9031.jpg', '海南三亚', '<p>看那清清的海水亲吻白白的柔沙；看那多情的椰树匍匐着嗅向浪花。看那绵延的碧翠环绕着有致的楼厦；看那蜿蜒的峰体拥抱着柔情的海湾。看那海中的波浪星星点点跳跃、闪烁，我又怀疑是不是海螺姑娘要出现，要不就是美人鱼要上岸了。抱歉！我处在美境中想象总是天马行空。那在这极美的地方也应该有动人的爱情吧？这哪能少得了呢！&ldquo;天涯海角&rdquo;&ldquo;鹿回头&rdquo;等等的唯美爱情传说，在这儿世代相传。我们都知道的那句爱情誓言：伴你去天涯海角！让我们动容。一切的一切使这方水土更加的浪漫灵秀。</p>\n<p>&nbsp;</p>\n<p>&middot;品质保障：绝不更换景点（不可抗力因素除外）；不压缩游览时间，不减少景点；</p>\n<p>&nbsp;</p>\n<p>&middot;精选景点：网红蜈支洲岛、亚特兰蒂斯水世界、南山佛教文化苑、热带天堂森林公园、帆船出海体验、船游漓江+遇龙河竹筏漂流、山水间歌舞秀+桂林水陆两栖夜游、阳朔西街、象鼻山、北海银滩等；</p>\n<p>&middot;特别赠送：帆船出海体验+珊瑚礁潜水、山水间歌舞秀+桂林水陆两栖夜游+遇龙河竹筏漂流，五大超级赠送，不虚此行；</p>\n<p><img src=\"http://localhost:9000/img/ea5a19d0210f4525b07a52a866567a18.jpg\"></p>', 3400, 99, '网红蜈支洲岛、天堂森林公园、南山文化苑、北海银滩、兴坪漓江', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (61, '广西北海', 'http://localhost:9000/img/1838f6d63109404d874a6d0dff11617b.jpg', 'http://localhost:9000/img/4cf381aafe834b5e8a14594340452e16.jpg', '广西北海', '<p>产品特色</p>\n<p>&nbsp;</p>\n<p>行程亮点：</p>\n<p>&nbsp;</p>\n<p>● 游览&ldquo;天下 一滩&rdquo;【北海银滩】；</p>\n<p>&nbsp;</p>\n<p>● 漫步北海百年历史老城【百年老街】感受历史文化，品尝舌尖上的中国；</p>\n<p>&nbsp;</p>\n<p>● 游览&ldquo;中国美丽的十大海岛之一&rdquo;【涠洲岛】；</p>\n<p>&nbsp;</p>\n<p>● 游览涠洲岛之魂，观赏岛上极美极壮观的火山岩层【火山地质公园】；</p>\n<p><img src=\"http://localhost:9000/img/5767fe2df8f648c19cffc864ed5cf982.jpg\"></p>\n<p><img src=\"http://localhost:9000/img/6ce5a359116b4b19974c9eef2792ba6c.jpg\"></p>', 1998, 99, '漫步北海百年历史老街，品尝舌尖上的中国', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (62, '南京', 'http://localhost:9000/img/d161b3a36deb4399bc2f9e45790e2af2.png', 'http://localhost:9000/img/9a58fba588e342338c0ea1d8400108d9.jpg', '南京', '<p><img src=\"http://localhost:9000/img/db80d24b4cc5429aa453036476f71135.jpg\"></p>\n<p><img src=\"http://localhost:9000/img/d0be5d8d53604308b48421148db9328d.png\"></p>', 2000, 99, '大报恩寺&老门东&夫子庙&秦淮夜游丨佛光幻影', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (63, '苏州+乌镇', 'http://localhost:9000/img/7fee86a4c3c744169655a5e0c6ba53d7.png', 'http://localhost:9000/img/ec54f33c2c4a425baf0b8b8ce415b648.jpg', '苏州', '<p><img src=\"http://localhost:9000/img/cb879c6b042f4a608d56f46a35d98450.jpg\"></p>', 2577, 99, '1晚品牌5钻酒店，1晚夜宿乌镇，特色三宴', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (64, '张家界', 'http://localhost:9000/img/50dada068a8a43ad85bf42e0c74f353e.jpg', 'http://localhost:9000/img/e590165817684d4598de19d00c70d105.jpg', '张家界', '<p><img src=\"http://localhost:9000/img/fdda22e5677b4db995a5341148876a1c.jpg\"></p>', 2557, 99, '百龙电梯+芙蓉镇门票+游船环保车+沱江泛舟', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (65, '兰州', 'http://localhost:9000/img/730f1a5efe754927aaebea9e7e924e7e.png', 'http://localhost:9000/img/a2aaa12109bb43cf9e1d9890268819fa.jpg', '兰州', '<p>产品特色</p>\n<p>大美西北欢迎您，大美西北等您来！丝路经典线路，质量有保证，好评如潮，口碑产品，带您体验丝路文化精髓！</p>\n<p>如您选择自己订机票，请点击&nbsp;西北大环线10日当地游&nbsp;6145016</p>\n<p>如您对北疆线路感兴趣，请点击&nbsp;含往返机票北疆+天山大环线10日&nbsp;19596504</p>\n<p>如您对南疆线路感兴趣，请点击&nbsp;含往返机票环游南疆10日&nbsp;20940113</p>\n<p>如您对甘南线路感兴趣，请点击&nbsp;含往返机票甘南7日&nbsp;19738822</p>\n<p>&nbsp;</p>\n<p>1、青海湖、茶卡盐湖，可可西里，翡翠湖，G315U型网红公路，水上雅丹，西台吉乃尔湖，敦煌莫高窟，鸣沙山月牙泉，嘉峪关，七彩丹霞...甘肃青海全景游览，不留遗憾。</p>\n<p>2、行程以景观文化多样性著称，有雪山、草原、花海、湖泊、沙漠、戈壁、雅丹地貌、丹霞彩丘、牛羊马群等多种自然景观。</p>\n<p>3、沿途边走边玩边摄，环保互助，深度纯玩，全程无购物，结合了休闲户外、摄影采风等深度游方式，老少皆宜。</p>\n<p>4、西北地区聚集了大量的少数民族人群，具有独特的人文特点汇合了汉、藏、伊斯兰等多样性文化，还有各种西北美食，度假期间玩好吃好。</p>\n<p>5、经验驾导，十数年成熟理念，贴心30万保额专业旅游意外险，为您的旅程保驾护航！</p>\n<p><img src=\"http://localhost:9000/img/c674d15abd53496bba2a77e5db9fb7be.png\"></p>', 5889, 99, '西台吉乃尔湖，二进鸣沙山沙漠，观水上雅丹奇景', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `scenic_spot` VALUES (66, '横店影视城', 'http://localhost:9000/img/bef78436e34d4ed7a33f96fe35364c6e.jpg', 'http://localhost:9000/img/953a051902e747c48f161e961d81aa95.jpg', '横店影视城', '<p class=\"detail_journey_title\">产品特色</p>\n<p>5景区套票说明：白天4景区加梦幻谷（含演艺秀）。4景区指：可在秦王宫、清明上河图、明清宫苑、广州街香港街、梦外滩、 圆明新园 （春苑）、 圆明新园 （夏苑）日景中任选4景区。门票有效期3天，每个景区限进1次。</p>\n<p>【注意事项】</p>\n<p>①、入园方式：预达日先至酒店前台激活门票再至景区游玩，需身份证检票入园；</p>\n<p>②、各景区开闭园时间以景区公告为准，恕不另行通知，敬请谅解；</p>\n<p>③、儿童票：随行儿童指 2010年（含）后出生儿童，儿童身高超出1.6米请携带户口本/身份证检票，否则按景区挂牌价补票入园；</p>\n<p>④产品一经预定，不支持修改出行人信息。</p>\n<p><img src=\"http://localhost:9000/img/07f4d2afad7e416997099d3a23342c96.jpg\"></p>', 1145, 99, '横店当地向导服务，风趣幽默的陪你逛吃横店的所有 ', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级菜单',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单路径',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (11, 'Home', '首页', '', '/', 'HomeFilled', 99, 0, '1', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_menu` VALUES (14, 'Sys', '系统设置', NULL, '/systemManage', 'Setting', 22, 0, NULL, '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_menu` VALUES (52, 'User', '用户管理', '', '/user', '', 88, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_menu` VALUES (64, 'Food', '美食管理', '', '/food', '', 44, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_menu` VALUES (65, 'Hotel', '酒店管理', '', '/hotel', '', 33, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_menu` VALUES (66, 'carousel', '轮播图管理', '', '/carousel', '', 77, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_menu` VALUES (67, 'icon', '图标管理', '', '/icon', '', 66, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_menu` VALUES (68, 'scenicSpot', '景点管理', '', '/scenicSpot', '', 55, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_menu` VALUES (69, 'Order', '订单管理', '', '/order', '', 30, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (2, 'common', '普通管理员', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `menu_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (3, 'common', 'User', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_role_menu` VALUES (4, 'common', 'Home', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_role_menu` VALUES (5, 'common', 'Food', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_role_menu` VALUES (6, 'common', 'Hotel', 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '账号是否启用，1 启用',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$UIjFCcU79qZ/qaF2/D3kPeSuFrLvbEMbzxAozHFx3Xv1BV/MGEDny', '管理员', '2131', '123123', 1, 0, NULL, '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `sys_user` VALUES (3, 'ikun', '$2a$10$hCA2lNmxM4LSyKuXKNKsyOduA0bSSEvtlDqORBXFw406tkdBB.D8O', 'Ikun', '13565678765', '', 1, 0, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户编码',
  `role_code` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (2, 'ikun', 'common', 1, '管理员', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nick_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `balance` int NULL DEFAULT NULL COMMENT '余额',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` tinyint NULL DEFAULT NULL COMMENT '性别，1 男 2 女',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `enable` tinyint NULL DEFAULT NULL COMMENT '账号是否启用，1 启用',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标志，1 表示删除',
  `create_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_user` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (7, '俞红环', '15502227374', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, 'http://localhost:9000/img/8b66fd87da9e42bb973feb70b1b9432e.jpg', 1, 0, '', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (8, '董庆磊', '15100030807', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, 'http://localhost:9000/img/22f9d94e3b004ef79844391445a92e5e.jpg', 1, 0, '', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (9, '俟刚', '15004488722', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, 'http://localhost:9000/img/0d5ff81d809247359c82fdbf5f23269a.jpg', 1, 0, '', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (10, '王会裕', '13701640840', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, 'http://localhost:9000/img/ee2554cf3f1542ccadfb218a5604d97b.jpg', 1, 0, '', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (11, '钱莉春', '13108781991', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, 'http://localhost:9000/img/688bd6e8d20941a88cd9562953712e9b.jpg', 1, 0, '', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (12, '廉弘福', '15105066136', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, 'http://localhost:9000/img/46b8baba36c3437caef36f0c0f4ec9dd.jpg', 1, 0, '', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (13, '那心', '13200551041', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, 'http://localhost:9000/img/12dcbbaae11e498c8bb8f5225b0aaddd.jpg', 1, 0, '', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (14, '丘静', '15005506413', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, 'http://localhost:9000/img/5360c11fd61d4f6ea72c22c9e15cbe09.jpg', 1, 0, '', '2025-12-10 12:58:18', '管理员', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (15, '徐静', '15706412027', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (16, '佘言', '15900136239', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (17, '苗艳黛', '13505554952', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (18, '徐韵', '13608424646', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (19, '晏霞', '13605656171', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (20, '古翔鸣', '13208385155', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (21, '阳丽', '13206401629', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (22, '劳娅妍', '15801301172', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (23, '淳雁凝', '13203485225', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (24, '孙彩芬', '13503446740', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (25, '舌德', '13801943524', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (26, '徐融', '13406465541', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (27, '甘辉', '13204304486', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (28, '闾玉莎', '13101327413', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (29, '融力榕', '15203661016', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (30, '丰亮武', '15308602150', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (31, '壤筠', '13005065363', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (32, '商厚宁', '15200851597', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (33, '何红', '13505272099', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (34, '堵庆建', '15807279053', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (35, '汲凤雅', '15603506088', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (36, '莫清', '15003662514', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (37, '澹卿荣', '13908850476', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (38, '孙莺玲', '13104998362', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (39, '包林', '13506320721', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (40, '孟中鹏', '13003521763', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (41, '纪玉枝', '15807491432', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (42, '娄丽', '13602984214', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (43, '宗康', '15606153155', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (44, '雷君美', '13003751581', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (45, '牟婵', '13204602624', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (46, '裴钧行', '15600050214', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (47, '谢蕊菁', '15007661607', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (48, '伊琼', '13005208381', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (49, '古邦', '15703475611', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (50, '席昭', '15203372779', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (51, '易坚', '15907858308', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (52, '麻广广', '15200082609', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (53, '阎绍', '13502961988', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (54, '门永德', '13002931262', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (55, '聂豪以', '13702798540', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (56, '凤佳', '13404403315', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (57, '任纯', '15804370585', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (58, '国壮', '13406866062', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (59, '宿顺', '15302792900', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (60, '殳娜希', '15808569073', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (61, '羊可希', '13606475965', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (62, '甘艳真', '15804862211', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (63, '言荣芳', '13802507570', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (64, '章娜', '15605768983', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (65, '詹有', '13201233985', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (66, '甘园娜', '15206795446', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (67, '乐翠', '15605072140', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (68, '雍琴', '15204730951', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (69, '屠薇', '15701893411', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (70, '卞诚飞', '15504961552', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (71, '孔瑶', '15102575268', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (72, '巫涛', '15606233933', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (73, '尚顺保', '15306172147', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (74, '童欢', '15500911539', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (75, '姚钧义', '13805047993', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (76, '佘泰子', '15101675806', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (77, '父启琛', '13407637765', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (78, '双媛璧', '15806890563', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (79, '褚馥琰', '15806257689', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (80, '乐若', '13308801180', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (81, '管义', '13600391685', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (82, '台诚保', '13403498140', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (83, '裘艺', '13102522676', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (84, '乜力', '15506034592', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (85, '魏心', '15802972066', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (86, '红茗', '15304382280', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (87, '全壮', '13800283934', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (88, '孟妹', '13100648220', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (89, '米黛叶', '13802835058', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (90, '厉坚文', '13808226502', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (91, '苍仪宁', '13408694649', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (92, '能政胜', '13607256377', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (93, '澹艺', '15102786409', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (94, '阳枫娴', '13403663815', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (95, '督栋伯', '13804123329', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (96, '舌思', '15206422953', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (97, '时彪', '13502656621', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (98, '爱岚', '15808506604', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (99, '良羽梦', '13203460585', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (100, '温涛安', '15200853793', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (101, '宁达', '13407975259', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (102, '红娅茜', '15502167553', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 1, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (103, '相东信', '13104371136', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (104, '戈伦', '15501377862', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (105, '戈之富', '15502734603', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (106, '祁勇', '13900814748', NULL, '$2a$10$.jbPDIU6kT/naZLp/vmzpOOznOgQVaC5R1aJ7WcR.6SCLZGtTNnPm', 2, NULL, 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (112, '123', '13566676667', NULL, '$2a$10$.p.5Pva.BI03IhsGYwUB.uep3QwrJk8qIi/ugXdPKXTEDUXL5j5gu', 2, 'http://localhost:9000/img/162f54b12523467d8f6258faf8a11edf.webp', 1, 0, '', '2025-12-10 12:58:18', '', '2025-12-10 12:58:18');
INSERT INTO `user` VALUES (113, '樟洋', '13594677777', 1815, '$2a$10$vUyPwJ4ptGD99ul7oAkjjenfzwnKil9wNgmuJwRtMGxsPmNwWrANO', 1, 'http://localhost:9000/img/7535e7212b794ce993c41c555dbe131a.jpg', 1, 0, '', '2025-12-10 12:58:18', '樟洋', '2025-12-10 12:58:18');

SET FOREIGN_KEY_CHECKS = 1;
