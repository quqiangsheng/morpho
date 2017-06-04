<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<html>
<head>
    <title>HttpSession管理</title>
    <jsp:include page="/common/common.jsp"></jsp:include>
	<style type="">
	.row-index {
	    width: 50px;
	    display: inline-block;
	}
	</style>
	<script >

/**
 * HttpSession
 */
var MgrHttpSession = {
    id: "httpSessionTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MgrHttpSession.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'sessionId', field: 'sessionId',align: 'center', valign: 'middle'},
        {title: '关联的用户名', field: 'userName', align: 'center', valign: 'middle'},
        {title: '关联的用户ID', field: 'userId', align: 'center', valign: 'middle'},
        {title: '主机ip', field: 'host', align: 'center', valign: 'middle'},
        {title: '会话开始时间', field: 'startTime', align: 'center', valign: 'middle'},
 		{title: '最后访问时间', field: 'lastAccess', align: 'center', valign: 'middle'},
 		{title: '状态', field: 'sessionStatus', align: 'center', valign: 'middle',formatter: function(value, row, index){
    					return value === false ? 
    					'<span class="label label-danger">离线</span>' : 
    					'<span class="label label-success">在线</span>';
    			}}];
    return columns;
};

/**
 * 检查是否选中
 */
MgrHttpSession.check = function () {
    return MgrHttpSession.table.checkSingleSelected();
};


/**
 * 强制离线
 */
MgrHttpSession.delMgrHttpSession = function () {
    if (MgrHttpSession.check()) {
        var sessionId = MgrHttpSession.table.getFirstSelectionRow().sessionId;;
    			//删除
    			confirm('确定要强制离线该会话吗？', function(){
    				$.ajax({
    					type: "POST",
    				    url: T.ctxPath + "/sys/httpsession/delete?sessionId="+sessionId,
    				    success: function(r){
    						if(r.status =='1'){
    							alert(r.info, function(index){
    								//重新加载数据
    								MgrHttpSession.table.refresh();
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
MgrHttpSession.search = function () {
    var queryData = {};
    queryData['sessionId'] = $("#sessionId").val();
    queryData['userName'] = $("#userName").val();
    queryData['userId'] = $("#userId").val();
    if($("#sessionStatus").val()===''){
		//不限状态
    }else{
        //状态条件
    	queryData['sessionStatus'] = $("#sessionStatus").val();
    }
	//带参数查询
    MgrHttpSession.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = MgrHttpSession.initColumn();
    var table = new BSTable("httpSessionTable", "/sys/httpsession/list", defaultColunms);
    table.setPaginationType("server");
    MgrHttpSession.table = table.init();
});


</script>
</head>
<body>
<body>
	<div id="rrapp">
	    <div class="row">
            <div class="col-sm-3">
                <fns:textInputQuery id="sessionId" name="会话ID" placeHolder="HttpSessionId"/>
            </div>
            <div class="col-sm-3">
                <fns:textInputQuery id="userName" name="用户名" placeHolder="用户名"/>
            </div>
            <div class="col-sm-3">
                <fns:textInputQuery id="userId" name="用户ID" placeHolder="用户ID"/>
            </div>
            <div class="col-sm-3">
                <fns:selectInputQuery name="会话状态" id="sessionStatus">
               		<option value="">所有</option>
                	<option value="false">离线</option>
                </fns:selectInputQuery>
            </div>
        </div>
	 	<div class="hidden-xs" id="httpSessionTableToolbar" role="group">	 
	 		 <fns:button name="搜索" icon="fa-search" clickFun="MgrHttpSession.search()" id=""></fns:button>   
	 		&nbsp
	        <fns:button name="强制离线" icon="fa-minus" clickFun="MgrHttpSession.delMgrHttpSession()" id="deleteMgrHttpSession"></fns:button>
		</div>
	    <fns:table id="httpSessionTable"></fns:table>
	</div>
</body>

</body>
</html>