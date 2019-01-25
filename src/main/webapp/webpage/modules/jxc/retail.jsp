<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>零售平台</title>
	<meta name="decorator" content="ani"/>
	<%@include file="retail.js" %>
	<link rel="stylesheet" href="${ctxStatic}/common/css/retail.css"/>
</head>
<body class="admin-retail row">
	<div class="col-md-6 left">
		<h3>商品列表</h3>
		<input type="text" id="product-name" class="form-control" placeholder="简码/名称" />
		
		<div class="product-area"></div>
	</div>
	<div class="col-md-6 right">
		<h3>订单详情</h3>
		<div class="order-content">
			<div class="row">
				<div class="col-md-3">名称</div>
				<div class="col-md-3">单价</div>
				<div class="col-md-3">数量</div>
				<div class="col-md-3">总价格</div>
			</div>
			<div class="order-list"></div>
		</div>
		<div class="order-footer">
			<button type="button" class="btn btn-danger btn-lg cancel-order">取消</button>
			<button type="button" class="btn btn-success btn-lg confirm-order">确认</button>
		</div>
	</div>
	<div class="retail-a">
		<a class="retail_home" href="/jeeplus/a/jxc/retail?fullScreen=true" target="_blank"><i class="fa fa-home" style="font-size:14px;"></i></a>
		<a href="#" onclick="toggleFullScreen()"><i class="fa fa-arrows-alt"></i></a>
	</div>
</body>
</html>