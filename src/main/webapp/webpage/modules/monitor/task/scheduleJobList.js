<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#table').bootstrapTable({
		 
		  //请求方法
				method: 'post',
				//类型json
				dataType: "json",
				contentType: "application/x-www-form-urlencoded",
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
				//显示检索按钮
		       showSearch: true,
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
				onShowSearch: function () {
					$("#search-collapse").slideToggle();
				},
               //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
               url: "${ctx}/monitor/scheduleJob/data",
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
                       jp.go("${ctx}/monitor/scheduleJob/form?id=" + row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该定时任务记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/monitor/scheduleJob/delete?id="+row.id, function(data){
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
              
               onClickRow: function(row, $el){
               },
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'name',
		        title: '任务名',
		        sortable: true
		       
		    }
			,{
		        field: 'group',
		        title: '任务组',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('schedule_task_group'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'cronExpression',
		        title: '定时规则',
		        sortable: true
		       
		    }
			,{
		        field: 'status',
		        title: '启用状态',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'isInfo',
		        title: '通知用户',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('schedule_task_info'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'className',
		        title: '任务类',
		        sortable: true
		       
		    }
			,{
		        field: 'description',
		        title: '描述',
		        sortable: true
		       
		    }, {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: {
    		        'click .resume': function (e, value, row, index) {
    		        	resume(row.id);
    		        },
    		        'click .stop': function (e, value, row, index) {
    		        	stop(row.id);
    		        }
    		    },
                formatter:  function operateFormatter(value, row, index) {
    		        return [
    		        	<shiro:hasPermission name="monitor:scheduleJob:resume">
						'<a href="#" class="resume" title="启动" >【启动】</a>',
						</shiro:hasPermission>
						<shiro:hasPermission name="monitor:scheduleJob:stop"> 
							'<a href="#" class="stop" title="暂停">【暂停】 </a>'
						</shiro:hasPermission>
    		        ].join('');
    		    }
		    }]
	});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#table').bootstrapTable("toggleView");
		}
	  
	  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
            $('#edit,#stop,#resume,#startNow').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
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

		jp.confirm('确认要删除该定时任务记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/monitor/scheduleJob/deleteAll?ids=" + getIdSelections(), function(data){
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
      jp.go("${ctx}/monitor/scheduleJob/form?id=" + getIdSelections());
  }
  
		   
//暂停
  function stop(id){
	  if(!id){
		  id = getIdSelections();
	  }
	  jp.confirm('确定要暂停任务？', function(){
			jp.loading();  	
			jp.get("${ctx}/monitor/scheduleJob/stop?id=" + id, function(data){
       	  		if(data.success){
       	  			$('#table').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})
        	   
		})
  }

  //恢复
  function resume(id){
	  if(!id){
		  id = getIdSelections();
	  }
	  jp.confirm('确定要恢复任务？', function(){
			jp.loading();  	
			jp.get("${ctx}/monitor/scheduleJob/resume?id=" + id, function(data){
       	  		if(data.success){
       	  			$('#table').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})
        	   
		})
  }

  //立即运行一次
  function startNow(){
	  jp.confirm('确定要立即运行一次该任务？', function(){
			jp.loading();  	
			jp.get("${ctx}/monitor/scheduleJob/startNow?id=" + getIdSelections(), function(data){
       	  		if(data.success){
       	  			$('#table').bootstrapTable('refresh');
       	  			jp.success(data.msg);
       	  		}else{
       	  			jp.error(data.msg);
       	  		}
       	  	})
        	   
		})
  	};
</script>