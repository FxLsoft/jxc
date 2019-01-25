<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>树选择控件</title>
	<meta name="decorator" content="blank"/>
	<%@include file="/webpage/include/treeview.jsp" %>
	<script type="text/javascript">
		var tree;
		$(document).ready(function(){
			
			//jstree初始化
			$('#jstree').jstree({
				'core' : {
					"multiple" : true,
					"animation" : 0,
					"themes" : { "icons":true ,"stripes":false},
					'data' : {
						"url" : "${ctx}${url}${fn:indexOf(url,'?')==-1?'?':'&'}&extId=${extId}&isAll=${isAll}&module=${module}&t="+ new Date().getTime(),
						"dataType" : "json" // needed only if you do not supply JSON headers
					}
				},
				'plugins' : ['types' ,"search", <c:if test="${checked==true}">'checkbox',</c:if> 'wholerow'],
				"types":{ 
					'default' : { 'icon' : 'fa fa-folder' },
			        '1' : {'icon' : 'fa fa-home'},
					'2' : {'icon' : 'fa fa-umbrella' },
				    '3' : { 'icon' : 'fa fa-group'},
					'4' : { 'icon' : 'fa fa-eur' },
					'btn':{'icon' : 'fa fa-square'}
				} 

			});
			
			 tree = $('#jstree').jstree(true);
			 
			 //查找功能
			  var to = false;
			  $('#search_q').keyup(function () {
			    if(to) { clearTimeout(to); }
			    to = setTimeout(function () {
			      var v = $('#search_q').val();
			      $('#jstree').jstree(true).search(v);
			    }, 250);
			  });
			  $("#searchButton").click(function(){
				  var v = $('#search_q').val();
			      $('#jstree').jstree(true).search(v);
			  })
			
		});
		
	</script>
</head>
<body>
		 <c:if test="${allowSearch}">
		<!-- 搜索 -->
			 <div class="input-search" style="margin: 5px 20px 0px 20px">
				 <button type="submit" class="input-search-btn">
					 <i class="fa fa-search" aria-hidden="true"></i></button>
				 <input   id="search_q" type="text" class="form-control input-sm" name="" placeholder="请输入关键词...">

			 </div>
		</c:if>
			<div class="row">
					<div id="jstree" ></div>
			</div>
</body>