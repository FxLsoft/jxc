<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>已结束的流程管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="processHistoryList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">已结束的流程列表</h3>
	</div>
	<div class="panel-body">

	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="procIns" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="流程实例ID：">流程实例ID：</label>
				 <input type="text" id="procInsId" name="procInsId" value="${procInsId}" class="form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="流程定义ID：">流程定义ID：</label>
				 <input type="text" id="procDefKey" name="procDefKey" value="${procDefKey}" class="form-control"/>
			</div>
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<div style="margin-top:26px">
			  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
			  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
			 </div>
	    </div>	
	</form:form>
	</div>
	</div>

	<!-- 工具栏 -->
	<div id="toolbar">
		<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
			<i class="glyphicon glyphicon-remove"></i> 删除
		</button>
	</div>
	<!-- 表格 -->
	<table id="procInsTable"   data-toolbar="#toolbar"></table>

	</div>
	</div>
	</div>
</body>
</html>