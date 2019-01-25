<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>薪酬调整申请管理</title>
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

	        $('#exeDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
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

		<form:form id="inputForm" modelAttribute="testAudit" action="${ctx}/test/activiti/testAudit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag" />
		<div class="form-group text-center">
			<h3> 薪酬调整申请</h3>
		</div>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>变动用户：</label></td>
					<td class="width-35">
						<sys:userselect id="user" name="user.id" value="${testAudit.user.id}" labelName="user.name" labelValue="${testAudit.user.name}"
							    cssClass="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${testAudit.office.id}" labelName="office.name" labelValue="${testAudit.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">岗位：</label></td>
					<td class="width-35">
						<form:input path="post" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">性别：</label></td>
					<td class="width-35">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">学历：</label></td>
					<td class="width-35">
						<form:input path="edu" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>调整原因：</label></td>
					<td class="width-35">
						<form:textarea path="content" htmlEscape="false" rows="4"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">现行标准 薪酬档级：</label></td>
					<td class="width-35">
						<form:input path="olda" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">现行标准 月工资额：</label></td>
					<td class="width-35">
						<form:input path="oldb" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">现行标准 年薪总额：</label></td>
					<td class="width-35">
						<form:input path="oldc" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">调整后标准 薪酬档级：</label></td>
					<td class="width-35">
						<form:input path="newa" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>调整后标准 月工资额：</label></td>
					<td class="width-35">
						<form:input path="newb" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">调整后标准 年薪总额：</label></td>
					<td class="width-35">
						<form:input path="newc" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">月增资：</label></td>
					<td class="width-35">
						<form:input path="addNum" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">执行时间：</label></td>
					<td class="width-35">
							<div class='input-group form_datetime' id='exeDate'>
			                    <input type='text'  name="exeDate" class="form-control "  value="<fmt:formatDate value="${testAudit.exeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
		<div class="form-group">
		<div class="col-lg-3"></div>
		<c:if test="${testAudit.act.taskDefKey ne '' && !testAudit.act.finishTask && testAudit.act.isNextGatewaty}">
			<div class="col-sm-6">
				<div class="form-group text-center">
					<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="重新提交" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
				</div>
			</div>
		</c:if>
		<c:if test="${testAudit.act.startTask}">
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
		<c:if test="${not empty testAudit.id}">
			<act:flowChart procInsId="${testAudit.act.procInsId}"/>
			<act:histoicFlow procInsId="${testAudit.act.procInsId}" />
		</c:if>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>