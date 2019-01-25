<%@ page contentType="text/html;charset=UTF-8" %>
<script>

$.fn.selectRange = function(start, end) {
    return this.each(function() {
        if (this.setSelectionRange) {
            this.focus();
            this.setSelectionRange(start, end);
        } else if (this.createTextRange) {
            var range = this.createTextRange();
            range.collapse(true);
            range.moveEnd('character', end);
            range.moveStart('character', start);
            range.select();
        }
    });
};
$(document).ready(function() {
	var url =  "${ctx}/jxc/queryProductByKey";
	var order_url = "${ctx}/jxc/retailComfirmOrder";
	var shopCart = [];
	var productList = [];
	var totalPrice = 0;
	function getProduct(key) {
		var a = $.ajax({url:url,data: {key: key || ''}}).then(res => {
			if (res.success) {
				var html = '';
				productList = res.body.product;
				for (var i = 0; i < res.body.product.length; i++) {
					var product = res.body.product[i];
					// <button type="button" class="btn btn-info">（一般信息）Info</button>
					html += '<div data-id=' + product.id + ' class="f-btn primary col-md-3 product">' + product.name +'</div>'
				}
				$(".product-area").html(html);
				$(".product-area .product").off("click").on("click", selectProduct)
			}
		});
	}
	$("#product-name").on("input", function() {
		console.log(this.value);
		getProduct(this.value);
	})
	
	$(".cancel-order").on("click", function() {
		shopCart = [];
		doShopCart();
	})
	
	$(".confirm-order").on("click", function() {
		if (shopCart.length === 0) return;
		jp.prompt("确认应付金额", totalPrice.toFixed(2), function(text) {
			if (/^\d+(\.\d+)?$/.test(text)) {
				var pay = parseFloat(text);
				var loading = jp.loading('正在支付。。。')
				jp.post(order_url, {
					payMoney: pay,
					productList: JSON.stringify(shopCart)
				}, function (res) {
					console.log(res);
					if (res.success) {
						jp.close(loading);
						jp.success("支付成功");
						shopCart = [];
						doShopCart();
						jp.voice();
						getProduct();
					} else {
						jp.warning(res.msg);
					}
				});
			} else {
				jp.warning("输入数字非法");
			}
		});
	})
	
	function selectProduct(event) {
		var id = this.dataset.id;
		var sIndex = 0;
		for (var i = 0; i < productList.length; i++) {
			if (productList[i].id === id) {
				var has = false;
				for (var j = 0; j < shopCart.length; j ++) {
					if (shopCart[j].id === id) {
						shopCart[j].amount = shopCart[j].amount + 1 > shopCart[j].count ? shopCart[j].count : shopCart[j].amount + 1;
						has = true;
						sIndex = j;
					}
				}
				if (!has) {
					shopCart.push({
						id: productList[i].id,
						name: productList[i].name,
						price: productList[i].price,
						amount: productList[i].count >= 1 ? 1 : productList[i].count
					})
					sIndex = shopCart.length - 1;
				}
			}
		}
		doShopCart(sIndex);
	}
	
	function doShopCart(index) {
		var html = "";
		totalPrice = 0;
		
		for (var i = 0; i < shopCart.length; i++) {
			var p = shopCart[i];
			var sum = (p.price*p.amount).toFixed(2);
			totalPrice = totalPrice + +sum;
			html += '<div class="row product-row"><div class="col-md-3">' + p.name + '</div><div class="col-md-3"><input value="' + p.price + '" type="text" class="form-control price" data-id="' +p.id+ '" data-index="' + i + '"/></div><div class="col-md-3"><input value="' + p.amount + '" type="text" class="form-control amount" data-id="' +p.id+ '" data-index="' + i + '"/></div><div class="col-md-2">' +sum+ '</div><div class="col-md-1"><a data-index="' + i + '" class="fa fa-times delete-shopcart"></a></div></div>';
		}
		html += '<div class="row product-total"> <div class="col-md-3 pay-money">应付金额：</div><div class="col-md-3 pay-money">' + totalPrice.toFixed(2) +  '</div></div>';
		$('.order-list').html(html);
		$('.amount').off('blur').on('blur', amountBlur);
		$('.amount').off('input').on('input', amountInput);
		$('.amount').off('keydown').on('keydown', keyDown);
		$('.price').off('keydown').on('keydown', priceKeyDown);
		$('.delete-shopcart').off('click').on('click', deleteShopCart);
		if (index >= 0) {
			var length = (shopCart[index].price + '').length;
			$('.amount').eq(index).selectRange(100, 100);
		}
	}
	
	function deleteShopCart() {
		shopCart.splice(+this.dataset.index, 1);
		doShopCart();
	}
	
	function amountInput() {
		// console.log(this.value);
	}
	
	function keyDown() {
//		console.log(event.keyCode, this.value);
		if (event.keyCode === 187 || event.keyCode === 189) {
			this.value = "";
		}
		// .
        if ([110, 190].indexOf(event.keyCode) > -1) {

        }
        // 0-9
        else if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)) {

        }
        // Backspace Shift Ctrl Delete
        else if ([8, 16, 17, 46].indexOf(event.keyCode) > -1) {

        }
        // Left Arrow | Up Arrow | Right Arrow | Down Arrow
        else if ([37, 38, 39, 40].indexOf(event.keyCode) > -1) {

        }
        else {
            event.returnValue = false;
        }
	}
	
	function priceKeyDown() {
		if (event.keyCode === 187) {
			this.value = "";
		}
		// .
        if ([110, 190].indexOf(event.keyCode) > -1) {

        }
        // 0-9
        else if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)) {

        }
        // Backspace Shift Ctrl Delete
        else if ([8, 16, 17, 46].indexOf(event.keyCode) > -1) {

        }
        // Left Arrow | Up Arrow | Right Arrow | Down Arrow
        else if ([37, 38, 39, 40].indexOf(event.keyCode) > -1) {

        }
        else {
            event.returnValue = false;
        }
	}
	
	function amountBlur() {
		var count = +this.value;
		if (count > shopCart[+this.dataset.index].count) {
			jp.warning("此商品库存不足 " + count);
			count = shopCart[+this.dataset.index].count;
		}
		shopCart[+this.dataset.index].amount = count;
		doShopCart();
	}
	
	getProduct();
	
	if (!!jp.getQueryString('fullScreen')) {
		$(".retail_home").hide();
	}
});
</script>