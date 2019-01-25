/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 采购订单Entity
 * @author FxLsoft
 * @version 2018-12-22
 */
public class PurchaseOrder extends DataEntity<PurchaseOrder> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 单号
	private Double sum;		// 总金额
	private Integer status;		// 订单状态
	private Date approveDate;		// 审核时间
	private Date cancelDate;		// 作废时间
	private Double beginSum;		// 开始 总金额
	private Double endSum;		// 结束 总金额
	private Date beginUpdateDate;		// 开始 更新时间
	private Date endUpdateDate;		// 结束 更新时间
	private List<PurchaseOrderDetail> purchaseOrderDetailList = Lists.newArrayList();		// 子表列表
	
	public PurchaseOrder() {
		super();
	}

	public PurchaseOrder(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@NotNull(message="总金额不能为空")
	@ExcelField(title="总金额", align=2, sort=2)
	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}
	
	@ExcelField(title="订单状态", dictType="order_status", align=2, sort=3)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=4)
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="作废时间", align=2, sort=5)
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	public Double getBeginSum() {
		return beginSum;
	}

	public void setBeginSum(Double beginSum) {
		this.beginSum = beginSum;
	}
	
	public Double getEndSum() {
		return endSum;
	}

	public void setEndSum(Double endSum) {
		this.endSum = endSum;
	}
		
	public Date getBeginUpdateDate() {
		return beginUpdateDate;
	}

	public void setBeginUpdateDate(Date beginUpdateDate) {
		this.beginUpdateDate = beginUpdateDate;
	}
	
	public Date getEndUpdateDate() {
		return endUpdateDate;
	}

	public void setEndUpdateDate(Date endUpdateDate) {
		this.endUpdateDate = endUpdateDate;
	}
		
	public List<PurchaseOrderDetail> getPurchaseOrderDetailList() {
		return purchaseOrderDetailList;
	}

	public void setPurchaseOrderDetailList(List<PurchaseOrderDetail> purchaseOrderDetailList) {
		this.purchaseOrderDetailList = purchaseOrderDetailList;
	}
}