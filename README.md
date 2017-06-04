#morpho


morpho是一款JAVAEE应用开发框架及脚手架
自2015年10月份以来以成功运用于多个商业项目
经过内部使用 精简出公用的脚手架部分 并升级最新的依赖

#有图有真相
![整合监控页面](https://git.oschina.net/uploads/images/2017/0604/173621_649db9b4_61523.jpeg "整合监控页面")
![session管理页面](https://git.oschina.net/uploads/images/2017/0604/173659_85615691_61523.jpeg "session管理页面")
![权限管理页面](https://git.oschina.net/uploads/images/2017/0604/173722_78b6c63a_61523.jpeg "权限管理页面")
![用户管理页面](https://git.oschina.net/uploads/images/2017/0604/173739_9adcbcca_61523.jpeg "用户管理页面")
![登录页](https://git.oschina.net/uploads/images/2017/0604/173813_d1738ed6_61523.jpeg "登录页")

#系统要求如下：
1. jdk1.7及以上
2. tomcat8.0及以上 8.5.x系列未经测试不保证
3. mysql5.5及以上
4. oracle11g及以上(如果您要使用mysql的话)
5. linux windows x86  32bit 64bit都支持
6. 容器要求servlet3.1规范及以上实现


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
fbf: help@max256.com

#项目捐助者:
to do list

#版权
apache2.0许可证
保留署名权
您可以根据自己的需要修改源码 
如果需要商业帮助请联系help@max256.com包括但不限于定制，咨询，培训，项目合作



#本项目的特点：



- 1.最新的框架依赖,帮助您学习最新的技术
- 2.框架本身精简 不偏向依赖某些技术 您可以自由选择
- 3.jsp el表达式 jquery这些传统技术，掌握的人多降低学习成本，同时引入了vue.js bootstrap-table等比较流行的插件也保持技术先进性，怎么使      
    用就要看您的意图了
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


1. 下到本地后运行init sql
2. 配置config.properties
3. 放入tomcat运行
数据库中内置登录账号
账号admin 密码admin 管理员用户
账号less  密码admin 最小权限用户

#参与进来


- 请先start在此谢过
- 问题请issue中提出 
- 特别紧急的问题请在群里@我活着issue提出后给我发封邮件 请注明问题内容 以及联系方式
- 欢迎fork


...........
TO DO LIST
文档逐步完善中