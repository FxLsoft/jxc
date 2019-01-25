<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>文件管理管理</title>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta name="decorator" content="ani"/>

</head>
<body>
<script type="text/javascript">
    function  play(url, type) {
//            var $a = $("<video></video>").attr("href", url);
        //var $a = $("<img></img>").attr("src", url).attr("width", "100%").attr("height", "100%");
        if(type == 'video'){
            jp.open({
                type: 1,
                title: false,
                area: ['900px', '560px'],
                shade: 0.8,
                closeBtn: 0,
                scrolling: "no",
                shadeClose: true,
                content:   '<video  scrolling="no" autoplay height="100%" style="scrolling:no;background-color: black;" width="100%" src="'+url+'"  controls="controls">'
                + 'Your browser does not support the video tag.'
                + '</video>'
            });


        }else if(type == 'audio'){
            jp.open({
                type: 1,
                title: false,
                shade: 0.8,
                closeBtn: 0,
                shadeClose: true,
                content:   '<audio  autoplay  width="400px" src="'+url+'"  controls="controls">'
                + 'Your browser does not support the audio tag.'
                + '</audio>'
            });
        }else if(type == 'image'){
            jp.showPic(url)
        }else if(type =='pdf' || type == 'text' || type == 'code'){
            jp.open({
                type: 2,
                title: false,
                area: ['900px', '560px'],
                shade: 0.8,
                closeBtn: 0,
                scrolling: "no",
                shadeClose: true,
                content:  url
            });

        }else{
            jp.info("不支持的预览格式!");
		}


    }
    jeeplus.ready(function(){


        jeeplus.ui({
            view:"filemanager",
            id:"fm",
            url:"${ctx}/sys/file/data",
            readonly: false,
            handlers:{
                "upload" 	: "${ctx}/sys/file/upload",
                "download"	: "${ctx}/sys/file/download",
                "copy"		: "${ctx}/sys/file/copy",
                "move"		: "${ctx}/sys/file/move",
                "remove"	: "${ctx}/sys/file/remove",
                "rename"	: "${ctx}/sys/file/rename",
                "create"	: "${ctx}/sys/file/createFolder"
            }
        });

        var actions = $$("fm").getMenu();
        actions.attachEvent("onItemClick", function(id, e){
                var target = this.nh.id.row?this.nh.id.row:this.nh.id;
                if(id == "view"){
                   jp.post("${ctx}/sys/file/getUrl",{dir:target},function (data) {
					   play(data.body.url,data.body.type);
                   })
                    $$("fm").getMenu().hide();
                }
            });

    });
</script>
</body>

</html>