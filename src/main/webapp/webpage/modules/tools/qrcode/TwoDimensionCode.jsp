<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>接口管理</title>
	<meta name="decorator" content="ani"/>
	<script type="text/javascript">
	//去后台解析二维码返回解析内容
	function readContent(str){
		$.ajax({
			type: "POST",
			url: '${ctx}/tools/TwoDimensionCodeController/readTwoDimensionCode',
	    	data: {imgId:str,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					 if('null' == data.readContent || null == data.readContent){
						 jp.alert('读取失败，二维码无效！');
					 }else{
						 $("#readContent").text(data.readContent);
					 }
				 }else{
					 jp.alert('后台读取出错！');
					 return;
				 }
			}
		});
	}

	//生成二维码
	function createTwoD(){
		
		if($("#encoderContent").val()==""){
			jp.alert('输入框不能为空！');
			$("#encoderContent").focus();
			return false;
		}
		$.ajax({
			type: "POST",
			url: '${ctx}/tools/TwoDimensionCodeController/createTwoDimensionCode.do',
	    	data: {encoderContent:$("#encoderContent").val(),tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 
				 if(data.success){
					 $("#encoderImgId").attr("src",data.body.filePath);       
				 }else{
					 jp.alert('生成二维码失败！');
					 return false;
				 }
				 
				 
			}
		});
		return true;
	}
	</script>
</head>
<body>
	<div class="wrapper wrapper-content">
	<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">二维码测试</h3>
	</div>
	
	
	<div class="panel-body">
       <form class="form-horizontal">
    	<div class="form-group">
             <div class="col-sm-2">二维码内容</div>

             <div class="col-sm-8">
             		<input type="text" id="encoderContent" title="输入内容" value="http://www.jeeplus.org" class="form-control">
             		<span  class="help-block">请输入要生成二维码的字符串</span>
             </div>
             <div class="col-sm-2">
             		<a class="btn btn-primary" onclick="createTwoD();">生成</a>
             </div>
         </div>
         <div class="hr-line-dashed"></div>
         
         <div class="form-group">
         	<div class="col-sm-2">二维码图像</div>

             <div class="col-sm-8">
             	<img id="encoderImgId" cache="false"  width="265px" height="265px;"  class="block"/>
             	 <span class="help-block">使用微信扫一扫</span>
            </div>
           
         </div>
       </form>
    </div>
    </div>
    </div>
</body>
</html>