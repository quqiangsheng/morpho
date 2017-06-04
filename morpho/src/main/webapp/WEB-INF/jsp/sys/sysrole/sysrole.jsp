<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>角色管理</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script type="">

/**
 * 角色管理
 */
var MgrSysRole = {
    id: "sysRoleTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
	sysResourceList:[]
};

/**
 * 初始化表格的列
 */
MgrSysRole.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
        {title: '角色编号', field: 'roleId', align: 'center', valign: 'middle'},
        {title: '角色名', field: 'roleName', align: 'center', valign: 'middle'},
 		{title: '角色描述', field: 'roleDesc', align: 'center', valign: 'middle'},
		{title: '拥有资源ID', field: 'resourceIds', align: 'center', valign: 'middle',formatter: function(value, row, index){		
						var result='';
						var valueArr=value.split(",");//value先逗号分隔开		
						for(var j in valueArr){
							var item =valueArr[j];
 							for(var i in MgrSysRole.sysResourceList){
								if(MgrSysRole.sysResourceList[i].resourceId==item){
									result+= MgrSysRole.sysResourceList[i].resourceId+':'+MgrSysRole.sysResourceList[i].resourceName+' &nbsp';
								}
							}
							
						}
						return result;		
    	}},
 		{title: '状态', field: 'isValid', align: 'center', valign: 'middle',formatter: function(value, row, index){
    					return value === '0' ? 
    					'<span class="label label-danger">禁用</span>' : 
    					'<span class="label label-success">正常</span>';
    			}}];
    return columns;
};

/**
 * 检查是否选中
 */
MgrSysRole.check = function () {
    return MgrSysRole.table.checkSingleSelected();
};

/**
 * 点击新增角色
 */
MgrSysRole.openAddSysRole = function () {
   	    var index = layer.open({
            type: 2,
            title: '新增角色',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/sysrole/add"
        });
        this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * 
 */
MgrSysRole.openChangeSysRole = function () {
    if (MgrSysRole.check()) {
		var row=MgrSysRole.table.getFirstSelectionRow();  			
        var index = layer.open({
            type: 2,
            title: '角色修改',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/sysrole/edit?uuid="+row.uuid
        });
        this.layerIndex = index;
    }
};



/**
 * 删除角色
 */
MgrSysRole.delMgrSysRole = function () {
    if (MgrSysRole.check()) {
        var uuid = MgrSysRole.table.getFirstSelectionRow().uuid;;
    			//删除
    			confirm('确定要删除选中的角色吗？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/sysrole/delete?uuid="+uuid,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrSysRole.table.refresh();
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
MgrSysRole.search = function () {
    var queryData = {};
    queryData['roleName'] = $("#roleName").val();
    queryData['roleId'] = $("#roleId").val();
	queryData['isValid'] = $("#isValid").val();
	//带参数查询
    MgrSysRole.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = MgrSysRole.initColumn();
    var table = new BSTable("sysRoleTable", "/sys/sysrole/list", defaultColunms);
    table.setPaginationType("server");
    MgrSysRole.table = table.init();

	//缓存权限字典
	$.ajax({
    				type: "GET",
    			    url: "sys/sysresource/list",
					async:false,
    			    data:{
    			    	pageNumber:'1',
    			    	pageSize:'999999'
        			},
    			   
    			    success: function(r){
        			    
    			    	if(r.status === "1"){
    			    		//
        					MgrSysRole.sysResourceList = r.rows;
							
    					}else{
    						
    					}
    				}
     });
});


</script>
</head>
<body>
	<div id="rrapp">
	    <div class="row">
	    	 <div class="col-sm-3">
                <fns:textInputQuery id="roleId" name="角色ID" placeHolder="角色ID"/>
            </div>
            <div class="col-sm-3">
                <fns:textInputQuery id="roleName" name="角色名" placeHolder="角色名"/>
            </div>
            <div class="col-sm-3">
                <fns:selectInputQuery name="状态" id="isValid">
                 	<option value="" >全部状态</option>
	                <option value="1" >有效</option>
	                <option value="0">无效</option>
                </fns:selectInputQuery>
            </div>
            <div class="col-sm-3">
                <fns:button name="搜索" icon="fa-search" clickFun="MgrSysRole.search()" id=""></fns:button>
            </div>
        </div>
	 	<div class="hidden-xs" id="sysRoleTableToolbar" role="group">	    
	        <fns:button name="新增角色" icon="fa-plus" clickFun="MgrSysRole.openAddSysRole()" id="addSysRole"></fns:button>
	        <fns:button name="修改角色" icon="fa-edit" clickFun="MgrSysRole.openChangeSysRole()" id="updateSysRole"></fns:button>
	        <fns:button name="删除角色" icon="fa-minus" clickFun="MgrSysRole.delMgrSysRole()" id="deleteSysRole"></fns:button>
		</div>
	    <fns:table id="sysRoleTable"></fns:table>
	</div>
</body>
</html>