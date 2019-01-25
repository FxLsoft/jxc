<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>机构列表</title>
	<meta name="decorator" content="ani"/>
	<%@include file="officeList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">机构列表</h3>
			</div>
			
			<div class="panel-body">
			<!-- 工具栏 -->
			<div class="row">
			<div class="col-sm-12">
				<div class="pull-left treetable-bar">
					<shiro:hasPermission name="sys:office:add">
						<a id="add" class="btn btn-primary"  onclick="jp.openSaveDialog('新增机构', '${ctx}/sys/office/form','800px', '600px')" ><i class="glyphicon glyphicon-plus"></i> 新建</a>
					</shiro:hasPermission>
			       <button class="btn btn-default" data-toggle="tooltip" data-placement="left" onclick="refresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
				
				</div>
			</div>
			</div>
		
			<table id="treeTable" class="table table-hover">
				<thead>
					<tr>
						<th>机构名称</th>
						<th>归属区域</th>
						<th>机构编码</th>
						<th>机构类型</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
			<br/>
			</div>
		</div>
	</div>
</body>
</html>