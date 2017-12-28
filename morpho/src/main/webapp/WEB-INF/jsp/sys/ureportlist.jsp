<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/common/common.jsp"></jsp:include>
	<title>报表列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
</head>

<body>
<div class="container-fluid" id="rrapp">
	<div class="row-fluid">
		<div class="span12">
		<hr/>
   		   <button class="btn btn-large btn-block btn-info" type="button" @click="loadReportProviders()">刷新</button>
		<hr/>
			<table class="table">
				<thead>
					<tr>
						<th>
							报表存储方式
						</th>
						<th>
							报表存储前缀
						</th>
						<th>
							报表文件名称
						</th>
						<th>
							报表更新时间
						</th>
						<th>
							设计报表
						</th>
						<th>
							预览报表
						</th>
						<th>
							pdf在线浏览
						</th>
						<th>
							pdf下载
						</th>
						<th>
							excel下载
						</th>
						<th>
							word下载
						</th>
					</tr>
				</thead>
				<tbody>
				
					<tr v-for="returnDataItem in returnData">
						<td>
						    {{returnDataItem.storeType}}
						</td>
						<td>
							{{returnDataItem.storePrefixName}}
						</td>
						<td>
							{{returnDataItem.reportName}}
						</td>
						<td>
							{{returnDataItem.reportUpdateDate}}
						</td>
						<td>
						    <a :href="returnDataItem.designerUrl"  target="_blank">设计</a>	
						</td>
						<td>
							<a :href="returnDataItem.previewUrl"  target="_blank">预览</a>	
						</td>
						<td>
							<a :href="returnDataItem.pdfOnlineUrl"  target="_blank">pdf在线浏览</a>	
						</td>
						<td>
							<a :href="returnDataItem.pdfUrl"  target="_blank">pdf</a>	
						</td>
						<td>
							<a :href="returnDataItem.excelUrl"  target="_blank">excel</a>	
						</td>
						<td>
							<a :href="returnDataItem.wordUrl"  target="_blank">word</a>	
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript">
var list_map = new Array();
var vm = new Vue({
	el:'#rrapp',

	/* 
		[
		    {
		        "prefix": "file:",
		        "name": "服务器文件系统",
		        "reportFiles": [
		            {
		                "name": "我的第一个报表.ureport.xml",
		                "updateDate": "2017-12-15 20:29:47"
		            }
		        ]
		    }
		]
	
	 */
	data:{
		returnData:list_map
	},
    created: function () {
           this.loadReportProviders();
    },
	methods:{
		loadReportProviders: function () {
			list_map.length=0;
			var url = "ureport/designer/loadReportProviders";
			$.ajax({
				type: "GET",
			    url: url,
					 async: false,  
			    success: function(r){
			    	var reports = r;
			    	for ( var report of reports) {
			    		for ( var reportreturnDataItem of report.reportFiles) {
			    			let nowdatetime=new Date();
			    			let datetime ="-" + nowdatetime.getFullYear() + "-" + (nowdatetime.getMonth() + 1) + "-" + nowdatetime.getDate() + "-" 
			    							+nowdatetime.getHours()+ "-" + nowdatetime.getMinutes()+"-"+nowdatetime.getSeconds();
			    			let exportName=reportreturnDataItem.name.substr(0,(reportreturnDataItem.name.length-12))+datetime;
			    			list_map.push({storeType:report.name,
			    							storePrefixName:report.prefix,
			    							reportName:reportreturnDataItem.name,
								            designerUrl:'ureport/designer?_u='+report.prefix+reportreturnDataItem.name+"&_n="+exportName,
							             	previewUrl:'ureport/preview?_u='+report.prefix+reportreturnDataItem.name+"&_n="+exportName,
								            pdfOnlineUrl:'ureport/pdf/show?_u='+report.prefix+reportreturnDataItem.name+"&_n="+exportName,
								            wordUrl:'ureport/word?_u='+report.prefix+reportreturnDataItem.name+"&_n="+exportName,
											excelUrl:'ureport/excel?_u='+report.prefix+reportreturnDataItem.name+"&_n="+exportName,
								            pdfUrl:'ureport/pdf?_u='+report.prefix+reportreturnDataItem.name+"&_n="+exportName,
			    							reportUpdateDate:reportreturnDataItem.updateDate
			    							});
						}
					}
				}
			});
		}
		
	}
});
</script>


</body>
</html>
