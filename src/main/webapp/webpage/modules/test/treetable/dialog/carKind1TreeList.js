<%@ page contentType="text/html;charset=UTF-8" %>
	<script>
		$(document).ready(function() {
			var to = false;
			$('#search_q').keyup(function () {
				if(to) { clearTimeout(to); }
				to = setTimeout(function () {
					var v = $('#search_q').val();
					$('#carKind1jsTree').jstree(true).search(v);
				}, 250);
			});
			$('#carKind1jsTree').jstree({
				'core' : {
					"multiple" : false,
					"animation" : 0,
					"themes" : { "variant" : "large", "icons":true , "stripes":true},
					'data' : {
						"url" : "${ctx}/test/treetable/dialog/carKind1/treeData",
						"dataType" : "json" 
					}
				},
				"conditionalselect" : function (node, event) {
					return false;
				},
				'plugins': ["contextmenu", 'types', 'wholerow', "search"],
				"contextmenu": {
					"items": function (node) {
						var tmp = $.jstree.defaults.contextmenu.items();
						delete tmp.create.action;
						delete tmp.rename.action;
						tmp.rename = null;
						tmp.create = {
							"label": "添加下级车系",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								jp.openSaveDialog('添加下级车系', '${ctx}/test/treetable/dialog/carKind1/form?parent.id=' + obj.id + "&parent.name=" + obj.text, '800px', '500px');
							}
						};
						tmp.remove = {
							"label": "删除车系",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								jp.confirm('确认要删除车系吗？', function(){
									jp.loading();
									$.get("${ctx}/test/treetable/dialog/carKind1/delete?id="+obj.id, function(data){
										if(data.success){
											$('#carKind1jsTree').jstree("refresh");
											jp.success(data.msg);
										}else{
											jp.error(data.msg);
										}
									})

								});
							}
						}
						tmp.ccp = {
							"label": "编辑车系",
							"action": function (data) {
								var inst = jQuery.jstree.reference(data.reference),
									obj = inst.get_node(data.reference);
								var parentId = inst.get_parent(data.reference);
								var parent = inst.get_node(parentId);
								jp.openSaveDialog('编辑车系', '${ctx}/test/treetable/dialog/carKind1/form?id=' + obj.id, '800px', '500px');
							}
						}
						return tmp;
					}

				},
				"types":{
					'default' : { 'icon' : 'fa fa-folder' },
					'1' : {'icon' : 'fa fa-home'},
					'2' : {'icon' : 'fa fa-umbrella' },
					'3' : { 'icon' : 'fa fa-group'},
					'4' : { 'icon' : 'fa fa-file-text-o' }
				}

			}).bind("activate_node.jstree", function (obj, e) {
				var node = $('#carKind1jsTree').jstree(true).get_selected(true)[0];
				var opt = {
					silent: true,
					query:{
						'kind.id':node.id
					}
				};
				$("#kindId").val(node.id);
				$("#kindName").val(node.text);
				$('#car1Table').bootstrapTable('refresh',opt);
			}).on('loaded.jstree', function() {
				$("#carKind1jsTree").jstree('open_all');
			});
		});

		function refreshTree() {
	            $('#carKind1jsTree').jstree("refresh");
	        }
	</script>