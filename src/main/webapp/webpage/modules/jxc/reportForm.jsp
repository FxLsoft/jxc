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
					jp.go("${ctx}/jxc/report");
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
				<a class="panelButton" href="${ctx}/jxc/report"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="report" action="${ctx}/jxc/report/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">报表头：</label>
					<div class="col-sm-10">
						<form:input path="title" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">日期：</label>
					<div class="col-sm-10">
						<form:input path="date" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">门店：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/jxc/store/data" id="store" name="store.id" value="${report.store.id}" labelName="store.name" labelValue="${report.store.name}"
							 title="选择门店" cssClass="form-control " fieldLabels="名称|省市区|地址" fieldKeys="name|area|address" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">销售应收：</label>
					<div class="col-sm-10">
						<form:input path="saleIn" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">销售实收：</label>
					<div class="col-sm-10">
						<form:input path="saleRealIn" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">销售优惠：</label>
					<div class="col-sm-10">
						<form:input path="saleBenefit" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">采购应付：</label>
					<div class="col-sm-10">
						<form:input path="purchaseOut" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">采购实付：</label>
					<div class="col-sm-10">
						<form:input path="purchaseRealOut" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退货应付：</label>
					<div class="col-sm-10">
						<form:input path="returnPay" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退货实付：</label>
					<div class="col-sm-10">
						<form:input path="returnRealPay" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">欠款已收：</label>
					<div class="col-sm-10">
						<form:input path="oldDebtIn" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">欠款已付：</label>
					<div class="col-sm-10">
						<form:input path="oldDebtOut" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电子秤销售：</label>
					<div class="col-sm-10">
						<form:input path="balanceIn" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">微信收款：</label>
					<div class="col-sm-10">
						<form:input path="wxPay" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付宝收款：</label>
					<div class="col-sm-10">
						<form:input path="aliPay" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">银行卡收款：</label>
					<div class="col-sm-10">
						<form:input path="bankPay" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">现付：</label>
					<div class="col-sm-10">
						<form:input path="moenyPay" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">实收总金额：</label>
					<div class="col-sm-10">
						<form:input path="totalIn" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">实收总计：</label>
					<div class="col-sm-10">
						<form:input path="totalRealIn" htmlEscape="false"    class="form-control  number"/>
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