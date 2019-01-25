/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.jxc.entity.Product;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 商品仓库Entity
 * @author 周涛
 * @version 2018-12-25
 */
public class Warehouse extends DataEntity<Warehouse> {
	
	private static final long serialVersionUID = 1L;
	private Product product;		// 商品
	private Double price;		// 入库单价
	private Double quantity;		// 数量
	private String unit;		// 单位
	private String intro;		// 规格说明
	private Integer isRetail;		// 是否可零售
	private Date releaseDate;		// 生产日期
	private Date expiryDate;		// 保质期
	private Date beginExpiryDate;		// 开始 保质期
	private Date endExpiryDate;		// 结束 保质期
	private String company;		// 店名
	
	public Warehouse() {
		super();
	}

	public Warehouse(String id){
		super(id);
	}

	@NotNull(message="商品不能为空")
	@ExcelField(title="商品", fieldType=Product.class, value="product.name", align=2, sort=1)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@NotNull(message="入库单价不能为空")
	@ExcelField(title="入库单价", align=2, sort=2)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=3)
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	@ExcelField(title="单位", align=2, sort=4)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="规格说明", align=2, sort=5)
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@ExcelField(title="是否可零售", dictType="yes_no", align=2, sort=6)
	public Integer getIsRetail() {
		return isRetail;
	}

	public void setIsRetail(Integer isRetail) {
		this.isRetail = isRetail;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="生产日期", align=2, sort=7)
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="保质期", align=2, sort=8)
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	@ExcelField(title="店名", align=2, sort=9)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public Date getBeginExpiryDate() {
		return beginExpiryDate;
	}

	public void setBeginExpiryDate(Date beginExpiryDate) {
		this.beginExpiryDate = beginExpiryDate;
	}
	
	public Date getEndExpiryDate() {
		return endExpiryDate;
	}

	public void setEndExpiryDate(Date endExpiryDate) {
		this.endExpiryDate = endExpiryDate;
	}
	
}