/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.jxc.entity.PurchaseOrder;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 入库单Entity
 * @author 周涛
 * @version 2018-12-25
 */
public class StockOrder extends DataEntity<StockOrder> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 单号
	private PurchaseOrder purchaseOrder;		// 采购单号
	private Double sum;		// 总金额
	private Integer status;		// 订单状态
	private Date approveDate;		// 审核时间
	private Date cancelDate;		// 作废时间
	private List<StockOrderDetail> stockOrderDetailList = Lists.newArrayList();		// 子表列表
	
	public StockOrder() {
		super();
	}

	public StockOrder(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@NotNull(message="采购单号不能为空")
	@ExcelField(title="采购单号", fieldType=PurchaseOrder.class, value="purchaseOrder.no", align=2, sort=2)
	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}
	
	@ExcelField(title="总金额", align=2, sort=3)
	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}
	
	@ExcelField(title="订单状态", dictType="order_status", align=2, sort=4)
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
	
	public List<StockOrderDetail> getStockOrderDetailList() {
		return stockOrderDetailList;
	}

	public void setStockOrderDetailList(List<StockOrderDetail> stockOrderDetailList) {
		this.stockOrderDetailList = stockOrderDetailList;
	}
}