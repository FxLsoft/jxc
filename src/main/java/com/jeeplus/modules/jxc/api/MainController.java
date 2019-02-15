package com.jeeplus.modules.jxc.api;

import java.util.List;
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
import com.jeeplus.modules.jxc.entity.OperOrderDetail;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.entity.Storage;
import com.jeeplus.modules.jxc.service.OperOrderPayService;
import com.jeeplus.modules.jxc.service.OperOrderService;
import com.jeeplus.modules.jxc.service.PriceService;
import com.jeeplus.modules.jxc.service.StorageService;
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
	
	@Autowired
	private StorageService storageService;
	
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
		OperOrder operOrder = operOrderService.get(id);
		if (operOrder == null) {
			result.setSuccess(false);
			result.setMsg("相应的单据未找到");
			return result;
		}
		if (operOrder.getStatus().equals(status)) {
			return result;
		}
		operOrderService.updateOrderStatus(id, status);
		// 由保存状态改成提交状态，那么就需要进行入库操作
		if ("0".equals(operOrder.getStatus()) && "1".equals(status)) {
			List<OperOrderDetail> detailList = operOrder.getOperOrderDetailList();
			for (OperOrderDetail operOrderDetail: detailList) {
				// 减库
				if ("-1".equals(operOrderDetail.getOperType())) {
					
				}
				// 加库
				else {
					Storage storage = new Storage();
					storage.setAmount(operOrderDetail.getAmount());
					storage.setPrice(operOrderDetail.getPrice());
					storage.setProduct(operOrderDetail.getProduct());
//					storage.setStore(operOrder.get);
					storage.setRemarks("由单据 " + operOrder.getNo() + " 入库");
				}
			}
		}
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
