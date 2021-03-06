<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


<!-- Mirrored from www.gzsxt.cn/theme/hplus/table_basic.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:20:01 GMT -->
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>办公系统 - 基础表格</title>
<meta name="keywords" content="办公系统">
<meta name="description" content="办公系统">

<link rel="shortcut icon" href="favicon.ico">
<link href="${pageContext.request.contextPath}/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.min862f.css?v=4.1.0" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/plugins/select/bootstrap-select.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/plugins/zTreeStyle/zTreeStyle.css" />

</head>

<body class="gray-bg">
	<div class="wrapper2 wrapper-content2 animated fadeInRight">
		<div class="row">
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>资源管理</h5>
					</div>
					<div class="ibox-content">
						<div class="zTreeDemoBackground left" style="font-size: 16px">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>资源添加</h5>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal" id="sourceForm">
							
							<div class="form-group">
								<label class="col-sm-4 control-label">菜单资源名称：</label>

								<div class="col-sm-7">
									<input type="text" class="form-control" name="name">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">父菜单：</label>

								<div class="col-sm-7">
									<select name="pid" id="pid">

									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">菜单资源路径：</label>

								<div class="col-sm-7">
									<input type="text" class="form-control" name="url">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">备注：</label>
								<div class="col-sm-7">
									<textarea class="form-control" name="remark"></textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-8">
									<input type="button" value="保存" onclick="saveSources();">
									<button class="btn btn-sm btn-white" type="submit">
										<i class="fa fa-undo"></i> 重置
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>

		</div>

	</div>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js?v=2.1.4"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/select/bootstrap-select.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/ztree/jquery.ztree.core.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/ztree/jquery.ztree.exedit.js"></script>

	<SCRIPT type="text/javascript">
		var setting = {
            async: {
                enable:true,
                url:"${pageContext.request.contextPath}/resources/getRootSources",
            },
            view : {
                addHoverDom : function(treeId, treeNode) {
                    var aObj = $("#" + treeNode.tId + "_a");
                    aObj.attr("href", "javascript:void(0);");
                    aObj.attr("target","_self");
                    if (treeNode.editNameFlag || $("#btnGroup" + treeNode.tId).length > 0)
                        return;
                    var s = '<span id="btnGroup'+treeNode.tId+'">';
                    if (treeNode.level == 1) {
                        if (treeNode.children.length == 0) {
                            s += '<span class="button edit" onclick="editNode('
                                + treeNode.id + ')" ></span>';
                            s += '<span class="button remove" onclick="deleteNode('
                                + treeNode.id + ')"></span>';
                        }
                    } else if (treeNode.level == 2) {
                        s += '<span class="button edit" onclick="editNode('
                            + treeNode.id + ')" ></span>';
                        s += '<span class="button remove" onclick="deleteNode('
                            + treeNode.id + ')" ></span>';
                    }
                    s += '</span>';
                    aObj.append(s);
                },
                removeHoverDom : function(treeId, treeNode) {
                    $("#btnGroup" + treeNode.tId).remove();
                }
            }
		};


		//编辑
		function editNode(id) {
			window.location="${pageContext.request.contextPath}/resources/update-resources?id="+id;
		}
		
		//删除
		function deleteNode(id) {
            $.ajax({
                url:"${pageContext.request.contextPath}/resources/deleteSourceById",
                type:"post",
				data:{"id":id},
                dataType:"json",
                cache:false,
                success:function(rs){
                    if(rs.status==200){
                        swal({
                            title : "信息提示",
                            text : rs.msg,
                        }, function() {
                            // 刷新数据 获取树对象
                            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                            //刷新我们的树
                            treeObj.reAsyncChildNodes(null, "refresh");

                        });

                    }else if(rs.status==400){
                        swal("信息提示", rs.msg, "success");
                    }
				}
			});
		}

		$(document).ready(function() {
			$.fn.zTree.init($("#treeDemo"), setting);

			//查询所有的父节点
			$.ajax({
				url:"${pageContext.request.contextPath}/resources/getParentNodes",
				type:"post",
				dataType:"json",
				cache:false,
				success:function(rs){
				    $.each(rs,function(index,element){
				        var option="<option value='"+element.id+"'>"+element.name+"</option>";
				        $("#pid").append(option);
					});
				}
			});

		});
		function saveSources(){
		    $.ajax({
                url:"${pageContext.request.contextPath}/resources/addSources",
                type:"post",
				data:$("#sourceForm").serialize(),
                dataType:"json",
                cache:false,
                success:function(rs){
					if(rs.status==200){
                        swal({
                            title : "信息提示",
                            text : rs.msg,
                        }, function() {
                            // 刷新数据 获取树对象
                            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                            //刷新我们的树
                            treeObj.reAsyncChildNodes(null, "refresh");
                            $("#sourceForm")[0].reset();

                        });

					}else if(rs.status==400){
                        swal("信息提示", rs.msg, "success");
					}
				}
			});

		}
	</SCRIPT>
</body>


</html>
