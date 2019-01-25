<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>请假申请管理</title>
	<meta name="decorator" content="ani"/>
	<!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/test/activiti/oALeave");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});


            $("#agree").click(function () {
                jp.prompt("同意, 审批意见", function (message) {
                    jp.post("${ctx}/act/task/audit",
                        {
                            "taskId":"${oALeave.act.taskId}",
                            "taskName":"${oALeave.act.taskName}",
                            "taskDefKey":"${oALeave.act.taskDefKey}",
                            "procInsId":"${oALeave.act.procInsId}",
                            "procDefId":"${oALeave.act.procDefId}",
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
                            "taskId":"${oALeave.act.taskId}",
                            "taskName":"${oALeave.act.taskName}",
                            "taskDefKey":"${oALeave.act.taskDefKey}",
                            "procInsId":"${oALeave.act.procInsId}",
                            "procDefId":"${oALeave.act.procDefId}",
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


	        $('#startTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#endTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
				//富文本初始化
			$('#reason').summernote({
				height: 300,                
                lang: 'zh-CN',
                readonly: true,
                callbacks: {
                    onChange: function(contents, $editable) {
                        $("input[name='reason']").val($('#reason').summernote('code'));//取富文本的值
                    }
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
		<div class="form-group text-center">
			<h3>${oALeave.act.taskName}</h3>
		</div>
		<form:form id="inputForm" modelAttribute="oALeave" action="${ctx}/test/activiti/oALeave/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>请假类型：</label>
					<div class="col-sm-10">
						 ${fns:getDictLabel(oALeave.leaveType, 'oa_leave_type', '')}
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>请假开始时间：</label>
					<div class="col-sm-10">
						<fmt:formatDate value="${oALeave.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>请假结束时间：</label>
					<div class="col-sm-10">
						<fmt:formatDate value="${oALeave.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>请假理由：</label>
					<div class="col-sm-10">
                         ${fns:unescapeHtml(oALeave.reason)}
					</div>
				</div>
			<hr>
		</form:form>

		
			<c:if test="${oALeave.act.taskDefKey != '' && !oALeave.act.finishTask && oALeave.act.isNextGatewaty}">
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
			<c:if test="${oALeave.act.taskDefKey != '' && !oALeave.act.finishTask && !oALeave.act.isNextGatewaty}">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="form-group text-center">
						<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="办 理" />&nbsp;
					</div>
				</div>
			</div>
			</c:if>

			<c:if test="${not empty oALeave.id}">
				<act:flowChart procInsId="${oALeave.act.procInsId}"/>
				<act:histoicFlow procInsId="${oALeave.act.procInsId}" />
			</c:if>

		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>