<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/product");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
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
				<a class="panelButton" href="${ctx}/jxc/product"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="product" action="${ctx}/jxc/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>商品名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>商品简码：</label>
					<div class="col-sm-10">
						<form:input path="brevityCode" htmlEscape="false"    class="form-control required isEnglish"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">参考价格：</label>
					<div class="col-sm-10">
						<form:input path="price" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>供应商名称：</label>
					<div class="col-sm-10">
						<form:input path="supplierName" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>供应商电话：</label>
					<div class="col-sm-10">
						<form:input path="supplierPhone" htmlEscape="false"    class="form-control required isTel"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">供应商银行账号：</label>
					<div class="col-sm-10">
						<form:input path="supplierCardNo" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">供应商地址：</label>
					<div class="col-sm-10">
						<form:input path="supplierAddress" htmlEscape="false"    class="form-control "/>
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