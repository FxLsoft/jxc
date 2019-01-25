<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>日志管理</title>
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<%@ include file="logList.js"%>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">日志列表</h3>
	</div>
	
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
		<div class="accordion-inner">
				<form id="searchForm" class="form form-horizontal well clearfix" >
				    <div class="col-xs-12 col-sm-6 col-md-4">
				    	<label class="label-item single-overflow pull-left" title="操作菜单：">操作菜单：</label>
				   		<input id="title" name="title" type="text" maxlength="50" class="form-control"/>
				    </div>
				    <div class="col-xs-12 col-sm-6 col-md-4">
				    	<label class="label-item single-overflow pull-left" title="用户id：">操作用户：</label>
				   		<input id="createBy.name" name="createBy.name" type="text" maxlength="50" class="form-control"/>
				    </div>
				    <div class="col-xs-12 col-sm-6 col-md-4">
				    	<label class="label-item single-overflow pull-left" title="URI：">URI：</label>
				   		<input id="requestUri" name="requestUri" type="text" maxlength="50"  class="form-control"/>
				    </div>
				    <div class="col-xs-12 col-sm-6 col-md-4">
					    	<label class="label-item single-overflow pull-left" title="日期范围：">日期范围：</label>
					    		<div class="col-xs-12">
								        <div class="col-xs-12 col-sm-5">
								        	<div class='input-group date' id='beginDate' style="left: -10px;" >
								                   <input type='text'  name="beginDate" class="form-control"  />
								                   <span class="input-group-addon">
								                       <span class="glyphicon glyphicon-calendar"></span>
								                   </span>
								             </div>	
								        </div>
								        <div class="col-xs-12 col-sm-1">
								        		~
								       	</div>
								        <div class="col-xs-12 col-sm-5">
									          <div class='input-group date' id='endDate' style="left: -10px;" >
									                   <input type='text'  name="endDate" class="form-control" />
									                   <span class="input-group-addon">
									                       <span class="glyphicon glyphicon-calendar"></span>
									                   </span>
									           </div>	
								        </div>
								</div>
					   </div>
					   <div class="col-xs-12 col-sm-6 col-md-4">
				   			 <div class="form-group">
								<label class="label-item single-overflow pull-left"  title="只查询异常信息：">只查询异常信息：</label>
								<div class="col-xs-12">
									<input id="exception" name="exception" class="i-checks form-control" type="checkbox" value="1"/>
								</div>
							</div>
					   </div>
				   
				    <div class="col-xs-12 col-sm-6 col-md-4">
						 <div style="margin-top:26px">
						  <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
						  <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
						</div>
				    </div>
				</form>
			</div>
	</div>
	
	<!-- 工具栏 -->
    <div id="toolbar">
    	<shiro:hasPermission name="sys:log:del">
	        <button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            <i class="glyphicon glyphicon-remove"></i> 删除
	        </button>
			<button class="accordion-toggle btn btn-danger"  onclick="empty()"  title="清空"><i class="fa fa-trash"></i> 清空</button>
		</shiro:hasPermission>
    </div>
    <!-- 表格 -->
    <table id="table"
           data-toolbar="#toolbar"
           data-show-export="true"
           data-show-pagination-switch="true"
           data-detail-formatter="detailFormatter"
           data-minimum-count-columns="2"
           data-id-field="id">
    </table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
        <li data-item="delete"><a>删除</a></li>
        <li data-item="action1"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>