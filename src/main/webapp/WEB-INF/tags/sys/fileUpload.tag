<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<%@ attribute name="path" type="java.lang.String" required="true" description="输入框"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框"%>
<%--
type:
    all: 允许上传所有文件
    file: 只允许上传所有非可执行文件（排除.sql,.exe,.jsp等等后缀类型的文件）
    image: 只允许上传图片
    audio: 只允许上传音频文件
    video: 只允许上传视频文件
    office: 只允许上传文档类型

    指定type参数时，将忽略allowedExtensions 和 deniedExtensions参数，请勿配置。
--%>
<%@ attribute name="type" type="java.lang.String" required="false" description="*,files、images、video、audio、office"%>
<%@ attribute name="uploadPath" type="java.lang.String" required="true" description="文件上传路径"%>
<%@ attribute name="fileNumLimit" type="java.lang.String" required="false" description="是否允许多选"%>
<%@ attribute name="fileSizeLimit" type="java.lang.String" required="false" description="文件大小"%>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="是否查看模式"%>

<%--
allowedExtensions:允许上传的文件类型
deniedExtensions： 禁止上传的文件类型
--%>
<%@ attribute name="allowedExtensions" type="java.lang.String"  required="false" description="允许的文件类型"%>
<%--7z,aiff,asf,avi,bmp,csv,doc,docx,fla,flv,gif,gz,gzip,jpeg,jpg,mid,mov,mp3,mp4,mpc,mpeg,mpg,ods,odt,pdf,png,ppt,pptx,pxd,qt,ram,rar,rm,rmi,rmvb,rtf,sdc,sitd,swf,sxc,sxw,tar,tgz,tif,tiff,txt,vsd,wav,wma,wmv,xls,xlsx,zip--%>
<%--bmp,gif,jpeg,jpg,png--%>

<div class="input-group" style="width:100%">
    <input type="hidden" name="${path}"  value="${value}"/>
    <input type="text" id="${path}" value="${fns:getLabels(value)}" class="form-control" readonly="readonly"/>
    <span class="input-group-btn">
	       		 <button type="button" id="${path}Button" onclick="${path}FileDialogOpen();" class="btn btn-primary "><i class="fa fa-cloud-upload"></i></button>
       		 </span>
</div>
<script type="text/javascript">
    function ${path}FileDialogOpen() {
        var currentFileValues = $("input[name='${path}']").val();
        jp.open({
            type: 2,
            area: ['800px', '300px'],
            title:"上传文件",
            auto:true,
            content: "${ctx}/tag/fileUpload?fileValues="+encodeURIComponent(currentFileValues)+"&uploadPath=${uploadPath}"+"&type=${type}"+"&readonly=${readonly}"
            +"&fileNumLimit=${fileNumLimit}"+"&fileSizeLimit=${fileSizeLimit}"+"&allowedExtensions=${allowedExtensions}"+"&deniedExtensions=${deniedExtensions}",
            cancel: function(index, layero){
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var fileNames =iframeWin.contentWindow.getUploadFileNames();//调用保存事件
                var fileValues =iframeWin.contentWindow.getUploadFileValues();//调用保存事件
                $("#${path}").val(fileNames);
                $("input[name='${path}']").val(fileValues);
            }
        });
    }

</script>
