<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>应收单信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/revenueOrder");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

	        $('#approveDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
	        $('#cancelDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
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
				<a class="panelButton" href="${ctx}/jxc/revenueOrder"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="revenueOrder" action="${ctx}/jxc/revenueOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>单号：</label>
					<div class="col-sm-10">
						<form:input path="no" htmlEscape="false" readonly="true" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>销售单号：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/jxc/saleOrder/data" id="saleOrder" name="saleOrder.id" value="${revenueOrder.saleOrder.id}" labelName="saleOrder.no" labelValue="${revenueOrder.saleOrder.no}"
							 title="选择销售单号" cssClass="form-control required" fieldLabels="单号|金额|更新时间" fieldKeys="no|sum|updateDate" searchLabels="单号" searchKeys="no" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>金额：</label>
					<div class="col-sm-10">
						<form:input path="receiveMoney" htmlEscape="false"    class="form-control required isFloatGtZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核状态：</label>
					<div class="col-sm-10">
						<form:select path="status" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">审核时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='approveDate'>
							<input type='text'  name="approveDate" class="form-control "  value="<fmt:formatDate value="${revenueOrder.approveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">作废时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='cancelDate'>
							<input type='text'  name="cancelDate" class="form-control "  value="<fmt:formatDate value="${revenueOrder.cancelDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
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