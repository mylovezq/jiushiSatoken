/*
 Navicat Premium Data Transfer

 Source Server         : jiushi
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : www.jiushi.com:3306
 Source Schema         : satoken-auth

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 20/02/2024 16:49:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `base_auth_user`;
CREATE TABLE `base_auth_user`  (
  `id` bigint NOT NULL,
  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `client_type` int NOT NULL,
  `role_list` json NULL,
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `creator_id` bigint NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `updater_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone_client_index`(`phone` ASC, `client_type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_auth_user
-- ----------------------------
INSERT INTO `base_auth_user` VALUES (1730502379218472962, '17612520985', 0, '[]', '九十与你', 'irNgbXNDeL9B1holx4Exkw==', '2023-12-01 16:22:22', 0, NULL, NULL);

-- ----------------------------
-- Table structure for oauth2_client_user
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_client_user`;
CREATE TABLE `oauth2_client_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'oauth2_registered_client表id',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '此账号在此Client下的openid',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'root' COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否已删除',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `open_id`(`open_id` ASC) USING BTREE,
  UNIQUE INDEX `unq_idx_uid_cid`(`client_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'OAuth2 应用和用户的关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth2_client_user
-- ----------------------------

-- ----------------------------
-- Table structure for oauth2_registered_client
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_registered_client`;
CREATE TABLE `oauth2_registered_client`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用id',
  `client_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端名称',
  `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接入方的logo图标url',
  `client_secret` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '应用秘钥',
  `contract_scope` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '应用签约的所有权限, 多个用逗号隔开',
  `allow_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '应用允许授权的所有URL, 多个用逗号隔开',
  `is_code` tinyint(1) NULL DEFAULT NULL COMMENT '此 Client 是否打开模式：授权码（Authorization Code）',
  `is_implicit` tinyint(1) NULL DEFAULT NULL COMMENT '此 Client 是否打开模式：隐藏式（Implicit）',
  `is_password` tinyint(1) NULL DEFAULT NULL COMMENT '此 Client 是否打开模式：密码式（Password）',
  `is_client` tinyint(1) NULL DEFAULT NULL COMMENT '此 Client 是否打开模式：凭证式（Client Credentials）',
  `is_auto_mode` tinyint(1) NULL DEFAULT NULL COMMENT '是否自动判断此 Client 开放的授权模式 此值为true时：四种模式（isCode、isImplicit、isPassword、isClient）是否生效，依靠全局设置 此值为false时：四种模式（isCode、isImplicit、isPassword、isClient）是否生效，依靠局部配置+全局配置',
  `is_new_refresh` tinyint(1) NULL DEFAULT NULL COMMENT '单独配置此Client：是否在每次 Refresh-Token 刷新 Access-Token 时，产生一个新的 Refresh-Token [默认取全局配置]',
  `access_token_timeout` bigint UNSIGNED NULL DEFAULT 7200 COMMENT '单独配置此Client：Access-Token 保存的时间(单位秒) [默认取全局配置]',
  `refresh_token_timeout` bigint UNSIGNED NULL DEFAULT 7200 COMMENT '单独配置此Client：Refresh-Token 保存的时间(单位秒) [默认取全局配置]',
  `client_token_timeout` bigint UNSIGNED NULL DEFAULT 7200 COMMENT '单独配置此Client：Client-Token 保存的时间(单位秒) [默认取全局配置]',
  `past_client_token_timeout` bigint UNSIGNED NULL DEFAULT 7200 COMMENT '单独配置此Client：Past-Client-Token 保存的时间(单位：秒) [默认取全局配置]\r\n',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否已删除',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建人者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'root' COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `client_id`(`client_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'OAuth2 客户端' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
INSERT INTO `oauth2_registered_client` VALUES (1, '1001', '测试', 'a', '8de95b980182d32c9eb06840f973bc72e0ad603478c09d0b198067e038d63d7a', 'UD_REAL_NAME,UD_MY', 'https://www.baidu.com', 1, 1, 1, 1, 1, 1, 7200, 7200, 7200, 7200, 1, NULL, NULL, 'root', NULL, NULL);

-- ----------------------------
-- Table structure for oauth2_resource_scope
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_resource_scope`;
CREATE TABLE `oauth2_resource_scope`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源编码',
  `remarks` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `scope_field` json NULL COMMENT '根据权限回显字段，用于展示给登录用户授权时的字段',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'root' COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否已删除',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_resource_scope_code`(`code` ASC) USING BTREE COMMENT '资源范围编码'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源范围' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth2_resource_scope
-- ----------------------------
INSERT INTO `oauth2_resource_scope` VALUES (1, 'UD_REAL_NAME', NULL, '[{\"sort\": 1, \"label\": \"姓名\", \"fieldName\": \"name\", \"confirmShow\": true, \"desensitizedType\": null}, {\"sort\": 2, \"label\": \"账户\", \"fieldName\": \"phone\", \"confirmShow\": true, \"desensitizedType\": \"MOBILE_PHONE\"}, {\"sort\": 3, \"label\": \"身份证\", \"fieldName\": \"identityCard\", \"confirmShow\": true, \"desensitizedType\": \"ID_CARD\"}, {\"sort\": 3, \"label\": \"openId\", \"fieldName\": \"openId\", \"confirmShow\": false, \"desensitizedType\": null}, {\"sort\": 3, \"label\": \"性别\", \"fieldName\": \"gender\", \"confirmShow\": false, \"desensitizedType\": null}, {\"sort\": 3, \"label\": \"所在机构代码\", \"fieldName\": \"orgId\", \"confirmShow\": false, \"desensitizedType\": null}, {\"sort\": 3, \"label\": \"所在机构名字\", \"fieldName\": \"orgName\", \"confirmShow\": false, \"desensitizedType\": null}]', NULL, 'root', NULL, NULL, 1, '用户信息');

SET FOREIGN_KEY_CHECKS = 1;
