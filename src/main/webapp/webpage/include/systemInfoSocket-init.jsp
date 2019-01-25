<!-- 本页面是系统socket页面
          使用方法 :在需要支持websocket消息推送的页面直接引入该页面，<-%@include file=".../common/systemInfoSocket-init.jsp->
              :在本页面需要配置wsServer.
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${ctxStatic}/plugin/layui/sockjs.js"></script><!-- 如果浏览器不支持socktjs，添加支持 -->
<script type="text/javascript">
(function() {
        var host = window.document.location.host;
        var pathName = window.document.location.pathname;
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        var wsServer = "ws://" + host + projectName;
		  
		var webSocket = null;
		if ('WebSocket' in window || 'MozWebSocket' in window) {
			  webSocket = new WebSocket(wsServer+"/systemInfoSocketServer");
		  } else {
			  webSocket = new SockJS( wsServer+"/sockjs/systemInfoSocketServer");
		  }
		
		  webSocket.onerror = function(event) {
		    jp.alert("websockt连接发生错误，请刷新页面重试!")
		  };
				
		// 接收到消息的回调方法
		  webSocket.onmessage = function(event) {
		    var res=event.data;
		    jp.toastr.info(res,"系统通知")
		    jp.voice();
		  };
})(jQuery);  
</script>