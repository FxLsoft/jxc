<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>库存管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/storage");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

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
				<a class="panelButton" href="${ctx}/jxc/storage"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="storage" action="${ctx}/jxc/storage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>商品：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/jxc/product/data" id="product" name="product.id" value="${storage.product.id}" labelName="product.name" labelValue="${storage.product.name}"
							 title="选择商品" cssClass="form-control required" fieldLabels="名称|简码|是否计重|计重编号" fieldKeys="name|brevityCode|isWeight|weightNo" searchLabels="名称/简码" searchKeys="searchKey" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>价格：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/jxc/price/data" id="price" name="price.id" value="${storage.price.id}" labelName="price.costPrice" labelValue="${storage.price.costPrice}"
							 title="选择价格" cssClass="form-control required" fieldLabels="单位|换算比例|进价|预售价|是否是基本单位" fieldKeys="unit|ratio|costPrice|advancePrice|isBasic" searchLabels="" searchKeys="" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>数量：</label>
					<div class="col-sm-10">
						<form:input path="amount" htmlEscape="false"    class="form-control required isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
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