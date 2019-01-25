<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                jp.loading();
                jp.post("${ctx}/sys/dict/save",$('#inputForm').serialize(),function(data){
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
	<form:form id="inputForm" modelAttribute="dictType" action="${ctx}/sys/dict/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		       <tr>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>类型:</label></td>
		         <td class="width-35" ><form:input path="type" htmlEscape="false" maxlength="50" class="form-control required abc"/></td>
		      </tr>
		       <tr>
		          <td  class="width-15 active">	<label class="pull-right"><font color="red">*</font>描述:</label></td>
		          <td  class="width-35" ><form:input path="description" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		   </tbody>
		   </table>   
	</form:form>
</body>
</html>