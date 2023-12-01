
DROP TABLE IF EXISTS `oauth2_client_user`;

CREATE TABLE `oauth2_client_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `client_id` varchar(32)  NOT NULL COMMENT 'oauth2_registered_client表id',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `open_id` varchar(64) NOT NULL COMMENT '此账号在此Client下的openid',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_no` varchar(32) DEFAULT 'root' COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_no` varchar(32) DEFAULT NULL COMMENT '修改人',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否已删除',
  `user_type` tinyint(4) NOT NULL COMMENT '用户类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id` (`open_id`),
  UNIQUE KEY `unq_idx_uid_cid` (`client_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2 应用和用户的关联';



DROP TABLE IF EXISTS `oauth2_registered_client`;

CREATE TABLE `oauth2_registered_client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `client_id` varchar(64) NOT NULL COMMENT '应用id',
  `client_secret` varchar(128) NOT NULL COMMENT '应用秘钥',
  `contract_scope` text COMMENT '应用签约的所有权限, 多个用逗号隔开',
  `allow_url` text COMMENT '应用允许授权的所有URL, 多个用逗号隔开',
  `is_code` tinyint(1) DEFAULT NULL COMMENT '此 Client 是否打开模式：授权码（Authorization Code）',
  `is_implicit` tinyint(1) DEFAULT NULL COMMENT '此 Client 是否打开模式：隐藏式（Implicit）',
  `is_password` tinyint(1) DEFAULT NULL COMMENT '此 Client 是否打开模式：密码式（Password）',
  `is_client` tinyint(1) DEFAULT NULL COMMENT '此 Client 是否打开模式：凭证式（Client Credentials）',
  `is_auto_mode` tinyint(1) DEFAULT NULL COMMENT '是否自动判断此 Client 开放的授权模式 此值为true时：四种模式（isCode、isImplicit、isPassword、isClient）是否生效，依靠全局设置 此值为false时：四种模式（isCode、isImplicit、isPassword、isClient）是否生效，依靠局部配置+全局配置',
  `is_new_refresh` tinyint(1) DEFAULT NULL COMMENT '单独配置此Client：是否在每次 Refresh-Token 刷新 Access-Token 时，产生一个新的 Refresh-Token [默认取全局配置]',
  `access_token_timeout` bigint(20) unsigned DEFAULT NULL COMMENT '单独配置此Client：Access-Token 保存的时间(单位秒) [默认取全局配置]',
  `refresh_token_timeout` bigint(20) unsigned DEFAULT NULL COMMENT '单独配置此Client：Refresh-Token 保存的时间(单位秒) [默认取全局配置]',
  `client_token_timeout` bigint(20) unsigned DEFAULT NULL COMMENT '单独配置此Client：Client-Token 保存的时间(单位秒) [默认取全局配置]',
  `past_client_token_timeout` bigint(20) unsigned DEFAULT NULL COMMENT '单独配置此Client：Past-Client-Token 保存的时间(单位：秒) [默认取全局配置]\r\n',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_no` varchar(32) DEFAULT 'root' COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_no` varchar(32) DEFAULT NULL COMMENT '修改人',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否已删除',
  `is_lv2_auth_verify` bit(1) DEFAULT b'0' COMMENT '是否进行lv2认证校验',
  `client_name` varchar(100) DEFAULT NULL COMMENT '客户端名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '接入方的logo图标url',
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='OAuth2 客户端';




DROP TABLE IF EXISTS `oauth2_resource_scope`;

CREATE TABLE `oauth2_resource_scope` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源编码',
  `remarks` varchar(64) DEFAULT NULL COMMENT '备注',
  `scope_field` json DEFAULT NULL COMMENT '根据权限回显字段，用于展示给登录用户授权时的字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_no` varchar(32) DEFAULT 'root' COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_no` varchar(32) DEFAULT NULL COMMENT '修改人',
  `enable` tinyint(1) DEFAULT '1' COMMENT '是否已删除',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_resource_scope_code` (`code`) COMMENT '资源范围编码'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='资源范围';

