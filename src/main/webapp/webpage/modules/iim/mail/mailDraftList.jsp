<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>草稿箱</title>
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
						<li  class="activeli">
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

					<div class="row">
					   <div class="col-sm-6" style="left:-5px">
	                    <h2 >
	                      	 草稿箱 (${mailDraftCount})
	                </h2>
	                </div>
					<div class="col-sm-6">
                     <form:form  id="searchForm" modelAttribute="mailCompose" action="${ctx}/iim/mailCompose/?status=0" method="post" class="pull-right mail-search">
                        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn  id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"></table:sortColumn><!-- 支持排序 -->
                        <div class="input-group">
                        	<form:input path="mail.title" htmlEscape="false" maxlength="128"  class=" form-control" placeholder="搜索邮件标题，正文等"/>
                            <div class="input-group-btn">
                                <button id="btnSubmit" type="submit" class="btn btn-primary">
                                    搜索
                                </button>
                            </div>
                        </div>
                    </form:form>
                    </div>
                	</div>
                 
                    <div class="mail-tools tooltip-demo m-t-md">
                        <div class="btn-group pull-right">
                        	${page }
                        </div>
                          <button class="btn btn-primary" data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新邮件列表"><i class="fa fa-refresh"></i> 刷新</button>
                        <button class="btn btn-default" data-toggle="tooltip" data-placement="top" title="标为已读"><i class="fa fa-eye"></i>
                        </button>
                        <button class="btn btn-default" data-toggle="tooltip" data-placement="top" title="标为重要"><i class="fa fa-exclamation"></i>
                        </button>
                        <table:delRow url="${ctx}/iim/mailCompose/deleteAllDraft" id="contentTable"></table:delRow><!-- 删除按钮 -->


                    </div>
                </div>
                <div class="mail-box">

                    <table id="contentTable" class="table table-hover table-mail">
                    	<thead> 
                    		<tr>
                    			<th class="check-mail">
	                                    <input type="checkbox" class="i-checks">
	                                </th>
                    			<th  class="sort-column receiver.name">收件人</th>
                    			<th  class="sort-column title">标题</th>
                    			<th  class="sort-column overview">内容</th>
                    			<th  class="sort-column sendtime">时间</th>
                    			<th>操作</th>
                    		</tr>
                    	</thead>
                        <tbody>
                        
                        	<c:forEach items="${page.list}" var="mailCompose">
								<tr>
									<td class="check-mail">
	                                    <input id="${mailCompose.id}" type="checkbox" class="i-checks">
	                                </td>
	                                <td class=""><a href="${ctx}/iim/mailCompose/draftDetail?id=${mailCompose.id}">
                                	${mailCompose.receiver.name}
									</a></td>
                                	<td class="mail-ontact"><a href="${ctx}/iim/mailCompose/draftDetail?id=${mailCompose.id}">
                                		
										${mailCompose.mail.title}
									</a></td>
									<td class="mail-subject"><a href="${ctx}/iim/mailCompose/draftDetail?id=${mailCompose.id}">
										${mailCompose.mail.overview}
									</a>
	                                </td>
	                                <td class="mail-date">${fns:formatDateTime(mailCompose.sendtime)}</td>
									
									<td>
										<a href="${ctx}/iim/mailCompose/delete?id=${mailCompose.id}" onclick="return jp.confirm('确认要删除该站内信吗？', this.href)"   class="btn btn-info btn-xs btn-danger"><i class="fa fa-trash"></i> 删除</a>
									</td>
								</tr>
							</c:forEach>
                         
                        </tbody>
                    </table>


                </div>
            </div>
        </div>
    </div>



    <script>

        
	   function search(){//查询，页码清零
			$("#pageNo").val(0);
			$("#searchForm").submit();
	   		return false;
	   }

		function reset(){//重置，页码清零
			$("#pageNo").val(0);
			$("#searchForm div.form-group input").val("");
			$("#searchForm div.form-group select").val("");
			$("#searchForm").submit();
	  		return false;
	 	 }
		function sortOrRefresh(){//刷新或者排序，页码不清零
			
			$("#searchForm").submit();
	 		return false;
	 	}
		function page(n,s){//翻页
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
			$("span.page-size").text(s);
			return false;
		}
    </script>

</body>
</html>