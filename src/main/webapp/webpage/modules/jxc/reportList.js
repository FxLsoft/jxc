<%@ page contentType="text/html;charset=UTF-8" %>
<script>
function getUrlParam (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
//单据来源（0：采购入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）
var from = getUrlParam('from') || 'in';
console.log('from', from);
$(document).ready(function() {
	$('#reportTable').bootstrapTable({
		 
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
               url: "${ctx}/jxc/report/data",
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
                        jp.confirm('确认要删除该报表信息记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/jxc/report/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#reportTable').bootstrapTable('refresh');
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
		        field: 'title',
		        title: '报表头',
		        sortable: true,
		        sortName: 'title'
		        ,formatter:function(value, row , index){
		        	value = jp.unescapeHTML(value);
				   <c:choose>
					   <c:when test="${fns:hasPermission('jxc:report:edit')}">
					      return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:when test="${fns:hasPermission('jxc:report:view')}">
					      return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
				      </c:when>
					  <c:otherwise>
					      return value;
				      </c:otherwise>
				   </c:choose>
		         }
		       
		    }
			,{
		        field: 'date',
		        title: '日期',
		        sortable: true,
		        sortName: 'date'
		       
		    }
			,{
		        field: 'store.name',
		        title: '门店',
		        sortable: true,
		        sortName: 'store.name'
		       
		    }
			,{
		        field: 'saleIn',
		        title: '销售应收',
		        sortable: true,
		        visible: from == 'in',
		        sortName: 'saleIn'
		        ,formatter: function (value, row, index) {
			        if (value != undefined) {
			        	return (value || 0).toFixed(2);
			        } else {
			        	return '-';
			        }
			    }
		    }
			,{
		        field: 'saleRealIn',
		        title: '销售实收',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'saleRealIn'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'saleBenefit',
		        title: '销售优惠',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'saleBenefit'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'purchaseOut',
		        title: '采购应付',
		        visible: from == 'out',
		        sortable: true,
		        sortName: 'purchaseOut'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'purchaseRealOut',
		        title: '采购实付',
		        visible: from == 'out',
		        sortable: true,
		        sortName: 'purchaseRealOut'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'returnPay',
		        title: '退货应付',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'returnPay'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'returnRealPay',
		        title: '退货实付',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'returnRealPay'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'oldDebtIn',
		        title: '欠款已收',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'oldDebtIn'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'oldDebtOut',
		        title: '欠款已付',
		        visible: from == 'out',
		        sortable: true,
		        sortName: 'oldDebtOut'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'balanceIn',
		        title: '电子秤销售',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'balanceIn'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'totalIn',
		        title: '总金额',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'totalIn'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'wxPay',
		        title: '微信收款',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'wxPay'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'aliPay',
		        title: '支付宝收款',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'aliPay'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'bankPay',
		        title: '银行卡收款',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'bankPay'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'moenyPay',
		        title: '现金收款',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'moenyPay'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'wxPayOut',
		        title: '微信付款',
		        visible: from == 'out',
		        sortable: true,
		        sortName: 'wxPayOut'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'aliPayOut',
		        title: '支付宝付款',
		        visible: from == 'out',
		        sortable: true,
		        sortName: 'aliPayOut'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'bankPayOut',
		        title: '银行卡付款',
		        visible: from == 'out',
		        sortable: true,
		        sortName: 'bankPayOut'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'moenyPayOut',
		        title: '现金付款',
		        visible: from == 'out',
		        sortable: true,
		        sortName: 'moenyPayOut'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'totalRealIn',
		        title: '实收总计',
		        visible: from == 'in',
		        sortable: true,
		        sortName: 'totalRealIn'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'totalRealOut',
		        title: '实付总计',
		        visible: from == 'out',
		        sortable: true,
		        sortName: 'totalRealOut'
		        	,formatter: function (value, row, index) {
				        if (value != undefined) {
				        	return (value || 0).toFixed(2);
				        } else {
				        	return '-';
				        }
				    }
		    }
			,{
		        field: 'createDate',
		        title: '创建时间',
		        sortable: true,
		        sortName: 'createDate'
		       
		    }
			,{
		        field: 'remarks',
		        title: '备注信息',
		        sortable: true,
		        sortName: 'remarks'
		       
		    }
		     ]
		
		});
		
		  
	  if(navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)){//如果是移动端

		 
		  $('#reportTable').bootstrapTable("toggleView");
		}
	  
	  $('#reportTable').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#reportTable').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#reportTable').bootstrapTable('getSelections').length!=1);
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
					 jp.downloadFile('${ctx}/jxc/report/import/template');
				  },
			    btn2: function(index, layero){
				        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/jxc/report/import', function (data) {
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
            var sortName = $('#reportTable').bootstrapTable("getOptions", "none").sortName;
            var sortOrder = $('#reportTable').bootstrapTable("getOptions", "none").sortOrder;
            var values = "";
            for(var key in searchParam){
                values = values + key + "=" + searchParam[key] + "&";
            }
            if(sortName != undefined && sortOrder != undefined){
                values = values + "orderBy=" + sortName + " "+sortOrder;
            }

			jp.downloadFile('${ctx}/jxc/report/export?'+values);
	  })

		    
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#reportTable').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		  $("#searchForm  .select-item").html("");
		  $('#reportTable').bootstrapTable('refresh');
		});
		
		$('#beginCreateDate').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$('#endCreateDate').datetimepicker({
			 format: "YYYY-MM-DD HH:mm:ss"
		});
		$("#createReport").click("click", function() {
			jp.confirm("确认生成报表?", function(){
				  var loading = jp.loading('正在生成。。。')
					jp.post("${ctx}/api/createReport", {}, function (res) {
						console.log(res);
						if (res.success) {
							jp.close(loading);
							jp.success("生成成功");
							$('#reportTable').bootstrapTable('refresh');
						} else {
							jp.warning(res.msg);
						}
					});
			  })
		});
	});
		
  function getIdSelections() {
        return $.map($("#reportTable").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该报表信息记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/jxc/report/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#reportTable').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  function refresh(){
  	$('#reportTable').bootstrapTable('refresh');
  }
  function add(){
		jp.go("${ctx}/jxc/report/form/add" + "?from=" + from);
	}

  function edit(id){
	  if(id == undefined){
		  id = getIdSelections();
	  }
	  jp.go("${ctx}/jxc/report/form/edit?id=" + id + "&from=" + from);
  }

  function view(id) {
      if(id == undefined){
          id = getIdSelections();
      }
      jp.go("${ctx}/jxc/report/form/view?id=" + id + "&from=" + from);
  }
  
</script>