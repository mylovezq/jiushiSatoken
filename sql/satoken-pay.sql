/*
 Navicat Premium Data Transfer

 Source Server         : jiushi
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : www.jiushi.com:3306
 Source Schema         : satoken-pay

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 20/02/2024 16:50:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pay_msg
-- ----------------------------
DROP TABLE IF EXISTS `pay_msg`;
CREATE TABLE `pay_msg`  (
  `id` bigint NOT NULL,
  `msg_body` json NOT NULL,
  `msg_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `err_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `msg_id`(`msg_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pay_msg
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
