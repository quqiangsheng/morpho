<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>修改定时任务</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script >

var ScheduleJob = {
    infoData : {},
    
};

/**
 * 清除数据
 */
ScheduleJob.clearData = function() {
    this.infoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
 ScheduleJob.set = function(key, val) {
    this.infoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScheduleJob.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ScheduleJob.close = function() {
	parent.layer.close(window.parent.MgrScheduleJob.layerIndex);
}



/**
 * 收集数据
 */
ScheduleJob.collectData = function() {
    this.set('jobId').set('beanName').set('methodName').set('params').set('cronExpression').set('remark').set('status');
}



/**
 * 提交修改
 */
ScheduleJob.editSubmit = function() {
    this.clearData();
    this.collectData();
    $.ajax({
		type: "POST",
	    url: T.ctxPath + "/sys/schedulejob/updateschedulejob",
	    data:this.infoData,
	    success: function(r){
			if(r.status =='1'){
				T.info(r.info);
			}else{
				T.error(r.info);
			}
		},
		error: function(data) {
			T.error("失败网络错误");
        }
	});
}

$(function() {
    //ajax获取信息填充页面
    $.ajax({
		type: "GET",
		async:false,
	    url: T.ctxPath + "/sys/schedulejob/uuid/"+T.urlParam("jobId"),
	    success: function(r){
			if(r.status =='1'){
				ScheduleJob.infoData=r.data;
			}else{
				T.error(r.info);
			}
		},
		error: function(data) {
			T.error("失败网络错误");
        }
	});

	//填充
    $("#jobId").val(ScheduleJob.infoData.jobId);
    $("#beanName").val(ScheduleJob.infoData.beanName);
    $("#methodName").val(ScheduleJob.infoData.methodName);
    $("#params").val(ScheduleJob.infoData.params);
    $("#cronExpression").val(ScheduleJob.infoData.cronExpression);
    $("#remark").val(ScheduleJob.infoData.remark);
    $("#status").val(ScheduleJob.infoData.status);
   
  
    
});

</script>

</head>
<body>
		<!-- 垂直表单 -->
      <div class="form-horizontal">
          <div class="row">
              <div class="col-sm-12 b-r">
              	 <fns:textInputQuery id="jobId" name="任务ID"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="beanName" name="bean名称"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="methodName" name="方法名称" />
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="params" name="方法参数" />
                   <div class="hr-line-dashed"></div>
                   <fns:textInputQuery id="cronExpression" name="cron表达式" />
                  <div class="hr-line-dashed"></div>
                   <fns:textInputQuery id="remark" name="备注" />
                  <div class="hr-line-dashed"></div>
                    <fns:selectInputQuery id="status" name="状态" >
                   	 <option value ="1">激活</option>
                   	 <option value ="0">暂停</option>
                   </fns:selectInputQuery>
                  <div class="hr-line-dashed"></div>
              </div>
          </div>

         
          <div class="row btn-group-m-t">
              <div class="col-sm-10">
                  <fns:button btnType="info" name="修改" id="ensure" icon="fa-check" clickFun="ScheduleJob.editSubmit()"/>
                  <fns:button btnType="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="ScheduleJob.close()"/>
              </div>
          </div>
      </div>


</body>
</html>
