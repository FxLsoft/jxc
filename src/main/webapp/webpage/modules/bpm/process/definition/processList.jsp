<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>流程管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="processList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">流程管理列表</h3>
	</div>
	<div class="panel-body">

	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
			<form:form id="searchForm"  class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="流程分类：">流程分类：</label>
				 <select id="category" name="category" class="form-control">
					 <option value="">全部分类</option>
					 <c:forEach items="${fns:getDictList('act_category')}" var="dict">
						 <option value="${dict.value}" ${dict.value==category?'selected':''}>${dict.label}</option>
					 </c:forEach>
				 </select>
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
			<shiro:hasPermission name="act:process:edit">
			    <button class="btn btn-primary" onclick="upload()">
	            	<i class="glyphicon glyphicon-plus"></i> 上传流程文件
	        	</button>
			</shiro:hasPermission>
		    </div>

	<!-- 表格 -->
	<table id="processTable"   data-toolbar="#toolbar"></table>

	</div>
	</div>
	</div>
</body>
</html>