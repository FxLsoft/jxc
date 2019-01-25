<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@ include file="roleList.js"%>
	<%@ include file="role-userList.js"%>
	
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">角色列表</h3>
	</div>
	<div class="panel-body">
	  <div class="row">
			<div id="left" class="col-sm-12">
			<!-- 搜索 -->
			<div id="search-collapse" class="collapse">
				<div class="accordion-inner">
					<form:form id="searchForm" modelAttribute="role"  class="form form-horizontal well clearfix" >
						<div class="col-xs-12 col-sm-6 col-md-4">
							<label class="label-item single-overflow pull-left" title="角色名称：">角色名称：</label>
							<form:input path="name"  htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
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
		    	<shiro:hasPermission name="sys:role:add">
		    		<a id="add" class="btn btn-primary"  onclick="add()"><i class="glyphicon glyphicon-plus"></i> 新建</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:role:edit">
					<button id="edit" class="btn btn-success" disabled onclick="edit()">
			            <i class="glyphicon glyphicon-edit"></i> 修改
			        </button>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:role:del">
					<button id="remove" class="btn btn-danger" disabled onclick="del()">
			            <i class="glyphicon glyphicon-remove"></i> 删除
			        </button>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:role:assign">
					<button id="auth" class="accordion-toggle btn btn-default" disabled onclick="auth()">
						<i class="fa fa-cog"></i>  权限设置
					</button>
				</shiro:hasPermission>
		    </div><!lo-- 工具栏结束 -->
	
		    <!-- 表格 -->
		    <table id="table"
		           data-toolbar="#toolbar"
		           data-id-field="id">
		    </table>
			
		    <!-- context menu -->
		    <ul id="context-menu" class="dropdown-menu">
		        <li data-item="edit"><a>编辑</a></li>
		        <li data-item="delete"><a>删除</a></li>
		        <li data-item="action1"><a>取消</a></li>
		    </ul> 
		    </div>
		    
		    <div  id="right" class="panel panel-default col-sm-6" style="display:none">
				<div class="panel-heading">
					<h3 class="panel-title"><label>用户列表，所属角色: </label><font id="roleLabel"></font><input type="hidden" id="roleId"/></h3>
				</div>
				<div class="panel-body">
				 <div id="userToolbar">
					<button id="assignButton"  class="btn btn-outline btn-primary" title="添加人员"><i class="fa fa-user-plus"></i> 添加人员</button>
				</div>
				 <!-- 表格 -->
			    <table id="userTable"
			           data-toolbar="#userToolbar"
			           data-id-field="id">
			    </table>
			   </div>
			</div>
			
		   </div>
		   </div>
		   </div>
	</div>
</body>
</html>