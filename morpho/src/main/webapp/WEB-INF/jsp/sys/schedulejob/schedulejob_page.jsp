<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>定时任务</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script type="">

/**
 *ScheduleJob定时任务管理
 */
var MgrScheduleJob = {
    id: "scheduleJobTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MgrScheduleJob.initColumn = function () {
    var columns = [
	        {field: 'selectItem', radio: true},
	       	
  {title: '任务ID', field: 'jobId', visible: true, align: 'center', valign: 'middle'}, 
  {title: 'spring bean名称', field: 'beanName', align: 'center', valign: 'middle'}, 
  {title: '方法名', field: 'methodName', align: 'center', valign: 'middle'}, 
  {title: '参数', field: 'params', align: 'center', valign: 'middle'}, 
  {title: 'cron表达式', field: 'cronExpression', align: 'center', valign: 'middle'}, 
  {title: '任务状态', field: 'statusText', align: 'center', valign: 'middle'}, 
  {title: '备注', field: 'remark', align: 'center', valign: 'middle'}, 
  {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle'}
    	];
    return columns;
};

/**
 * 检查是否选中
 */
MgrScheduleJob.check = function () {
    return MgrScheduleJob.table.checkSingleSelected();
};

/**
 * 点击新增
 */
MgrScheduleJob.openAddScheduleJob = function () {
   	    var index = layer.open({
            type: 2,
            title: '新增ScheduleJob定时任务',
            area: ['600px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/schedulejob/add"
        });
        this.layerIndex = index;
};

/**
 * 点击修改按钮时
 */
MgrScheduleJob.openChangeScheduleJob = function () {
    if (MgrScheduleJob.check()) {
		var row=MgrScheduleJob.table.getFirstSelectionRow();  			
        var index = layer.open({
            type: 2,
            title: 'ScheduleJob定时任务修改',
            area: ['600px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: T.ctxPath + "/sys/schedulejob/edit?jobId="+row.jobId
        });
        this.layerIndex = index;
    }
};



/**
 * 删除
 */
MgrScheduleJob.delMgrScheduleJob = function () {
    if (MgrScheduleJob.check()) {
        var jobId = MgrScheduleJob.table.getFirstSelectionRow().jobId;;
    			//删除
    			confirm('确定要删除ScheduleJob吗？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/schedulejob/delete?jobIds="+jobId,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrScheduleJob.table.refresh();
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
MgrScheduleJob.search = function () {
    var queryData = {};
   	
    queryData['jobId'] = $("#jobId").val();  //任务id
    queryData['beanName'] = $("#beanName").val();  //spring bean名称
    queryData['methodName'] = $("#methodName").val();  //方法名
    queryData['params'] = $("#params").val();  //参数
    queryData['status'] = $("#status").val();  //任务状态  0：正常  1：暂停
	//带参数查询
    MgrScheduleJob.table.refresh({query: queryData});
}

//MgrScheduleJob.justDoIt
MgrScheduleJob.justDoIt=function () {
    if (MgrScheduleJob.check()) {
        var jobId = MgrScheduleJob.table.getFirstSelectionRow().jobId;
    			//立即执行
    			confirm('确定要立即执行吗？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/schedulejob/run?jobIds="+jobId,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrScheduleJob.table.refresh();
    							});
    						}else{
    							alert(r.info);
    						}
    					}
    				});
    			});
    }
}

//暂停
MgrScheduleJob.pauseJob=function () {
    if (MgrScheduleJob.check()) {
        var jobId = MgrScheduleJob.table.getFirstSelectionRow().jobId;
    			//立即执行
    			confirm('确定要暂停执行吗？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/schedulejob/pause?jobIds="+jobId,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrScheduleJob.table.refresh();
    							});
    						}else{
    							alert(r.info);
    						}
    					}
    				});
    			});
    }
}

//恢复
MgrScheduleJob.recoverJob=function () {
    if (MgrScheduleJob.check()) {
        var jobId = MgrScheduleJob.table.getFirstSelectionRow().jobId;
    			//立即执行
    			confirm('确定要恢复执行吗？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/schedulejob/resume?jobIds="+jobId,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrScheduleJob.table.refresh();
    							});
    						}else{
    							alert(r.info);
    						}
    					}
    				});
    			});
    }
}
//打开在线生成Cron表达式页面
MgrScheduleJob.makeCron=function () {
	window.open("statics/Cron/Cron.html");
}

$(function () {
    var defaultColunms = MgrScheduleJob.initColumn();
    var table = new BSTable("scheduleJobTable", "/sys/schedulejob/list", defaultColunms);
    table.setPaginationType("server");
    MgrScheduleJob.table = table.init();
});


</script>
</head>
<body>
	<div id="rrapp">
	    <div class="row">
            
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
 <fns:textInputQuery id="cronExpression" name="cron表达式" placeHolder=""/> 
</div>
<div class="col-sm-3">
	<fns:selectInputQuery id="status" name="任务状态" >
    	 <option value ="1">激活</option>
    	 <option value ="0">暂停</option>
    </fns:selectInputQuery> 
</div>


<div class="col-sm-3">
     <fns:button name="搜索" icon="fa-search" clickFun="MgrScheduleJob.search()" id=""></fns:button>
 </div>
        </div>
	 	<div class="hidden-xs" id="sysResourceTableToolbar" role="group">	    
	        <fns:button name="新增" icon="fa-plus" clickFun="MgrScheduleJob.openAddScheduleJob()" id="addScheduleJob"></fns:button>
	        <fns:button name="修改" icon="fa-edit" clickFun="MgrScheduleJob.openChangeScheduleJob()" id="updateScheduleJob"></fns:button>
	        <fns:button name="删除" icon="fa-minus" clickFun="MgrScheduleJob.delMgrScheduleJob()" id="deleteScheduleJob"></fns:button>
	        <fns:button name="暂停执行" icon="fa-minus" clickFun="MgrScheduleJob.pauseJob()" id="pauseJob"></fns:button>
	        <fns:button name="恢复执行" icon="fa-minus" clickFun="MgrScheduleJob.recoverJob()" id="recoverJob"></fns:button>
	        <fns:button name="立即执行一次" icon="fa-minus" clickFun="MgrScheduleJob.justDoIt()" id="justDoItId"></fns:button>
	        <fns:button name="在线生成Cron表达式" icon="fa-minus" clickFun="MgrScheduleJob.makeCron()" id="makeCron"></fns:button>
		</div>
	    <fns:table id="scheduleJobTable"></fns:table>
	</div>
</body>
</html>



