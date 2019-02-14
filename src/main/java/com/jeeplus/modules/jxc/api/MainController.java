package com.jeeplus.modules.jxc.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.IdWorker;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.service.OperOrderPayService;
import com.jeeplus.modules.jxc.service.OperOrderService;
import com.jeeplus.modules.jxc.service.PriceService;
import com.jeeplus.modules.sys.utils.DictUtils;

@Controller
@RequestMapping(value = "${adminPath}/api")
public class MainController extends BaseController{
	
	@Autowired
	private PriceService priceService;
	
	@Autowired
	private OperOrderService operOrderService;
	
	@Autowired
	private OperOrderPayService operOrderPayService;
	
	/**
	 * 获取 Price 列表
	 * @param price
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/getPrice")
	@ResponseBody
	public Map<String, Object> getPrice(Price price, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Price> page = priceService.findPage(new Page<Price>(request, response), price);
		return getBootstrapData(page);
	}
	
	@RequestMapping("/uploadBalanceSell")
	@ResponseBody
	public AjaxJson uploadBalanceSell() {
		AjaxJson result = new AjaxJson();
		return result;
	}
	
	@RequestMapping("/updateOrderStatus")
	@ResponseBody
	public AjaxJson updateOrderStatus(String id, String status) {
		AjaxJson result = new AjaxJson();
		operOrderService.updateOrderStatus(id, status);
		// 如果是提交状态
		if ("1".equals(status)) {
			// 入库和出库操作
		}
		return result;
	}
	
	@RequestMapping("/payOrder")
	@ResponseBody
	public AjaxJson payOrder(String ids, String type, String payMoney) {
		AjaxJson result = new AjaxJson();
		String[] idArr = ids.split(",");
		String[] payMoneyArr = payMoney.split(",");
		String payNo = "P" + IdWorker.getId();
		for (int i = 0; i < Math.min(payMoneyArr.length, idArr.length); i++) {
			String id = idArr[i];
			OperOrder operOrder = operOrderService.get(id);
			if (operOrder == null) continue;
			Double pay = Double.parseDouble(payMoneyArr[i]);
			if (pay < 0) continue;
			operOrder.setRealPay(operOrder.getRealPay() + pay);
			OperOrderPay orderPay = new OperOrderPay();
			orderPay.setNo(payNo);
			orderPay.setPayType(type);
			orderPay.setPrice(pay);
			orderPay.setOperOrder(operOrder);
			operOrderPayService.save(orderPay);
			if (operOrder.getRealPrice() - operOrder.getRealPay() <= Double.parseDouble(DictUtils.getDictValue("order_complete_deviation", "small", "0"))) {
				operOrder.setStatus("3");
			}
			operOrderService.save(operOrder);
		}
		return result;
	}
	
}
