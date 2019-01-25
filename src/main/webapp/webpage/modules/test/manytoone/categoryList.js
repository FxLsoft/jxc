<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $categoryTreeTable=null;  
		$(document).ready(function() {
			$categoryTreeTable=$('#categoryTreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/test/manytoone/category/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#categoryTreeTableTpl").html();
		            	 item.dict = {};

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($categoryTreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $categoryTreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($categoryTreeTable, id) {   
		            },  
		            afterExpand : function($categoryTreeTable, id) {  
		            },  
		            beforeClose : function($categoryTreeTable, id) {    
		            	
		            }  
		        });
		        
		        $categoryTreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除商品类型吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/test/manytoone/category/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$categoryTreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		} 
		
		function refreshNode(data) {//刷新节点
            var current_id = data.body.category.id;
			var target = $categoryTreeTable.get(current_id);
			var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
			var current_parent_id = data.body.category.parentId;
			var current_parent_ids = data.body.category.parentIds;
			if(old_parent_id == current_parent_id){
				if(current_parent_id == '0'){
					$categoryTreeTable.refreshPoint(-1);
				}else{
					$categoryTreeTable.refreshPoint(current_parent_id);
				}
			}else{
				$categoryTreeTable.del(current_id);//刷新删除旧节点
				$categoryTreeTable.initParents(current_parent_ids, "0");
			}
        }
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$categoryTreeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="categoryTreeTableTpl">
			<td>
			<c:choose>
			      <c:when test="${fns:hasPermission('test:manytoone:category:edit')}">
				    <a  href="#" onclick="jp.openSaveDialog('编辑商品类型', '${ctx}/test/manytoone/category/form?id={{d.row.id}}','800px', '500px')">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>
			      </c:when>
			      <c:when test="${fns:hasPermission('test:manytoone:category:view')}">
				    <a  href="#" onclick="jp.openViewDialog('查看商品类型', '${ctx}/test/manytoone/category/form?id={{d.row.id}}','800px', '500px')">
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
					<shiro:hasPermission name="test:manytoone:category:view">
						<li><a href="#" onclick="jp.openViewDialog('查看商品类型', '${ctx}/test/manytoone/category/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-search-plus"></i> 查看</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="test:manytoone:category:edit">
						<li><a href="#" onclick="jp.openSaveDialog('修改商品类型', '${ctx}/test/manytoone/category/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-edit"></i> 修改</a></li>
		   			</shiro:hasPermission>
		   			<shiro:hasPermission name="test:manytoone:category:del">
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					</shiro:hasPermission>
		   			<shiro:hasPermission name="test:manytoone:category:add">
						<li><a href="#" onclick="jp.openSaveDialog('添加下级商品类型', '${ctx}/test/manytoone/category/form?parent.id={{d.row.id}}','800px', '500px')"><i class="fa fa-plus"></i> 添加下级商品类型</a></li>
					</shiro:hasPermission>
				  </ul>
				</div>
			</td>
	</script>