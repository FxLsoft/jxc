package com.jeeplus.modules.jxc.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.IdWorker;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jxc.entity.BalanceSale;
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.service.BalanceSaleService;
import com.jeeplus.modules.jxc.service.OperOrderPayService;
import com.jeeplus.modules.jxc.service.OperOrderService;
import com.jeeplus.modules.jxc.service.PriceService;
import com.jeeplus.modules.sys.utils.DictUtils;

@Controller
@RequestMapping(value = "${adminPath}/api")
public class MainController extends BaseController{
	public Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private PriceService priceService;
	
	@Autowired
	private OperOrderService operOrderService;
	
	@Autowired
	private OperOrderPayService operOrderPayService;
	
	@Autowired
	private BalanceSaleService balanceSaleService;
	
	@RequestMapping(value = "print")
	public String print(Model model) {
		return "modules/jxc/print";
	}
	
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
	
	@RequestMapping("/confirmBalanceSale")
	@ResponseBody
	public AjaxJson confirmBalanceSale(@RequestParam(defaultValue="") String ids) {
		AjaxJson result = new AjaxJson();
		String[] idArr = ids.split(",");
		List<String> errMsg = Lists.newArrayList();
		for (int i = 0; i < idArr.length; i++) {
			BalanceSale balanceSale = balanceSaleService.get(idArr[i]);
			if (balanceSale != null) {
				// 处理
				ArrayList<String> errArr = balanceSaleService.dealBalanceSale(balanceSale);
				if (errArr.isEmpty()) {
					balanceSale.setStatus("1");
					balanceSaleService.saveOnly(balanceSale);
				} else {
					errMsg.addAll(errArr);
				}
			}
		}
		if (!errMsg.isEmpty()) {
			result.setSuccess(false);
			result.setMsg(errMsg.toString());
		}
		return result;
	}
	
	@RequestMapping("/getOperOrderById")
	@ResponseBody
	public AjaxJson getOperOrderById(String id) {
		AjaxJson result = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("operOrder", operOrderService.get(id));
		result.setBody(body);
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
		// 由保存状态改成提交状态，那么就需要进行入库操作
		if ("0".equals(operOrder.getStatus()) && "1".equals(status)) {
			List<Product> errDetails = operOrderService.dealStorage(operOrder, true);
			if (errDetails.size() == 0) {
				operOrderService.dealStorage(operOrder, false);
				operOrder.setCreateDate(new Date());
			} else {
				StringBuffer errMsg = new StringBuffer();
				for (Product product: errDetails) {
					errMsg.append(product.getRemarks() + "\r\n");
				}
				result.setSuccess(false);
				result.setMsg(errMsg.toString());
				return result;
			}
		}
		// 如果是提交状态
		if ("1".equals(status)) {
			
		}
		operOrder.setStatus(status);
		operOrderService.save(operOrder);
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
			logger.debug("付款误差：" + DictUtils.getDictValue("small", "order_complete_deviation", "0"));
			if ((operOrder.getRealPrice() - operOrder.getRealPay()) <= Double.parseDouble(DictUtils.getDictValue("order_complete_deviation", "small", "0"))) {
				operOrder.setStatus("3");
			}
			operOrderService.save(operOrder);
		}
		return result;
	}
	
	
}
