<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>

<head>

    <title>通讯录</title>
    <meta name="decorator" content="ani"/>
	<script type="text/javascript">
	
		function search(n,s){
			$("#searchForm").attr("action","${ctx}/iim/contact/index");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>

<body>
        <div class="wrapper wrapper-content">
		<div class="panel panel-default">
			<div class="panel-body">
                        <h2>我的通讯录</h2>
                        <p>
                          可以向通讯录中的人发送，站内信，短消息和发起会话。
                        </p>
                        	<form:form id="searchForm" modelAttribute="user" action="${ctx}/iim/contact/index" method="post" class="input-group">
								<form:input path="name" htmlEscape="false" maxlength="50" placeholder="查找联系人" class="input form-control"/>
                                <span class="input-group-btn">
                                        <button type="button" class="btn btn btn-primary" onclick="return search();"> <i class="fa fa-search"></i> 搜索</button>
                                </span>
							</form:form>
                            
                        <div class="clients-list" style="margin-top: 20px;">
                            <ul class="nav nav-tabs">
                                <span class="pull-right small text-muted">${fn:length(list)} 个联系人</span>
                            </ul>
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane active">
                                    <div class="full-height-scroll">
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <tbody>
                                              
                                               <c:forEach items="${list}" var="user">
													<tr>
														<td class="client-avatar"><img alt="image" style="width: 50px;max-height: 50px;"src="<c:if test="${user.photo == null || user.photo==''}">${ctxStatic}/common/images/flat-avatar.png</c:if> <c:if test="${user.photo != null && user.photo!=''}">${user.photo}</c:if>"> </td>
														<td><a data-toggle="tab" href="#contact-1" class="client-link">${user.name}</a>
														<td>${user.office.name}</td>
														<td class="contact-type"><i class="fa fa-envelope"> </i>
                                                            ${user.email}</td>
                                                        <td class="contact-type"><i class="fa fa-mobile"> </i>
                                                             ${user.mobile}</td>
                                                        <td class="contact-type"><i class="fa fa-phone"> </i>
                                                             ${user.phone}</td>
                                                         <td class="contact-type"><a href="${ctx}/iim/mailCompose/sendLetter?id=${user.id}" class="btn btn-info btn-xs"><i class="fa fa-envelope"> 站内信</i></a>
                                                        </td>
                                                         <td class="client-status">
                                                         <c:if test="${user.loginFlag == '1'}">
                                                         	<span class="label label-primary">激活</span>
                                                         </c:if>
                                                          <c:if test="${user.loginFlag == '0'}">
                                                         	<span class="label label-error">未激活</span>
                                                         </c:if>
                                                        
                                                        </td>
													</tr>
												</c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                   
                            </div>

                        </div>
                    </div>
                </div>
            </div>
</body>

</html>