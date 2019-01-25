package com.jeeplus.modules.jxc.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.IdWorker;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.jxc.entity.BillOrder;
import com.jeeplus.modules.jxc.entity.Financial;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.entity.PurchaseOrder;
import com.jeeplus.modules.jxc.entity.PurchaseOrderDetail;
import com.jeeplus.modules.jxc.entity.ReturnBill;
import com.jeeplus.modules.jxc.entity.ReturnOrder;
import com.jeeplus.modules.jxc.entity.ReturnOrderDetail;
import com.jeeplus.modules.jxc.entity.RevenueOrder;
import com.jeeplus.modules.jxc.entity.SaleOrder;
import com.jeeplus.modules.jxc.entity.SaleOrderDetail;
import com.jeeplus.modules.jxc.entity.StockOrder;
import com.jeeplus.modules.jxc.entity.StockOrderDetail;
import com.jeeplus.modules.jxc.entity.Warehouse;
import com.jeeplus.modules.jxc.mapper.StockOrderDetailMapper;
import com.jeeplus.modules.jxc.service.BillOrderService;
import com.jeeplus.modules.jxc.service.FinancialService;
import com.jeeplus.modules.jxc.service.ProductService;
import com.jeeplus.modules.jxc.service.PurchaseOrderService;
import com.jeeplus.modules.jxc.service.ReturnBillService;
import com.jeeplus.modules.jxc.service.ReturnOrderService;
import com.jeeplus.modules.jxc.service.RevenueOrderService;
import com.jeeplus.modules.jxc.service.SaleOrderService;
import com.jeeplus.modules.jxc.service.StockOrderService;
import com.jeeplus.modules.jxc.service.WarehouseService;
import com.jeeplus.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/jxc")
public class MainController {
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Autowired
	private StockOrderService stockOrderService;
	
	@Autowired
	private SaleOrderService saleOrderService;
	
	@Autowired
	private ReturnOrderService returnOrderService;
	
	@Autowired
	private BillOrderService billOrderService;
	
	@Autowired
	private RevenueOrderService revenueOrderService;
	
	@Autowired
	private ReturnBillService returnBillService;
	
	@Autowired
	private WarehouseService warehouseService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FinancialService financialService;
	
	@Autowired
	private StockOrderDetailMapper stockOrderDetailMapper;
	
	@Bean
	public SystemInfoSocketHandler systemInfoSocketHandler() {
	        return new SystemInfoSocketHandler();
	}
	
	@RequestMapping(value = {"home", ""})
	public String home() {
		return "modules/jxc/home";
	}
	
	@RequestMapping(value = "retail")
	public String retail() {
		return "modules/jxc/retail";
	}
	
	@RequestMapping(value = "getWorker")
	@ResponseBody
	public String getWorker() {
		return IdWorker.getId().toString();
	}
	
	@RequestMapping(value = "queryProductByKey")
	@ResponseBody
	public AjaxJson queryProductByKey(String key) {
		AjaxJson result = new AjaxJson();
	    List<Product> list = productService.queryProductByKey(key, 1, 0, 20, UserUtils.getUser().isAdmin() ? null : UserUtils.getUser().getCompany().getName());
	    LinkedHashMap<String, Object> body = new LinkedHashMap<>();
	    body.put("product", list);
	    result.setBody(body);
		return result;
	}
	
	@RequestMapping(value = "queryProductByPage")
	@ResponseBody
	public Map<String, Object> queryProductByPage(String key, Integer pageNo, Integer pageSize) {
		Map<String, Object> result = new HashMap<>();
		int start = (pageNo - 1) * pageSize;
		int end = start + pageSize;
	    List<Product> list = productService.queryProductByKey(key, null, start, end, UserUtils.getUser().isAdmin() ? null : UserUtils.getUser().getCompany().getName());
	    Integer total = productService.countProductByKey(key, null, UserUtils.getUser().isAdmin() ? null : UserUtils.getUser().getCompany().getName());
	    result.put("rows", list);
	    result.put("total", total);
		return result;
	}
	
	@RequestMapping(value = "retailComfirmOrder")
	@ResponseBody
	public AjaxJson retailComfirmOrder(Double payMoney, String productList) {
		AjaxJson result = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<>();
		JSONArray list = JSONArray.parseArray(productList);
		// 创建销售订单
		SaleOrder saleOrder = new SaleOrder();
		List<SaleOrderDetail> saleOrderDetail = new ArrayList<>();
		saleOrder.setNo("XS" + IdWorker.getId());
		saleOrder.setStatus(1);
		saleOrder.setType(1);
		Double sum = 0d;
		// 查询库存是否充足
		for (int i = 0, size = list.size(); i < size; i++) {
			JSONObject p = list.getJSONObject(i);
			SaleOrderDetail detail = new SaleOrderDetail();
			Product product = new Product();
			product.setId(p.getString("id"));
			detail.setId("");
			detail.setProduct(product);
			detail.setPrice(p.getDouble("price"));
			detail.setQuantity(p.getDouble("amount"));
			saleOrderDetail.add(detail);
			sum += p.getDouble("price") * p.getInteger("amount");
			Double total = warehouseService.sumProduct(product.getId(), 1, UserUtils.getUser().isAdmin() ? null : UserUtils.getUser().getCompany().getName());
			if (total < detail.getQuantity()) {
				result.setMsg(p.getString("name") + "库存不足(" + total + ")");
				result.setSuccess(false);
				return result;
			}
		}
		saleOrder.setSaleOrderDetailList(saleOrderDetail);
		saleOrder.setSum(sum);
		// 减库存
		for (int i = 0, size = saleOrderDetail.size(); i < size; i++) {
			SaleOrderDetail orderDetail = saleOrderDetail.get(i);
			Double stock = orderDetail.getQuantity();
			List<Warehouse> wList = warehouseService.selectWarehouseById(orderDetail.getProduct().getId(), 1, UserUtils.getUser().isAdmin() ? null : UserUtils.getUser().getCompany().getName());
			for (int j = 0, wSize = wList.size(); j < wSize; j++) {
				Warehouse w = wList.get(j);
				if (stock >= w.getQuantity()) {
					warehouseService.deleteByLogic(w);
				} else {
					w.setQuantity(w.getQuantity() - stock);
					warehouseService.save(w);
				}
				stock = stock - w.getQuantity();
				if (stock <= 0) {
					break;
				}
			}
		}
		saleOrderService.save(saleOrder);
		// 创建应收单
		RevenueOrder revenueOrder = new RevenueOrder();
		revenueOrder.setNo("YS" + IdWorker.getId());
		revenueOrder.setStatus(1);
		revenueOrder.setReceiveMoney(payMoney);
		revenueOrder.setRealMoney(sum);
		revenueOrder.setSaleOrder(saleOrder);
		revenueOrderService.save(revenueOrder);
		result.setBody(body);
		return result;
	}
	
	@RequestMapping(value = "print")
	public String print(String id, String type, Model model) {
		// 采购
		if ("cg".equals(type)) {
			model.addAttribute("data", purchaseOrderService.get(id));
			model.addAttribute("mode", "dj");
			model.addAttribute("title", "采购单");
		}
		// 入库
		else if ("rk".equals(type)) {
			model.addAttribute("data", stockOrderService.get(id));
			model.addAttribute("mode", "dj");
			model.addAttribute("title", "入库单");
		}
		// 销售
		else if ("xs".equals(type)) {
			model.addAttribute("data", saleOrderService.get(id));
			model.addAttribute("mode", "dj");
			model.addAttribute("title", "销售单");
		}
		// 退货
		else if ("th".equals(type)) {
			model.addAttribute("data", returnOrderService.get(id));
			model.addAttribute("mode", "dj");
			model.addAttribute("title", "退货单");
		}
		// 应付单
		else if ("yf".equals(type)) {
			model.addAttribute("data", billOrderService.get(id));
			model.addAttribute("mode", "sj");
			model.addAttribute("title", "应付单");
		}
		// 应收单
		else if ("ys".equals(type)) {
			model.addAttribute("data", revenueOrderService.get(id));
			model.addAttribute("mode", "sj");
			model.addAttribute("title", "应收单");
		}
		// 退款
		else if ("thd".equals(type)) {
			model.addAttribute("data", returnBillService.get(id));
			model.addAttribute("mode", "sj");
			model.addAttribute("title", "退款单");
		}
		return "modules/jxc/print";
	}
	
	/**
	 * 审核采购订单
	 */
	@ResponseBody
//	@RequiresPermissions("jxc:order:check")
	@RequestMapping(value = "check/order")
	public AjaxJson check(String id, Integer status, String type) {
		AjaxJson j = new AjaxJson();
		@SuppressWarnings("rawtypes")
		DataEntity entity = null;
		// 采购
		if ("cg".equals(type)) {
			entity = purchaseOrderService.get(id);
		}
		// 入库
		else if ("rk".equals(type)) {
			entity = stockOrderService.get(id);
		}
		// 销售
		else if ("xs".equals(type)) {
			entity = saleOrderService.get(id);
		}
		// 退货
		else if ("th".equals(type)) {
			entity = returnOrderService.get(id);
		}
		// 应付单
		else if ("yf".equals(type)) {
			entity = billOrderService.get(id);
		}
		// 应收单
		else if ("ys".equals(type)) {
			entity = revenueOrderService.get(id);
		}
		// 退款
		else if ("thd".equals(type)) {
			entity = returnBillService.get(id);
		}
		if (entity == null) {
			j.setSuccess(false);
			j.setMsg("未查到相关信息");
		}
		else {
			String message = "";
			String no = "";
			// 通过
			if (status == 1) {
				message = "通过";
			}
			// 作废
			else {
				message = "作废";
			}
			// 采购
			if ("cg".equals(type)) {
				PurchaseOrder purchaseOrder = (PurchaseOrder) entity;
				if (status == 1) {
					purchaseOrder.setApproveDate(new Date());
					// 生成入库单
//					StockOrder stockOrder = new StockOrder();
//					stockOrder.setNo("RK" + IdWorker.getId());
//					stockOrder.setStatus(0);
//					stockOrder.setSum(purchaseOrder.getSum());
//					List<StockOrderDetail> sList = new ArrayList<>();
//					List<PurchaseOrderDetail> pList = purchaseOrder.getPurchaseOrderDetailList();
//					for (PurchaseOrderDetail pd: pList) {
//						StockOrderDetail sd = new StockOrderDetail();
//						sd.setId(IdWorker.getId() + "");
//						sd.setProduct(pd.getProduct());
//						sd.setPrice(pd.getPrice());
//						sd.setUnit(pd.getUnit());
//						sd.setIntro(pd.getDesc());
//						sd.setQuantity(pd.getQuantity());
//						sd.setIsRetail(1);
//						sd.setRemarks("采购提交自动入库");
//						sd.setStockOrder(stockOrder);
//						sd.setCreateBy(UserUtils.getUser());
//						sd.setUpdateBy(UserUtils.getUser());
//						sd.setDelFlag("0");
//						stockOrderDetailMapper.insert(sd);
//						sList.add(sd);
//					}
//					stockOrder.setPurchaseOrder(purchaseOrder);
//					stockOrderService.save(stockOrder);
				} else {
					purchaseOrder.setCancelDate(new Date());
				}
				purchaseOrder.setStatus(status);
				purchaseOrderService.save(purchaseOrder);
				no = purchaseOrder.getNo();
			}
			// 入库
			else if ("rk".equals(type)) {
				StockOrder stockOrder = (StockOrder)entity;
				stockOrder.setStatus(status);
				if (status == 1) {
					stockOrder.setApproveDate(new Date());
					// 入库 
					Double sum = 0d;
					List<StockOrderDetail> detailList = stockOrder.getStockOrderDetailList();
					for (StockOrderDetail detail: detailList) {
						Warehouse warehouse = new Warehouse();
						warehouse.setPrice(detail.getPrice());
						warehouse.setProduct(detail.getProduct());
						warehouse.setQuantity(detail.getQuantity());
						warehouse.setUnit(detail.getUnit());
						warehouse.setIntro(detail.getIntro());
						warehouse.setExpiryDate(detail.getExpiryDate());
						warehouse.setReleaseDate(detail.getReleaseDate());
						warehouse.setIsRetail(detail.getIsRetail());
						warehouse.setRemarks("商品来源于入库单：" + stockOrder.getNo());
						warehouse.setCompany(UserUtils.get(stockOrder.getCreateBy().getId()).getCompany().getName());
						warehouseService.save(warehouse);
						sum += detail.getPrice() * detail.getQuantity();
					}
					// 生成应付单
					BillOrder billOrder = new BillOrder();
					billOrder.setNo("YF" + IdWorker.getId());
					billOrder.setPayMoney(sum);
					billOrder.setStatus(0);
					billOrder.setRemarks("由入库单" + stockOrder.getNo() + "生成");
					billOrder.setStockOrder(stockOrder);
					billOrderService.save(billOrder);
				} else {
					stockOrder.setCancelDate(new Date());
				}
				stockOrderService.save(stockOrder);
			}
			// 销售
			else if ("xs".equals(type)) {
				SaleOrder saleOrder = (SaleOrder) entity;
				if (status == 1) {
					// 判断库存是否充足
					List<SaleOrderDetail> saleOrderDetaiList = saleOrder.getUniqueSaleOrderDetailList();
					for (int i = 0, size = saleOrderDetaiList.size(); i < size; i++) {
						SaleOrderDetail saleOrderDetail = saleOrderDetaiList.get(i);
						Double total = warehouseService.sumProduct(saleOrderDetail.getProduct().getId(), saleOrder.getType() == 1 ? 1 : null, UserUtils.getUser().isAdmin() ? null : UserUtils.getUser().getCompany().getName());
						if (total < saleOrderDetail.getQuantity()) {
							j.setMsg(saleOrderDetail.getProduct().getName() + "库存不足(" + total + ")");
							j.setSuccess(false);
							return j;
						}
					}
					Double sum = 0d;
					// 减库存
					for (int i = 0, size = saleOrderDetaiList.size(); i < size; i++) {
						SaleOrderDetail orderDetail = saleOrderDetaiList.get(i);
						Double stock = orderDetail.getQuantity();
						sum += orderDetail.getQuantity() * orderDetail.getPrice();
						List<Warehouse> wList = warehouseService.selectWarehouseById(orderDetail.getProduct().getId(), saleOrder.getType() == 1 ? 1 : null, UserUtils.getUser().isAdmin() ? null : UserUtils.getUser().getCompany().getName());
						for (int k = 0, wSize = wList.size(); k < wSize; k++) {
							Warehouse w = wList.get(k);
							if (stock >= w.getQuantity()) {
								warehouseService.deleteByLogic(w);
							} else {
								w.setQuantity(w.getQuantity() - stock);
								warehouseService.save(w);
							}
							stock = stock - w.getQuantity();
							if (stock <= 0) {
								break;
							}
						}
					}
					saleOrder.setApproveDate(new Date());
					// 生成应收单
					RevenueOrder revenueOrder = new RevenueOrder();
					revenueOrder.setNo("YS" + IdWorker.getId());
					revenueOrder.setSaleOrder(saleOrder);
					revenueOrder.setReceiveMoney(saleOrder.getSum());
					revenueOrder.setRealMoney(sum);
					revenueOrder.setStatus(0);
					revenueOrderService.save(revenueOrder);
				} else {
					saleOrder.setCancelDate(new Date());
				}
				saleOrder.setStatus(status);
				saleOrderService.save(saleOrder);
			}
			// 退货
			else if ("th".equals(type)) {
				ReturnOrder returnOrder = (ReturnOrder) entity;
				returnOrder.setStatus(status);
				if (status == 1) {
					returnOrder.setApproveDate(new Date());
					// 生成退款收据
					ReturnBill returnBill = new ReturnBill();
					returnBill.setNo("TK" + IdWorker.getId());
					returnBill.setReturnMoney(returnOrder.getSum());
					returnBill.setReturnOrder(returnOrder);
					returnBill.setStatus(0);
					returnBillService.save(returnBill);
					// 入库
					List <ReturnOrderDetail> returnOrderDetaiList = returnOrder.getReturnOrderDetailList();
					for (int i = 0, size = returnOrderDetaiList.size(); i < size; i++) {
						Warehouse warehouse = new Warehouse();
						ReturnOrderDetail returnOrderDetail = returnOrderDetaiList.get(i);
						warehouse.setProduct(returnOrderDetail.getProduct());
						warehouse.setPrice(returnOrderDetail.getPrice());
						warehouse.setQuantity(returnOrderDetail.getQuantity());
						warehouse.setUnit(returnOrderDetail.getUnit());
						warehouse.setExpiryDate(returnOrderDetail.getExpiryDate());
						warehouse.setReleaseDate(returnOrderDetail.getReleaseDate());
						warehouse.setRemarks("商品来源于退货单：" + returnOrder.getNo());
						warehouse.setCompany(UserUtils.get(returnBill.getCreateBy().getId()).getCompany().getName());
						warehouse.setIsRetail(returnOrder.getSaleOrder().getType() == 1 ? 1 : 0);
						warehouseService.save(warehouse);
					}
				} else {
					returnOrder.setCancelDate(new Date());
				}
				returnOrderService.save(returnOrder);
				no = returnOrder.getNo();
			}
			// 应付单
			else if ("yf".equals(type)) {
				BillOrder billOrder = (BillOrder) entity;
				billOrder.setStatus(status);
				if (status == 1) {
					billOrder.setApproveDate(new Date());
				} else {
					billOrder.setCancelDate(new Date());
				}
				billOrderService.save(billOrder);
				no = billOrder.getNo();
			}
			// 应收单
			else if ("ys".equals(type)) {
				RevenueOrder revenueOrder = (RevenueOrder) entity;
				revenueOrder.setStatus(status);
				if (status == 1) {
					revenueOrder.setApproveDate(new Date());
				} else {
					revenueOrder.setCancelDate(new Date());
				}
				revenueOrderService.save(revenueOrder);
				no = revenueOrder.getNo();
			}
			// 退款
			else if ("thd".equals(type)) {
				ReturnBill returnBill = (ReturnBill) entity;
				returnBill.setStatus(status);
				if (status == 1) {
					returnBill.setApproveDate(new Date());
				} else {
					returnBill.setCancelDate(new Date());
				}
				returnBillService.save(returnBill);
				no = returnBill.getNo();
			}
			//发送通知到客户端
			systemInfoSocketHandler().sendMessageToUser(UserUtils.get(entity.getCreateBy().getId()).getLoginName(), "单号：" + no + " 状态已更改（" + message + "）");
			j.setSuccess(true);
			j.setMsg("成功");
		}
		return j;
	}
	
	@RequestMapping(value = "queryFinancialByTime")
	@ResponseBody
	public AjaxJson queryFinancialByTime(Long beginDate, Long endDate) {
		AjaxJson result = new AjaxJson();
		Financial financial = financialService.queryFinancialByTime(new Date(beginDate), new Date(endDate));
		LinkedHashMap<String, Object> body = new LinkedHashMap<>();
		body.put("financial", financial);
		result.setBody(body);
		return result;
	}
	
	@RequestMapping(value = "getOffice")
	@ResponseBody
	public AjaxJson getOffice(Boolean isAll) {
		AjaxJson result = new AjaxJson();
		LinkedHashMap<String, Object> body = new LinkedHashMap<>();
		body.put("office", isAll ? UserUtils.getOfficeAllList() : UserUtils.getUser());
		result.setBody(body);
		return result;
	}
	
	@RequestMapping(value = "getSaleOrderById")
	@ResponseBody
	public SaleOrder getSaleOrderById(@RequestParam(required=false) String id) {
		SaleOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = saleOrderService.get(id);
		}
		if (entity == null){
			entity = new SaleOrder();
		}
		return entity;
	}
}
