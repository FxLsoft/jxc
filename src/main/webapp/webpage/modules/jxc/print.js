<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	var data = ${fns:toJson(data)};
	var mode = '${mode}';
	$("#title").text('${title}');
	// 单据
	if (mode === 'dj') {
		var printAbstract = $("#printAbstract").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var printHeader =  $("#printHeader").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var printBody = $("#printBody").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		data.sum = (data.sum || 0).toFixed(2);
		data.status = jp.getDictLabel(${fns:toJson(fns:getDictList('order_status'))}, data.status, "已保存");
		var abstract = Mustache.render(printAbstract, data);
		var header = Mustache.render(printHeader);
		$(".print-area").append(abstract);
		$(".print-area").append(header);
		var orderDetail = data.purchaseOrderDetailList || data.stockOrderDetailList || data.returnOrderDetailList || data.saleOrderDetailList;
		for (var i = 0; i < orderDetail.length; i++) {
			orderDetail[i].sum = (orderDetail[i].price * orderDetail[i].quantity).toFixed(2);
			orderDetail[i].price = (orderDetail[i].price || 0).toFixed(2);
			$("#table-body").append(Mustache.render(printBody, orderDetail[i]));
		}
	} else {
		data.sum = (data.payMoney || data.receiveMoney || data.returnMoney).toFixed(2);
		data.status = jp.getDictLabel(${fns:toJson(fns:getDictList('order_status'))}, data.status, "已保存");
		var printSj = $("#printSj").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var abstract = Mustache.render(printSj, data);
		$(".print-area").append(abstract);
	}
	
})

function addRow(list, idx, tpl, row){
		$(list).append(Mustache.render(tpl, {
			idx: idx, delBtn: true, row: row
		}));
	}
</script>

<script type="text/template" id="printAbstract">//<!--
	<div>
		<span>单号:{{no}}</span>&nbsp;&nbsp;&nbsp;
		<span>总金额:{{sum}}</span>&nbsp;&nbsp;&nbsp;
		<span>时间:{{updateDate}}</span>&nbsp;&nbsp;&nbsp;
		<span>订单状态:{{status}}</span>
	</div>
//-->
</script>
<script type="text/template" id="printHeader">//<!--
<table border="0" cellspacing="0" width="100%" style="border-collapse: collapse;font-size=12px;font-weight:normal;margin-top:10px;">
	<thead style="font-size: 12px;line-height: 30px;">
		<tr>
			<th style="border: none;">商品</th>
			<th style="border: none;">单价</th>
			<th style="border: none;">数量</th>
			<th style="border: none;">单位</th>
			<th style="border: none;">规格说明</th>
			<th style="border: none;">总计</th>
		</tr>
	</thead>
	<tbody id="table-body"></tbody>
</table>//-->
</script>
<script type="text/template" id="printBody">//<!--
			<tr style="font-size: 12px;line-height: 28px;">
				<td>
					{{product.name}}
				</td>
				<td>
					{{price}}
				</td>
				<td>
					{{quantity}}
				</td>
				<td>
					{{unit}}
				</td>
				<td>
					{{desc}}
				</td>
				<td>
					{{sum}}
				</td>
			</tr>//-->
</script>

<script type="text/template" id="printSj">//<!--
<div>
	<h4><label>单号:</label>{{no}}</h4>
	<h4><label>金额:</label>{{sum}}</h4>
	<h4><label>时间:</label>{{updateDate}}</h4>
	<h4><label>审核状态:</label>{{status}}</h4>
</div>
//-->
</script>