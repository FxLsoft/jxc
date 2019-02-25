/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;


import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 付款记录Entity
 * @author FxLsoft
 * @version 2019-02-13
 */
public class OperOrderPay extends DataEntity<OperOrderPay> {
	
	private static final long serialVersionUID = 1L;
	private String no;
	private OperOrder operOrder;		// 单据 父类
	private String payType;		// 付款类型（-1：付款，1：收款）
	private Double price;		// 金额
	private Date startDate;
	private Date endDate;
	
	public OperOrderPay() {
		super();
	}

	public OperOrderPay(String id){
		super(id);
	}

	public OperOrderPay(OperOrder operOrder){
		this.operOrder = operOrder;
	}

	public OperOrder getOperOrder() {
		return operOrder;
	}

	public void setOperOrder(OperOrder operOrder) {
		this.operOrder = operOrder;
	}
	
	@ExcelField(title="付款类型（-1：付款，1：收款）", dictType="pay_type", align=2, sort=2)
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@ExcelField(title="金额", align=2, sort=3)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}