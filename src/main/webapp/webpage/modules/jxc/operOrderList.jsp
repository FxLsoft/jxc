<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>单据信息管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@include file="/webpage/include/treeview.jsp" %>
	<%@include file="operOrderList.js" %>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">单据信息列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
			<form:form id="searchForm" modelAttribute="operOrder" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="编号：">编号：</label>
				<form:input path="no" htmlEscape="false" maxlength="64"  class=" form-control"/>
			</div>
			<c:if test="${from == 0}">
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="商家：">商家：</label>
										<sys:gridselect url="${ctx}/jxc/agency/data" id="agency" name="agency.id" value="${operOrder.agency.id}" labelName="agency.name" labelValue="${operOrder.agency.name}"
							 title="选择商家" cssClass="form-control " fieldLabels="名称|联系人|联系方式|车牌号|地址" fieldKeys="name|linkman|phone|plateNumber|address" searchLabels="名称|联系方式|车牌号" searchKeys="name|phone|plateNumber" ></sys:gridselect>
			</div>
			</c:if>
			<c:if test="${from == 4 || from == 5 }">
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="客户">客户：</label>
										<sys:gridselect url="${ctx}/jxc/customer/data" id="customer" name="customer.id" value="${operOrder.customer.id}" labelName="customer.name" labelValue="${operOrder.customer.name}"
							 title="选择客户" cssClass="form-control" fieldLabels="名称|联系方式|地址" fieldKeys="name|phone|address" searchLabels="名称|联系方式" searchKeys="name|phone" ></sys:gridselect>
			</div>
			</c:if>
			<div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="门店">门店：</label>
										<sys:gridselect url="${ctx}/jxc/store/data" id="store" name="store.id" value="${operOrder.store.id}" labelName="store.name" labelValue="${operOrder.store.name}"
							 title="选择门店" cssClass="form-control " fieldLabels="名称|区域|地址" fieldKeys="name|area|address" searchLabels="名称" searchKeys="name" ></sys:gridselect>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="单据状态（0：保存，1：提交，2：作废，3：完成）：">单据状态：</label>
				<form:select path="status"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-6">
				 <div class="form-group">
					<label class="label-item single-overflow pull-left" title="创建时间：">&nbsp;创建时间：</label>
					<div class="col-xs-12">
						   <div class="col-xs-12 col-sm-5">
					        	  <div class='input-group date' id='beginCreateDate' style="left: -10px;" >
					                   <input type='text'  name="beginCreateDate" class="form-control"  />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					             </div>	
					        </div>
					        <div class="col-xs-12 col-sm-1">
					        		~
					       	</div>
					        <div class="col-xs-12 col-sm-5">
					          	<div class='input-group date' id='endCreateDate' style="left: -10px;" >
					                   <input type='text'  name="endCreateDate" class="form-control" />
					                   <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
					           	</div>	
					        </div>
					</div>
				</div>
			</div>
		 <div class="col-xs-12 col-sm-6 col-md-2">
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
			<c:if test="${from != 3 }">
			<shiro:hasPermission name="jxc:operOrder:add">
				<button id="add" class="btn btn-primary" onclick="add()">
					<i class="glyphicon glyphicon-plus"></i> 新建
				</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="jxc:operOrder:edit">
			    <button id="edit" class="btn btn-success" disabled onclick="edit()">
	            	<i class="glyphicon glyphicon-edit"></i> 修改
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="jxc:operOrder:del">
				<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            	<i class="glyphicon glyphicon-remove"></i> 删除
	        	</button>
			</shiro:hasPermission>
			<shiro:hasPermission name="jxc:operOrder:import">
				<button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
			</shiro:hasPermission>
			</c:if>
			<shiro:hasPermission name="jxc:operOrder:export">
	        		<button id="export" class="btn btn-warning">
					<i class="fa fa-file-excel-o"></i> 导出
				</button>
			 </shiro:hasPermission>
	                 <shiro:hasPermission name="jxc:operOrder:view">
				<button id="view" class="btn btn-default" disabled onclick="view()">
					<i class="fa fa-search-plus"></i> 查看
				</button>
			</shiro:hasPermission>
				<button id="payAll" class="btn btn-success" disabled onclick="payAll()">
	            	<i class="glyphicon glyphicon-edit"></i> 付款
	        	</button>
		    </div>
		
	<!-- 表格 -->
	<table id="operOrderTable"   data-toolbar="#toolbar"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="jxc:operOrder:view">
        <li data-item="view"><a>查看</a></li>
        </shiro:hasPermission>
    	<shiro:hasPermission name="jxc:operOrder:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="jxc:operOrder:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>