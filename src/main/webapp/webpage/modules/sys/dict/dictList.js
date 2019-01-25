<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
$(document).ready(function() {
	$('#table').bootstrapTable({
			  //请求方法
				method: 'post',
				//类型json
				dataType: "json",
				contentType: "application/x-www-form-urlencoded",
                 //是否显示行间隔色
                striped: true,
                //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
                cache: false,
				//显示检索按钮
		        showSearch: true,
				//显示刷新按钮
				showRefresh: true,
				//显示切换手机试图按钮
				showToggle: true,
				//显示 内容列下拉框
				showColumns: true,
				//显示切换分页按钮
                //是否显示分页（*）  
                pagination: true, 
                
                pageList: [10, 25, 50, 100],
                //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
                url: "${ctx}/sys/dict/data",
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
                    if($el.data("item") == "edit"){
                    	edit(row.id);
                    } else if($el.data("item") == "delete"){
                        del(row.id);
                       
                    } 
                },
                
                columns: [{
			        checkbox: true
			       
			    }, {
			        field: 'type',
			        title: '类型',
			        sortable:true
			    }, {
			        field: 'description',
			        title: '描述',
			        sortable:true
			    }, {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
        		        'click .view': function (e, value, row, index) {
        		        	jp.openViewDialog('查看字典', '${ctx}/sys/dict/form?id=' + row.id,'800px', '500px');
        		        },
        		        'click .edit': function (e, value, row, index) {
        		        	jp.openSaveDialog('编辑字典', '${ctx}/sys/dict/form?id=' + row.id,'800px', '500px');
        		        },
        		        'click .del': function (e, value, row, index) {
        		        	del(row.id);
        		        },
        		        'click .setDictValue': function (e, value, row, index) {
        		        	
        					$("#left").attr("class", "col-sm-6");
        					setTimeout(function(){
        						$("#right").fadeIn(500);
        					},500)
        					$("#dictTypeLabel").html(row.type);
        					$("#dictTypeId").val(row.id);
        					$('#dictValueTable').bootstrapTable("refresh",{query:{dictTypeId:row.id}})
        		        }
        		    },
                    formatter:  function operateFormatter(value, row, index) {
        		        return [
        		        	<shiro:hasPermission name="sys:dict:view">
    						'<a href="#" class="view" title="查看" ><i class="fa fa-eye"></i> </a>',
    						</shiro:hasPermission>
    						<shiro:hasPermission name="sys:dict:edit"> 
    							'<a href="#" class="edit" title="修改"><i class="fa fa-edit"></i> </a>',
    						</shiro:hasPermission>
    						<shiro:hasPermission name="sys:dict:del"> 
    						    '<a href="#" class="del" title="删除"><i class="fa fa-trash"></i> </a>',
    						</shiro:hasPermission>
    						<shiro:hasPermission name="sys:dict:edit"> 
    							'<a href="#" class="setDictValue"  title="管理键值"><i class="fa fa-cog"></i> </a>'
    						</shiro:hasPermission>
        		        ].join('');
        		    }
                }]
			
			});
		
		  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端，默认关闭tab
			  $('#table').bootstrapTable("toggleView");
			}
		  
		  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
	                'check-all.bs.table uncheck-all.bs.table', function () {
	            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
	            $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
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
  
  function add(){
	  jp.openSaveDialog('新建字典', '${ctx}/sys/dict/form','800px', '500px')
  }
  
  function edit(id){
	  if(!id){
			id = getIdSelections();
		}
	  jp.openSaveDialog('编辑字典', "${ctx}/sys/dict/form?id=" + id,'800px', '500px')
	  
  }
  function del(ids){
		if(!ids){
			ids = getIdSelections();
		}
		jp.loading();
		jp.confirm('确认要删除选中字典吗？',  function(){
     	  	$.get("${ctx}/sys/dict/deleteAll?ids=" + ids, function(data){
     	  		if(data.success){
     	  			$('#table').bootstrapTable('refresh');
    	  			jp.success(data.msg);
    	  		}else{
    	  			jp.error(data.msg);
    	  		}
     	  	})
		})
  }

  function refresh(){
      $('#table').bootstrapTable('refresh');
  }
function refreshDictValue(){
    $('#dictValueTable').bootstrapTable("refresh");
}
  
  
$(document).ready(function() {
	var $dictValueTable =	$('#dictValueTable').bootstrapTable({
			  //请求方法
				method: 'post',
				//类型json
				dataType: "json",
				contentType: "application/x-www-form-urlencoded",
                 //是否显示行间隔色
                striped: true,
                //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）     
                cache: false,    
                //是否显示分页（*）  
                pagination: false,   
                //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据  
                url: "${ctx}/sys/dict/getDictValue",
                //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
                //queryParamsType:'',   
                ////查询参数,每次调用是会带上这个参数，可自定义                         
                queryParams : function(params) {
                    return {dictTypeId:$("#dictTypeId").val()};
                },
                //分页方式：client客户端分页，server服务端分页（*）
                sidePagination: "server",
                columns: [{
			        field: 'label',
			        title: '标签'
			    }, {
			        field: 'value',
			        title: '键值'
			    },{
			    	field: 'sort',
			        title: '排序'
			       
			    }, {
                    field: 'operate',
                    title: '操作',
                    align: 'center',
                    events: {
                    	 'click .edit': function (e, value, row, index) {
            		        	
                    		 jp.openSaveDialog('编辑键值', '${ctx}/sys/dict/dictValueForm?dictTypeId=' + $("#dictTypeId").val()+"&dictValueId="+row.id,'800px', '500px');
            		        },
        		        'click .del': function (e, value, row, index) {
        		        	
        		        	jp.confirm('确认要删除键值吗？',function(){
        		        		jp.loading();
        		        		$.get('${ctx}/sys/dict/deleteDictValue?dictValueId='+row.id+'&dictTypeId=' + $("#dictTypeId").val(), function(data){
  	                    	  		if(data.success){
  	                    	  			$('#dictValueTable').bootstrapTable("refresh");
  	                    	  			jp.success(data.msg);
  	                    	  		}else{
  	                    	  			jp.error(data.msg);
  	                    	  		}
  	                    	  	})
        		        	});
        		        }
        		    },
                    formatter:  function operateFormatter(value, row, index) {
        		        return [
        		        	<shiro:hasPermission name="sys:dict:edit">
    						'<a href="#" class="edit" title="编辑" >[编辑] </a>',
    						</shiro:hasPermission>
        		        	<shiro:hasPermission name="sys:dict:edit">
    						'<a href="#" class="del" title="删除" >[删除] </a>'
    						</shiro:hasPermission>
        		        ].join('');
        		    }
                }]
			
			});
		
		  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端
			  $('#dictValueTable').bootstrapTable("toggleView");
			}
		  
		  $("#dictValueButton").click(function(){
				
				jp.openSaveDialog('添加键值', '${ctx}/sys/dict/dictValueForm?dictTypeId=' + $("#dictTypeId").val(),'800px', '500px');
			});
		  
		  });

		
	</script>