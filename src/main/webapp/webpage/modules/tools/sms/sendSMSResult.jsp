<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="decorator" content="ani"/>

</head>

<body>

	
   <div class="middle-box text-center lockscreen animated fadeInDown">
        <div>
            <div class="m-b-md">
                <img alt="image" style="width:150px;"class="img-circle circle-border" src="${ctxStatic}/common/images/success.jpg">
            </div>
             <sys:message  content="${result}"/>
            
            <form class="m-t" role="form" action="index.html">
                <a href="${ctx}/tools/sms" class="btn btn-primary block full-width">返回</a>
            </form>
        </div>
    </div>
</body>

</html>