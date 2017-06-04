<!-- 查询条件框 -->
<div class="easyui-panel" style="width:100%;padding:5px;margin-top: 5px;margin-bottom: 5px;text-align: left;">
	<label class="easyui-input-label">请输入查询条件</label>
	<#if .dataModel??> 
		<#list .dataModel?keys as mykey>
			<!-- 字段:${mykey},含义:${.dataModel[mykey]} -->
			<label class="easyui-input-label">${.dataModel[mykey]}</label>
			<input id="${mykey}"  style="width:200px" />
		</#list>
	 </#if>
	<!-- 查询按钮 -->
	<a id="btn_query" style="padding:5px;margin-top: 5px;margin-bottom: 5px;text-align: right;"  class="easyui-linkbutton">查询</a>			
</div>
