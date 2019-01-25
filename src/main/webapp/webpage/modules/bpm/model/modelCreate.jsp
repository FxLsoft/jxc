<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>模型管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                jp.loading();
                jp.post("${ctx}/act/model/create",$('#inputForm').serialize(),function(data){
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
<form:form id="inputForm"  class="form-horizontal">
	<table class="table table-bordered">
		<tbody>
		<tr>
			<td class="width-15 active"><label class="pull-right">流程分类：</label></td>
			<td class="width-35">
				<select id="category" name="category" class="required form-control ">
					<c:forEach items="${fns:getDictList('act_category')}" var="dict">
						<option value="${dict.value}">${dict.label}</option>
					</c:forEach>
				</select>
			</td>
			<td class="width-15 active"><label class="pull-right">模型标识：</label></td>
			<td class="width-35">
				<input id="key" name="key" type="text" class="form-control required" />
			</td>
		</tr>
		<tr>
			<td class="width-15 active"><label class="pull-right">模型名称：</label></td>
			<td class="width-35">
				<input id="name" name="name" type="text" class="form-control required" />
			</td>
			<td class="width-15 active"><label class="pull-right">模块描述：</label></td>
			<td class="width-35">
				<textarea id="description" name="description" class="form-control required"></textarea>
			</td>
		</tr>
		</tbody>
	</table>
</form:form>
</body>
</html>
