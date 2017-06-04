<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>数据字典</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script >

/**
 * 数据字典
 */
var MgrSysCode = {
    id: "sysCodeTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MgrSysCode.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
        {title: '字典ID', field: 'codeId', align: 'center', valign: 'middle'},
        {title: '字典名', field: 'codeName', align: 'center', valign: 'middle'},
 		{title: '字典文本', field: 'codeText', align: 'center', valign: 'middle'},
		{title: '字典值', field: 'codeValue', align: 'center', valign: 'middle'},
		{title: '备注', field: 'codeDesc', align: 'center', valign: 'middle'},
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
MgrSysCode.check = function () {
    return MgrSysCode.table.checkSingleSelected();
};

/**
 * 点击新增字典项
 */
MgrSysCode.openAddSysCode = function () {
   	    var index = layer.open({
            type: 2,
            title: '新增字典项',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/syscode/detail?uuid="
        });
        this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * 
 */
MgrSysCode.openChangeSysCode = function () {
    if (MgrSysCode.check()) {
		var row=MgrSysCode.table.getFirstSelectionRow();  			
        var index = layer.open({
            type: 2,
            title: '字典项修改',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/syscode/detail?uuid="+row.uuid
        });
        this.layerIndex = index;
    }
};



/**
 * 删除字典项
 */
MgrSysCode.delMgrSysCode = function () {
    if (MgrSysCode.check()) {
        var uuid = MgrSysCode.table.getFirstSelectionRow().uuid;;
    			//删除
    			confirm('确定要删除选中的字典项吗？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/syscode/delete?uuid="+uuid,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrSysCode.table.refresh();
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
MgrSysCode.search = function () {
    var queryData = {};
    queryData['codeId'] = $("#codeId").val();
    queryData['codeName'] = $("#codeName").val();
    queryData['codeText'] = $("#codeText").val();
    queryData['codeValue'] = $("#codeValue").val();
	//带参数查询
    MgrSysCode.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = MgrSysCode.initColumn();
    var table = new BSTable("sysCodeTable", "/sys/syscode/list", defaultColunms);
    table.setPaginationType("server");
    MgrSysCode.table = table.init();
});


</script>
</head>
<body>
	<div id="rrapp">
	    <div class="row">
            <div class="col-sm-2">
                <fns:textInputQuery id="codeId" name="字典ID" placeHolder="字典ID"/>
            </div>
            <div class="col-sm-2">
                <fns:textInputQuery id="codeName" name="字典名" placeHolder="字典名"/>
            </div>
            <div class="col-sm-2">
                <fns:textInputQuery id="codeText" name="字典文本" placeHolder="字典文本"/>
            </div>
            <div class="col-sm-2">
                <fns:textInputQuery id="codeValue" name="字典值" placeHolder="字典值"/>
            </div>
            <div class="col-sm-4">
                <fns:button name="搜索" icon="fa-search" clickFun="MgrSysCode.search()" id=""></fns:button>
            </div>
        </div>
	 	<div class="hidden-xs" id="sysCodeTableToolbar" role="group">	    
	        <fns:button name="新增字典项" icon="fa-plus" clickFun="MgrSysCode.openAddSysCode()" id="addSysCode"></fns:button>
	        <fns:button name="修改字典项" icon="fa-edit" clickFun="MgrSysCode.openChangeSysCode()" id="updateSysCode"></fns:button>
	        <fns:button name="删除字典项" icon="fa-minus" clickFun="MgrSysCode.delMgrSysCode()" id="deleteSysCode"></fns:button>
		</div>
	    <fns:table id="sysCodeTable"></fns:table>
	</div>
</body>
</html>