/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.jxc.entity.StockOrder;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 应付单Entity
 * @author 周涛
 * @version 2018-12-22
 */
public class BillOrder extends DataEntity<BillOrder> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 单号
	private StockOrder stockOrder;		// 入库单号
	private Double payMoney;		// 应付金额
	private Integer status;		// 审核状态
	private Date approveDate;		// 审核时间
	private Date cancelDate;		// 作废时间
	
	public BillOrder() {
		super();
	}

	public BillOrder(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@NotNull(message="入库单号不能为空")
	@ExcelField(title="入库单号", fieldType=StockOrder.class, value="stockOrder.no", align=2, sort=2)
	public StockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}
	
	@NotNull(message="应付金额不能为空")
	@ExcelField(title="应付金额", align=2, sort=3)
	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
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
	
}