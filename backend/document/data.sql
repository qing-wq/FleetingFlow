-- MariaDB dump 10.19-11.1.2-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: qiniu
-- ------------------------------------------------------
-- Server version	11.1.2-MariaDB-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

drop database if exists qiniu;
create database qiniu;
use qiniu;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category`
(
    `id`            int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `category_name` varchar(64)      NOT NULL DEFAULT '' COMMENT '类目名称',
    `status`        tinyint(4)       NOT NULL DEFAULT 0 COMMENT '状态：0-未发布，1-已发布',
    `rank`          tinyint(4)       NOT NULL DEFAULT 0 COMMENT '排序',
    `deleted`       tinyint(4)       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time`   timestamp        NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time`   timestamp        NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='类目管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `category`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment`
(
    `id`                int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `video_id`          int(10) unsigned NOT NULL DEFAULT 0 COMMENT '视频ID',
    `user_id`           int(10) unsigned NOT NULL DEFAULT 0 COMMENT '用户ID',
    `content`           varchar(300)     NOT NULL DEFAULT '' COMMENT '评论内容',
    `top_comment_id`    int(11)          NOT NULL DEFAULT 0 COMMENT '顶级评论ID',
    `parent_comment_id` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '父评论ID',
    `deleted`           tinyint(4)       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time`       timestamp        NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time`       timestamp        NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_article_id` (`video_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `comment`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notify_msg`
--

DROP TABLE IF EXISTS `notify_msg`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notify_msg`
(
    `id`              int(10) unsigned    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `related_id`      int(10) unsigned    NOT NULL DEFAULT 0 COMMENT '关联的主键',
    `notify_user_id`  int(10) unsigned    NOT NULL DEFAULT 0 COMMENT '通知的用户id',
    `operate_user_id` int(10) unsigned    NOT NULL DEFAULT 0 COMMENT '触发这个通知的用户id',
    `msg`             varchar(1024)       NOT NULL DEFAULT '' COMMENT '消息内容',
    `type`            tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '类型: 0-默认，1-评论，2-回复 3-点赞 4-收藏 5-关注 6-系统',
    `state`           tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '阅读状态: 0-未读，1-已读',
    `create_time`     timestamp           NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time`     timestamp           NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    KEY `key_notify_user_id_type_state` (`notify_user_id`, `type`, `state`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='消息通知列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notify_msg`
--

LOCK TABLES `notify_msg` WRITE;
/*!40000 ALTER TABLE `notify_msg`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `notify_msg`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `read_count`
--

DROP TABLE IF EXISTS `read_count`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `read_count`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `video_id`    int(10) unsigned NOT NULL DEFAULT 0 COMMENT '视频ID',
    `cnt`         int(10) unsigned NOT NULL DEFAULT 0 COMMENT '访问计数',
    `create_time` timestamp        NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time` timestamp        NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_document_id_type` (`video_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='计数表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `read_count`
--

LOCK TABLES `read_count` WRITE;
/*!40000 ALTER TABLE `read_count`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `read_count`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tag_name`    varchar(120)     NOT NULL COMMENT '标签名称',
    `category_id` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '分类ID',
    `status`      tinyint(4)       NOT NULL DEFAULT 0 COMMENT '状态：0-未发布，1-已发布',
    `deleted`     tinyint(4)       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time` timestamp        NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time` timestamp        NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='标签管理表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tag`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_local_storage`
--

DROP TABLE IF EXISTS `tool_local_storage`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tool_local_storage`
(
    `storage_id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `real_name`   varchar(255) DEFAULT NULL COMMENT '文件真实的名称',
    `name`        varchar(255) DEFAULT NULL COMMENT '文件名',
    `suffix`      varchar(255) DEFAULT NULL COMMENT '后缀',
    `path`        varchar(255) DEFAULT NULL COMMENT '路径',
    `type`        varchar(255) DEFAULT NULL COMMENT '类型',
    `size`        varchar(100) DEFAULT NULL COMMENT '大小',
    `create_by`   varchar(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   varchar(255) DEFAULT NULL COMMENT '更新者',
    `create_time` datetime     DEFAULT NULL COMMENT '创建日期',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`storage_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT COMMENT ='本地存储';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_local_storage`
--

LOCK TABLES `tool_local_storage` WRITE;
/*!40000 ALTER TABLE `tool_local_storage`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_local_storage`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_qiniu_config`
--

DROP TABLE IF EXISTS `tool_qiniu_config`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tool_qiniu_config`
(
    `config_id`  bigint(20)   NOT NULL COMMENT 'ID',
    `access_key` text         DEFAULT NULL COMMENT 'accessKey',
    `bucket`     varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
    `host`       varchar(255) NOT NULL COMMENT '外链域名',
    `secret_key` text         DEFAULT NULL COMMENT 'secretKey',
    `type`       varchar(255) DEFAULT NULL COMMENT '空间类型',
    `zone`       varchar(255) DEFAULT NULL COMMENT '机房',
    PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT COMMENT ='七牛云配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_qiniu_config`
--

LOCK TABLES `tool_qiniu_config` WRITE;
/*!40000 ALTER TABLE `tool_qiniu_config`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_qiniu_config`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool_qiniu_content`
--

DROP TABLE IF EXISTS `tool_qiniu_content`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tool_qiniu_content`
(
    `content_id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bucket`      varchar(255) DEFAULT NULL COMMENT 'Bucket 识别符',
    `name`        varchar(180) DEFAULT NULL COMMENT '文件名称',
    `size`        varchar(255) DEFAULT NULL COMMENT '文件大小',
    `type`        varchar(255) DEFAULT NULL COMMENT '文件类型：私有或公开',
    `url`         varchar(255) DEFAULT NULL COMMENT '文件url',
    `suffix`      varchar(255) DEFAULT NULL COMMENT '文件后缀',
    `update_time` datetime     DEFAULT NULL COMMENT '上传或同步的时间',
    PRIMARY KEY (`content_id`) USING BTREE,
    UNIQUE KEY `uniq_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = COMPACT COMMENT ='七牛云文件存储';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool_qiniu_content`
--

LOCK TABLES `tool_qiniu_content` WRITE;
/*!40000 ALTER TABLE `tool_qiniu_content`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tool_qiniu_content`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user`
(
    `id`               int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `third_account_id` varchar(128)     NOT NULL DEFAULT '' COMMENT '第三方用户ID',
    `user_name`        varchar(64)      NOT NULL DEFAULT '' COMMENT '用户名',
    `password`         varchar(128)     NOT NULL DEFAULT '' COMMENT '密码',
    `login_type`       tinyint(4)       NOT NULL DEFAULT 0 COMMENT '登录方式: 0-微信登录，1-账号密码登录',
    `deleted`          tinyint(4)       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time`      timestamp        NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time`      timestamp        NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    KEY `key_third_account_id` (`third_account_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户登录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
INSERT INTO `user`
VALUES (1, '', 'admin', '$2a$10$mva5SAaibyFPAkNfb8N7UOlkdYyUtycScQnNHxadDO.TW7Y18mIIy', 0, 0, '2023-10-24 17:30:44',
        '2023-10-24 17:38:31');
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_foot`
--

DROP TABLE IF EXISTS `user_foot`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_foot`
(
    `id`              int(10) unsigned    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`         int(10) unsigned    NOT NULL DEFAULT 0 COMMENT '用户ID',
    `video_id`        int(10) unsigned    NOT NULL DEFAULT 0 COMMENT '视频ID',
    `type`            tinyint(4)          NOT NULL DEFAULT 1 COMMENT '类型：1-视频，2-评论',
    `video_user_id`   int(10) unsigned    NOT NULL DEFAULT 0 COMMENT '发布该视频的用户ID',
    `collection_stat` tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '收藏状态: 0-未收藏，1-已收藏，2-取消收藏',
    `read_stat`       tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '阅读状态: 0-未读，1-已读',
    `comment_stat`    tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '评论状态: 0-未评论，1-已评论，2-删除评论',
    `praise_stat`     tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '点赞状态: 0-未点赞，1-已点赞，2-取消点赞',
    `create_time`     timestamp           NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time`     timestamp           NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_user_video` (`user_id`, `video_id`, `type`),
    KEY `idx_video_id` (`video_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户足迹表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_foot`
--

LOCK TABLES `user_foot` WRITE;
/*!40000 ALTER TABLE `user_foot`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `user_foot`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     int(10) unsigned NOT NULL DEFAULT 0 COMMENT '用户ID',
    `user_name`   varchar(50)      NOT NULL DEFAULT '' COMMENT '用户名',
    `picture`     varchar(128)     NOT NULL DEFAULT '' COMMENT '用户图像',
    `email`       varchar(100)     NOT NULL DEFAULT '' COMMENT '电子邮件',
    `phone`       varchar(100)     NOT NULL DEFAULT '' COMMENT '手机号码',
    `profile`     varchar(225)     NOT NULL DEFAULT '' COMMENT '个人简介',
    `user_role`   int(11)          NOT NULL DEFAULT 0 COMMENT '0-普通用户 1-超管',
    `ip`          varchar(300)     NOT NULL DEFAULT '' COMMENT '用户的ip信息',
    `deleted`     tinyint(4)       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time` timestamp        NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time` timestamp        NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    KEY `key_user_id` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户个人信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info`
    DISABLE KEYS */;
INSERT INTO `user_info`
VALUES (1, 1, '测试账号', '', '', '', '', 0, '', 0, '2023-10-24 17:46:07', '2023-10-24 17:46:07');
/*!40000 ALTER TABLE `user_info`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_relation`
--

DROP TABLE IF EXISTS `user_relation`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_relation`
(
    `id`             int(10) unsigned    NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`        int(10) unsigned    NOT NULL DEFAULT 0 COMMENT '用户ID',
    `follow_user_id` int(10) unsigned    NOT NULL COMMENT '关注userId的用户id，即粉丝userId',
    `follow_state`   tinyint(3) unsigned NOT NULL DEFAULT 0 COMMENT '阅读状态: 0-未关注，1-已关注，2-取消关注',
    `create_time`    timestamp           NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time`    timestamp           NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_follow` (`user_id`, `follow_user_id`),
    KEY `key_follow_user_id` (`follow_user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='用户关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_relation`
--

LOCK TABLES `user_relation` WRITE;
/*!40000 ALTER TABLE `user_relation`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `user_relation`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `video`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     int(10) unsigned NOT NULL DEFAULT 0 COMMENT '用户ID',
    `title`       varchar(120)     NOT NULL DEFAULT '' COMMENT '视频标题',
    `thumbnail`   varchar(300)     NOT NULL DEFAULT '' COMMENT '视频介绍',
    `picture`     varchar(128)     NOT NULL DEFAULT '' COMMENT '视频头图',
    `category_id` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '分类',
    `url`         varchar(200)     NOT NULL DEFAULT '' COMMENT '视频连接',
    `status`      tinyint(4)       NOT NULL DEFAULT 0 COMMENT '状态：0-未发布，1-已发布，2-待审核',
    `deleted`     tinyint(4)       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `create_time` timestamp        NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
    `update_time` timestamp        NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='视频信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `video`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video_tag`
--

DROP TABLE IF EXISTS `video_tag`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `video_tag`
(
    `id`          int(10) unsigned NOT NULL,
    `video_id`    int(10) unsigned      DEFAULT NULL,
    `tag_id`      int(11)               DEFAULT NULL COMMENT '标签id',
    `deleted`     tinyint(4)            DEFAULT NULL,
    `create_time` timestamp        NULL DEFAULT NULL,
    `update_time` timestamp        NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_tag_id` (`tag_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_tag`
--

LOCK TABLES `video_tag` WRITE;
/*!40000 ALTER TABLE `video_tag`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `video_tag`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2023-10-25 19:57:23
