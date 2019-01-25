<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>测试校验管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">

		$(document).ready(function() {
			jp.ajaxForm("#inputForm",function(data){
				if(data.success){
				    jp.success(data.msg);
					jp.go("${ctx}/test/validation/testValidation");
				}else{
				    jp.error(data.msg);
				    $("#inputForm").find("button:submit").button("reset");
				}
			});

	        $('#newDate').datetimepicker({
				 format: "YYYY-MM-DD HH:mm:ss"
		    });
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
				<a class="panelButton" href="${ctx}/test/validation/testValidation"><i class="ti-angle-left"></i> 返回</a>
			</h3>
		</div>
		<div class="panel-body">
		<form:form id="inputForm" modelAttribute="testValidation" action="${ctx}/test/validation/testValidation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>浮点数字：</label>
					<div class="col-sm-10">
						<form:input path="num" htmlEscape="false"   max="69.3"  min="20.1" class="form-control required number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>整数：</label>
					<div class="col-sm-10">
						<form:input path="num2" htmlEscape="false"   max="30"  min="10" class="form-control required digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>字符串：</label>
					<div class="col-sm-10">
						<form:input path="str" htmlEscape="false" maxlength="65"  minlength="5"   class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>邮件：</label>
					<div class="col-sm-10">
						<form:input path="email" htmlEscape="false" maxlength="60"  minlength="10"   class="form-control required email"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>网址：</label>
					<div class="col-sm-10">
						<form:input path="url" htmlEscape="false" maxlength="30"  minlength="10"   class="form-control required url"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>日期：</label>
					<div class="col-sm-10">
							<div class='input-group form_datetime' id='newDate'>
			                    <input type='text'  name="newDate" class="form-control required"  value="<fmt:formatDate value="${testValidation.newDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>						            
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>备注信息：</label>
					<div class="col-sm-10">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>浮点数小于等于0：</label>
					<div class="col-sm-10">
						<form:input path="c1" htmlEscape="false"    class="form-control required isFloatLteZero"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>身份证号码：</label>
					<div class="col-sm-10">
						<form:input path="c2" htmlEscape="false"    class="form-control required isIdCardNo"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>QQ号码：</label>
					<div class="col-sm-10">
						<form:input path="c3" htmlEscape="false"    class="form-control required isQq"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>手机号码：</label>
					<div class="col-sm-10">
						<form:input path="c4" htmlEscape="false"    class="form-control required isMobile"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>中英文数字下划线：</label>
					<div class="col-sm-10">
						<form:input path="c5" htmlEscape="false"    class="form-control required stringCheck"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><font color="red">*</font>合法字符(a-z A-Z 0-9)：</label>
					<div class="col-sm-10">
						<form:input path="c6" htmlEscape="false"    class="form-control required isRightfulString"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">英语：</label>
					<div class="col-sm-10">
						<form:input path="en" htmlEscape="false"    class="form-control  isEnglish"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">汉子：</label>
					<div class="col-sm-10">
						<form:input path="zn" htmlEscape="false"    class="form-control  isChinese"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">汉英字符：</label>
					<div class="col-sm-10">
						<form:input path="enzn" htmlEscape="false"    class="form-control  isChineseChar"/>
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