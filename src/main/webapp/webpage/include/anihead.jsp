<%@ page contentType="text/html;charset=UTF-8" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="author" content="http://www.jeeplus.org/"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=9,IE=10" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<script>var d = "${fns:getConfig('do'.concat('ma').concat('in').concat('.u').concat('rl'))}";</script>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<!-- 设置浏览器图标 -->
<link rel="shortcut icon" href="${ctxStatic}/favicon.ico">
<!-- 引入jeeplus ajax版本库文件，该文件压缩了jQuery，datatime等常用js文件以提高加载速度 -->
<link rel="stylesheet" href="${ctxStatic}/common/css/vendor.css" />
<script src="${ctxStatic}/common/js/vendor.js"></script>
<!-- 引入依赖的第三方插件 -->
<link href="${ctxStatic}/plugin/awesome/4.4/css/font-awesome.min.css" rel="stylesheet" />
<script src="${ctxStatic}/plugin/jquery-validation/1.14.0/jquery.validate.js" type="text/javascript"></script>
<script src="${ctxStatic}/plugin/jquery-validation/1.14.0/localization/messages_zh.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/plugin/jquery-validation/1.14.0/validation-methods.js" type="text/javascript"></script>
<link href="${ctxStatic}/plugin/icheck-1.x/skins/square/_all.css" rel="stylesheet" />
<script src="${ctxStatic}/plugin/icheck-1.x/icheck.js"></script>
<script type="text/javascript">

    $(document).ready(function(){
        $('input.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-${cookie.theme.value==null?"blue":cookie.theme.value}',
            radioClass: 'iradio_square-${cookie.theme.value==null?"blue":cookie.theme.value}',
            increaseArea: '20%' // optional
        });
    });


</script>
<!--省市区三级联动插件-->
<link href="${ctxStatic}/plugin/city-picker/css/city-picker.css" rel="stylesheet" type="text/css"/>
<script src="${ctxStatic}/plugin/city-picker/js/city-picker.data.js" type="text/javascript"></script>
<script src="${ctxStatic}/plugin/city-picker/js/city-picker.js" type="text/javascript"></script>

<!-- 引入layer插件,当做独立组件使用，不使用layui模块，该版本修复了chrome下花屏的bug -->
<script src="${ctxStatic}/plugin/layui/layer/layer.js"></script>
<script src="${ctxStatic}/plugin/layui/laytpl/laytpl.js"></script>
<%--<script src="${ctxStatic}/plugin/layui/dist/layui.js"></script>--%>

<!--引入webuploader-->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/webuploader-0.1.5/demo.css">
<script type="text/javascript" src="${ctxStatic}/plugin/webuploader-0.1.5/webuploader.js"></script>

<!-- 引入toastr -->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/toastr/toastr.css">
<script type="text/javascript" src="${ctxStatic}/plugin/toastr/toastr.min.js"></script>

<!--引入select2-->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/select2/css/select2.min.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/plugin/select2/css/select2-bootstrap.css">
<script type="text/javascript" src="${ctxStatic}/plugin/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/plugin/select2/js/i18n/zh-CN.js"></script>

<!-- 引入自定义文件 -->
<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
<link rel="stylesheet" id="theme" href="${ctxStatic}/common/css/app-${cookie.theme.value==null?'blue':cookie.theme.value}.css" />
<script src="${ctxStatic}/common/js/jeeplus.js"></script>

<!-- admin-ui -->
<link rel="stylesheet" href="${ctxStatic}/common/css/admin-ui.css" />

<script src="${ctxStatic}/plugin/jquery-print/jquery-migrate-1.2.1.min.js"></script>
<script src="${ctxStatic}/plugin/jquery-print/jquery.jqprint-0.3.js"></script>