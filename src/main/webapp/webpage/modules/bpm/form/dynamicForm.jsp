<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程表单设计器</title>
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/formBuilder/assets/css/demo.css">
	<link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/plugin/formBuilder/assets/css/jquery.rateyo.min.css">
	<%--<script src="${ctxStatic}/plugin/formBuilder/assets/js/vendor.js"></script>--%>
	<script src="${ctxStatic}/plugin/formBuilder/assets/js/form-builder.min.js"></script>
	<script src="${ctxStatic}/plugin/formBuilder/assets/js/form-render.min.js"></script>
	<script src="${ctxStatic}/plugin/formBuilder/assets/js/jquery.rateyo.min.js"></script>
	<style>
		div.counter {
			/* display: none; */
			position: relative;
			/* float: right; */
			width: 30px;
			padding: 5px;
			font-size: 15px;
			line-height: 10px;
			/* height: 20px; */
			background-color: rgba(0, 0, 0, 0.8);
			/* top: 50%; */
			left: 200px;
			margin-top: -25px;
			color: white;
		}
		.city-picker-dropdown{
			position: relative !important;
		}
	</style>
	<script>
        <%@include file="index.js" %>
        $(document).ready(function() {
            jp.ajaxForm("#inputForm", function (data) {
                if(data.success){
                    jp.success("保存成功!")
                    jp.go("${ctx}"+ data.body.targetUrl);
                }else{
                    jp.error("保存失败!");
                }

            })
            $("#agree").click(function () {
                jp.prompt("同意, 审批意见", function (message) {
                    jp.post("${ctx}/act/task/audit",
                        {
                            "taskId":"${actForm.act.taskId}",
                            "taskName":"${actForm.act.taskName}",
                            "taskDefKey":"${actForm.act.taskDefKey}",
                            "procInsId":"${actForm.act.procInsId}",
                            "procDefId":"${actForm.act.procDefId}",
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
                            "taskId":"${actForm.act.taskId}",
                            "taskName":"${actForm.act.taskName}",
                            "taskDefKey":"${actForm.act.taskDefKey}",
                            "procInsId":"${actForm.act.procInsId}",
                            "procDefId":"${actForm.act.procDefId}",
                            "flag":$("flag").val(),
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

            $("#agree2").click(function () {
                jp.prompt("办理意见", function (message) {
                    jp.post("${ctx}/act/task/audit",
                        {
                            "taskId":"${actForm.act.taskId}",
                            "taskName":"${actForm.act.taskName}",
                            "taskDefKey":"${actForm.act.taskDefKey}",
                            "procInsId":"${actForm.act.procInsId}",
                            "procDefId":"${actForm.act.procDefId}",
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



        });
	</script>

</head>
<body>

<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">
			<a class="panelButton" href="${ctx}/form/dynamic/list?form_id=${form_id}"><i class="ti-angle-left"></i> 返回</a>
		</h3>
	</div>
	
	<div class="panel-body">
		<form id="inputForm"  action="${ctx}/form/dynamic/save">
			<input type="hidden" name="form_id" value="${form_id}">
			<input type="hidden" name="act.taskId" value="${actForm.act.taskId}"/>
			<input type="hidden" name="act.taskName" value="${actForm.act.taskName}"/>
			<input type="hidden" name="act.taskDefKey" value="${actForm.act.taskDefKey}"/>
			<input type="hidden" name="act.procInsId" value="${actForm.act.procInsId}"/>
			<input type="hidden" name="act.procDefId" value="${actForm.act.procDefId}"/>
			<input type="hidden" name="act.isNextGatewaty" value="${actForm.act.isNextGatewaty}" />
			<input type="hidden" id="flag" name="act.flag" value="${actForm.act.flag}" />
			<div id="dynamic" class="render-wrap">


			</div>
			<c:if test="${actForm.act.taskDefKey eq ''}">
			<div class="col-lg-3"></div>
			<div class="col-lg-6">
				<div class="form-group text-center">
					<div>
						<button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
					</div>
				</div>
			</div>
			</c:if>
		</form>
		<c:if test="${actForm.act.taskDefKey ne '' && !actForm.act.finishTask && actForm.act.isNextGatewaty}">
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">
				<div class="form-group text-center">
					<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</div>
			</div>
		</div>
		</c:if>
		<c:if test="${actForm.act.taskDefKey ne '' && !actForm.act.finishTask && !actForm.act.isNextGatewaty}">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="form-group text-center">
						<input id="agree2" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="办 理" onclick="$('#flag').val('yes')"/>&nbsp;
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${not empty actForm.id}">
			<act:flowChart procInsId="${actForm.act.procInsId}"/>
			<act:histoicFlow procInsId="${actForm.act.procInsId}" />
		</c:if>

	</div>
	</div>
	</div>
</body>
</html>