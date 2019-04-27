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
				isLoading: true,
				vm: {
					agency: {},
					customer: {},
					store: {}
				},
				date: ''
			}
		},
		mounted() {
			this.date = new Date().Format('yyyy年MM月dd日 hh:mm:ss');
			var _self = this;
			var loading = jp.loading('load...');
			_self.isLoading = true;
			jp.get("${ctx}/api/getOperOrderById?id="+getUrlParam("id"), function(data){
			  	if(data.success){
			  		var operOrder = data.body.operOrder;
			  		if (operOrder.agency.id) {
			  			operOrder.isShowAgency = true;
			  		} else {
			  			operOrder.isShowAgency = false;
			  		}
			  		operOrder.amount = 0;
			  		for (var i = 0; i < operOrder.operOrderDetailList.length; i++) {
			  			operOrder.amount += operOrder.operOrderDetailList[i].amount;
			  		}
			  		_self.vm = operOrder;
			  		_self.isLoading = false;
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