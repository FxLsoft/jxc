<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                jp.loading();
                jp.post("${ctx}/sys/office/save",$('#inputForm').serialize(),function(data){
                    if(data.success){
                        jp.getParent().refreshNode(data);
                        jp.success(data.msg);
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                    }else {
                        jp.error(data.msg);
                    }
                })
            }

        }
	</script>
</head>
<body class="bg-white">
	<form:form id="inputForm" modelAttribute="office"  method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">上级机构:</label></td>
		         <td class="width-35"><sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
					title="机构" url="/sys/office/treeData" extId="${office.id}"  cssClass="form-control" allowClear="true"/></td>
		         <td  class="width-15"  class="active"><label class="pull-right"><font color="red">*</font>归属区域:</label></td>
		         <td class="width-35"><sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" allowClear="true" url="/sys/area/treeData" cssClass="form-control required"/></td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right"><font color="red">*</font>机构名称:</label></td>
		         <td class="width-35"><form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-15"  class="active"><label class="pull-right">机构编码:</label></td>
		         <td class="width-35"><form:input path="code" htmlEscape="false" maxlength="50" class="form-control"/></td>
		      </tr>
		       <tr>
		         <td class="width-15 active"><label class="pull-right">机构类型:</label></td>
		         <td class="width-35"><form:select path="type" class="form-control">
					<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select></td>
		         <td  class="width-15"  class="active"><label class="pull-right">机构级别:</label></td>
		         <td class="width-35"><form:select path="grade" class="form-control">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">是否可用:</label></td>
		         <td class="width-35"><form:select path="useable" class="form-control">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span></td>
		         <td class="width-15"  class="active"><label class="pull-right">主负责人:</label></td>
		         <td class="width-35"><sys:userselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}"
					 cssClass="form-control" isMultiSelected="false"/></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">副负责人:</label></td>
		         <td class="width-35"><sys:userselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
					  cssClass="form-control" isMultiSelected="false"/></td>
		         <td class="width-15"  class="active"><label class="pull-right">联系地址:</label></td>
		         <td class="width-35"><form:input path="address" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">邮政编码:</label></td>
		         <td class="width-35"><form:input path="zipCode" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		         <td  class="width-15" class="active"><label class="pull-right">负责人:</label></td>
		         <td class="width-35"><form:input path="master" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">电话:</label></td>
		         <td class="width-35"><form:input path="phone" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		         <td  class="width-15"  class="active"><label class="pull-right">传真:</label></td>
		         <td class="width-35"><form:input path="fax" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		      </tr>
		      <tr>
		         <td class="width-15 active"><label class="pull-right">邮箱:</label></td>
		         <td class="width-35"><form:input path="email" htmlEscape="false" maxlength="50" cssClass="form-control" /></td>
		         <td  class="width-15" class="active"><label class="pull-right">备注:</label></td>
		         <td class="width-35"><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
		      </tr>
	      </tbody>
	      </table>
	</form:form>
</body>
</html>