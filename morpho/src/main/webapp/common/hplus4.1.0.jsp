<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page isELIgnored="false" %>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%--本文件是登录页和后台框架主页的引用内容 如果您需要更换自己风格的后台主页 修改这里就可以--%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String User_Agent = request.getHeader("User-Agent");
	out.println("<!--用户的浏览器信息： " + User_Agent + " -->");
%>
<%--basePath --%>
<base href="<%=basePath%>" />
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!-- h+ js文件 -->
<script src="<%=path%>/jslib/H+v4.1.0/js/jquery.min.js?v=2.1.4"></script>
<script src="<%=path%>/jslib/H+v4.1.0/js/bootstrap.min.js?v=3.3.6"></script>
<script src="<%=path%>/jslib/H+v4.1.0/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="<%=path%>/jslib/H+v4.1.0/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="<%=path%>/jslib/H+v4.1.0/js/plugins/layer/layer.min.js"></script>
<script src="<%=path%>/jslib/H+v4.1.0/js/hplus.min.js?v=4.1.0"></script>
<script src="<%=path%>/jslib/H+v4.1.0/js/contabs.min.js"></script>
<script src="<%=path%>/jslib/H+v4.1.0/js/plugins/pace/pace.min.js"></script>

<!-- h+主题模板 -->
<link href="<%=path%>/jslib/H+v4.1.0/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="<%=path%>/jslib/H+v4.1.0/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="<%=path%>/jslib/H+v4.1.0/css/animate.min.css" rel="stylesheet">
<link href="<%=path%>/jslib/H+v4.1.0/css/style.min.css?v=4.1.0" rel="stylesheet">

<script type="text/javascript" charset="utf-8">
	//全局的AJAX访问，处理AJAX请求时SESSION超时
	$.ajaxSetup({
		cache: false,
	    contentType:"application/x-www-form-urlencoded;charset=utf-8",
	    complete:function(XMLHttpRequest,textStatus){
	          if(XMLHttpRequest.status=="408"){
	               //跳转错误页 提示超时
	               window.location.replace('${ctx}/errorpage?shiroLoginFailure=0007');
	       		}	
	    }
	});
</script>
