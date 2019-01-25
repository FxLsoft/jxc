<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>多数据源</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                jp.loading();
                jp.post("${ctx}/tools/sysDataSource/save",$('#inputForm').serialize(),function(data){
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
<form:form id="inputForm" modelAttribute="sysDataSource" class="form-horizontal">
	<form:hidden path="id"/>
	<table class="table table-bordered">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">连接名称：</label></td>
			<td class="width-35">
				<form:input path="name" htmlEscape="false"    class="form-control "/>
			</td>
			<td class="width-15 active"><label class="pull-right">连接英文名：</label></td>
			<td class="width-35">
				<form:input path="enname" htmlEscape="false"    class="form-control "/>
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">数据库用户名：</label></td>
			<td class="width-35">
				<form:input path="dbUserName" htmlEscape="false"    class="form-control "/>
			</td>
			<td class="width-15 active"><label class="pull-right">数据库密码：</label></td>
			<td class="width-35">
				<form:input path="dbPassword" htmlEscape="false"    class="form-control "/>
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">数据库链接：</label></td>
			<td class="width-35">
				<form:input path="dbUrl" htmlEscape="false"    class="form-control "/>
			</td>
			<td class="width-15 active"><label class="pull-right">数据库驱动类：</label></td>
			<td class="width-35">
				<form:select path="dbDriver" class="form-control ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('db_driver')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</td>
		</tr>
		</tbody>
	</table>
</form:form>
</body>
</html>