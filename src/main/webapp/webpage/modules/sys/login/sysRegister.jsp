<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

	<head>
	<meta name="decorator" content="ani"/>
		<title>${fns:getConfig('productName')} 登录</title>
		<script>
			if (window.top !== window.self) {
				window.top.location = window.location;
			}
		</script>
		<script type="text/javascript">
				$(document).ready(function() {
					$("#loginForm").validate({
						rules: {
							validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
						},
						messages: {
							username: {required: "请填写用户名."},password: {required: "请填写密码."},
							validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
						},
						errorLabelContainer: "#messageBox",
						errorPlacement: function(error, element) {
							error.appendTo($("#loginError").parent());
						} 
					});
				});
				// 如果在框架或在对话框中，则弹出提示并跳转到首页
				if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
					alert('未登录或登录超时。请重新登录，谢谢！');
					top.location = "${ctx}";
				}
		</script>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
				loginName: {remote: "${ctx}/sys/user/validateLoginName"},
				mobile: {remote: "${ctx}/sys/user/validateMobile"},
				randomCode: {

					  remote:{

						   url:"${ctx}/sys/register/validateMobileCode", 
	
						  data:{
					       mobile:function(){
					          return $("#tel").val();
					          }
			          		} 

						}


					}
			},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"},
					ck1: {required: "必须接受用户协议."},
					loginName: {remote: "此用户名已经被注册!", required: "用户名不能为空."},
					mobile:{remote: "此手机号已经被注册!", required: "手机号不能为空."},
					randomCode:{remote: "验证码不正确!", required: "验证码不能为空."}
				},
				submitHandler: function(form){
					jp.loading();
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			$("#resetForm").validate({
				rules: {
				mobile: {remote: "${ctx}/sys/user/validateMobileExist"}
			},
				messages: {
					mobile:{remote: "此手机号未注册!", required: "手机号不能为空."}
				},
				submitHandler: function(form){
					jp.loading();
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			// 手机号码验证
			jQuery.validator.addMethod("isMobile", function(value, element) {
			    var length = value.length;
			    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
			    return (length == 11 && mobile.test(value));
			}, "请正确填写您的手机号码");



			$('#sendPassBtn').click(function () { 
				if($("#tel_resetpass").val()=="" || $("#tel_resetpass-error").text()!=""){
					top.layer.alert("请输入有效的注册手机号码！", {icon: 0});//讨厌的白色字体问题
					return;

				}
				$("#sendPassBtn").attr("disabled", true); 
				$.get("${ctx}/sys/user/resetPassword?mobile="+$("#tel_resetpass").val(),function(data){
						if(data.success == false){
							top.layer.alert(data.msg, {icon: 0});//讨厌的白色字体问题
							//alert(data.msg);
							$("#sendPassBtn").html("重新发送").removeAttr("disabled"); 
							clearInterval(countdown); 

						}

					});
				var count = 60; 
				var countdown = setInterval(CountDown, 1000); 
				function CountDown() { 
					$("#sendPassBtn").attr("disabled", true); 
					$("#sendPassBtn").html("等待 " + count + "秒!"); 
					if (count == 0) { 
						$("#sendPassBtn").html("重新发送").removeAttr("disabled"); 
						clearInterval(countdown); 
					} 
					count--; 
				}

				
			}) ;
			

			$('#sendCodeBtn').click(function () { 
				if($("#tel").val()=="" || $("#tel-error").text()!=""){
					top.layer.alert("请输入有效的注册手机号码！", {icon: 0});//讨厌的白色字体问题
					return;

				}
				$("#sendCodeBtn").attr("disabled", true); 
				$.get("${ctx}/sys/register/getRegisterCode?mobile="+$("#tel").val(),function(data){
						if(data.success == false){
							//top.layer.alert(data.msg, {icon: 0});讨厌的白色字体问题
							alert(data.msg);
							$("#sendCodeBtn").html("重新发送").removeAttr("disabled"); 
							clearInterval(countdown); 

						}

					});
				var count = 60; 
				var countdown = setInterval(CountDown, 1000); 
				function CountDown() { 
					$("#sendCodeBtn").attr("disabled", true); 
					$("#sendCodeBtn").html("等待 " + count + "秒!"); 
					if (count == 0) { 
						$("#sendCodeBtn").html("重新发送").removeAttr("disabled"); 
						clearInterval(countdown); 
					} 
					count--; 
				}

				
			}) ;
			
		});

	
		
		

		
	</script>
	</head>

	
	<body>
		

		<div class="login-page">
		<div class="row">
			<div class="col-md-4 col-lg-4 col-md-offset-4 col-lg-offset-4">
				<img  class="img-circle" src="${ctxStatic}/common/images/flat-avatar.png" class="user-avatar" />
				<h1>Jeeplus</h1>
				<sys:message content="${message}" showType="1"/>
				<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/register/registerUser" method="post" class="form-group form-horizontal">
							<input  type="hidden" value="wangba" name="roleName"><!-- 默认注册用户都是网吧管理员 -->
							<div class="form-content">
							<div class="form-group">
								<input id="tel" name="mobile" type="text" value="" maxlength="11" minlength="11" class="form-control input-underline input-lg required isMobile"  placeholder="手机号"/>
							</div>
	
							<div class=" form-group">
									<div class="col-sm-9">
										<input id="code" name="randomCode" type="text" value="" maxlength="4" minlength="4" class="form-control input-underline input-lg required"  placeholder="验证码"/>
									</div>
									<div class="col-sm-3">
										<button type="submit" class="btn btn-default" id="sendCodeBtn">获取验证码!</button>
									</div>
							</div>
							<div class="form-group">
										<input id="userId" name="loginName" type="text" value="${user.loginName }" maxlength="20" minlength="3" class="form-control input-underline input-lg required" placeholder="用户名" />
							</div>
							<div class="form-group">

										<input id="newPassword" name="password" type="password" value="" maxlength="20" minlength="3"  class="form-control input-underline input-lg required" placeholder="密码" />
							</div>
							<div class="form-group">
										<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="20" minlength="3" class="form-control input-underline input-lg required" equalTo="#newPassword" placeholder="重复密码" />
							</div>
							<div class="form-group">

								<label class="block">
									<input name="ck1" type="checkbox" />
									<span class="lbl">
										我接受
										<a href="#"><font  color="white">《JeePlus用户注册协议》</font></a>
									</span>
									<label id="ck1-error" class="error" for="ck1" style="display: none;">必须接受用户协议</label>
								</label>
								<ul class="pull-right btn btn-info btn-circle" style="background-color:white;height:45px;width:46px">	
									<li class="dropdown color-picker" >
										<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
											<span><i class="fa fa-circle"></i></span>
										</a>
										<ul class="dropdown-menu pull-right animated fadeIn" role="menu">
											<li class="padder-h-xs">
												<table class="table color-swatches-table text-center no-m-b">
													<tr>
														<td class="text-center colorr">
															<a href="#" data-theme="blue" class="theme-picker">
																<i class="fa fa-circle blue-base"></i>
															</a>
														</td>
														<td class="text-center colorr">
															<a href="#" data-theme="green" class="theme-picker">
																<i class="fa fa-circle green-base"></i>
															</a>
														</td>
														<td class="text-center colorr">
															<a href="#" data-theme="red" class="theme-picker">
																<i class="fa fa-circle red-base"></i>
															</a>
														</td>
													</tr>
													<tr>
														<td class="text-center colorr">
															<a href="#" data-theme="purple" class="theme-picker">
																<i class="fa fa-circle purple-base"></i>
															</a>
														</td>
														<td class="text-center color">
															<a href="#" data-theme="midnight-blue" class="theme-picker">
																<i class="fa fa-circle midnight-blue-base"></i>
															</a>
														</td>
														<td class="text-center colorr">
															<a href="#" data-theme="lynch" class="theme-picker">
																<i class="fa fa-circle lynch-base"></i>
															</a>
														</td>
													</tr>
												</table>
											</li>
										</ul>
									</li>
							</ul>
							</div>
						<input type="reset" class="btn btn-white btn-outline btn-lg btn-rounded progress-login"  value="重置">
						&nbsp;
						<input type="submit" class="btn btn-white btn-outline btn-lg btn-rounded progress-login"  value="注册">
							</div>
			</form:form>
			<div class="form-values center">
				<a href="${ctx }/login" data-target="#login-box" class="">
					<font color=" #A73438"><i class="ace-icon fa fa-arrow-left"></i>
						返回登录
					</font>
					</a>
				</div>
			</div>
							
				
			</div>			
		</div>
	<script>

		
$(function(){
		$('.theme-picker').click(function() {
			changeTheme($(this).attr('data-theme'));
		}); 	
	
});

function changeTheme(theme) {
	$('<link>')
	.appendTo('head')
	.attr({type : 'text/css', rel : 'stylesheet'})
	.attr('href', '${ctxStatic}/common/css/app-'+theme+'.css');
	//$.get('api/change-theme?theme='+theme);
	 $.get('${pageContext.request.contextPath}/theme/'+theme+'?url='+window.top.location.href,function(result){  });
}
</script>
<style>
li.color-picker i {
    font-size: 24px;
    line-height: 30px;
}
.red-base {
    color: #D24D57;
}
.blue-base {
    color: #3CA2E0;
}
.green-base {
    color: #27ae60;
}
.purple-base {
    color: #957BBD;
}
.midnight-blue-base {
    color: #2c3e50;
}
.lynch-base {
    color: #6C7A89;
}
</style>
</body>
</html>