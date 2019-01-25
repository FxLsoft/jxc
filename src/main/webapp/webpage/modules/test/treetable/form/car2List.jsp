<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>车辆管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="carKind2TreeList.js" %>
	<%@include file="car2List.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">车辆列表</h3>
	</div>
	<div class="panel-body">
		<div class="row">
				<div class="col-sm-4 col-md-3" >
					<div class="form-group">
						<div class="row">
							<div class="col-sm-10" >
								<div class="input-search">
									<button type="submit" class="input-search-btn">
										<i class="fa fa-search" aria-hidden="true"></i></button>
									<input   id="search_q" type="text" class="form-control input-sm" name="" placeholder="查找...">

								</div>
							</div>
							<div class="col-sm-2" >
								<button  class="btn btn-default btn-sm"  onclick="jp.openSaveDialog('新建车系', '${ctx}/test/treetable/form/carKind2/form','800px', '500px')">
									<i class="fa fa-plus"></i>
								</button>
							</div>
						</div>
					</div>
					<div id="carKind2jsTree" style="overflow-x:auto; border:0px;"></div>
				</div>
				<div  class="col-sm-8 col-md-9 animated fadeInRight">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="car2" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="车系：">车系：</label>
				<sys:treeselect id="kind" name="kind.id" value="${car2.kind.id}" labelName="kind.name" labelValue="${car2.kind.name}"
					title="车系" url="/test/treetable/form/carKind2/treeData" extId="${car2.id}" cssClass="form-control " allowClear="true"/>
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
			<shiro:hasPermission name="test:treetable:form:car2:add">
				<button id="add" class="btn btn-primary" onclick="add()">
					<i class="glyphicon glyphicon-plus"></i> 新建
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="test:treetable:form:car2:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="test:treetable:form:car2:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="test:treetable:form:car2:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="test:treetable:form:car2:export">
	        		<button id="export" class="btn btn-warning">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			 </shiro:hasPermission>
	                 <shiro:hasPermission name="test:treetable:form:car2:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission>
		    </div>
		
	<!-- 表格 -->
	<table id="car2Table"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="test:treetable:form:car2:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="test:treetable:form:car2:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="test:treetable:form:car2:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
	</div>
</div>
</body>
</html>