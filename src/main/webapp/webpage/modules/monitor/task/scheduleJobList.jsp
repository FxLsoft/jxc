<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>定时任务管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="scheduleJobList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">定时任务列表</h3>
	</div>
	<div class="panel-body">
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
		<form:form id="searchForm" modelAttribute="scheduleJob" class="form form-horizontal well clearfix">
		 <div class="col-xs-12 col-sm-6 col-md-4">
			<label class="label-item single-overflow pull-left" title="任务名：">任务名：</label>
			<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
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
			<shiro:hasPermission name="monitor:scheduleJob:add">
				<a id="add" class="btn btn-primary" href="${ctx}/monitor/scheduleJob/form" title="定时任务"><i class="glyphicon glyphicon-plus"></i> 新建</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="monitor:scheduleJob:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="monitor:scheduleJob:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="monitor:scheduleJob:stop">
				<button id="stop" class="btn btn-warning" disabled onclick="stop()">
	            	<i class="glyphicon"></i> 暂停
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="monitor:scheduleJob:resume">
				<button id="resume" class="btn btn-info" disabled onclick="resume()">
	            	<i class="glyphicon"></i> 启动
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="monitor:scheduleJob:startNow">
				<button id="startNow" class="btn btn-default" disabled onclick="startNow()">
	            	<i class="glyphicon"></i> 立即运行一次
	        	</button>
			</shiro:hasPermission>
		    </div>
		
	<!-- 表格 -->
	<table id="table"   data-toolbar="#toolbar"/>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
        <li data-item="edit"><a>编辑</a></li>
        <li data-item="delete"><a>删除</a></li>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>