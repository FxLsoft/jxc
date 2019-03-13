package com.jeeplus.modules.jxc.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.jxc.entity.BalanceSale;
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.entity.Report;
import com.jeeplus.modules.jxc.entity.Store;
import com.jeeplus.modules.jxc.service.BalanceSaleService;
import com.jeeplus.modules.jxc.service.OperOrderPayService;
import com.jeeplus.modules.jxc.service.OperOrderService;
import com.jeeplus.modules.jxc.service.ReportService;
import com.jeeplus.modules.jxc.service.StoreService;
import com.jeeplus.modules.monitor.entity.Task;

public class DailyTask extends Task{
	
	public Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public void run() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date  beginDate = calendar.getTime();
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date endDate = calendar.getTime();
		logger.debug("任务开始执行。。。");
		BalanceSaleService balanceSaleService = SpringContextHolder.getBean(BalanceSaleService.class);
		OperOrderService operOrderService = SpringContextHolder.getBean(OperOrderService.class);
		OperOrderPayService operOrderPayService = SpringContextHolder.getBean(OperOrderPayService.class);
		StoreService storeService = SpringContextHolder.getBean(StoreService.class);
		ReportService reportService = SpringContextHolder.getBean(ReportService.class);
		List<BalanceSale> balanceSaleList = balanceSaleService.getSaleBalanceList();
		logger.debug("获取所有未被确认的点子销售");
		for (BalanceSale balanceSale: balanceSaleList) {
			if ("1".equals(balanceSale.getStatus())) {
				continue;
			}
			balanceSale = balanceSaleService.get(balanceSale.getId());
			logger.debug("开始处理 [" + balanceSale.getSaleNo() + "]");
			ArrayList<String> errMsg = balanceSaleService.dealBalanceSale(balanceSale);
			if (errMsg.isEmpty()) {
				balanceSale.setStatus("1");
				balanceSaleService.saveOnly(balanceSale);
				logger.debug("处理 [" + balanceSale.getSaleNo() + "]成功 ");
			} else {
				balanceSale.setRemarks(errMsg.toString());
				balanceSaleService.saveOnly(balanceSale);
				logger.debug("处理 [" + balanceSale.getSaleNo() + "]失败，原因：[" + errMsg.toString() + "]");
			}
		}
		logger.debug("点子称销售处理已完成。。。");
//		List<Store> storeList = storeService.getAllStore();
//		for (Store store: storeList) {
//			Report report = new Report();
//			Double saleIn = 0d, saleRealIn = 0d, returnPay = 0d, returnRealPay = 0d, oldDebtIn = 0d, totalIn = 0d, balanceIn = 0d, balanceRealIn = 0d;
//			List<OperOrder> operOrderList = operOrderService.findListByWhere(store.getId(), beginDate, endDate);
//			List<OperOrderPay> operOrderPayList = operOrderPayService.findListByWhere(store.getId(), beginDate, endDate);
////			单据来源（0：采购入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）
//			for (OperOrder operOrder: operOrderList) {
//				if ("4".equals(operOrder.getSource()) || "5".equals(operOrder.getSource())) {
//					saleIn += operOrder.getRealPrice();
//					saleRealIn += operOrder.getRealPay();
//				}
//				if ("2".equals(operOrder.getSource())) {
//					returnPay += operOrder.getRealPrice();
//					returnRealPay += operOrder.getRealPay();
//				}
//				if ("3".equals(operOrder.getSource())) {
//					balanceIn += operOrder.getRealPrice();
//					balanceRealIn += operOrder.getRealPay();
//				}
//			}
//			
//			for (OperOrderPay operOrderPay: operOrderPayList) {
//				if ("-1".equals(operOrderPay.getPayType())) {
//					totalIn -= operOrderPay.getPrice();
//				} else {
//					totalIn += operOrderPay.getPrice();
//				}
//				if (operOrderPay.getCreateDate().getTime() < beginDate.getTime()) {
//					oldDebtIn += operOrderPay.getPrice();
//				}
//			}
//			report.setTitle("每日财务报表");
//			report.setDate(new SimpleDateFormat("yyyy-MM-dd").format(endDate));
//			report.setStore(store);
//			report.setSaleIn(saleIn);
//			report.setSaleRealIn(saleRealIn);
//			report.setReturnPay(returnPay);
//			report.setReturnRealPay(returnRealPay);
//			report.setOldDebtIn(oldDebtIn);
//			report.setTotalIn(totalIn);
//			report.setBalanceIn(balanceIn);
//			report.setRemarks("报表" + dateFormat.format(beginDate) + " - " + dateFormat.format(endDate));
//			reportService.save(report);
//		}
//		logger.debug(dateFormat.format(beginDate) + " - " + dateFormat.format(endDate) + " 已经生成");
	}
}
