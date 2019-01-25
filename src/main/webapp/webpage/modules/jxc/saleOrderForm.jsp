<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>销售单信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/saleOrder");
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
				<a class="panelButton" href="${ctx}/jxc/saleOrder"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="saleOrder" action="${ctx}/jxc/saleOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>单号：</label>
					<div class="col-sm-10">
						<form:input path="no" htmlEscape="false"  readonly="true"  class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>总金额：</label>
					<div class="col-sm-10">
						<form:input path="sum" htmlEscape="false" readonly="true" class="form-control required isFloatGteZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>类别：</label>
					<div class="col-sm-10">
						<form:select path="type" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('sale_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">销售明细：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
				<c:if test="${mode == 'add' || mode=='edit'}">
			<a class="btn btn-white btn-sm" onclick="addRow('#saleOrderDetailList', saleOrderDetailRowIdx, saleOrderDetailTpl);saleOrderDetailRowIdx = saleOrderDetailRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
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
						<th>生产日期</th>
						<th>保质期</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="saleOrderDetailList">
				</tbody>
			</table>
			<script type="text/template" id="saleOrderDetailTpl">//<!--
				<tr id="saleOrderDetailList{{idx}}">
					<td class="hide">
						<input id="saleOrderDetailList{{idx}}_id" name="saleOrderDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="saleOrderDetailList{{idx}}_delFlag" name="saleOrderDetailList[{{idx}}].delFlag" class="del_flag" type="hidden" value="0"/>
					</td>
					
					<td>
						<sys:gridselect url="${ctx}/jxc/queryProductByPage" id="saleOrderDetailList{{idx}}_product" name="saleOrderDetailList[{{idx}}].product.id" value="{{row.product.id}}" labelName="saleOrderDetailList{{idx}}.product.name" labelValue="{{row.product.name}}"
							 title="选择商品" cssClass="form-control  " fieldLabels="名称|简码|参考价|库存量|供应商" fieldKeys="name|brevityCode|price|count|supplierName" searchLabels="名称/简码" searchKeys="key" ></sys:gridselect>
					</td>
					
					
					<td>
						<input id="saleOrderDetailList{{idx}}_price" name="saleOrderDetailList[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required isFloatGtZero input-price"/>
					</td>
					
					
					<td>
						<input id="saleOrderDetailList{{idx}}_quantity" name="saleOrderDetailList[{{idx}}].quantity" type="text" value="{{row.quantity}}"    class="form-control required isIntGtZero input-quantity"/>
					</td>
					
					
					<td>
						<input id="saleOrderDetailList{{idx}}_unit" name="saleOrderDetailList[{{idx}}].unit" type="text" value="{{row.unit}}"    class="form-control"/>
					</td>
					
					
					<td>
						<input id="saleOrderDetailList{{idx}}_intro" name="saleOrderDetailList[{{idx}}].intro" type="text" value="{{row.intro}}"    class="form-control"/>
					</td>
					
					<td>
						<div class='input-group form_datetime' id="saleOrderDetailList{{idx}}_releaseDate">
		                    <input type='text'  name="saleOrderDetailList[{{idx}}].releaseDate" class="form-control"  value="{{row.releaseDate}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="saleOrderDetailList{{idx}}_expiryDate">
		                    <input type='text'  name="saleOrderDetailList[{{idx}}].expiryDate" class="form-control"  value="{{row.expiryDate}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					<td class="text-center" width="10">
						<c:if test="${mode == 'add' || mode=='edit'}">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#saleOrderDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
						</c:if>
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var saleOrderDetailRowIdx = 0, saleOrderDetailTpl = $("#saleOrderDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(saleOrder.saleOrderDetailList)};
					for (var i=0; i<data.length; i++){
						addRow('#saleOrderDetailList', saleOrderDetailRowIdx, saleOrderDetailTpl, data[i]);
						saleOrderDetailRowIdx = saleOrderDetailRowIdx + 1;
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