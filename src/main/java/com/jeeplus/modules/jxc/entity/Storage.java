/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.jxc.entity.Store;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.jxc.entity.Product;
import com.jeeplus.modules.jxc.entity.Price;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 库存Entity
 * @author FxLsoft
 * @version 2019-02-12
 */
public class Storage extends DataEntity<Storage> {
	
	private static final long serialVersionUID = 1L;
	private Store store;		// 门店
	private Product product;		// 商品
	private Price price;		// 价格
	private Double amount;		// 数量
	private Double beginAmount;		// 开始 数量
	private Double endAmount;		// 结束 数量
	
	public Storage() {
		super();
	}

	public Storage(String id){
		super(id);
	}

	@NotNull(message="门店不能为空")
	@ExcelField(title="门店", fieldType=Store.class, value="store.name", align=2, sort=1)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	@NotNull(message="商品不能为空")
	@ExcelField(title="商品", fieldType=Product.class, value="product.name", align=2, sort=2)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@NotNull(message="价格不能为空")
	@ExcelField(title="单位", fieldType=Price.class, value="price.unit", align=2, sort=3)
	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=4)
	public Double getAmount() {
		if (amount == null) {
			amount = 0d;
		}
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
		
}