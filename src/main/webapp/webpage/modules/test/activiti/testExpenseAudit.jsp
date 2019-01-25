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
					jp.go("${ctx}/test/activiti/testExpense");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});


            $("#agree").click(function () {
                jp.prompt("同意, 审批意见", function (message) {
                    jp.post("${ctx}/act/task/audit",
                        {
                            "taskId":"${testExpense.act.taskId}",
                            "taskName":"${testExpense.act.taskName}",
                            "taskDefKey":"${testExpense.act.taskDefKey}",
                            "procInsId":"${testExpense.act.procInsId}",
                            "procDefId":"${testExpense.act.procDefId}",
                            "flag":"yes",
                            "comment":message

                        },
                        function (data) {
								if(data.success){
									jp.success(data.msg);
									jp.go("${ctx}/act/task/todo")
								}
                        })
                })
            })


            $("#reject").click(function () {
                jp.prompt("驳回, 审批意见", function (message) {
                    jp.post("${ctx}/act/task/audit",
                        {
                            "taskId":"${testExpense.act.taskId}",
                            "taskName":"${testExpense.act.taskName}",
                            "taskDefKey":"${testExpense.act.taskDefKey}",
                            "procInsId":"${testExpense.act.procInsId}",
                            "procDefId":"${testExpense.act.procDefId}",
                            "flag":"no",
                            "comment":message

                        },
                        function (data) {

                            if(data.success){
								jp.success(data.msg);
								jp.go("${ctx}/act/task/todo")
                            }


                        })
                })
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
				<a class="panelButton"  href="#"  onclick="history.go(-1)"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<div class="form-group text-center">
			<h3>${testExpense.act.taskName}</h3>
		</div>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>员工姓名：</label></td>
					<td class="width-35">
					${testExpense.tuser.name}
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属部门：</label></td>
					<td class="width-35">
						${testExpense.office.name}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>报销费用：</label></td>
					<td class="width-35">
						${testExpense.cost}
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>报销事由：</label></td>
					<td class="width-35">
						${testExpense.reason}
					</td>
				</tr>
		 	</tbody>
		</table>

		
			<c:if test="${testExpense.act.taskDefKey != '' && !testExpense.act.finishTask && testExpense.act.isNextGatewaty}">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="form-group text-center">
						<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="同 意" />&nbsp;
						<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit" value="驳 回" />&nbsp;
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${testExpense.act.taskDefKey != '' && !testExpense.act.finishTask && !testExpense.act.isNextGatewaty}">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="form-group text-center">
						<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="办 理" />&nbsp;
					</div>
				</div>
			</div>
			</c:if>

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