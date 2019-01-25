<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>群组管理</title>
	<meta name="decorator" content="ani"/>
	<link href="${ctxStatic}/plugin/layui/css/manager.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript">
		$(document).ready(function() {
            refreshMembers();
		});




        function refreshMembers() {
            jp.get("${ctx}/iim/layGroup/memberData?id=${layGroup.id}", function (data) {
                debugger
                var memberTpl = $('#memberTpl').html(); //读取模版
                laytpl(memberTpl).render(data, function(render){
                    $("#group-members-view").html(render);
                });

            })
        }
        function addMemberToGroup(){
            jp.openUserSelectDialog(true,function (ids) {
                jp.get("${ctx}/iim/layGroup/addUser?ids="+ids+"&groupid=${layGroup.id}", function (result) {
                    if(result.success){
                        refreshMembers()
                        jp.success(result.msg);
                    }
                })
            });
        };
        function delFromGroup(id){
            jp.get("${ctx}/iim/layGroup/logout?user.id="+id+"&group.id=${layGroup.id}",function (data) {
				if(data.success){
                    refreshMembers()
				    jp.success(data.msg);
				}
            })
        }


	</script>
</head>
<body class="bg-white">

		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
				<div class="col-xs-2">
					<div class="contact-box">
						<a href="#" onclick="addMemberToGroup()">
								<div class="text-center">
									<img alt="image"  class="img-circle m-t-xs img-responsive" src="${ctxStatic}/common/images/add_user.jpg">
								</div>
								<div class="text-center">
									<h5>添加成员</h5>
								</div>
						</a>
					</div>
				</div>
				<div id="group-members-view"></div>
			</div>
		</div>

		<script id="memberTpl" type="text/html">
			{{# for(var i = 0, len = d.length; i < len; i++){ }}
			<div class="col-xs-2">
				<div class="contact-box">
					<a href="#">
						<div class="text-center photo">
							<img alt="image" class="img-circle m-t-xs img-responsive" src="{{# if(d[i].user.photo != ''){ }}{{  d[i].user.photo }}{{# }else{ }}${ctxStatic}/common/images/flat-avatar.png {{# } }}" />
							<h5>{{  d[i].user.name }}</h5>
							<div class="mask ">
								<div class="opts">
									<button class='btn btn-primary btn-sm hand'  onclick="delFromGroup('{{ d[i].user.id }}')">
										<span class='icon icon-file-alt mr-3'></span>删除
									</button>
								</div>
							</div>

						</div>
					</a>
				</div>
			</div>
			{{# } }}
		</script>

</body>
</html>