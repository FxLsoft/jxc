<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
	function save() {
		var isValidate = jp.validateForm('#inputForm');//校验表单
		if(!isValidate){
			return false;
		}else{
			jp.loading();
			jp.post("${ctx}/sys/menu/save",$('#inputForm').serialize(),function(data){
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
	<form:form id="inputForm" modelAttribute="menu" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered">
		     <tbody>
		      <tr>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font> 上级菜单:</label></td>
		         <td class="width-35" ><sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
					title="菜单" url="/sys/menu/treeData" extId="${menu.id}" allowClear="true" allowSearch="true" cssClass="form-control required"/></td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font> 名称:</label></td>
		         <td  class="width-35" ><form:input path="name" htmlEscape="false" maxlength="50" class="required form-control "/></td>
		      </tr>
		      <tr>
		         <td  class="active"><label class="pull-right">链接:</label></td>
		         <td><form:input path="href" htmlEscape="false" maxlength="2000" class="form-control "/>
					<span class="help-inline">点击菜单跳转的页面</span></td>
		         <td  class="active"><label class="pull-right">目标:</label></td>
		         <td><form:input path="target" htmlEscape="false" maxlength="10" class="form-control "/>
					<span class="help-inline">链接打开的目标窗口，默认：mainFrame</span></td>
		      </tr>
		      <tr>
		         <td  class="active"><label class="pull-right">图标:</label></td>
		         <td><sys:iconselect id="icon" name="icon" value="${menu.icon}"/></td>
		         <td  class="active"><label class="pull-right">菜单类型:</label></td>
		         <td><form:radiobuttons path="type" items="${fns:getDictList('menu_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required i-checks "/>
					<span class="help-inline"></span></td>
		      </tr>
		      <tr>
		      	 <td  class="active"><label class="pull-right">可见:</label></td>
		         <td><form:radiobuttons path="isShow" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required i-checks "/>
					<span class="help-inline">该菜单或操作是否显示到系统菜单中</span></td>
		         <td  class="active"><label class="pull-right">权限标识:</label></td>
		         <td><form:input path="permission" htmlEscape="false" maxlength="100" class="form-control "/>
					<span class="help-inline">控制器中定义的权限标识，如：@Requires Permissions("权限标识")</span></td>
		      </tr>
		      <tr>
			     <td  class="active"><label class="pull-right">备注:</label></td>
		         <td><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control "/></td>
		         <td  class="active"></td>
		         <td></td>
		      </tr>
		    </tbody>
		  </table>
	</form:form>
</body>
</html>