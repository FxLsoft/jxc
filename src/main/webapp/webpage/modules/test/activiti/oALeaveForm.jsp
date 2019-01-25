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
					jp.go("${ctx}"+ data.body.targetUrl);
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

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
		<form:form id="inputForm" modelAttribute="oALeave" action="${ctx}/test/activiti/oALeave/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag" />
		<div class="form-group text-center">
			<h3> 请假申请</h3>
		</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>请假类型：</label>
					<div class="col-sm-10">
						<form:select path="leaveType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('oa_leave_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>请假开始时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='startTime'>
							<input type='text'  name="startTime" class="form-control required"  value="<fmt:formatDate value="${oALeave.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>请假结束时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='endTime'>
							<input type='text'  name="endTime" class="form-control required"  value="<fmt:formatDate value="${oALeave.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
			<hr>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>请假理由：</label>
					<div class="col-sm-10">
                        <input type="hidden" name="reason" value=" ${oALeave.reason}"/>
						<div id="reason">
                          ${fns:unescapeHtml(oALeave.reason)}
                        </div>
					</div>
				</div>
			<hr>
		<div class="form-group">
		<div class="col-lg-3"></div>
		<c:if test="${oALeave.act.taskDefKey ne '' && !oALeave.act.finishTask && oALeave.act.isNextGatewaty}">
			<div class="col-sm-6">
				<div class="form-group text-center">
					<input id="agree" class="btn  btn-primary btn-lg btn-parsley" type="submit" value="重新提交" onclick="$('#flag').val('yes')"/>&nbsp;
					<input id="reject" class="btn  btn-danger btn-lg btn-parsley" type="submit" value="销毁申请" onclick="$('#flag').val('no')"/>&nbsp;
				</div>
			</div>
		</c:if>
		<c:if test="${oALeave.act.startTask}">
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