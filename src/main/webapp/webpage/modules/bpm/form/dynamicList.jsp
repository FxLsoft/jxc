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
            <h3 class="panel-title">请假表单列表</h3>
        </div>
        <div class="panel-body">

            <!-- 搜索 -->
            <div class="accordion-group">
                <div id="collapseTwo" class="accordion-body collapse">
                    <div class="accordion-inner">
                        <form id="searchForm" class="form form-horizontal well clearfix">
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="归属部门：">归属部门：</label>
                                <sys:treeselect id="office" name="office.id" labelName="office.name"
                                                title="部门" url="/sys/office/treeData?type=2" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="员工：">员工：</label>
                                <sys:userselect id="tuser" name="tuser.id" labelName="tuser.name"
                                                cssClass="form-control required"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="归属区域：">归属区域：</label>
                                <div class=" input-group" style=" width: 100%;">
                                    <input name="area" htmlEscape="false" data-toggle="city-picker" style="height: 34px;font-size: 14px;"/>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="form-group">
                                    <label class="label-item single-overflow pull-left" title="请假开始日期：">&nbsp;请假开始日期：</label>
                                    <div class="col-xs-12">
                                        <div class="col-xs-12 col-sm-5">
                                            <div class='input-group date' id='beginBeginDate' style="left: -10px;" >
                                                <input type='text'  name="beginBeginDate" class="form-control"  />
                                                <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-1">
                                            ~
                                        </div>
                                        <div class="col-xs-12 col-sm-5">
                                            <div class='input-group date' id='endBeginDate' style="left: -10px;" >
                                                <input type='text'  name="endBeginDate" class="form-control" />
                                                <span class="input-group-addon">
					                       <span class="glyphicon glyphicon-calendar"></span>
					                   </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div class="form-group">
                                    <label class="label-item single-overflow pull-left" title="请假结束日期：">&nbsp;请假结束日期：</label>
                                    <div class="col-xs-12">
                                        <div class='input-group date' id='endDate' >
                                            <input type='text'  name="endDate" class="form-control"  />
                                            <span class="input-group-addon">
			                       <span class="glyphicon glyphicon-calendar"></span>
			                   </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <label class="label-item single-overflow pull-left" title="备注信息：">备注信息：</label>
                                <input name="remarks" htmlEscape="false" maxlength="257"  class=" form-control"/>
                            </div>
                            <div class="col-xs-12 col-sm-6 col-md-4">
                                <div style="margin-top:26px">
                                    <a  id="search" class="btn btn-primary btn-rounded  btn-bordered btn-sm"><i class="fa fa-search"></i> 查询</a>
                                    <a  id="reset" class="btn btn-primary btn-rounded  btn-bordered btn-sm" ><i class="fa fa-refresh"></i> 重置</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- 工具栏 -->
            <div id="toolbar">
                <shiro:hasPermission name="test:one:leaveForm:add">
                    <button id="add" class="btn btn-primary" onclick="add()" title="请假表单"><i class="glyphicon glyphicon-plus"></i> 新建</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="test:one:leaveForm:edit">
                    <button id="edit" class="btn btn-success" disabled onclick="edit()">
                        <i class="glyphicon glyphicon-edit"></i> 修改
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="test:one:leaveForm:del">
                    <button id="remove" class="btn btn-danger" disabled onclick="deleteAll()">
                        <i class="glyphicon glyphicon-remove"></i> 删除
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="test:one:leaveForm:import">
                    <button id="btnImport" class="btn btn-info"><i class="fa fa-folder-open-o"></i> 导入</button>
                    <div id="importBox" class="hide">
                        <form id="importForm" action="${ctx}/test/one/leaveForm/import" method="post" enctype="multipart/form-data"
                              style="padding-left:20px;text-align:center;" ><br/>
                            <input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　


                        </form>
                    </div>
                </shiro:hasPermission>
                <a class="accordion-toggle btn btn-default" data-toggle="collapse" data-parent="#accordion2" href="#collapseTwo">
                    <i class="fa fa-search"></i> 检索
                </a>
            </div>
            <!-- 表格 -->
            <table id="table"   data-toolbar="#toolbar" ></table>
        </div>
    </div>
</div>
<script>

    function add() {
        jp.go("${ctx}/form/dynamic/form?form_id=${form_id}");
    }

    function getIdSelections() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

    function getIds() {
        return $.map($("#table").bootstrapTable('getSelections'), function (row) {
            return "'"+row.id+"'"
        });
    }

    function deleteAll(){

        jp.confirm('确认要删除选中记录吗？', function(){
            jp.loading();
            jp.get("${ctx}/form/dynamic/deleteAll?form_id=${form_id}&ids=" + getIds(), function(data){
                if(data.success){
                    $('#table').bootstrapTable('refresh');
                    jp.success(data.msg);
                }else{
                    jp.error(data.msg);
                }
            })

        })
    }
    function edit(){
        jp.go("${ctx}/form/dynamic/form?form_id=${form_id}&id=" + getIdSelections());
    }

    $(document).ready(function() {
        $('#table').on('check.bs.table uncheck.bs.table load-success.bs.table ' +
            'check-all.bs.table uncheck-all.bs.table', function () {
            $('#remove').prop('disabled', ! $('#table').bootstrapTable('getSelections').length);
            $('#edit').prop('disabled', $('#table').bootstrapTable('getSelections').length!=1);
        });

            $('#table').bootstrapTable({
                url: '${ctx}/form/dynamic/data?form_id=${form_id}',
                //请求方法
                method: 'post',
                //类型json
                dataType: "json",
                contentType: "application/x-www-form-urlencoded",
                //显示刷新按钮
                showRefresh: true,
                //显示切换手机试图按钮
                showToggle: true,
                //显示 内容列下拉框
                showColumns: true,
                //显示到处按钮
                showExport: true,
                //显示切换分页按钮
                showPaginationSwitch: true,
                //最低显示2行
                minimumCountColumns: 2,
                //是否显示行间隔色
                striped: true,
                //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                cache: false,
                //是否显示分页（*）
                pagination: true,
                //排序方式
                sortOrder: "asc",
                //初始化加载第一页，默认第一页
                pageNumber:1,
                //每页的记录行数（*）
                pageSize: 10,
                //可供选择的每页的行数（*）
                pageList: [10, 25, 50, 100],
                sidePagination: "server",
                queryParams : function(params) {
                    var searchParam = $("#searchForm").serializeJSON();
                    searchParam.pageNo = params.limit === undefined? "1" :params.offset/params.limit+1;
                    searchParam.pageSize = params.limit === undefined? -1 : params.limit;
                    searchParam.orderBy = params.sort === undefined? "" : params.sort+ " "+  params.order;
                    return searchParam;
                },
                columns: ${columns}
            });

    })
    </script>

</body>
</html>