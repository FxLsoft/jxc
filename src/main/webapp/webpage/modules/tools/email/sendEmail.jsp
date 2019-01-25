<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="decorator" content="ani"/>
      <!-- SUMMERNOTE -->
	<%@include file="/webpage/include/summernote.jsp" %>
</head>

<body>
    <div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">外部邮件</h3>
	</div>
	<div class="panel-body">
        <div class="row">

            <div class="col-sm-12 animated fadeInRight">
                <div class="mail-box-header">
                    <div class="pull-right tooltip-demo">
                       <button type="button" class="btn btn-white  btn-sm" onclick="sendLetter()"> <i class="fa fa-pencil"></i> 发送邮件</button>
                    </div>
                    <h2>
                    写邮件
                </h2>
                </div>
               
                <div class="mail-box">


                    <div class="mail-body">
					<form:form id="inputForm" modelAttribute="mailBox" action="${ctx}/tools/email/send" method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>发送到：</label>

                                <div class="col-sm-8">
                                  	  <input type="text" placeholder="输入多个邮件地址请用英文符号;隔开" id="emailAddress" name="emailAddress"  class="form-control" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">主题：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="title" name="title"  class="form-control" value="">
                                </div>
                            </div>
                              <input type="hidden" id="content" name="content"><!-- 内容 -->
 					</form:form>	
                    </div>

                    <div class="mail-text h-200">

                        <div class="summernote">
                           

                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="mail-body text-right tooltip-demo">
                    	
                    	 <button type="button" class="btn btn-primary  btn-sm" onclick="sendLetter()"> <i class="fa fa-reply"></i> 发送</button>
                    </div>
                    <div class="clearfix"></div>



                </div>
            
            </div>
			
        </div>
    </div>

	</div>
</div>
    <script>
    $(document).ready(function () {
    	//富文本初始化
		$('.summernote').summernote({
			height: 300,                
            lang: 'zh-CN',
            focus: true
        });
        

    });

        function sendLetter(){
            if($("#emailAddress").val()==''){
            	jp.alert('收件人不能为空！');
            	return;
            }
            if($("#title").val()==''){
              	jp.alert('标题不能为空！');
              	return;
              }
            $("#content").val($('.summernote').summernote("code"));
			jp.loading();
			$("#inputForm").submit();
	    }
    </script>

</body>

</html>