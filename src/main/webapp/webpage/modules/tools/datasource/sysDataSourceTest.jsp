<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>功能测试</title>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="decorator" content="ani"/>
    <%@ include file="/webpage/include/bootstraptable.jsp"%>
    <%@include file="/webpage/include/treeview.jsp" %>
</head>
<body>
<div class="wrapper wrapper-content">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">数据库测试</h3>
        </div>
        <div class="panel-body">
        <form:form id="inputForm" modelAttribute="sysDataSource"  method="post" class="form-horizontal animated fadeInRight">
            <div class="row">
                <label class="col-sm-3 control-label"><font color="red">*</font>选择数据库：</label>
                <div class="col-sm-7">
                    <form:select path="id" class="form-control">
                        <form:option value="">-- 请选择一个数据库 --</form:option>
                        <form:options items="${sysDataSourceList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
                    </form:select>
                </div>
                <div class="col-sm-2">

                </div>
            </div>
            <br>
            <div class="row">
                <label class="col-sm-3 control-label"><font color="red">*</font>选择表：</label>
                <div class="col-sm-7">
                   <select id="tableName" name="tableName" class="form-control">
                       <option value="">-- 请选择一个表 --</option>
                    </select>
                </div>
                <div class="col-sm-2">
                </div>
            </div>
            <br>
            <div class="row">
                <label class="col-sm-3 control-label"><font color="red">*</font>选择列：</label>
                <div class="col-sm-7">
                   <div id="column"></div>
                </div>
                <div class="col-sm-2">

                    <button type="button" class="btn btn-primary  btn-sm" onclick="queryByColumn()"> <i class="fa fa-reply"></i> 生成sql</button>
                </div>
            </div>
            <br>
            <div class="row ">
                    <label class="col-sm-3 control-label"><font color="red">*</font>SQL内容：</label>
                    <div class="col-sm-7">
                           <textarea id="sql" name="sql"  class="form-control" required="" aria-required="true"></textarea>
                    </div>
                    <div class="col-sm-2">

                        <button type="button" class="btn btn-primary  btn-sm" onclick="query()"> <i class="fa fa-reply"></i> 查询</button>
                    </div>

            </div>

        </form:form>
        <hr>
        <div id="table"></div>
        </div>
    </div>
</div>


<script>

        $(function () {
            $("#id").change(function(){
                link();
            });

            $("#tableName").change(function(){
                getColumn();
            });


        })
        function query() {
            if ($("#id").val() == '') {
                jp.alert('必须选择一个数据库！');
                return;
            }

            if ($("#sql").val() == '') {
               jp.alert('sql不能为空！');
                return;
            }
            jp.post("${ctx}/tools/sysDataSource/executeSql", $('#inputForm').serialize(), function (data) {
                var datas = [];
                var columns = [{
                    checkbox: true

                }]
                var selectedItems = $("input[name='column']:checked");
                for(var i =0; i<selectedItems.length; i++){
                    columns.push({field:$(selectedItems[i]).val(), title:$(selectedItems[i]).val(), sortable:true})
                }

                var d = data.body.result;
                for(var i = 0; i < d.length; i++){
                    var obj = {};
                 for(var x in d[i] ){
                     obj[x.toLowerCase()] = d[i][x];
                 }
                 datas.push(obj);
                }

                $('#table').bootstrapTable('destroy').bootstrapTable({
                    //请求方法
                    data:datas,
                    columns: columns

                });

            });
        }

        function link() {
            $("#tableName").html('<option value="">-- 请选择一个表 --</option>');
            if ($("#id").val() == '') {
                return;
            }

            jp.post("${ctx}/tools/sysDataSource/getTable", $('#inputForm').serialize(), function (data) {
                var list = data.body.result;
                for(var i=0; i<list.length; i++){
                    $("#tableName").append("<option value='"+list[i].name+"'>"+list[i].nameAndComments+"</option>");
                }



            });
        }
        function getColumn() {
            if ($("#id").val() == '') {
                jp.alert('必须选择一个数据库！');
                return;
            }

            $("#column").html("");
            jp.post("${ctx}/tools/sysDataSource/getColumn", $('#inputForm').serialize(), function (data) {
                var list = data.body.result;
                for(var i=0; i<list.length; i++){
                    $("#column").append('<label><input name="column" type="checkbox" value="'+list[i].name+'" />'+list[i].name+' </label>');
                }



            });
        }

        function queryByColumn() {
            if ($("#id").val() == '') {
                jp.alert('必须选择一个数据库！');
                return;
            }

            if ($("#tableName").val() == '') {
                jp.alert('必须选择一个表！');
                return;
            }

            var sql= "select "
            var selectedItems = $("input[name='column']:checked");
            debugger
            if (selectedItems.length == 0) {
                jp.alert('必须至少选择一列！');
                return;
            }
            for(var i =0; i<selectedItems.length; i++){
                sql +=  " "+ $(selectedItems[i]).val()+","
            }
            sql = sql.substring(0, sql.length-1);
            sql += " from "+$("#tableName").find("option:selected").val() ;
            $("#sql").val(sql);
        }
    </script>

</body>
</html>