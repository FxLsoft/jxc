<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="ani"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script type="text/javascript">
        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                jp.loading();
                jp.post("${ctx}/sys/role/save",$('#inputForm').serialize(),function(data){
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
		
		$(document).ready(function(){
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")},//设置了远程验证，在初始化时必须预先调用一次。
					enname: {remote: "${ctx}/sys/role/checkEnname?oldEnname=" + encodeURIComponent("${role.enname}")}
				},
				messages: {
					name: {remote: "角色名已存在"},
					enname: {remote: "英文名已存在"}
				}
			});
		});

	</script>
</head>
<body class="bg-white">
	<form:form id="inputForm" modelAttribute="role" autocomplete="off"  method="post" class="form-horizontal" >
		<form:hidden path="id"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">归属机构:</label></td>
		         <td class="width-35"> <sys:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name" labelValue="${role.office.name}"
					 title="机构" url="/sys/office/treeData" allowClear="true" cssClass="form-control required"/></td>
		         <td  class="width-15 active" class="active"><label class="pull-right"><font color="red">*</font>角色名称:</label></td>
		         <td class="width-35"><input id="oldName" name="oldName" type="hidden" value="${role.name}">
					<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>英文名称:</label></td>
		         <td class="width-35"><input id="oldEnname" name="oldEnname" type="hidden" value="${role.enname}">
					<form:input path="enname" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-15 active" class="active"><label class="pull-right">角色类型:</label></td>
		         <td class="width-35"><%--
					<form:input path="roleType" htmlEscape="false" maxlength="50" class="required"/>
					<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
						工作流组用户组类型（security-role：管理员、assignment：可进行任务分配、user：普通用户）</span> --%>
					<form:select path="roleType" class="form-control ">
						<form:option value="assignment">任务分配</form:option>
						<form:option value="security-role">管理角色</form:option>
						<form:option value="user">普通角色</form:option>
					</form:select>
					<span class="help-inline" title="activiti有3种预定义的组类型：security-role、assignment、user 如果使用Activiti Explorer，需要security-role才能看到manage页签，需要assignment才能claim任务">
						工作流组用户组类型（任务分配：assignment、管理角色：security-role、普通角色：user）</span></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">是否系统数据:</label></td>
		         <td class="width-35"><form:select path="sysData" class="form-control ">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline">“是”代表此数据只有超级管理员能进行修改，“否”则表示拥有角色修改人员的权限都能进行修改</span></td>
		         <td  class="width-15 active" class="active"><label class="pull-right">是否可用</label></td>
		         <td class="width-35"><form:select path="useable" class="form-control ">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline">“是”代表此数据可用，“否”则表示此数据不可用</span></td>
		      </tr>
		      <tr>
				 <td class="width-15 active"><label class="pull-right">备注:</label></td>
		         <td class="width-35"><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control "/></td>
		          <td class="width-15 active"></td>
		         <td class="width-35"></td>
		      </tr>
			</tbody>
			</table>
			<form:hidden path="menuIds"/>
	</form:form>
</body>
</html>