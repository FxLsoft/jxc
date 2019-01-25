<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
		var $treeTable=null;  
		$(document).ready(function() {
			$treeTable=$('#treeTable').treeTable({  
		    	   theme:'vsStyle',	           
					expandLevel : 2,
					column:0,
					checkbox: false,
		            url:'${ctx}/sys/area/getChildren?parentId=',  
		            callback:function(item) {
		    	   	    item.type =  jp.getDictLabel(${fns:toJson(fns:getDictList('sys_area_type'))}, item.type);//字典转换
                        var areaItemTpl = $("#areaItemTpl").html();
                        var result = laytpl(areaItemTpl).render(item);
		                return result;                   
		            },  
		            beforeClick: function($treeTable, id) { 
		                //异步获取数据 这里模拟替换处理  
		                $treeTable.refreshPoint(id);  
		            },  
		            beforeExpand : function($treeTable, id) {   
		            },  
		            afterExpand : function($treeTable, id) {  
		            	//layer.closeAll(); 
		            },  
		            beforeClose : function($treeTable, id) {    
		            	
		            }  
		        });  
		       
		});
		
		function del(con,id){  
			jp.confirm('确认要删除区域吗？', function(){
				jp.loading();
	       	  	$.get("${ctx}/sys/area/delete?id="+id, function(data){
	       	  		if(data.success){
	       	  			$treeTable.del(id);
	       	  			jp.success(data.msg);
	       	  		}else{
	       	  			jp.error(data.msg);
	       	  		}
	       	  	})
	       	   
       		});
	
		} 
		
		function refresh(){//刷新
			var index = jp.loading("正在加载，请稍等...");
			$treeTable.refresh();
			jp.close(index);
		}
</script>
<script type="text/html" id="areaItemTpl">
<td><a  href="#" onclick="jp.openViewDialog('查看区域', '${ctx}/sys/area/form?id={{ d.id }}','800px', '500px')">{{ d.name }}</a></td>
<td>{{ d.code  === undefined? "" : d.code}}</td>
<td>{{ d.type  === undefined? "" : d.type}}</td>
<td>{{ d.remarks === undefined? "" :  d.remarks}}</td>
<td>
	<div class="btn-group">
 		<button type="button" class="btn  btn-primary btn-xs dropdown-toggle" data-toggle="dropdown">
			<i class="fa fa-cog"></i>
			<span class="fa fa-chevron-down"></span>
		</button>
	  <ul class="dropdown-menu" role="menu">
		<shiro:hasPermission name="sys:area:view">
			<li><a href="#" onclick="jp.openViewDialog('查看区域', '${ctx}/sys/area/form?id={{ d.id }}','800px', '500px')"  ><i class="fa fa-search-plus"></i>  查看</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:area:edit">
			<li><a href="#" onclick="jp.openSaveDialog('修改区域', '${ctx}/sys/area/form?id={{ d.id }}','800px', '500px')" ><i class="fa fa-edit"></i> 修改</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:area:del">
			<li><a  onclick="return del(this, '{{ d.id }}')"><i class="fa fa-trash"></i> 删除</a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:area:add">
			<li><a href="#" onclick="jp.openSaveDialog('添加下级区域', '${ctx}/sys/area/form?parent.id={{ d.id }}','800px', '500px')"><i class="fa fa-plus"></i> 添加下级区域</a></li>
		</shiro:hasPermission>
	  </ul>
	</div>
</td>
</script>