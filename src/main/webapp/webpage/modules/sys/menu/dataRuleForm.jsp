<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>数据权限管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

	function save() {
		var isValidate = jp.validateForm('#inputForm');//校验表单
		if(!isValidate){
			return false;
		}else{
			jp.loading();
			jp.post("${ctx}/sys/dataRule/save",$('#inputForm').serialize(),function(data){
				if(data.success){
					jp.getParent().refresh();
					var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
					parent.layer.close(dialogIndex);
					jp.success(data.msg)
				}else{
					jp.error(data.msg);
				}
			})
		}

	}
</script>
</head>
<body class="bg-white">
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="dataRule" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="menuId"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">数据规则名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">规则实体类：</label>
					<div class="col-sm-10">
						<form:input path="className" htmlEscape="false"    class="form-control "/>
							<span class="help-inline">请输入要进行数据过滤的的实体类名(不含包名,例如：User),将对findList和findAllList进行数据过滤。</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">规则字段：</label>
					<div class="col-sm-10">
						<form:input path="field" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">规则条件：</label>
					<div class="col-sm-10">
						<form:select path="express" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('t_express')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">规则值：</label>
					<div class="col-sm-10">
						<form:input path="value" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">自定义sql：</label>
					<div class="col-sm-10">
						<form:textarea path="sqlSegment" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</body>
</html>