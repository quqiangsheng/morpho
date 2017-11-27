<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>资源权限修改</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script >


/**
 * 初始化资源权限对话框
 */
var MgrSysResource = {
    sysResourceData : {},
    zTreeInstance : null
};



/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MgrSysResource.set = function(key, val) {
    this.sysResourceData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MgrSysResource.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MgrSysResource.close = function() {
	parent.layer.close(window.parent.MgrSysResource.layerIndex);
}

/**
 * 选中某个资源权限ztree列表的选项时
 * 设置pid的值到隐藏域 设置parentId输入框的value为SelectedVal
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
MgrSysResource.onClickDept = function(e, treeId, treeNode) {
    $("#parentId").val( MgrSysResource.zTreeInstance.getSelectedVal());
    $("#pid").val( treeNode.id);
}

/**
 * 显示资源权限选择的树
 *
 * @returns
 */
MgrSysResource.showDeptSelectTree = function() {
    var parentId = $("#parentId");
    var parentIdOffset = $("#parentId").offset();
    $("#parentSysResourceMenu").css({
        left : parentIdOffset.left + "px",
        top : parentIdOffset.top + parentId.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏上级资源权限选择框div
 */
MgrSysResource.hideDeptSelectTree = function() {
    $("#parentSysResourceMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 收集数据
 */
MgrSysResource.collectData = function() {
    this.set('uuid').set('permission').set('resourceUrl').set('isValid').set('pid');
}



/**
 * 提交修改
 */
MgrSysResource.editSubmit = function() {
	//收集填写的表单
    this.collectData();
    $.ajax({
		type: "POST",
		data:this.sysResourceData,
	    url: T.ctxPath + "/sys/sysresource/update",
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

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" 
        ||event.target.id == "parentSysResourceMenu" 
        || $(event.target).parents("#parentSysResourceMenu").length > 0)) {
        MgrSysResource.hideDeptSelectTree();
    }
}

$(function() {
    var ztree = new $ZTree("parentSysResourceMenuTree", "/sys/sysresource/tree");
    ztree.bindOnClick(MgrSysResource.onClickDept);
    ztree.init();
    MgrSysResource.zTreeInstance = ztree;
    //ajax获取信息填充页面
    $.ajax({
		type: "GET",
		async:false,
	    url: T.ctxPath + "/sys/sysresource/uuid/"+T.urlParam("uuid"),
	    success: function(r){
			if(r.status =='1'){
				MgrSysResource.sysResourceData=r.data;
			}else{
				T.error(r.info);
			}
		},
		error: function(data) {
			T.error("失败网络错误");
        }
	});

	//填充
    $("#resourceId").val(MgrSysResource.sysResourceData.resourceId);
    $("#uuid").val(MgrSysResource.sysResourceData.uuid);
    $("#resourceName").val(MgrSysResource.sysResourceData.resourceName);
    $("#permission").val(MgrSysResource.sysResourceData.permission);
    $("#resourceUrl").val(MgrSysResource.sysResourceData.resourceUrl);
    $("#resourceType").val(MgrSysResource.sysResourceData.resourceType);
    $("#isValid").val(MgrSysResource.sysResourceData.isValid);
    $("#pid").val(MgrSysResource.sysResourceData.parentId);
    //设置树选中
    $.fn.zTree.getZTreeObj("parentSysResourceMenuTree").getNodeByParam("id", MgrSysResource.sysResourceData.parentId, null).checked;
   	var zTree_Menu = $.fn.zTree.getZTreeObj("parentSysResourceMenuTree");  
    var node =  $.fn.zTree.getZTreeObj("parentSysResourceMenuTree").getNodeByParam("id", MgrSysResource.sysResourceData.parentId, null);  
    zTree_Menu.selectNode(node,true);//指定选中ID的节点  
    zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开  
    //设置上级组织文本
    $("#parentId").val( $.fn.zTree.getZTreeObj("parentSysResourceMenuTree").getNodeByParam("id", MgrSysResource.sysResourceData.parentId, null).name);
});


</script>

</head>
<body>
		<!-- 垂直表单 -->
      <div class="form-horizontal">
          <div class="row">
              <div class="col-sm-6 b-r">
                  <fns:textInputQuery id="resourceId" name="资源权限ID" readonly="readonly"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="resourceName" name="资源权限名称" readonly="readonly"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="permission" name="资源权限字符串" />
                  <div class="hr-line-dashed"></div>
                   <fns:textInputQuery id="resourceUrl" name="资源权限URL" />
                  <div class="hr-line-dashed"></div>
              </div>
              <div class="col-sm-6">
              
              	   <fns:selectInputQuery name="状态" id="isValid">
              	  	<option value="1" >有效</option>
              	  	<option value="0">无效</option>
              	  </fns:selectInputQuery>
              	  <div class="hr-line-dashed"></div>
              	  <fns:selectInputQuery name="资源权限类型" id="resourceType">
              	  	<option value="2" >按钮</option>
              	  	<option value="1">菜单</option>
              	  </fns:selectInputQuery>
				  <div class="hr-line-dashed"></div>
				  
                  <div class="input-group">
					    <div class="input-group-btn">
						    <button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button"> 
						     上级资源权限
						    </button>
					    </div>
				    	<!-- 树 -->
				        <input class="form-control" type="text"  placeholder="请选择"
				        		id="parentId"
				           		onclick="MgrSysResource.showDeptSelectTree(); return false;"
				                style="background-color: #ffffff !important;"/>
						<input class="form-control" type="hidden" id="pid" value=""/>	
						<input class="form-control" type="hidden" id="uuid" value=""/>		       
					   
					</div>
					<div class="hr-line-dashed"></div>
              </div>
          </div>

          <!-- 父级资源权限的选择框 -->
          <div id="parentSysResourceMenu" class="menuContent"
               style="display: none; position: absolute; z-index: 200;">
              <ul id="parentSysResourceMenuTree" class="ztree tree-box" style="width: 245px !important;"></ul>
          </div>

          <div class="row btn-group-m-t">
              <div class="col-sm-10">
                  <fns:button btnType="info" name="提交" id="ensure" icon="fa-check" clickFun="MgrSysResource.editSubmit()"/>
                  <fns:button btnType="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="MgrSysResource.close()"/>
              </div>
          </div>
      </div>


</body>
</html>
