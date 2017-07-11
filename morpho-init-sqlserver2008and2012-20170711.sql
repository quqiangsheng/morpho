USE [morpho]
GO

/****** Object:  Table [dbo].[sys_code]    Script Date: 2017/7/11 10:25:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[sys_code](
	[UUID] [varchar](32) NOT NULL,
	[CODE_ID] [varchar](32) NULL,
	[CODE_NAME] [varchar](255) NULL,
	[CODE_TEXT] [varchar](255) NULL,
	[CODE_VALUE] [varchar](255) NULL,
	[CODE_DESC] [varchar](255) NULL,
	[IS_VALID] [varchar](3) NULL,
PRIMARY KEY CLUSTERED 
(
	[UUID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


CREATE TABLE [dbo].[sys_log](
	[UUID] [varchar](32) NOT NULL,
	[LOG_AGENT] [varchar](255) NULL,
	[LOG_ID] [varchar](255) NULL,
	[LOG_INFO] [varchar](255) NULL,
	[LOG_IP] [varchar](255) NULL,
	[LOG_TIME] [datetime2](0) NULL,
	[LOG_USER] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[UUID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

CREATE TABLE [dbo].[sys_organization](
	[UUID] [varchar](32) NOT NULL,
	[ORG_ID] [int] NULL,
	[ORG_DESC] [varchar](255) NULL,
	[IS_VALID] [varchar](3) NULL,
	[ORG_NAME] [varchar](255) NULL,
	[PARENT_ID] [int] NULL,
	[REGISTER_TIME] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[UUID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


CREATE TABLE [dbo].[sys_resource](
	[UUID] [varchar](32) NOT NULL,
	[RESOURCE_ID] [int] NULL,
	[IS_VALID] [varchar](3) NULL,
	[RESOURCE_NAME] [varchar](255) NULL,
	[PARENT_ID] [int] NULL,
	[PERMISSION] [varchar](255) NULL,
	[RESOURCE_TYPE] [varchar](5) NULL,
	[RESOURCE_URL] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[UUID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]



CREATE TABLE [dbo].[sys_role](
	[UUID] [varchar](32) NOT NULL,
	[ROLE_DESC] [varchar](255) NULL,
	[ROLE_ID] [varchar](255) NULL,
	[IS_VALID] [varchar](3) NULL,
	[RESOURCE_IDS] [varchar](255) NULL,
	[ROLE_NAME] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[UUID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


CREATE TABLE [dbo].[sys_user](
	[UUID] [varchar](32) NOT NULL,
	[EMAIL] [varchar](255) NULL,
	[IS_VALID] [varchar](3) NULL,
	[REGISTER_TIME] [datetime2](0) NULL,
	[SALT] [varchar](32) NULL,
	[SYS_ORGANIZATION_ID] [varchar](255) NULL,
	[SYS_ROLE_IDS] [varchar](255) NULL,
	[USER_ID] [varchar](45) NULL,
	[USER_NAME] [varchar](45) NULL,
	[USER_NICKNAME] [varchar](45) NULL,
	[USER_PASSWORD] [varchar](45) NULL,
PRIMARY KEY CLUSTERED 
(
	[UUID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


INSERT INTO [sys_log] VALUES ('0122cffe03d94b03acc68145c7b736eb', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '0122cffe03d94b03acc68145c7b736eb', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:09:51', 'admin');
GO
INSERT INTO [sys_log] VALUES ('0ffa34ebe8134f61ac738642582a96da', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '0ffa34ebe8134f61ac738642582a96da', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:40:23', 'admin');
GO
INSERT INTO [sys_log] VALUES ('1441aa8b72a143c6a0bb7b1995d68a3e', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '1441aa8b72a143c6a0bb7b1995d68a3e', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:20:00', 'admin');
GO
INSERT INTO [sys_log] VALUES ('149edbab273c4be88fcf62e841c3d829', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '149edbab273c4be88fcf62e841c3d829', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:11:36', 'admin');
GO
INSERT INTO [sys_log] VALUES ('1789399202944e23b02f9fc25e2f2bc2', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '1789399202944e23b02f9fc25e2f2bc2', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:07:47', 'admin');
GO
INSERT INTO [sys_log] VALUES ('1a7f2ca3189540e79e60fb55c913ef71', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '1a7f2ca3189540e79e60fb55c913ef71', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:22:58', 'admin');
GO
INSERT INTO [sys_log] VALUES ('1ffe12bb679f4be3b30cbcd2b29e8697', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '1ffe12bb679f4be3b30cbcd2b29e8697', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 09:33:57', 'admin');
GO
INSERT INTO [sys_log] VALUES ('230f7e3781b7499d9566faa49b823666', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '230f7e3781b7499d9566faa49b823666', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:11:56', 'admin');
GO
INSERT INTO [sys_log] VALUES ('252978e7db1e4dfb962891ba68fa56f8', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '252978e7db1e4dfb962891ba68fa56f8', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:07:30', 'admin');
GO
INSERT INTO [sys_log] VALUES ('25530d3e162541b8abf6f592fc6b1614', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '25530d3e162541b8abf6f592fc6b1614', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:24:30', 'admin');
GO
INSERT INTO [sys_log] VALUES ('2836e47e23b242bea494a3ede51885b3', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36', '2836e47e23b242bea494a3ede51885b3', '用户:less 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-4 12:29:32', 'less');
GO
INSERT INTO [sys_log] VALUES ('335af7204c254dc8b443f9206aa77dd7', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', '335af7204c254dc8b443f9206aa77dd7', '用户:less 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-3 21:05:49', 'less');
GO
INSERT INTO [sys_log] VALUES ('35ce95c03669456e809b9587290dbbe1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36', '35ce95c03669456e809b9587290dbbe1', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-4 10:50:06', 'admin');
GO
INSERT INTO [sys_log] VALUES ('38d214695e3d40cb81655371af60224f', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '38d214695e3d40cb81655371af60224f', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:23:11', 'admin');
GO
INSERT INTO [sys_log] VALUES ('3bf87acb80dd42ed8f73bf27d2bf73ee', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '3bf87acb80dd42ed8f73bf27d2bf73ee', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:26:25', 'admin');
GO
INSERT INTO [sys_log] VALUES ('425ebc32c7194101b0380e797e46b535', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '425ebc32c7194101b0380e797e46b535', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:10:48', 'admin');
GO
INSERT INTO [sys_log] VALUES ('4417556e753f457686a08c686a89f2d4', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '4417556e753f457686a08c686a89f2d4', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:17:29', 'admin');
GO
INSERT INTO [sys_log] VALUES ('44c27756a7484160a78c303d85cc75e6', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '44c27756a7484160a78c303d85cc75e6', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 09:58:05', 'admin');
GO
INSERT INTO [sys_log] VALUES ('4512bd77b202499f9f019e1ca222269a', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '4512bd77b202499f9f019e1ca222269a', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:12:32', 'admin');
GO
INSERT INTO [sys_log] VALUES ('45640b6131b842c98175d6eb1676c1c9', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '45640b6131b842c98175d6eb1676c1c9', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:19:56', 'admin');
GO
INSERT INTO [sys_log] VALUES ('50637ce1d72348ae9e7ceaa001393fa2', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', '50637ce1d72348ae9e7ceaa001393fa2', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 16:52:28', 'admin');
GO
INSERT INTO [sys_log] VALUES ('50b74aa2a562417298c77545274ec054', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/7.2.0.12990', '50b74aa2a562417298c77545274ec054', '用户:test 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-5 09:44:20', 'test');
GO
INSERT INTO [sys_log] VALUES ('5d401bb6b98c43c1bf507431edb033d1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '5d401bb6b98c43c1bf507431edb033d1', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:00:46', 'admin');
GO
INSERT INTO [sys_log] VALUES ('60b8a8762366491587b8a05a84df50ed', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '60b8a8762366491587b8a05a84df50ed', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 09:41:50', 'admin');
GO
INSERT INTO [sys_log] VALUES ('60f8e561d11e457888271ec031d9b2cf', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', '60f8e561d11e457888271ec031d9b2cf', '用户:less 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-3 21:04:46', 'less');
GO
INSERT INTO [sys_log] VALUES ('6476dee379ee4c11a8a5d5cfb9ffec7e', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '6476dee379ee4c11a8a5d5cfb9ffec7e', '用户:test 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-5 09:32:56', 'test');
GO
INSERT INTO [sys_log] VALUES ('69635cdce92346d7bb2928dd0232504e', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '69635cdce92346d7bb2928dd0232504e', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 09:54:11', 'admin');
GO
INSERT INTO [sys_log] VALUES ('6a493de738304371b2bb1045f76f0e99', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '6a493de738304371b2bb1045f76f0e99', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:28:03', 'admin');
GO
INSERT INTO [sys_log] VALUES ('6c2303725bdc4eadab149bcdc175857b', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '6c2303725bdc4eadab149bcdc175857b', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:22:16', 'admin');
GO
INSERT INTO [sys_log] VALUES ('77b1d299fe8f4ab69fcd45864c3808ef', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '77b1d299fe8f4ab69fcd45864c3808ef', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:08:48', 'admin');
GO
INSERT INTO [sys_log] VALUES ('7841d8f0b0964bd28fdc76e910b48e85', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '7841d8f0b0964bd28fdc76e910b48e85', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 09:53:38', 'admin');
GO
INSERT INTO [sys_log] VALUES ('7b382405fbcf4167b1f313716acfb582', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '7b382405fbcf4167b1f313716acfb582', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:31:11', 'admin');
GO
INSERT INTO [sys_log] VALUES ('7e636090d4aa467d88132505cb4be806', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '7e636090d4aa467d88132505cb4be806', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:04:17', 'admin');
GO
INSERT INTO [sys_log] VALUES ('8c2cdbf49f3d47ffbe822317cfd99844', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '8c2cdbf49f3d47ffbe822317cfd99844', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 09:59:19', 'admin');
GO
INSERT INTO [sys_log] VALUES ('913a5c44fafb418293fbfb57afc188f0', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '913a5c44fafb418293fbfb57afc188f0', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:40:43', 'admin');
GO
INSERT INTO [sys_log] VALUES ('93af6d3521424cfbae98fbfc3154a07e', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '93af6d3521424cfbae98fbfc3154a07e', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:06:26', 'admin');
GO
INSERT INTO [sys_log] VALUES ('988cd2b354e7456fa313822bb23c9617', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '988cd2b354e7456fa313822bb23c9617', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:11:59', 'admin');
GO
INSERT INTO [sys_log] VALUES ('9b276f42f03f4b89ae20b25c25dbe3c1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '9b276f42f03f4b89ae20b25c25dbe3c1', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 09:40:08', 'admin');
GO
INSERT INTO [sys_log] VALUES ('9b8aa794557841d8ada2b01574c8d99b', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '9b8aa794557841d8ada2b01574c8d99b', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:19:30', 'admin');
GO
INSERT INTO [sys_log] VALUES ('9e4bc471842d45a699a3377ecac65ace', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '9e4bc471842d45a699a3377ecac65ace', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:27:19', 'admin');
GO
INSERT INTO [sys_log] VALUES ('9ff19db37f904a079c04123ecddc2973', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '9ff19db37f904a079c04123ecddc2973', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:00:38', 'admin');
GO
INSERT INTO [sys_log] VALUES ('a2759d5e64494066a20c8234ec19568d', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'a2759d5e64494066a20c8234ec19568d', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:58:50', 'admin');
GO
INSERT INTO [sys_log] VALUES ('a4567b29fbfa4115a09eeb6b44380297', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'a4567b29fbfa4115a09eeb6b44380297', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-6 09:44:34', 'admin');
GO
INSERT INTO [sys_log] VALUES ('abf5774aca514fa0baa1848588361b3d', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'abf5774aca514fa0baa1848588361b3d', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:08:49', 'admin');
GO
INSERT INTO [sys_log] VALUES ('adc6e0d601534595b054e54121b8912a', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'adc6e0d601534595b054e54121b8912a', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:51:06', 'admin');
GO
INSERT INTO [sys_log] VALUES ('b9b428cfc7b142f9b68c2b3294c65644', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'b9b428cfc7b142f9b68c2b3294c65644', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-5 09:42:13', 'admin');
GO
INSERT INTO [sys_log] VALUES ('bb60f917887245f9b30db2b2434d4ce5', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'bb60f917887245f9b30db2b2434d4ce5', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:23:26', 'admin');
GO
INSERT INTO [sys_log] VALUES ('c12d098b25d642ecb77ff37050d9bf66', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'c12d098b25d642ecb77ff37050d9bf66', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 09:52:10', 'admin');
GO
INSERT INTO [sys_log] VALUES ('ce6b49cd43ba4872b34d02ebf88b34e8', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'ce6b49cd43ba4872b34d02ebf88b34e8', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:13:25', 'admin');
GO
INSERT INTO [sys_log] VALUES ('d4a25cf09d074399ba6de93f194fb3df', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'd4a25cf09d074399ba6de93f194fb3df', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:18:13', 'admin');
GO
INSERT INTO [sys_log] VALUES ('d5b5b47f1975464788c9dedf7e9dee22', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'd5b5b47f1975464788c9dedf7e9dee22', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-5 09:31:54', 'admin');
GO
INSERT INTO [sys_log] VALUES ('d77e3bcebf91454faa2f3ce049c59c21', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'd77e3bcebf91454faa2f3ce049c59c21', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:49:29', 'admin');
GO
INSERT INTO [sys_log] VALUES ('d7d883a2fa4b45e9965106e4508758cb', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'd7d883a2fa4b45e9965106e4508758cb', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:10:15', 'admin');
GO
INSERT INTO [sys_log] VALUES ('dadb4ef458ff4354bdbaaaea0372f7d5', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'dadb4ef458ff4354bdbaaaea0372f7d5', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:11:13', 'admin');
GO
INSERT INTO [sys_log] VALUES ('e6594ca5559b4fd9975d9c9bfb91b98c', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'e6594ca5559b4fd9975d9c9bfb91b98c', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:54:07', 'admin');
GO
INSERT INTO [sys_log] VALUES ('e850d2f8a3c0480eb0747e5a5cf017fd', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'e850d2f8a3c0480eb0747e5a5cf017fd', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:30:45', 'admin');
GO
INSERT INTO [sys_log] VALUES ('ea726c9fe49141099ecf798311db7638', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'ea726c9fe49141099ecf798311db7638', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 11:13:51', 'admin');
GO
INSERT INTO [sys_log] VALUES ('f0f5153dec6a4081af2b5f8600befeed', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0', 'f0f5153dec6a4081af2b5f8600befeed', '用户:less 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-3 21:00:51', 'less');
GO
INSERT INTO [sys_log] VALUES ('f428d0ad4eaa4891a15527020ffbfc27', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'f428d0ad4eaa4891a15527020ffbfc27', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:30:12', 'admin');
GO
INSERT INTO [sys_log] VALUES ('f6a5ebc2860e4cc48d07c802a31dc03f', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'f6a5ebc2860e4cc48d07c802a31dc03f', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:32:00', 'admin');
GO
INSERT INTO [sys_log] VALUES ('f713c620c5b24fc6ba497617bda338fc', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'f713c620c5b24fc6ba497617bda338fc', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 09:35:04', 'admin');
GO
INSERT INTO [sys_log] VALUES ('f7b25ddc01574c88bf1ee6ea8c60f077', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'f7b25ddc01574c88bf1ee6ea8c60f077', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-4 19:55:15', 'admin');
GO
INSERT INTO [sys_log] VALUES ('fc20c7f2421e460da2f1b4c0c4c2e52a', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', 'fc20c7f2421e460da2f1b4c0c4c2e52a', '用户:admin 成功登录系统!', '127.0.0.1 国家：未分配或者内网IP,地区：0,省：0,市：0,运营商：0,', '2017-6-8 10:53:33', 'admin');
GO








INSERT INTO [sys_organization] VALUES ('07a0b722c0894c37a35cab7b983044c4', 1, '最高级', '1', '最高级', 0, '2016-12-12');
GO
INSERT INTO [sys_organization] VALUES ('4440bdd26e4e4ce19b1b1d63654b02fe', 5, '财务部', '1', '财务部', 3, '2017-6-2');
GO
INSERT INTO [sys_organization] VALUES ('4ca1e95bf3d84ce29df72663407d8684', 2, '集团总部', '1', '集团总部', 1, '2016-10-9');
GO
INSERT INTO [sys_organization] VALUES ('4ca1e95bf3d84ce29df72663407d8e59', 3, '软件公司', '1', '软件公司', 2, '2016-12-12');
GO
INSERT INTO [sys_organization] VALUES ('7bcc83da60bd40d5a1255b91dac2473b', 6, '财务公司', '1', '财务公司', 2, '2017-6-2');
GO
INSERT INTO [sys_organization] VALUES ('8b0c4ea7a69345a798beb4f97744a17d', 4, '技术部', '1', '技术部', 3, '2016-12-12');
GO





INSERT INTO [sys_resource] VALUES ('0', 0, '1', '资源树根', -1, '*', '1', '');
GO
INSERT INTO [sys_resource] VALUES ('1', 1, '1', '通信加密', 0, 'transfer:encrypt:*', '1', '/encrypt/');
GO
INSERT INTO [sys_resource] VALUES ('10', 10, '1', '删除', 8, 'sys:code:delete', '2', '/sys/syscode/delete');
GO
INSERT INTO [sys_resource] VALUES ('11', 11, '1', '数据字典页面访问', 8, 'sys:code:page', '2', '/sys/syscode/syscode');
GO
INSERT INTO [sys_resource] VALUES ('12', 12, '1', '新增', 8, 'sys:code:create', '2', '/sys/syscode/createsyscode');
GO
INSERT INTO [sys_resource] VALUES ('13', 13, '1', '修改', 8, 'sys:code:update', '2', '/sys/syscode/updatesyscode');
GO
INSERT INTO [sys_resource] VALUES ('14', 14, '1', '系统日志管理', 0, 'sys:log:*', '1', '/sys/syslog/');
GO
INSERT INTO [sys_resource] VALUES ('15', 15, '1', '查询', 14, 'sys:log:query', '2', '/sys/syslog/list');
GO
INSERT INTO [sys_resource] VALUES ('16', 16, '1', '页面访问', 14, 'sys:log:page', '2', '/sys/syslog/syslog');
GO
INSERT INTO [sys_resource] VALUES ('17', 17, '1', '组织机构管理', 0, 'sys:organization:*', '1', '/sys/sysorganization/');
GO
INSERT INTO [sys_resource] VALUES ('18', 18, '1', '查询', 17, 'sys:organization:query', '2', '/sys/sysorganization/tree');
GO
INSERT INTO [sys_resource] VALUES ('19', 19, '1', '删除', 17, 'sys:organization:delete', '2', '/sys/sysorganization/delete');
GO
INSERT INTO [sys_resource] VALUES ('2', 2, '1', '通信加密密钥交换', 1, 'transfer:encrypt:start', '2', '/encrypt/getserverpublickey');
GO
INSERT INTO [sys_resource] VALUES ('20', 20, '1', '新增', 17, 'sys:organization:create', '2', '/sys/sysorganization/add');
GO
INSERT INTO [sys_resource] VALUES ('21', 21, '1', '更新', 17, 'sys:organization:update', '2', '/sys/sysorganization/update');
GO
INSERT INTO [sys_resource] VALUES ('22', 22, '1', '页面访问', 17, 'sys:organization:page', '2', '/sys/sysorganization/sysorganization');
GO
INSERT INTO [sys_resource] VALUES ('23', 23, '1', '资源权限管理', 0, 'sys:resource:*', '1', '/sys/sysresource/');
GO
INSERT INTO [sys_resource] VALUES ('24', 24, '1', '查询', 23, 'sys:resource:query', '2', '/sys/sysresource/tree');
GO
INSERT INTO [sys_resource] VALUES ('25', 25, '1', '删除', 23, 'sys:resource:delete', '2', '/sys/sysresource/delete');
GO
INSERT INTO [sys_resource] VALUES ('26', 26, '1', '新增', 23, 'sys:resource:create', '2', '/sys/sysresource/add');
GO
INSERT INTO [sys_resource] VALUES ('27', 27, '1', '更新', 23, 'sys:resource:update', '2', '/sys/sysresource/update');
GO
INSERT INTO [sys_resource] VALUES ('28', 28, '1', '页面访问', 23, 'sys:resource:page', '2', '/sys/sysresource/sysresource');
GO
INSERT INTO [sys_resource] VALUES ('29', 29, '1', '角色管理', 0, 'sys:role:*', '1', '/sys/sysrole/');
GO
INSERT INTO [sys_resource] VALUES ('3', 3, '1', '通信加密解密示例', 1, 'transfer:encrypt:demo', '2', '/encrypt/encrypt');
GO
INSERT INTO [sys_resource] VALUES ('30', 30, '1', '查询', 29, 'sys:role:query', '2', '/sys/sysrole/list');
GO
INSERT INTO [sys_resource] VALUES ('31', 31, '1', '删除', 29, 'sys:role:delete', '2', '/sys/sysrole/delete');
GO
INSERT INTO [sys_resource] VALUES ('32', 32, '1', '新增', 29, 'sys:role:create', '2', '/sys/sysrole/createsysrole');
GO
INSERT INTO [sys_resource] VALUES ('33', 33, '1', '更新', 29, 'sys:role:update', '2', '/sys/sysrole/updatesysrole');
GO
INSERT INTO [sys_resource] VALUES ('34', 34, '1', '页面访问', 29, 'sys:role:page', '2', '/sys/sysrole/sysrole');
GO
INSERT INTO [sys_resource] VALUES ('35', 35, '1', '用户管理', 0, 'sys:user:*', '1', '/sys/sysuser/');
GO
INSERT INTO [sys_resource] VALUES ('36', 36, '1', '查询', 35, 'sys:user:query', '2', '/sys/sysuser/list');
GO
INSERT INTO [sys_resource] VALUES ('37', 37, '1', '删除', 35, 'sys:user:delete', '2', '/sys/sysuser/delete');
GO
INSERT INTO [sys_resource] VALUES ('38', 38, '1', '页面访问', 35, 'sys:user:page', '2', '/sys/sysuser/sysuser');
GO
INSERT INTO [sys_resource] VALUES ('39', 39, '1', '新增', 35, 'sys:user:create', '2', '/sys/sysuser/createsysuser');
GO
INSERT INTO [sys_resource] VALUES ('4', 4, '1', 'Session管理', 0, 'sys:session:*', '1', '/sys/httpsession/');
GO
INSERT INTO [sys_resource] VALUES ('40', 40, '1', '更新', 35, 'sys:user:update', '2', '/sys/sysuser/updatesysuser');
GO
INSERT INTO [sys_resource] VALUES ('5', 5, '1', '查询', 4, 'sys:session:query', '2', '/sys/httpsession/list');
GO
INSERT INTO [sys_resource] VALUES ('6', 6, '1', '删除', 4, 'sys:session:delete', '2', '/sys/httpsession/delete');
GO
INSERT INTO [sys_resource] VALUES ('7', 7, '1', 'session管理页面访问', 4, 'sys:session:page', '2', '/sys/httpsession/httpsession');
GO
INSERT INTO [sys_resource] VALUES ('8', 8, '1', '数据字典管理', 0, 'sys:code:*', '1', '/sys/syscode/');
GO
INSERT INTO [sys_resource] VALUES ('9', 9, '1', '查询', 8, 'sys:code:query', '2', '/sys/syscode/list');
GO






INSERT INTO [sys_role] VALUES ('2', '管理员拥有全部权限', '1', '1', '0,1,2,3,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,4,5,6,7,8,10,11,12,13,9', '管理员');
GO
INSERT INTO [sys_role] VALUES ('a9edc635b8384903a7d923c88ca52f93', '最小权限', '2', '1', '2,3', '最小权限');
GO



INSERT INTO [sys_user] VALUES ('1', '', '1', '2016-3-28 11:54:58', '21db5c79691c39641c5683cc4d098399', '1', '1', '1', 'admin', '管理员用户', '8666b02032933eacfde467bda24ad66b');
GO
INSERT INTO [sys_user] VALUES ('2', '', '1', '2016-4-1 09:59:58', '27bd32d58701c78dd8b909c07db34206', '1', '2', '2', 'less', '最小权限用户', '33a4fad6cefa3c9f6310437ce2392b25');
GO
INSERT INTO [sys_user] VALUES ('60a187647f474d03b11de578156c0961', NULL, '1', '2017-6-4 11:00:53', '585bd2a210a40e3e9c2a8f2b5af8af26', NULL, '2', '4', 'less3', 'less3', '0ff8b61179be86458e717c2394584262');
GO
INSERT INTO [sys_user] VALUES ('fe317c7ed36740b0a6c6a210bf18a561', NULL, '1', '2017-6-5 09:32:28', '356e1d74d190d1acdc6bd4a779f1d7e9', NULL, '1', '3', 'test', 'test', '6f36aedf14c407926f4fc0ada8992d1d');
GO

