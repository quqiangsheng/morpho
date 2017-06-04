<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>系统日志</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script type="">

/**
 * 日志管理
 */
var MgrLog = {
    id: "sysLogTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MgrLog.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'uuid', visible: false, align: 'center', valign: 'middle'},
        {title: '日志编号', field: 'logId', align: 'center', valign: 'middle'},
        {title: '日志信息', field: 'logInfo', align: 'center', valign: 'middle'},
 		{title: '设备类型', field: 'logAgent', align: 'center', valign: 'middle'},
		{title: '设备ip', field: 'logIp', align: 'center', valign: 'middle'},
 		{title: '用户名', field: 'logUser', align: 'center', valign: 'middle'},
 		{title: '记录时间', field: 'logTime', align: 'center', valign: 'middle',formatter: function(value, row, index){
    					return $.format.date(value, "yyyy/MM/dd HH:mm:ss");;
    			}}];
    return columns;
};


//当点击搜索时
MgrLog.search = function () {
    var queryData = {};
    queryData['logUser'] = $("#logUser").val();
    queryData['beginDate'] = $("#beginDate").val();
    queryData['endDate'] = $("#endDate").val();
	//带参数查询
    MgrLog.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = MgrLog.initColumn();
    var table = new BSTable("sysLogTable", "/sys/syslog/list", defaultColunms);
    table.setPaginationType("server");
    MgrLog.table = table.init();
});


</script>
</head>
<body>

	<div id="rrapp">
	    <div class="row">
            <div class="col-sm-3">
                <fns:textInputQuery id="logUser" name="用户名" placeHolder="用户名"/>
            </div>
            <div class="col-sm-3">
                <fns:dateInputQuery  id="beginDate" name="开始日期" isTime="false" pattern="YYYY-MM-DD"/>
            </div>
            <div class="col-sm-3">
               <fns:dateInputQuery  id="endDate" name="结束日期" isTime="false" pattern="YYYY-MM-DD"/>
            </div>
            <div class="col-sm-3">
                <fns:button name="搜索" icon="fa-search" clickFun="MgrLog.search()" id=""></fns:button>
            </div>
        </div>
	 	<div class="hidden-xs" id="sysLogTableToolbar" role="group">	    
	        
		</div>
	    <fns:table id="sysLogTable"></fns:table>
		
	    
	</div>
</body>
</html>