<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#dataRuleTable').bootstrapTable({
		 
		  //请求方法
				method: 'post',
				//类型json
				dataType: "json",
				contentType: "application/x-www-form-urlencoded",
               //显示刷新按钮
               showRefresh: true,
               //显示切换手机试图按钮
               showToggle: true,
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
               url: "${ctx}/sys/dataRule/data",
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
               	var searchParam = {};
               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
               	searchParam.menuId = $("#menuId").val();
                   return searchParam;
               },
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                   if($el.data("item") == "edit"){
                       jp.go("${ctx}/sys/dataRule/form?id=" + row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该数据权限记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/sys/dataRule/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#dataRuleTable').bootstrapTable('refresh');
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
		        title: '数据规则名称',
		        sortable: true
		        ,formatter:function(value, row , index){
		        	return "<a href='${ctx}/sys/dataRule/form?id="+row.id+"'>"+value+"</a>";
		         }
		       
		    },{
		        field: 'className',
		        title: '实体类',
		        sortable: true
		       
		    }
			,{
		        field: 'field',
		        title: '字段',
		        sortable: true
		       
		    }
			,{
		        field: 'express',
		        title: '条件',
		        sortable: true,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('t_express'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'value',
		        title: '规则值',
		        sortable: true
		       
		    }
			,{
		        field: 'sqlSegment',
		        title: '自定义sql',
		        sortable: true
		       
		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true
		       
		    }, {
                field: 'operate',
                title: '操作',
                align: 'center',
                events: {
    		        'click .del': function (e, value, row, index) {

    		             jp.confirm('确认要删除该数据权限记录吗？', function(){
    		            	jp.loading();
    		            	jp.get("${ctx}/sys/dataRule/delete?id="+row.id, function(data){
    		        	  		if(data.success){
    		        	  			$('#dataRuleTable').bootstrapTable('refresh');
    		        	  			jp.success(data.msg);
    		        	  		}else{
    		        	  			jp.error(data.msg);
    		        	  		}
    		        	  	})
    		        	   
    		        	});
    		           
    		        
    		        	
    		        },
    		        
    		        'click .edit': function (e, value, row, index) {

    		        	jp.openSaveDialog('编辑数据规则', '${ctx}/sys/dataRule/form?id='+row.id+'&menuId='+$("#menuId").val(),'800px', '580px');
    		        }
    		    },
                formatter:  function operateFormatter(value, row, index) {
    		        return [
						'<a href="#" class="edit" title="编辑" >[编辑] </a>',
						'<a href="#" class="del" title="删除" >[删除] </a>'
    		        ].join('');
    		    }
            }
		     ]

		});
		
	
	
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#dataRuleTable').bootstrapTable("toggleView");
		}
	  
	  $('#dataRuleTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#dataRuleTable').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#dataRuleTable').bootstrapTable('getSelections').length!=1);
        });
		  
		    
		$("#dataRuleAddButton").click(function(){
			
			jp.openSaveDialog('添加数据规则', '${ctx}/sys/dataRule/form?menuId='+$("#menuId").val(),'800px', '580px');
			
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#dataRuleTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该数据权限记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/sys/dataRule/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#dataRuleTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function edit(){
      jp.go("${ctx}/sys/dataRule/form?id=" + getIdSelections());
  }

  function refresh() {
      $('#dataRuleTable').bootstrapTable('refresh');
  }
  
</script>