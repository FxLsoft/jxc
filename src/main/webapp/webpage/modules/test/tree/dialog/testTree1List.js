<%@ page contentType="text/html;charset=UTF-8" %>
<script>
	    var $testTree1TreeTable=null;  
		$(document).ready(function() {
			$testTree1TreeTable=$('#testTree1TreeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/test/tree/dialog/testTree1/getChildren?parentId=',  
		            callback:function(item) { 
		            	 var treeTableTpl= $("#testTree1TreeTableTpl").html();
		            	 item.dict = {};

		            	 var result = laytpl(treeTableTpl).render({
								  row: item
							});
		                return result;                   
		            },  
		            beforeClick: function($testTree1TreeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $testTree1TreeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($testTree1TreeTable, id) {   
		            },  
		            afterExpand : function($testTree1TreeTable, id) {  
		            },  
		            beforeClose : function($testTree1TreeTable, id) {    
		            	
		            }  
		        });
		        
		        $testTree1TreeTable.initParents('${parentIds}', "0");//在保存编辑时定位展开当前节点
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除机构吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/test/tree/dialog/testTree1/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$testTree1TreeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		} 
		
		function refreshNode(data) {//刷新节点
            var current_id = data.body.testTree1.id;
			var target = $testTree1TreeTable.get(current_id);
			var old_parent_id = target.attr("pid") == undefined?'1':target.attr("pid");
			var current_parent_id = data.body.testTree1.parentId;
			var current_parent_ids = data.body.testTree1.parentIds;
			if(old_parent_id == current_parent_id){
				if(current_parent_id == '0'){
					$testTree1TreeTable.refreshPoint(-1);
				}else{
					$testTree1TreeTable.refreshPoint(current_parent_id);
				}
			}else{
				$testTree1TreeTable.del(current_id);//刷新删除旧节点
				$testTree1TreeTable.initParents(current_parent_ids, "0");
			}
        }
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$testTree1TreeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="testTree1TreeTableTpl">
			<td>
			<c:choose>
			      <c:when test="${fns:hasPermission('test:tree:dialog:testTree1:edit')}">
				    <a  href="#" onclick="jp.openSaveDialog('编辑机构', '${ctx}/test/tree/dialog/testTree1/form?id={{d.row.id}}','800px', '500px')">
							{{d.row.name === undefined ? "": d.row.name}}
					</a>
			      </c:when>
			      <c:when test="${fns:hasPermission('test:tree:dialog:testTree1:view')}">
				    <a  href="#" onclick="jp.openViewDialog('查看机构', '${ctx}/test/tree/dialog/testTree1/form?id={{d.row.id}}','800px', '500px')">
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
					<shiro:hasPermission name="test:tree:dialog:testTree1:view">
						<li><a href="#" onclick="jp.openViewDialog('查看机构', '${ctx}/test/tree/dialog/testTree1/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-search-plus"></i> 查看</a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="test:tree:dialog:testTree1:edit">
						<li><a href="#" onclick="jp.openSaveDialog('修改机构', '${ctx}/test/tree/dialog/testTree1/form?id={{d.row.id}}','800px', '500px')"><i class="fa fa-edit"></i> 修改</a></li>
		   			</shiro:hasPermission>
		   			<shiro:hasPermission name="test:tree:dialog:testTree1:del">
		   				<li><a  onclick="return del(this, '{{d.row.id}}')"><i class="fa fa-trash"></i> 删除</a></li>
					</shiro:hasPermission>
		   			<shiro:hasPermission name="test:tree:dialog:testTree1:add">
						<li><a href="#" onclick="jp.openSaveDialog('添加下级机构', '${ctx}/test/tree/dialog/testTree1/form?parent.id={{d.row.id}}','800px', '500px')"><i class="fa fa-plus"></i> 添加下级机构</a></li>
					</shiro:hasPermission>
				  </ul>
				</div>
			</td>
	</script>