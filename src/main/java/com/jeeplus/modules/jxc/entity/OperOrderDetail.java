/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.entity.Price;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 单据详情Entity
 * @author FxLsoft
 * @version 2019-02-13
 */
public class OperOrderDetail extends DataEntity<OperOrderDetail> {
	
	private static final long serialVersionUID = 1L;
	private OperOrder operOrder;		// 单据 父类
	private String operType;		// 类型（-1：减库，1：加库）
	private Product product;		// 商品
	private Price price;		// 价格属性
	private Double amount;		// 数量
	private Double operPrice;		// 价格信息
	private Double discount;		// 折扣
	private Double beginAmount;		// 开始 数量
	private Double endAmount;		// 结束 数量
	private Double beginOperPrice;		// 开始 价格信息
	private Double endOperPrice;		// 结束 价格信息
	private Double beginDiscount;		// 开始 折扣
	private Double endDiscount;		// 结束 折扣
	
	public OperOrderDetail() {
		super();
	}

	public OperOrderDetail(String id){
		super(id);
	}

	public OperOrderDetail(OperOrder operOrder){
		this.operOrder = operOrder;
	}

	public OperOrder getOperOrder() {
		return operOrder;
	}

	public void setOperOrder(OperOrder operOrder) {
		this.operOrder = operOrder;
	}
	
	@ExcelField(title="类型（-1：减库，1：加库）", dictType="oper_type", align=2, sort=2)
	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	@ExcelField(title="商品", fieldType=Product.class, value="product.name", align=2, sort=3)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@ExcelField(title="价格属性", fieldType=Price.class, value="price.costPrice", align=2, sort=4)
	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
	
	@ExcelField(title="数量", align=2, sort=5)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@ExcelField(title="价格信息", align=2, sort=6)
	public Double getOperPrice() {
		return operPrice;
	}

	public void setOperPrice(Double operPrice) {
		this.operPrice = operPrice;
	}
	
	@ExcelField(title="折扣", align=2, sort=7)
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	public Double getBeginAmount() {
		return beginAmount;
	}

	public void setBeginAmount(Double beginAmount) {
		this.beginAmount = beginAmount;
	}
	
	public Double getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(Double endAmount) {
		this.endAmount = endAmount;
	}
		
	public Double getBeginOperPrice() {
		return beginOperPrice;
	}

	public void setBeginOperPrice(Double beginOperPrice) {
		this.beginOperPrice = beginOperPrice;
	}
	
	public Double getEndOperPrice() {
		return endOperPrice;
	}

	public void setEndOperPrice(Double endOperPrice) {
		this.endOperPrice = endOperPrice;
	}
		
	public Double getBeginDiscount() {
		return beginDiscount;
	}

	public void setBeginDiscount(Double beginDiscount) {
		this.beginDiscount = beginDiscount;
	}
	
	public Double getEndDiscount() {
		return endDiscount;
	}

	public void setEndDiscount(Double endDiscount) {
		this.endDiscount = endDiscount;
	}
		
}