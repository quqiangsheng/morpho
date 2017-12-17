<%-- 本文件使用bootstrap风格 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<%--本文件是系统页面引用内容 如需个性化修改这里就可以--%>
<%	
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String User_Agent = request.getHeader("User-Agent");
	out.println("<!--用户的浏览器信息： " + User_Agent + " -->");
%>
<%-- basePath --%>
<base href="<%=basePath%>" />
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- http元数据 -->
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- bootstrap css3.3.7 -->
<link href="${pageContext.request.contextPath}/statics/css/bootstrap.min.css" rel="stylesheet">
<!-- bootstrap-table css -->
<link href="${pageContext.request.contextPath}/jslib/bootstrap-table-master/dist/bootstrap-table.min.css" rel="stylesheet">
<!-- 字体 css -->
<link href="${pageContext.request.contextPath}/statics/css/font-awesome.min.css" rel="stylesheet">
<!-- 动画css -->
<link href="${pageContext.request.contextPath}/jslib/H+v4.1.0/css/animate.min.css" rel="stylesheet">
<!-- 后台模板css -->
<link href="${pageContext.request.contextPath}/jslib/H+v4.1.0/css/style.min.css" rel="stylesheet">
<!-- main css -->
<link href="${pageContext.request.contextPath}/statics/css/main.css" rel="stylesheet">
<!-- jquery -->
<script src="${pageContext.request.contextPath}/statics/libs/jquery.min.js"></script>
<!-- vue mvvc -->
<script src="${pageContext.request.contextPath}/statics/libs/vue.js"></script>
<!-- vue配合使用路由 -->
<script src="${pageContext.request.contextPath}/statics/libs/router.js"></script>
<!-- bootstrap js -->
<script src="${pageContext.request.contextPath}/statics/libs/bootstrap.min.js"></script>
<!-- bootstrap-table js -->
<script src="${pageContext.request.contextPath}/jslib/bootstrap-table-master/dist/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/jslib/bootstrap-table-master/dist/locale/bootstrap-table-zh-CN.min.js"></script>
<!-- jquery 滚动条 -->
<script src="${pageContext.request.contextPath}/statics/libs/jquery.slimscroll.min.js"></script>
<!-- jquery 点击库 -->
<script src="${pageContext.request.contextPath}/statics/libs/fastclick.min.js"></script>
<!-- layer3.0系列 有问题 -->
<%-- <script src="${pageContext.request.contextPath}/statics/plugins/layer/layer.js"></script> --%>
<!-- h+ 主题js文件 -->
<script src="${pageContext.request.contextPath}/jslib/H+v4.1.0/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<!-- layer2.0系列 -->
<script src="${pageContext.request.contextPath}/jslib/H+v4.1.0/js/plugins/layer/layer.min.js"></script>
<!-- layer date1.1 -->
<script src="${pageContext.request.contextPath}/jslib/laydate/laydate.js"></script>
<!-- 后台主题 -->
<script src="${pageContext.request.contextPath}/jslib/H+v4.1.0/js/hplus.min.js?v=4.1.0"></script>
<!-- tab插件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/H+v4.1.0/js/contabs.min.js"></script>
<!-- pace.js -->
<script src="${pageContext.request.contextPath}/jslib/H+v4.1.0/js/plugins/pace/pace.min.js"></script>
<!-- my97日期控件 -->
<script src="${pageContext.request.contextPath}/jslib/My97DatePicker/WdatePicker.js" type="text/javascript" charset="utf-8"></script>
<!-- ueditor百度文本编辑器控件  -->
<script src="${pageContext.request.contextPath}/jslib/ueditor1_4_3_1-utf8-jsp/ueditor.all.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/jslib/ueditor1_4_3_1-utf8-jsp/ueditor.config.js" type="text/javascript" charset="utf-8"></script>
<!-- 百度文本编辑器内部的highcharts -->
<script src="${pageContext.request.contextPath}/jslib/ueditor1_4_3_1-utf8-jsp/third-party/highcharts/highcharts.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/jslib/ueditor1_4_3_1-utf8-jsp/third-party/highcharts/modules/exporting.js" type="text/javascript" charset="utf-8"></script>
<!-- json-->
<script src="${pageContext.request.contextPath}/jslib/jquery.json.min.js" type="text/javascript" charset="utf-8"></script>
<!-- timer-->
<script src="${pageContext.request.contextPath}/jslib/jquery.timer.js" type="text/javascript" charset="utf-8"></script>
<!-- mousewheel-->
<script src="${pageContext.request.contextPath}/jslib/jquery.mousewheel.js" type="text/javascript" charset="utf-8"></script>
<!-- zTree相关 -->
<link  href="${ctx}/jslib/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/jslib/zTree_v3/js/jquery.ztree.all-3.5.js" type="text/javascript" charset="utf-8"></script>
<!-- js数据字典工具 -->
<script src="${pageContext.request.contextPath}/jslib/jquery.dicbinder.js" type="text/javascript" charset="utf-8"></script>
<!-- 前台时间格式化工具-->
<script src="${pageContext.request.contextPath}/jslib/jquery-dateFormat.js" type="text/javascript" charset="utf-8"></script>
<!-- 全局js -->
<script type="text/javascript" charset="utf-8">
	//laydate日期选择器皮肤初始化
	$(function() {
		laydate.skin('yahui')
	});
	// 百度文本编辑器url配置
	$(function() {
		window.UEDITOR_HOME_URL = '${path}/jslib/ueditor1_4_3_1-utf8-jsp/jsp/';
	});
	var appCon = window.appCon || {};
	appCon.path = '${path}';
	appCon.basePath = '${basePath}';
	appCon.version = '${version}';

	//全局的AJAX访问，处理AJAX请求时SESSION超时
	$.ajaxSetup({
		/* dataType: "json", */
		cache: false,
	    contentType:"application/x-www-form-urlencoded;charset=utf-8",
	    complete:function(XMLHttpRequest,textStatus){
	          if(XMLHttpRequest.status=="408"){
	               //跳转错误页 提示超时
	               window.location.replace('${ctx}/errorpage?shiroLoginFailure=0007');
	       		}	
	    }
	});
	//重写alert 使用layer弹窗插件
	window.alert = function(msg, callback){
		parent.layer.alert(msg, function(index){
			parent.layer.close(index);
			if(typeof(callback) === "function"){
				callback("ok");
			}
		});
	}

	//重写confirm式样框
	window.confirm = function(msg, callback){
		parent.layer.confirm(msg, {btn: ['确定','取消']},
		function(){//确定事件
			if(typeof(callback) === "function"){
				callback("ok");
			}
		});
	}
	//工具集合Tools
	window.T = {};

	// 获取请求参数
	// 使用示例
	// location.href = http://localhost:8080/index.html?id=123
	// T.p('id') --> 123;
	var urlParam = function(name) {
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if(r!=null)return  unescape(r[2]); return null;
	};
	T.urlParam = urlParam;
	T.p = urlParam;
	//工具js
	T.ctxPath='${ctx}';
	T.confirm=  function (tip, ensure) {//询问框
	        parent.layer.confirm(tip, {
	            btn: ['确定', '取消']
	        }, function (index) {
	            ensure();
	            parent.layer.close(index);
	        }, function (index) {
	            parent.layer.close(index);
	        });
    	};
    T.log=  function (info) {
        console.log(info);
    };
    T.alert=  function (info, iconIndex) {
        parent.layer.msg(info, {
            icon: iconIndex
        });
    };
    T.info= function (info) {
        T.alert(info, 0);
    };
    T.success= function (info) {
        T.alert(info, 1);
    };
    T.error=  function (info) {
        T.alert(info, 2);
    };
    T.infoDetail=  function (title, info) {
        var display = "";
        if (typeof info == "string") {
            display = info;
        } else {
            if (info instanceof Array) {
                for (var x in info) {
                    display = display + info[x] + "<br/>";
                }
            } else {
                display = info;
            }
        }
        parent.layer.open({
            title: title,
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['850px', '550px'], //宽高
            content: '<div style="padding: 15px;">' + display + '</div>'
        });
    };
    T.writeObj=  function (obj) {
        var description = "";
        for (var i in obj) {
            var property = obj[i];
            description += i + " = " + property + ",";
        }
        layer.alert(description, {
            skin: 'layui-layer-molv',
            closeBtn: 0
        });
    };

    T.zTreeCheckedNodes=  function (zTreeId) {
        var zTree = $.fn.zTree.getZTreeObj(zTreeId);
        var nodes = zTree.getCheckedNodes();
        var ids = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            ids += "," + nodes[i].id;
        }
        return ids.substring(1);
    };
    T.eventParseObject=  function (event) {//获取点击事件的源对象
        event = event ? event : window.event;
        var obj = event.srcElement ? event.srcElement : event.target;
        return $(obj);
    };
	
	/**
	 * 初始化 BootStrap Table 的封装
	 * 简称bstable
	 * 约定：toolbar的id为 (bstableId + "Toolbar")
	 *
	 */
	(function () {
	    var BSTable = function (bstableId, url, columns) {
	        this.btInstance = null;					//jquery和BootStrapTable绑定的对象
	        this.bstableId = bstableId;
	        this.url = '${ctx}'+ url;
	        this.method = "post";					//默认POST方法
	        this.paginationType = "server";			//默认分页方式是服务器分页,可选项"client"
	        this.toolbarId = bstableId + "Toolbar";	//约定：toolbar的id为 (bstableId + "Toolbar")
	        this.columns = columns;					//列
	        this.height = 650,						//默认表格高度650
	        this.data = {};							//data数据
	    };

	    BSTable.prototype = {
	        /**
	         * 初始化bootstrap table
	         */
	        init: function () {
	            var tableId = this.bstableId;
	            this.btInstance =
	                $('#' + tableId).bootstrapTable({
	                    contentType: "application/x-www-form-urlencoded",// The contentType of request remote data. 请求数据的编码方式
	                    dataType:'json',			//服务端返回的格式
	                    url: this.url,				//请求地址
	                    method: this.method,		//ajax方式,post还是get
	                    ajaxOptions: {				//ajax请求的附带参数
	                        data: this.data
	                    },
	                    toolbar: "#" + this.toolbarId,//顶部工具条
	                    striped: true,     			//是否显示行间隔色
	                    cache: false,      			//是否使用缓存,默认为true
	                    pagination: true,     		//是否显示分页（*）
	                    sortable: true,      		//是否启用排序
	                    sortOrder: "desc",     		//排序方式
	                    pageNumber: 1,      		//初始化加载第一页，默认第一页
	                    pageSize: 50,      			//每页的记录行数（*）
	                    pageList: [10,50,100,200],  //可供选择的每页的行数（*）
	                    queryParamsType: 'custom', 	//默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset, search, sort, order 这是适合mysql风格的分业
	                    							//我们不采用这种默认更改为custom 这时传递pageSize, pageNumber, searchText, sortName, sortOrder
	                    sidePagination: this.paginationType,   //分页方式：client客户端分页，server服务端分页（*）
	                    search: false,      		//是否显示表格搜索，此搜索是客户端搜索，不会进服务端
	                    strictSearch: true,			//设置为 true启用 全匹配搜索，否则为模糊搜索
	                    showColumns: true,     		//是否显示所有的列
	                    showRefresh: true,     		//是否显示刷新按钮
	                    minimumCountColumns: 2,    	//最少允许的列数 2 一般至少一个主键 一个业务字段
	                    clickToSelect: true,    	//是否启用点击选中行
	                    searchOnEnterKey: true,		//设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
	                    columns: this.columns,		//列数组
	                    pagination: true,			//是否显示分页条
	                    height: this.height,		//高度
	                    showFooter:true,			//显示footer
	                    showHeader:true,			//显示header
	                    showToggle:false,			//显示showToggle:true True to show the toggle button to toggle table / card view.
	                    showPaginationSwitch:false,	//显示True to show the pagination switch button.
	                    dataField : 'rows',			//默认值
	                    totalField : 'total',		//默认值
	                    undefinedText:'',			//Defines the default undefined text.
	                    icons: {					//定义工具栏、分页和细节视图中使用的图标。
	                        refresh: 'glyphicon-repeat',
	                        toggle: 'glyphicon-list-alt',
	                        columns: 'glyphicon-list'
	                    },
	                    responseHandler:function(res) {//Before load remote data, handler the response data format, the parameters object contains: res: the response data.
							//先判断res的状态如果不为1 则报错
							if(res.status=='1'){
								
								return res;
							}else{
								//提示错误
								alert("无法获取数据 "+res.info)
								return null;
							}	                    
	                    	
	                    },
	                    iconSize: 'outline'
	                });
	            return this;
	        },

	        /**
	         * 设置分页方式：server 或者 client
	         */
	        setPaginationType: function (type) {
	            this.paginationType = type;
	        },

	        /**
	         * 设置排序方式asc desc 客户端排序
	         */
	        setSortOrder:function (type) {
	            this.sortOrder = type;
	        },
	         
	        /**
	         * 设置提交请求方法默认post  可以输入'post'   'get'
	         */
	        setMethod:function (type) {
	            this.method = type;
	        },
	        /**
	         * 设置ajax post请求时候附带的参数
	         */
	        set: function (key, value) {
	            if (typeof key == "object") {
	                for (var i in key) {
	                    if (typeof i == "function")
	                        continue;
	                    this.data[i] = key[i];
	                }
	            } else {
	                this.data[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
	            }
	            return this;
	        },

	        /**
	         * 设置ajax post请求时候附带的参数
	         */
	        setData: function (data) {
	            this.data = data;
	            return this;
	        },

	        /**
	         * 清空ajax post请求参数
	         */
	        clear: function () {
	            this.data = {};
	            return this;
	        },
	        /**
	         * 得到所选的行 返回的是row数组
	         */
	        getSelectionRows: function () {
	        	var selected = $('#' + this.bstableId).bootstrapTable('getSelections');
	            return selected;
	        },
	        /**
	         * 得到所选中的第一行 返回的是row
	         */
	        getFirstSelectionRow: function () {
		        
	        	var selected = $('#' + this.bstableId).bootstrapTable('getSelections');
	        	if(selected.length == 0){
	        		return null;
		        }
	        	return selected[0];
	        },
	        /**
	         * 检查是否单选选中 没有选中或者多选 都返回false
	         */
	        checkSingleSelected: function () {
	        	var selected = $('#' + this.bstableId).bootstrapTable('getSelections');
	        	//没选中
        	    if (selected.length == 0) {
        	        return false;
        	    }
        	    //单选成功 
        	    else if (selected.length == 1) {
        	        return true;
        	    } 
        	    //多选不允许
        	    else{
        	        return false;
        	    }
	        },
	        /**
	         * 检查是否选中 没有选中返回false  单选或者多选返回true
	         */
	        checkSelected: function () {
	        	var selected = $('#' + this.bstableId).bootstrapTable('getSelections');
	        	//没选中
        	    if (selected.length == 0) {
        	        return false;
        	    }
        	    //选中
        	    else{
        	        return true;
        	    }
	        },
	        /**
	         * 下一页
	         */
	        nextPage: function () {
	        	$('#' + this.bstableId).bootstrapTable('nextPage');
	        },
	        /**
	         * 上一页
	         */
	        prevPage: function () {
	        	$('#' + this.bstableId).bootstrapTable('prevPage');
	        },
	        /**
	         * 到指定的页码
	         */
	        selectPage: function (pageNum) {
	        	$('#' + this.bstableId).bootstrapTable('selectPage',pageNum);
	        },
	        /**
	         * 刷新 bootstrap 表格
	         * Refresh the remote server data,
	         * you can set {silent: true} to refresh the data silently,
	         * and set {url: newUrl} to change the url.
	         * To supply query params specific to this request, set {query: {foo: 'bar'}}
	         */
	        refresh: function (parms) {
	            if (typeof parms != "undefined") {
	                this.btInstance.bootstrapTable('refresh', parms);
	            } else {
	                this.btInstance.bootstrapTable('refresh');
	            }
	        }
	    };

	    window.BSTable = BSTable;

	}());

	/* zTree 封装 */
	(function() {
		
		var $ZTree = function(id, url) {
			this.id = id;
			this.url = url;
			this.onClick = null;
			this.settings = null;
		};
	
		$ZTree.prototype = {
			/**
			 * 初始化ztree的设置
			 */
			initSetting : function() {
				var settings = {
					view : {
						dblClickExpand : true,
						selectedMulti : false
					},
					data : {simpleData : {enable : true}},//简单数据类型
					callback : {
						onClick : this.onClick
					}
				};
				return settings;
			},
			
			/**
			 * 手动设置ztree的设置
			 */
			setSettings : function(val) {
				this.settings = val;
			},
			
			/**
			 * 初始化ztree
			 */
			init : function() {
				var zNodeSeting = null;
				if(this.settings != null){
					zNodeSeting = this.settings;
				}else{
					zNodeSeting = this.initSetting();
				}
				var zNodes = this.loadNodes();
				
				$.fn.zTree.init($("#" + this.id), zNodeSeting, zNodes);
			},
			
			/**
			 * 绑定onclick事件
			 */
			bindOnClick : function(func) {
				this.onClick = func;
			},
			
			/**
			 * 加载节点
			 */
			loadNodes : function() {
				var zNodes = null;
				$.ajax({
					type: "POST",
					async:false,
				    url: T.ctxPath + this.url,
				    success: function(r){
						if(r.status =='1'){
							//rows里才是树List Json
							zNodes=r.rows;
						}else{
							alert(r.info);
						}
					},
					error: function(data) {
						T.error("加载ztree树失败!");
			        }
				});
				
				return zNodes;
			},
			
			/**
			 * 获取选中的值(第一个选中)
			 */
			getSelectedVal : function(){
				var zTree = $.fn.zTree.getZTreeObj(this.id);
				var nodes = zTree.getSelectedNodes();
				return nodes[0].name;
			},
			/**
			 * 获取所有选中的name用逗号分隔(多选)
			 */
			getAllSelectedName : function(){
				var zTree = $.fn.zTree.getZTreeObj(this.id);
				var nodes = zTree.getCheckedNodes(true);
				var nodesName=new Array();
				for(var i=0;i<nodes.length;i++){
					nodesName[i] = nodes[i].name;
				}
				return  nodesName.join(','); 
			},
			/**
			 * 获取所有选中的id用逗号分隔(多选)
			 */
			getAllSelectedId : function(){
				var zTree = $.fn.zTree.getZTreeObj(this.id);
				var nodes = zTree.getCheckedNodes(true);
				var nodesId=new Array();
				for(var i=0;i<nodes.length;i++){
					nodesId[i] = nodes[i].id;
				}
				return  nodesId.join(','); 
			},
			getZtree:function(){
				var zTree = $.fn.zTree.getZTreeObj(this.id);
				return zTree;
			}
		};
	
		window.$ZTree = $ZTree;
	
	}());

		

</script>


