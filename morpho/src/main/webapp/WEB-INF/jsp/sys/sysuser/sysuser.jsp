<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>用户管理</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script type="">

/**
 * 系统管理--用户管理的单例对象
 */
var MgrUser = {
    id: "sysUserTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
	sysRoleList:[]
};

/**
 * 初始化表格的列
 */
MgrUser.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
        {title: '用户编号', field: 'userId', align: 'center', valign: 'middle'},
        {title: '用户名', field: 'userName', align: 'center', valign: 'middle'},
 		{title: '昵称', field: 'userNickname', align: 'center', valign: 'middle'},
		{title: '角色', field: 'sysRoleIds', align: 'center', valign: 'middle',formatter: function(value, row, index){	
						var codeArray=$.getSysCodeArray('sysRoleList', value,'sys/sysrole/list','get');		
						for(var i in codeArray){
							if(codeArray[i].roleId==value){
								return '<span class="label label-success">'+codeArray[i].roleName+'</span>';
							}
						}
					//没有找到要翻译的值
					return '<span class="label label-success">'+value+'</span>';
    					
    			}},
		{title: '邮箱', field: 'email', align: 'center', valign: 'middle'},
 		{title: '状态', field: 'isValid', align: 'center', valign: 'middle',formatter: function(value, row, index){
    					return value === '0' ? 
    					'<span class="label label-danger">禁用</span>' : 
    					'<span class="label label-success">正常</span>';
    			}},
 		{title: '创建时间', field: 'registerTime', align: 'center', valign: 'middle',formatter: function(value, row, index){
    					return $.format.date(value, "yyyy/MM/dd HH:mm:ss");
    			}}];
    return columns;
};

/**
 * 检查是否选中
 */
MgrUser.check = function () {
    return MgrUser.table.checkSingleSelected();
};

/**
 * 点击新增用户
 */
MgrUser.openAddUser = function () {
   	    var index = layer.open({
            type: 2,
            title: '新增用户',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/sysuser/detail?uuid="
        });
        this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * 
 */
MgrUser.openChangeUser = function () {
    if (MgrUser.check()) {
		var row=MgrUser.table.getFirstSelectionRow();  			
        var index = layer.open({
            type: 2,
            title: '用户修改',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/sysuser/detail?uuid="+row.uuid
        });
        this.layerIndex = index;
    }
};



/**
 * 删除用户
 */
MgrUser.delMgrUser = function () {
    if (MgrUser.check()) {
        var uuid = MgrUser.table.getFirstSelectionRow().uuid;;
    			//删除
    			confirm('确定要删除选中的用户吗？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/sysuser/delete?uuid="+uuid,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrUser.table.refresh();
    							});
    						}else{
    							alert(r.info);
    						}
    					}
    				});
    			});
    }
};


//当点击搜索时
MgrUser.search = function () {
    var queryData = {};
    queryData['userName'] = $("#userName").val();
    queryData['beginDate'] = $("#beginDate").val();
    queryData['endDate'] = $("#endDate").val();
	//带参数查询
    MgrUser.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = MgrUser.initColumn();
    var table = new BSTable("sysUserTable", "/sys/sysuser/list", defaultColunms);
    table.setPaginationType("server");
    MgrUser.table = table.init();


});


</script>
</head>
<body>
	<div id="rrapp">
	    <div class="row">
            <div class="col-sm-3">
                <fns:textInputQuery id="userName" name="用户名" placeHolder="用户名"/>
            </div>
            <div class="col-sm-3">
                <fns:dateInputQuery  id="beginDate" name="注册开始日期" isTime="false" pattern="YYYY-MM-DD"/>
            </div>
            <div class="col-sm-3">
               <fns:dateInputQuery  id="endDate" name="注册结束日期" isTime="false" pattern="YYYY-MM-DD"/>
            </div>
            <div class="col-sm-3">
                <fns:button name="搜索" icon="fa-search" clickFun="MgrUser.search()" id=""></fns:button>
            </div>
        </div>
	 	<div class="hidden-xs" id="sysUserTableToolbar" role="group">	    
	        <fns:button name="新增用户" icon="fa-plus" clickFun="MgrUser.openAddUser()" id="addUser"></fns:button>
	        <fns:button name="修改用户" icon="fa-edit" clickFun="MgrUser.openChangeUser()" id="updateUser"></fns:button>
	        <fns:button name="删除用户" icon="fa-minus" clickFun="MgrUser.delMgrUser()" id="deleteUser"></fns:button>
		</div>
	    <fns:table id="sysUserTable"></fns:table>
	</div>
</body>
</html>