<%@ page contentType="text/html;charset=UTF-8" %>
<script>
function getUrlParam (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
$(document).ready(function() {
	var app = new Vue({
		el: '#app',
		data() {
			return {
				vm: {
					agency: {}
				},
				date: ''
			}
		},
		mounted() {
			this.date = new Date().Format('yyyy年MM月dd日 HH:mm:ss');
			var _self = this;
			var loading = jp.loading('load...');
			jp.get("${ctx}/api/getOperOrderById?id="+getUrlParam("id"), function(data){
			  	if(data.success){
			  		console.log(data);
			  		var operOrder = data.body.operOrder;
			  		_self.vm = operOrder;
			  		jp.success(data.msg);
			  	}else{
			  		jp.error(data.msg);
			  	}
			  	jp.close(loading);
			})
		}
	})
	
});
</script>