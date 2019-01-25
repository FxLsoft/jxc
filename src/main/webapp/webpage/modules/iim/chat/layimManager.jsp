<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>群组管理</title>
	<meta name="decorator" content="ani"/>
	<link href="${ctxStatic}/plugin/layui/dist/css/manager.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript">
		$(document).ready(function() {
            refreshFriends();
            refreshGroups();
		});

        function refreshGroups() {
            jp.get("${ctx}/iim/layGroup/data", function (data) {
                var  groupTpl = $('#groupTpl').html(); //读取模版
                laytpl(groupTpl).render(data, function(render){
                    $("#my-groups-view").html(render);
                });

            })
        }
        function addToGroup(layGroupId){
            top.layer.tab({
                type: 2,
                area: ['800px', '500px'],
                title:"添加好友",
                name:'friend',
                content: "${ctx}/iim/contact/searchUsers" ,
                btn: ['确定', '关闭'],
                yes: function(index, layero){
                    var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                    var ids = iframeWin.getSelectedIds();

                    if(ids == "-1"){
                        return;
                    }
                    jp.go("${ctx}/iim/layGroup/addUser?ids="+ids+"&groupid="+layGroupId);
                    top.layer.close(index);//关闭对话框。
                },
                cancel: function(index){
                }
            });
        };

        function addGroup(){
            jp.openChildDialog("创建群组","${ctx}/iim/layGroup/form","800px", "500px", refreshGroups);

        }
        function delGroup(id) {
            jp.confirm("确认解散该群吗？",function () {
                jp.get('${ctx}/iim/layGroup/delete?id='+id, function (data) {
                    if(data.success){
                        jp.success("解散成功!");
                        refreshGroups();
                    }
                })
            })

        }
        function editGroup(id) {
            jp.openChildDialog("编辑群组","${ctx}/iim/layGroup/form?id="+id,"800px", "500px", refreshGroups);

        }
        function viewGroupMember(id) {
           jp.openViewDialog("查看群成员","${ctx}/iim/layGroup/member-view?id="+id,"800px", "500px");

        }
        function editGroupMember(id) {
            jp.openViewDialog("查看群成员","${ctx}/iim/layGroup/member-edit?id="+id,"800px", "500px");

        }
        function outGroup(id) {
            jp.confirm("确认退出该群吗？",function () {
                jp.get('${ctx}/iim/layGroup/logout?user.id=${fns:getUser().id}&group.id='+id, function (data) {
                    if(data.success){
                        jp.success("退出成功!");
                        refreshGroups();
                    }
                })
            })

        }

        function refreshFriends() {
            jp.get("${ctx}/iim/contact/myFriends", function (data) {
                var friendTpl = $('#friendTpl').html(); //读取模版
                laytpl(friendTpl).render(data, function(render){
                    $("#my-friends-view").html(render);
                });

            })
        }
        function addFriend(){
            jp.openUserSelectDialog(true,function (ids) {
                jp.get("${ctx}/iim/contact/addFriend?ids="+ids, function (result) {
                    if(result.success){
                        refreshFriends()
                        jp.success(result.msg);
                    }
                })
            });
        };
        function delFriend(id){
            jp.get("${ctx}/iim/contact/delFriend?id="+id,function (data) {
				if(data.success){
                    refreshFriends()
				    jp.success(data.msg);
				}
            })
        }


	</script>
</head>
<body class="bg-white">

<div class="tabs-container">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">好友管理：</a>
		</li>
		<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">群组管理：</a>
		</li>
	</ul>
	<div class="tab-content">
		<div id="tab-1" class="tab-pane fade in  active">
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="row">
					<div class="col-xs-2">
						<div class="contact-box">
							<a href="#" onclick="addFriend()">
									<div class="text-center">
										<img alt="image"  class="img-circle m-t-xs img-responsive" src="${ctxStatic}/common/images/add_user.jpg">
									</div>
									<div class="text-center">
										<h5>添加好友</h5>
									</div>
							</a>
						</div>
					</div>
					<div id="my-friends-view"></div>
				</div>
			</div>
		</div>
		<div id="tab-2" class="tab-pane fade in">
			<div class="wrapper wrapper-content animated fadeInRight">
				<div class="row">
					<div class="col-xs-4">
					<div class="config-card add hand">
						<a href="#" onclick="addGroup()">
							<span>+</span></a>
					</div>
				</div>
				<div id="my-groups-view"></div>
			</div>
			</div>
		</div>
		</div>

		<script id="groupTpl" type="text/html">
			{{# for(var i = 0, len = d.length; i < len; i++){ }}
			<div class="col-xs-4">
				<div class='config-card'>
					<div class="head bg-primary">
						<span class='title ml-4'>{{ d[i].groupname }}</span>
						<div class='circle bg-primary'></div>
						<div class="line bg-primary"></div>
						<div class='priority low bg-primary'>
							<img alt="image" class="img-circle m-t-xs img-responsive" src="{{# if(d[i].avatar != ''){ }}{{  d[i].avatar }}{{# }else{ }}${ctxStatic}/common/images/flat-avatar.png {{# } }}" />
						</div>
					</div>
					<div class='content mx-2' style="padding: 10px;">
						<div class="card" style="width: 20rem;">
							<img class="card-img-top" src="{{ d[i].createBy.photo }}" style="width: 35px;height: auto;" alt="Card image cap">
							<h9>{{d[i].createBy.name}}(群主)</h9>
							<hr>
							<div class="card-block">
								<p class="card-text">{{ d[i].remarks }}</p>
							</div>
						</div>
					</div>
					<div class="mask">
						<div class="opts">
							{{# if(d[i].createBy.name == '${fns:getUser().name}'){ }}
							<button type="button" onclick="editGroup('{{ d[i].id }}')" class="opt-btn btn btn-primary btn-sm">
								<span class='icon-edit mr-2'></span>编辑
							</button>
							<button type="button" onclick="delGroup('{{ d[i].id }}')" class="opt-btn btn btn-primary btn-sm">
							<span class='icon-trash mr-2'></span>删除</button>
							<button type="button" onclick="editGroupMember('{{ d[i].id }}')" class="opt-btn btn btn-primary btn-sm">
								<span class='icon-trash mr-2'></span>群员</button>
								{{# }else{ }}
								<button type="button" onclick="outGroup('{{ d[i].id }}')" class="opt-btn btn btn-primary btn-sm">
									<span class='icon-trash mr-2'></span>退出</button>
								<button type="button" onclick="viewGroupMember('{{ d[i].id }}')" class="opt-btn btn btn-primary btn-sm">
								<span class='icon-trash mr-2'></span>群员</button>
								{{# } }}

						</div>
					</div>
				</div>
			</div>
			{{# } }}
		</script>
		<script id="friendTpl" type="text/html">
			{{# for(var i = 0, len = d.length; i < len; i++){ }}
			<div class="col-xs-2">
				<div class="contact-box">
					<a href="#">
						<div class="text-center photo">
							<img alt="image" class="img-circle m-t-xs img-responsive" src="{{# if(d[i].photo != ''){ }}{{  d[i].photo }}{{# }else{ }}${ctxStatic}/common/images/flat-avatar.png {{# } }}" />
							<h5>{{  d[i].name }}</h5>
							<div class="mask ">
								<div class="opts">
									<button class='btn btn-primary btn-sm hand'  onclick="delFriend('{{ d[i].id }}')">
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