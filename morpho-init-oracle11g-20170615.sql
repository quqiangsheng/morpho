

-- ----------------------------
-- Table structure for sys_code
-- ----------------------------
CREATE TABLE "SYS_CODE" (
"UUID" VARCHAR2(32) NOT NULL ,
"CODE_ID" VARCHAR2(32) NULL ,
"CODE_NAME" VARCHAR2(255) NULL ,
"CODE_TEXT" VARCHAR2(255) NULL ,
"CODE_VALUE" VARCHAR2(255) NULL ,
"CODE_DESC" VARCHAR2(255) NULL ,
"IS_VALID" VARCHAR2(3) NULL 
)

;

-- ----------------------------
-- Records of sys_code
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------

CREATE TABLE "SYS_LOG" (
"UUID" VARCHAR2(32) NOT NULL ,
"LOG_AGENT" VARCHAR2(255) NULL ,
"LOG_ID" VARCHAR2(255) NULL ,
"LOG_INFO" VARCHAR2(255) NULL ,
"LOG_IP" VARCHAR2(255) NULL ,
"LOG_TIME" DATE NULL ,
"LOG_USER" VARCHAR2(255) NULL 
)

;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO "SYS_LOG" VALUES ('0122cffe03d94b03acc68145c7b736eb', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '0122cffe03d94b03acc68145c7b736eb', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:09:51', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('0ffa34ebe8134f61ac738642582a96da', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '0ffa34ebe8134f61ac738642582a96da', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:40:23', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('1441aa8b72a143c6a0bb7b1995d68a3e', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '1441aa8b72a143c6a0bb7b1995d68a3e', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:20:00', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('149edbab273c4be88fcf62e841c3d829', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '149edbab273c4be88fcf62e841c3d829', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:11:36', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('1789399202944e23b02f9fc25e2f2bc2', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '1789399202944e23b02f9fc25e2f2bc2', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:07:47', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('1a7f2ca3189540e79e60fb55c913ef71', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '1a7f2ca3189540e79e60fb55c913ef71', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:22:58', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('1ffe12bb679f4be3b30cbcd2b29e8697', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '1ffe12bb679f4be3b30cbcd2b29e8697', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 09:33:57', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('230f7e3781b7499d9566faa49b823666', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '230f7e3781b7499d9566faa49b823666', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:11:56', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('252978e7db1e4dfb962891ba68fa56f8', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '252978e7db1e4dfb962891ba68fa56f8', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:07:30', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('25530d3e162541b8abf6f592fc6b1614', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '25530d3e162541b8abf6f592fc6b1614', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:24:30', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('2836e47e23b242bea494a3ede51885b3', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36', '2836e47e23b242bea494a3ede51885b3', '用户:less 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-04 12:29:32', 'YYYY-MM-DD HH24:MI:SS'), 'less');
INSERT INTO "SYS_LOG" VALUES ('335af7204c254dc8b443f9206aa77dd7', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', '335af7204c254dc8b443f9206aa77dd7', '用户:less 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-03 21:05:49', 'YYYY-MM-DD HH24:MI:SS'), 'less');
INSERT INTO "SYS_LOG" VALUES ('35ce95c03669456e809b9587290dbbe1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36', '35ce95c03669456e809b9587290dbbe1', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-04 10:50:06', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('38d214695e3d40cb81655371af60224f', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '38d214695e3d40cb81655371af60224f', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:23:11', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('3bf87acb80dd42ed8f73bf27d2bf73ee', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '3bf87acb80dd42ed8f73bf27d2bf73ee', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:26:25', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('425ebc32c7194101b0380e797e46b535', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '425ebc32c7194101b0380e797e46b535', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:10:48', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('4417556e753f457686a08c686a89f2d4', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '4417556e753f457686a08c686a89f2d4', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:17:29', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('44c27756a7484160a78c303d85cc75e6', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '44c27756a7484160a78c303d85cc75e6', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 09:58:05', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('4512bd77b202499f9f019e1ca222269a', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '4512bd77b202499f9f019e1ca222269a', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:12:32', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('45640b6131b842c98175d6eb1676c1c9', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '45640b6131b842c98175d6eb1676c1c9', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:19:56', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('50637ce1d72348ae9e7ceaa001393fa2', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', '50637ce1d72348ae9e7ceaa001393fa2', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 16:52:28', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('50b74aa2a562417298c77545274ec054', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/7.2.0.12990', '50b74aa2a562417298c77545274ec054', '用户:test 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-05 09:44:20', 'YYYY-MM-DD HH24:MI:SS'), 'test');
INSERT INTO "SYS_LOG" VALUES ('5d401bb6b98c43c1bf507431edb033d1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '5d401bb6b98c43c1bf507431edb033d1', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:00:46', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('60b8a8762366491587b8a05a84df50ed', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '60b8a8762366491587b8a05a84df50ed', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 09:41:50', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('60f8e561d11e457888271ec031d9b2cf', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', '60f8e561d11e457888271ec031d9b2cf', '用户:less 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-03 21:04:46', 'YYYY-MM-DD HH24:MI:SS'), 'less');
INSERT INTO "SYS_LOG" VALUES ('6476dee379ee4c11a8a5d5cfb9ffec7e', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '6476dee379ee4c11a8a5d5cfb9ffec7e', '用户:test 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-05 09:32:56', 'YYYY-MM-DD HH24:MI:SS'), 'test');
INSERT INTO "SYS_LOG" VALUES ('69635cdce92346d7bb2928dd0232504e', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '69635cdce92346d7bb2928dd0232504e', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 09:54:11', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('6a493de738304371b2bb1045f76f0e99', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '6a493de738304371b2bb1045f76f0e99', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:28:03', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('6c2303725bdc4eadab149bcdc175857b', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '6c2303725bdc4eadab149bcdc175857b', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:22:16', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('77b1d299fe8f4ab69fcd45864c3808ef', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '77b1d299fe8f4ab69fcd45864c3808ef', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:08:48', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('7841d8f0b0964bd28fdc76e910b48e85', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '7841d8f0b0964bd28fdc76e910b48e85', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 09:53:38', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('7b382405fbcf4167b1f313716acfb582', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '7b382405fbcf4167b1f313716acfb582', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:31:11', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('7e636090d4aa467d88132505cb4be806', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '7e636090d4aa467d88132505cb4be806', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:04:17', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('8c2cdbf49f3d47ffbe822317cfd99844', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '8c2cdbf49f3d47ffbe822317cfd99844', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 09:59:19', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('913a5c44fafb418293fbfb57afc188f0', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '913a5c44fafb418293fbfb57afc188f0', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:40:43', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('93af6d3521424cfbae98fbfc3154a07e', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '93af6d3521424cfbae98fbfc3154a07e', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:06:26', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('988cd2b354e7456fa313822bb23c9617', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '988cd2b354e7456fa313822bb23c9617', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:11:59', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('9b276f42f03f4b89ae20b25c25dbe3c1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '9b276f42f03f4b89ae20b25c25dbe3c1', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 09:40:08', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('9b8aa794557841d8ada2b01574c8d99b', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '9b8aa794557841d8ada2b01574c8d99b', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:19:30', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('9e4bc471842d45a699a3377ecac65ace', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '9e4bc471842d45a699a3377ecac65ace', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:27:19', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('9ff19db37f904a079c04123ecddc2973', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '9ff19db37f904a079c04123ecddc2973', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:00:38', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('a2759d5e64494066a20c8234ec19568d', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'a2759d5e64494066a20c8234ec19568d', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:58:50', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('a4567b29fbfa4115a09eeb6b44380297', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'a4567b29fbfa4115a09eeb6b44380297', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-06 09:44:34', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('abf5774aca514fa0baa1848588361b3d', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'abf5774aca514fa0baa1848588361b3d', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:08:49', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('adc6e0d601534595b054e54121b8912a', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'adc6e0d601534595b054e54121b8912a', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:51:06', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('b9b428cfc7b142f9b68c2b3294c65644', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'b9b428cfc7b142f9b68c2b3294c65644', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-05 09:42:13', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('bb60f917887245f9b30db2b2434d4ce5', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'bb60f917887245f9b30db2b2434d4ce5', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:23:26', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('c12d098b25d642ecb77ff37050d9bf66', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'c12d098b25d642ecb77ff37050d9bf66', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 09:52:10', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('ce6b49cd43ba4872b34d02ebf88b34e8', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'ce6b49cd43ba4872b34d02ebf88b34e8', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:13:25', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('d4a25cf09d074399ba6de93f194fb3df', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'd4a25cf09d074399ba6de93f194fb3df', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:18:13', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('d5b5b47f1975464788c9dedf7e9dee22', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'd5b5b47f1975464788c9dedf7e9dee22', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-05 09:31:54', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('d77e3bcebf91454faa2f3ce049c59c21', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'd77e3bcebf91454faa2f3ce049c59c21', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:49:29', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('d7d883a2fa4b45e9965106e4508758cb', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'd7d883a2fa4b45e9965106e4508758cb', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:10:15', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('dadb4ef458ff4354bdbaaaea0372f7d5', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'dadb4ef458ff4354bdbaaaea0372f7d5', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:11:13', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('e6594ca5559b4fd9975d9c9bfb91b98c', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'e6594ca5559b4fd9975d9c9bfb91b98c', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:54:07', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('e850d2f8a3c0480eb0747e5a5cf017fd', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'e850d2f8a3c0480eb0747e5a5cf017fd', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:30:45', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('ea726c9fe49141099ecf798311db7638', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'ea726c9fe49141099ecf798311db7638', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 11:13:51', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('f0f5153dec6a4081af2b5f8600befeed', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'f0f5153dec6a4081af2b5f8600befeed', '用户:less 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-03 21:00:51', 'YYYY-MM-DD HH24:MI:SS'), 'less');
INSERT INTO "SYS_LOG" VALUES ('f428d0ad4eaa4891a15527020ffbfc27', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'f428d0ad4eaa4891a15527020ffbfc27', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:30:12', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('f6a5ebc2860e4cc48d07c802a31dc03f', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'f6a5ebc2860e4cc48d07c802a31dc03f', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:32:00', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('f713c620c5b24fc6ba497617bda338fc', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'f713c620c5b24fc6ba497617bda338fc', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 09:35:04', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('f7b25ddc01574c88bf1ee6ea8c60f077', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'f7b25ddc01574c88bf1ee6ea8c60f077', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-04 19:55:15', 'YYYY-MM-DD HH24:MI:SS'), 'admin');
INSERT INTO "SYS_LOG" VALUES ('fc20c7f2421e460da2f1b4c0c4c2e52a', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'fc20c7f2421e460da2f1b4c0c4c2e52a', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', TO_TIMESTAMP('2017-06-08 10:53:33', 'YYYY-MM-DD HH24:MI:SS'), 'admin');

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------

CREATE TABLE "SYS_ORGANIZATION" (
"UUID" VARCHAR2(32) NOT NULL ,
"ORG_ID" NUMBER(11) NULL ,
"ORG_DESC" VARCHAR2(255) NULL ,
"IS_VALID" VARCHAR2(3) NULL ,
"ORG_NAME" VARCHAR2(255) NULL ,
"PARENT_ID" NUMBER(11) NULL ,
"REGISTER_TIME" DATE NULL 
)

;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO "SYS_ORGANIZATION" VALUES ('07a0b722c0894c37a35cab7b983044c4', '1', '最高级', '1', '最高级', '0', TO_TIMESTAMP('2016-12-12', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "SYS_ORGANIZATION" VALUES ('4440bdd26e4e4ce19b1b1d63654b02fe', '5', '财务部', '1', '财务部', '3', TO_TIMESTAMP('2017-06-02', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "SYS_ORGANIZATION" VALUES ('4ca1e95bf3d84ce29df72663407d8684', '2', '集团总部', '1', '集团总部', '1', TO_TIMESTAMP('2016-10-09', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "SYS_ORGANIZATION" VALUES ('4ca1e95bf3d84ce29df72663407d8e59', '3', '软件公司', '1', '软件公司', '2', TO_TIMESTAMP('2016-12-12', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "SYS_ORGANIZATION" VALUES ('7bcc83da60bd40d5a1255b91dac2473b', '6', '财务公司', '1', '财务公司', '2', TO_TIMESTAMP('2017-06-02', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "SYS_ORGANIZATION" VALUES ('8b0c4ea7a69345a798beb4f97744a17d', '4', '技术部', '1', '技术部', '3', TO_TIMESTAMP('2016-12-12', 'YYYY-MM-DD HH24:MI:SS'));

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------

CREATE TABLE "SYS_RESOURCE" (
"UUID" VARCHAR2(32) NOT NULL ,
"RESOURCE_ID" NUMBER(11) NULL ,
"IS_VALID" VARCHAR2(3) NULL ,
"RESOURCE_NAME" VARCHAR2(255) NULL ,
"PARENT_ID" NUMBER(11) NULL ,
"PERMISSION" VARCHAR2(255) NULL ,
"RESOURCE_TYPE" VARCHAR2(5) NULL ,
"RESOURCE_URL" VARCHAR2(255) NULL 
)

;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------



insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('0', 0, '1', '资源树根', -1, '*', '1', null);

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('1', 1, '1', '通信加密', 0, 'transfer:encrypt:*', '1', '/encrypt/');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('10', 10, '1', '删除', 8, 'sys:code:delete', '2', '/sys/syscode/delete');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('11', 11, '1', '数据字典页面访问', 8, 'sys:code:page', '2', '/sys/syscode/syscode');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('12', 12, '1', '新增', 8, 'sys:code:create', '2', '/sys/syscode/createsyscode');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('13', 13, '1', '修改', 8, 'sys:code:update', '2', '/sys/syscode/updatesyscode');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('14', 14, '1', '系统日志管理', 0, 'sys:log:*', '1', '/sys/syslog/');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('15', 15, '1', '查询', 14, 'sys:log:query', '2', '/sys/syslog/list');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('16', 16, '1', '页面访问', 14, 'sys:log:page', '2', '/sys/syslog/syslog');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('17', 17, '1', '组织机构管理', 0, 'sys:organization:*', '1', '/sys/sysorganization/');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('18', 18, '1', '查询', 17, 'sys:organization:query', '2', '/sys/sysorganization/tree');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('19', 19, '1', '删除', 17, 'sys:organization:delete', '2', '/sys/sysorganization/delete');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('2', 2, '1', '通信加密密钥交换', 1, 'transfer:encrypt:start', '2', '/encrypt/getserverpublickey');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('20', 20, '1', '新增', 17, 'sys:organization:create', '2', '/sys/sysorganization/add');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('21', 21, '1', '更新', 17, 'sys:organization:update', '2', '/sys/sysorganization/update');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('22', 22, '1', '页面访问', 17, 'sys:organization:page', '2', '/sys/sysorganization/sysorganization');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('23', 23, '1', '资源权限管理', 0, 'sys:resource:*', '1', '/sys/sysresource/');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('24', 24, '1', '查询', 23, 'sys:resource:query', '2', '/sys/sysresource/tree');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('25', 25, '1', '删除', 23, 'sys:resource:delete', '2', '/sys/sysresource/delete');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('26', 26, '1', '新增', 23, 'sys:resource:create', '2', '/sys/sysresource/add');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('27', 27, '1', '更新', 23, 'sys:resource:update', '2', '/sys/sysresource/update');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('28', 28, '1', '页面访问', 23, 'sys:resource:page', '2', '/sys/sysresource/sysresource');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('29', 29, '1', '角色管理', 0, 'sys:role:*', '1', '/sys/sysrole/');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('3', 3, '1', '通信加密解密示例', 1, 'transfer:encrypt:demo', '2', '/encrypt/encrypt');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('30', 30, '1', '查询', 29, 'sys:role:query', '2', '/sys/sysrole/list');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('31', 31, '1', '删除', 29, 'sys:role:delete', '2', '/sys/sysrole/delete');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('32', 32, '1', '新增', 29, 'sys:role:create', '2', '/sys/sysrole/createsysrole');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('33', 33, '1', '更新', 29, 'sys:role:update', '2', '/sys/sysrole/updatesysrole');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('34', 34, '1', '页面访问', 29, 'sys:role:page', '2', '/sys/sysrole/sysrole');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('35', 35, '1', '用户管理', 0, 'sys:user:*', '1', '/sys/sysuser/');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('36', 36, '1', '查询', 35, 'sys:user:query', '2', '/sys/sysuser/list');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('37', 37, '1', '删除', 35, 'sys:user:delete', '2', '/sys/sysuser/delete');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('38', 38, '1', '页面访问', 35, 'sys:user:page', '2', '/sys/sysuser/sysuser');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('39', 39, '1', '新增', 35, 'sys:user:create', '2', '/sys/sysuser/createsysuser');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('4', 4, '1', 'Session管理', 0, 'sys:session:*', '1', '/sys/httpsession/');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('40', 40, '1', '更新', 35, 'sys:user:update', '2', '/sys/sysuser/updatesysuser');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('41', 41, '1', '定时任务日志', 0, 'sys:schedulejoblog:*', '1', '/sys/schedulejoblog');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('42', 42, '1', '定时任务日志页面访问', 41, 'sys:schedulejoblog:page', '2', '/sys/schedulejoblog/schedulejoblogpage');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('43', 43, '1', '查询', 41, 'sys:schedulejoblog:query', '2', '/sys/schedulejoblog/list');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('44', 44, '1', '定时任务管理', 0, 'sys:schedulejob:*', '1', '/sys/schedulejob');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('45', 45, '1', '定时任务页面访问', 44, 'sys:schedulejob:page', '2', '/sys/schedulejob/schedulejobpage');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('46', 46, '1', '查询详细信息', 44, 'sys:schedulejob:query', '2', '/sys/schedulejob/uuid/{jobId}');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('47', 47, '1', '删除', 44, 'sys:schedulejob:delete', '2', '/sys/schedulejob/delete');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('48', 48, '1', '查询', 44, 'sys:schedulejob:query', '2', '/sys/schedulejob/list');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('49', 49, '1', '新增', 44, 'sys:schedulejob:add', '2', '/sys/schedulejob/add');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('5', 5, '1', '查询', 4, 'sys:session:query', '2', '/sys/httpsession/list');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('50', 50, '1', '修改', 44, 'sys:schedulejob:update', '2', '/sys/schedulejob/updateschedulejob');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('51', 51, '1', '立即执行', 44, 'sys:schedulejob:run', '2', '/sys/schedulejob/run');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('52', 52, '1', '暂停执行', 44, 'sys:schedulejob:pause', '2', '/sys/schedulejob/pause');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('53', 53, '1', '恢复执行', 44, 'sys:schedulejob:resume', '2', '/sys/schedulejob/resume');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('6', 6, '1', '删除', 4, 'sys:session:delete', '2', '/sys/httpsession/delete');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('7', 7, '1', 'session管理页面访问', 4, 'sys:session:page', '2', '/sys/httpsession/httpsession');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('8', 8, '1', '数据字典管理', 0, 'sys:code:*', '1', '/sys/syscode/');

insert into SYS_RESOURCE (UUID, RESOURCE_ID, IS_VALID, RESOURCE_NAME, PARENT_ID, PERMISSION, RESOURCE_TYPE, RESOURCE_URL)
values ('9', 9, '1', '查询', 8, 'sys:code:query', '2', '/sys/syscode/list');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------

CREATE TABLE "SYS_ROLE" (
"UUID" VARCHAR2(32) NOT NULL ,
"ROLE_DESC" VARCHAR2(255) NULL ,
"ROLE_ID" VARCHAR2(255) NULL ,
"IS_VALID" VARCHAR2(3) NULL ,
"RESOURCE_IDS" VARCHAR2(255) NULL ,
"ROLE_NAME" VARCHAR2(255) NULL 
)

;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "SYS_ROLE" VALUES ('2', '管理员拥有全部权限', '1', '1', '0,1,2,3,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,4,5,6,7,8,10,11,12,13,9,41,42,43,44,45,46,47,48,49,50,51,52,53', '管理员');
INSERT INTO "SYS_ROLE" VALUES ('a9edc635b8384903a7d923c88ca52f93', '最小权限', '2', '1', '2,3', '最小权限');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------

CREATE TABLE "SYS_USER" (
"UUID" VARCHAR2(32) NOT NULL ,
"EMAIL" VARCHAR2(255) NULL ,
"IS_VALID" VARCHAR2(3) NULL ,
"REGISTER_TIME" DATE NULL ,
"SALT" VARCHAR2(32) NULL ,
"SYS_ORGANIZATION_ID" VARCHAR2(255) NULL ,
"SYS_ROLE_IDS" VARCHAR2(255) NULL ,
"USER_ID" VARCHAR2(45) NULL ,
"USER_NAME" VARCHAR2(45) NULL ,
"USER_NICKNAME" VARCHAR2(45) NULL ,
"USER_PASSWORD" VARCHAR2(45) NULL 
)

;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "SYS_USER" VALUES ('1', '', '1', TO_TIMESTAMP('2016-03-28 11:54:58', 'YYYY-MM-DD HH24:MI:SS'), '21db5c79691c39641c5683cc4d098399', '1', '1', '1', 'admin', '管理员用户', '8666b02032933eacfde467bda24ad66b');
INSERT INTO "SYS_USER" VALUES ('2', '', '1', TO_TIMESTAMP('2016-04-01 09:59:58', 'YYYY-MM-DD HH24:MI:SS'), '27bd32d58701c78dd8b909c07db34206', '1', '2', '2', 'less', '最小权限用户', '33a4fad6cefa3c9f6310437ce2392b25');

-- ----------------------------
-- Primary Key structure for table sys_code
-- ----------------------------
ALTER TABLE "SYS_CODE" ADD PRIMARY KEY ("UUID");

-- ----------------------------
-- Primary Key structure for table sys_log
-- ----------------------------
ALTER TABLE "SYS_LOG" ADD PRIMARY KEY ("UUID");

-- ----------------------------
-- Primary Key structure for table sys_organization
-- ----------------------------
ALTER TABLE "SYS_ORGANIZATION" ADD PRIMARY KEY ("UUID");

-- ----------------------------
-- Primary Key structure for table sys_resource
-- ----------------------------
ALTER TABLE "SYS_RESOURCE" ADD PRIMARY KEY ("UUID");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "SYS_ROLE" ADD PRIMARY KEY ("UUID");

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "SYS_USER" ADD PRIMARY KEY ("UUID");

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
create table SCHEDULE_JOB
(
  job_id          NUMBER(19) not null,
  bean_name       VARCHAR2(255 CHAR),
  create_time     TIMESTAMP(6),
  cron_expression VARCHAR2(255 CHAR),
  method_name     VARCHAR2(255 CHAR),
  params          VARCHAR2(255 CHAR),
  remark          VARCHAR2(255 CHAR),
  status          NUMBER(10)
)
alter table SCHEDULE_JOB
  add primary key (JOB_ID);



-- Create table
create table SCHEDULE_JOB_LOG
(
  log_id      VARCHAR2(255 CHAR) not null,
  bean_name   VARCHAR2(255 CHAR),
  create_time TIMESTAMP(6),
  error_info  VARCHAR2(255 CHAR),
  job_id      NUMBER(19),
  method_name VARCHAR2(255 CHAR),
  params      VARCHAR2(255 CHAR),
  status      NUMBER(10),
  times       NUMBER(19)
)

alter table SCHEDULE_JOB_LOG
  add primary key (LOG_ID);

--
-- A hint submitted by a user: Oracle DB MUST be created as "shared" and the 
-- job_queue_processes parameter  must be greater than 2
-- However, these settings are pretty much standard after any
-- Oracle install, so most users need not worry about this.
--
-- Many other users (including the primary author of Quartz) have had success
-- runing in dedicated mode, so only consider the above as a hint ;-)
--

delete from qrtz_fired_triggers;
delete from qrtz_simple_triggers;
delete from qrtz_simprop_triggers;
delete from qrtz_cron_triggers;
delete from qrtz_blob_triggers;
delete from qrtz_triggers;
delete from qrtz_job_details;
delete from qrtz_calendars;
delete from qrtz_paused_trigger_grps;
delete from qrtz_locks;
delete from qrtz_scheduler_state;

drop table qrtz_calendars;
drop table qrtz_fired_triggers;
drop table qrtz_blob_triggers;
drop table qrtz_cron_triggers;
drop table qrtz_simple_triggers;
drop table qrtz_simprop_triggers;
drop table qrtz_triggers;
drop table qrtz_job_details;
drop table qrtz_paused_trigger_grps;
drop table qrtz_locks;
drop table qrtz_scheduler_state;


CREATE TABLE qrtz_job_details
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL,
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    JOB_CLASS_NAME   VARCHAR2(250) NOT NULL, 
    IS_DURABLE VARCHAR2(1) NOT NULL,
    IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
    IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);
CREATE TABLE qrtz_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    JOB_NAME  VARCHAR2(200) NOT NULL, 
    JOB_GROUP VARCHAR2(200) NOT NULL,
    DESCRIPTION VARCHAR2(250) NULL,
    NEXT_FIRE_TIME NUMBER(13) NULL,
    PREV_FIRE_TIME NUMBER(13) NULL,
    PRIORITY NUMBER(13) NULL,
    TRIGGER_STATE VARCHAR2(16) NOT NULL,
    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
    START_TIME NUMBER(13) NOT NULL,
    END_TIME NUMBER(13) NULL,
    CALENDAR_NAME VARCHAR2(200) NULL,
    MISFIRE_INSTR NUMBER(2) NULL,
    JOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_TRIGGER_TO_JOBS_FK FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP) 
      REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP) 
);
CREATE TABLE qrtz_simple_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    REPEAT_COUNT NUMBER(7) NOT NULL,
    REPEAT_INTERVAL NUMBER(12) NOT NULL,
    TIMES_TRIGGERED NUMBER(10) NOT NULL,
    CONSTRAINT QRTZ_SIMPLE_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_SIMPLE_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
	REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_cron_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    CRON_EXPRESSION VARCHAR2(120) NOT NULL,
    TIME_ZONE_ID VARCHAR2(80),
    CONSTRAINT QRTZ_CRON_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_CRON_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
      REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_simprop_triggers
  (          
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    STR_PROP_1 VARCHAR2(512) NULL,
    STR_PROP_2 VARCHAR2(512) NULL,
    STR_PROP_3 VARCHAR2(512) NULL,
    INT_PROP_1 NUMBER(10) NULL,
    INT_PROP_2 NUMBER(10) NULL,
    LONG_PROP_1 NUMBER(13) NULL,
    LONG_PROP_2 NUMBER(13) NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR2(1) NULL,
    BOOL_PROP_2 VARCHAR2(1) NULL,
    CONSTRAINT QRTZ_SIMPROP_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_SIMPROP_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
      REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_blob_triggers
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    BLOB_DATA BLOB NULL,
    CONSTRAINT QRTZ_BLOB_TRIG_PK PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    CONSTRAINT QRTZ_BLOB_TRIG_TO_TRIG_FK FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
        REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_calendars
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    CALENDAR_NAME  VARCHAR2(200) NOT NULL, 
    CALENDAR BLOB NOT NULL,
    CONSTRAINT QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);
CREATE TABLE qrtz_paused_trigger_grps
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    TRIGGER_GROUP  VARCHAR2(200) NOT NULL, 
    CONSTRAINT QRTZ_PAUSED_TRIG_GRPS_PK PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);
CREATE TABLE qrtz_fired_triggers 
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    ENTRY_ID VARCHAR2(95) NOT NULL,
    TRIGGER_NAME VARCHAR2(200) NOT NULL,
    TRIGGER_GROUP VARCHAR2(200) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    FIRED_TIME NUMBER(13) NOT NULL,
    SCHED_TIME NUMBER(13) NOT NULL,
    PRIORITY NUMBER(13) NOT NULL,
    STATE VARCHAR2(16) NOT NULL,
    JOB_NAME VARCHAR2(200) NULL,
    JOB_GROUP VARCHAR2(200) NULL,
    IS_NONCONCURRENT VARCHAR2(1) NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NULL,
    CONSTRAINT QRTZ_FIRED_TRIGGER_PK PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);
CREATE TABLE qrtz_scheduler_state 
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    INSTANCE_NAME VARCHAR2(200) NOT NULL,
    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
    CONSTRAINT QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);
CREATE TABLE qrtz_locks
  (
    SCHED_NAME VARCHAR2(120) NOT NULL,
    LOCK_NAME  VARCHAR2(40) NOT NULL, 
    CONSTRAINT QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);


