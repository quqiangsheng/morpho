<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>资源权限新增</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script >
/**
 * 初始化资源权限新增对话框
 */
var MgrSysResource = {
    sysResourceData : {},
    zTreeInstance : null
};

/**
 * 清除数据
 */
MgrSysResource.clearData = function() {
    this.sysResourceData = {};
}

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
 * 选中某个资源权限新增ztree列表的选项时
 * 设置pid的值到隐藏域 设置parentId输入框的value为SelectedVal
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
MgrSysResource.onClickDept = function(e, treeId, treeNode) {
    $("#parentId").attr("value", MgrSysResource.zTreeInstance.getSelectedVal());
    $("#pid").attr("value", treeNode.id);
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
    this.set('resourceId').set('resourceName').set('permission').set('isValid').set('resourceType').set('resourceUrl').set('pid');
}

/**
 * 提交添加资源权限
 */
MgrSysResource.addSubmit = function() {
	//清空提交数据
    this.clearData();
    //收集填写的表单
    this.collectData();
    $.ajax({
		type: "POST",
		data:this.sysResourceData,
	    url: T.ctxPath + "/sys/sysresource/add",
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

/**
 * 提交修改
 */
MgrSysResource.editSubmit = function() {
    this.clearData();
    this.collectData();
    $.ajax({
		type: "POST",
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
});


</script>

</head>
<body>
		<!-- 垂直表单 -->
      <div class="form-horizontal">
          <div class="row">
              <div class="col-sm-6 b-r">
                  <fns:textInputQuery id="resourceId" name="资源权限新增ID"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="resourceName" name="资源权限新增名称" />
				  <div class="hr-line-dashed"></div>
				   <fns:textInputQuery id="permission" name="资源权限字符串" />
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="resourceUrl" name="资源权限URL" />
                  <div class="hr-line-dashed"></div>
              </div>
              <div class="col-sm-6">
              
              	  <fns:selectInputQuery name="状态" id="isValid">
              	  	<option value="1" selected="selected">有效</option>
              	  	<option value="0">无效</option>
              	  </fns:selectInputQuery>
              	  <div class="hr-line-dashed"></div>
              	  <fns:selectInputQuery name="资源权限类型" id="resourceType">
              	  	<option value="2" selected="selected">按钮</option>
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
				        		readonly="readonly"
				           		onclick="MgrSysResource.showDeptSelectTree(); return false;"
				                style="background-color: #ffffff !important;"/>
						<input class="form-control" type="hidden" id="pid" value=""/>			       
					   
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
                  <fns:button btnType="info" name="提交" id="ensure" icon="fa-check" clickFun="MgrSysResource.addSubmit()"/>
                  <fns:button btnType="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="MgrSysResource.close()"/>
              </div>
          </div>
      </div>


</body>
</html>
