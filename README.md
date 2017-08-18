#morpho




-  **morpho是一款JAVAEE应用开发框架及脚手架** 

#项目重大更新进程
- 自2015年10月份以来以成功运用于多个商业项目
- 经过内部使用 精简出公用的脚手架部分 并升级最新的依赖
- 20170804更新 增加了代码生成器 详见https://git.oschina.net/max256/morpho-code-help
- 20170818更新 新增功能excel报表推荐使用easypoi以整合 新增整合ureport2报表引擎推荐使用 更新最新依赖 修复已知bug

#项目名称来历


- 来自内部项目butterfly由于这个项目有诸多问题，经过多次维护后升级到下一代morpho项目 
- Morpho （Linnaeus, 1758），闪蝶属，鳞翅目蛱蝶科闪蝶亚科的一属。只分布于新热带界。中国学者又会把此属分类为独立一科——闪蝶科（Morphidae）或称灿蝶科。此属以雄蝶翅面的闪蓝色著名，此属的所有物种均受到蝴蝶收藏家珍视。
![Morpho](https://git.oschina.net/uploads/images/2017/0604/182759_5137b6fa_61523.jpeg "Morpho")


#设计思想


1. 简单 
2. 开箱即用
3. 不是炫技之作,选用了最最最常见的技术架构，减少学习风险，不使用小众的模板语言，小众的ORM，小众的前端框架和小众的代码风格 一切为了快速上手 如果您针对某些模块有必要需求 您只需 换掉该部分即可 
4. 现代，仅对现代浏览器提供支持，java版本最低支持1.8 没有技术债务
5. 注释第一 完备的注释 如果真没写 那可能是实在不需要注释的段落了~  
6. 轻量 没有做maven分模块项目 如果您想分 您分分钟就能搞出来 作为脚手架项目再分太多模块的话可能会对您自己的项目本身造成模块划分的不良影响 这点您自己决定
7. 安全，内置大量加解密，认证授权，请求过滤等组件，您按需取用即可，当今网络，可不那么安全

#有图有真相
![整合监控页面](https://git.oschina.net/uploads/images/2017/0604/173621_649db9b4_61523.jpeg "整合监控页面")
![session管理页面](https://git.oschina.net/uploads/images/2017/0604/173659_85615691_61523.jpeg "session管理页面")
![权限管理页面](https://git.oschina.net/uploads/images/2017/0604/173722_78b6c63a_61523.jpeg "权限管理页面")
![用户管理页面](https://git.oschina.net/uploads/images/2017/0604/173739_9adcbcca_61523.jpeg "用户管理页面")
![登录页](https://git.oschina.net/uploads/images/2017/0604/173813_d1738ed6_61523.jpeg "登录页")

#系统要求如下：
1. jdk1.8及以上（1.7的话有一点需要自行适配的 有部分用了1.8的api 本想兼容1.7的最后还是没把持住~~~）
2. tomcat8.0及以上 8.5.x系列未经测试不保证
3. mysql5.5及以上(如果您要使用mysql的话)
4. oracle11g及以上(11g 12c测试通过 10g理论上应该也没有问题 未经测试)
5. linux windows x86  32bit 64bit都支持
6. 容器要求servlet3.1规范及以上实现


7.新增支持 sqlserver2008及以上版本 win7及以上（中文版本）系统 

#morpho的技术选型如下:

##后端技术



1. 核心框架: Spring Framework4.3.8
2. 数据库访问: Mybatis3.4.4 +hibernate5.2.10（可选支持 框架已经集成 但脚手架部分并不依赖 如果您特别不喜欢hibernate可以直接移除）
3. 视图框架: Spring MVC
4. 页面视图: jsp
5. 日志组件: log4j2
6. 报表：jasperreport（支持中文）,poi,excel封装
7. 缓存：ehcahce redis(非强制依赖 如果您不需要可以移除)
8. 定时任务：quartz spring内置的调度器
9. 权限：shiro 1.3.2
10. 安全：esapi
11. 其他组件: apache commons系列 joda系列 spring系列 druid fastjson jackson springfox guava tk.mybatis.mapper3.4
12. 数据库：支持mysql oracle 默认mysql oracle需要做一点点配置即可 详见文档


##前端技术



1. JS框架： jquery
2. CSS框架: Bootstrap3.X
3. 富文本编辑器: UEditor
4. 树形组件: zTree
5. 日期控件: My97DatePicker laydate
6. 表格组件： bootstrap-table 同时也支持jqgrid和easyui的datagrid 后两者版权问题 不默认使用 根据您的情况自行选择
7. MVVC：vue.js


#WIKI
http://git.oschina.net/max256/morpho/wikis

#技术交流
QQ群：210722852 请备注来自 开源中国morpho项目

#项目作者:
fbf: help~~~max256.com   ~~~替换成@

#项目捐助者:
to do list

#版权


- apache2.0许可证
- 保留署名权
- 您可以根据自己的需要修改源码 
- 如果需要商业帮助请联系help~~~max256.com包括但不限于定制，咨询，培训，项目合作  ~~~替换成@



#本项目的特点：



- 1.最新的框架依赖,帮助您学习最新的技术
- 2.框架本身精简 不偏向依赖某些技术 您可以自由选择
- 3.jsp el表达式 jquery这些传统技术，掌握的人多降低学习成本，同时引入了vue.js bootstrap-table等比较流行的插件也保持技术先进性，怎么使用就要看您的意图了
- 4.hibernate和mybatis同时支持 这应该可以囊括您之前的技术栈了 ORM随心换
- 5.集成shiro权限控制支持AOP方法级权限控制  并对shiro相关bug作出处理
- 6.注释完备！~
- 7.内置一个加解密通信的模块 如果有需要可以直接使用
- 8.完成企业应用的常见功能，在此基础上加速您的开发进度

#已完成的功能


- 1.用户管理
- 2.角色管理
- 3.资源权限管理
- 4.组织机构管理
- 5.系统运行监控
- 6.数据源监控
- 7.日志管理
- 8.在线SESSION管理（可以踢人）
- 9.api管理与测试工具
- 10.基于easyui的代码生成器(上一代框架使用了easyui，写了一个生成器，不再维护，如果您使用了easyui可以用用，扩展也很简单)
- 11.工具-用于生成资源权限表的数据根据注解扫描 减轻开发完配置资源权限的工作量

#开始使用


1. 下到本地后运行init sql 默认使用的是mysql5.6及以上版本innodb引擎
   如果您需要使用oralce请导入oracle版本基于11g制作内容和mysql版本一样 并且修改配置文件中的url schema dialect 
2. 配置config.properties
3. 放入tomcat运行


- 数据库中内置登录账号
- 账号admin 密码admin 管理员用户
- 账号less  密码admin 最小权限用户


#关于使用sqlserver配置的注意事项 


- 请手动建库morpho(或者根据您的情况自行决定)
- 运行初始化脚本 前半部分为建表 后半部分为插入数据 运行这个sql脚本
- 在配置文件中配置
- druid.jdbc.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
- druid.jdbc.url=jdbc:sqlserver://localhost:1433;DatabaseName=morpho
- 以下两项根据实际情况配置
- hibernate.default_catalog=
- hibernate.default_schema=
- 根据实际情况配置
- hibernate.dialect=org.hibernate.dialect.SQLServer2008Dialect
- 或者
- hibernate.dialect=org.hibernate.dialect.SQLServer2012Dialect

配置/morpho/src/main/resources/spring/applicationContext-mybatis.xml
中的分页插件dialect为sqlserver或者sqlserver2012  前者适用2005、2008版本后者适用2012版本
ps:建表语句默认使用varchar表示字符串类型没有使用nvarchar所以在非中文版本windows中乱码 请您明知这一点 如果需要nvarchar请自行修改表结构
平且配置hibernate注解类型明确nvarchar 或者继承sqlserver驱动注册string到nvarchar的默认映射 请您考虑并解决


#开发步骤
参见wiki http://git.oschina.net/max256/morpho/wikis/%E9%A1%B9%E7%9B%AE%E6%96%87%E6%A1%A3


#参与进来


- 请先start在此谢过
- 问题请issue中提出 
- 特别紧急的问题请在群里@我或者issue提出后给我发封邮件 请注明问题内容 以及联系方式
- 欢迎fork

#参考过的项目与特别鸣谢


- 通用Mapper像hibernate丝绸般润滑的mybatis使用姿势 https://github.com/abel533/Mapper
- llsfw项目     http://git.oschina.net/wangkang/llsfw
- jeesite虽然不怎么更新了但具有价值 https://github.com/thinkgem/jeesite
- LarvaFrame权限管理系统  http://git.oschina.net/sxjun1904/LarvaFrame
- spring-shiro-training https://git.oschina.net/wangzhixuan/spring-shiro-training
- spring一站式范例 http://git.oschina.net/chunanyong/springrain
- jeecg参考了excel报表部分  http://www.jeecg.org/
- springmore   https://github.com/tangyanbo/springmore
- iBase4J    http://git.oschina.net/iBase4J/iBase4J
- SpringWind  http://git.oschina.net/juapk/SpringWind
- swagger-ui（即spring fox）  https://github.com/swagger-api/swagger-ui
- SpringBlade  https://git.oschina.net/smallc/SpringBlade
- s2jh  https://github.com/xautlx/s2jh
- 国产ui良心之作layui  http://www.layui.com/
- renren-security参考了vue.js部分（为数不多的vue.js企业级java应用示例，推荐）  http://git.oschina.net/babaio/renren-security
- webside    https://git.oschina.net/wjggwm/webside
- ip2region离线ip地址库  https://github.com/lionsoul2014/ip2region
- shiro权限生成器  https://github.com/quqiangsheng/shiro-assistant
- 张开涛的es脚手架https://github.com/zhangkaitao/es
- 锐道中间件出品的ureport2 https://github.com/youseries/ureport
- easypoi  https://gitee.com/jueyue/easypoi


...........
TO DO LIST
文档逐步完善中