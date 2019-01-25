<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>接口管理</title>
	<meta name="decorator" content="ani"/>
	<%@ include file="/webpage/include/bootstraptable.jsp"%>
	<script>
	$(document).ready(function() {
		$('#table').bootstrapTable({
		 
			  //请求方法
				method: 'post',
				//类型json
				dataType: "json",
				contentType: "application/x-www-form-urlencoded",
				//显示检索按钮
				showSearch: true,
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
               //显示 内容列下拉框
    	       showColumns: true,
    	       //显示到处按钮
    	       showExport: true,
    	       //显示切换分页按钮
    	       showPaginationSwitch: true,
    	       //最低显示2行
    	       minimumCountColumns: 2,
               //是否显示行间隔色
               striped: true,
               //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
               cache: false,    
               //是否显示分页（*）  
               pagination: true,   
                //排序方式 
               sortOrder: "asc",  
               //初始化加载第一页，默认第一页
               pageNumber:1,   
               //每页的记录行数（*）   
               pageSize: 10,  
               //可供选择的每页的行数（*）    
               pageList: [10, 25, 50, 100],
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/tools/testInterface/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = $("#searchForm").serializeJSON();
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                       jp.go("${ctx}/tools/testInterface/form?id=" + row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该测试接口记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/tools/testInterface/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#table').bootstrapTable('refresh');
                   	  			jp.success(data.msg);
                   	  		}else{
                   	  			jp.error(data.msg);
                   	  		}
                   	  	})
                   	   
                   	});
                      
                   } 
               },
				onShowSearch: function () {
					$("#search-collapse").slideToggle();
				},
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'name',
		        title: '接口名称',
		        sortable: true
		       
		    }
			,{
		        field: 'type',
		        title: '接口类型',
		        sortable: true
		       
		    }
			,{
		        field: 'url',
		        width:"200px",
		        title: '请求URL',
		        sortable: true
		       
		    }
			,{
		        field: 'body',
		        title: '请求body',
		        sortable: true
		       
		    }
			,{
		        field: 'successmsg',
		        title: '成功时返回消息',
		        sortable: true
		       
		    }
			,{
		        field: 'errormsg',
		        title: '失败时返回消息',
		        sortable: true
		       
		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true
		       
		    }, {
                field: 'operate',
                title: '操作',
                width:"200px",
                class:"text-nowrap",
                align: 'center',
                events: {
    		        'click .edit': function (e, value, row, index) {
                        jp.go("${ctx}/tools/testInterface/form?id=" + row.id);
    		        },
    		        'click .del': function (e, value, row, index) {
    		        	jp.confirm('确认要删除该测试接口记录吗？', function(){
    		    			jp.loading();  	
    		    			jp.get('${ctx}/tools/testInterface/delete?id='+row.id, function(data){
    		             	  		if(data.success){
    		             	  			$('#table').bootstrapTable('refresh');
    		             	  			jp.success(data.msg);
    		             	  		}else{
    		             	  			jp.error(data.msg);
    		             	  		}
    		             	  	})
    		              	   
    		    		})
    		        },
    		        'click .test': function (e, value, row, index) {
    		        	test(row.id);
    		        }
    		    },
                formatter:  function operateFormatter(value, row, index) {
                	   return [
       		        	'<div class="btn-group" role="group">',
   						<shiro:hasPermission name="tools:testInterface:edit"> 
   						'<div class="btn-group" role="group">',
       						'<button type="button" class="edit btn btn-warning">修改</button>',
       					'</div>',
   						</shiro:hasPermission>
   						<shiro:hasPermission name="tools:testInterface:del"> 
   						'<div class="btn-group" role="group">',
       						'<button type="button" class="del btn btn-danger">删除</button>',
       					'</div>',
   						</shiro:hasPermission>
       					'<div class="btn-group" role="group">',
       						'<button type="button" class="test btn btn-success">测试</button>',
       					'</div>',
   						'</div>'
       		        ].join('');
    		    }
            }
		    
		     ]
		
		});
		
		  
		 

	  
	  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
        });
		  
		$("#btnImport").click(function(){
			jp.open({
			    type: 1, 
			    area: [500, 300],
			    title:"导入数据",
			    content:$("#importBox").html() ,
			    btn: ['下载模板','确定', '关闭'],
				    btn1: function(index, layero){
					  window.location='${ctx}/tools/testInterface/import/template';
				  },
			    btn2: function(index, layero){
				        var inputForm =top.$("#importForm");
				        var top_iframe = top.getActiveTab().attr("name");//获取当前active的tab的iframe 
				        inputForm.attr("target",top_iframe);//表单提交成功后，从服务器返回的url在当前tab中展示
				        inputForm.onsubmit = function(){
				        	jp.loading('  正在导入，请稍等...');
				        }
				        inputForm.submit();
					    jp.close(index);
				  },
				 
				  btn3: function(index){ 
					  jp.close(index);
	    	       }
			}); 
		});
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#table').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $('#table').bootstrapTable('refresh');
		});
	});
		
  function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该测试接口记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/tools/testInterface/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#table').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
      jp.go("${ctx}/tools/testInterface/form?id=" + getIdSelections());
  }
  
		function test(id){
            if(!id){
            	id = getIdSelections();
            }
            top.openTab("${ctx}/tools/testInterface/test?id="+id,"接口测试", false);

        }
	</script>
	<style type="text/css"> 
	.AutoNewline 
	{ 
	  Word-break: break-all;/*必须*/ 
	} 
	</style>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">接口列表</h3>
	</div>
	<div class="panel-body">
	
	<!-- 搜索 -->
	<div id="search-collapse" class="collapse">
			<div class="accordion-inner">
			<form:form  id="searchForm" modelAttribute="testInterface" class="form form-horizontal well clearfix">
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="接口名称">接口名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="1024"  class=" form-control"/>
			</div>
			 <div class="col-xs-12 col-sm-6 col-md-4">
				<label class="label-item single-overflow pull-left" title="接口类型">接口类型：</label>
				<form:select path="type"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('interface_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
    	<shiro:hasPermission name="tools:testInterface:add">
    		<a id="add" class="btn btn-primary" href="${ctx}/tools/testInterface/form"><i class="glyphicon glyphicon-plus"></i> 新建</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="tools:testInterface:edit">
			<button id="edit" class="btn btn-success" disabled onclick="edit()">
	            <i class="glyphicon glyphicon-edit"></i> 修改
	        </button>
		</shiro:hasPermission>
		<shiro:hasPermission name="tools:testInterface:del">
			<button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
	            <i class="glyphicon glyphicon-remove"></i> 删除
	        </button>
		</shiro:hasPermission>
		 <button class="btn btn-info " onclick="test()"><i class="fa fa-check"></i> 测试</button>
    </div>
	<!-- 表格 -->
	<table id="table"   data-toolbar="#toolbar" style="word-break:break-all; word-wrap:break-all;"></table>

    <!-- context menu -->
    <ul id="context-menu" class="dropdown-menu">
    	<shiro:hasPermission name="tools:testInterface:edit">
        <li data-item="edit"><a>编辑</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="tools:testInterface:del">
        <li data-item="delete"><a>删除</a></li>
        </shiro:hasPermission>
        <li data-item="action"><a>取消</a></li>
    </ul>  
	</div>
	</div>
	</div>
</body>
</html>