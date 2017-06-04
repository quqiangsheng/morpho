<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>角色修改</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script >


/**
 * 初始化角色对话框
 */
var MgrSysRole = {
    sysRoleData : {},
    zTreeInstance : null
};



/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MgrSysRole.set = function(key, val) {
    this.sysRoleData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MgrSysRole.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MgrSysRole.close = function() {
	parent.layer.close(window.parent.MgrSysRole.layerIndex);
}

/**
 * 选中某个角色ztree列表的选项时
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
MgrSysRole.onClickTree = function(e, treeId, treeNode) {
	
    $("#parentId").val( MgrSysRole.zTreeInstance.getAllSelectedName());
    $("#resourceIds").attr("value", MgrSysRole.zTreeInstance.getAllSelectedId());
}

/**
 * 显示角色选择的树
 *
 * @returns
 */
MgrSysRole.showResourceSelectTree = function() {
    var parentId = $("#parentId");
    var parentIdOffset = $("#parentId").offset();
    $("#sysRoleMenu").css({
        left : parentIdOffset.left + "px",
        top : parentIdOffset.top + parentId.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏上级角色选择框div
 */
MgrSysRole.hideDeptSelectTree = function() {
    $("#sysRoleMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 收集数据
 */
MgrSysRole.collectData = function() {
    this.set('uuid').set('roleDesc').set('isValid').set('resourceIds');
}



/**
 * 提交修改
 */
MgrSysRole.editSubmit = function() {
	//收集填写的表单
    this.collectData();
    $.ajax({
		type: "POST",
		data:this.sysRoleData,
	    url: T.ctxPath + "/sys/sysrole/updatesysrole",
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
        ||event.target.id == "sysRoleMenu" 
        || $(event.target).parents("#sysRoleMenu").length > 0)) {
        MgrSysRole.hideDeptSelectTree();
    }
}

$(function() {
	//ztree setting 允许多选
	var settings = {
					check: {  
				              enable: true,  
				              chkStyle: "checkbox",  
				              chkboxType: { "Y": "s", "N": "ps" }  
				    },  
					view : {
						dblClickExpand : true,
						selectedMulti : true
					},
					data : {simpleData : {enable : true}},//简单数据类型
					callback : {
						
						onCheck : MgrSysRole.onClickTree
					}
	};
	
    var ztree =new $ZTree("sysResourceMenuTree", "/sys/sysresource/tree")
    ztree.setSettings(settings);
    ztree.init();
    MgrSysRole.zTreeInstance = ztree;
    //ajax获取信息填充页面
    $.ajax({
		type: "GET",
		async:false,
	    url: T.ctxPath + "/sys/sysrole/uuid/"+T.urlParam("uuid"),
	    success: function(r){
			if(r.status =='1'){
				MgrSysRole.sysRoleData=r.data;
			}else{
				T.error(r.info);
			}
		},
		error: function(data) {
			T.error("失败网络错误");
        }
	});

	//填充
    $("#roleId").val(MgrSysRole.sysRoleData.roleId);
    $("#uuid").val(MgrSysRole.sysRoleData.uuid);
    $("#roleName").val(MgrSysRole.sysRoleData.roleName);
    $("#roleDesc").val(MgrSysRole.sysRoleData.roleDesc);
    $("#isValid").val(MgrSysRole.sysRoleData.isValid);
    $("#resourceIds").val(MgrSysRole.sysRoleData.resourceIds);
    var resourceIds=MgrSysRole.sysRoleData.resourceIds;//当前勾选的节点id
    var resourceIdsArray=resourceIds.split(",");
    for(var i=0;i<resourceIdsArray.length;i++){
        //
      	var itemId=resourceIdsArray[i]
      	//设置树选中
        $.fn.zTree.getZTreeObj("sysResourceMenuTree").getNodeByParam("id", itemId, null).checked;
    	var zTree_Menu = $.fn.zTree.getZTreeObj("sysResourceMenuTree");  
        var node =  $.fn.zTree.getZTreeObj("sysResourceMenuTree").getNodeByParam("id", itemId, null);  
        zTree_Menu.selectNode(node,true);//指定选中ID的节点  
        zTree_Menu.checkNode(node,true,true);//指定选中ID的节点  
        zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开  
    }
    //设置资源权限文本
    $.ajax({
		type: "GET",
		async:false,
		data:{
		'resourceIds':resourceIds
			},
	    url: T.ctxPath + "/sys/sysresource/getresourcenamebyresourceid",
	    success: function(r){
			if(r.status =='1'){
				$("#parentId").val(r.data);
			}else{
				
			}
		},
		error: function(data) {
			T.error("失败网络错误");
        }
	});
    
});


</script>

</head>
<body>
		<!-- 垂直表单 -->
      <div class="form-horizontal">
          <div class="row">
              <div class="col-sm-6 b-r">
                  <fns:textInputQuery id="roleId" name="角色ID" readonly="readonly"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="roleName" name="角色名称" readonly="readonly"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="roleDesc" name="角色描述" />
                  <div class="hr-line-dashed"></div>
              </div>
              <div class="col-sm-6">
              
              	  <fns:selectInputQuery name="状态" id="isValid">
              	  	<option value="1">有效</option>
              	  	<option value="0">无效</option>
              	  </fns:selectInputQuery>
				  <div class="hr-line-dashed"></div>
				  
                  <div class="input-group">
					    <div class="input-group-btn">
						    <button data-toggle="dropdown" class="btn btn-white dropdown-toggle" type="button"> 
						     拥有资源权限
						    </button>
					    </div>
				    	<!-- 树 -->
				        <input  class="form-control" type="text"  placeHolder="请选择"
				        		id="parentId"
				           		onclick="MgrSysRole.showResourceSelectTree(); return false;"
				                style="background-color: #ffffff !important;"/>
						<input class="form-control" type="hidden" id="resourceIds" value=""/>	
						<input class="form-control" type="hidden" id="uuid" value=""/>			       
					   
					</div>
					<div class="hr-line-dashed"></div>
              </div>
          </div>

          <!-- 拥有的资源权限ztree -->
          <div id="sysRoleMenu" class="menuContent"
               style="display: none; position: absolute; z-index: 200;">
              <ul id="sysResourceMenuTree" class="ztree tree-box" style="width: 245px !important;"></ul>
          </div>

          <div class="row btn-group-m-t">
              <div class="col-sm-10">
                  <fns:button btnType="info" name="提交" id="ensure" icon="fa-check" clickFun="MgrSysRole.editSubmit()"/>
                  <fns:button btnType="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="MgrSysRole.close()"/>
              </div>
          </div>
      </div>


</body>
</html>
