package com.jeeplus.modules.jxc.api;

import java.util.LinkedHashMap;
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
	
	@RequestMapping("/uploadBalanceSell")
	@ResponseBody
	public AjaxJson uploadBalanceSell() {
		AjaxJson result = new AjaxJson();
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
			OperOrderDetail returnOperOrder = dealStorage(operOrder, true);
			if (returnOperOrder == null) {
				dealStorage(operOrder, false);
			} else {
				result.setSuccess(false);
				result.setMsg(returnOperOrder.getProduct().getName() + " 库存不足");
				return result;
			}
		}
		// 如果是提交状态
		if ("1".equals(status)) {
			
		}
		operOrderService.updateOrderStatus(id, status);
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
	
	public OperOrderDetail dealStorage(OperOrder operOrder, Boolean isCheck) {
		List<OperOrderDetail> detailList = operOrder.getOperOrderDetailList();
		for (OperOrderDetail operOrderDetail: detailList) {
			Price sPrice = new Price();
			sPrice.setProduct(operOrderDetail.getProduct());
			// 获取产品价格属性表 按照换算比例升序
			List<Price> priceList = priceService.findList(sPrice);
			Storage sStorage = new Storage();
			sStorage.setProduct(operOrderDetail.getProduct());
			sStorage.setStore(operOrder.getStore());
			// 获取产品库存情况
			List<Storage> storageList = storageService.findList(sStorage);
			// 标识基本价格
			int base = -1;
			// 入库产品价格信息
			int orderIndex = -1;
			for (int i = 0; i< priceList.size(); i++) {
				for (int j = 0; j < storageList.size(); j++) {
					if (priceList.get(i).getId().equals(storageList.get(j).getPrice().getId())) {
						priceList.get(i).setStorage(storageList.get(j));
						storageList.remove(j);
						break;
					}
				}
				if ("0".equals(priceList.get(i).getIsBasic())) {
					base = i;
				} else if (i == priceList.size() - 1) {
					base = 0;
				}
				if (priceList.get(i).getId().equals(operOrderDetail.getPrice().getId())) {
					orderIndex = i;
				}
			}
			if (base == -1 || orderIndex == -1) {
				// 库存操作失败 原因：产品价格信息错误
				return operOrderDetail;
			}
			// 减库
			if ("-1".equals(operOrderDetail.getOperType())) {
				// 减
				Double storageAmount = 0d;
				Double needAmount = priceList.get(orderIndex).getRatio() / priceList.get(base).getRatio() * operOrderDetail.getAmount();
				for (int i = 0; i< priceList.size(); i++) {
					if (priceList.get(i).getStorage() != null) {
						storageAmount += priceList.get(i).getRatio() / priceList.get(base).getRatio() * priceList.get(i).getStorage().getAmount();
					}
				}
				// 剩余库存
				Double remainder = storageAmount - needAmount;
				// 库存不足
				if (remainder < 0) {
					return operOrderDetail;
				} else {
					if (isCheck) continue;
					for (int i = priceList.size() - 1; i >= 0; i--) {
						if (base == i) {
							Storage storage = priceList.get(orderIndex).getStorage();
							if (storage == null) {
								storage = new Storage();
								storage.setAmount(remainder);
								storage.setPrice(operOrderDetail.getPrice());
								storage.setProduct(operOrderDetail.getProduct());
								storage.setStore(operOrder.getStore());
							} else {
								storage.setAmount(remainder);
							}
							remainder = 0d;
							storageService.save(storage);
						}
						if (priceList.get(i).getStorage() != null && priceList.get(i).getStorage().getAmount() > 0) {
							Double amount = Math.floor(priceList.get(base).getRatio() / priceList.get(i).getRatio() * remainder);
							if (amount >= priceList.get(i).getStorage().getAmount() ) {
								remainder = remainder - priceList.get(i).getStorage().getAmount() * priceList.get(i).getRatio() / priceList.get(base).getRatio();
							} else {
								remainder = remainder - amount * priceList.get(i).getRatio() / priceList.get(base).getRatio();
								priceList.get(i).getStorage().setAmount(amount);
								storageService.save(priceList.get(i).getStorage());
							}
						}
					}
				}
			}
			// 加库
			else {
				if (isCheck) continue;
				// 如果没有相应的产品库存，那么就创建一条记录
				Storage storage = priceList.get(orderIndex).getStorage();
				if (storage == null) {
					storage = new Storage();
					storage.setAmount(operOrderDetail.getAmount());
					storage.setPrice(operOrderDetail.getPrice());
					storage.setProduct(operOrderDetail.getProduct());
					storage.setStore(operOrder.getStore());
				} else {
					storage.setAmount(operOrderDetail.getAmount() + storage.getAmount());
				}
				// 保存 库存
				storageService.save(storage);
			}
		}
		return null;
	}
}
