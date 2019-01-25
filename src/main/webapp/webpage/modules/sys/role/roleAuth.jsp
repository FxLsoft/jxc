<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>权限设置</title>
	<meta name="decorator" content="ani"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script type="text/javascript">

        function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
            }else{
                //功能权限
                var ref = $('#menuTree').jstree(true);
                var ids = ref.get_selected();
                //取半选节点ID
                $("#menuTree li").has("i[class*='jstree-undetermined']").each(function(){
                    ids+=","+$(this).attr("id");
                });
                $("#menuIds").val(ids);

                //数据权限
                var data_ref = $('#dataRuleTree').jstree(true);
                var nodes = data_ref.get_selected(true);
                var data_ids = new Array();
                for(var i=0; i<nodes.length; i++){

                    if(nodes[i].type==='4'){
                        data_ids.push(nodes[i].id);
                    }
                }
                $("#dataRuleIds").val(data_ids.join(","));
                jp.loading();
                jp.post("${ctx}/sys/role/save",$('#inputForm').serialize(),function(data){
                    if(data.success){
                        // jp.getParent().refresh();
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                        jp.success(data.msg)

                    }else{
                        jp.error(data.msg);
                    }
                })
            }

        }
		$(document).ready(function(){
		
			//功能权限
			$('#menuTree').jstree({
				'core' : {
					"multiple" : true,
					"animation" : 0,
					"themes" : { "icons":true ,"stripes":false},
					'data' : {
						"url" : "${ctx}/sys/menu/treeData?roleId=${role.id}",
						"dataType" : "json" // needed only if you do not supply JSON headers
					}
				},
				'plugins' : [ "checkbox", 'types' , 'wholerow'],
				"types":{
							'default' : {
								'icon' : 'fa fa-folder'
							},
			                'html' : {
			                    'icon' : 'fa fa-file-code-o'
			                },
			                'btn':{'icon' : 'fa fa-square'}
				},
				'checkbox' : {  
	                // 禁用级联选中  
	                'three_state' : false,       
	                'cascade' : 'undetermined|down|up' //有三个选项，up, down, undetermined; 使用前需要先禁用three_state  
	            },  

			});
			//数据权限
			$('#dataRuleTree').jstree({
				'core' : {
					"multiple" : true,
					"animation" : 0,
					"themes" : { "icons":true ,"stripes":false},
					'data' : {
						"url" : "${ctx}/sys/dataRule/treeData?roleId=${role.id}",
						"dataType" : "json" // needed only if you do not supply JSON headers
					}
				},
				'plugins' : [ "checkbox", 'types' , 'wholerow'],
				"types":{ 'default' : {
                       			 'icon' : 'fa fa-folder'
			                },
			                '4' : {//权限type
			                    'icon' : ' fa fa-anchor'
			                }
				},
				'checkbox' : {  
	                // 禁用级联选中  
	                'three_state' : false,       
	                'cascade' : 'undetermined|down|up' //有三个选项，up, down, undetermined; 使用前需要先禁用three_state  
	            },  

			});

		});
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">功能权限：</a></li>
		<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">数据权限：</a></li>
    </ul>
    <div class="tab-content">
		<div id="tab-1" class="tab-pane fade in  active">
			<div id="menuTree"></div>
		</div>
		<div id="tab-2" class="tab-pane fade in">
			<div id="dataRuleTree"></div>
		</div>
	</div>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input  name="office.id" type="hidden" value="${role.office.id}">
		<input  name="office.name" type="hidden" value="${role.office.name}">
		<input  name="name" type="hidden" value="${role.name}">
		<input  name="oldName" type="hidden" value="${role.name}">
		<input  name="enname" type="hidden" value="${role.enname}">
		<input  name="oldEnname" type="hidden" value="${role.enname}">
		<input  name="roleType" type="hidden" value="${role.roleType}">
		<input  name="sysData" type="hidden" value="${role.sysData}">
		<input  name="useable" type="hidden" value="${role.useable}">
		<input  name="remarks" type="hidden" value="${role.remarks}">
		<form:hidden path="menuIds"/>
		<form:hidden path="dataRuleIds"/>
	</form:form>
</body>
</html>