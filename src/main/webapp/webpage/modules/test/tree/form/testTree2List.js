<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $testTree2TreeTable=null;  
		$(document).ready(function() {
			$testTree2TreeTable=$('#testTree2TreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/test/tree/form/testTree2/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#testTree2TreeTableTpl").html();
		            	 item.dict = {};
	           	  var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($testTree2TreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $testTree2TreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($testTree2TreeTable, id) {   
		            },  
		            afterExpand : function($testTree2TreeTable, id) {  
		            },  
		            beforeClose : function($testTree2TreeTable, id) {    
		            	
		            }  
		        });
		        
		        $testTree2TreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除机构吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/test/tree/form/testTree2/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$testTree2TreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		} 
		
		function add(){//新增
			jp.go('${ctx}/test/tree/form/testTree2/form/add');
		}
		function edit(id){//编辑
			jp.go('${ctx}/test/tree/form/testTree2/form/edit?id='+id);
		}
		function view(id){//查看
			jp.go('${ctx}/test/tree/form/testTree2/form/view?id='+id);
		}
		function addChild(id){//添加下级机构
			jp.go('${ctx}/test/tree/form/testTree2/form/add?parent.id='+id);
		}
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$testTree2TreeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="testTree2TreeTableTpl">
			<td>
			<c:choose>
			      <c:when test="${fns:hasPermission('test:tree:form:testTree2:edit')}">
				    <a  href="${ctx}/test/tree/form/testTree2/form/edit?id={{d.row.id}}">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>	
			      </c:when>
			      <c:when test="${fns:hasPermission('test:tree:form:testTree2:view')}">
				    <a  href="${ctx}/test/tree/form/testTree2/form/view?id={{d.row.id}}">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>	
			      </c:when>
			      <c:otherwise>
							{{d.row.name === undefined ? "": d.row.name}}
			      </c:otherwise>
			</c:choose>
			</td>
			<td>
							{{d.row.remarks === undefined ? "": d.row.remarks}}
			</td>
			<td>
				<div class="btn-group">
			 		<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-cog"></i>
						<span class="fa fa-chevron-down"></span>
					</button>
				  <ul class="dropdown-menu" role="menu">
					<shiro:hasPermission name="test:tree:form:testTree2:view">
						<li><a href="${ctx}/test/tree/form/testTree2/form/view?id={{d.row.id}}"><i class="fa fa-search-plus"></i> 查看</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="test:tree:form:testTree2:edit">
		   				<li><a href="${ctx}/test/tree/form/testTree2/form/edit?id={{d.row.id}}"><i class="fa fa-edit"></i> 修改</a></li>
		   			</shiro:hasPermission>
		   			<shiro:hasPermission name="test:tree:form:testTree2:del">
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					</shiro:hasPermission>
		   			<shiro:hasPermission name="test:tree:form:testTree2:add">
						<li><a href="${ctx}/test/tree/form/testTree2/form/add?parent.id={{d.row.id}}"><i class="fa fa-plus"></i> 添加下级机构</a></li>
					</shiro:hasPermission>
				  </ul>
				</div>
			</td>
	</script>