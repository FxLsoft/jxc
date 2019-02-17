<%@ page contentType="text/html;charset=UTF-8" %>
<script>
function getUrlParam (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
//单据来源（0：采购入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）
var from = getUrlParam('from') || 0;
console.log('from', from);

function checkOrder(id, status) {
	var message = status == 1 ? '确认提交此单？' : '确认作废此单？';
	jp.confirm(message, function(){
		updateStatus(id, status);
	})
}

function updateStatus(id, status) {
	var loading = jp.loading('load...')
	jp.get("${ctx}/api/updateOrderStatus?id="+id + "&status=" + status + "&from=" + from, function(data){
	  	if(data.success){
	  		$('#operOrderTable').bootstrapTable('refresh');
	  		jp.success(data.msg);
	  	}else{
	  		jp.error(data.msg);
	  	}
	  	jp.close(loading);
	})
}

var order_url = "${ctx}/api/payOrder";
//1 付款 // 2 收帐 // 3 退款
function payOrder(ids, maxPay, type) {
	jp.prompt("确认" + (type == '1' ? '应收' : '应付') + "金额", maxPay.toFixed(2), function(text) {
		if (/^\d+(\.\d+)?$/.test(text)) {
			var pay = parseFloat(text);
			var loading = jp.loading('正在付款。。。')
			jp.post(order_url, {
				payMoney: pay,
				ids: ids,
				type: type
			}, function (res) {
				console.log(res);
				if (res.success) {
					jp.close(loading);
					jp.success("付款成功");
					$('#operOrderTable').bootstrapTable('refresh');
				} else {
					jp.warning(res.msg);
				}
			});
		} else {
			jp.warning("输入数字非法");
		}
	});
}

function printDialog(id, type) {
	jp.openPrintDialog('打印', "${ctx}/api/print?id=" + id + "&type=ys",'250px', '500px');
}


$(document).ready(function() {
	// 标题
	if(from == 0) {
		$('.panel-title').text('采购信息列表');
	} else {
		$('#payAll').hide();
	}
	$('#operOrderTable').bootstrapTable({
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
               url: "${ctx}/jxc/operOrder/data?source=" + from,
               //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
               //queryParamsType:'',   
               ////查询参数,每次调用是会带上这个参数，可自定义                         
               queryParams : function(params) {
	               	var searchParam = $("#searchForm").serializeJSON();
	               	searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
	               	searchParam.pageSize = params.limit === undefined? -1 : params.limit;
	               	searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
	               	
//	               	searchParam.source = from;
	               	
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
                        jp.confirm('确认要删除该单据信息记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/jxc/operOrder/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#operOrderTable').bootstrapTable('refresh');
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
					   <c:when test="${fns:hasPermission('jxc:operOrder:edit')}">
					      return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:when test="${fns:hasPermission('jxc:operOrder:view')}">
					      return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:otherwise>
					      return value;
				      </c:otherwise>
				   </c:choose>
		         }
		       
		    }
			,{
		        field: 'agency.name',
		        title: '商家',
		        sortable: true,
		        sortName: 'agency.name'
		       
		    }
			,{
		        field: 'type',
		        title: '单据类型',
		        sortable: true,
		        sortName: 'type',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('order_type'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'status',
		        title: '单据状态',
		        sortable: true,
		        sortName: 'status',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('order_status'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'source',
		        title: '单据来源',
		        sortable: true,
		        sortName: 'source',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('order_from'))}, value, "-");
		        }
		       
		    }
			
			,{
		        field: 'realPrice',
		        title: '总计',
		        sortable: true,
		        sortName: 'realPrice',
		        formatter: function (value, row, index) {
		        	return (value || 0).toFixed(2);
		        }
		       
		    }
			,{
		        field: 'realPay',
		        // 单据来源（0：采购入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）
		        title: from == 0 ? '实付' : from == 2 ? '实退' : from == 3 || from == 4 || from == 5 ? '实收' : '',
		        sortable: true,
		        sortName: 'realPay',
		        visible: from != 1,
		        formatter: function (value, row, index) {
		        	return (value || 0).toFixed(2);
		        }
		       
		    }
			,{
		        field: 'updateDate',
		        title: '更新时间',
		        sortable: true,
		        sortName: 'updateDate'
		       
		    }
			,{
		        field: 'store.name',
		        title: '门店',
		        sortable: false,
		        sortName: 'store.name'
		       
		    }
			,{
		        title: '操作',
		        sortable: false,
		        formatter:function(value, row, index){
		        	var btn = [];
		        	btn.push('<button class="btn btn-primary btn-sm" onclick="printDialog(\'' +row.id+ '\')">打印</button>');
		        	if (row.status == 0) {
		        		// 1 提交
		        		// 2 作废
		        		btn.push('<button class="btn btn-success btn-sm" onclick="checkOrder(\'' +row.id+ '\', 1)">提交</button>');
		        		btn.push('<button class="btn btn-warning btn-sm" onclick="checkOrder(\'' +row.id+ '\', 2)">作废</button>');
		        	}
		        	if (from == 0 && row.status == 1) {
		        		btn.push('<button class="btn btn-success btn-sm" onclick="payOrder(\'' +row.id+ '\', ' + ((row.realPrice || 0) - (row.realPay || 0)) + ', -1)">付款</button>');
		        	}
		        	
		        	if (from == 2 && row.status == 1) {
		        		btn.push('<button class="btn btn-success btn-sm" onclick="payOrder(\'' +row.id+ '\', ' + ((row.realPrice || 0) - (row.realPay || 0)) + ', -1)">退款</button>');
		        	}
		        	
		        	if ((from == 3 || from == 4 || from == 5) && row.status == 1) {
		        		btn.push('<button class="btn btn-success btn-sm" onclick="payOrder(\'' +row.id+ '\', ' + ((row.realPrice || 0) - (row.realPay || 0)) + ', 1)">收款</button>');
		        	}
		        	
		        	return btn.join();
		        }
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#operOrderTable').bootstrapTable("toggleView");
		}
	  
	  $('#operOrderTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#operOrderTable').bootstrapTable('getSelections').length);
            $('#payAll').prop('disabled', ! getPayList().length);
            $('#view,#edit').prop('disabled', $('#operOrderTable').bootstrapTable('getSelections').length!=1);
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
					  jp.downloadFile('${ctx}/jxc/operOrder/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/jxc/operOrder/import', function (data) {
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
            var sortName = $('#operOrderTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#operOrderTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/jxc/operOrder/export?'+values);
	  })
		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#operOrderTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#operOrderTable').bootstrapTable('refresh');
		});
		
		
	});
		
  function getIdSelections() {
        return $.map($("#operOrderTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  function isCanEdit(id) {
	  var data = $("#operOrderTable").bootstrapTable('getData');
	  for (var i = 0; i < data.length; i++) {
		  if (data[i].id == id && data[i].status == 0) {
			  return true;
		  }
	  }
	  return false;
  }
  function getPayList() {
	  var selected = $("#operOrderTable").bootstrapTable('getSelections');
	  var out = [];
	  for (var i = 0; i < selected.length; i++) {
		  if (selected[i].status == 1) {
			  out.push(selected[i]);
		  }
	  }
	  return out;
  }
  
  function deleteAll(){

		jp.confirm('确认要删除该单据信息记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/jxc/operOrder/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#operOrderTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
  function payAll() {
	  var payList = getPayList();
	  if (payList.length > 0) {
		  var maxPay = 0;
		  var ids = [];
		  var pays = [];
		  for (var i = 0; i < payList.length; i++) {
			  var pay = (payList[i].realPrice || 0) - (payList[i].realPay || 0);
			  pays.push(pay);
			  maxPay += pay;
			  ids.push(payList[i].id);
		  }
		  jp.confirm("确认整体支付 " + maxPay.toFixed(2) + "?", function(){
			  var loading = jp.loading('正在付款。。。')
				jp.post(order_url, {
					payMoney: pays.join(","),
					ids: ids.join(","),
					type: from == 0 ? -1 : 1
				}, function (res) {
					console.log(res);
					if (res.success) {
						jp.close(loading);
						jp.success("付款成功");
						$('#operOrderTable').bootstrapTable('refresh');
					} else {
						jp.warning(res.msg);
					}
				});
		  })
	  }
  }

     //刷新列表
  function refresh(){
  	$('#operOrderTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/jxc/operOrder/form/add?from=" + from);
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  if (!isCanEdit(id)) {
		   	 jp.alert("已提交单据不能被修改");
		   	 return;
		  }
	  jp.go("${ctx}/jxc/operOrder/form/edit?id=" + id + "&from=" + from);
  }
  
 function view(id){//没有权限时，不显示确定按钮
      if(id == undefined){
             id = getIdSelections();
      }
         jp.go("${ctx}/jxc/operOrder/form/view?id=" + id + "&from=" + from);
 }

  
  
  
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#operOrderChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/jxc/operOrder/detail?id="+row.id, function(operOrder){
    	var operOrderChild1RowIdx = 0, operOrderChild1Tpl = $("#operOrderChild1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  operOrder.operOrderDetailList || [];
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			data1[i].dict.operType = jp.getDictLabel(${fns:toJson(fns:getDictList('oper_type'))}, data1[i].operType, "-");
			addRow('#operOrderChild-'+row.id+'-1-List', operOrderChild1RowIdx, operOrderChild1Tpl, data1[i]);
			operOrderChild1RowIdx = operOrderChild1RowIdx + 1;
		}
				
    	var operOrderChild2RowIdx = 0, operOrderChild2Tpl = $("#operOrderChild2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data2 =  operOrder.operOrderPayList || [];
		for (var i=0; i<data2.length; i++){
			data2[i].dict = {};
			data2[i].dict.payType = jp.getDictLabel(${fns:toJson(fns:getDictList('pay_type'))}, data2[i].payType, "-");
			addRow('#operOrderChild-'+row.id+'-2-List', operOrderChild2RowIdx, operOrderChild2Tpl, data2[i]);
			operOrderChild2RowIdx = operOrderChild2RowIdx + 1;
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
<script type="text/template" id="operOrderChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">单据详情</a></li>
				<li><a data-toggle="tab" href="#tab-{{idx}}-2" aria-expanded="true">账务记录</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>类型</th>
								<th>商品</th>
								<th>单位价格</th>
								<th>数量</th>
								<th>价格信息</th>
								<th>折扣</th>
							</tr>
						</thead>
						<tbody id="operOrderChild-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-2" class="tab-pane fade">
					<table class="ani table">
						<thead>
							<tr>
								<th>水流号</th>
								<th>款项</th>
								<th>金额</th>
								<th>发生时间</th>
							</tr>
						</thead>
						<tbody id="operOrderChild-{{idx}}-2-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="operOrderChild1Tpl">//<!--
				<tr>
					<td>
						{{row.dict.operType}}
					</td>
					<td>
						{{row.product.name}}
					</td>
					<td>
						{{row.price.unit}}/{{row.price.costPrice}}
					</td>
					<td>
						{{row.amount}}
					</td>
					<td>
						{{row.operPrice}}
					</td>
					<td>
						{{row.discount}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="operOrderChild2Tpl">//<!--
				<tr>
					<td>
						{{row.no}}
					</td>
					<td>
						{{row.dict.payType}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.createDate}}
					</td>
				</tr>//-->
	</script>
