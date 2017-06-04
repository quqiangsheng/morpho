<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>用户管理</title>
<jsp:include page="/common/common.jsp"></jsp:include>
</head>
<body>
	<div class="panel panel-default" id="rrapp" v-cloak>
		<!-- 垂直表单 -->
		<form class="form-horizontal">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">用户ID</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="user.userId" placeholder="建议输入指定用户ID,否则系统将自动生成"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">用户名</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="user.userName" placeholder="登录账号"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">用户昵称</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="user.userNickname" placeholder="用户昵称"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">密码</div>
			   	<div class="col-sm-10">
			      <input type="password" class="form-control" v-model="user.userPassword" placeholder="密码长度5到20位字符串,包括5和20位"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">邮箱</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="user.email" placeholder="邮箱"/>
			    </div>
			</div>
			
			<div class="form-group">
			   	<div class="col-sm-2 control-label">角色</div>
			   	<div class="col-sm-10">
				   	<label v-for="role in roleList" class="radio-inline">
					  <input type="radio" :value="role.roleId" v-model="user.sysRoleIds"/>{{role.roleName}}
					</label>
				</div>
			</div>
			
			
			
			
			<div class="form-group">
				<div class="col-sm-2 control-label">状态</div> 
				<label class="radio-inline">
				  <input type="radio" name="isValid" value="0" v-model="user.isValid"/> 禁用
				</label>
				<label class="radio-inline">
				  <input type="radio" name="isValid" value="1" v-model="user.isValid"/> 正常
				</label>
			</div>
			<div class="form-group">
				<!-- 按钮组 -->
				<div class="col-sm-2 control-label"></div> 
				<input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
				
			</div>
		</form>
	</div>
    <script>
    //T.p()方法来自自定义工具 从url里提取出指定的参数值
    var sysUserUuid= T.p("uuid");//可能为空 因为本页面是新增页面没有传入uuid
    var vm = new Vue({
    	el:'#rrapp',
    	data:{
		    roleList:{},
			user:{
				isValid:1,
				sysRoleIds:[]
			}
    	},
    	created: function() {
    		/* 构造函数 */
    		if(sysUserUuid != null){
    			this.getUser(sysUserUuid)
    			//获取角色信息
        		this.getRoleList();
    		}
    		
        },
    	methods: {
        	//获取用户信息
    		getUser: function(uuid){
    			/* rest风格请求 uuid为的user对象的uuid */
    			$.get("sys/sysuser/uuid/"+uuid, function(r){
    				if(r.status==='1'){
        				//双向数据绑定填充表单
    					vm.user = r.data;
    					
    				}
    			});
    		},
    		//获取角色列表
    		getRoleList: function(){
    			$.ajax({
    				type: "GET",
    			    url: "sys/sysrole/list",
    			    data:{
    			    	pageNumber:'1',
    			    	pageSize:'999999'
        			},
    			   
    			    success: function(r){
        			    
    			    	if(r.status === "1"){
    			    		
    			    		//双向数据绑定填充表单
        					vm.roleList = r.rows;
        					
    					}else{
    						
    					}
    				}
    			});
    		},
    		//保存或者更新
    		saveOrUpdate: function (event) {
    			var url = vm.user.uuid == null ? "sys/sysuser/createsysuser" : "sys/sysuser/updatesysuser";
    			$.ajax({
    				type: "POST",
    			    url: url,
    			    data: vm.user,//JSON.stringify(vm.user),
    			    success: function(r){
    			    	if(r.satatus === '1'){
    						alert(r.info, function(index){
    							
    						});
    					}else{
    						alert(r.info);
    					}
    				}
    			});
    		},
    		
    	}
    });
    </script>
</body>
</html>