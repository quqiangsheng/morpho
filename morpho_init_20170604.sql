/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.3-m13 : Database - morpho
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

/*Table structure for table `sys_code` */

DROP TABLE IF EXISTS `sys_code`;

CREATE TABLE `sys_code` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL,
  `CODE_ID` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `CODE_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CODE_TEXT` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CODE_VALUE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CODE_DESC` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_VALID` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL,
  `LOG_AGENT` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOG_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOG_INFO` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOG_IP` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOG_TIME` datetime DEFAULT NULL,
  `LOG_USER` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_log` */

insert  into `sys_log`(`UUID`,`LOG_AGENT`,`LOG_ID`,`LOG_INFO`,`LOG_IP`,`LOG_TIME`,`LOG_USER`) values ('2836e47e23b242bea494a3ede51885b3','Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36','2836e47e23b242bea494a3ede51885b3','用户:less 成功登录系统!','127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,','2017-06-04 12:29:32','less'),('335af7204c254dc8b443f9206aa77dd7','Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0','335af7204c254dc8b443f9206aa77dd7','用户:less 成功登录系统!','127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,','2017-06-03 21:05:49','less'),('35ce95c03669456e809b9587290dbbe1','Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36','35ce95c03669456e809b9587290dbbe1','用户:admin 成功登录系统!','127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,','2017-06-04 10:50:06','admin'),('60f8e561d11e457888271ec031d9b2cf','Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0','60f8e561d11e457888271ec031d9b2cf','用户:less 成功登录系统!','127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,','2017-06-03 21:04:46','less'),('f0f5153dec6a4081af2b5f8600befeed','Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0','f0f5153dec6a4081af2b5f8600befeed','用户:less 成功登录系统!','127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,','2017-06-03 21:00:51','less');

/*Table structure for table `sys_organization` */

DROP TABLE IF EXISTS `sys_organization`;

CREATE TABLE `sys_organization` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL,
  `ORG_ID` int(32) DEFAULT NULL,
  `ORG_DESC` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_VALID` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `ORG_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID` int(32) DEFAULT NULL,
  `REGISTER_TIME` date DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_organization` */

insert  into `sys_organization`(`UUID`,`ORG_ID`,`ORG_DESC`,`IS_VALID`,`ORG_NAME`,`PARENT_ID`,`REGISTER_TIME`) values ('07a0b722c0894c37a35cab7b983044c4',1,'最高级','1','最高级',0,'2016-12-12'),('4440bdd26e4e4ce19b1b1d63654b02fe',5,'财务部','1','财务部',3,'2017-06-02'),('4ca1e95bf3d84ce29df72663407d8684',2,'集团总部','1','集团总部',1,'2016-10-09'),('4ca1e95bf3d84ce29df72663407d8e59',3,'软件公司','1','软件公司',2,'2016-12-12'),('7bcc83da60bd40d5a1255b91dac2473b',6,'财务公司','1','财务公司',2,'2017-06-02'),('8b0c4ea7a69345a798beb4f97744a17d',4,'技术部','1','技术部',3,'2016-12-12');

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

insert  into `sys_resource`(`UUID`,`RESOURCE_ID`,`IS_VALID`,`RESOURCE_NAME`,`PARENT_ID`,`PERMISSION`,`RESOURCE_TYPE`,`RESOURCE_URL`) values ('0',0,'1','资源树根',-1,'*','1',''),('1',1,'1','通信加密',0,'transfer:encrypt:*','1','/encrypt/'),('10',10,'1','删除',8,'sys:code:delete','2','/sys/syscode/delete'),('11',11,'1','数据字典页面访问',8,'sys:code:page','2','/sys/syscode/syscode'),('12',12,'1','新增',8,'sys:code:create','2','/sys/syscode/createsyscode'),('13',13,'1','修改',8,'sys:code:update','2','/sys/syscode/updatesyscode'),('14',14,'1','系统日志管理',0,'sys:log:*','1','/sys/syslog/'),('15',15,'1','查询',14,'sys:log:query','2','/sys/syslog/list'),('16',16,'1','页面访问',14,'sys:log:page','2','/sys/syslog/syslog'),('17',17,'1','组织机构管理',0,'sys:organization:*','1','/sys/sysorganization/'),('18',18,'1','查询',17,'sys:organization:query','2','/sys/sysorganization/tree'),('19',19,'1','删除',17,'sys:organization:delete','2','/sys/sysorganization/delete'),('2',2,'1','通信加密密钥交换',1,'transfer:encrypt:start','2','/encrypt/getserverpublickey'),('20',20,'1','新增',17,'sys:organization:create','2','/sys/sysorganization/add'),('21',21,'1','更新',17,'sys:organization:update','2','/sys/sysorganization/update'),('22',22,'1','页面访问',17,'sys:organization:page','2','/sys/sysorganization/sysorganization'),('23',23,'1','资源权限管理',0,'sys:resource:*','1','/sys/sysresource/'),('24',24,'1','查询',23,'sys:resource:query','2','/sys/sysresource/tree'),('25',25,'1','删除',23,'sys:resource:delete','2','/sys/sysresource/delete'),('26',26,'1','新增',23,'sys:resource:create','2','/sys/sysresource/add'),('27',27,'1','更新',23,'sys:resource:update','2','/sys/sysresource/update'),('28',28,'1','页面访问',23,'sys:resource:page','2','/sys/sysresource/sysresource'),('29',29,'1','角色管理',0,'sys:role:*','1','/sys/sysrole/'),('3',3,'1','通信加密解密示例',1,'transfer:encrypt:demo','2','/encrypt/encrypt'),('30',30,'1','查询',29,'sys:role:query','2','/sys/sysrole/list'),('31',31,'1','删除',29,'sys:role:delete','2','/sys/sysrole/delete'),('32',32,'1','新增',29,'sys:role:create','2','/sys/sysrole/createsysrole'),('33',33,'1','更新',29,'sys:role:update','2','/sys/sysrole/updatesysrole'),('34',34,'1','页面访问',29,'sys:role:page','2','/sys/sysrole/sysrole'),('35',35,'1','用户管理',0,'sys:user:*','1','/sys/sysuser/'),('36',36,'1','查询',35,'sys:user:query','2','/sys/sysuser/list'),('37',37,'1','删除',35,'sys:user:delete','2','/sys/sysuser/delete'),('38',38,'1','页面访问',35,'sys:user:page','2','/sys/sysuser/sysuser'),('39',39,'1','新增',35,'sys:user:create','2','/sys/sysuser/createsysuser'),('4',4,'1','Session管理',0,'sys:session:*','1','/sys/httpsession/'),('40',40,'1','更新',35,'sys:user:update','2','/sys/sysuser/updatesysuser'),('5',5,'1','查询',4,'sys:session:query','2','/sys/httpsession/list'),('6',6,'1','删除',4,'sys:session:delete','2','/sys/httpsession/delete'),('7',7,'1','session管理页面访问',4,'sys:session:page','2','/sys/httpsession/httpsession'),('8',8,'1','数据字典管理',0,'sys:code:*','1','/sys/syscode/'),('9',9,'1','查询',8,'sys:code:query','2','/sys/syscode/list');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL,
  `ROLE_DESC` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ROLE_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_VALID` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_IDS` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ROLE_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_role` */

insert  into `sys_role`(`UUID`,`ROLE_DESC`,`ROLE_ID`,`IS_VALID`,`RESOURCE_IDS`,`ROLE_NAME`) values ('2','管理员拥有全部权限','1','1','0,1,2,3,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,4,5,6,7,8,10,11,12,13,9','管理员'),('a9edc635b8384903a7d923c88ca52f93','最小权限','2','1','2,3','最小权限');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `UUID` varchar(32) COLLATE utf8_bin NOT NULL,
  `EMAIL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_VALID` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `REGISTER_TIME` datetime DEFAULT NULL,
  `SALT` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `SYS_ORGANIZATION_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SYS_ROLE_IDS` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `USER_NAME` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `USER_NICKNAME` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `USER_PASSWORD` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `sys_user` */

insert  into `sys_user`(`UUID`,`EMAIL`,`IS_VALID`,`REGISTER_TIME`,`SALT`,`SYS_ORGANIZATION_ID`,`SYS_ROLE_IDS`,`USER_ID`,`USER_NAME`,`USER_NICKNAME`,`USER_PASSWORD`) values ('1','','1','2016-03-28 11:54:58','21db5c79691c39641c5683cc4d098399','1','1','1','admin','管理员用户','8666b02032933eacfde467bda24ad66b'),('2','','1','2016-04-01 09:59:58','27bd32d58701c78dd8b909c07db34206','1','2','2','less','最小权限用户','33a4fad6cefa3c9f6310437ce2392b25'),('60a187647f474d03b11de578156c0961',NULL,'1','2017-06-04 11:00:53','585bd2a210a40e3e9c2a8f2b5af8af26',NULL,'2','4','less3','less3','0ff8b61179be86458e717c2394584262');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
