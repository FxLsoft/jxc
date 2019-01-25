<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#testValidationTable').bootstrapTable({
		 
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
               url: "${ctx}/test/validation/testValidation/data",
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
                   		edit(row.id);
                   }else if($el.data("item") == "view"){
                       view(row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该测试校验记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/test/validation/testValidation/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#testValidationTable').bootstrapTable('refresh');
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
               	onShowSearch: function () {
			$("#search-collapse").slideToggle();
		},
               columns: [{
		        checkbox: true
		       
		    }
			,{
		        field: 'num',
		        title: '浮点数字',
		        sortable: true,
		        sortName: 'num'
		        ,formatter:function(value, row , index){
		        	value = jp.unescapeHTML(value);
				   <c:choose>
					   <c:when test="${fns:hasPermission('test:validation:testValidation:edit')}">
					      return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:when test="${fns:hasPermission('test:validation:testValidation:view')}">
					      return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:otherwise>
					      return value;
				      </c:otherwise>
				   </c:choose>
		         }
		       
		    }
			,{
		        field: 'num2',
		        title: '整数',
		        sortable: true,
		        sortName: 'num2'
		       
		    }
			,{
		        field: 'str',
		        title: '字符串',
		        sortable: true,
		        sortName: 'str'
		       
		    }
			,{
		        field: 'email',
		        title: '邮件',
		        sortable: true,
		        sortName: 'email'
		       
		    }
			,{
		        field: 'url',
		        title: '网址',
		        sortable: true,
		        sortName: 'url'
		       
		    }
			,{
		        field: 'newDate',
		        title: '日期',
		        sortable: true,
		        sortName: 'newDate'
		       
		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true,
		        sortName: 'remarks'
		       
		    }
			,{
		        field: 'c1',
		        title: '浮点数小于等于0',
		        sortable: true,
		        sortName: 'c1'
		       
		    }
			,{
		        field: 'c2',
		        title: '身份证号码',
		        sortable: true,
		        sortName: 'c2'
		       
		    }
			,{
		        field: 'c3',
		        title: 'QQ号码',
		        sortable: true,
		        sortName: 'c3'
		       
		    }
			,{
		        field: 'c4',
		        title: '手机号码',
		        sortable: true,
		        sortName: 'c4'
		       
		    }
			,{
		        field: 'c5',
		        title: '中英文数字下划线',
		        sortable: true,
		        sortName: 'c5'
		       
		    }
			,{
		        field: 'c6',
		        title: '合法字符(a-z A-Z 0-9)',
		        sortable: true,
		        sortName: 'c6'
		       
		    }
			,{
		        field: 'en',
		        title: '英语',
		        sortable: true,
		        sortName: 'en'
		       
		    }
			,{
		        field: 'zn',
		        title: '汉子',
		        sortable: true,
		        sortName: 'zn'
		       
		    }
			,{
		        field: 'enzn',
		        title: '汉英字符',
		        sortable: true,
		        sortName: 'enzn'
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#testValidationTable').bootstrapTable("toggleView");
		}
	  
	  $('#testValidationTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#testValidationTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#testValidationTable').bootstrapTable('getSelections').length!=1);
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
					 jp.downloadFile('${ctx}/test/validation/testValidation/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/test/validation/testValidation/import', function (data) {
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
					  jp.close(index);
	    	       }
			}); 
		});
		    
		
	  $("#export").click(function(){//导出Excel文件
			jp.downloadFile('${ctx}/test/validation/testValidation/export');
	  });

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#testValidationTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#testValidationTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#testValidationTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该测试校验记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/test/validation/testValidation/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#testValidationTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function refresh(){
  	$('#testValidationTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/test/validation/testValidation/form/add");
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  jp.go("${ctx}/test/validation/testValidation/form/edit?id=" + id);
  }

  function view(id) {
      if(id == undefined){
          id = getIdSelections();
      }
      jp.go("${ctx}/test/validation/testValidation/form/view?id=" + id);
  }
  
</script>