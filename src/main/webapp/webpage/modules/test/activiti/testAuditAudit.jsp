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
					jp.go("${ctx}/test/activiti/testAudit");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});


            $("#agree").click(function () {
                jp.prompt("同意, 审批意见", function (message) {
                    jp.post("${ctx}/act/task/audit",
                        {
                            "taskId":"${testAudit.act.taskId}",
                            "taskName":"${testAudit.act.taskName}",
                            "taskDefKey":"${testAudit.act.taskDefKey}",
                            "procInsId":"${testAudit.act.procInsId}",
                            "procDefId":"${testAudit.act.procDefId}",
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
                            "taskId":"${testAudit.act.taskId}",
                            "taskName":"${testAudit.act.taskName}",
                            "taskDefKey":"${testAudit.act.taskDefKey}",
                            "procInsId":"${testAudit.act.procInsId}",
                            "procDefId":"${testAudit.act.procDefId}",
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
		<div class="form-group text-center">
			<h3>${testAudit.act.taskName}</h3>
		</div>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>变动用户：</label></td>
					<td class="width-35">
					${testAudit.user.name}
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属部门：</label></td>
					<td class="width-35">
						${testAudit.office.name}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">岗位：</label></td>
					<td class="width-35">
						${testAudit.post}
					</td>
					<td class="width-15 active"><label class="pull-right">性别：</label></td>
					<td class="width-35">
			 			${fns:getDictLabel(testAudit.sex, 'sex', '')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">学历：</label></td>
					<td class="width-35">
						${testAudit.edu}
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>调整原因：</label></td>
					<td class="width-35">
						${testAudit.content}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">现行标准 薪酬档级：</label></td>
					<td class="width-35">
						${testAudit.olda}
					</td>
					<td class="width-15 active"><label class="pull-right">现行标准 月工资额：</label></td>
					<td class="width-35">
						${testAudit.oldb}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">现行标准 年薪总额：</label></td>
					<td class="width-35">
						${testAudit.oldc}
					</td>
					<td class="width-15 active"><label class="pull-right">调整后标准 薪酬档级：</label></td>
					<td class="width-35">
						${testAudit.newa}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>调整后标准 月工资额：</label></td>
					<td class="width-35">
						${testAudit.newb}
					</td>
					<td class="width-15 active"><label class="pull-right">调整后标准 年薪总额：</label></td>
					<td class="width-35">
						${testAudit.newc}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">月增资：</label></td>
					<td class="width-35">
						${testAudit.addNum}
					</td>
					<td class="width-15 active"><label class="pull-right">执行时间：</label></td>
					<td class="width-35">
						<fmt:formatDate value="${testAudit.exeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						${testAudit.remarks}
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>

		
			<c:if test="${testAudit.act.taskDefKey != '' && !testAudit.act.finishTask && testAudit.act.isNextGatewaty}">
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
			<c:if test="${testAudit.act.taskDefKey != '' && !testAudit.act.finishTask && !testAudit.act.isNextGatewaty}">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="form-group text-center">
						<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="办 理" />&nbsp;
					</div>
				</div>
			</div>
			</c:if>

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