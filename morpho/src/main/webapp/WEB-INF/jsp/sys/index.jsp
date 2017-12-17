<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/common/hplus4.1.0.jsp"></jsp:include>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <link rel="shortcut icon" href="${ctx}/favicon.ico">
    <title>${sysname }</title>
	<script type="text/javascript">

	function changePassword(){
		layer.open({
	        type: 2,
	        title: '修改密码',
	        area: ['600px', '550px'], //宽高
	        fix: true, //不固定
	        maxmin: true,
	        content: "${pageContext.request.contextPath}/sys/sysuser/changepassword"
	    });
	}
	

	</script>
</head>


<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
            	<!-- 无序列表 导航菜单 -->
                <ul class="nav" id="side-menu">
                	<!-- 第一项 用户头像 -->
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img alt="image" class="img-circle" src="${ctx}/jslib/H+v4.1.0/img/profile_small.jpg" /></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">                       
                                <!-- 用户名  --><span class="block m-t-xs">当前用户:<strong class="font-bold"><shiro:principal property="userName"/></strong></span>
                                <!-- 角色 --><span class="block m-t-xs">用户角色:<strong class="font-bold">${currentRolesName}</strong></span>
                                <!-- 组织机构 --><%-- <span class="block m-t-xs">组织机构:<strong class="font-bold">${currentOrganizationName}</strong><b class="caret"></b></span> --%>
                                </span>
                            </a>
                            <!-- 点击头像 触发的用户相关信息 -->
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                               
                               
                                <!-- 安全退出 -->
                                <li><a href="javascript:changePassword()">修改密码</a>
                                </li>
                                <li><a href="${ctx}/logout">安全退出</a>
                                </li>
                            </ul>
                        </div>
                        <!-- 左边菜单收起时的显示文字 -->
                        <div class="logo-element">morpho
                        </div>
                    </li>
                    <!-- 菜单 -->
                    <li>
                    	<!--业务操作 -->
                        <a href="#"><i class="fa fa-edit"></i> <span class="nav-label">业务操作</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            
                        </ul>
                    </li>
                    
                    <li>
                    	<!--报表管理 -->
                        <a href="#"><i class="fa fa-edit"></i> <span class="nav-label">报表管理</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        	<li><a class="J_menuItem" href="${ctx}/report/list">查看报表</a>
                            </li>
                            <li><a class="J_menuItem" href="${ctx}/ureport/designer">报表设计器</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                    	<!--规则管理 -->
                        <a href="#"><i class="fa fa-edit"></i> <span class="nav-label">规则管理</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li><a class="J_menuItem" href="${ctx}/urule/frame">规则引擎</a>
                            </li>
                        </ul>
                    </li>
                    
                    <li>
                    	<!--业务流程管理 -->
                        <a href="#"><i class="fa fa-edit"></i> <span class="nav-label">业务流程管理</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        
                        	<li><a class="J_menuItem" href="${ctx}/uflo/todo">待办任务</a>
                            </li>
                        	<li><a class="J_menuItem" href="${ctx}/uflo/central">流程监控</a>
                            </li>
                            <li><a class="J_menuItem" href="${ctx}/uflo/designer">流程设计器</a>
                            </li>
                            <li><a class="J_menuItem" href="${ctx}/uflo/calendar">日历管理</a>
                            </li>
                            
                            
                        </ul>
                    </li>
                    <li>
                    	<!--定时任务 -->
                        <a href="#"><i class="fa fa-table"></i> <span class="nav-label">定时任务</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                          <shiro:hasPermission name="sys:schedulejob:page">
                         	<li><a class="J_menuItem" href="${ctx}/sys/schedulejob/schedulejobpage">定时任务管理</a>
                            </li>
                          </shiro:hasPermission>
                          <shiro:hasPermission name="sys:schedulejoblog:page">
                            <li><a class="J_menuItem" href="${ctx}/sys/schedulejoblog/schedulejoblogpage">定时任务日志</a>
                            </li>
                          </shiro:hasPermission>

                            
                        </ul>
                    </li>
                    
                  	 <li>
                    	<!--系统设置 -->
                        <a href="#"><i class="fa fa-table"></i> <span class="nav-label">系统设置</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <shiro:hasPermission name="sys:user:page">
                         	<li><a class="J_menuItem" href="${ctx}/sys/sysuser/sysuser">用户管理</a>
                            </li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:code:page">
                            <li><a class="J_menuItem" href="${ctx}/sys/syscode/syscode">数据字典</a>
                            </li>
                            </shiro:hasPermission>

                            <shiro:hasPermission name="sys:log:page">  
                            <li><a class="J_menuItem" href="${ctx}/sys/syslog/syslog">系统日志</a>
                            </li>
                            </shiro:hasPermission>
                            
                        </ul>
                    </li>
             
                    <li>
                    	<!-- 权限管理 -->
                        <a href="#"><i class="fa fa-flask"></i> <span class="nav-label">权限管理</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                         	<shiro:hasPermission name="sys:role:page">  
                            <li><a class="J_menuItem" href="${ctx}/sys/sysrole/sysrole">角色管理</a>
                            </li>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="sys:resource:page">  
                            <li><a class="J_menuItem" href="${ctx}/sys/sysresource/sysresource">资源管理</a>
                            </li>
							</shiro:hasPermission>   
							<shiro:hasPermission name="sys.organization:page">
                           	<li><a class="J_menuItem" href="${ctx}/sys/sysorganization/sysorganization">组织机构</a>
                            </li>
                            </shiro:hasPermission>                                                                                     
                        </ul>
                    </li>
                  
                   
                    <li>
                        <a href="#"><i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">运行监控</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                        	<shiro:hasPermission name="sys:session:page"> 
                            <li><a class="J_menuItem" href="${ctx}/sys/httpsession/httpsession">HttpSession管理</a>
                            </li>   
                            </shiro:hasPermission>
                            <shiro:hasRole name="管理员">
	                           <li><a class="J_menuItem" href="${ctx}/monitoring">系统运行监控</a>
	                           </li>   
	                           <li><a class="J_menuItem" href="${ctx}/druid">数据库连接池监控</a>
	                           </li>           
	                           <li><a class="J_menuItem" href="${ctx}/swagger-ui.html">HTTP接口(开发人员选项)</a>
	                           </li> 
	             
                            </shiro:hasRole>            
                        </ul>
                    </li>
                      
                    <li>
                        <a href="#"><i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">代码生成</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <shiro:hasRole name="管理员">
	                
	                            <li><a class="J_menuItem" href="${ctx}/jslib/formBuilder-2.9.6/demo/index.html">html可视化表单制作</a>
	                           </li> 
	                            <li><a class="J_menuItem" href="${ctx}/jslib/layoutit-gh-pages/index.html">bootstrap3可视化布局</a>
	                           </li>     
                            </shiro:hasRole>            
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                	<!-- 顶部的搜索框  -->
                    <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " ><i class="fa fa-bars"></i> </a>
                    	<!-- 全局搜索框 -->
                    
                       <%--  <form role="search" class="navbar-form-custom" method="post" action="${ctx }/">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form> --%>
                        <!-- 大标题 -->
                        <h2 class="nav navbar-right">
                        	${sysname }
                        </h2>
                    </div>
                    <ul class="nav navbar-top-links navbar-right">                        
                        <li class="dropdown hidden-xs">
                            <a class="right-sidebar-toggle" aria-expanded="false">
                                <i class="fa fa-tasks"></i> 主题
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
            <div class="row content-tabs">
                <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
                </button>
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab" data-id="${ctx }/sys/firstscreen">首页</a>
                    </div>
                </nav>
                <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
                </button>
                <div class="btn-group roll-nav roll-right">
                    <button class="dropdown J_tabClose" data-toggle="dropdown">选项卡<span class="caret"></span>

                    </button>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                     	
                        <!-- 添加的功能刷新当前选项卡 -->
                        <li class="J_tabRefresh"><a>刷新当前选项卡</a>
                        </li>
                        <!-- 分隔线 -->
                        <li class="divider"></li>       
                        
                        </li>                
                  
                         <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                        <li class="J_tabCloseAll"><a>关闭所有选项卡</a>
                        </li>
                       
                    </ul>
                </div>
                <a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx }/sys/firstscreen"" frameborder="0" data-id="${ctx }/sys/firstscreen" seamless></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">${sysname}${copyright}
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
        <!--右侧边栏开始-->
        <div id="right-sidebar">
            <div class="sidebar-container">
                <ul class="nav nav-tabs navs-3">

                    <li class="active">
                        <a data-toggle="tab" href="#tab-1">
                            <i class="fa fa-gear"></i> 主题
                        </a>
                    </li>
                   
                </ul>
                <div class="tab-content">
                    <div id="tab-1" class="tab-pane active">
                        <div class="sidebar-title">
                            <h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
                            <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                        </div>
                        <div class="skin-setttings">
                            <div class="title">主题设置</div>
                            <div class="setings-item">
                                <span>收起左侧菜单</span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                        <label class="onoffswitch-label" for="collapsemenu">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>固定顶部</span>

                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                                        <label class="onoffswitch-label" for="fixednavbar">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="setings-item">
                                <span>
                 		       固定宽度
                    </span>
                                <div class="switch">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                                        <label class="onoffswitch-label" for="boxedlayout">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="title">皮肤选择</div>
                            <div class="setings-item default-skin nb">
                                <span class="skin-name ">
                         <a href="#" class="s-skin-0">
                             默认皮肤
                         </a>
                    </span>
                            </div>
                            <div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-1">
                            蓝色主题
                        </a>
                    </span>
                            </div>
                            <div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-3">
                            黄色/紫色主题
                        </a>
                    </span>
                            </div>
                        </div>
                    </div>      
        </div>
        <!--右侧边栏结束--> 
    </div>
</body>

</html>

