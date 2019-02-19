/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 电子秤销售记录详情Entity
 * @author FxLsoft
 * @version 2019-02-19
 */
public class BalanceSaleDetail extends DataEntity<BalanceSaleDetail> {
	
	private static final long serialVersionUID = 1L;
	private BalanceSale balanceSale;		// 电子秤销售 父类
	private String saleNo;		// 销售编号
	private String orderNo;		// 序号
	private Double salePrice;		// 销售价
	private String productNo;		// 商品编号
	private String groupNo;		// 组编号
	private String officeNo;		// 部门编号
	private Double costPrice;		// 成本价
	private Double amount;		// 重量
	private String unit;		// 单位
	private String isReturn;		// 是否退货
	private Double tax;		// 税额
	private Double originPrice;		// 原始单价
	private Double realPrice;		// 实际单价
	private String productName;		// 商品名称
	
	public BalanceSaleDetail() {
		super();
	}

	public BalanceSaleDetail(String id){
		super(id);
	}

	public BalanceSaleDetail(BalanceSale balanceSale){
		this.balanceSale = balanceSale;
	}

	public BalanceSale getBalanceSale() {
		return balanceSale;
	}

	public void setBalanceSale(BalanceSale balanceSale) {
		this.balanceSale = balanceSale;
	}
	
	@ExcelField(title="销售编号", align=2, sort=2)
	public String getSaleNo() {
		return saleNo;
	}

	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	
	@ExcelField(title="序号", align=2, sort=3)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@ExcelField(title="销售价", align=2, sort=4)
	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	
	@ExcelField(title="商品编号", align=2, sort=5)
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	@ExcelField(title="组编号", align=2, sort=6)
	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	
	@ExcelField(title="部门编号", align=2, sort=7)
	public String getOfficeNo() {
		return officeNo;
	}

	public void setOfficeNo(String officeNo) {
		this.officeNo = officeNo;
	}
	
	@ExcelField(title="成本价", align=2, sort=8)
	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	
	@ExcelField(title="重量", align=2, sort=9)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@ExcelField(title="单位", align=2, sort=10)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="是否退货", dictType="yes_no", align=2, sort=11)
	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	
	@ExcelField(title="税额", align=2, sort=12)
	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}
	
	@ExcelField(title="原始单价", align=2, sort=13)
	public Double getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(Double originPrice) {
		this.originPrice = originPrice;
	}
	
	@ExcelField(title="实际单价", align=2, sort=14)
	public Double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}
	
	@ExcelField(title="商品名称", align=2, sort=15)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}