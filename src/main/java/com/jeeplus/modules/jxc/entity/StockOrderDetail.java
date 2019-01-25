/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 入库详情Entity
 * @author 周涛
 * @version 2018-12-25
 */
public class StockOrderDetail extends DataEntity<StockOrderDetail> {
	
	private static final long serialVersionUID = 1L;
	private StockOrder stockOrder;		// 入库单号 父类
	private Product product;		// 商品
	private Double price;		// 单价
	private Double quantity;		// 数量
	private String unit;		// 单位
	private String intro;		// 规格说明
	private Integer isRetail = 0;		// 是否可零售
	private Date releaseDate;		// 生产日期
	private Date expiryDate;		// 保质期
	
	public StockOrderDetail() {
		super();
	}

	public StockOrderDetail(String id){
		super(id);
	}

	public StockOrderDetail(StockOrder stockOrder){
		this.stockOrder = stockOrder;
	}

	public StockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}
	
	@NotNull(message="商品不能为空")
	@ExcelField(title="商品", fieldType=Product.class, value="product.name", align=2, sort=2)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@NotNull(message="单价不能为空")
	@ExcelField(title="单价", align=2, sort=3)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=4)
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	@ExcelField(title="单位", align=2, sort=5)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="规格说明", align=2, sort=6)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@ExcelField(title="是否可零售", dictType="yes_no", align=2, sort=7)
	public Integer getIsRetail() {
		return isRetail;
	}

	public void setIsRetail(Integer isRetail) {
		this.isRetail = isRetail;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="生产日期不能为空")
	@ExcelField(title="生产日期", align=2, sort=8)
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="保质期不能为空")
	@ExcelField(title="保质期", align=2, sort=9)
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
}