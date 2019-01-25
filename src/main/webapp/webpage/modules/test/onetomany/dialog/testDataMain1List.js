<%@ page contentType="text/html;charset=UTF-8" %>
<script>
$(document).ready(function() {
	$('#testDataMain1Table').bootstrapTable({
		 
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
               url: "${ctx}/test/onetomany/dialog/testDataMain1/data",
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
                        jp.confirm('确认要删除该票务代理记录吗？', function(){
                       	jp.loading();
                       	jp.get("${ctx}/test/onetomany/dialog/testDataMain1/delete?id="+row.id, function(data){
                   	  		if(data.success){
                   	  			$('#testDataMain1Table').bootstrapTable('refresh');
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
		        field: 'tuser.name',
		        title: '归属用户',
		        sortable: true,
		        sortName: 'tuser.name'
		        ,formatter:function(value, row , index){

			   if(value == null || value ==""){
				   value = "-";
			   }
			   <c:choose>
				   <c:when test="${fns:hasPermission('test:onetomany:dialog:testDataMain1:edit')}">
				      return "<a href='javascript:edit(\""+row.id+"\")'>"+value+"</a>";
			      </c:when>
				  <c:when test="${fns:hasPermission('test:onetomany:dialog:testDataMain1:view')}">
				      return "<a href='javascript:view(\""+row.id+"\")'>"+value+"</a>";
			      </c:when>
				  <c:otherwise>
				      return value;
			      </c:otherwise>
			   </c:choose>

		        }
		       
		    }
			,{
		        field: 'office.name',
		        title: '归属部门',
		        sortable: true,
		        sortName: 'office.name'
		       
		    }
			,{
		        field: 'area.name',
		        title: '归属区域',
		        sortable: true,
		        sortName: 'area.name'
		       
		    }
			,{
		        field: 'name',
		        title: '名称',
		        sortable: true,
		        sortName: 'name'
		       
		    }
			,{
		        field: 'sex',
		        title: '性别',
		        sortable: true,
		        sortName: 'sex',
		        formatter:function(value, row , index){
		        	return jp.getDictLabel(${fns:toJson(fns:getDictList('sex'))}, value, "-");
		        }
		       
		    }
			,{
		        field: 'inDate',
		        title: '加入日期',
		        sortable: true,
		        sortName: 'inDate'
		       
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

		 
		  $('#testDataMain1Table').bootstrapTable("toggleView");
		}
	  
	  $('#testDataMain1Table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
                'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#testDataMain1Table').bootstrapTable('getSelections').length);
            $('#view,#edit').prop('disabled', $('#testDataMain1Table').bootstrapTable('getSelections').length!=1);
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
					  jp.downloadFile('${ctx}/test/onetomany/dialog/testDataMain1/import/template');
				  },
			    btn2: function(index, layero){
						var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
						iframeWin.contentWindow.importExcel('${ctx}/test/onetomany/dialog/testDataMain1/import', function (data) {
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
			jp.downloadFile('${ctx}/test/onetomany/dialog/testDataMain1/export');
	  });
	  $("#search").click("click", function() {// 绑定查询按扭
		  $('#testDataMain1Table').bootstrapTable('refresh');
		});
	 
	 $("#reset").click("click", function() {// 绑定查询按扭
		  $("#searchForm  input").val("");
		  $("#searchForm  select").val("");
		   $("#searchForm  .select-item").html("");
		  $('#testDataMain1Table').bootstrapTable('refresh');
		});
		
				$('#beginInDate').datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
				});
				$('#endInDate').datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
				});
		
	});
		
  function getIdSelections() {
        return $.map($("#testDataMain1Table").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }
  
  function deleteAll(){

		jp.confirm('确认要删除该票务代理记录吗？', function(){
			jp.loading();  	
			jp.get("${ctx}/test/onetomany/dialog/testDataMain1/deleteAll?ids=" + getIdSelections(), function(data){
         	  		if(data.success){
         	  			$('#testDataMain1Table').bootstrapTable('refresh');
         	  			jp.success(data.msg);
         	  		}else{
         	  			jp.error(data.msg);
         	  		}
         	  	})
          	   
		})
  }
  
    //刷新列表
  function refresh() {
      $('#testDataMain1Table').bootstrapTable('refresh');
  }
  function add(){
	  jp.openSaveDialog('新增票务代理', "${ctx}/test/onetomany/dialog/testDataMain1/form",'800px', '500px');
  }
  
   function edit(id){//没有权限时，不显示确定按钮
       if(id == undefined){
	      id = getIdSelections();
	}
	jp.openSaveDialog('编辑票务代理', "${ctx}/test/onetomany/dialog/testDataMain1/form?id=" + id, '800px', '500px');
  }

  
 function view(id){//没有权限时，不显示确定按钮
      if(id == undefined){
             id = getIdSelections();
      }
        jp.openViewDialog('查看票务代理', "${ctx}/test/onetomany/dialog/testDataMain1/form?id=" + id, '800px', '500px');
 }
  
  
  
  
		   
  function detailFormatter(index, row) {
	  var htmltpl =  $("#testDataMain1ChildrenTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
	  var html = Mustache.render(htmltpl, {
			idx:row.id
		});
	  $.get("${ctx}/test/onetomany/dialog/testDataMain1/detail?id="+row.id, function(testDataMain1){
    	var testDataMain1Child1RowIdx = 0, testDataMain1Child1Tpl = $("#testDataMain1Child1Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data1 =  testDataMain1.testDataChild11List;
		for (var i=0; i<data1.length; i++){
			data1[i].dict = {};
			data1[i].dict.isHave = jp.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, data1[i].isHave, "-");
			addRow('#testDataMain1Child-'+row.id+'-1-List', testDataMain1Child1RowIdx, testDataMain1Child1Tpl, data1[i]);
			testDataMain1Child1RowIdx = testDataMain1Child1RowIdx + 1;
		}
				
    	var testDataMain1Child2RowIdx = 0, testDataMain1Child2Tpl = $("#testDataMain1Child2Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data2 =  testDataMain1.testDataChild12List;
		for (var i=0; i<data2.length; i++){
			data2[i].dict = {};
			data2[i].dict.isHave = jp.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, data2[i].isHave, "-");
			addRow('#testDataMain1Child-'+row.id+'-2-List', testDataMain1Child2RowIdx, testDataMain1Child2Tpl, data2[i]);
			testDataMain1Child2RowIdx = testDataMain1Child2RowIdx + 1;
		}
				
    	var testDataMain1Child3RowIdx = 0, testDataMain1Child3Tpl = $("#testDataMain1Child3Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
		var data3 =  testDataMain1.testDataChild13List;
		for (var i=0; i<data3.length; i++){
			data3[i].dict = {};
			data3[i].dict.isHave = jp.getDictLabel(${fns:toJson(fns:getDictList('yes_no'))}, data3[i].isHave, "-");
			addRow('#testDataMain1Child-'+row.id+'-3-List', testDataMain1Child3RowIdx, testDataMain1Child3Tpl, data3[i]);
			testDataMain1Child3RowIdx = testDataMain1Child3RowIdx + 1;
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
<script type="text/template" id="testDataMain1ChildrenTpl">//<!--
	<div class="tabs-container">
		<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-{{idx}}-1" aria-expanded="true">火车票</a></li>
				<li><a data-toggle="tab" href="#tab-{{idx}}-2" aria-expanded="true">飞机票</a></li>
				<li><a data-toggle="tab" href="#tab-{{idx}}-3" aria-expanded="true">汽车票</a></li>
		</ul>
		<div class="tab-content">
				 <div id="tab-{{idx}}-1" class="tab-pane fade in active">
						<table class="ani table">
						<thead>
							<tr>
								<th>出发地</th>
								<th>目的地</th>
								<th>出发时间</th>
								<th>代理价格</th>
								<th>是否有票</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="testDataMain1Child-{{idx}}-1-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-2" class="tab-pane fade">
					<table class="ani table">
						<thead>
							<tr>
								<th>出发地</th>
								<th>目的地</th>
								<th>出发时间</th>
								<th>代理价格</th>
								<th>是否有票</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="testDataMain1Child-{{idx}}-2-List">
						</tbody>
					</table>
				</div>
				<div id="tab-{{idx}}-3" class="tab-pane fade">
					<table class="ani table">
						<thead>
							<tr>
								<th>出发地</th>
								<th>目的地</th>
								<th>代理价格</th>
								<th>是否有票</th>
								<th>备注信息</th>
							</tr>
						</thead>
						<tbody id="testDataMain1Child-{{idx}}-3-List">
						</tbody>
					</table>
				</div>
		</div>//-->
	</script>
	<script type="text/template" id="testDataMain1Child1Tpl">//<!--
				<tr>
					<td>
						{{row.startArea.name}}
					</td>
					<td>
						{{row.endArea.name}}
					</td>
					<td>
						{{row.starttime}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.dict.isHave}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="testDataMain1Child2Tpl">//<!--
				<tr>
					<td>
						{{row.startArea.name}}
					</td>
					<td>
						{{row.endArea.name}}
					</td>
					<td>
						{{row.startTime}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.dict.isHave}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
	<script type="text/template" id="testDataMain1Child3Tpl">//<!--
				<tr>
					<td>
						{{row.startArea.name}}
					</td>
					<td>
						{{row.endArea.name}}
					</td>
					<td>
						{{row.price}}
					</td>
					<td>
						{{row.dict.isHave}}
					</td>
					<td>
						{{row.remarks}}
					</td>
				</tr>//-->
	</script>
