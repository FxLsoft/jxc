<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>入库单信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/stockOrder");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
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
					 format: "YYYY-MM-DD"
			    });
			});
			$('.input-price').off('blur').on('blur', sumPrice);
			$('.input-quantity').off('blur').on('blur', sumPrice);
			sumPrice();
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
			$(obj).parent().parent().remove();
			$('.input-price').off('blur').on('blur', sumPrice);
			$('.input-quantity').off('blur').on('blur', sumPrice);
			sumPrice();
		}
		function sumPrice() {
			var regex = /^\d+(\.\d+)?$/;
			var ip = $('.input-price');
			var iq = $('.input-quantity');
			var id = $('.del_flag');
			var sum = 0;
			for (var i = 0; i < ip.length; i++) {
				if (regex.test(ip[i].value) && regex.test(iq[i].value) && id[i].value == 0) {
					sum += ip[i].value * iq[i].value;
				}
			}
			$("#sum").val(sum.toFixed(2))
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
				<a class="panelButton" href="${ctx}/jxc/stockOrder"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="stockOrder" action="${ctx}/jxc/stockOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>单号：</label>
					<div class="col-sm-10">
						<form:input path="no" htmlEscape="false" readonly="true" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>采购单号：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/jxc/purchaseOrder/data" id="purchaseOrder" name="purchaseOrder.id" value="${stockOrder.purchaseOrder.id}" labelName="purchaseOrder.no" labelValue="${stockOrder.purchaseOrder.no}"
							 title="选择采购单号" cssClass="form-control required" fieldLabels="单号|金额" fieldKeys="no|sum" searchLabels="单号" searchKeys="no" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>总金额：</label>
					<div class="col-sm-10">
						<form:input path="sum" htmlEscape="false" readonly="true" class="form-control required isFloatGteZero"/>
					</div>
				</div>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">入库详情：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
				<c:if test="${mode == 'add' || mode=='edit'}">
			<a class="btn btn-white btn-sm" onclick="addRow('#stockOrderDetailList', stockOrderDetailRowIdx, stockOrderDetailTpl);stockOrderDetailRowIdx = stockOrderDetailRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			</c:if>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th><font color="red">*</font>商品</th>
						<th><font color="red">*</font>单价</th>
						<th><font color="red">*</font>数量</th>
						<th>单位</th>
						<th>规格说明</th>
						<th width="85">是否可零售</th>
						<th>生产日期</th>
						<th>保质期</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="stockOrderDetailList">
				</tbody>
			</table>
			<script type="text/template" id="stockOrderDetailTpl">//<!--
				<tr id="stockOrderDetailList{{idx}}">
					<td class="hide">
						<input id="stockOrderDetailList{{idx}}_id" name="stockOrderDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="stockOrderDetailList{{idx}}_delFlag" name="stockOrderDetailList[{{idx}}].delFlag" class="del_flag" type="hidden" value="0"/>
					</td>
					
					<td>
						<sys:gridselect url="${ctx}/jxc/product/data" id="stockOrderDetailList{{idx}}_product" name="stockOrderDetailList[{{idx}}].product.id" value="{{row.product.id}}" labelName="stockOrderDetailList{{idx}}.product.name" labelValue="{{row.product.name}}"
							 title="选择商品" cssClass="form-control  required" fieldLabels="名称|简码|参考价|供应商" fieldKeys="name|brevityCode|price|supplierName" searchLabels="名称/简码" searchKeys="name" ></sys:gridselect>
					</td>
					
					
					<td>
						<input id="stockOrderDetailList{{idx}}_price" name="stockOrderDetailList[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required isFloatGtZero input-price"/>
					</td>
					
					
					<td>
						<input id="stockOrderDetailList{{idx}}_quantity" name="stockOrderDetailList[{{idx}}].quantity" type="text" value="{{row.quantity}}"    class="form-control required isIntGtZero input-quantity"/>
					</td>
					
					
					<td>
						<input id="stockOrderDetailList{{idx}}_unit" name="stockOrderDetailList[{{idx}}].unit" type="text" value="{{row.unit}}"    class="form-control "/>
					</td>
					
					
					<td>
						<input id="stockOrderDetailList{{idx}}_intro" name="stockOrderDetailList[{{idx}}].intro" type="text" value="{{row.intro}}"    class="form-control "/>
					</td>
					
					
					<td>
						<select id="stockOrderDetailList{{idx}}_isRetail" name="stockOrderDetailList[{{idx}}].isRetail" data-value="{{row.isRetail}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="stockOrderDetailList{{idx}}_releaseDate">
		                    <input type='text'  name="stockOrderDetailList[{{idx}}].releaseDate" class="form-control"  value="{{row.releaseDate}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="stockOrderDetailList{{idx}}_expiryDate">
		                    <input type='text'  name="stockOrderDetailList[{{idx}}].expiryDate" class="form-control"  value="{{row.expiryDate}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					<td class="text-center" width="10">
						<c:if test="${mode == 'add' || mode=='edit'}">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#stockOrderDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
						</c:if>
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var stockOrderDetailRowIdx = 0, stockOrderDetailTpl = $("#stockOrderDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(stockOrder.stockOrderDetailList)};
					for (var i=0; i<data.length; i++){
						addRow('#stockOrderDetailList', stockOrderDetailRowIdx, stockOrderDetailTpl, data[i]);
						stockOrderDetailRowIdx = stockOrderDetailRowIdx + 1;
					}
					var mode = "${mode}";
					if (mode === 'view') {
						$('input').attr('disabled', true);
						$('button').attr('disabled', true);
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