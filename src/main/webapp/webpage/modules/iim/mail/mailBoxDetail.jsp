<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>查看邮件</title>
   	<meta name="decorator" content="ani"/>

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
						<li class="activeli">
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
                </div>
            <div class="col-sm-9 animated fadeInRight">
                <div class="mail-box-header">
                    <div class="pull-right tooltip-demo">
                    	<a href="${ctx}/iim/mailBox/list?orderBy=sendtime desc" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="返回"><i class="fa fa-backward"></i> 返回</a>
                        <a href="${ctx}/iim/mailCompose/replyLetter?id=${mailBox.id}" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="回复"><i class="fa fa-reply"></i> 回复</a>
                        <a href="#" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="打印邮件"><i class="fa fa-print"></i> </a>
                        <a href="#" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="top" title="标为垃圾邮件"><i class="fa fa-trash-o"></i> </a>
                    </div>
                    <h2>
                    查看邮件
                </h2>
                    <div class="mail-tools tooltip-demo m-t-md">


                        <h3>
                        <span class="font-noraml">主题： </span>${mailBox.mail.title }
                    </h3>
                        <h5>
                        <span class="pull-right font-noraml"><fmt:formatDate value="${mailBox.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        <span class="font-noraml">发件人： </span>${(fns:getUserById(mailBox.sender)).name}
                        </h5>
                        <h5>
                        <span class="font-noraml">收件人： </span>${(fns:getUserById(mailBox.receiver)).name}
                    </h5>
                    </div>
                </div>
                <div class="mail-box">


                    <div id="content" class="mail-body">
                       ${fns:unescapeHtml(mailBox.mail.content)}
                    </div>
                    <div class="mail-attachment">
                     
                    </div>
                    <div class="mail-body text-right tooltip-demo">
                        <a class="btn btn-sm btn-white" href="${ctx}/iim/mailCompose/replyLetter?id=${mailBox.id}"><i class="fa fa-reply"></i> 回复</a>
                       <!--   <a class="btn btn-sm btn-white" href="#"><i class="fa fa-arrow-right"></i> 下一封</a>
                        <button title="" data-placement="top" data-toggle="tooltip" type="button" data-original-title="打印这封邮件" class="btn btn-sm btn-white"><i class="fa fa-print"></i> 打印</button>-->
                        <button title="" onclick="return jp.confirm('确认要删除该站内信吗？', '${ctx}/iim/mailBox/delete?id=${mailBox.id}')"  data-placement="top" data-toggle="tooltip" data-original-title="删除邮件" class="btn btn-sm btn-white"><i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                    <div class="clearfix"></div>


                </div>
            </div>
        </div>
    </div>
</body>

</html>