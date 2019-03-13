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
 * @version 2019-03-13
 */
public class Report extends DataEntity<Report> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 报表头
	private String date;		// 日期
	private Store store;		// 门店
	private Double saleIn;		// 销售应收
	private Double saleRealIn;		// 销售实收
	private Double saleBenefit;		// 销售优惠
	private Double purchaseOut;		// 采购应付
	private Double purchaseRealOut;		// 采购实付
	private Double returnPay;		// 退货应付
	private Double returnRealPay;		// 退货实付
	private Double oldDebtIn;		// 欠款已收
	private Double oldDebtOut;		// 欠款已付
	private Double balanceIn;		// 电子秤销售
	private Double wxPay;		// 微信收款
	private Double aliPay;		// 支付宝收款
	private Double bankPay;		// 银行卡收款
	private Double moenyPay;		// 现付
	private Double totalIn;		// 实收总金额
	private Double totalRealIn;		// 实收总计
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
	
	@ExcelField(title="销售优惠", align=2, sort=6)
	public Double getSaleBenefit() {
		return saleBenefit;
	}

	public void setSaleBenefit(Double saleBenefit) {
		this.saleBenefit = saleBenefit;
	}
	
	@ExcelField(title="采购应付", align=2, sort=7)
	public Double getPurchaseOut() {
		return purchaseOut;
	}

	public void setPurchaseOut(Double purchaseOut) {
		this.purchaseOut = purchaseOut;
	}
	
	@ExcelField(title="采购实付", align=2, sort=8)
	public Double getPurchaseRealOut() {
		return purchaseRealOut;
	}

	public void setPurchaseRealOut(Double purchaseRealOut) {
		this.purchaseRealOut = purchaseRealOut;
	}
	
	@ExcelField(title="退货应付", align=2, sort=9)
	public Double getReturnPay() {
		return returnPay;
	}

	public void setReturnPay(Double returnPay) {
		this.returnPay = returnPay;
	}
	
	@ExcelField(title="退货实付", align=2, sort=10)
	public Double getReturnRealPay() {
		return returnRealPay;
	}

	public void setReturnRealPay(Double returnRealPay) {
		this.returnRealPay = returnRealPay;
	}
	
	@ExcelField(title="欠款已收", align=2, sort=11)
	public Double getOldDebtIn() {
		return oldDebtIn;
	}

	public void setOldDebtIn(Double oldDebtIn) {
		this.oldDebtIn = oldDebtIn;
	}
	
	@ExcelField(title="欠款已付", align=2, sort=12)
	public Double getOldDebtOut() {
		return oldDebtOut;
	}

	public void setOldDebtOut(Double oldDebtOut) {
		this.oldDebtOut = oldDebtOut;
	}
	
	@ExcelField(title="电子秤销售", align=2, sort=13)
	public Double getBalanceIn() {
		return balanceIn;
	}

	public void setBalanceIn(Double balanceIn) {
		this.balanceIn = balanceIn;
	}
	
	@ExcelField(title="微信收款", align=2, sort=14)
	public Double getWxPay() {
		return wxPay;
	}

	public void setWxPay(Double wxPay) {
		this.wxPay = wxPay;
	}
	
	@ExcelField(title="支付宝收款", align=2, sort=15)
	public Double getAliPay() {
		return aliPay;
	}

	public void setAliPay(Double aliPay) {
		this.aliPay = aliPay;
	}
	
	@ExcelField(title="银行卡收款", align=2, sort=16)
	public Double getBankPay() {
		return bankPay;
	}

	public void setBankPay(Double bankPay) {
		this.bankPay = bankPay;
	}
	
	@ExcelField(title="现付", align=2, sort=17)
	public Double getMoenyPay() {
		return moenyPay;
	}

	public void setMoenyPay(Double moenyPay) {
		this.moenyPay = moenyPay;
	}
	
	@ExcelField(title="实收总金额", align=2, sort=18)
	public Double getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(Double totalIn) {
		this.totalIn = totalIn;
	}
	
	@ExcelField(title="实收总计", align=2, sort=19)
	public Double getTotalRealIn() {
		return totalRealIn;
	}

	public void setTotalRealIn(Double totalRealIn) {
		this.totalRealIn = totalRealIn;
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