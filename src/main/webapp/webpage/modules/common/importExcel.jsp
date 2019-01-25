<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>导入Excel</title>
    <meta name="decorator" content="ani"/>
    <script>
        function importExcel(url, fn) {
            var importForm =$("#importForm")[0];
            jp.loading('  正在导入，请稍等...');
            jp.uploadFile(importForm, url,function (data) {
               fn(data);
            })
        }
    </script>
</head>
<body class="bg-white">
    <div class="wrapper wrapper-content">
        <div id="importBox" >
            <form id="importForm" action="${url}" method="post" enctype="multipart/form-data"
                  style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');">
                <input id="uploadFile" name="file" type="file" class="form-control" style="width:300px"/>
                <br/>
                导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！　　

            </form>
        </div>
    </div>
</body>
</html>
