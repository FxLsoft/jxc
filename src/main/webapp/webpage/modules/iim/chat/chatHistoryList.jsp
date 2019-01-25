<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>聊天管理</title>
	<meta name="decorator" content="ani"/>
	<link href="${ctxStatic}/plugin/layui/dist/css/layim.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript">
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
</head>
<body>
	
	 <div class="layim-chat layim-chat-friend">
	 
	 			 
				<form:form id="searchForm" modelAttribute="chatHistory" action="${ctx}/iim/chatHistory/" method="post" class="form-inline">
					<input type="hidden" name="id" value="${chatHistory.id }"/>
					<input type="hidden" name="type" value="${chatHistory.type }"/>
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->

					<div class="row" style="padding-top: 10px">
						<div class="col-xs-1"></div>
						<div class="col-xs-6"><form:input path="msg" htmlEscape="false" maxlength="1024"  class=" form-control input-sm"/></div>
						<div class="col-xs-3"><button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button></div>
					 </div>
				</form:form>
				 <div class="layim-chat-main" style="width:70%; height:100%">
					 <ul>
					 <c:forEach items="${page.list}" var="chatHistory">
						 <c:if test="${fn:contains(chatHistory.sender, '_msg_')}"><!-- 如果是群组信息，取出实际的发送者信息 -->
							 <c:set var="sender" value="${fn:substringAfter(chatHistory.sender, '_msg_')}"/>
						 </c:if>
						 <c:if test="${fn:contains(chatHistory.sender, '_msg_') == false}"><!-- 如果是群组信息，取出实际的发送者信息 -->
							 <c:set var="sender" value="${chatHistory.sender}"/>
						 </c:if>
						 <c:if test="${fns:getUser().loginName != sender}">
							 <li>
								 <div class="layim-chat-user">
									 <img src="${fns:getByLoginName(sender).photo}">
									 <cite>${fns:getByLoginName(sender).name}<i><fmt:formatDate value="${chatHistory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></i></cite>
								 </div>
								 <div class="layim-chat-text">
									 <c:if test="${fn:contains(chatHistory.msg, 'img[')}">
									 	<img src="${fn:substring(chatHistory.msg, 4, fn:length(chatHistory.msg)-1)}"/>
								 	</c:if>
									 <c:if test="${fn:contains(chatHistory.msg, 'img[')==false}">
										 ${chatHistory.msg}
									 </c:if>
								 </div>
							 </li>
						 </c:if>
						 <c:if test="${fns:getUser().loginName == sender}">
							 <li class="layim-chat-mine">
								 <div class="layim-chat-user">
									 <img src="${fns:getByLoginName(sender).photo}">
									 <cite><i><fmt:formatDate value="${chatHistory.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></i>${fns:getByLoginName(sender).name}</cite>
								 </div>
								 <div class="layim-chat-text">
									 <c:if test="${fn:contains(chatHistory.msg, 'img[')}">
										 <img src="${fn:substring(chatHistory.msg, 4, fn:length(chatHistory.msg)-1)}"/>
									 </c:if>
									 <c:if test="${fn:contains(chatHistory.msg, 'img[')==false}">
										 ${chatHistory.msg}
									 </c:if>
								 </div>
							 </li>
						 </c:if>
					 </c:forEach>
					 </ul>



					</div>

				</div>
			</div>

                    </div>
        	</div>
		 	<table:page page="${page}"></table:page>
    </div>
</body>
</html>