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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<title>登录${sysname }</title>
<jsp:include page="/common/hplus4.1.0.jsp"></jsp:include>
<link href="${ctx}/jslib/H+v4.1.0/css/login.min.css" rel="stylesheet" />

<script>
	if (window.top !== window.self) {
		window.top.location = window.location
	};

	//动态刷新验证码
	function reloadCaptcha() {
		$("#img_captcha").attr("src",
				"${ctx}/captchacode?random=" + new Date().getTime())
	}
</script>
</head>

<body class="signin">
	<div class="signinpanel">
		<div class="row">
			<div class="col-sm-7">
				<div class="signin-info">
					<div class="logopanel m-b">
						<h1>${sysname }</h1>
					</div>
					<div class="m-b"></div>

					<ul class="m-b">
						<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 快速</li>
						<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 准确</li>
						<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 美观</li>
						<li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 强大</li>
					</ul>
					<strong>还没有体验？ <a href="#" class="text-warning">这里开始&raquo;</a></strong>
				</div>
			</div>
			<div class="col-sm-5">
				<!-- 用户登录表单 -->
				<form method="post" action="${ctx }/login">
					<h4 class="no-margins">用户登录：</h4>
					<p class="m-t-md">登录到${sysname }后台</p>
					<input type="text" name="usrn" class="form-control uname"
						placeholder="用户名" /> 
					<input type="password" name="usrp"
						class="form-control pword m-b" placeholder="密码" /> 
					<input
						type="text" name="validatecode" class="form-control uname"
						placeholder="请输入验证码" />
					 <img
						style="width: 120px;position: relative" class="yzm"
						id="img_captcha" src="${ctx }/captchacode" alt="验证码,看不到请点击刷新."
						onclick="reloadCaptcha()" />
					 <a href="javascript:reloadCaptcha()"
						class="text-warning">换验证码</a> <a href="" class="text-danger">忘记密码</a><!-- 忘记密码根据您的需要请自行开发 -->
					<button type="submit" class="btn btn-success btn-block">登录</button>
				</form>
			</div>
		</div>
		<div class="signup-footer ">
			<!-- 版权信息 -->
			<div class="pull-right">${copyright }</div>
		</div>
	</div>
</body>
</html>
