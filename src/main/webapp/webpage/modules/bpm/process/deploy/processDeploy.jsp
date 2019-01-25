<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>部署流程 - 流程管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                jp.loading('  正在导入，请稍等...');
                var importForm =$("#inputForm")[0];
                jp.uploadFile(importForm, "${ctx}/act/process/deploy",function (data) {
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
	<br/>
	<form id="inputForm" method="post" enctype="multipart/form-data" class="form-horizontal">
		<div class="control-group">
			<label class="control-label">流程分类：</label>
			<div class="controls">
				<select id="category" name="category" class="required form-control">
					<c:forEach items="${fns:getDictList('act_category')}" var="dict">
						<option value="${dict.value}">${dict.label}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流程文件：</label>
			<div class="controls">
				<input type="file" id="file" name="file" class="required form-control"/>
				<span class="help-inline">支持文件格式：zip、bar、bpmn、bpmn20.xml</span>
			</div>
		</div>
	</form>
</body>
</html>
