<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
 <meta charset="utf-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <title>我的日程</title>
 <meta name="decorator" content="ani"/>

    

<!--
	说明：需要整合农历节气和节日，引入fullcalendar.js fullcalendar2.css
	不需要则引入：fullcalendar.min.js fullcalendar.css
-->

<script type="text/javascript">
$(function() {
	//页面加载完初始化日历 
	$('#calendar').fullCalendar({
		//设置日历头部信息
		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},
		events: function (start, end, timezone, callback) {
		    $.ajax({
		        url: '${ctx}/iim/myCalendar/findList',
		        dataType: 'json',
		        success: function(data) {
		            callback(data)
		        }
		    })
		},
		firstDay:1,//每行第一天为周一 
		editable: true,
		droppable: true, // this allows things to be dropped onto the calendar
		drop: function(date, event, ui, resourceId ) {
			var start, end;
			if(resourceId.intervalUnit == "week" || resourceId.intervalUnit == "day"){
				start=moment(date).format("YYYY-MM-DD HH:mm:ss")
				end = moment(date).add(2, 'hours').format("YYYY-MM-DD HH:mm:ss");
			}else{
				start=moment(date).format("YYYY-MM-DD")
				end = moment(date).add(1, 'day').format("YYYY-MM-DD");
			}
			
		    jp.post('${ctx}/iim/myCalendar/add',
				{
					start:start,
				    end:end,
				    title:$.trim($(this).text()),
				    color:$(this).css("background-color")
				},
				(result)=>{
					if(result.success){
						$('#calendar').fullCalendar('refetchEvents');
						jp.info(result.msg);
					}

			});

			// Wed Jun 18 2014 
			//alert(event.title + " was dropped on " + event.start.format());
			// is the "remove after drop" checkbox checked?
			if ($('#drop-remove').is(':checked')) {
				// if so, remove the element from the "Draggable Events" list
				$(this).remove();
			}
		},
        locale: 'zh-cn',
       // timeFormat: 'H(:mm)' ,// uppercase H for 24-hour clock
		//点击某一天时促发
		dayClick: function(date, jsEvent, view) {
    	},
		//单击事件项时触发 
        eventClick: function(calEvent, jsEvent, view) { 
        	jp.open({
		   	    type: 2,  
		   	    area: ['800px', '500px'],
		   	    title: '事件',
		   	    auto:true,
		   	    maxmin: true, //开启最大化最小化按钮
		   	    content: '${ctx}/iim/myCalendar/form?id='+calEvent.id ,
		   	    btn: ['删除','确定', '关闭'],
		   	    btn1:function(index,layero){
		   	         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
	   	         	 iframeWin.contentWindow.del(index, $('#calendar'));
	   	         	 
		   	    },
		   	    btn2: function(index, layero){
		   	     	var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
   	         	 	iframeWin.contentWindow.save(index, $('#calendar'));
		   		  },
		   	   	btn3: function(index){ 
		   		     jp.close(index);
		   	      }
		   	}); 
        },
		
		//拖动事件 
		eventDrop: function(event, delta, revertFunc) {
        	$.post("${ctx}/iim/myCalendar/drag",{id:event.id,daydiff:delta._days, minudiff:delta._milliseconds},function(result){ 
            	if(result.success){
            		jp.info(result.msg);
            	}
            	
        	}); 
    	},
		
		//日程事件的缩放
		eventResize: function(event, delta, revertFunc) {
    		jp.post("${ctx}/iim/myCalendar/resize",{id:event.id,daydiff:delta._days, minudiff:delta._milliseconds},function(result){
    			if(result.success){
            		jp.info(result.msg); 
        		}else{
        			jp.error(result.msg); 
        		}
    		}); 
		},
		
		selectable: true, //允许用户拖动 
		select: function( startDate, endDate, allDay, jsEvent, view ){ 
	    	var start =moment(startDate).format("YYYY-MM-DD HH:mm:ss"); 
	    	var end =moment(endDate).format("YYYY-MM-DD HH:mm:ss"); 
	        jp.open({
		   	    type: 2,  
		   	    area: ['800px', '500px'],
		   	    title: '事件',
		   	    auto:true,
		   	    maxmin: true, //开启最大化最小化按钮
		   	    content: '${ctx}/iim/myCalendar/form?start='+start+'&end='+end ,
		   	    btn: ['确定', '关闭'],
		   	    yes: function(index, layero){
		   	    	var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		         	 	iframeWin.contentWindow.save(index, $('#calendar'));
		   		  },
		   	   cancel: function(index){ 
		   	      }
		   		}); 
		} 
	});
	
	$('#external-events .fc-event').each(function() {
		// store data so the calendar knows to render an event upon drop
		$(this).data('event', {
			title: $.trim($(this).text()), // use the element's text as the event title
			color:$(this).css("background-color")
			//stick: true // maintain when user navigates (see docs on the renderEvent method)
		});
		// make the event draggable using jQuery UI
		$(this).draggable({
			zIndex: 999,
			revert: true,      // will cause the event to go back to its
			revertDuration: 0  //  original position after the drag
		});

	});
	
});
function add(){
	var html = $("<div class='fc-event  bg-default'>"+$("#title").val()+"</div>");
	$(html).insertBefore($("#p"));
	$(html).draggable({
		zIndex: 999,
		revert: true,      // will cause the event to go back to its
		revertDuration: 0  //  original position after the drag
	});

}
</script>
<style>

	#external-events {
		padding: 0 10px;
		background: #eee;
		text-align: left;
	}
		
	#external-events h4 {
		font-size: 16px;
		margin-top: 0;
		padding-top: 1em;
	}
		
	#external-events .fc-event {
		margin: 10px 0;
		cursor: pointer;
	}
		
	#external-events p {
		margin: 1.5em 0;
		font-size: 11px;
		color: #666;
	}
		
	#external-events p input {
		margin: 0;
		vertical-align: middle;
	}


</style>
</head>

<body>
    <div class="conter-wrapper">
	<div class="panel panel-default">
	<div class="panel-body">
		<div class="row">
			<div class="col-sm-3">
				<div id='external-events' class="list-group">
					<h4>拖拽事件</h4>
					<div class='fc-event  bg-default'>事件1</div>
					<div class='fc-event bg-info'>事件2</div>
					<div class='fc-event bg-success'>事件3</div>
					<div class='fc-event bg-warning'>事件4</div>
					<div class='fc-event bg-danger'>事件5</div>
					<p id="p">
						<input type='checkbox' id='drop-remove' />
						<label for='drop-remove'>移动后删除</label>
					</p>
					<div class="input-group">
						<input id="title" type="text" class="form-control" placeholder="">
						<span class="input-group-btn">
							<button class="btn btn-default" onclick="add()" type="submit">Add</button>
						</span>
					</div>	
					<br>
				</div>
				
			</div>
			<div class="col-sm-9">
				<div id="calendar"></div>
			</div>
		</div>
         
    </div>
    </div>
    </div>
</body>


</html>