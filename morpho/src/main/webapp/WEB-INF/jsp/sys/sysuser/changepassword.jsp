<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>修改密码</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<script >


 	 function update() {
 	 	
 	 	var oldPass=$("#oldPassword").val();
 	 	var newPass1=$("#newPassword1").val();
 	 	var newPass2=$("#newPassword2").val();

 	 	
 		var paramData={
 				'oldPassword':oldPass,
 				'newPassword':newPass1,
 				'newPassword2':newPass2
 			}
 	 		$.ajax({
 					type: "POST",
 				    url: "${pageContext.request.contextPath}/sys/sysuser/changepassword",
 				    data:paramData,
 				    success: function(r){
 				    	if(r.satatus === '1'){
 				    		alert(r.info);
 						}else{
 							alert(r.info);
 						}
 					}
 				});
	 
 	 }
 			
</script>
</head>
<body>
	<div class="panel panel-default" >
		<!-- 垂直表单 -->
		<form class="form-horizontal">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">用户ID</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" readonly="readonly" value="<shiro:principal property="userId"/>" />
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">用户名</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" value="<shiro:principal property="userName"/>" readonly="readonly"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">旧密码</div>
			   	<div class="col-sm-10">
			      <input type="password" class="form-control" id="oldPassword"/>
			    </div>
			</div>

			<div class="form-group">
			   	<div class="col-sm-2 control-label">新密码</div>
			   	<div class="col-sm-10">
				    <input type="password" class="form-control" id="newPassword1"/>
				</div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">再次输入新密码</div>
			   	<div class="col-sm-10">
				    <input type="password" class="form-control" id="newPassword2"/>
				</div>
			</div>

			<div class="form-group">
				<!-- 按钮组 -->
				<div class="col-sm-2 control-label"></div> 
				<input type="button" class="btn btn-primary" onclick="update()" value="确定修改"/>
			</div>
		</form>
	</div>

</body>
</html>