

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>定时任务日志</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script type="">

/**
 *ScheduleJobLog定时任务日志管理
 */
var MgrScheduleJobLog = {
    id: "scheduleJobLogTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MgrScheduleJobLog.initColumn = function () {
    var columns = [
	        {field: 'selectItem', radio: true},
	       	
  {title: '日志ID', field: 'logId', visible: true, align: 'center', valign: 'middle'}, 
  {title: '任务id', field: 'jobId', align: 'center', valign: 'middle'}, 
  {title: 'spring bean名称', field: 'beanName', align: 'center', valign: 'middle'}, 
  {title: '方法名', field: 'methodName', align: 'center', valign: 'middle'}, 
  {title: '参数', field: 'params', align: 'center', valign: 'middle'}, 
  {title: '任务状态', field: 'statusText', align: 'center', valign: 'middle'}, 
  {title: '失败信息', field: 'error', align: 'center', valign: 'middle'}, 
  {title: '耗时(单位：毫秒)', field: 'times', align: 'center', valign: 'middle'}, 
  {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle'}
    	];
    return columns;
};





//当点击搜索时
MgrScheduleJobLog.search = function () {
    var queryData = {};
   	
    queryData['logId'] = $("#logId").val();  //任务日志id
    queryData['jobId'] = $("#jobId").val();  //任务id
    queryData['beanName'] = $("#beanName").val();  //spring bean名称
    queryData['methodName'] = $("#methodName").val();  //方法名
    queryData['params'] = $("#params").val();  //参数
    queryData['status'] = $("#status").val();  //任务状态    0：成功    1：失败
    queryData['error'] = $("#error").val();  //失败信息
    queryData['times'] = $("#times").val();  //耗时(单位：毫秒)
    queryData['createTime'] = $("#createTime").val();  //创建时间

	//带参数查询
    MgrScheduleJobLog.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = MgrScheduleJobLog.initColumn();
    var table = new BSTable("scheduleJobLogTable", "/sys/schedulejoblog/list", defaultColunms);
    table.setPaginationType("server");
    MgrScheduleJobLog.table = table.init();
});


</script>
</head>
<body>
	<div id="rrapp">
	    <div class="row">
            
<div class="col-sm-3">
 <fns:textInputQuery id="logId" name="任务日志id" placeHolder=""/> 
</div>
<div class="col-sm-3">
 <fns:textInputQuery id="jobId" name="任务id" placeHolder=""/> 
</div>
<div class="col-sm-3">
 <fns:textInputQuery id="beanName" name="spring bean名称" placeHolder=""/> 
</div>
<div class="col-sm-3">
 <fns:textInputQuery id="methodName" name="方法名" placeHolder=""/> 
</div>
<div class="col-sm-3">
 <fns:textInputQuery id="params" name="参数" placeHolder=""/> 
</div>
<div class="col-sm-3">
	<fns:selectInputQuery id="status" name="任务状态" >
    	 <option value ="1">成功</option>
    	 <option value ="0">失败</option>
    </fns:selectInputQuery>
</div>
<div class="col-sm-3">
     <fns:button name="搜索" icon="fa-search" clickFun="MgrScheduleJobLog.search()" id=""></fns:button>
 </div>
</div>
        </div>
	    <fns:table id="scheduleJobLogTable"></fns:table>
	</div>
</body>
</html>



