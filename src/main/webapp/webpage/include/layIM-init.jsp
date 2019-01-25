<!-- 本页面是layIM聊天插件 
          使用方法 :在需要支持聊天的页面直接引入该页面，<-%@include file=".../common/layIM-init.jsp->
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<meta name="decorator" content="ani"/>
<link rel="stylesheet" href="${ctxStatic}/plugin/layui/dist/css/layui.css">
<script src="${ctxStatic}/plugin/layui/dist/layui.js"></script>
<script src="${ctxStatic}/plugin/layui/sockjs.js"></script><!-- 如果浏览器不支持socktjs，添加支持 -->
<script type="text/javascript">
(function() {
	//初始化
	layui.use('layim', function(layim){
		//---即使聊天服务端地址配置，端口和web服务器端口一样，80可以省略
        var host = window.document.location.host;
        var pathName = window.document.location.pathname;
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        var wsServer = "ws://" + host + projectName;
		var currentLoginName = '${fns:getUser().loginName}';
		var currentName = '${fns:getUser().name}';
		var currentFace ='${fns:getUser().photo}';
	    var webSocket = null;
        if ('WebSocket' in window || 'MozWebSocket' in window) {
            webSocket = new WebSocket(wsServer+"/layIMSocketServer");
        } else {
            webSocket = new SockJS( wsServer+"/sockjs/layIMSocketServer");
        }

	  webSocket.onerror = function(event) {
	    jp.alert("websockt连接发生错误，请刷新页面重试!")
	  };
			
		// 连接成功建立的回调方法
		  webSocket.onopen = function(event) {
		  //  webSocket.send("_online_user_"+currentLoginName);
		  };
		// 接收到消息的回调方法
		  webSocket.onmessage = function(event) {
		    var res=event.data;
			if(res.indexOf("_online_all_status_")>=0){
				var dd = res.substring("_online_all_status_".length);
				setTimeout(function(){updateOnlineStatus(eval('('+dd+')').body.data);},500);
				
			}
			
			if(res.indexOf('_sys_')>=0){//系统通知
				var arra = res.split("_sys_");
		    	var sender=arra[0];
		    	var contents=arra[1];
	            var obj = {
	                username: "系统通知"
	                ,avatar: layui.cache.dir+'images/face/notify.png'
	                ,id:sender
	                ,type: 'friend'
	                ,content: contents
	              }
	            layim.getMessage(obj);
	            return;
				
			}
		    if(res.indexOf("_msg_")>=0){
		    	var arra = res.split("_msg_");
		    	var sender=arra[0];
		    	var receiver=arra[1];//发送者登录名
		    	var content=arra[2];
		    	var avatar=arra[3];
		    	var type=arra[4];
		    	var senderName=arra[5];//发送者姓名
		    	var sendtime = arra[6];
		    	 if(sendtime != 'NAN'){//使用历史记录时间
			        layim.getMessage({
			            username: senderName
			            ,avatar: avatar
			            ,id: sender
			            ,type: type
			            ,content: content
			            ,timestamp:parseFloat(sendtime)
			          });
		    	 }else{
		    		 
		    		 layim.getMessage({
				            username: senderName
				            ,avatar: avatar
				            ,id: sender
				            ,type: type
				            ,content: content
				          });
		    	 }
		    }
		  };
		  //发送消息的方法
		  function send(mine,To) {
			webSocket.send(currentLoginName+"_msg_"+To.id+"_msg_"+mine.content+"_msg_"+mine.avatar+"_msg_"+To.type+"_msg_"+currentName+"_msg_NAN");
		  };
		//切换在线状态的方法
		  function setonline() {
			webSocket.send("_online_user_"+currentLoginName);
		  };
		//切换离线状态的方法
		  function sethide() {
			webSocket.send("_leave_user_"+currentLoginName);
		  };
		  
		//更新在线用户信息
		  function updateOnlineStatus(arra)//更新在线用户信息
		  {
		  	 $("div.layui-layim-main ul.layim-list-friend li ul.layui-layim-list li").each(function(){//状态还原
		  		 if(this.className != 'layim-null'){
			  		 var span = $(this).find("span:first");
			  		 var name = span.html();
			  		 var loginName = this.className.replace("layim-friend","").trim();
			  		 if((','+arra+",").indexOf(','+loginName+',')>=0){
			  			 if(name.indexOf('(<font color="red">离线</font>)')>=0){
                             span.replace('(<font color="red">离线</font>)', '(<font color="green">在线</font>)')
                         } else if (name.indexOf('(<font color="green">在线</font>)')>=0){

                         }else{
                             span.html(name +'(<font color="green">在线</font>)');
                         }

			  		 }else{
                         if(name.indexOf('(<font color="red">离线</font>)')>=0){
                         } else if (name.indexOf('(<font color="green">在线</font>)')>=0){
                             span.replace( '(<font color="green">在线</font>)', '(<font color="red">离线</font>)')
                         }else{
                             span.html(name+'(<font color="red">离线</font>)');
                         }

			  		 }
		  		 }
		  	 });
		  }
		  
	  //基础配置
	  layim.config({

	    //初始化接口
	    init: {
		  url: '${ctx}/iim/contact/friend'
	      ,data: {}
	    }

	    //简约模式（不显示主面板）
	    //,brief: true

	    //查看群员接口
	    ,members: {
	       url: '${ctx}/iim/contact/getMembers'
	      ,data: {}
	    }
	    
	    ,uploadImage: {
	       url: '${ctx}/iim/contact/uploadImage'//（返回的数据格式见下文）
	      ,type: 'post' //默认post
	    }

	    ,uploadFile: {
	       url: '${ctx}/iim/contact/uploadFile' //（返回的数据格式见下文）
	      ,type: 'post' //默认post
	    }
	    
	  //扩展工具栏，下文会做进一步介绍（如果无需扩展，剔除该项即可）
	    ,tool: [{
	       alias: 'code' //工具别名
	      ,title: '代码' //工具名称
	      ,icon: '&#xe64e;' //工具图标，参考图标文档
	    }]
	    
	   // ,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
	    ,chatLog:'${ctx}/iim/chatHistory' //聊天记录地址
	    ,find:'${ctx}/iim/contact/layimManager' //发现页面地址，若不开启，剔除该项即可
	  });

	//监听自定义工具栏点击，以添加代码为例
	  layim.on('tool(code)', function(insert, send, obj){ //事件中的tool为固定字符，而code则为过滤器，对应的是工具别名（alias）
	    layer.prompt({
	      title: '插入代码'
	      ,formType: 2
	      ,shade: 0
	    }, function(text, index){
	      layer.close(index);
	      insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器，主要由insert完成
	      //send(); //自动发送
	    });
	  });

	  //监听发送消息
	  layim.on('sendMessage', function(data){
		  var mine = data.mine; //包含我发送的消息及我的信息
		    var To = data.to; //对方的信息

		    //发送消息到WebSocket服务
		    send(mine,To);
		    
	  });

	  //监听在线状态的切换事件
	  layim.on('online', function(data){
	    if(data=="online"){
	    	setonline(); //用户上线
	    }else{
	    	sethide();//用户离线
	    }
	    
	  });


	  
	  


	  //layim建立就绪
	  layim.on('ready', function(res){
		  
		  //更改签名
	          var signObj = $('.layui-layim-remark');
	          signObj.focus(function () {
	              signObj.removeClass('layim-sign-hide');
	              console.log('准备更改签名');
	          });
	          signObj.blur(function () {
	              console.log('更改签名完毕');
	              signObj.addClass('layim-sign-hide');
	          });
	          //enter提交
	          signObj.keydown(function (event) {
	              if (event.which == 13) {
	                  console.log('按下了Enter提交签名');
	                  signObj.addClass('layim-sign-hide');
	                  signObj.blur();
	                  var value= signObj.val();
	                  $.post("${ctx}/sys/user/saveSign",{'sign':value},function(data){
	                	  
	                	  top.layer.alert(data.msg, {icon: 1});
	                  })
	                  
	              }
	          });
	  

	  });

	  //监听查看群员
	  layim.on('members', function(data){
	    console.log(data);
	  });
	  
	  //监听聊天窗口的切换
	  layim.on('chatChange', function(data){
	    console.log(data);
	  });
	});
})(jQuery);  		 
	</script>
