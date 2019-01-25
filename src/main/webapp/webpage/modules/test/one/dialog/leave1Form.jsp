<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>请假表单管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {

	        $('#beginDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#endDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/test/one/dialog/leave1/save",$('#inputForm').serialize(),function(data){
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
		<form:form id="inputForm" modelAttribute="leave1" class="form-horizontal">
		<form:hidden path="id"/>	
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${leave1.office.id}" labelName="office.name" labelValue="${leave1.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>员工：</label></td>
					<td class="width-35">
						<sys:userselect id="tuser" name="tuser.id" value="${leave1.tuser.id}" labelName="tuser.name" labelValue="${leave1.tuser.name}"
							    cssClass="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">归属区域：</label></td>
					<td class="width-35">
					<div class=" input-group" style=" width: 100%;">
						  <form:input path="area" htmlEscape="false"  class="" data-toggle="city-picker" style="height: 34px;"/>
					</div>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>请假开始日期：</label></td>
					<td class="width-35">
							<div class='input-group form_datetime' id='beginDate'>
			                    <input type='text'  name="beginDate" class="form-control required"  value="<fmt:formatDate value="${leave1.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>请假结束日期：</label></td>
					<td class="width-35">
							<div class='input-group form_datetime' id='endDate'>
			                    <input type='text'  name="endDate" class="form-control required"  value="<fmt:formatDate value="${leave1.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
					</td>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>