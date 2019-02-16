/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 价格表Entity
 * @author FxLsoft
 * @version 2019-02-11
 */
public class Price extends DataEntity<Price> {
	
	private static final long serialVersionUID = 1L;
	private Product product;		// 商品 父类
	private String unit;		// 单位
	private Double ratio;		// 换算比例
	private Double costPrice;		// 进价
	private Double advancePrice;		// 预售价
	private String isBasic;		// 是否基本单位（0：是；1：否）
	private Double beginRatio;		// 开始 换算比例
	private Double endRatio;		// 结束 换算比例
	private Double beginCostPrice;		// 开始 进价
	private Double endCostPrice;		// 结束 进价
	private Double beginAdvancePrice;		// 开始 预售价
	private Double endAdvancePrice;		// 结束 预售价
	private Storage storage;
	
	public Price() {
		super();
	}

	public Price(String id){
		super(id);
	}

	public Price(Product product){
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@ExcelField(title="单位", align=2, sort=2)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@NotNull(message="换算比例不能为空")
	@ExcelField(title="换算比例", align=2, sort=3)
	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
	
	@NotNull(message="进价不能为空")
	@ExcelField(title="进价", align=2, sort=4)
	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	
	@NotNull(message="预售价不能为空")
	@ExcelField(title="预售价", align=2, sort=5)
	public Double getAdvancePrice() {
		return advancePrice;
	}

	public void setAdvancePrice(Double advancePrice) {
		this.advancePrice = advancePrice;
	}
	
	@ExcelField(title="是否基本单位（0：是；1：否）", dictType="yes_no", align=2, sort=6)
	public String getIsBasic() {
		return isBasic;
	}

	public void setIsBasic(String isBasic) {
		this.isBasic = isBasic;
	}
	
	public Double getBeginRatio() {
		return beginRatio;
	}

	public void setBeginRatio(Double beginRatio) {
		this.beginRatio = beginRatio;
	}
	
	public Double getEndRatio() {
		return endRatio;
	}

	public void setEndRatio(Double endRatio) {
		this.endRatio = endRatio;
	}
		
	public Double getBeginCostPrice() {
		return beginCostPrice;
	}

	public void setBeginCostPrice(Double beginCostPrice) {
		this.beginCostPrice = beginCostPrice;
	}
	
	public Double getEndCostPrice() {
		return endCostPrice;
	}

	public void setEndCostPrice(Double endCostPrice) {
		this.endCostPrice = endCostPrice;
	}
		
	public Double getBeginAdvancePrice() {
		return beginAdvancePrice;
	}

	public void setBeginAdvancePrice(Double beginAdvancePrice) {
		this.beginAdvancePrice = beginAdvancePrice;
	}
	
	public Double getEndAdvancePrice() {
		return endAdvancePrice;
	}

	public void setEndAdvancePrice(Double endAdvancePrice) {
		this.endAdvancePrice = endAdvancePrice;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}
		
}