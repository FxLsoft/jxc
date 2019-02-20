/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 电子秤销售Entity
 * @author FxLsoft
 * @version 2019-02-19
 */
public class BalanceSale extends DataEntity<BalanceSale> {
	
	private static final long serialVersionUID = 1L;
	private String saleId;		// 唯一码
	private String saleNo;		// 销售编号
	private String status;		// 
	private String balanceNo;		// 称编号
	private Date saleTime;		// 销售时间
	private String wholeNo;		// 全局累计记录
	private String currentNo;		// 当前累计记录
	private String sellerNo;		// 营业员编号
	private String buyerCardNo;		// 买方账号
	private Double realPay;		// 实收现金
	private Double moneyPay;		// 现付
	private Double cardPay;		// 卡付
	private String isRegularSale;		// 是否异常销售
	private String sellerCardNo;		// 卖方账号
	private String payType;		// 支付方式
	private Date beginSaleTime;		// 开始 销售时间
	private Date endSaleTime;		// 结束 销售时间
	private List<BalanceSaleDetail> balanceSaleDetailList = Lists.newArrayList();		// 子表列表
	
	public BalanceSale() {
		super();
	}

	public BalanceSale(String id){
		super(id);
	}

	@ExcelField(title="唯一码", align=2, sort=1)
	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	
	@ExcelField(title="销售编号", align=2, sort=2)
	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	
	@ExcelField(title="称编号", align=2, sort=3)
	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="销售时间", align=2, sort=4)
	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}
	
	@ExcelField(title="全局累计记录", align=2, sort=5)
	public String getWholeNo() {
		return wholeNo;
	}

	public void setWholeNo(String wholeNo) {
		this.wholeNo = wholeNo;
	}
	
	@ExcelField(title="当前累计记录", align=2, sort=6)
	public String getCurrentNo() {
		return currentNo;
	}

	public void setCurrentNo(String currentNo) {
		this.currentNo = currentNo;
	}
	
	@ExcelField(title="营业员编号", align=2, sort=7)
	public String getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}
	
	@ExcelField(title="买方账号", align=2, sort=8)
	public String getBuyerCardNo() {
		return buyerCardNo;
	}

	public void setBuyerCardNo(String buyerCardNo) {
		this.buyerCardNo = buyerCardNo;
	}
	
	@ExcelField(title="实收现金", align=2, sort=9)
	public Double getRealPay() {
		return realPay;
	}

	public void setRealPay(Double realPay) {
		this.realPay = realPay;
	}
	
	@ExcelField(title="现付", align=2, sort=10)
	public Double getMoneyPay() {
		return moneyPay;
	}

	public void setMoneyPay(Double moneyPay) {
		this.moneyPay = moneyPay;
	}
	
	@ExcelField(title="卡付", align=2, sort=11)
	public Double getCardPay() {
		return cardPay;
	}

	public void setCardPay(Double cardPay) {
		this.cardPay = cardPay;
	}
	
	@ExcelField(title="是否异常销售", dictType="yes_no", align=2, sort=12)
	public String getIsRegularSale() {
		return isRegularSale;
	}

	public void setIsRegularSale(String isRegularSale) {
		this.isRegularSale = isRegularSale;
	}
	
	@ExcelField(title="卖方账号", align=2, sort=13)
	public String getSellerCardNo() {
		return sellerCardNo;
	}

	public void setSellerCardNo(String sellerCardNo) {
		this.sellerCardNo = sellerCardNo;
	}
	
	@ExcelField(title="支付方式", dictType="order_pay_type", align=2, sort=14)
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public Date getBeginSaleTime() {
		return beginSaleTime;
	}

	public void setBeginSaleTime(Date beginSaleTime) {
		this.beginSaleTime = beginSaleTime;
	}
	
	public Date getEndSaleTime() {
		return endSaleTime;
	}

	public void setEndSaleTime(Date endSaleTime) {
		this.endSaleTime = endSaleTime;
	}
		
	public List<BalanceSaleDetail> getBalanceSaleDetailList() {
		return balanceSaleDetailList;
	}

	public void setBalanceSaleDetailList(List<BalanceSaleDetail> balanceSaleDetailList) {
		this.balanceSaleDetailList = balanceSaleDetailList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}