/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jxc.entity.OperOrder;
import com.jeeplus.modules.jxc.entity.OperOrderPay;
import com.jeeplus.modules.jxc.entity.Report;
import com.jeeplus.modules.jxc.entity.Store;
import com.jeeplus.modules.jxc.mapper.ReportMapper;

/**
 * 财务报表Service
 * 
 * @author FxLsoft
 * @version 2019-03-13
 */
@Service
@Transactional(readOnly = true)
public class ReportService extends CrudService<ReportMapper, Report> {

	@Autowired
	private StoreService storeService;

	@Autowired
	private ReportMapper reportMapper;

	@Autowired
	private OperOrderService operOrderService;

	@Autowired
	private OperOrderPayService operOrderPayService;

	public Report get(String id) {
		return super.get(id);
	}

	public List<Report> findList(Report report) {
		return super.findList(report);
	}

	public Page<Report> findPage(Page<Report> page, Report report) {
		return super.findPage(page, report);
	}

	@Transactional(readOnly = false)
	public void save(Report report) {
		super.save(report);
	}

	@Transactional(readOnly = false)
	public void delete(Report report) {
		super.delete(report);
	}

	// 生成报表
	public boolean createReport() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Store> storeList = storeService.getAllStore();
		Date endDate = new Date();
		Date beginDate = reportMapper.getLastCreateDate();
		if (beginDate == null) {
			beginDate = new Date(0);
		}
		for (Store store : storeList) {
			Report report = new Report();
			Double saleIn = 0d, saleRealIn = 0d, returnPay = 0d, returnRealPay = 0d, oldDebtIn = 0d, totalIn = 0d,
					balanceIn = 0d, balanceRealIn = 0d;
			List<OperOrder> operOrderList = operOrderService.findListByWhere(store.getId(), beginDate, endDate);
			List<OperOrderPay> operOrderPayList = operOrderPayService.findListByWhere(store.getId(), beginDate,
					endDate);
			// 单据来源 （0：采购入库，1：盘点入库，2：退货入库，3、电子秤零售，4、零售出库，5、批发出库）
			for (OperOrder operOrder : operOrderList) {
				if ("4".equals(operOrder.getSource()) || "5".equals(operOrder.getSource())) {
					saleIn += operOrder.getRealPrice();
					saleRealIn += operOrder.getRealPay();
				}
				if ("2".equals(operOrder.getSource())) {
					returnPay += operOrder.getRealPrice();
					returnRealPay += operOrder.getRealPay();
				}
				if ("3".equals(operOrder.getSource())) {
					balanceIn += operOrder.getRealPrice();
					balanceRealIn += operOrder.getRealPay();
				}
			}

			for (OperOrderPay operOrderPay : operOrderPayList) {
				if ("-1".equals(operOrderPay.getPayType())) {
					totalIn -= operOrderPay.getPrice();
				} else {
					totalIn += operOrderPay.getPrice();
				}
				if (operOrderPay.getCreateDate().getTime() < beginDate.getTime()) {
					oldDebtIn += operOrderPay.getPrice();
				}
			}
			report.setTitle("每日财务报表");
			report.setDate(new SimpleDateFormat("yyyy-MM-dd").format(endDate));
			report.setStore(store);
			report.setSaleIn(saleIn);
			report.setSaleRealIn(saleRealIn);
			report.setReturnPay(returnPay);
			report.setReturnRealPay(returnRealPay);
			report.setOldDebtIn(oldDebtIn);
			report.setTotalIn(totalIn);
			report.setBalanceIn(balanceIn);
			report.setRemarks("报表" + dateFormat.format(beginDate) + " - " + dateFormat.format(endDate));
			this.save(report);
		}
		logger.debug(dateFormat.format(beginDate) + " - " + dateFormat.format(endDate) + " 已经生成");
		return true;
	}
}