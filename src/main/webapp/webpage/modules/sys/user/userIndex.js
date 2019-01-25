<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
		$(document).ready(function() {
		//zTree初始化
			$.getJSON("${ctx}/sys/office/bootstrapTreeData",function(data){
				$('#jstree').treeview({
					data: data,
					levels: 5,
		            onNodeSelected: function(event, treeNode) {
		            	var id = treeNode.id == '0' ? '' :treeNode.id;
						if(treeNode.level == 1){//level=0 代表公司
							$("#companyId").val(id);
							$("#companyName").val(treeNode.text);
							$("#officeId").val("");
							$("#officeName").val("");
						}else{
							$("#companyId").val("");
							$("#companyName").val("");
							$("#officeId").val(id);
							$("#officeName").val(treeNode.text);
						}
						
						$('#table').bootstrapTable('refresh');
		            },
		         });
			});
			
						
			  //表格初始化
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
                  showPaginationSwitch: true,
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
	                url: "${ctx}/sys/user/list",
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
	                    	deleteAll(row.id);
	                    } 
	                },
	               
	                onClickRow: function(row, $el){
	                },
	                columns: [{
				        checkbox: true
				       
				    }, {
				        field: 'photo',
				        title: '头像',
				        formatter:function(value, row , index){
				        	if(value ==''){
				        		return '<img height="40px" src="${ctxStatic}/common/images/flat-avatar.png">';
				        	}else{
				        		return '<img   onclick="jp.showPic(\''+value+'\')"'+' height="40px" src="'+value+'">';
				        	}
				        	
				        }
				       
				    }, {
				        field: 'loginName',
				        title: '登录名',
				        sortable: true
				       
				    }, {
				        field: 'name',
				        title: '姓名',
				        sortable: true
				    }, {
				        field: 'phone',
				        title: '电话',
				        sortable: true
				    }, {
				        field: 'mobile',
				        title: '手机',
				        sortable: true
				    }, {
				        field: 'company.name',
				        title: '归属公司',
                        sortable: true,
						sortName: 'c.name'
				    }, {
				        field: 'office.name',
				        title: '归属部门',
                        sortable: true,
						sortName: 'o.name'
				    }]
				
				});
			
			  
			  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端
				  $('#table').bootstrapTable("toggleView");
				}
			  
			  $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
		                'check-all.bs.table uncheck-all.bs.table', function () {
		            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
		            $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
		        });
	
			$("#btnImport").click(function(){
					jp.open({
					    type: 2,
                        area: [500, 200],
                        auto: true,
                        title:"导入数据",
                        content: "${ctx}/tag/importExcel" ,
					    btn: ['下载模板','确定', '关闭'],

                        btn1: function(index, layero){
                            jp.downloadFile('${ctx}/sys/user/import/template');
                        },
                        btn2: function(index, layero){
                            var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                            iframeWin.contentWindow.importExcel('${ctx}/sys/user/import', function (data) {
                                if(data.success){
                                    jp.success(data.msg);
                                    refresh();
                                }else{
                                    jp.error(data.msg);
                                }
                                jp.close(index);
                            });//调用保存事件
                            return false;
                        },

                        btn3: function(index){
                            //  jp.close(index);
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
			  
			  $('#beginInDate').datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
			    });
			  $('#endInDate').datetimepicker({
				 	format: "YYYY-MM-DD HH:mm:ss"
		      });
			  
		});
		
		  function getIdSelections() {
		        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
		            return row.id
		        });
		    }
		  
		  function deleteAll(ids){
			    if(!ids){
			    	ids =  getIdSelections();
			    }
				jp.confirm('确认要删除选中用户吗？',  function(){
					    jp.loading();
                 	  	$.get("${ctx}/sys/user/deleteAll?ids=" +ids, function(data){
                 	  		if(data.success){
                 	  			$('#table').bootstrapTable('refresh');
                	  			jp.success(data.msg);
                	  		}else{
                	  			jp.error(data.msg);
                	  		}
                 	  	})
				})
		  }
		  function edit(id){
			  if(!id){
				  id = getIdSelections();
			  }
			  jp.openSaveDialog('编辑用户', "${ctx}/sys/user/form?id=" + id,'800px', '680px');
			  
		  }
		  function refresh() {
              $('#table').bootstrapTable('refresh');
          }
	</script>