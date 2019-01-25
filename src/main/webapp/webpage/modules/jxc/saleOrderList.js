<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	window.printDialog = function(id, type) {
		jp.openPrintDialog('打印', "${ctx}/jxc/print?id=" + id + "&type=xs",'800px', '500px');
	}
	window.checkOrder = function(id, status) {
		var message = status == 1 ? '确认通过此单？' : '确认要作废此单？';
		jp.confirm(message, function(){
			jp.get("${ctx}/jxc/check/order?id="+id + "&status=" + status + "&type=xs", function(data){
	   	  		if(data.success){
	   	  			$('#saleOrderTable').bootstrapTable('refresh');
	   	  			jp.success(data.msg);
	   	  		}else{
	   	  			jp.error(data.msg);
	   	  		}
	   	  	})
		})
	}
	$('#saleOrderTable').bootstrapTable({
		 
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
    	       //显示详情按钮
    	       detailView: true,
    	       	//显示详细内容函数
	           detailFormatter: "detailFormatter",
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
               url: "${ctx}/jxc/saleOrder/data",
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
                        jp.confirm('确认要删除该销售单信息记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/jxc/saleOrder/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#saleOrderTable').bootstrapTable('refresh');
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
		        field: 'no',
		        title: '单号',
		        sortable: true,
		        sortName: 'no'
		        ,formatter:function(value, row , index){
		        	value = jp.unescapeHTML(value);
				   <c:choose>
					   <c:when test="${fns:hasPermission('jxc:saleOrder:edit')}">
					      return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:when test="${fns:hasPermission('jxc:saleOrder:view')}">
					      return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:otherwise>
					      return value;
				      </c:otherwise>
				   </c:choose>
		         }
		       
		    }
			,{
		        field: 'sum',
		        title: '总金额',
		        sortable: true,
		        sortName: 'sum',
		        formatter: function (value, row, index) {
		        	return (value || 0).toFixed(2);
		        }
		       
		    }
			,{
		        field: 'type',
		        title: '类别',
		        sortable: true,
		        sortName: 'type',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('sale_type'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'status',
		        title: '订单状态',
		        sortable: true,
		        sortName: 'status',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('order_status'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'approveDate',
		        title: '提交时间',
		        sortable: true,
		        sortName: 'approveDate'
		       
		    }
			,{
		        field: 'createBy.name',
		        title: '创建者',
		        sortable: true,
		        sortName: 'createBy.name'
		       
		    }
			,{
		        field: 'createDate',
		        title: '创建时间',
		        sortable: true,
		        sortName: 'createDate'
		       
		    }
			,{
		        field: 'updateBy.name',
		        title: '更新者',
		        sortable: true,
		        sortName: 'updateBy.name'
		       
		    }
			,{
		        field: 'updateDate',
		        title: '更新时间',
		        sortable: true,
		        sortName: 'updateDate'
		       
		    }
			,{
		        title: '操作',
		        sortable: false,
		        formatter:function(value, row, index){
		        	var btn = [];
		        	btn.push('<button class="btn btn-primary btn-sm" onclick="printDialog(\'' +row.id+ '\')">打印</button>');
		        	if (row.status == 0 && (row.createBy.id == '${fns:getUser().id}')) {
		        		btn.push('<button class="btn btn-success btn-sm" onclick="checkOrder(\'' +row.id+ '\', 1)">提交</button>');
		        		btn.push('<button class="btn btn-warning btn-sm" onclick="checkOrder(\'' +row.id+ '\', 2)">作废</button>');
		        	} else if (row.status == 1) {
		        		<c:if test="${fns:hasPermission('jxc:order:check')}">
		        			btn.push('<button class="btn btn-warning btn-sm" onclick="checkOrder(\'' +row.id+ '\', 2)">作废</button>');
					    </c:if>
					}
		        	return btn.join();
		        }
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#saleOrderTable').bootstrapTable("toggleView");
		}
	  
	  $('#saleOrderTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#saleOrderTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#saleOrderTable').bootstrapTable('getSelections').length!=1);
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
					  jp.downloadFile('${ctx}/jxc/saleOrder/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/jxc/saleOrder/import', function (data) {
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
	        var searchParam = $("#searchForm").serializeJSON();
	        searchParam.pageNo = 1;
	        searchParam.pageSize = -1;
            var sortName = $('#saleOrderTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#saleOrderTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/jxc/saleOrder/export?'+values);
	  })
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#saleOrderTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#saleOrderTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#saleOrderTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  function isCanEdit(id) {
	  var data = $("#saleOrderTable").bootstrapTable('getData');
	  for (var i = 0; i < data.length; i++) {
		  if (data[i].id == id && data[i].status == 0) {
			  return true;
		  }
	  }
	  return false;
  }
  
  function deleteAll(){

		jp.confirm('确认要删除该销售单信息记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/jxc/saleOrder/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#saleOrderTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }

     //刷新列表
  function refresh(){
  	$('#saleOrderTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/jxc/saleOrder/form/add");
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  if (!isCanEdit(id)) {
		  jp.go("${ctx}/jxc/saleOrder/form/view?id=" + id);
	  } else {
		  jp.go("${ctx}/jxc/saleOrder/form/edit?id=" + id);
	  }
  }
  
 function view(id){//没有权限时，不显示确定按钮
      if(id == undefined){
             id = getIdSelections();
      }
         jp.go("${ctx}/jxc/saleOrder/form/view?id=" + id);
 }

  
  
  
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#saleOrderChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/jxc/saleOrder/detail?id="+row.id, function(saleOrder){
    	var saleOrderChild1RowIdx = 0, saleOrderChild1Tpl = $("#saleOrderChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  saleOrder.saleOrderDetailList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			addRow('#saleOrderChild-'+row.id+'-1-List', saleOrderChild1RowIdx, saleOrderChild1Tpl, data1[i]);
			saleOrderChild1RowIdx = saleOrderChild1RowIdx + 1;
		}
				
      	  			
      })
     
        return html;
    }
  
	function addRow(list, idx, tpl, row){
		$(list).append(Mustache.render(tpl, {
			idx: idx, delBtn: true, row: row
		}));
	}
			
</script>
<script type="text/template" id="saleOrderChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">销售明细</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>销售单号</th>
								<th>商品</th>
								<th>单价</th>
								<th>数量</th>
								<th>单位</th>
								<th>规格说明</th>
								<th>生产日期</th>
								<th>保质期</th>
							</tr>
						</thead>
						<tbody id="saleOrderChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="saleOrderChild1Tpl">//<!--
				<tr>
					<td>
						{{row.saleOrder.no}}
					</td>
					<td>
						{{row.product.name}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.quantity}}
					</td>
					<td>
						{{row.unit}}
					</td>
					<td>
						{{row.intro}}
					</td>
					<td>
						{{row.releaseDate}}
					</td>
					<td>
						{{row.expiryDate}}
					</td>
				</tr>//-->
	</script>
