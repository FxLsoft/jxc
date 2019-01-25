<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!-- _login_page_ --><!--登录超时标记 勿删-->
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
				if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0){
					alert('未登录或登录超时。请重新登录，谢谢！');
					top.location = "${ctx}";
				}
		</script>
	
	</head>

	
	<body>
		

		<div class="login-page">
		<div class="row">
			<div class="login-form">
				<img  class="img-circle" src="${ctxStatic}/common/images/flat-avatar.png" class="user-avatar" />
				<h1>超市进销管理系统</h1>
				<sys:message content="${message}" showType="1"/>
				<form id="loginForm" role="form" action="${ctx}/login" method="post">
					<div class="form-content">
						<div class="form-group">
							<input type="text" id="username" name="username" class="form-control required f-input full-width large"  value="" placeholder="用户名">
						</div>

						<div class="form-group">
							<input type="password" id="password" name="password" value="" class="form-control required f-input full-width large" placeholder="密码">
						</div>
						<c:if test="${isValidateCodeLogin}">
						<div class="form-group  text-muted">
								<label class="inline"><font color="white">验证码:</font></label>
							<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:5px;" buttonCssStyle="color:white"/>
						</div>
						</c:if>
						<label class="inline">
								<input  type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} class="ace" />
								<%-- <span id="rememberMe" class="f-switch ${rememberMe ? 'on' : ''}"></span> --%>
								<span class="lbl"> 记住我</span>
						</label>
					</div>
					<input type="submit" class="f-btn primary full-width"  value="登录">
				</form>
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
</body>
</html>