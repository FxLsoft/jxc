<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>

<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
	<title><sitemesh:title/></title>
	<sitemesh:head/>
	<link rel="stylesheet" href="${ctxStatic}/common/css/vendor.css" />
	<script type="text/javascript" src="https://cdn.bootcss.com/vue/2.5.22/vue.common.js"></script>
	<script src="${ctxStatic}/common/js/vendor.js"></script>
</head>
<body>
	<sitemesh:body/>
</body>
</html>