<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>组织机构</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script type="">

/**
 * 组织机构管理
 */
var MgrSysOrganization = {
    id: "sysOrganizationTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MgrSysOrganization.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
        {title: '组织机构ID', field: 'orgId', align: 'center', valign: 'middle'},
        {title: '组织机构名字', field: 'orgName', align: 'center', valign: 'middle'},
 		{title: '组织机构信息', field: 'orgDesc', align: 'center', valign: 'middle'},
		{title: '上级组织ID', field: 'parentId', align: 'center', valign: 'middle'},
 		{title: '状态', field: 'isValid', align: 'center', valign: 'middle',formatter: function(value, row, index){
    					return value === '0' ? 
    					'<span class="label label-danger">禁用</span>' : 
    					'<span class="label label-success">正常</span>';
    			}},
 		{title: '创建时间', field: 'registerTime', align: 'center', valign: 'middle',formatter: function(value, row, index){
    					return $.format.date(value, "yyyy/MM/dd");
    			}}];
    return columns;
};

/**
 * 检查是否选中
 */
MgrSysOrganization.check = function () {
    return MgrSysOrganization.table.checkSingleSelected();
};

/**
 * 点击新增机构
 */
MgrSysOrganization.openAddSysOrganization = function () {
   	    var index = layer.open({
            type: 2,
            title: '新增组织机构',
            area: ['800px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/sysorganization/add"
        });
        this.layerIndex = index;
};

/**
 * 点击修改按钮时
 */
MgrSysOrganization.openChangeSysOrganization = function () {
    if (MgrSysOrganization.check()) {
		var row=MgrSysOrganization.table.getFirstSelectionRow();  			
        var index = layer.open({
            type: 2,
            title: '组织机构修改',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/sysorganization/edit?uuid="+row.uuid
        });
        this.layerIndex = index;
    }
};



/**
 * 删除组织机构
 */
MgrSysOrganization.delMgrSysOrganization = function () {
    if (MgrSysOrganization.check()) {
        var uuid = MgrSysOrganization.table.getFirstSelectionRow().uuid;;
    			//删除
    			confirm('确定要删除该组织机构及其所有子机构吗!!!？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/sysorganization/delete?uuid="+uuid,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrSysOrganization.table.refresh();
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
MgrSysOrganization.search = function () {
    var queryData = {};
    queryData['orgName'] = $("#orgName").val();
    queryData['orgId'] = $("#orgId").val();
    queryData['parentId'] = $("#parentId").val();
	//带参数查询
    MgrSysOrganization.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = MgrSysOrganization.initColumn();
    var table = new BSTable("sysOrganizationTable", "/sys/sysorganization/list", defaultColunms);
    table.setPaginationType("server");
    MgrSysOrganization.table = table.init();
});


</script>
</head>
<body>
	<div id="rrapp">
	    <div class="row">
            <div class="col-sm-3">
                <fns:textInputQuery id="orgName" name="组织机构名称" placeHolder="组织机构名称"/>
            </div>
            <div class="col-sm-3">
                <fns:textInputQuery id="orgId" name="组织机构ID" placeHolder="组织机构ID"/>
            </div>
            <div class="col-sm-3">
                <fns:textInputQuery id="parentId" name="上级机构ID" placeHolder="上级机构ID"/>
            </div>
            <div class="col-sm-3">
                <fns:button name="搜索" icon="fa-search" clickFun="MgrSysOrganization.search()" id=""></fns:button>
            </div>
        </div>
	 	<div class="hidden-xs" id="sysOrganizationTableToolbar" role="group">	    
	        <fns:button name="新增组织机构" icon="fa-plus" clickFun="MgrSysOrganization.openAddSysOrganization()" id="addSysOrganization"></fns:button>
	        <fns:button name="修改组织机构" icon="fa-edit" clickFun="MgrSysOrganization.openChangeSysOrganization()" id="updateSysOrganization"></fns:button>
	        <fns:button name="删除组织机构" icon="fa-minus" clickFun="MgrSysOrganization.delMgrSysOrganization()" id="deleteSysOrganization"></fns:button>
		</div>
	    <fns:table id="sysOrganizationTable"></fns:table>
	</div>
</body>
</html>