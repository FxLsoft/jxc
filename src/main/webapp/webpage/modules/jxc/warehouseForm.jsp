<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>仓库信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/warehouse");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

	        $('#releaseDate').datetimepicker({
				 format: "YYYY-MM-DD"
		    });
	        $('#expiryDate').datetimepicker({
				 format: "YYYY-MM-DD"
		    });
	        var mode = "${mode}";
			if (mode === 'view') {
				$('input').attr('disabled', true);
				$('button').attr('disabled', true);
			}
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
				<a class="panelButton" href="${ctx}/jxc/warehouse"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="warehouse" action="${ctx}/jxc/warehouse/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>商品：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/jxc/product/data" id="product" name="product.id" value="${warehouse.product.id}" labelName="product.name" labelValue="${warehouse.product.name}"
							 title="选择商品" cssClass="form-control required" fieldLabels="名称|简码|参考价|供应商" fieldKeys="name|brevityCode|price|supplierName" searchLabels="名称|简码" searchKeys="name|brevityCode" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>入库单价：</label>
					<div class="col-sm-10">
						<form:input path="price" htmlEscape="false"    class="form-control required isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>数量：</label>
					<div class="col-sm-10">
						<form:input path="quantity" htmlEscape="false"    class="form-control required isIntGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单位：</label>
					<div class="col-sm-10">
						<form:input path="unit" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">规格说明：</label>
					<div class="col-sm-10">
						<form:input path="intro" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否可零售：</label>
					<div class="col-sm-10">
						<form:select path="isRetail" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">生产日期：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='releaseDate'>
							<input type='text'  name="releaseDate" class="form-control "  value="<fmt:formatDate value="${warehouse.releaseDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">保质期：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='expiryDate'>
							<input type='text'  name="expiryDate" class="form-control "  value="<fmt:formatDate value="${warehouse.expiryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
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