<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="menuList.js" %>
	<%@include file="menu-dataRuleList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">菜单列表	  </h3>
			</div>
			<div class="panel-body">
			
			 <div class="row">
				<div id="left" class="col-sm-12">
				<!-- 工具栏 -->
			    <div id="toolbar" class="treetable-bar">
			    	<shiro:hasPermission name="sys:menu:add">
						<a id="add" class="btn btn-primary" onclick="jp.openSaveDialog('新建菜单', '${ctx}/sys/menu/form','800px', '500px')"><i class="glyphicon glyphicon-plus"></i> 新建菜单</a><!-- 增加按钮 -->
					</shiro:hasPermission>
					<button class="btn btn-default" data-toggle="tooltip" data-placement="left" onclick="refreshMenu()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
			    </div><!-- 工具栏结束 -->
	
				<table id="treeTable" class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>链接</th>
							<th>排序</th>
							<th>显示/隐藏</th>
							<th>权限标识</th>
							<th>操作</th>
						</tr>
					</thead>
				    <tbody>  
				    </tbody>  
				</table> 
	 		</div>
	 		
	 		 
		    <div  id="right" class="panel panel-default col-sm-6" style="display:none">
				<div class="panel-heading">
					<h3 class="panel-title"><label>数据规则列表，所属菜单: </label><font id="menuLabel"></font><input type="hidden" id="menuId"/></h3>
				</div>
				<div class="panel-body">
				 <div id="dataRuleToolbar">
					<button id="dataRuleAddButton"  class="btn btn-outline btn-primary" title="添加规则"><i class="fa fa-plus"></i> 添加规则</button>
				</div>
				 <!-- 表格 -->
			    <table id="dataRuleTable"
			           data-toolbar="#dataRuleToolbar"
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