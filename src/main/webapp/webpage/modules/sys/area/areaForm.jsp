<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                var index = jp.loading();
                jp.post("${ctx}/sys/area/save",$('#inputForm').serialize(),function(data){
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
	<form:form id="inputForm" modelAttribute="area" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		      <tr>
		         <td  class="width-15 active"><label class="pull-right">上级区域:</label></td>
		         <td class="width-35" ><sys:treeselect id="area" name="parent.id" value="${area.parent.id}" labelName="parent.name" labelValue="${area.parent.name}"
					title="区域" url="/sys/area/treeData" extId="${area.id}" cssClass="form-control m-s" allowClear="true"/></td>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>区域名称:</label></td>
		         <td  class="width-35" ><form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		      </tr>
		      <tr>
		         <td  class="width-15 active"><label class="pull-right"><font color="red">*</font>区域编码:</label></td>
		         <td class="width-35" ><form:input path="code" htmlEscape="false" maxlength="50" class="form-control required"/></td>
		         <td  class="width-15 active"><label class="pull-right">区域类型:</label></td>
		         <td  class="width-35" ><form:select path="type" class="form-control">
					<form:options items="${fns:getDictList('sys_area_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select></td>
		      </tr>
			  <tr>
		         <td  class="width-15 active"><label class="pull-right">备注:</label></td>
		         <td class="width-35" ><form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/></td>
		         <td  class="width-15 active"><label class="pull-right"></label></td>
		         <td  class="width-35" ></td>
		      </tr>
		</tbody>
		</table>
	</form:form>
</body>
</html>