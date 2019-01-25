<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="ani"/>
<script type="text/javascript">
$(document).ready(function() {
	 $('#start').datetimepicker({
		 format: "YYYY-MM-DD HH:mm:ss"
    });
    $('#end').datetimepicker({
		 format: "YYYY-MM-DD HH:mm:ss"
    });
	//提交表单
});

//删除事件
function del(index, calendar){
	jp.confirm("确定要删除该事件吗？",function(){
		var eventid = $("#eventid").val();
		$.post("${ctx }/iim/myCalendar/del",{id:eventid},function(result){
			if(result.success){//删除成功
				jp.info(result.msg);
				calendar.fullCalendar('removeEvents' ,[eventid]);
				jp.close(index);
			}else{
				jp.alert(result.msg);
			}
		});
		
	})
};

function save(index, calendar){
		var eventid = $("#eventid").val();
		$.post("${ctx }/iim/myCalendar/save",$('#inputForm').serialize(),function(result){
			if(result.success){//删除成功
				jp.info(result.msg);
				calendar.fullCalendar('refetchEvents'); //重新获取所有事件数据
				jp.close(index);
			}else{
				jp.alert(result.msg);
			}
		});
		
};
</script>
</head>
<body class="bg-white">
    <form id="inputForm"  method="post" class="form-horizontal">
    <input type="hidden" name="id" id="eventid" value="${myCalendar.id}">
    <div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>日程内容：</label>
		<div class="col-sm-10">
			<input type="text" class="form-control required" name="title" id="title"   placeholder="记录你将要做的一件事..." value="${myCalendar.title}">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>开始时间：</label>
		<div class="col-sm-10">
            <div class='input-group form_datetime' id='start'>
                <input type='text'  name="start" class="form-control required"  value="<fmt:formatDate value="${myCalendar.start}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
		</div>
   </div>
   <div class="form-group">
		<label class="col-sm-2 control-label"><font color="red">*</font>结束时间：</label>
		<div class="col-sm-10">
				<div class='input-group form_datetime' id='end'>
                    <input type='text'  name="end" class="form-control required"  value="<fmt:formatDate value="${myCalendar.end}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
		</div>
    </div>
    </form>
</body>
</html>