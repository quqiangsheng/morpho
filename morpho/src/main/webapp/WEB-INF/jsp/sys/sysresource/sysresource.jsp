<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>资源权限</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script type="">

/**
 * 资源权限管理
 */
var MgrSysResource = {
    id: "sysResourceTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MgrSysResource.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
        {title: '资源权限ID', field: 'resourceId', align: 'center', valign: 'middle'},
        {title: '资源权限名字', field: 'resourceName', align: 'center', valign: 'middle'},
 		{title: '上级资源权限ID', field: 'parentId', align: 'center', valign: 'middle'},
		{title: '权限字符串', field: 'permission', align: 'center', valign: 'middle'},
		{title: '资源权限类型', field: 'resourceType', align: 'center', valign: 'middle' ,formatter: function(value, row, index){
    					return value === '1' ? 
    					'<span class="label label-info">菜单</span>' : 
    					'<span class="label label-success">按钮</span>';
    	}},
		{title: '资源权限URL', field: 'resourceUrl', align: 'center', valign: 'middle'},
 		{title: '状态', field: 'isValid', align: 'center', valign: 'middle',formatter: function(value, row, index){
    					return value === '0' ? 
    					'<span class="label label-danger">停用</span>' : 
    					'<span class="label label-success">启用</span>';
    			}}];
    return columns;
};

/**
 * 检查是否选中
 */
MgrSysResource.check = function () {
    return MgrSysResource.table.checkSingleSelected();
};

/**
 * 点击新增资源权限
 */
MgrSysResource.openAddSysResource = function () {
   	    var index = layer.open({
            type: 2,
            title: '新增资源权限',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/sysresource/add"
        });
        this.layerIndex = index;
};

/**
 * 点击修改按钮时
 */
MgrSysResource.openChangeSysResource = function () {
    if (MgrSysResource.check()) {
		var row=MgrSysResource.table.getFirstSelectionRow();  			
        var index = layer.open({
            type: 2,
            title: '资源权限修改',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/sysresource/edit?uuid="+row.uuid
        });
        this.layerIndex = index;
    }
};



/**
 * 删除资源权限
 */
MgrSysResource.delMgrSysResource = function () {
    if (MgrSysResource.check()) {
        var uuid = MgrSysResource.table.getFirstSelectionRow().uuid;;
    			//删除
    			confirm('确定要删除该资源权限及其所有子资源权限吗!!!？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/sysresource/delete?uuid="+uuid,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrSysResource.table.refresh();
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
MgrSysResource.search = function () {
    var queryData = {};
    queryData['resourceName'] = $("#resourceName").val();
    queryData['resourceId'] = $("#resourceId").val();
    queryData['parentId'] = $("#parentId").val();
	//带参数查询
    MgrSysResource.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = MgrSysResource.initColumn();
    var table = new BSTable("sysResourceTable", "/sys/sysresource/list", defaultColunms);
    table.setPaginationType("server");
    MgrSysResource.table = table.init();
});


</script>
</head>
<body>
	<div id="rrapp">
	    <div class="row">
            <div class="col-sm-3">
                <fns:textInputQuery id="resourceName" name="资源权限名称" placeHolder="资源权限名称"/>
            </div>
            <div class="col-sm-3">
                <fns:textInputQuery id="resourceId" name="资源权限ID" placeHolder="资源权限ID"/>
            </div>
            <div class="col-sm-3">
                <fns:textInputQuery id="parentId" name="上级资源权限ID" placeHolder="上级资源权限ID"/>
            </div>
            <div class="col-sm-3">
                <fns:button name="搜索" icon="fa-search" clickFun="MgrSysResource.search()" id=""></fns:button>
            </div>
        </div>
	 	<div class="hidden-xs" id="sysResourceTableToolbar" role="group">	    
	        <fns:button name="新增资源权限" icon="fa-plus" clickFun="MgrSysResource.openAddSysResource()" id="addSysResource"></fns:button>
	        <fns:button name="修改资源权限" icon="fa-edit" clickFun="MgrSysResource.openChangeSysResource()" id="updateSysResource"></fns:button>
	        <fns:button name="删除资源权限" icon="fa-minus" clickFun="MgrSysResource.delMgrSysResource()" id="deleteSysResource"></fns:button>
		</div>
	    <fns:table id="sysResourceTable"></fns:table>
	</div>
</body>
</html>