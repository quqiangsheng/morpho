<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>字典项</title>
<jsp:include page="/common/common.jsp"></jsp:include>
</head>
<body>
	<div class="panel panel-default" id="rrapp" v-cloak>
		<!-- 垂直表单 -->
		<form class="form-horizontal">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">字典项ID</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="code.codeId" placeholder="字典项ID"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">字典名</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="code.codeName" placeholder="字典名"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">字典文本</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="code.codeText" placeholder="字典文本"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">字典值</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="code.codeValue" placeholder="字典值"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">备注</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="code.codeDesc" placeholder="备注"/>
			    </div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-2 control-label">状态</div> 
				<label class="radio-inline">
				  <input type="radio" name="isValid" value="0" v-model="code.isValid"/> 停用
				</label>
				<label class="radio-inline">
				  <input type="radio" name="isValid" value="1" v-model="code.isValid"/> 启用
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
    var sysCodeUuid= T.p("uuid");//可能为空 因为本页面是新增页面没有传入uuid
    var vm = new Vue({
    	el:'#rrapp',
    	data:{
			code:{
				isValid:1
			}
    	},
    	created: function() {
    		/* 构造函数 */
    		if(sysCodeUuid != null){
    			this.getSysCode(sysCodeUuid)
    		}
    		
        },
    	methods: {
        	//获取用户信息
    		getSysCode: function(uuid){
    			/* rest风格请求 uuid为的code对象的uuid */
    			$.get("sys/syscode/uuid/"+uuid, function(r){
    				if(r.status==='1'){
        				//双向数据绑定填充表单
    					vm.code = r.data;
    				}
    			});
    		},
    		//保存或者更新
    		saveOrUpdate: function (event) {
    			var url = vm.code.uuid == null ? "sys/syscode/createsyscode" : "sys/syscode/updatesyscode";
    			$.ajax({
    				type: "POST",
    			    url: url,
    			    data: vm.code,//JSON.stringify(vm.code),
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