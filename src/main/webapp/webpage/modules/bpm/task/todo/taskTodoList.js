<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#actTable').bootstrapTable({
		 
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
               url: "${ctx}/act/task/todo/data",
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

				onShowSearch: function () {
					$("#search-collapse").slideToggle();
				},
               columns: [{
		        checkbox: true
		       
		    }

			,{
		        field: 'vars.title',
		        title: '实例标题'
		       
		    },{
                       field: 'procDef.name',
                       title: '流程名称'

                   }
			,{
		        field: 'task.name',
		        title: '当前环节'
		       
		    }

			,{
		        field: 'vars.userName',
		        title: '流程发起人'
		       
		    }
			,{
		        field: 'task.createTime',
		        title: '创建时间',
			   formatter:function (value, row, index) {
				   return jp.dateFormat(value, "yyyy-MM-dd hh:mm:ss");
			   }
		       
		    }, {
                       field: 'operate',
                       title: '操作',
                       align: 'center',
                       formatter: function operateFormatter(value, row, index) {
						   var arra = new Array();
                       	  if(row['task.assignee'] == undefined || row['task.assignee'] == ""){
                       	  	arra.push('<a href="javascript:claim(\''+row['task.id']+'\');">【签收任务】</a>');
						  }else{
                       	  	arra.push('<a href="${ctx}/act/task/form?taskId='+row['task.id']+'&taskName='+encodeURI(row['task.name'])+'&taskDefKey='+row['task.taskDefinitionKey']+'&procInsId='+row['task.processInstanceId']+'&procDefId='+row['task.processDefinitionId']+'&status='+row.status+'">【任务办理】</a>');

						  };
                           <shiro:hasPermission name="act:process:edit">
                               if(row['task.executionId'] == undefined || row['task.executionId'] == ""){
                       	  			arra.push( '<a href="${ctx}/act/task/deleteTask?taskId='+row['task.id']+'&reason=" onclick="return jp.prompt(\'删除原因\',this.href);">【删除任务】</a>')
                               };

                           </shiro:hasPermission>
                           arra.push('<a  href="javascript:jp.openViewDialog(\'跟踪\',\'${ctx}/act/task/trace/photo/'+row['task.processDefinitionId']+'/'+row['task.executionId']+'\',\'1000px\', \'600px\')">【跟踪】</a>');

							return arra.join("");

                       }
                   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#actTable').bootstrapTable("toggleView");
		}
	  
	  $('#actTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#actTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#actTable').bootstrapTable('getSelections').length!=1);
        });
		  

	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#actTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#actTable').bootstrapTable('refresh');
		});
		
		$('#beginDate').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#endDate').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		
	});
		
  function getIdSelections() {
        return $.map($("#actTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该发起任务记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/task/todo/act/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#actTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
function claim(taskId) {
    $.post('${ctx}/act/task/claim' ,{taskId: taskId}, function(data) {
        if (data == 'true'){
            jp.success('签收完成');
            $('#actTable').bootstrapTable('refresh');
        }else{
            jp.error('签收失败');
        }
    });
}

</script>