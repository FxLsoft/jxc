<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>单据信息管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/jxc/operOrder");
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
				<a class="panelButton" href="${ctx}/jxc/operOrder"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="operOrder" action="${ctx}/jxc/operOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">编号：</label>
					<div class="col-sm-10">
						<form:input path="no" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单据类型（0：入库，1：出库，2：盘点）：</label>
					<div class="col-sm-10">
						<form:select path="type" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单据状态（0：保存，1：提交，2：作废，3：完成）：</label>
					<div class="col-sm-10">
						<form:select path="status" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单据来源（0：直接入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）：</label>
					<div class="col-sm-10">
						<form:select path="source" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('order_from')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">总计：</label>
					<div class="col-sm-10">
						<form:input path="totalPrice" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">实际总额：</label>
					<div class="col-sm-10">
						<form:input path="realPrice" htmlEscape="false"    class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">实付：</label>
					<div class="col-sm-10">
						<form:input path="realPay" htmlEscape="false"    class="form-control "/>
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
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">操作单据详情：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane fade in  active">
			<a class="btn btn-white btn-sm" onclick="addRow('#operOrderDetailList', operOrderDetailRowIdx, operOrderDetailTpl);operOrderDetailRowIdx = operOrderDetailRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>类型（-1：减库，1：加库）</th>
						<th>商品</th>
						<th>价格属性</th>
						<th>数量</th>
						<th>价格</th>
						<th>折扣</th>
						<th width="10">&nbsp;</th>
					</tr>
				</thead>
				<tbody id="operOrderDetailList">
				</tbody>
			</table>
			<script type="text/template" id="operOrderDetailTpl">//<!--
				<tr id="operOrderDetailList{{idx}}">
					<td class="hide">
						<input id="operOrderDetailList{{idx}}_id" name="operOrderDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="operOrderDetailList{{idx}}_delFlag" name="operOrderDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<c:forEach items="${fns:getDictList('oper_type')}" var="dict" varStatus="dictStatus">
							<span><input id="operOrderDetailList{{idx}}_operType${dictStatus.index}" name="operOrderDetailList[{{idx}}].operType" type="radio" class="i-checks" value="${dict.value}" data-value="{{row.operType}}"><label for="operOrderDetailList{{idx}}_operType${dictStatus.index}">${dict.label}</label></span>
						</c:forEach>
					</td>
					
					
					<td>
						<sys:gridselect url="${ctx}/jxc/product/data" id="operOrderDetailList{{idx}}_product" name="operOrderDetailList[{{idx}}].product.id" value="{{row.product.id}}" labelName="operOrderDetailList{{idx}}.product.name" labelValue="{{row.product.name}}"
							 title="选择商品" cssClass="form-control  " fieldLabels="名称|简码|是否计重|计重编号" fieldKeys="name|brevityCode|isWeight|weightNo" searchLabels="名称/简码" searchKeys="searchKey" ></sys:gridselect>
					</td>
					
					
					<td>
						<sys:gridselect url="${ctx}/jxc/price/data" id="operOrderDetailList{{idx}}_price" name="operOrderDetailList[{{idx}}].price.id" value="{{row.price.id}}" labelName="operOrderDetailList{{idx}}.price.costPrice" labelValue="{{row.price.costPrice}}"
							 title="选择价格属性" cssClass="form-control  " fieldLabels="单位|换算比例|进价|预售价|是否是基本单位" fieldKeys="unit|ratio|costPrice|advancePrice|isBasic" searchLabels="" searchKeys="" ></sys:gridselect>
					</td>
					
					
					<td>
						<input id="operOrderDetailList{{idx}}_amount" name="operOrderDetailList[{{idx}}].amount" type="text" value="{{row.amount}}"    class="form-control  isFloatGtZero"/>
					</td>
					
					
					<td>
						<input id="operOrderDetailList{{idx}}_operPrice" name="operOrderDetailList[{{idx}}].operPrice" type="text" value="{{row.operPrice}}"    class="form-control  isFloatGtZero"/>
					</td>
					
					
					<td>
						<input id="operOrderDetailList{{idx}}_discount" name="operOrderDetailList[{{idx}}].discount" type="text" value="{{row.discount}}"    class="form-control  isFloatGtZero"/>
					</td>
					
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#operOrderDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var operOrderDetailRowIdx = 0, operOrderDetailTpl = $("#operOrderDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(operOrder.operOrderDetailList)};
					for (var i=0; i<data.length; i++){
						addRow('#operOrderDetailList', operOrderDetailRowIdx, operOrderDetailTpl, data[i]);
						operOrderDetailRowIdx = operOrderDetailRowIdx + 1;
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