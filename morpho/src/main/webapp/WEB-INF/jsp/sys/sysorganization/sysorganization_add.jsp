<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>组织结构新增</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<style type="">
.row-index {
    width: 50px;
    display: inline-block;
}
</style>
<script >
/**
 * 初始化组织机构对话框
 */
var DeptInfoDlg = {
    deptInfoData : {},
    zTreeInstance : null
};

/**
 * 清除数据
 */
DeptInfoDlg.clearData = function() {
    this.deptInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptInfoDlg.set = function(key, val) {
    this.deptInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeptInfoDlg.close = function() {
	parent.layer.close(window.parent.MgrSysOrganization.layerIndex);
}

/**
 * 选中某个组织机构ztree列表的选项时
 * 设置pid的值到隐藏域 设置parentId输入框的value为SelectedVal
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
DeptInfoDlg.onClickDept = function(e, treeId, treeNode) {
    $("#parentId").attr("value", DeptInfoDlg.zTreeInstance.getSelectedVal());
    $("#pid").attr("value", treeNode.id);
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
DeptInfoDlg.showDeptSelectTree = function() {
    var parentId = $("#parentId");
    var parentIdOffset = $("#parentId").offset();
    $("#parentDeptMenu").css({
        left : parentIdOffset.left + "px",
        top : parentIdOffset.top + parentId.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏上级机构选择框div
 */
DeptInfoDlg.hideDeptSelectTree = function() {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 收集数据
 */
DeptInfoDlg.collectData = function() {
    this.set('orgId').set('orgName').set('orgDesc').set('isValid').set('pid');
}

/**
 * 提交添加部门
 */
DeptInfoDlg.addSubmit = function() {
	//清空提交数据
    this.clearData();
    //收集填写的表单
    this.collectData();
    $.ajax({
		type: "POST",
		data:this.deptInfoData,
	    url: T.ctxPath + "/sys/sysorganization/add",
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
DeptInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();
    $.ajax({
		type: "POST",
	    url: T.ctxPath + "/sys/sysorganization/update",
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
        ||event.target.id == "parentDeptMenu" 
        || $(event.target).parents("#parentDeptMenu").length > 0)) {
        DeptInfoDlg.hideDeptSelectTree();
    }
}

$(function() {
    var ztree = new $ZTree("parentDeptMenuTree", "/sys/sysorganization/tree");
    ztree.bindOnClick(DeptInfoDlg.onClickDept);
    ztree.init();
    DeptInfoDlg.zTreeInstance = ztree;
});


</script>

</head>
<body>
		<!-- 垂直表单 -->
      <div class="form-horizontal">
          <div class="row">
              <div class="col-sm-6 b-r">
                  <fns:textInputQuery id="orgId" name="组织机构ID"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="orgName" name="组织机构名称" />
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="orgDesc" name="组织机构信息" />
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
						     上级组织机构
						    </button>
					    </div>
				    	<!-- 树 -->
				        <input class="form-control" type="text"  placeholder="请选择"
				        		id="parentId"
				        		readonly="readonly"
				           		onclick="DeptInfoDlg.showDeptSelectTree(); return false;"
				                style="background-color: #ffffff !important;"/>
						<input class="form-control" type="hidden" id="pid" value=""/>			       
					   
					</div>
					<div class="hr-line-dashed"></div>
              </div>
          </div>

          <!-- 父级部门的选择框 -->
          <div id="parentDeptMenu" class="menuContent"
               style="display: none; position: absolute; z-index: 200;">
              <ul id="parentDeptMenuTree" class="ztree tree-box" style="width: 245px !important;"></ul>
          </div>

          <div class="row btn-group-m-t">
              <div class="col-sm-10">
                  <fns:button btnType="info" name="提交" id="ensure" icon="fa-check" clickFun="DeptInfoDlg.addSubmit()"/>
                  <fns:button btnType="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="DeptInfoDlg.close()"/>
              </div>
          </div>
      </div>


</body>
</html>
