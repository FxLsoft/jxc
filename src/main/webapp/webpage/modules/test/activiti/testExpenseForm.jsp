<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>报销申请管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}"+ data.body.targetUrl);
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

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
				<a class="panelButton"  href="#"  onclick="history.go(-1)"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">

		<form:form id="inputForm" modelAttribute="testExpense" action="${ctx}/test/activiti/testExpense/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag" />
		<div class="form-group text-center">
			<h3> 报销申请</h3>
		</div>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>员工姓名：</label></td>
					<td class="width-35">
						<sys:userselect id="tuser" name="tuser.id" value="${testExpense.tuser.id}" labelName="tuser.name" labelValue="${testExpense.tuser.name}"
							    cssClass="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${testExpense.office.id}" labelName="office.name" labelValue="${testExpense.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>报销费用：</label></td>
					<td class="width-35">
						<form:input path="cost" htmlEscape="false"    class="form-control required isFloatGtZero"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>报销事由：</label></td>
					<td class="width-35">
						<form:textarea path="reason" htmlEscape="false" rows="4"    class="form-control required"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		<div class="form-group">
		<div class="col-lg-3"></div>
		<c:if test="${testExpense.act.taskDefKey ne '' && !testExpense.act.finishTask && testExpense.act.isNextGatewaty}">
			<div class="col-sm-6">
				<div class="form-group text-center">
					<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="重新提交" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
				</div>
			</div>
		</c:if>
		<c:if test="${testExpense.act.startTask}">
		<div class="col-lg-6">
			 <div class="form-group text-center">
				 <div>
					 <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
				 </div>
			 </div>
		</div>
		</c:if>
		</div>
		</form:form>
		<c:if test="${not empty testExpense.id}">
			<act:flowChart procInsId="${testExpense.act.procInsId}"/>
			<act:histoicFlow procInsId="${testExpense.act.procInsId}" />
		</c:if>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>