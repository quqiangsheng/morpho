/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.9-log : Database - morpho
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`morpho` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `morpho`;

/*Table structure for table `qrtz_blob_triggers` */

DROP TABLE IF EXISTS `qrtz_blob_triggers`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_blob_triggers` */

LOCK TABLES `qrtz_blob_triggers` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_calendars` */

DROP TABLE IF EXISTS `qrtz_calendars`;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_calendars` */

LOCK TABLES `qrtz_calendars` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_cron_triggers` */

DROP TABLE IF EXISTS `qrtz_cron_triggers`;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_cron_triggers` */

LOCK TABLES `qrtz_cron_triggers` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_fired_triggers` */

DROP TABLE IF EXISTS `qrtz_fired_triggers`;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_fired_triggers` */

LOCK TABLES `qrtz_fired_triggers` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_job_details` */

DROP TABLE IF EXISTS `qrtz_job_details`;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_job_details` */

LOCK TABLES `qrtz_job_details` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_locks` */

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_locks` */

LOCK TABLES `qrtz_locks` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_paused_trigger_grps` */

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_paused_trigger_grps` */

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_scheduler_state` */

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_scheduler_state` */

LOCK TABLES `qrtz_scheduler_state` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_simple_triggers` */

DROP TABLE IF EXISTS `qrtz_simple_triggers`;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simple_triggers` */

LOCK TABLES `qrtz_simple_triggers` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_simprop_triggers` */

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simprop_triggers` */

LOCK TABLES `qrtz_simprop_triggers` WRITE;

UNLOCK TABLES;

/*Table structure for table `qrtz_triggers` */

DROP TABLE IF EXISTS `qrtz_triggers`;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_triggers` */

LOCK TABLES `qrtz_triggers` WRITE;

UNLOCK TABLES;

/*Table structure for table `schedule_job` */

DROP TABLE IF EXISTS `schedule_job`;

CREATE TABLE `schedule_job` (
  `job_id` bigint(32) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(150) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(2) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';

/*Data for the table `schedule_job` */

LOCK TABLES `schedule_job` WRITE;

UNLOCK TABLES;

/*Table structure for table `schedule_job_log` */

DROP TABLE IF EXISTS `schedule_job_log`;

CREATE TABLE `schedule_job_log` (
  `log_id` varchar(32) NOT NULL COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) NOT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) NOT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(2) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `times` bigint(15) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `error_info` varchar(255) DEFAULT NULL COMMENT '失败信息',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

/*Data for the table `schedule_job_log` */

LOCK TABLES `schedule_job_log` WRITE;

UNLOCK TABLES;

/*Table structure for table `sys_code` */

DROP TABLE IF EXISTS `sys_code`;

CREATE TABLE `sys_code` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `CODE_ID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '字典id',
  `CODE_NAME` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '字典名字',
  `CODE_TEXT` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '字典文本',
  `CODE_VALUE` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '字典值',
  `CODE_DESC` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '字典描述',
  `IS_VALID` varchar(3) COLLATE utf8_bin NOT NULL COMMENT '是否有效1有效  0无效',
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_code` */

LOCK TABLES `sys_code` WRITE;

UNLOCK TABLES;

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `LOG_AGENT` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '浏览器信息',
  `LOG_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '日志id',
  `LOG_INFO` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '日志信息',
  `LOG_IP` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '访问者ip',
  `LOG_TIME` datetime DEFAULT NULL COMMENT '日志产生时间',
  `LOG_USER` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '日志产生涉及相关人员',
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_log` */

LOCK TABLES `sys_log` WRITE;

UNLOCK TABLES;

/*Table structure for table `sys_organization` */

DROP TABLE IF EXISTS `sys_organization`;

CREATE TABLE `sys_organization` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `ORG_ID` int(32) NOT NULL COMMENT '组织机构id',
  `ORG_DESC` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '组织机构描述',
  `IS_VALID` varchar(3) COLLATE utf8_bin NOT NULL COMMENT '是否有效1有效 0无效',
  `ORG_NAME` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '组织机构名字',
  `PARENT_ID` int(32) NOT NULL COMMENT '上级组织机构id',
  `REGISTER_TIME` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_organization` */

LOCK TABLES `sys_organization` WRITE;

insert  into `sys_organization`(`UUID`,`ORG_ID`,`ORG_DESC`,`IS_VALID`,`ORG_NAME`,`PARENT_ID`,`REGISTER_TIME`) values ('07a0b722c0894c37a35cab7b983044c4',1,'最高级','1','最高级',0,'2016-12-12'),('4440bdd26e4e4ce19b1b1d63654b02fe',5,'财务部','1','财务部',3,'2017-06-02'),('4ca1e95bf3d84ce29df72663407d8684',2,'集团总部','1','集团总部',1,'2016-10-09'),('4ca1e95bf3d84ce29df72663407d8e59',3,'软件公司','1','软件公司',2,'2016-12-12'),('7bcc83da60bd40d5a1255b91dac2473b',6,'财务公司','1','财务公司',2,'2017-06-02'),('8b0c4ea7a69345a798beb4f97744a17d',4,'技术部','1','技术部',3,'2016-12-12');

UNLOCK TABLES;

/*Table structure for table `sys_resource` */

DROP TABLE IF EXISTS `sys_resource`;

CREATE TABLE `sys_resource` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL,
  `RESOURCE_ID` int(255) DEFAULT NULL,
  `IS_VALID` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID` int(255) DEFAULT NULL,
  `PERMISSION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_TYPE` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_URL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_resource` */

LOCK TABLES `sys_resource` WRITE;

insert  into `sys_resource`(`UUID`,`RESOURCE_ID`,`IS_VALID`,`RESOURCE_NAME`,`PARENT_ID`,`PERMISSION`,`RESOURCE_TYPE`,`RESOURCE_URL`) values ('0',0,'1','资源树根',-1,'*','1',''),('1',1,'1','通信加密',0,'transfer:encrypt:*','1','/encrypt/'),('10',10,'1','删除',8,'sys:code:delete','2','/sys/syscode/delete'),('11',11,'1','数据字典页面访问',8,'sys:code:page','2','/sys/syscode/syscode'),('12',12,'1','新增',8,'sys:code:create','2','/sys/syscode/createsyscode'),('13',13,'1','修改',8,'sys:code:update','2','/sys/syscode/updatesyscode'),('14',14,'1','系统日志管理',0,'sys:log:*','1','/sys/syslog/'),('15',15,'1','查询',14,'sys:log:query','2','/sys/syslog/list'),('16',16,'1','页面访问',14,'sys:log:page','2','/sys/syslog/syslog'),('17',17,'1','组织机构管理',0,'sys:organization:*','1','/sys/sysorganization/'),('18',18,'1','查询',17,'sys:organization:query','2','/sys/sysorganization/tree'),('19',19,'1','删除',17,'sys:organization:delete','2','/sys/sysorganization/delete'),('2',2,'1','通信加密密钥交换',1,'transfer:encrypt:start','2','/encrypt/getserverpublickey'),('20',20,'1','新增',17,'sys:organization:create','2','/sys/sysorganization/add'),('21',21,'1','更新',17,'sys:organization:update','2','/sys/sysorganization/update'),('22',22,'1','页面访问',17,'sys:organization:page','2','/sys/sysorganization/sysorganization'),('23',23,'1','资源权限管理',0,'sys:resource:*','1','/sys/sysresource/'),('24',24,'1','查询',23,'sys:resource:query','2','/sys/sysresource/tree'),('25',25,'1','删除',23,'sys:resource:delete','2','/sys/sysresource/delete'),('26',26,'1','新增',23,'sys:resource:create','2','/sys/sysresource/add'),('27',27,'1','更新',23,'sys:resource:update','2','/sys/sysresource/update'),('28',28,'1','页面访问',23,'sys:resource:page','2','/sys/sysresource/sysresource'),('29',29,'1','角色管理',0,'sys:role:*','1','/sys/sysrole/'),('3',3,'1','通信加密解密示例',1,'transfer:encrypt:demo','2','/encrypt/encrypt'),('30',30,'1','查询',29,'sys:role:query','2','/sys/sysrole/list'),('31',31,'1','删除',29,'sys:role:delete','2','/sys/sysrole/delete'),('32',32,'1','新增',29,'sys:role:create','2','/sys/sysrole/createsysrole'),('33',33,'1','更新',29,'sys:role:update','2','/sys/sysrole/updatesysrole'),('34',34,'1','页面访问',29,'sys:role:page','2','/sys/sysrole/sysrole'),('35',35,'1','用户管理',0,'sys:user:*','1','/sys/sysuser/'),('36',36,'1','查询',35,'sys:user:query','2','/sys/sysuser/list'),('37',37,'1','删除',35,'sys:user:delete','2','/sys/sysuser/delete'),('38',38,'1','页面访问',35,'sys:user:page','2','/sys/sysuser/sysuser'),('39',39,'1','新增',35,'sys:user:create','2','/sys/sysuser/createsysuser'),('4',4,'1','Session管理',0,'sys:session:*','1','/sys/httpsession/'),('40',40,'1','更新',35,'sys:user:update','2','/sys/sysuser/updatesysuser'),('41',41,'1','定时任务日志',0,'sys:schedulejoblog:*','1','/sys/schedulejoblog'),('42',42,'1','定时任务日志页面访问',41,'sys:schedulejoblog:page','2','/sys/schedulejoblog/schedulejoblogpage'),('43',43,'1','查询',41,'sys:schedulejoblog:query','2','/sys/schedulejoblog/list'),('44',44,'1','定时任务管理',0,'sys:schedulejob:*','1','/sys/schedulejob'),('45',45,'1','定时任务页面访问',44,'sys:schedulejob:page','2','/sys/schedulejob/schedulejobpage'),('46',46,'1','查询详细信息',44,'sys:schedulejob:query','2','/sys/schedulejob/uuid/{jobId}'),('47',47,'1','删除',44,'sys:schedulejob:delete','2','/sys/schedulejob/delete'),('48',48,'1','查询',44,'sys:schedulejob:query','2','/sys/schedulejob/list'),('49',49,'1','新增',44,'sys:schedulejob:add','2','/sys/schedulejob/add'),('5',5,'1','查询',4,'sys:session:query','2','/sys/httpsession/list'),('50',50,'1','修改',44,'sys:schedulejob:update','2','/sys/schedulejob/updateschedulejob'),('51',51,'1','立即执行',44,'sys:schedulejob:run','2','/sys/schedulejob/run'),('52',52,'1','暂停执行',44,'sys:schedulejob:pause','2','/sys/schedulejob/pause'),('53',53,'1','恢复执行',44,'sys:schedulejob:resume','2','/sys/schedulejob/resume'),('6',6,'1','删除',4,'sys:session:delete','2','/sys/httpsession/delete'),('7',7,'1','session管理页面访问',4,'sys:session:page','2','/sys/httpsession/httpsession'),('8',8,'1','数据字典管理',0,'sys:code:*','1','/sys/syscode/'),('9',9,'1','查询',8,'sys:code:query','2','/sys/syscode/list');

UNLOCK TABLES;

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '角色',
  `ROLE_DESC` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色描述',
  `ROLE_ID` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '角色id',
  `IS_VALID` varchar(3) COLLATE utf8_bin NOT NULL COMMENT '是否有效1有效 0无效',
  `RESOURCE_IDS` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '资源id',
  `ROLE_NAME` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '角色名字',
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_role` */

LOCK TABLES `sys_role` WRITE;

insert  into `sys_role`(`UUID`,`ROLE_DESC`,`ROLE_ID`,`IS_VALID`,`RESOURCE_IDS`,`ROLE_NAME`) values ('2','管理员拥有全部权限','1','1','0,1,2,3,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,4,5,6,7,8,10,11,12,13,9,41,42,43,44,45,46,47,48,49,50,51,52,53','管理员'),('a9edc635b8384903a7d923c88ca52f93','最小权限','2','1','2,3','最小权限');

UNLOCK TABLES;

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '主键',
  `EMAIL` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `IS_VALID` varchar(3) COLLATE utf8_bin NOT NULL COMMENT '是否有效1有效 0无效',
  `REGISTER_TIME` datetime DEFAULT NULL COMMENT '注册时间',
  `SALT` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '盐',
  `SYS_ORGANIZATION_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '组织结构id',
  `SYS_ROLE_IDS` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '角色id',
  `USER_ID` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `USER_NAME` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `USER_NICKNAME` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `USER_PASSWORD` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '密码',
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_user` */

LOCK TABLES `sys_user` WRITE;

insert  into `sys_user`(`UUID`,`EMAIL`,`IS_VALID`,`REGISTER_TIME`,`SALT`,`SYS_ORGANIZATION_ID`,`SYS_ROLE_IDS`,`USER_ID`,`USER_NAME`,`USER_NICKNAME`,`USER_PASSWORD`) values ('1','','1','2016-03-28 11:54:58','21db5c79691c39641c5683cc4d098399','1','1','1','admin','管理员用户','8666b02032933eacfde467bda24ad66b'),('2','','1','2016-04-01 09:59:58','21db5c79691c39641c5683cc4d098399','1','2','2','less','最小权限用户','8666b02032933eacfde467bda24ad66b');

UNLOCK TABLES;

/*Table structure for table `uflo_blob` */

DROP TABLE IF EXISTS `uflo_blob`;

CREATE TABLE `uflo_blob` (
  `ID_` bigint(20) NOT NULL,
  `BLOB_VALUE_` longblob,
  `NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_ID_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_blob` */

LOCK TABLES `uflo_blob` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_calendar` */

DROP TABLE IF EXISTS `uflo_calendar`;

CREATE TABLE `uflo_calendar` (
  `ID_` bigint(20) NOT NULL,
  `CATEGORY_ID_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `DESC_` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_calendar` */

LOCK TABLES `uflo_calendar` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_calendar_date` */

DROP TABLE IF EXISTS `uflo_calendar_date`;

CREATE TABLE `uflo_calendar_date` (
  `ID_` bigint(20) NOT NULL,
  `CALENDAR_DATE_` datetime DEFAULT NULL,
  `CALENDAR_ID_` bigint(20) DEFAULT NULL,
  `DAY_OF_MONTH_` int(11) DEFAULT NULL,
  `DAY_OF_WEEK_` int(11) DEFAULT NULL,
  `MONTH_OF_YEAR_` int(11) DEFAULT NULL,
  `NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `RANGE_END_TIME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `RANGE_START_TIME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_calendar_date` */

LOCK TABLES `uflo_calendar_date` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_context_property` */

DROP TABLE IF EXISTS `uflo_context_property`;

CREATE TABLE `uflo_context_property` (
  `KEY_` varchar(120) COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`KEY_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_context_property` */

LOCK TABLES `uflo_context_property` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_his_activity` */

DROP TABLE IF EXISTS `uflo_his_activity`;

CREATE TABLE `uflo_his_activity` (
  `ID_` bigint(20) NOT NULL,
  `DESCRIPTION_` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `NODE_NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_ID_` bigint(20) DEFAULT NULL,
  `CREATE_DATE_` datetime DEFAULT NULL,
  `END_DATE_` datetime DEFAULT NULL,
  `HIS_PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `LEAVE_FLOW_NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `ROOT_PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_his_activity` */

LOCK TABLES `uflo_his_activity` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_his_blob` */

DROP TABLE IF EXISTS `uflo_his_blob`;

CREATE TABLE `uflo_his_blob` (
  `ID_` bigint(20) NOT NULL,
  `BLOB_VALUE_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_his_blob` */

LOCK TABLES `uflo_his_blob` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_his_process_instance` */

DROP TABLE IF EXISTS `uflo_his_process_instance`;

CREATE TABLE `uflo_his_process_instance` (
  `ID_` bigint(20) NOT NULL,
  `BUSINESS_ID_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_DATE_` datetime DEFAULT NULL,
  `END_DATE_` datetime DEFAULT NULL,
  `PROCESS_ID_` bigint(20) DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `PROMOTER_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `SUBJECT_` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `TAG_` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_his_process_instance` */

LOCK TABLES `uflo_his_process_instance` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_his_task` */

DROP TABLE IF EXISTS `uflo_his_task`;

CREATE TABLE `uflo_his_task` (
  `ID_` bigint(20) NOT NULL,
  `DESCRIPTION_` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `NODE_NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_ID_` bigint(20) DEFAULT NULL,
  `ASSIGNEE_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_ID_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_DATE_` datetime DEFAULT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `END_DATE_` datetime DEFAULT NULL,
  `HIS_PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `OPINION_` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `ROOT_PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `SUBJECT_` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` bigint(20) DEFAULT NULL,
  `TASK_NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_his_task` */

LOCK TABLES `uflo_his_task` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_his_variable` */

DROP TABLE IF EXISTS `uflo_his_variable`;

CREATE TABLE `uflo_his_variable` (
  `ID_` bigint(20) NOT NULL,
  `HIS_PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `KEY_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_his_variable` */

LOCK TABLES `uflo_his_variable` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_job_heartbeat` */

DROP TABLE IF EXISTS `uflo_job_heartbeat`;

CREATE TABLE `uflo_job_heartbeat` (
  `ID_` varchar(60) COLLATE utf8_bin NOT NULL,
  `DATE_` datetime DEFAULT NULL,
  `INSTANCE_NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_job_heartbeat` */

LOCK TABLES `uflo_job_heartbeat` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_process` */

DROP TABLE IF EXISTS `uflo_process`;

CREATE TABLE `uflo_process` (
  `ID_` bigint(20) NOT NULL,
  `CATEGORY_ID_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_DATE_` datetime DEFAULT NULL,
  `DESCRIPTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EFFECT_DATE_` datetime DEFAULT NULL,
  `KEY_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `START_PROCESS_URL_` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_process` */

LOCK TABLES `uflo_process` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_process_instance` */

DROP TABLE IF EXISTS `uflo_process_instance`;

CREATE TABLE `uflo_process_instance` (
  `ID_` bigint(20) NOT NULL,
  `BUSINESS_ID_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_DATE_` datetime DEFAULT NULL,
  `CURRENT_NODE_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `CURRENT_TASK_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `HIS_PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `PARALLEL_INSTANCE_COUNT_` int(11) DEFAULT NULL,
  `PARENT_ID_` bigint(20) DEFAULT NULL,
  `PROCESS_ID_` bigint(20) DEFAULT NULL,
  `PROMOTER_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_ID_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `SUBJECT_` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `TAG_` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_process_instance` */

LOCK TABLES `uflo_process_instance` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_task` */

DROP TABLE IF EXISTS `uflo_task`;

CREATE TABLE `uflo_task` (
  `ID_` bigint(20) NOT NULL,
  `DESCRIPTION_` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `NODE_NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_ID_` bigint(20) DEFAULT NULL,
  `ASSIGNEE_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_ID_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `COUNTERSIGN_COUNT_` int(11) DEFAULT NULL,
  `CREATE_DATE_` datetime DEFAULT NULL,
  `DATE_UNIT_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `DUE_ACTION_DATE_` datetime DEFAULT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `END_DATE_` datetime DEFAULT NULL,
  `OPINION_` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PREV_STATE_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PREV_TASK_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `PROGRESS_` int(11) DEFAULT NULL,
  `ROOT_PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `SUBJECT_` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `TASK_NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_task` */

LOCK TABLES `uflo_task` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_task_appointor` */

DROP TABLE IF EXISTS `uflo_task_appointor`;

CREATE TABLE `uflo_task_appointor` (
  `ID_` bigint(20) NOT NULL,
  `APPOINTOR_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `APPOINTOR_NODE_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `TASK_NODE_NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_task_appointor` */

LOCK TABLES `uflo_task_appointor` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_task_participator` */

DROP TABLE IF EXISTS `uflo_task_participator`;

CREATE TABLE `uflo_task_participator` (
  `ID_` bigint(20) NOT NULL,
  `TASK_ID_` bigint(20) NOT NULL,
  `USER_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `FKpqe63u3gnbwpjhvf8996md6ip` (`TASK_ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_task_participator` */

LOCK TABLES `uflo_task_participator` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_task_reminder` */

DROP TABLE IF EXISTS `uflo_task_reminder`;

CREATE TABLE `uflo_task_reminder` (
  `ID_` bigint(20) NOT NULL,
  `CRON_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_ID_` bigint(20) DEFAULT NULL,
  `REMINDER_HANDLER_BEAN_` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `START_DATE_` datetime DEFAULT NULL,
  `TASK_ID_` bigint(20) DEFAULT NULL,
  `TASK_NODE_NAME_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `REMINDER_TYPE_` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_task_reminder` */

LOCK TABLES `uflo_task_reminder` WRITE;

UNLOCK TABLES;

/*Table structure for table `uflo_variable` */

DROP TABLE IF EXISTS `uflo_variable`;

CREATE TABLE `uflo_variable` (
  `TYPE_` varchar(30) COLLATE utf8_bin NOT NULL,
  `ID_` bigint(20) NOT NULL,
  `KEY_` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `ROOT_PROCESS_INSTANCE_ID_` bigint(20) DEFAULT NULL,
  `BLOB_ID_` bigint(20) DEFAULT NULL,
  `BOOLEAN_VALUE_` bit(1) DEFAULT NULL,
  `BYTE_VALUE_` tinyint(4) DEFAULT NULL,
  `CHARACTER_VALUE_` char(1) COLLATE utf8_bin DEFAULT NULL,
  `DATE_VALUE_` datetime DEFAULT NULL,
  `DOUBLE_VALUE_` double DEFAULT NULL,
  `FLOAT_VALUE_` float DEFAULT NULL,
  `INTEGER_VALUE_` int(11) DEFAULT NULL,
  `LONG_VALUE_` bigint(20) DEFAULT NULL,
  `SHORT_VALUE_` smallint(6) DEFAULT NULL,
  `STRING_VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `uflo_variable` */

LOCK TABLES `uflo_variable` WRITE;

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
