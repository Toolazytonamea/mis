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
    <link href="${pageContext.request.contextPath}/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/select/bootstrap-select.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        
      <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>个人资料<small>>修改密码</small></h5>
                    </div>
                    <div class="ibox-content">
                        <form method="get" action="#" class="form-horizontal">
                       	<div class="row">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">原密码</label>
                                <div class="col-sm-3">
                                    <input id="oldPassword" type="password" onblur="validateOldPassword();" class="form-control input-sm">
                                    <span id="oldMsg"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">新密码</label>
                                <div class="col-sm-3">
                                    <input id="newPassword" type="password" onblur="validateNewPassword();" class="form-control input-sm">
                                    <span id="newMsg"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">确认密码</label>
                                <div class="col-sm-3">
                                    <input id="confirmPassword"type="password" onblur="validateConfirmPassword();" class="form-control input-sm">
                                    <span id="confirmPasswordMsg"></span>
                                </div>
                            </div>
                        </div>
                        
                        
                     	<div class="row">
                     		<div class="hr-line-dashed"></div>
                     	</div>
                          
                         <div class="row">
                            <div class="form-group">
                                <div class="col-sm-3 col-sm-offset-3 text-right">
                                    <button type="button" class="btn btn-primary" onclick="updatePassword();">更新密码</button>
                                </div>
                                <div class="col-sm-3">
                                	<a href="list-project.html" class="btn btn-white">返回</a>
                                	</div>
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
    <script src="${pageContext.request.contextPath}/js/plugins/sweetalert/sweetalert.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/select/bootstrap-select.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
    <link href="${pageContext.request.contextPath}/css/customer.css" rel="stylesheet">
    <script>

        var flag=false;
        //验证有原密码
        function validateOldPassword(){
            var oldPassword=$("#oldPassword").val();
            if(oldPassword==null || ""==oldPassword){
                $("#oldMsg").text("原密码不能为空");
                $("#oldMsg").css("color","red");
                return flag;
            }else{
                $.ajax({
                    url:"${pageContext.request.contextPath}/emp/checkPassword",
                    type:"post",
                    data:{"password":oldPassword},
                    dataType:"json",
                    cache:false,
                    success:function(rs){
                        $("#oldMsg").text(rs.msg);
                        if(rs.status==200){
                            $("#oldMsg").css("color","green");
                            flag=true;
                        }else{
                            $("#oldMsg").css("color","red");
                        }
                        return flag;
                    }

                });
            }
        }

        function validateNewPassword(){
           var  newPassword= $("#newPassword").val();
            if(newPassword==null || ""==newPassword){
                $("#newMsg").text("新密码不能为空");
                $("#newMsg").css("color","red");
                return false;
            }
            $("#newMsg").text("新密码符合要求");
            $("#newMsg").css("color","green");
            validateConfirmPassword();
            return true;

        }

        function validateConfirmPassword(){
            var  confirmPassword= $("#confirmPassword").val();
            if(confirmPassword==null || ""==confirmPassword){
                $("#confirmPasswordMsg").text("确认密码不能为空");
                $("#confirmPasswordMsg").css("color","red");
                return false;
            }
            var  newPassword= $("#newPassword").val();
            if(newPassword==null || ""==newPassword){
                $("#newMsg").text("新密码不能为空");
                $("#newMsg").css("color","red");
                return false;
            }
            if(confirmPassword!=newPassword){
                $("#confirmPasswordMsg").text("两次密码不一致");
                $("#confirmPasswordMsg").css("color","red");
                return false;
            }
            $("#confirmPasswordMsg").text("两次密码一致");
            $("#confirmPasswordMsg").css("color","green");
            return true;
        }
        function updatePassword(){

            if(flag && validateConfirmPassword() && validateConfirmPassword() ){
                $.ajax({
                    url:"${pageContext.request.contextPath}/emp/updatePassword",
                    type:"post",
                    data:{"password":$("#newPassword").val()},
                    dataType:"json",
                    cache:false,
                    success:function(rs){
                        if(rs.status==200){
                            swal({
                                title : "信息提示",
                                text : rs.msg,
                            },function(){
                                window.top.location="${pageContext.request.contextPath}/main/index";
                            });
                        }else{
                            swal("信息提示", rs.msg, "error");
                        }

                    }
                });
            }
        }
    </script>
</body>


</html>
    