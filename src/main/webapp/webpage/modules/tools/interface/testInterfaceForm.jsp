<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>接口管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
					jp.toastr.success(data.msg);
                    jp.go("${ctx}/tools/testInterface");
				}else{
					jp.toastr.error(data.msg);
				}
			})
			
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/tools/testInterface"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="testInterface" action="${ctx}/tools/testInterface/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">接口名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false" maxlength="1024" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接口类型：</label>
					<div class="col-sm-10">
						<form:select path="type" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('interface_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">请求URL：</label>
					<div class="col-sm-10">
						<form:input path="url" htmlEscape="false" maxlength="256" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">请求body：</label>
					<div class="col-sm-10">
						<form:input path="body" htmlEscape="false" maxlength="2048" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">成功时返回消息：</label>
					<div class="col-sm-10">
						<form:input path="successmsg" htmlEscape="false" maxlength="512" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">失败时返回消息：</label>
					<div class="col-sm-10">
						<form:input path="errormsg" htmlEscape="false" maxlength="512" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" maxlength="512" class="form-control "/>
					</div>
				</div>
		<shiro:hasPermission name="tools:testInterface:edit">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <label></label>
		
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</shiro:hasPermission>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>