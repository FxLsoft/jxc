/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.jxc.entity.Store;
import java.util.Date;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 财务报表Entity
 * @author FxLsoft
 * @version 2019-02-23
 */
public class Report extends DataEntity<Report> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 报表头
	private String date;		// 日期
	private Store store;		// 门店
	private Double saleIn;		// 销售应收
	private Double saleRealIn;		// 销售实收
	private Double returnPay;		// 退货应付
	private Double returnRealPay;		// 退货实付
	private Double oldDebtIn;		// 欠款已收
	private Double balanceIn;		// 电子秤销售
	private Double totalIn;		// 实收总金额
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	
	public Report() {
		super();
	}

	public Report(String id){
		super(id);
	}

	@ExcelField(title="报表头", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="日期", align=2, sort=2)
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	@ExcelField(title="门店", fieldType=Store.class, value="store.name", align=2, sort=3)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	@ExcelField(title="销售应收", align=2, sort=4)
	public Double getSaleIn() {
		return saleIn;
	}

	public void setSaleIn(Double saleIn) {
		this.saleIn = saleIn;
	}
	
	@ExcelField(title="销售实收", align=2, sort=5)
	public Double getSaleRealIn() {
		return saleRealIn;
	}

	public void setSaleRealIn(Double saleRealIn) {
		this.saleRealIn = saleRealIn;
	}
	
	@ExcelField(title="退货应付", align=2, sort=6)
	public Double getReturnPay() {
		return returnPay;
	}

	public void setReturnPay(Double returnPay) {
		this.returnPay = returnPay;
	}
	
	@ExcelField(title="退货实付", align=2, sort=7)
	public Double getReturnRealPay() {
		return returnRealPay;
	}

	public void setReturnRealPay(Double returnRealPay) {
		this.returnRealPay = returnRealPay;
	}
	
	@ExcelField(title="欠款已收", align=2, sort=8)
	public Double getOldDebtIn() {
		return oldDebtIn;
	}

	public void setOldDebtIn(Double oldDebtIn) {
		this.oldDebtIn = oldDebtIn;
	}
	
	@ExcelField(title="电子秤销售", align=2, sort=9)
	public Double getBalanceIn() {
		return balanceIn;
	}

	public void setBalanceIn(Double balanceIn) {
		this.balanceIn = balanceIn;
	}
	
	@ExcelField(title="实收总金额", align=2, sort=10)
	public Double getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(Double totalIn) {
		this.totalIn = totalIn;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}