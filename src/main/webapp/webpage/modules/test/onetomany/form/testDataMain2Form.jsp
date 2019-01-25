<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>票务代理管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/test/onetomany/form/testDataMain2");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});
			
	        $('#inDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
		});
		
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
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/test/onetomany/form/testDataMain2"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="testDataMain2" action="${ctx}/test/onetomany/form/testDataMain2/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>归属用户：</label>
					<div class="col-sm-10">
						<sys:userselect id="tuser" name="tuser.id" value="${testDataMain2.tuser.id}" labelName="tuser.name" labelValue="${testDataMain2.tuser.name}"
							    cssClass="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>归属部门：</label>
					<div class="col-sm-10">
						<sys:treeselect id="office" name="office.id" value="${testDataMain2.office.id}" labelName="office.name" labelValue="${testDataMain2.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>归属区域：</label>
					<div class="col-sm-10">
						<sys:treeselect id="area" name="area.id" value="${testDataMain2.area.id}" labelName="area.name" labelValue="${testDataMain2.area.name}"
							title="区域" url="/sys/area/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>性别：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>加入日期：</label>
					<div class="col-sm-10">
							<div class='input-group form_datetime' id='inDate'>
			                    <input type='text'  name="inDate" class="form-control required"  value="<fmt:formatDate value="${testDataMain2.inDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</div>
				</div>
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
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild21List', testDataChild21RowIdx, testDataChild21Tpl);testDataChild21RowIdx = testDataChild21RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
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
				<tbody id="testDataChild21List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild21Tpl">//<!--
				<tr id="testDataChild21List{{idx}}">
					<td class="hide">
						<input id="testDataChild21List{{idx}}_id" name="testDataChild21List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild21List{{idx}}_delFlag" name="testDataChild21List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild21List{{idx}}_startArea" name="testDataChild21List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild21List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild21List{{idx}}_endArea" name="testDataChild21List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild21List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="testDataChild21List{{idx}}_starttime">
		                    <input type='text'  name="testDataChild21List[{{idx}}].starttime" class="form-control required"  value="{{row.starttime}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<input id="testDataChild21List{{idx}}_price" name="testDataChild21List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required isFloatGteZero"/>
					</td>
					
					
					<td>
						<select id="testDataChild21List{{idx}}_isHave" name="testDataChild21List[{{idx}}].isHave" data-value="{{row.isHave}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<textarea id="testDataChild21List{{idx}}_remarks" name="testDataChild21List[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild21List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild21RowIdx = 0, testDataChild21Tpl = $("#testDataChild21Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMain2.testDataChild21List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild21List', testDataChild21RowIdx, testDataChild21Tpl, data[i]);
						testDataChild21RowIdx = testDataChild21RowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-2" class="tab-pane fade">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild22List', testDataChild22RowIdx, testDataChild22Tpl);testDataChild22RowIdx = testDataChild22RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
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
				<tbody id="testDataChild22List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild22Tpl">//<!--
				<tr id="testDataChild22List{{idx}}">
					<td class="hide">
						<input id="testDataChild22List{{idx}}_id" name="testDataChild22List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild22List{{idx}}_delFlag" name="testDataChild22List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild22List{{idx}}_startArea" name="testDataChild22List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild22List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild22List{{idx}}_endArea" name="testDataChild22List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild22List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<div class='input-group form_datetime' id="testDataChild22List{{idx}}_startTime">
		                    <input type='text'  name="testDataChild22List[{{idx}}].startTime" class="form-control required"  value="{{row.startTime}}"/>
		                    <span class="input-group-addon">
		                        <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>						            
					</td>
					
					
					<td>
						<input id="testDataChild22List{{idx}}_price" name="testDataChild22List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required isFloatGteZero"/>
					</td>
					
					
					<td>
						<select id="testDataChild22List{{idx}}_isHave" name="testDataChild22List[{{idx}}].isHave" data-value="{{row.isHave}}" class="form-control m-b  required">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<textarea id="testDataChild22List{{idx}}_remarks" name="testDataChild22List[{{idx}}].remarks" rows="4"    class="form-control required">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild22List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild22RowIdx = 0, testDataChild22Tpl = $("#testDataChild22Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMain2.testDataChild22List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild22List', testDataChild22RowIdx, testDataChild22Tpl, data[i]);
						testDataChild22RowIdx = testDataChild22RowIdx + 1;
					}
				});
			</script>
			</div>
				<div id="tab-3" class="tab-pane fade">
			<a class="btn btn-white btn-sm" onclick="addRow('#testDataChild23List', testDataChild23RowIdx, testDataChild23Tpl);testDataChild23RowIdx = testDataChild23RowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
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
				<tbody id="testDataChild23List">
				</tbody>
			</table>
			<script type="text/template" id="testDataChild23Tpl">//<!--
				<tr id="testDataChild23List{{idx}}">
					<td class="hide">
						<input id="testDataChild23List{{idx}}_id" name="testDataChild23List[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="testDataChild23List{{idx}}_delFlag" name="testDataChild23List[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild23List{{idx}}_startArea" name="testDataChild23List[{{idx}}].startArea.id" value="{{row.startArea.id}}" labelName="testDataChild23List{{idx}}.startArea.name" labelValue="{{row.startArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td  class="max-width-250">
						<sys:treeselect id="testDataChild23List{{idx}}_endArea" name="testDataChild23List[{{idx}}].endArea.id" value="{{row.endArea.id}}" labelName="testDataChild23List{{idx}}.endArea.name" labelValue="{{row.endArea.name}}"
							title="区域" url="/sys/area/treeData" cssClass="form-control  required" allowClear="true" notAllowSelectParent="true"/>
					</td>
					
					
					<td>
						<input id="testDataChild23List{{idx}}_price" name="testDataChild23List[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control "/>
					</td>
					
					
					<td>
						<select id="testDataChild23List{{idx}}_isHave" name="testDataChild23List[{{idx}}].isHave" data-value="{{row.isHave}}" class="form-control m-b  ">
							<option value=""></option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</td>
					
					
					<td>
						<textarea id="testDataChild23List{{idx}}_remarks" name="testDataChild23List[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#testDataChild23List{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var testDataChild23RowIdx = 0, testDataChild23Tpl = $("#testDataChild23Tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(testDataMain2.testDataChild23List)};
					for (var i=0; i<data.length; i++){
						addRow('#testDataChild23List', testDataChild23RowIdx, testDataChild23Tpl, data[i]);
						testDataChild23RowIdx = testDataChild23RowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
		<c:if test="${mode == 'add' || mode=='edit'}">
				<div class="col-lg-3"></div>
		        <div class="col-lg-6">
		             <div class="form-group text-center">
		                 <div>
		                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
		                 </div>
		             </div>
		        </div>
		</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>
</body>
</html>