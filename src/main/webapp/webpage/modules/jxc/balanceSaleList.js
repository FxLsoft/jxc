<%@ page contentType="text/html;charset=UTF-8" %>
<script>

function confirmSale(ids) {
	if (!ids) {
		ids = $.map(getSelectedList(), function (row) {
            return row.id
        }).join(',');
	}
	jp.confirm("是否确认？", function(){
		var loading = jp.loading('load...')
		jp.get("${ctx}/api/confirmBalanceSale?ids="+ids, function(data){
		  	if(data.success){
		  		jp.success(data.msg);
		  	}else{
		  		jp.alert(data.msg);
		  	}
		  	$('#balanceSaleTable').bootstrapTable('refresh');
		  	jp.close(loading);
		  	
		})
	})
}
$(document).ready(function() {
	$('#balanceSaleTable').bootstrapTable({
		 
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
               url: "${ctx}/jxc/balanceSale/data",
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
                	   view(row.id);
                   }else if($el.data("item") == "view"){
                       view(row.id);
                   } else if($el.data("item") == "delete"){
                        jp.confirm('确认要删除该电子秤销售记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/jxc/balanceSale/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#balanceSaleTable').bootstrapTable('refresh');
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
		        field: 'saleId',
		        title: '称号',
		        sortable: true,
		        sortName: 'saleId'
		        ,formatter:function(value, row , index){
		        	value = jp.unescapeHTML(value);
				   <c:choose>
					   <c:when test="${fns:hasPermission('jxc:balanceSale:edit')}">
					      return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:when test="${fns:hasPermission('jxc:balanceSale:view')}">
					      return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:otherwise>
					      return value;
				      </c:otherwise>
				   </c:choose>
		         }
		       
		    }
			,{
		        field: 'saleNo',
		        title: '销售编号',
		        sortable: true,
		        sortName: 'saleNo'
		       
		    }
			,{
		        field: 'balanceNo',
		        title: '称编号',
		        sortable: true,
		        sortName: 'balanceNo'
		       
		    }
			,{
		        field: 'saleTime',
		        title: '销售时间',
		        sortable: true,
		        sortName: 'saleTime'
		       
		    }
			,{
		        field: 'wholeNo',
		        title: '全局累计记录',
		        sortable: true,
		        sortName: 'wholeNo'
		       
		    }
			,{
		        field: 'currentNo',
		        title: '当前累计记录',
		        sortable: true,
		        sortName: 'currentNo'
		       
		    }
			,{
		        field: 'realPay',
		        title: '实收现金',
		        sortable: true,
		        sortName: 'realPay'
		       
		    }
			,{
		        title: '操作',
		        sortable: false,
		        formatter:function(value, row, index){
		        	var btn = [];
		        	if (row.status != 1) {
		        		btn.push('<button class="btn btn-success btn-sm" onclick="confirmSale(\'' +row.id+ '\')">确认</button>');
		        	} else {
		        		return '-';
		        	}
		        	return btn.join();
		        }
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#balanceSaleTable').bootstrapTable("toggleView");
		}
	  
	  $('#balanceSaleTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#balanceSaleTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#balanceSaleTable').bootstrapTable('getSelections').length!=1);
            $('#confirmSale').prop('disabled', ! getSelectedList().length);
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
					  jp.downloadFile('${ctx}/jxc/balanceSale/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/jxc/balanceSale/import', function (data) {
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
            var sortName = $('#balanceSaleTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#balanceSaleTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/jxc/balanceSale/export?'+values);
	  })
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#balanceSaleTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#balanceSaleTable').bootstrapTable('refresh');
		});
		
				$('#beginSaleTime').datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
				});
				$('#endSaleTime').datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
				});
		
	});
		
  function getIdSelections() {
        return $.map($("#balanceSaleTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  function getSelectedList() {
	  
	  var selected = $("#balanceSaleTable").bootstrapTable('getSelections');
	  var out = [];
	  for (var i = 0; i < selected.length; i++) {
		  if (selected[i].status !== 1) {
			  out.push(selected[i]);
		  }
	  }
	  return out;
  }
  
  function deleteAll(){

		jp.confirm('确认要删除该电子秤销售记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/jxc/balanceSale/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#balanceSaleTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }

     //刷新列表
  function refresh(){
  	$('#balanceSaleTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/jxc/balanceSale/form/add");
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  jp.go("${ctx}/jxc/balanceSale/form/edit?id=" + id);
  }
  
 function view(id){//没有权限时，不显示确定按钮
      if(id == undefined){
             id = getIdSelections();
      }
         jp.go("${ctx}/jxc/balanceSale/form/view?id=" + id);
 }

  
  
  
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#balanceSaleChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/jxc/balanceSale/detail?id="+row.id, function(balanceSale){
    	var balanceSaleChild1RowIdx = 0, balanceSaleChild1Tpl = $("#balanceSaleChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  balanceSale.balanceSaleDetailList;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			data1[i].dict.isReturn = jp.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, data1[i].isReturn, "-");
			addRow('#balanceSaleChild-'+row.id+'-1-List', balanceSaleChild1RowIdx, balanceSaleChild1Tpl, data1[i]);
			balanceSaleChild1RowIdx = balanceSaleChild1RowIdx + 1;
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
<script type="text/template" id="balanceSaleChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">电子秤销售记录详情</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>序号</th>
								<th>销售编号</th>
								<th>销售价</th>
								<th>商品编号</th>
								<th>重量</th>
								<th>单位</th>
								<th>是否退货</th>
								<th>实际单价</th>
								<th>商品名称</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="balanceSaleChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="balanceSaleChild1Tpl">//<!--
				<tr>
					<td>
						{{row.orderNo}}
					</td>
					<td>
						{{row.saleNo}}
					</td>
					
					<td>
						{{row.salePrice}}
					</td>
					<td>
						{{row.productNo}}
					</td>
					<td>
						{{row.amount}}
					</td>
					<td>
						{{row.unit}}
					</td>
					<td>
						{{row.dict.isReturn}}
					</td>
					<td>
						{{row.realPrice}}
					</td>
					<td>
						{{row.productName}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
