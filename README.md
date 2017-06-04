#morpho
morpho是一款JAVAEE应用开发框架及脚手架
自2015年10月份以来以成功运用于多个商业项目
经过内部使用 精简出公用的脚手架部分 并升级最新的依赖
使用apache2.0许可证开源

系统要求如下：
jdk1.7及以上
tomcat8.0及以上
mysql5.5及以上
oracle11g及以上
linux windows x86  32bit 64bit都支持
容器要求servlet3.1规范及以上实现


morpho的技术选型如下:

后端技术

核心框架: Spring Framework4.3.8
数据库访问: Mybatis3.4.4 +hibernate5.2.10（可选支持 框架已经集成 但脚手架部分并不依赖 如果您特别不喜欢hibernate可以直接移除）
视图框架: Spring MVC
页面视图: jsp
日志组件: log4j2
报表：jasperreport（支持中文）,poi,excel封装
缓存：ehcahce redis(非强制依赖 如果您不需要可以移除)
定时任务：quartz spring内置的调度器
权限：shiro 1.3.2
安全：esapi
其他组件: apache commons系列 joda系列 spring系列 druid fastjson jackson springfox guava tk.mybatis.mapper3.4
数据库：支持mysql oracle 默认mysql oracle需要做一点点配置即可 详见文档


前端技术

JS框架： jquery
CSS框架: Bootstrap3.X
富文本编辑器: UEditor
树形组件: zTree
日期控件: My97DatePicker laydate
表格组件： bootstrap-table 同时也支持jqgrid和easyui的datagrid 后两者版权问题 不默认使用 根据您的情况自行选择
MVVC：vue.js


WIKI
http://git.oschina.net/max256/morpho/wikis

技术交流
QQ群：210722852 请备注来自 开源中国morpho项目

项目作者:
fbf: help@max256.com

项目捐助者:
to do list

版权
apache2.0许可证
保留署名权
您可以根据自己的需要修改源码 
如果需要商业帮助请联系help@max256.com包括但不限于定制，咨询，培训，项目合作



本项目的特点：

1最新的框架依赖,帮助您学习最新的技术
2框架本身精简 不偏向依赖某些技术 您可以自由选择
3jsp el表达式 jquery这些传统技术，掌握的人多降低学习成本，同时引入了vue.js bootstrap-table等比较流行的插件也保持技术先进性，怎么使用          
 就要看您的意图了
4hibernate和mybatis同时支持 这应该可以囊括您之前的技术栈了 ORM随心换
5集成shiro权限控制支持AOP方法级权限控制  并对shiro相关bug作出处理
6注释完备！~
7内置一个加解密通信的模块 如果有需要可以直接使用
8完成企业应用的常见功能，在此基础上加速您的开发进度

已完成的功能
1用户管理
2角色管理
3资源权限管理
4组织机构管理
5系统运行监控
6数据源监控
7日志管理
8在线SESSION管理（可以踢人）
9api管理与测试工具
10基于easyui的代码生成器(上一代框架使用了easyui，写了一个生成器，不再维护，如果您使用了easyui可以用用，扩展也很简单)
11工具-用于生成资源权限表的数据根据注解扫描 减轻开发完配置资源权限的工作量

开始使用
下到本地后运行init sql
配置config.properties
放入tomcat运行
内置账号
账号admin 密码admin
账号less  密码admin

...........
TO DO LIST
