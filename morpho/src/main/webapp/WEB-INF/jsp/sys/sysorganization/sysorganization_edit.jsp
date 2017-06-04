<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fns" uri="http://www.max256.com/morpho/tags/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>组织机构修改</title>
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
    $("#parentId").val( DeptInfoDlg.zTreeInstance.getSelectedVal());
    $("#pid").val( treeNode.id);
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
    this.set('uuid').set('orgName').set('orgDesc').set('isValid').set('pid');
}



/**
 * 提交修改
 */
DeptInfoDlg.editSubmit = function() {
	//收集填写的表单
    this.collectData();
    $.ajax({
		type: "POST",
		data:this.deptInfoData,
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
    //ajax获取信息填充页面
    $.ajax({
		type: "GET",
		async:false,
	    url: T.ctxPath + "/sys/sysorganization/uuid/"+T.urlParam("uuid"),
	    success: function(r){
			if(r.status =='1'){
				DeptInfoDlg.deptInfoData=r.data;
			}else{
				T.error(r.info);
			}
		},
		error: function(data) {
			T.error("失败网络错误");
        }
	});

	//填充
    $("#orgId").val(DeptInfoDlg.deptInfoData.orgId);
    $("#uuid").val(DeptInfoDlg.deptInfoData.uuid);
    $("#orgName").val(DeptInfoDlg.deptInfoData.orgName);
    $("#orgDesc").val(DeptInfoDlg.deptInfoData.orgDesc);
    $("#isValid").val(DeptInfoDlg.deptInfoData.isValid);
    $("#pid").val(DeptInfoDlg.deptInfoData.parentId);
    //设置树选中
    $.fn.zTree.getZTreeObj("parentDeptMenuTree").getNodeByParam("id", DeptInfoDlg.deptInfoData.parentId, null).checked;
	var zTree_Menu = $.fn.zTree.getZTreeObj("parentDeptMenuTree");  
    var node =  $.fn.zTree.getZTreeObj("parentDeptMenuTree").getNodeByParam("id", DeptInfoDlg.deptInfoData.parentId, null);  
    zTree_Menu.selectNode(node,true);//指定选中ID的节点  
    zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开  
    //设置上级组织文本
    $("#parentId").val( $.fn.zTree.getZTreeObj("parentDeptMenuTree").getNodeByParam("id", DeptInfoDlg.deptInfoData.parentId, null).name);
});


</script>

</head>
<body>
		<!-- 垂直表单 -->
      <div class="form-horizontal">
          <div class="row">
              <div class="col-sm-6 b-r">
                  <fns:textInputQuery id="orgId" name="组织机构ID" readonly="readonly"/>
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="orgName" name="组织机构名称" />
				  <div class="hr-line-dashed"></div>
                  <fns:textInputQuery id="orgDesc" name="组织机构信息" />
                  <div class="hr-line-dashed"></div>
              </div>
              <div class="col-sm-6">
              
              	  <fns:selectInputQuery name="状态" id="isValid" disabled="false">
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
				           		onclick="DeptInfoDlg.showDeptSelectTree(); return false;"
				                style="background-color: #ffffff !important;"/>
						<input class="form-control" type="hidden" id="pid" value=""/>	
						<input class="form-control" type="hidden" id="uuid" value=""/>		       
					   
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
                  <fns:button btnType="info" name="提交" id="ensure" icon="fa-check" clickFun="DeptInfoDlg.editSubmit()"/>
                  <fns:button btnType="danger" name="取消" id="cancel" icon="fa-eraser" clickFun="DeptInfoDlg.close()"/>
              </div>
          </div>
      </div>


</body>
</html>
