/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品信息Entity
 * @author FxLsoft
 * @version 2018-12-22
 */
public class Product extends DataEntity<Product> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商品名称
	private String brevityCode;		// 商品简码
	private Double price;		// 参考价格
	private String supplierName;		// 供应商名称
	private String supplierPhone;		// 供应商电话
	private String supplierCardNo;		// 供应商银行账号
	private String supplierAddress;		// 供应商地址
	private Double count;
	private String unit;
	
	public Product() {
		super();
	}

	public Product(String id){
		super(id);
	}

	@ExcelField(title="商品名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="商品简码", align=2, sort=2)
	public String getBrevityCode() {
		return brevityCode;
	}

	public void setBrevityCode(String brevityCode) {
		this.brevityCode = brevityCode;
	}
	
	@ExcelField(title="参考价格", align=2, sort=3)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="供应商名称", align=2, sort=4)
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	@ExcelField(title="供应商电话", align=2, sort=5)
	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}
	
	@ExcelField(title="供应商银行账号", align=2, sort=6)
	public String getSupplierCardNo() {
		return supplierCardNo;
	}

	public void setSupplierCardNo(String supplierCardNo) {
		this.supplierCardNo = supplierCardNo;
	}
	
	@ExcelField(title="供应商地址", align=2, sort=7)
	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}