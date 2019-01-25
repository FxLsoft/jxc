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
        <div class="row">
            <div class="col-sm-3">
                   <div class="inbox-container">
			<div class="col email-options ps-container">
				<div class="padding-15">
					<div class="butt-container">
						<a href="${ctx}/iim/mailCompose/sendLetter" class="btn btn-primary btn-block btn-rounded">写信</a>
					</div>
					<ul class="main-options">
						<li>
							<a href="${ctx}/iim/mailBox/list?orderBy=sendtime desc">
								<span class="title"> &nbsp; 收件箱</span>
								<span class="badge pull-right">${noReadCount}/${mailBoxCount}</span>
							</a>	
						</li>
						<li>
							<a href="${ctx}/iim/mailCompose/list?status=1&orderBy=sendtime desc">
								<span class="title"> &nbsp; 已发送</span>
								<span class="badge pull-right">${mailComposeCount}</span>
							</a>	
						</li>
						<li>
							<a href="${ctx}/iim/mailCompose/list?status=0&orderBy=sendtime desc">
								<span class="title"> &nbsp; 草稿箱</span>
								<span class="badge pull-right">${mailDraftCount}</span>
							</a>	
						</li>
						<hr class="poor">
						<h5>标签</h5>
						<li>
							<a href="#">
								<span class="title"> &nbsp; 客户 <i class="fa fa-stop pull-right faorange"></i></span>
							</a>	
						</li>
						<li>
							<a href="#">
								<span class="title"> &nbsp; 社交 <i class="fa fa-stop pull-right fayellow"></i></span>
							</a>	
						</li>
						<li>
							<a href="#">
								<span class="title"> &nbsp; 家人 <i class="fa fa-stop pull-right facyan"></i></span>
							</a>	
						</li>
						<li>
							<a href="#">
								<span class="title"> &nbsp; 朋友 <i class="fa fa-stop pull-right fapurple"></i></span>
							</a>	
						</li>
					</ul>
				</div>
			</div>
			</div>
			<div class="row">
	            <div class="col-sm-12" style="align:center">
	            	<input id="btnCancel" class="btn btn-primary btn-bg" style="margin-left:100px" type="button" value="返 回" onclick="history.go(-1)">
	            </div>
        	</div>
            </div>
            <div class="col-sm-9 animated fadeInRight">
                <div class="mail-box-header">
                    <div class="pull-right tooltip-demo">
                       <button type="button" class="btn btn-white  btn-sm" onclick="saveLetter()"> <i class="fa fa-pencil"></i> 存为草稿</button>
                        <a href="${ctx}/iim/mailBox/list" class="btn btn-danger btn-sm" data-toggle="tooltip" data-placement="top" title="放弃"><i class="fa fa-times"></i> 放弃</a>
                    </div>
                    <h2>
                    写信
                </h2>
                </div>
               
                <div class="mail-box">


                    <div class="mail-body">
					<form:form id="inputForm" modelAttribute="mailBox" action="${ctx}/iim/mailCompose/save" method="post" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-2 control-label"><font color="red">*</font>发送到：</label>

                                <div class="col-sm-8">
			               		 <sys:userselect id="receiver" name="receiverIds" value="${mailBox.sender.id}" labelName="receiverNames" labelValue="${mailBox.sender.name}"
								   cssClass="form-control required" isMultiSelected="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">主题：</label>

                                <div class="col-sm-8">
                                    <input type="text" id="title" name="mail.title"  class="form-control" value="回复:${mailBox.mail.title }">
                                </div>
                            </div>
                          <input type="hidden" id="status" name="status" value="1"><!-- 0 草稿  1 已发送 -->
                          <input type="hidden" id="overview" name="mail.overview"><!-- 内容简介 -->
                    	  <input type="hidden" id="content" name="mail.content"><!-- 内容 -->
 					</form:form>	
                    </div>

                    <div class="mail-text h-200">

                        <div class="summernote">
                           

                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="mail-body text-right tooltip-demo">
                    	
                    	 <button type="button" class="btn btn-primary  btn-sm" onclick="sendLetter()"> <i class="fa fa-reply"></i> 发送</button>
                        <a href="${ctx}/iim/mailBox/list" class="btn btn-danger btn-sm" data-toggle="tooltip" data-placement="top" title="Discard email"><i class="fa fa-times"></i> 放弃</a>
                   		 <button type="button" class="btn btn-white  btn-sm" onclick="saveLetter()"> <i class="fa fa-pencil"></i> 存为草稿</button>
                    </div>
                    <div class="clearfix"></div>



                </div>
            
            </div>
			
        </div>
    </div>

   <div style="display:none" id="contentView">
   	  ${mailBox.mail.content}
   </div>

    <script>
        $(document).ready(function () {
        	//富文本初始化
			$('.summernote').summernote({
				height: 300,                
                lang: 'zh-CN',
                focus: true
            });

            var receiverEmail = "<br/><br/><br/>------------------ 原始邮件 ------------------<br/>";
            receiverEmail += "发件人:${(fns:getUserById(mailBox.sender.id)).name}<br/>";
            receiverEmail += '发送时间:<fmt:formatDate value="${mailBox.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/><br/>';
            receiverEmail += "收件人:${(fns:getUserById(mailBox.receiver.id)).name}<br/>";
            receiverEmail += "主题:${mailBox.mail.title}<br/>";
            receiverEmail += $("#contentView").text();
            $('.summernote').summernote("code",receiverEmail);
        });

        function sendLetter(){
        	if($("#receiverId").val()=='' || $("#receiverId").val()==undefined){
            	jp.alert('收件人不能为空！');
            	return;
            }
            if($("#title").val()==''){
              	jp.alert('标题不能为空！');
              	return;
              }
            $("#status").val("1");
			$("#content").val($('.summernote').summernote("code"));
			$("#overview").val($(".note-editable").text().substring(0,20));
			jp.loading();
			$("#inputForm").submit();
	    }
        function saveLetter(){
        	if($("#title").val()==''){
              	jp.alert('标题不能为空！');
              	return;
              }
        	$("#status").val("0");
 			$("#content").val($('.summernote').summernote("code"));
 			$("#overview").val($(".note-editable").text().substring(0,20));
 			jp.loading();
 			$("#inputForm").submit();
	    }
    </script>

</body>

</html>