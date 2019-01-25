<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>通知管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
					jp.success(data.msg);
					jp.go("${ctx}/oa/oaNotify");
				}else{
					jp.error(data.msg);
				}
			})
			
		});
	</script>
</head>
<body>
<div class="wrapper wrapper-content">				
<div class="row">
	<div class="col-md-12">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title"> 
				<a class="panelButton" href="${ctx}/oa/oaNotify${isSelf?'/self':'' }"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="oaNotify" action="${ctx}/oa/oaNotify/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>类型：</label>
					<div class="col-sm-10">
						<form:select path="type" class="form-control required">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('oa_notify_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>标题：</label>
					<div class="col-sm-10">
						<form:input path="title" htmlEscape="false" maxlength="200" class="form-control required"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>内容：</label>
					<div class="col-sm-10">
						<form:textarea path="content" htmlEscape="false" rows="6" maxlength="2000" class="form-control required"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">附件：</label>
					<div class="col-sm-10">
						<c:if test="${oaNotify.status ne '1'}">
							<sys:fileUpload path="files" value="${oaNotify.files}" type="file" uploadPath="/oa/notify"/>
						</c:if>
				         <c:if test="${oaNotify.status eq '1'}">
							 <sys:fileUpload path="files" value="${oaNotify.files}" type="file" uploadPath="/oa/notify"  readonly="true"/>
				         </c:if>
					</div>
				</div>
				
				<c:if test="${oaNotify.status ne '1'}">
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>状态：</label>
					<div class="col-sm-10">
					<form:radiobuttons path="status" items="${fns:getDictList('oa_notify_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>接受人：</label>
					<div class="col-sm-10">
					<sys:userselect id="oaNotifyRecord" name="oaNotifyRecordIds" value="${oaNotify.oaNotifyRecordIds}" labelName="oaNotifyRecordNames" labelValue="${oaNotify.oaNotifyRecordNames}"
							cssClass="form-control required"  isMultiSelected="true"/>
					</div>
				</div>
				</c:if>
				
				
			  <c:if test="${oaNotify.status eq '1'}">
				<div class="form-group">
				<table>
					  <tr>
				         <td  class="width-15 active">	<label class="pull-right">接受人：</label></td>
				         <td class="width-35" colspan="3"><table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
								<thead>
									<tr>
										<th>接受人</th>
										<th>接受部门</th>
										<th>阅读状态</th>
										<th>阅读时间</th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${oaNotify.oaNotifyRecordList}" var="oaNotifyRecord">
									<tr>
										<td>
											${oaNotifyRecord.user.name}
										</td>
										<td>
											${oaNotifyRecord.user.office.name}
										</td>
										<td>
											${fns:getDictLabel(oaNotifyRecord.readFlag, 'oa_notify_read', '')}
										</td>
										<td>
											<fmt:formatDate value="${oaNotifyRecord.readDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							已查阅：${oaNotify.readNum} &nbsp; 未查阅：${oaNotify.unReadNum} &nbsp; 总共：${oaNotify.readNum + oaNotify.unReadNum}</td>
				      </tr>
				  </table>
				  </div>
				</c:if>
				<c:if test="${oaNotify.status ne '1'}">
				<shiro:hasPermission name="oa:oaNotify:edit">
						<div class="col-lg-3"></div>
				        <div class="col-lg-6">
				             <div class="form-group text-center">
				                 <div>
				                     <button class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提 交</button>
				                 </div>
				             </div>
				        </div>
				</shiro:hasPermission>
				</c:if>
		</form:form>
		</div>				
	</div>
	</div>
</div>
</div>	
</body>
</html>