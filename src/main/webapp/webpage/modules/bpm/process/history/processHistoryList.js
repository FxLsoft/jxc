<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#procInsTable').bootstrapTable({
		 
		  //请求方法
				method: 'post',
				//类型json
				dataType: "json",
				contentType: "application/x-www-form-urlencoded",
		       //显示检索按钮
		       showSearch:true,
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
               url: "${ctx}/act/process/historyListData/",
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
		       
		    },{
               	field: 'processVariables.title',
				   title:'实例名称'
			   },{
               	field: 'processDefinitionName',
				title:'流程名称'

			   }
			,{
		        field: 'processVariables.userName',
		        title: '流程发起人'
		       
		    }
			,{
		        field: 'startTime',
		        title: '流程启动时间',
			   formatter:function (value, row, index) {
				   return jp.dateFormat(value,"yyyy-MM-dd hh:mm:ss");
			   }
		       
		    }
			,{
		        field: 'endTime',
		        title: '流程结束时间',
			   formatter:function (value, row, index) {
				   return jp.dateFormat(value,"yyyy-MM-dd hh:mm:ss");
			   }
		       
		    }
			,{
		        field: 'deleteReason',
		        title: '流程状态',
			   formatter:function (value, row, index) {
		        	if(value == null){
		        		return "[正常结束]";
					}else if(value=="用户撤销"){
                        return "<font color='red'>[用户撤销]</font>";
					}else {
                        return "<font color='red'>[流程作废] 原因："+value+"</font>";
					}
			   }
		       
		    }, {
                       field: 'operate',
                       title: '操作',
                       align: 'left',
					    width: '200px',
                       events: {
                           'click .history': function (e, value, row, index) {
                               jp.openViewDialog('查看流程历史', '${ctx}/act/task/trace/photo/'+row.processDefinitionId+"/" + row.id, '1000px', '600px')

                           },
                           'click .del': function (e, value, row, index) {
                               jp.confirm("确定要删除历史流程吗？", function (data) {
                                   jp.loading();
                                   jp.get("${ctx}/act/process/history/deleteProcIns?procInsId="+row.processInstanceId,function (result) {
                                       if(result.success){
                                           $('#procInsTable').bootstrapTable('refresh');
                                           jp.success(result.msg);
                                       }else{
                                           jp.error(result.msg);
                                       }
                                   })

                               })
                           }
                       },
					   formatter: function operateFormatter(value, row, index) {
						   return [
                               '<a class="history" href="#" >【跟踪】</a>',
                               '<a href="${ctx}/act/task/formDetail?'+'&procInsId='+row.processInstanceId+'&procDefId='+row.processDefinitionId+'">【详情】</a>',
                               '<a class="del" href="#" >【删除】</a>',
						   ].join('');
					   }
                   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#procInsTable').bootstrapTable("toggleView");
		}
	  
	  $('#procInsTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#procInsTable').bootstrapTable('getSelections').length);
        });


	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#procInsTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#procInsTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#procInsTable").bootstrapTable('getSelections'), function (row) {
            return row.processInstanceId
        });
    }

function deleteAll() {

    jp.confirm('确认要删除选中的流程记录吗？', function(){
        jp.loading();
        jp.get("${ctx}/act/process/history/deleteAllProcIns?procInsIds=" + getIdSelections(), function(data){
            if(data.success){
                $('#procInsTable').bootstrapTable('refresh');
                jp.success(data.msg);
            }else{
                jp.error(data.msg);
            }
        })

    })

	}
</script>