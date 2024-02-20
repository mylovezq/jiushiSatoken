/*
 Navicat Premium Data Transfer

 Source Server         : jiushi
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : www.jiushi.com:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 20/02/2024 16:49:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'group_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'configuration description',
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'configuration usage',
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置生效的描述',
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '配置的类型',
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '配置的模式',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'public-config.yaml', 'satoken-dev', '# Sa-Token配置\nsa-token:\n  jwt-secret-key: mylovezq\n  # token名称 (同时也是cookie名称)\n  token-name: Authorization\n  # token有效期 设为30天 (必定过期) 单位: 秒\n  timeout: 2592000\n  # token临时有效期 (指定时间无操作就过期) 单位: 秒\n  active-timeout: 86400\n  # 开启内网服务调用鉴权\n  check-id-token: true\n  # Id-Token的有效期 (单位: 秒)\n  id-token-timeout: 600\n  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)\n  is-concurrent: true\n  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)\n  is-share: true\n  # 是否尝试从header里读取token\n  is-read-head: true\n  # 是否尝试从cookie里读取token\n  is-read-cookie: false\n  # token前缀\n  token-prefix: \"Bearer\"\n  #token风格\n  token-style: uuid\n  #是否在初始化配置时打印版本字符画\n  isPrint: false\n  # 是否输出操作日志\n  is-log: false\nspring:\n  redis:\n    ##redis 单机环境配置\n    host: www.jiushi.com\n    port: 6379\n    password: mylovezq\n    database: 0\n    ssl: false\n    pool:\n      #      最大连接数\n      max-active: 8\n      #      最大阻塞等待时间（负数表示没有限制）\n      max-wait: 1\n      #      最大空闲连接\n      max-idle: 8\n      #      最小空闲链接\n      min-idle: 0\n      #      链接超时时间（毫秒）\n      timeout: 60000', '64d4330f25c758fca7851c1e480400ad', '2024-01-12 15:11:28', '2024-02-08 14:24:11', NULL, '192.168.1.1', '', 'satoken-dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (4, 'auth-satoken-dev.yaml', 'satoken-dev', 'server:\n  port: 1100\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq', 'ee1fe778141ffd6330cf78d3fc0e4b14', '2024-01-12 15:27:35', '2024-01-12 15:36:21', 'nacos', '172.17.0.1', '', 'satoken-dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (9, 'gateway-satoken-dev.yaml', 'satoken-dev', 'server:\n  port: 9999\nspring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lowerCaseServiceId: true\n      routes:\n        # auth\n        - id: route_auth\n          uri: lb://auth\n          predicates:    \n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        # user\n        - id: route_user\n          uri: lb://user\n          predicates:    \n            - Path=/user/**\n          filters:\n            - StripPrefix=1\n        # pay\n        - id: route_pay\n          uri: lb://pay\n          predicates:    \n            - Path=/pay/**\n          filters:\n            - StripPrefix=1\ngatewayurl:\n  skipUrl:\n    - /auth/auth2Open/**\n    - /user/user/sendPayMessage', 'f33ceb9d905aa3d132d706b3e6efaff7', '2024-01-12 16:30:25', '2024-02-01 17:57:43', NULL, '192.168.1.1', '', 'satoken-dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (13, 'user-satoken-dev.yaml', 'satoken-dev', 'server:\n  port: 1200\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql:// www.jiushi.com:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer:  www.jiushi.com:9876; www.jiushi.com:9877\n  producer:\n    group: jiushiGroup', 'a88d141c4aa508d2aee944b55d440f32', '2024-01-12 16:51:38', '2024-02-08 14:24:47', NULL, '192.168.1.1', '', 'satoken-dev', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (16, 'pay-satoken-dev.yaml', 'satoken-dev', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql:// www.jiushi.com:3306/satoken-pay?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer:  www.jiushi.com:9876; www.jiushi.com:9877\n  producer:\n    group: jiushiGroup', '837030e8a29beb4bc92cf2e6f09138be', '2024-02-01 16:52:01', '2024-02-18 15:22:28', NULL, '192.168.1.1', '', 'satoken-dev', '', '', '', 'yaml', '', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `datum_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增长标识',
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id` ASC, `tag_name` ASC, `tag_type` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint UNSIGNED NOT NULL COMMENT 'id',
  `nid` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'nid, 自增标识',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'operation type',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密钥',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create` ASC) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified` ASC) USING BTREE,
  INDEX `idx_did`(`data_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 1, 'public-config.yaml', 'satoken-dev', '', '# Sa-Token配置\r\nsa-token:\r\n  jwt-secret-key: pso\r\n  # token名称 (同时也是cookie名称)\r\n  token-name: Authorization\r\n  # token有效期 设为30天 (必定过期) 单位: 秒\r\n  timeout: 2592000\r\n  # token临时有效期 (指定时间无操作就过期) 单位: 秒\r\n  active-timeout: 86400\r\n  # 开启内网服务调用鉴权\r\n  check-id-token: true\r\n  # Id-Token的有效期 (单位: 秒)\r\n  id-token-timeout: 600\r\n  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)\r\n  is-concurrent: true\r\n  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)\r\n  is-share: true\r\n  # 是否尝试从header里读取token\r\n  is-read-head: true\r\n  # 是否尝试从cookie里读取token\r\n  is-read-cookie: false\r\n  # token前缀\r\n  token-prefix: \"Bearer\"\r\n  #token风格\r\n  token-style: uuid\r\n  #是否在初始化配置时打印版本字符画\r\n  isPrint: false\r\n  # 是否输出操作日志\r\n  is-log: false', 'eb2a019d2f732bae9dfc00bab0101529', '2024-01-12 07:11:27', '2024-01-12 15:11:28', NULL, '172.17.0.1', 'I', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (0, 2, 'auth.yaml', 'satoken-dev', '', 'server:\r\n  port: 1100\r\n\r\nspring:\r\n  cloud:\r\n  datasource:\r\n    driver-class-name: com.mysql.jdbc.Driver\r\n    url: jdbc:mysql://192.168.1.20:3306/satoken-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n    username: root\r\n    password: mylovezq', '616f5829c814bb783dbfa13e6947c8c5', '2024-01-12 07:24:21', '2024-01-12 15:24:21', NULL, '172.17.0.1', 'I', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (1, 3, 'public-config.yaml', 'satoken-dev', '', '# Sa-Token配置\r\nsa-token:\r\n  jwt-secret-key: pso\r\n  # token名称 (同时也是cookie名称)\r\n  token-name: Authorization\r\n  # token有效期 设为30天 (必定过期) 单位: 秒\r\n  timeout: 2592000\r\n  # token临时有效期 (指定时间无操作就过期) 单位: 秒\r\n  active-timeout: 86400\r\n  # 开启内网服务调用鉴权\r\n  check-id-token: true\r\n  # Id-Token的有效期 (单位: 秒)\r\n  id-token-timeout: 600\r\n  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)\r\n  is-concurrent: true\r\n  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)\r\n  is-share: true\r\n  # 是否尝试从header里读取token\r\n  is-read-head: true\r\n  # 是否尝试从cookie里读取token\r\n  is-read-cookie: false\r\n  # token前缀\r\n  token-prefix: \"Bearer\"\r\n  #token风格\r\n  token-style: uuid\r\n  #是否在初始化配置时打印版本字符画\r\n  isPrint: false\r\n  # 是否输出操作日志\r\n  is-log: false', 'eb2a019d2f732bae9dfc00bab0101529', '2024-01-12 07:25:27', '2024-01-12 15:25:28', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (0, 4, 'auth-satoken-dev.yaml', 'satoken-dev', '', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2024-01-12 07:27:35', '2024-01-12 15:27:35', NULL, '172.17.0.1', 'I', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (4, 5, 'auth-satoken-dev.yaml', 'satoken-dev', '', '1', 'c4ca4238a0b923820dcc509a6f75849b', '2024-01-12 07:27:53', '2024-01-12 15:27:53', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (2, 6, 'auth.yaml', 'satoken-dev', '', 'server:\r\n  port: 1100\r\n\r\nspring:\r\n  cloud:\r\n  datasource:\r\n    driver-class-name: com.mysql.jdbc.Driver\r\n    url: jdbc:mysql://192.168.1.20:3306/satoken-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n    username: root\r\n    password: mylovezq', '616f5829c814bb783dbfa13e6947c8c5', '2024-01-12 07:28:00', '2024-01-12 15:28:01', NULL, '172.17.0.1', 'D', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (4, 7, 'auth-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1100\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq', '83aeeee011f5d7e5958ba2df4a8021c7', '2024-01-12 07:36:21', '2024-01-12 15:36:21', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (1, 8, 'public-config.yaml', 'satoken-dev', '', '# Sa-Token配置\nsa-token:\n  jwt-secret-key: pso\n  # token名称 (同时也是cookie名称)\n  token-name: Authorization\n  # token有效期 设为30天 (必定过期) 单位: 秒\n  timeout: 2592000\n  # token临时有效期 (指定时间无操作就过期) 单位: 秒\n  active-timeout: 86400\n  # 开启内网服务调用鉴权\n  check-id-token: true\n  # Id-Token的有效期 (单位: 秒)\n  id-token-timeout: 600\n  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)\n  is-concurrent: true\n  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)\n  is-share: true\n  # 是否尝试从header里读取token\n  is-read-head: true\n  # 是否尝试从cookie里读取token\n  is-read-cookie: false\n  # token前缀\n  token-prefix: \"Bearer\"\n  #token风格\n  token-style: uuid\n  #是否在初始化配置时打印版本字符画\n  isPrint: false\n  # 是否输出操作日志\n  is-log: false\nspring:\n  redis:\n    ##redis 单机环境配置\n    host: 192.168.1.20\n    port: 6379\n    password: mylovezq\n    database: 0\n    ssl: false\n    pool:\n      #      最大连接数\n      max-active: 8\n      #      最大阻塞等待时间（负数表示没有限制）\n      max-wait: 1\n      #      最大空闲连接\n      max-idle: 8\n      #      最小空闲链接\n      min-idle: 0\n      #      链接超时时间（毫秒）\n      timeout: 60000', 'a25f500348f42c8aff76051260e3dceb', '2024-01-12 07:36:41', '2024-01-12 15:36:41', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (1, 9, 'public-config.yaml', 'satoken-dev', '', '# Sa-Token配置\nsa-token:\n  jwt-secret-key: mylovezq\n  # token名称 (同时也是cookie名称)\n  token-name: Authorization\n  # token有效期 设为30天 (必定过期) 单位: 秒\n  timeout: 2592000\n  # token临时有效期 (指定时间无操作就过期) 单位: 秒\n  active-timeout: 86400\n  # 开启内网服务调用鉴权\n  check-id-token: true\n  # Id-Token的有效期 (单位: 秒)\n  id-token-timeout: 600\n  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)\n  is-concurrent: true\n  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)\n  is-share: true\n  # 是否尝试从header里读取token\n  is-read-head: true\n  # 是否尝试从cookie里读取token\n  is-read-cookie: false\n  # token前缀\n  token-prefix: \"Bearer\"\n  #token风格\n  token-style: uuid\n  #是否在初始化配置时打印版本字符画\n  isPrint: false\n  # 是否输出操作日志\n  is-log: false\nspring:\n  redis:\n    ##redis 单机环境配置\n    host: 192.168.1.20\n    port: 6379\n    password: mylovezq\n    database: 0\n    ssl: false\n    pool:\n      #      最大连接数\n      max-active: 8\n      #      最大阻塞等待时间（负数表示没有限制）\n      max-wait: 1\n      #      最大空闲连接\n      max-idle: 8\n      #      最小空闲链接\n      min-idle: 0\n      #      链接超时时间（毫秒）\n      timeout: 60000', 'c050933db584542f99ddf77a284ac602', '2024-01-12 07:37:46', '2024-01-12 15:37:46', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (0, 10, 'gateway-satoken-dev.yaml', 'satoken-dev', '', 'server:\r\n  port: 9999', '01e0cfa6deb6df14a899dbc9baa0ae38', '2024-01-12 08:30:24', '2024-01-12 16:30:25', NULL, '172.17.0.1', 'I', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (9, 11, 'gateway-satoken-dev.yaml', 'satoken-dev', '', 'server:\r\n  port: 9999', '01e0cfa6deb6df14a899dbc9baa0ae38', '2024-01-12 08:33:52', '2024-01-12 16:33:53', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (9, 12, 'gateway-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 9999\nspring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lowerCaseServiceId: true\n      routes:\n        # auth\n        - id: route_auth\n          uri: lb://auth\n          predicates:    \n            - Path=/auth/**\n          filters:\n            - StripPrefix=1 ', 'a2da08a6d6777d2af609749e32ccae72', '2024-01-12 08:34:40', '2024-01-12 16:34:41', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (9, 13, 'gateway-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 9999\nspring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lowerCaseServiceId: true\n      routes:\n        # auth\n        - id: route_auth\n          uri: lb://auth\n          predicates:    \n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        # user\n        - id: route_user\n          uri: lb://user\n          predicates:    \n            - Path=/user/**\n          filters:\n            - StripPrefix=1', '2a7ee43141b3c3ac3f025eab799e476e', '2024-01-12 08:37:39', '2024-01-12 16:37:39', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (0, 14, 'user-satoken-dev.yaml', 'satoken-dev', '', 'server:\r\n  port: 1200\r\n\r\nspring:\r\n  cloud:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n    username: root\r\n    password: mylovezq', 'f467a924fe009c9f05335b377040fd6a', '2024-01-12 08:51:38', '2024-01-12 16:51:38', NULL, '172.17.0.1', 'I', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (9, 15, 'gateway-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 9999\nspring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lowerCaseServiceId: true\n      routes:\n        # auth\n        - id: route_auth\n          uri: lb://auth\n          predicates:    \n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        # user\n        - id: route_user\n          uri: lb://user\n          predicates:    \n            - Path=/user/**\n          filters:\n            - StripPrefix=1\ngatewayurl:\n  skipUrl:\n    - /auth/auth2Open', 'e94f1b5589e80627ebb02b89ecfd2809', '2024-01-12 09:43:32', '2024-01-12 17:43:33', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (9, 16, 'gateway-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 9999\nspring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lowerCaseServiceId: true\n      routes:\n        # auth\n        - id: route_auth\n          uri: lb://auth\n          predicates:    \n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        # user\n        - id: route_user\n          uri: lb://user\n          predicates:    \n            - Path=/user/**\n          filters:\n            - StripPrefix=1\ngatewayurl:\n  skipUrl:\n    - /auth/auth/auth2Open', '2be4c6352fce69e1bee8902ca3e1f293', '2024-01-12 09:46:23', '2024-01-12 17:46:23', 'nacos', '172.17.0.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (0, 17, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\r\n  port: 1300\r\n\r\nspring:\r\n  cloud:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n    username: root\r\n    password: mylovezq', 'f610415b842e5e1c4f2de593f9525013', '2024-02-01 08:52:00', '2024-02-01 16:52:01', NULL, '192.168.1.1', 'I', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (9, 18, 'gateway-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 9999\nspring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lowerCaseServiceId: true\n      routes:\n        # auth\n        - id: route_auth\n          uri: lb://auth\n          predicates:    \n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        # user\n        - id: route_user\n          uri: lb://user\n          predicates:    \n            - Path=/user/**\n          filters:\n            - StripPrefix=1\ngatewayurl:\n  skipUrl:\n    - /auth/auth2Open/**', '457a7d34913740bd4434f517ad673946', '2024-02-01 08:52:25', '2024-02-01 16:52:26', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 19, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\r\n  port: 1300\r\n\r\nspring:\r\n  cloud:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n    username: root\r\n    password: mylovezq', 'f610415b842e5e1c4f2de593f9525013', '2024-02-01 09:01:59', '2024-02-01 17:01:59', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 20, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n    consumer:\n        group: jiushiPayConsumer\n        # 一次拉取消息最大值，注意是拉取消息的最大值而非消费最大值\n        pull-batch-size: 100\n    name-server: 192.168.1.20:9876;192.168.1.21:9876\n    producer:\n        # 发送同一类消息的设置为同一个group，保证唯一\n        group: jiushiPayConsumerProducer\n        # 发送消息超时时间，默认3000\n        sendMessageTimeout: 10000\n        # 发送消息失败重试次数，默认2\n        retryTimesWhenSendFailed: 2\n        # 异步消息重试此处，默认2\n        retryTimesWhenSendAsyncFailed: 2\n        # 消息最大长度，默认1024 * 1024 * 4(默认4M)\n        maxMessageSize: 40960\n        # 压缩消息阈值，默认4k(1024 * 4)\n        compressMessageBodyThreshold: 4096\n        # 是否在内部发送失败时重试另一个broker，默认false\n        retryNextServer: false', '940eb6277cd5243108c5da416206046b', '2024-02-01 09:10:16', '2024-02-01 17:10:16', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 21, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n    name-server: 192.168.1.20:9876;192.168.1.21:9876', '3bbf7b7814d0d15cf97825bbd977c77a', '2024-02-01 09:38:12', '2024-02-01 17:38:12', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 22, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n    name-server: 192.168.1.20:9876;192.168.1.21:9876', '3bbf7b7814d0d15cf97825bbd977c77a', '2024-02-01 09:43:50', '2024-02-01 17:43:50', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 23, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\n    rocketmq:\n      name-server: 192.168.1.20:9876;192.168.1.21:9876\n    ', 'c187ec8d31f095c84e03fe8e2c3866cc', '2024-02-01 09:44:12', '2024-02-01 17:44:12', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 24, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\n    rocketmq:\n      name-server: 192.168.1.20:9876;192.168.1.21:9876\n    ', 'c187ec8d31f095c84e03fe8e2c3866cc', '2024-02-01 09:44:23', '2024-02-01 17:44:24', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 25, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\n  rocketmq:\n    name-server: 192.168.1.20:9876;192.168.1.21:9876\n    ', '2c14e62d29579db7fdee5007d28d97af', '2024-02-01 09:45:21', '2024-02-01 17:45:22', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 26, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\n  rocketmq:\n    nameServer: 192.168.1.20:9876;192.168.1.21:9876\n    ', '30017afdc6cd5da569195d0a44cb69fd', '2024-02-01 09:48:44', '2024-02-01 17:48:44', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 27, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876;192.168.1.21:9876\n    ', 'cc7426b8698f575ce41a94c388900c64', '2024-02-01 09:49:31', '2024-02-01 17:49:31', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 28, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876;192.168.1.21:9876\n  producer.group: jiushi', '3768149442800336ae5cfb820feceaa9', '2024-02-01 09:51:51', '2024-02-01 17:51:52', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 29, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876;192.168.1.21:9876', 'bda46b46dcd22b798d9916529f102220', '2024-02-01 09:52:47', '2024-02-01 17:52:48', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (13, 30, 'user-satoken-dev.yaml', 'satoken-dev', '', 'server:\r\n  port: 1200\r\n\r\nspring:\r\n  cloud:\r\n  datasource:\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n    username: root\r\n    password: mylovezq', 'f467a924fe009c9f05335b377040fd6a', '2024-02-01 09:53:45', '2024-02-01 17:53:45', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (9, 31, 'gateway-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 9999\nspring:\n  cloud:\n    gateway:\n      discovery:\n        locator:\n          enabled: true\n          lowerCaseServiceId: true\n      routes:\n        # auth\n        - id: route_auth\n          uri: lb://auth\n          predicates:    \n            - Path=/auth/**\n          filters:\n            - StripPrefix=1\n        # user\n        - id: route_user\n          uri: lb://user\n          predicates:    \n            - Path=/user/**\n          filters:\n            - StripPrefix=1\n        # pay\n        - id: route_pay\n          uri: lb://pay\n          predicates:    \n            - Path=/pay/**\n          filters:\n            - StripPrefix=1\ngatewayurl:\n  skipUrl:\n    - /auth/auth2Open/**', 'b43ff4b999f655906b0e753e6dce4fde', '2024-02-01 09:57:43', '2024-02-01 17:57:43', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 32, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876;192.168.1.21:9876\n  producer:\n    group: jiushiGroup', '7fcbe8941b30b0a93ea83f803147bd3b', '2024-02-05 02:44:07', '2024-02-05 10:44:08', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (13, 33, 'user-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1200\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876;192.168.1.21:9876\n  producer:\n    group: jiushiGroup', '94f4400c7bb61ee0df3da1265452431a', '2024-02-05 02:44:45', '2024-02-05 10:44:46', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 34, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876\n  producer:\n    group: jiushiGroup', 'b1d00b4abe9b188c1e7e6993b70b7da9', '2024-02-05 03:39:38', '2024-02-05 11:39:38', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (13, 35, 'user-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1200\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876\n  producer:\n    group: jiushiGroup', '6ea8e71c746cda2592ffa04ac343d89d', '2024-02-05 03:39:55', '2024-02-05 11:39:56', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (13, 36, 'user-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1200\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876;192.168.1.21:9876\n  producer:\n    group: jiushiGroup', '94f4400c7bb61ee0df3da1265452431a', '2024-02-05 03:47:00', '2024-02-05 11:47:01', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (1, 37, 'public-config.yaml', 'satoken-dev', '', '# Sa-Token配置\nsa-token:\n  jwt-secret-key: mylovezq\n  # token名称 (同时也是cookie名称)\n  token-name: Authorization\n  # token有效期 设为30天 (必定过期) 单位: 秒\n  timeout: 2592000\n  # token临时有效期 (指定时间无操作就过期) 单位: 秒\n  active-timeout: 86400\n  # 开启内网服务调用鉴权\n  check-id-token: true\n  # Id-Token的有效期 (单位: 秒)\n  id-token-timeout: 600\n  # 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)\n  is-concurrent: true\n  # 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)\n  is-share: true\n  # 是否尝试从header里读取token\n  is-read-head: true\n  # 是否尝试从cookie里读取token\n  is-read-cookie: false\n  # token前缀\n  token-prefix: \"Bearer\"\n  #token风格\n  token-style: uuid\n  #是否在初始化配置时打印版本字符画\n  isPrint: false\n  # 是否输出操作日志\n  is-log: false\nspring:\n  redis:\n    ##redis 单机环境配置\n    host: 192.168.1.20\n    port: 6379\n    password: mylovezq\n    database: 0\n    ssl: false\n    pool:\n      #      最大连接数\n      max-active: 8\n      #      最大阻塞等待时间（负数表示没有限制）\n      max-wait: 1\n      #      最大空闲连接\n      max-idle: 8\n      #      最小空闲链接\n      min-idle: 0\n      #      链接超时时间（毫秒）\n      timeout: 60000', 'c050933db584542f99ddf77a284ac602', '2024-02-08 06:24:10', '2024-02-08 14:24:11', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (13, 38, 'user-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1200\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876;192.168.1.21:9876\n  producer:\n    group: jiushiGroup', '94f4400c7bb61ee0df3da1265452431a', '2024-02-08 06:24:46', '2024-02-08 14:24:47', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 39, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.1.20:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer: 192.168.1.20:9876;192.168.1.21:9876\n  producer:\n    group: jiushiGroup', '7fcbe8941b30b0a93ea83f803147bd3b', '2024-02-08 06:25:06', '2024-02-08 14:25:06', NULL, '192.168.1.1', 'U', 'satoken-dev', '');
INSERT INTO `his_config_info` VALUES (16, 40, 'pay-satoken-dev.yaml', 'satoken-dev', '', 'server:\n  port: 1300\n\nspring:\n  cloud:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql:// www.jiushi.com:3306/satoken-user?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n    username: root\n    password: mylovezq\nrocketmq:\n  nameServer:  www.jiushi.com:9876; www.jiushi.com:9877\n  producer:\n    group: jiushiGroup', 'fedcb53f8b2b943f50508f03ba1bed98', '2024-02-18 07:22:28', '2024-02-18 15:22:28', NULL, '192.168.1.1', 'U', 'satoken-dev', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'role',
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'resource',
  `action` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'action',
  UNIQUE INDEX `uk_role_permission`(`role` ASC, `resource` ASC, `action` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'username',
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'role',
  UNIQUE INDEX `idx_user_role`(`username` ASC, `role` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp` ASC, `tenant_id` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (2, '1', 'satoken-dev', 'satoken-dev', 'satoken-dev', 'nacos', 1705041758784, 1705041758784);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'username',
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'password',
  `enabled` tinyint(1) NOT NULL COMMENT 'enabled',
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('jiushi', '$2a$10$4R6Y8Hv5ygOjGUEXLQPtwefdGKz.JpR7YCMsRwarzDz1hrSAZjQ0O', 1);
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
