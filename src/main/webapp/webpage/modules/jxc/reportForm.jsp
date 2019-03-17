<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>报表信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
	function getUrlParam (name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) return unescape(r[2]); return null;
	}
	//单据来源（0：采购入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）
	var from = getUrlParam('from') || 'in';
		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/report?from=" + from);
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});
			if (from == 'in') {
				$(".saleInput").show();
				$(".purchaseInput").hide();
			} else {
				$(".saleInput").hide();
				$(".purchaseInput").show();
			}
			$(".inputPriceIn").on("input", function() {
				var totalPrice = 0;
				$(".inputPriceIn").each(function(index, el) {
					if (/^\d+(\.\d+)?$/.test(el.value)) {
						totalPrice += parseFloat(el.value);
					}
				})
				$(".inputTotalIn").val(totalPrice.toFixed(2));
			})
			$(".inputPriceOut").on("input", function() {
				var totalPrice = 0;
				$(".inputPriceOut").each(function(index, el) {
					if (/^\d+(\.\d+)?$/.test(el.value)) {
						totalPrice += parseFloat(el.value);
					}
				})
				$(".inputTotalOut").val(totalPrice.toFixed(2));
			})
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
				<a class="panelButton" href="${ctx}/jxc/report?from=${from}"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="report" action="${ctx}/jxc/report/save?from=${from}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">报表头：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="title" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">日期：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="date" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">门店：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/jxc/store/data" disabled="true" id="store" name="store.id" value="${report.store.id}" labelName="store.name" labelValue="${report.store.name}"
							 title="选择门店" cssClass="form-control " fieldLabels="名称|省市区|地址" fieldKeys="name|area|address" searchLabels="名称" searchKeys="name" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">销售应收：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="saleIn" htmlEscape="false"    class="form-control  number"/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">销售实收：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="saleRealIn" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">销售优惠：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="saleBenefit" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group purchaseInput">
					<label class="col-sm-2 control-label">采购应付：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="purchaseOut" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group purchaseInput">
					<label class="col-sm-2 control-label">采购实付：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="purchaseRealOut" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">退货应付：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="returnPay" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">退货实付：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="returnRealPay" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">欠款已收：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="oldDebtIn" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group purchaseInput">
					<label class="col-sm-2 control-label">欠款已付：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="oldDebtOut" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">电子秤销售：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="balanceIn" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">总金额：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="saleTotal" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group purchaseInput">
					<label class="col-sm-2 control-label">总金额：</label>
					<div class="col-sm-10">
						<form:input readonly="true" path="purchaseTotal" htmlEscape="false"    class="form-control  number "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">微信收款：</label>
					<div class="col-sm-10">
						<form:input path="wxPay" htmlEscape="false"    class="form-control  number inputPriceIn "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">支付宝收款：</label>
					<div class="col-sm-10">
						<form:input path="aliPay" htmlEscape="false"    class="form-control  number inputPriceIn "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">银行卡收款：</label>
					<div class="col-sm-10">
						<form:input path="bankPay" htmlEscape="false"    class="form-control  number inputPriceIn "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">现金收款：</label>
					<div class="col-sm-10">
						<form:input path="moenyPay" htmlEscape="false"    class="form-control  number inputPriceIn "/>
					</div>
				</div>
				<div class="form-group purchaseInput">
					<label class="col-sm-2 control-label">微信付款：</label>
					<div class="col-sm-10">
						<form:input path="wxPayOut" htmlEscape="false"    class="form-control  number inputPriceOut "/>
					</div>
				</div>
				<div class="form-group purchaseInput">
					<label class="col-sm-2 control-label">支付宝付款：</label>
					<div class="col-sm-10">
						<form:input path="aliPayOut" htmlEscape="false"    class="form-control  number inputPriceOut "/>
					</div>
				</div>
				<div class="form-group purchaseInput">
					<label class="col-sm-2 control-label">银行卡付款：</label>
					<div class="col-sm-10">
						<form:input path="bankPayOut" htmlEscape="false"    class="form-control  number inputPriceOut "/>
					</div>
				</div>
				<div class="form-group purchaseInput">
					<label class="col-sm-2 control-label">现金付款：</label>
					<div class="col-sm-10">
						<form:input path="moenyPayOut" htmlEscape="false"    class="form-control  number inputPriceOut "/>
					</div>
				</div>
				<div class="form-group saleInput">
					<label class="col-sm-2 control-label">实收总计：</label>
					<div class="col-sm-10">
						<form:input path="totalRealIn" readonly="true" htmlEscape="false"    class="form-control  number inputTotalIn "/>
					</div>
				</div>
				<div class="form-group purchaseInput">
					<label class="col-sm-2 control-label">实付总计：</label>
					<div class="col-sm-10">
						<form:input path="totalRealOut" readonly="true" htmlEscape="false"    class="form-control  number inputTotalOut "/>
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