<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>电子秤销售管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/balanceSale");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});
			
	        $('#saleTime').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
		
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			$(list+idx).find(".form_datetime").each(function(){
				 $(this).datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
			    });
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/jxc/balanceSale"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="balanceSale" action="${ctx}/jxc/balanceSale/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">称号：</label>
					<div class="col-sm-10">
						<form:input path="saleId" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">销售编号：</label>
					<div class="col-sm-10">
						<form:input path="saleNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">称编号：</label>
					<div class="col-sm-10">
						<form:input path="balanceNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">销售时间：</label>
					<div class="col-sm-10">
						<div class='input-group form_datetime' id='saleTime'>
							<input type='text'  name="saleTime" class="form-control "  value="<fmt:formatDate value="${balanceSale.saleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">全局累计记录：</label>
					<div class="col-sm-10">
						<form:input path="wholeNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">当前累计记录：</label>
					<div class="col-sm-10">
						<form:input path="currentNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">营业员编号：</label>
					<div class="col-sm-10">
						<form:input path="sellerNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">买方账号：</label>
					<div class="col-sm-10">
						<form:input path="buyerCardNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">实收现金：</label>
					<div class="col-sm-10">
						<form:input path="realPay" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">现付：</label>
					<div class="col-sm-10">
						<form:input path="moneyPay" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">卡付：</label>
					<div class="col-sm-10">
						<form:input path="cardPay" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否异常销售：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isRegularSale" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">卖方账号：</label>
					<div class="col-sm-10">
						<form:input path="sellerCardNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">支付方式：</label>
					<div class="col-sm-10">
						<form:select path="payType" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_pay_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">电子秤销售记录详情：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>序号</th>
						<th>销售价</th>
						<th>商品编号</th>
						<th>组编号</th>
						<th>部门编号</th>
						<th>成本价</th>
						<th>重量</th>
						<th>单位</th>
						<th>是否退货</th>
						<th>税额</th>
						<th>原始单价</th>
						<th>实际单价</th>
						<th>商品名称</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="balanceSaleDetailList">
				</tbody>
			</table>
			<script type="text/template" id="balanceSaleDetailTpl">//<!--
				<tr id="balanceSaleDetailList{{idx}}">
					<td class="hide">
						<input id="balanceSaleDetailList{{idx}}_id" name="balanceSaleDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="balanceSaleDetailList{{idx}}_delFlag" name="balanceSaleDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					<td>
						<input id="balanceSaleDetailList{{idx}}_orderNo" name="balanceSaleDetailList[{{idx}}].orderNo" type="text" value="{{row.orderNo}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_salePrice" name="balanceSaleDetailList[{{idx}}].salePrice" type="text" value="{{row.salePrice}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_productNo" name="balanceSaleDetailList[{{idx}}].productNo" type="text" value="{{row.productNo}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_groupNo" name="balanceSaleDetailList[{{idx}}].groupNo" type="text" value="{{row.groupNo}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_officeNo" name="balanceSaleDetailList[{{idx}}].officeNo" type="text" value="{{row.officeNo}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_costPrice" name="balanceSaleDetailList[{{idx}}].costPrice" type="text" value="{{row.costPrice}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_amount" name="balanceSaleDetailList[{{idx}}].amount" type="text" value="{{row.amount}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_unit" name="balanceSaleDetailList[{{idx}}].unit" type="text" value="{{row.unit}}"    class="form-control "/>
					</td>
					
					
					<td>
						<c:forEach items="${fns:getDictList('yes_no')}" var="dict" varStatus="dictStatus">
							<span><input id="balanceSaleDetailList{{idx}}_isReturn${dictStatus.index}" name="balanceSaleDetailList[{{idx}}].isReturn" type="radio" class="i-checks" value="${dict.value}" data-value="{{row.isReturn}}"><label for="balanceSaleDetailList{{idx}}_isReturn${dictStatus.index}">${dict.label}</label></span>
						</c:forEach>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_tax" name="balanceSaleDetailList[{{idx}}].tax" type="text" value="{{row.tax}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_originPrice" name="balanceSaleDetailList[{{idx}}].originPrice" type="text" value="{{row.originPrice}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_realPrice" name="balanceSaleDetailList[{{idx}}].realPrice" type="text" value="{{row.realPrice}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="balanceSaleDetailList{{idx}}_productName" name="balanceSaleDetailList[{{idx}}].productName" type="text" value="{{row.productName}}"    class="form-control "/>
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var balanceSaleDetailRowIdx = 0, balanceSaleDetailTpl = $("#balanceSaleDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(balanceSale.balanceSaleDetailList)};
					for (var i=0; i<data.length; i++){
						addRow('#balanceSaleDetailList', balanceSaleDetailRowIdx, balanceSaleDetailTpl, data[i]);
						balanceSaleDetailRowIdx = balanceSaleDetailRowIdx + 1;
					}
					var mode = "${mode}";
					if (mode === 'view') {
						$('input').attr('disabled', true);
						$('button').attr('disabled', true);
						$('select').attr('disabled', true);
					}
				});
			</script>
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