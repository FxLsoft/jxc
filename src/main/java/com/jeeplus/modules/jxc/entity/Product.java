/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.jxc.entity.Agency;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.jxc.entity.Category;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品Entity
 * @author FxLsoft
 * @version 2019-02-11
 */
public class Product extends DataEntity<Product> {
	
	private static final long serialVersionUID = 1L;
	private String name;			// 名称
	private String brevityCode;		// 简码
	private String isWeight;		// 是否计重（0：否；1：是）
	private String weightNo;		// 计重编号
	private Agency agency;			// 经销商
	private Category category;		// 所属类型
	private List<Price> priceList = Lists.newArrayList();		// 子表列表
	private Double amount;
	private Double storageAmount;
	private Integer baseUnit;
	private List<Storage> storageList = Lists.newArrayList();
	
	public Product() {
		super();
	}

	public Product(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="简码", align=2, sort=2)
	public String getBrevityCode() {
		return brevityCode;
	}

	public void setBrevityCode(String brevityCode) {
		this.brevityCode = brevityCode;
	}
	
	@ExcelField(title="是否计重（0：否；1：是）", dictType="yes_no", align=2, sort=3)
	public String getIsWeight() {
		return isWeight;
	}

	public void setIsWeight(String isWeight) {
		this.isWeight = isWeight;
	}
	
	@ExcelField(title="计重编号", align=2, sort=4)
	public String getWeightNo() {
		return weightNo;
	}

	public void setWeightNo(String weightNo) {
		this.weightNo = weightNo;
	}
	
	@NotNull(message="经销商不能为空")
	@ExcelField(title="经销商", fieldType=Agency.class, value="agency.name", align=2, sort=5)
	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	
	@NotNull(message="所属类型不能为空")
	@ExcelField(title="所属类型", fieldType=Category.class, value="category.name", align=2, sort=6)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public List<Price> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<Price> priceList) {
		this.priceList = priceList;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getStorageAmount() {
		return storageAmount;
	}

	public void setStorageAmount(Double storageAmount) {
		this.storageAmount = storageAmount;
	}

	public List<Storage> getStorageList() {
		return storageList;
	}

	public void setStorageList(List<Storage> storageList) {
		this.storageList = storageList;
	}

	public Integer getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(Integer baseUnit) {
		this.baseUnit = baseUnit;
	}
}