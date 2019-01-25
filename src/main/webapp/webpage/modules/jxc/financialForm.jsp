<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>报表信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/financial");
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
				<a class="panelButton" href="${ctx}/jxc/financial"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="financial" action="${ctx}/jxc/financial/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>报表类型：</label>
					<div class="col-sm-10">
						<form:select path="type" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('financial_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">采购总金额：</label>
					<div class="col-sm-10">
						<form:input path="purchaseAmount" htmlEscape="false"    class="form-control  isIntLteZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">入库总金额：</label>
					<div class="col-sm-10">
						<form:input path="stockAmount" htmlEscape="false"    class="form-control  isIntLteZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">批发总金额：</label>
					<div class="col-sm-10">
						<form:input path="wholesaleAmount" htmlEscape="false"    class="form-control  isIntLteZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">零售总金额：</label>
					<div class="col-sm-10">
						<form:input path="retailAmount" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退货总金额：</label>
					<div class="col-sm-10">
						<form:input path="returnAmount" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">批发已收金额：</label>
					<div class="col-sm-10">
						<form:input path="wholesaleGatherAmount" htmlEscape="false"    class="form-control  isIntLteZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">零售已收金额：</label>
					<div class="col-sm-10">
						<form:input path="retailGatherAmount" htmlEscape="false"    class="form-control  isIntLteZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">已收金额：</label>
					<div class="col-sm-10">
						<form:input path="gatherAmount" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">入库已付金额：</label>
					<div class="col-sm-10">
						<form:input path="stockBillAmount" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退货已付金额：</label>
					<div class="col-sm-10">
						<form:input path="returnBillAmount" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">已付金额：</label>
					<div class="col-sm-10">
						<form:input path="billAmount" htmlEscape="false"    class="form-control  isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:input path="remarks" htmlEscape="false"    class="form-control "/>
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