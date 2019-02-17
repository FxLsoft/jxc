<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>打印</title>
	<meta name="decorator" content="vue"/>
	<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/vue"></script>
	<%@include file="print.js" %>
	
</head>
<body class="admin-print" style="font-size: 9px;font-weight: normal; margin: 0; padding: 0; display: flex; justify-content: center;">
	<style type="text/css">
		p {
			margin: 0;
			padding: 0;
		}
	</style>
	<div id="app" style="width: 210px;">
		<p style="text-align: center;">{{vm.agency.name}}</p>
		<p>单号：{{vm.no}}</p>
		<p>联系方式：{{vm.agency.phone}}</p>
		<p>客户：{{vm.agency.linkman}}</p>
		<p>车号：{{vm.agency.plateNumber}}</p>
		<p>时间：{{date}}</p>
		<table style="font-size: 9px;font-weight: normal;width: 100%;">
			<tr>
				<th>名称</th><th>单位</th><th>数量</th><th style="text-align: center;">单价</th><th style="text-align: center;">金额</th>
            </tr>
            <tr v-for="(el, index) in vm.operOrderDetailList" :key="el.id">
	            <td>{{el.product.name}}</td>
	            <td>{{el.price.unit}}</td>
	            <td>{{el.amount}}</td>
	            <td style="text-align: right;">{{(el.operPrice || 0).toFixed(2)}}</td>
	            <td style="text-align: right;">{{(el.amount * el.operPrice).toFixed(2)}}</td>
            </tr>
            <tr>
            	<td colspan="5" style="padding-top:10px">总计(<span>人民币</span>)：{{(vm.realPrice || 0).toFixed(2)}}</td>
            </tr>
		</table>
		<p style="margin-top: 10px; transform: scale(0.8); white-space: nowrap; margin-bottom: 20px;">请顾客当面点清货物，出市场外概不负责。</p>
	</div>
	
</body>
</html>