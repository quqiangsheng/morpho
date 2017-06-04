<!-- ${dataGridName}的DataGrid列表 -->
<div class="easyui-panel" style="width:100%;margin-bottom: 10px;">
	<table id="${dataGridId}" class="easyui-datagrid" style="width:100%;height:500px;"></table>
</div>



<!-- js部分 (${dataGridName}的DataGrid列表)-->
$(function() {
		//$.loaddictionary('isValid');//如果有数据字典翻译的话放开注释自行修改
		//页面加载完毕设置${dataGridName}的数据表格
		$('#${dataGridId}').datagrid(
				{
					//url : '',//${dataGridName}后台处理URL
					idField : 'uuid',//主键字段
					remoteSort : false,//远程排序
					showFooter : true,//显示页脚
					pagination : true,//显示分页  
					rownumbers : true,//True to show a row number column.
					//singleSelect : true,//True to allow selecting only one row.
					ctrlSelect : true,//True to only allow multi-selection when ctrl+click is used. Available since version 1.3.6.
					pageSize : 50,//分页大小
					pageList : [ 3, 10, 50, 100, 1000 ],//每页的个数  
					fit : true,//自动补全  
					fitColumns : true,
					frozenColumns : [ [] ],//固定列
					columns : [ [
						<#list itemMap?keys as mykey>
							{
								sortable : true,
								field : '${mykey}',
								title : '${itemMap[mykey]}',
								width : 100,
								align : 'center'
							},							
						</#list>
							{
								field : 'opt1',
								title : '操作',
								width : 60,
								align : 'center',
								formatter : function(value, row, index) {
									return "<a href=javascript:doSth('"
											+ row.uuid + "')>操作</a>";
								}
							} ] ],
					onBeforeLoad : function(param) {
					},
					onLoadSuccess : function(data) {	
					}
				});
	
		
	//${dataGridName}列表分页事件处理
	$('#${dataGridId}').datagrid("getPager").pagination({
		onBeforeRefresh : function() {
			//do sth
		},
		onRefresh : function(pageNumber, pageSize) {
			param_query(pageNumber, pageSize);//do sth
		},
		onChangePageSize : function(pageNumber, pageSize) {
			param_query(pageNumber, pageSize);//do sth
		},
		onSelectPage : function(pageNumber, pageSize) {
			param_query(pageNumber, pageSize);//do sth
		}
	});
})


//按参数查询列表
function param_query(pageNumber,pageSize){
	//重新加载表格
	//$('#${dataGridId}').datagrid('loading');
	//禁用查询按钮
	$('#btn_query').linkbutton('disable');
	//设置默认分页参数
	if (!pageNumber) {
		pageNumber = $('#${dataGridId}').datagrid("getPager").pagination(
				'options').pageNumber;
	}
	if (!pageSize) {
		pageSize = $('#${dataGridId}').datagrid("getPager").pagination(
				'options').pageSize;
	}
	//查询参数
	var queryParam = {
		<#list itemMap?keys as mykey>					
		${mykey} : $('#${mykey}').val(),										
		</#list>
		//分页参数 必须的
		page : pageNumber,
		rows : pageSize,
	};
    //发起查询请求
	$.ajax({
		url : '',//后台处理查询URL
		data : queryParam,
		dataType : 'json',
		type : 'get',//或post 根据需要
		cache : false,//不缓存
		success : function(re) {	
			var v_total = re.total;
			//设置页脚相关信息和查询出来的内容
			var grid_data = {
				'total' : v_total,
				'rows' : re.rows,
				'footer' : re.footer
			};
			$('#${dataGridId}').datagrid('loadData', grid_data);
			$('#${dataGridId}').datagrid('reload');
			$('#btn_query').linkbutton('enable');//恢复查询按钮可点击			
		}
	});
}

