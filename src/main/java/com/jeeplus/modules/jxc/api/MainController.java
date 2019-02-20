package com.jeeplus.modules.jxc.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeeplus.modules.jxc.entity.Balance;
import com.jeeplus.modules.jxc.entity.BalanceSale;
import com.jeeplus.modules.jxc.entity.BalanceSaleDetail;
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.entity.OperOrderDetail;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.entity.Storage;
import com.jeeplus.modules.jxc.service.BalanceSaleService;
import com.jeeplus.modules.jxc.service.BalanceService;
import com.jeeplus.modules.jxc.service.OperOrderPayService;
import com.jeeplus.modules.jxc.service.OperOrderService;
import com.jeeplus.modules.jxc.service.PriceService;
import com.jeeplus.modules.jxc.service.ProductService;
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
	
	@Autowired
	private BalanceSaleService balanceSaleService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BalanceService balanceService;
	
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
				ArrayList<String> errArr = dealBalanceSale(balanceSale);
				if (errArr.isEmpty()) {
					balanceSale.setStatus("1");
					balanceSaleService.saveOnly(balanceSale);
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
			List<OperOrderDetail> errDetails = dealStorage(operOrder, true);
			if (errDetails.size() == 0) {
				dealStorage(operOrder, false);
			} else {
				StringBuffer errMsg = new StringBuffer();
				for (OperOrderDetail operOrderDetail: errDetails) {
					errMsg.append(operOrderDetail.getRemarks() + "\r\n");
				}
				result.setSuccess(false);
				result.setMsg(errMsg.toString());
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
	
	public List<OperOrderDetail> dealStorage(OperOrder operOrder, Boolean isCheck) {
		List<OperOrderDetail> errDetail = Lists.newArrayList();
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
				operOrderDetail.setRemarks("库存操作失败 原因： " + operOrderDetail.getProduct().getName() + " 产品价格信息错误");
				errDetail.add(operOrderDetail);
				continue;
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
					// 库存操作失败 原因：产品价格信息错误
					operOrderDetail.setRemarks("库存操作失败 原因： " + operOrderDetail.getProduct().getName() + " 产品价格信息错误");
					errDetail.add(operOrderDetail);
					continue;
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
		return errDetail;
	}
	
	public ArrayList<String> dealBalanceSale (BalanceSale balanceSale) {
		ArrayList<String> errMsg = Lists.newArrayList();
		// 通过电子秤编号获取电子秤详情
		Balance balance = balanceService.findUniqueByProperty("no", balanceSale.getSaleId());
		OperOrder operOrder = new OperOrder();
		if (balance == null) {
			errMsg.add("电子秤编号：" + balanceSale.getSaleId() + " 未被管理；");
		} else {
			operOrder.setNo(balanceSale.getSaleNo());
			// 出库
			operOrder.setType("1");
			// 提交
			operOrder.setStatus("1");
			// 电子秤零售
			operOrder.setSource("3");
			operOrder.setStore(balance.getStore());
			operOrder.setTotalPrice(balanceSale.getRealPay());
			operOrder.setRealPay(balanceSale.getRealPay());
			operOrder.setRealPrice(balanceSale.getRealPay());
			operOrder.setOperOrderDetailList(Lists.newArrayList());
		}
		for (BalanceSaleDetail balanceSaleDetail: balanceSale.getBalanceSaleDetailList()) {
			// 通过产品计重编号获取产品详情
			Product product = productService.findUniqueByProperty("weight_no", balanceSaleDetail.getProductNo());
			if (product == null) {
				errMsg.add("计重编号为：" + balanceSaleDetail.getProductNo() + " 的产品未被管理；\r\n");
			} else if (balance != null){
				Price basePrice = null;
				for (Price price: product.getPriceList()) {
					if (price.getUnit().equals(balance.getBaseUnit())) {
						basePrice = price;
						break;
					}
				}
				if (basePrice == null) {
					errMsg.add(product.getName() + " 产品的价格设置与称的设置不相符；\r\n");
				} else {
					OperOrderDetail operOrderDetail = new OperOrderDetail();
					operOrderDetail.setOperType("-1");
					operOrderDetail.setProduct(product);
					operOrderDetail.setPrice(basePrice);
					operOrderDetail.setAmount(balanceSaleDetail.getAmount());
					operOrderDetail.setOperPrice(balanceSaleDetail.getSalePrice());
					operOrder.getOperOrderDetailList().add(operOrderDetail);
				}
			}
		}
		if (errMsg.size() == 0) {
			operOrderService.save(operOrder);
			// 操作库存 先检查再入库
			List<OperOrderDetail> errDetails = dealStorage(operOrder, true);
			if (errDetails.size() == 0) {
				dealStorage(operOrder, false);
			} else {
				StringBuffer msg = new StringBuffer();
				for (OperOrderDetail operOrderDetail: errDetails) {
					msg.append(operOrderDetail.getRemarks() + "\r\n");
				}
				errMsg.add(msg.toString());
			}
		}
		return errMsg;
	}
}
