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
		table {
		    border-collapse:collapse;
		}
		table,th, td {
		    border: 1px solid black;
		}
	</style>
	<div id="app" style="width: 100%;padding: 20px 60px;" v-show="!isLoading">
		<div v-if="vm.isShowAgency">
			<p style="text-align: center;">{{vm.agency.name}}</p>
			<p><div style="width: 49%;display: inline-block;">客户：{{vm.agency.linkman}}</div><div style="width: 49%;display: inline-block;">联系方式：{{vm.agency.phone}}</div></p>
			<p><div style="width: 49%;display: inline-block;">车牌：{{vm.agency.plateNumber}}</div><div style="width: 49%;display: inline-block;white-space: nowrap;">时间：{{date}}</div></p>
			<p>单号：{{vm.no}}</p>
		</div>
		<div v-else>
			<p style="text-align: center;">{{vm.store.officeName}}</p>
			<p><div style="width: 49%;display: inline-block;">客户：{{vm.customer.name}}</div> <div style="width: 49%;display: inline-block;">联系方式：{{vm.customer.phone}}</div></p>
			<p><div style="width: 49%;display: inline-block;">地址：{{vm.customer.address}}</div> <div style="width: 49%;display: inline-block;white-space: nowrap;">时间：{{date}}</div></p>
			<p>单号：{{vm.no}}</p>
		</div>
		<table style="font-size: 9px;font-weight: normal;width: 100%;margin-top:10px;">
			<tr>
				<th>名称</th><th>单位</th><th>数量</th><th style="text-align: center;">单价</th><th style="text-align: center;">金额</th>
            </tr>
            <tr v-for="(el, index) in vm.operOrderDetailList" :key="el.id">
	            <td>{{el.product.name}}</td>
	            <td style="text-align: center;">{{el.price.unit}}</td>
	            <td style="text-align: center;">{{el.amount}}</td>
	            <td style="text-align: center;">{{(el.operPrice || 0).toFixed(2)}}</td>
	            <td style="text-align: center;">{{(el.amount * el.operPrice).toFixed(2)}}</td>
            </tr>
            <tr>
            	<td colspan="3" style="padding:5px">合计数量：{{vm.amount || 0}}</td>
            	<td colspan="2" style="padding:5px">总金额：{{(vm.realPrice || 0).toFixed(2)}}</td>
            </tr>
		</table>
		<div v-if="!vm.isShowAgency" style="margin-top: 10px; white-space: nowrap; margin-bottom: 20px;">
			<p >{{vm.store.address}}</p>
			<p>{{vm.store.remarks}}</p>
		</div>
	</div>
	
</body>
</html>