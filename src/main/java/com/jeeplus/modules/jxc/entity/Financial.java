/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 报表Entity
 * @author 周涛
 * @version 2018-12-22
 */
public class Financial extends DataEntity<Financial> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 报表类型
	private Double purchaseAmount;		// 采购总金额
	private Double stockAmount;		// 入库总金额
	private Double wholesaleAmount;		// 批发总金额
	private Double retailAmount;		// 零售总金额
	private Double returnAmount;		// 退货总金额
	private Double wholesaleGatherAmount;		// 批发已收金额
	private Double retailGatherAmount;		// 零售已收金额
	private Double gatherAmount;		// 已收金额
	private Double stockBillAmount;		// 入库已付金额
	private Double returnBillAmount;		// 退货已付金额
	private Double billAmount;		// 已付金额
	private Date beginCreateDate;		// 开始 更新时间
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

	private Date endCreateDate;		// 结束 更新时间
	
	public Financial() {
		super();
	}

	public Financial(String id){
		super(id);
	}

	@NotNull(message="报表类型不能为空")
	@ExcelField(title="报表类型", dictType="financial_type", align=2, sort=1)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@ExcelField(title="采购总金额", align=2, sort=2)
	public Double getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(Double purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	
	@ExcelField(title="入库总金额", align=2, sort=3)
	public Double getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Double stockAmount) {
		this.stockAmount = stockAmount;
	}
	
	@ExcelField(title="批发总金额", align=2, sort=4)
	public Double getWholesaleAmount() {
		return wholesaleAmount;
	}

	public void setWholesaleAmount(Double wholesaleAmount) {
		this.wholesaleAmount = wholesaleAmount;
	}
	
	@ExcelField(title="零售总金额", align=2, sort=5)
	public Double getRetailAmount() {
		return retailAmount;
	}

	public void setRetailAmount(Double retailAmount) {
		this.retailAmount = retailAmount;
	}
	
	@ExcelField(title="退货总金额", align=2, sort=6)
	public Double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}
	
	@ExcelField(title="批发已收金额", align=2, sort=7)
	public Double getWholesaleGatherAmount() {
		return wholesaleGatherAmount;
	}

	public void setWholesaleGatherAmount(Double wholesaleGatherAmount) {
		this.wholesaleGatherAmount = wholesaleGatherAmount;
	}
	
	@ExcelField(title="零售已收金额", align=2, sort=8)
	public Double getRetailGatherAmount() {
		return retailGatherAmount;
	}

	public void setRetailGatherAmount(Double retailGatherAmount) {
		this.retailGatherAmount = retailGatherAmount;
	}
	
	@ExcelField(title="已收金额", align=2, sort=9)
	public Double getGatherAmount() {
		return gatherAmount;
	}

	public void setGatherAmount(Double gatherAmount) {
		this.gatherAmount = gatherAmount;
	}
	
	@ExcelField(title="入库已付金额", align=2, sort=10)
	public Double getStockBillAmount() {
		return stockBillAmount;
	}

	public void setStockBillAmount(Double stockBillAmount) {
		this.stockBillAmount = stockBillAmount;
	}
	
	@ExcelField(title="退货已付金额", align=2, sort=11)
	public Double getReturnBillAmount() {
		return returnBillAmount;
	}

	public void setReturnBillAmount(Double returnBillAmount) {
		this.returnBillAmount = returnBillAmount;
	}
	
	@ExcelField(title="已付金额", align=2, sort=12)
	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}
	
}