/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 应收单Entity
 * @author 周涛
 * @version 2018-12-23
 */
public class RevenueOrder extends DataEntity<RevenueOrder> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 单号
	private SaleOrder saleOrder;		// 销售单号
	private Double receiveMoney;		// 金额
	private Double realMoney;	// 实际金额
	private Integer status;		// 审核状态
	private Date approveDate;		// 审核时间
	private Date cancelDate;		// 作废时间
	
	public RevenueOrder() {
		super();
	}

	public RevenueOrder(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@NotNull(message="销售单号不能为空")
	@ExcelField(title="销售单号", fieldType=SaleOrder.class, value="saleOrder.no", align=2, sort=2)
	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
	
	@NotNull(message="金额不能为空")
	@ExcelField(title="金额", align=2, sort=3)
	public Double getReceiveMoney() {
		return receiveMoney;
	}

	public void setReceiveMoney(Double receiveMoney) {
		this.receiveMoney = receiveMoney;
	}
	
	@ExcelField(title="审核状态", dictType="order_status", align=2, sort=4)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=5)
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="作废时间", align=2, sort=6)
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Double getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(Double realMoney) {
		this.realMoney = realMoney;
	}
	
}