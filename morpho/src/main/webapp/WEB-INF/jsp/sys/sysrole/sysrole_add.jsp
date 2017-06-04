<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>新增角色</title>
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
 * 清除数据
 */
MgrSysRole.clearData = function() {
    this.sysRoleData = {};
}

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
 * 选中某个资源权限ztree列表的选项时
 * 设置resourceIds的值到隐藏域 设置parentId输入框的value为SelectedVal
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
MgrSysRole.onClickTree = function(e, treeId, treeNode) {
    $("#parentId").attr("value", MgrSysRole.zTreeInstance.getAllSelectedName());
    $("#resourceIds").attr("value", MgrSysRole.zTreeInstance.getAllSelectedId());
}

/**
 * 显示资源权限选择的树
 *
 * @returns
 */
MgrSysRole.showDeptSelectTree = function() {
    var parentId = $("#parentId");
    var parentIdOffset = $("#parentId").offset();
    $("#parentSysRoleMenu").css({
        left : parentIdOffset.left + "px",
        top : parentIdOffset.top + parentId.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏资源权限选择框div
 */
MgrSysRole.hideDeptSelectTree = function() {
    $("#parentSysRoleMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 收集数据
 */
MgrSysRole.collectData = function() {
    this.set('roleId').set('roleName').set('roleDesc').set('isValid').set('resourceIds');
}

/**
 * 提交添加角色
 */
MgrSysRole.addSubmit = function() {
	//清空提交数据
    this.clearData();
    //收集填写的表单
    this.collectData();
    $.ajax({
		type: "POST",
		data:this.sysRoleData,
	    url: T.ctxPath + "/sys/sysrole/createsysrole",
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
        ||event.target.id == "parentSysRoleMenu" 
        || $(event.target).parents("#parentSysRoleMenu").length > 0)) {
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
	
    var ztree =new $ZTree("parentSysResourceMenuTree", "/sys/sysresource/tree")
    ztree.setSettings(settings);
    ztree.init();
    MgrSysRole.zTreeInstance = ztree;
});


</script>

</head>
<body>
		<!-- 垂直表单 -->
      <div class="form-horizontal">
          <div class="row">
              <div class="col-sm-6 b-r">
                  <fns:textInputQuery id="roleId" name="角色ID"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="roleName" name="角色名称" />
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="roleDesc" name="角色描述" />
                  <div class="hr-line-dashed"></div>
              </div>
              <div class="col-sm-6">
              
              	  <fns:selectInputQuery name="状态" id="isValid">
              	  	<option value="1" selected="selected">有效</option>
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
				           		onclick="MgrSysRole.showDeptSelectTree(); return false;"
				                style="background-color: #ffffff !important;"/>
						<input class="form-control" type="hidden" id="resourceIds" value=""/>			       
					   
					</div>
					<div class="hr-line-dashed"></div>
              </div>
          </div>

          <!-- 拥有的资源权限ztree -->
          <div id="parentSysRoleMenu" class="menuContent"
               style="display: none; position: absolute; z-index: 200;">
              <ul id="parentSysResourceMenuTree" class="ztree tree-box" style="width: 245px !important;"></ul>
          </div>

          <div class="row btn-group-m-t">
              <div class="col-sm-10">
                  <fns:button btnType="info" name="提交" id="ensure" icon="fa-check" clickFun="MgrSysRole.addSubmit()"/>
                  <fns:button btnType="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="MgrSysRole.close()"/>
              </div>
          </div>
      </div>


</body>
</html>
