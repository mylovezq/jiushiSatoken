-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 172.16.0.155    Database: edocyun_auth
-- ------------------------------------------------------
-- Server version	5.7.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `oauth2_client_user`
--

DROP TABLE IF EXISTS `oauth2_client_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_client_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `client_id` bigint(20) unsigned NOT NULL COMMENT 'oauth2_registered_client表id',
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_client_user`
--

LOCK TABLES `oauth2_client_user` WRITE;
/*!40000 ALTER TABLE `oauth2_client_user` DISABLE KEYS */;
INSERT INTO `oauth2_client_user` VALUES (17,1001,'UD2023081400003','PSO_eb2a79e6169f489ea7f20bf88aaba352',NULL,'2023-11-25 17:07:24','root',NULL,NULL,1,1),(18,1001,'UD2023081600003','PSO_a218012e303a415aae32ba322015ba49',NULL,'2023-11-29 16:53:59','U2023081600010',NULL,NULL,1,1),(19,1001,'UD2023080100001','PSO_0582a73d2ba84e809c9c6c66a6c39d99',NULL,'2023-11-29 17:00:17','U2023080100015',NULL,NULL,1,1);
/*!40000 ALTER TABLE `oauth2_client_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_registered_client`
--

DROP TABLE IF EXISTS `oauth2_registered_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_registered_client`
--

LOCK TABLES `oauth2_registered_client` WRITE;
/*!40000 ALTER TABLE `oauth2_registered_client` DISABLE KEYS */;
INSERT INTO `oauth2_registered_client` VALUES (1,'1001','8de95b980182d32c9eb06840f973bc72e0ad603478c09d0b198067e038d63d7a','UD_REAL_NAME,UD_MY','https://www.baidu.com',1,1,1,1,1,1,7200,7200,7200,7200,NULL,NULL,'root',NULL,NULL,1,_binary '','测试专病库','https://edocyun.oss-cn-shanghai.aliyuncs.com/dev/1701252229117.png'),(3,'pso_N6VeZSxj','978ccb901d836a86ce640ef45425deaff888306a2c33b966ca977e1b8ca14202','UD_REAL_NAME','*',0,0,0,0,1,0,7200,7200,7200,7200,NULL,NULL,'root',NULL,NULL,1,_binary '','哈哈哈哈','https://cimp-minio.edocyun.com.cn/prod-yccimp/2023-11-20/8b6077c13dab4742a6b1e5634c7589aa.png'),(4,'pso_M5k4h4zX','9d2dea2328be6923de9dfc288180114dac87ada38c47c5d21ed398643f80a215','UD_REAL_NAME','123',1,0,0,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'root',NULL,NULL,1,_binary '\0','新增客户端','https://szhrkprod.oss-cn-shanghai.aliyuncs.com/DEV/1701315933810.png'),(5,'pso_2YMSiGER','c70afa941b9ebd3fbebd617a40609249a1d5e6e3cf540c946f0a136e6f0a9155','UD_REAL_NAME','11',1,0,0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'root',NULL,NULL,1,_binary '\0','新增客户端12','https://szhrkprod.oss-cn-shanghai.aliyuncs.com/DEV/1701329588041.png'),(6,'pso_RPHDSmF6','e8c79069691eb612c30d5e999bea937df24619328ca0fa2a6ec348eca6f8812a','UD_REAL_NAME','123',0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'root',NULL,NULL,1,_binary '\0','新增客户端','https://szhrkprod.oss-cn-shanghai.aliyuncs.com/DEV/1701331248090.png'),(7,'pso_4Z58N8Pu','0461207c0c83e734d170ad4255f085804c6a83bf7dca671a0df49d7d0c50356c','UD_REAL_NAME','123',0,1,1,1,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'root',NULL,NULL,1,_binary '\0','13223','https://szhrkprod.oss-cn-shanghai.aliyuncs.com/DEV/1701331347439.png'),(8,'pso_82krdUMf','a9f882fa416f6a14b13133a3f71e934263797222e361ac5b3b357889775dbb01','UD_REAL_NAME','123',1,1,1,1,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'root',NULL,NULL,1,_binary '\0','123','https://szhrkprod.oss-cn-shanghai.aliyuncs.com/DEV/1701331382186.png'),(9,'pso_kpnKcX7t','31148f63e24a37067b05bdfa69c023dbac1e7955014632fcf1373479c9f37449','UD_REAL_NAME','123',0,1,1,1,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'root',NULL,NULL,1,_binary '\0','新增客户端123','https://szhrkprod.oss-cn-shanghai.aliyuncs.com/DEV/1701331418723.png'),(10,'pso_CN887B5u','e1e0cb63e378c1bae415a5705b057b02b9be4dd4ccdd407d061297e62244d2c3','UD_REAL_NAME','132',1,0,0,0,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'root',NULL,NULL,1,_binary '\0','新增客户端12123','https://szhrkprod.oss-cn-shanghai.aliyuncs.com/DEV/1701331468904.png'),(11,'pso_u6t24C3u','99e886ca9557466551d5c9ec1ca05c4a10b004530e03d92103b4da0c8ac13056','UD_REAL_NAME','123123',0,0,1,1,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'root',NULL,NULL,1,_binary '\0','新增客户端ds','https://szhrkprod.oss-cn-shanghai.aliyuncs.com/DEV/1701331512192.png'),(12,'pso_ihwNcNa3','3be371796510320d5084b3a9ce40cce77faa47a6a7cd7e844a7f274652b137e9','UD_REAL_NAME','13',0,1,1,1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'root',NULL,NULL,1,_binary '\0','123sdadsa','https://szhrkprod.oss-cn-shanghai.aliyuncs.com/DEV/1701331549348.png');
/*!40000 ALTER TABLE `oauth2_registered_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_resource_scope`
--

DROP TABLE IF EXISTS `oauth2_resource_scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_resource_scope`
--

LOCK TABLES `oauth2_resource_scope` WRITE;
/*!40000 ALTER TABLE `oauth2_resource_scope` DISABLE KEYS */;
INSERT INTO `oauth2_resource_scope` VALUES (1,'UD_REAL_NAME',NULL,'[{\"sort\": 1, \"label\": \"姓名\", \"fieldName\": \"name\", \"confirmShow\": true, \"desensitizedType\": null}, {\"sort\": 2, \"label\": \"账户\", \"fieldName\": \"phone\", \"confirmShow\": true, \"desensitizedType\": \"MOBILE_PHONE\"}, {\"sort\": 3, \"label\": \"身份证\", \"fieldName\": \"identityCard\", \"confirmShow\": true, \"desensitizedType\": \"ID_CARD\"}, {\"sort\": 3, \"label\": \"openId\", \"fieldName\": \"openId\", \"confirmShow\": false, \"desensitizedType\": null}, {\"sort\": 3, \"label\": \"性别\", \"fieldName\": \"gender\", \"confirmShow\": false, \"desensitizedType\": null}, {\"sort\": 3, \"label\": \"所在机构代码\", \"fieldName\": \"orgId\", \"confirmShow\": false, \"desensitizedType\": null}, {\"sort\": 3, \"label\": \"所在机构名字\", \"fieldName\": \"orgName\", \"confirmShow\": false, \"desensitizedType\": null}]',NULL,'root',NULL,NULL,1,'用户信息');
/*!40000 ALTER TABLE `oauth2_resource_scope` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'edocyun_auth'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-01 17:39:23
