<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#actModelTable').bootstrapTable({

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
               url: "${ctx}/act/model/data",
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

				onShowSearch: function () {
					$("#search-collapse").slideToggle();
				},
               //分页方式：client客户端分页，server服务端分页（*）
               sidePagination: "server",
               contextMenuTrigger:"right",//pc端 按右键弹出菜单
               contextMenuTriggerMobile:"press",//手机端 弹出菜单，click：单击， press：长按。
               contextMenu: '#context-menu',
               onContextMenuItem: function(row, $el){
                  if($el.data("item") == "delete"){
                       del(row.id);

                   } 
               },
              
               onClickRow: function(row, $el){
               },
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'category',
		        title: '流程分类',
		        sortable: false,
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('act_category'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'key',
		        title: '模型标识',
		        sortable: false
		       
		    }
			,{
		        field: 'name',
		        title: '模型名称',
		        sortable: true
		       
		    }
			,{
		        field: 'version',
		        title: '版本号',
		        sortable: false
		       
		    }
			,{
		        field: 'createTime',
		        title: '创建时间',
		        sortable: false,
			    formatter:function (value, row, index) {
				   return jp.dateFormat(value,"yyyy-MM-dd hh:mm:ss");
			    }
		       
		    }
			,{
		        field: 'lastUpdateTime',
		        title: '最后更新时间',
		        sortable: false,
			   formatter:function (value, row, index) {
				   return jp.dateFormat(value,"yyyy-MM-dd hh:mm:ss");
			   }
		       
		    }, {
                       field: 'operate',
                       title: '操作',
                       align: 'center',
                       events: {
                           'click .deploy': function (e, value, row, index) {
								   jp.confirm('确认要部署该模型吗？',function () {
                                       jp.get("${ctx}/act/model/deploy?id="+row.id,function (data) {
                                           if(data.success){
                                               jp.success(data.msg);
                                               $('#actModelTable').bootstrapTable('refresh');

                                           }else{
                                               jp.error(data.msg);
                                           }

                                       })
                                   })

                           },
                           'click .del': function (e, value, row, index) {
                           		del(row.id);
                           }
                       },
                       formatter:  function operateFormatter(value, row, index) {
                           return [
                               <shiro:hasPermission name="act:model:edit">
								   '<a class="" href="${pageContext.request.contextPath}/act/rest/modeler.html?modelId='+row.id+'" target="_blank">【在线设计】</a>',
							   </shiro:hasPermission>
							   <shiro:hasPermission name="act:model:deploy">
								   '<a  class="deploy" href="#">【部署】</a>',
						       </shiro:hasPermission>
							   <shiro:hasPermission name="act:model:export">
								   '<a class="" href="${ctx}/act/model/export?id='+row.id+'" target="_blank">【导出】</a>',
							   </shiro:hasPermission>
							   <shiro:hasPermission name="act:model:del">
								   '<a href="#" class="del">【删除】</a>'
                               </shiro:hasPermission>
                           ].join('');
                       }
                   }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#actModelTable').bootstrapTable("toggleView");
		}
	  
	  $('#actModelTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#actModelTable').bootstrapTable('getSelections').length);
        });

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#actModelTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#actModelTable').bootstrapTable('refresh');
		});
		
		
	});

Date.prototype.format = function(format) {

};
  function getIdSelections() {
        return $.map($("#actModelTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该模型记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/act/model/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#actModelTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
	function del(id) {
        jp.confirm('确认要删除该模型记录吗？', function() {
            jp.loading();
            jp.get("${ctx}/act/model/delete?id=" + id, function (data) {
                if (data.success) {
                    $('#actModelTable').bootstrapTable('refresh');
                    jp.success(data.msg);
                } else {
                    jp.error(data.msg);
                }
            })
        })
	}
   function add(){
	  jp.openSaveDialog('新增模型', "${ctx}/act/model/create",'800px', '500px');
  };

  function refresh() {
      $('#actModelTable').bootstrapTable('refresh');
  }

</script>