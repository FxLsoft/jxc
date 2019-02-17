package com.jeeplus.modules.jxc.task;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.IdWorker;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.jxc.entity.Balance;
import com.jeeplus.modules.jxc.entity.BalanceSale;
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.entity.OperOrderDetail;
import com.jeeplus.modules.jxc.entity.Price;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.service.BalanceSaleService;
import com.jeeplus.modules.jxc.service.BalanceService;
import com.jeeplus.modules.jxc.service.OperOrderService;
import com.jeeplus.modules.jxc.service.ProductService;
import com.jeeplus.modules.monitor.entity.Task;
import com.jeeplus.modules.sys.entity.Log;
import com.jeeplus.modules.sys.service.LogService;

public class DailyTask extends Task{
	
	
	@Override
	public void run() {
		BalanceSaleService balanceSaleService = SpringContextHolder.getBean(BalanceSaleService.class);
		ProductService productService = SpringContextHolder.getBean(ProductService.class);
		BalanceService balanceService = SpringContextHolder.getBean(BalanceService.class);
		OperOrderService operOrderService = SpringContextHolder.getBean(OperOrderService.class);
		LogService logService = SpringContextHolder.getBean(LogService.class);
	 	List<BalanceSale> saleList = balanceSaleService.findAllList(new BalanceSale());
	 	if (saleList != null && saleList.size() > 0) {
	 		List<OperOrderDetail> operOrderDetailList = Lists.newArrayList();
	 		Double totalPrice = 0d;
	 		for (BalanceSale balanceSale: saleList) {
	 			OperOrderDetail operOrderDetail = new OperOrderDetail();
	 			Product product = productService.findUniqueByProperty("weight_no", balanceSale.getWeightNo());
	 			Balance balance = balanceService.findUniqueByProperty("no", balanceSale.getBalanceNo());
	 			balanceSale.setProduct(product);
	 			balanceSale.setBalance(balance);
	 			List<Price> priceList = product.getPriceList();
	 			Price orderPrice = null;
	 			for (int i = 0; i < priceList.size(); i++) {
	 				if (priceList.get(i).getUnit().equals(balance.getBaseUnit())) {
	 					orderPrice = priceList.get(i);
	 					break;
	 				}
	 				if (i == priceList.size() - 1) {
	 					orderPrice = priceList.get(0);
	 				}
	 			}
	 			if (orderPrice == null) {
	 				Log log = new Log();
	 				log.setMethod("DailyTask.run");
	 				log.setException("" + balanceSale.getBalanceNo() + "生成订单失败");
	 				logService.save(log);
	 				continue;
	 			};
	 			operOrderDetail.setProduct(product);
	 			operOrderDetail.setPrice(orderPrice);
	 			operOrderDetail.setAmount(balanceSale.getAmount());
	 			operOrderDetail.setOperPrice(orderPrice.getAdvancePrice());
	 			operOrderDetailList.add(operOrderDetail);
	 		}
	 		OperOrder operOrder = new OperOrder();
	 		operOrder.setNo("D" + IdWorker.getId());
	 		// 电子秤出库
	 		operOrder.setType("1");
	 		// 提交状态
	 		operOrder.setStatus("1");
	 		// 电子秤销售
	 		operOrder.setSource("3");
	 		// 总计
	 		operOrder.setTotalPrice(totalPrice);
	 		operOrder.setRealPrice(totalPrice);
	 		// 单据详情
	 		operOrder.setOperOrderDetailList(operOrderDetailList);
	 		// 统一插入/保存
	 		operOrderService.save(operOrder);
	 	}
//		FinancialService financialService = SpringContextHolder.getBean(FinancialService.class);
//		LogService logService = SpringContextHolder.getBean(LogService.class);
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Calendar calendar = Calendar.getInstance();
//		Log log = new Log();
//		calendar.add(Calendar.DATE, -1);
//		calendar.set(Calendar.HOUR, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
//		Date  beginDate = calendar.getTime();
//		calendar.set(Calendar.HOUR, 23);
//		calendar.set(Calendar.MINUTE, 59);
//		calendar.set(Calendar.SECOND, 59);
//		Date endDate = calendar.getTime();
//		Financial financial = financialService.queryFinancialByTime(beginDate, endDate);
//		// 日报
//		financial.setId("BB" + IdWorker.getId());
//		financial.setType(1);
//		financial.setCreateDate(beginDate);
//		financial.setUpdateDate(endDate);
//		financial.setRemarks("由系统生成");
//		financialService.insert(financial);
//		log.setTitle(dateFormat.format(beginDate) + " - " + dateFormat.format(endDate) + " 已经生成");
//		logService.save(log);
	}
}
