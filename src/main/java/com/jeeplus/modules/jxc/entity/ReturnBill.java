/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.jxc.entity.ReturnOrder;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 退货单据Entity
 * @author FxLsoft
 * @version 2018-12-23
 */
public class ReturnBill extends DataEntity<ReturnBill> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 单号
	private ReturnOrder returnOrder;		// 退货单号
	private Double returnMoney;		// 退款金额
	private Integer status;		// 审核状态
	private Date approveDate;		// 审核时间
	private Date cancelDate;		// 作废时间
	
	public ReturnBill() {
		super();
	}

	public ReturnBill(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@NotNull(message="退货单号不能为空")
	@ExcelField(title="退货单号", fieldType=ReturnOrder.class, value="returnOrder.no", align=2, sort=2)
	public ReturnOrder getReturnOrder() {
		return returnOrder;
	}

	public void setReturnOrder(ReturnOrder returnOrder) {
		this.returnOrder = returnOrder;
	}
	
	@NotNull(message="退款金额不能为空")
	@ExcelField(title="退款金额", align=2, sort=3)
	public Double getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(Double returnMoney) {
		this.returnMoney = returnMoney;
	}
	
	@ExcelField(title="审核状态", dictType="", align=2, sort=4)
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