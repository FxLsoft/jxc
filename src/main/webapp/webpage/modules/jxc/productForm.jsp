<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/product");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
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
				<a class="panelButton" href="${ctx}/jxc/product"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="product" action="${ctx}/jxc/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">名称：</label>
					<div class="col-sm-10">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">简码：</label>
					<div class="col-sm-10">
						<form:input path="brevityCode" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否计重（0：否；1：是）：</label>
					<div class="col-sm-10">
						<form:radiobuttons path="isWeight" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">计重编号：</label>
					<div class="col-sm-10">
						<form:input path="weightNo" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>经销商：</label>
					<div class="col-sm-10">
						<sys:gridselect url="${ctx}/jxc/agency/data" id="agency" name="agency.id" value="${product.agency.id}" labelName="agency.name" labelValue="${product.agency.name}"
							 title="选择经销商" cssClass="form-control required" fieldLabels="名称|联系人|联系方式|车牌号|地址" fieldKeys="name|linkman|phone|plateNumber|address" searchLabels="名称|联系方式|车牌号" searchKeys="name|phone|plateNumber" ></sys:gridselect>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>所属类型：</label>
					<div class="col-sm-10">
						<sys:treeselect id="category" name="category.id" value="${product.category.id}" labelName="category.name" labelValue="${product.category.name}"
							title="所属类型" url="/jxc/category/treeData" extId="${product.id}" cssClass="form-control required" allowClear="true"/>
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
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">价格表：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#priceList', priceRowIdx, priceTpl);priceRowIdx = priceRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th><font color="red">*</font>单位</th>
						<th><font color="red">*</font>换算比例</th>
						<th><font color="red">*</font>进价</th>
						<th><font color="red">*</font>预售价</th>
						<th><font color="red">*</font>是否基本单位（0：是；1：否）</th>
						<th>备注信息</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="priceList">
				</tbody>
			</table>
			<script type="text/template" id="priceTpl">//<!--
				<tr id="priceList{{idx}}">
					<td class="hide">
						<input id="priceList{{idx}}_id" name="priceList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="priceList{{idx}}_delFlag" name="priceList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="priceList{{idx}}_unit" name="priceList[{{idx}}].unit" type="text" value="{{row.unit}}"    class="form-control required"/>
					</td>
					
					
					<td>
						<input id="priceList{{idx}}_ratio" name="priceList[{{idx}}].ratio" type="text" value="{{row.ratio}}"    class="form-control required isIntGtZero"/>
					</td>
					
					
					<td>
						<input id="priceList{{idx}}_costPrice" name="priceList[{{idx}}].costPrice" type="text" value="{{row.costPrice}}"    class="form-control required isFloatGtZero"/>
					</td>
					
					
					<td>
						<input id="priceList{{idx}}_advancePrice" name="priceList[{{idx}}].advancePrice" type="text" value="{{row.advancePrice}}"    class="form-control required isFloatGtZero"/>
					</td>
					
					
					<td>
						<c:forEach items="${fns:getDictList('yes_no')}" var="dict" varStatus="dictStatus">
							<span><input id="priceList{{idx}}_isBasic${dictStatus.index}" name="priceList[{{idx}}].isBasic" type="radio" class="i-checks" value="${dict.value}" data-value="{{row.isBasic}}"><label for="priceList{{idx}}_isBasic${dictStatus.index}">${dict.label}</label></span>
						</c:forEach>
					</td>
					
					
					<td>
						<textarea id="priceList{{idx}}_remarks" name="priceList[{{idx}}].remarks" rows="4"    class="form-control ">{{row.remarks}}</textarea>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#priceList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var priceRowIdx = 0, priceTpl = $("#priceTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(product.priceList)};
					for (var i=0; i<data.length; i++){
						addRow('#priceList', priceRowIdx, priceTpl, data[i]);
						priceRowIdx = priceRowIdx + 1;
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