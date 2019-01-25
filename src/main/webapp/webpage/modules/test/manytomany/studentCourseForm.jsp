<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>学生课程记录管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/test/manytomany/studentCourse");
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
				<a class="panelButton" href="${ctx}/test/manytomany/studentCourse"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="studentCourse" action="${ctx}/test/manytomany/studentCourse/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>学生：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/test/manytomany/student/data" id="student" name="student.id" value="${studentCourse.student.id}" labelName="student.name" labelValue="${studentCourse.student.name}"
							 title="选择学生" cssClass="form-control required" fieldLabels="姓名" fieldKeys="name" searchLabels="姓名" searchKeys="name" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>课程：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/test/manytomany/course/data" id="course" name="course.id" value="${studentCourse.course.id}" labelName="course.name" labelValue="${studentCourse.course.name}"
							 title="选择课程" cssClass="form-control required" fieldLabels="课程名" fieldKeys="name" searchLabels="课程名" searchKeys="name" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>分数：</label>
					<div class="col-sm-10">
						<form:input path="score" htmlEscape="false"    class="form-control required number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
		<c:if test="${mode == 'add' || mode=='edit'}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>