<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>票务代理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
	        $('#inDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});

		function save() {
            var isValidate = jp.validateForm('#inputForm');//校验表单
            if(!isValidate){
                return false;
			}else{
                jp.loading();
                jp.post("${ctx}/test/onetomany/dialog/testDataMain1/save",$('#inputForm').serialize(),function(data){
                    if(data.success){
                        jp.getParent().refresh();
                        var dialogIndex = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(dialogIndex);
                        jp.success(data.msg)

                    }else{
                        jp.error(data.msg);
                    }
                })
			}

        }
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
			$(list+idx).find(".form_datetime").each(function(){
				 $(this).datetimepicker({
					 format: "YYYY-MM-DD HH:mm:ss"
			    });
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body class="bg-white">
		<form:form id="inputForm" modelAttribute="testDataMain1" action="${ctx}/test/onetomany/dialog/testDataMain1/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table class="table table-bordered">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属用户：</label></td>
					<td class="width-35">
						<sys:userselect id="tuser" name="tuser.id" value="${testDataMain1.tuser.id}" labelName="tuser.name" labelValue="${testDataMain1.tuser.name}"
							    cssClass="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属部门：</label></td>
					<td class="width-35">
						<sys:treeselect id="office" name="office.id" value="${testDataMain1.office.id}" labelName="office.name" labelValue="${testDataMain1.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>归属区域：</label></td>
					<td class="width-35">
						<sys:treeselect id="area" name="area.id" value="${testDataMain1.area.id}" labelName="area.name" labelValue="${testDataMain1.area.name}"
							title="区域" url="/sys/area/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>性别：</label></td>
					<td class="width-35">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>加入日期：</label></td>
					<td class="width-35">
							<div class='input-group form_datetime' id='inDate'>
			                    <input type='text'  name="inDate" class="form-control required"  value="<fmt:formatDate value="${testDataMain1.inDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">火车票：</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">飞机票：</a>
                </li>
				<li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="false">汽车票：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild11List', testDataChild11RowIdx, testDataChild11Tpl);testDataChild11RowIdx = testDataChild11RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th><font color="red">*</font>出发地</th>
						<th><font color="red">*</font>目的地</th>
						<th><font color="red">*</font>出发时间</th>
						<th><font color="red">*</font>代理价格</th>
						<th>是否有票</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="testDataChild11List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild11Tpl">//<!--
				<tr id="testDataChild11List{{idx}}">
					<td class="hide">
						<input id="testDataChild11List{{idx}}_id" name="testDataChild11List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild11List{{idx}}_delFlag" name="testDataChild11List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild11List{{idx}}_startArea" name="testDataChild11List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild11List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild11List{{idx}}_endArea" name="testDataChild11List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild11List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="testDataChild11List{{idx}}_starttime">
		                    <input type='text'  name="testDataChild11List[{{idx}}].starttime" class="form-control required"  value="{{row.starttime}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<input id="testDataChild11List{{idx}}_price" name="testDataChild11List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required isFloatGteZero"/>
					</td>
					
					
					<td>
						<select id="testDataChild11List{{idx}}_isHave" name="testDataChild11List[{{idx}}].isHave" data-value="{{row.isHave}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<textarea id="testDataChild11List{{idx}}_remarks" name="testDataChild11List[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild11List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild11RowIdx = 0, testDataChild11Tpl = $("#testDataChild11Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMain1.testDataChild11List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild11List', testDataChild11RowIdx, testDataChild11Tpl, data[i]);
						testDataChild11RowIdx = testDataChild11RowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-2" class="tab-pane fade">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild12List', testDataChild12RowIdx, testDataChild12Tpl);testDataChild12RowIdx = testDataChild12RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th><font color="red">*</font>出发地</th>
						<th><font color="red">*</font>目的地</th>
						<th><font color="red">*</font>出发时间</th>
						<th><font color="red">*</font>代理价格</th>
						<th><font color="red">*</font>是否有票</th>
						<th><font color="red">*</font>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="testDataChild12List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild12Tpl">//<!--
				<tr id="testDataChild12List{{idx}}">
					<td class="hide">
						<input id="testDataChild12List{{idx}}_id" name="testDataChild12List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild12List{{idx}}_delFlag" name="testDataChild12List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild12List{{idx}}_startArea" name="testDataChild12List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild12List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild12List{{idx}}_endArea" name="testDataChild12List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild12List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="testDataChild12List{{idx}}_startTime">
		                    <input type='text'  name="testDataChild12List[{{idx}}].startTime" class="form-control required"  value="{{row.startTime}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<input id="testDataChild12List{{idx}}_price" name="testDataChild12List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required isFloatGteZero"/>
					</td>
					
					
					<td>
						<select id="testDataChild12List{{idx}}_isHave" name="testDataChild12List[{{idx}}].isHave" data-value="{{row.isHave}}" class="form-control m-b  required">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<textarea id="testDataChild12List{{idx}}_remarks" name="testDataChild12List[{{idx}}].remarks" rows="4"    class="form-control required">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild12List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild12RowIdx = 0, testDataChild12Tpl = $("#testDataChild12Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMain1.testDataChild12List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild12List', testDataChild12RowIdx, testDataChild12Tpl, data[i]);
						testDataChild12RowIdx = testDataChild12RowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-3" class="tab-pane fade">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild13List', testDataChild13RowIdx, testDataChild13Tpl);testDataChild13RowIdx = testDataChild13RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th><font color="red">*</font>出发地</th>
						<th><font color="red">*</font>目的地</th>
						<th>代理价格</th>
						<th>是否有票</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="testDataChild13List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild13Tpl">//<!--
				<tr id="testDataChild13List{{idx}}">
					<td class="hide">
						<input id="testDataChild13List{{idx}}_id" name="testDataChild13List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild13List{{idx}}_delFlag" name="testDataChild13List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild13List{{idx}}_startArea" name="testDataChild13List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild13List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild13List{{idx}}_endArea" name="testDataChild13List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild13List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<input id="testDataChild13List{{idx}}_price" name="testDataChild13List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control "/>
					</td>
					
					
					<td>
						<select id="testDataChild13List{{idx}}_isHave" name="testDataChild13List[{{idx}}].isHave" data-value="{{row.isHave}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<textarea id="testDataChild13List{{idx}}_remarks" name="testDataChild13List[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild13List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild13RowIdx = 0, testDataChild13Tpl = $("#testDataChild13Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMain1.testDataChild13List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild13List', testDataChild13RowIdx, testDataChild13Tpl, data[i]);
						testDataChild13RowIdx = testDataChild13RowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
		</form:form>
</body>
</html>