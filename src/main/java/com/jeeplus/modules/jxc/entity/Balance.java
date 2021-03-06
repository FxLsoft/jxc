/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.jxc.entity.Store;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 电子秤管理Entity
 * @author FxLsoft
 * @version 2019-02-15
 */
public class Balance extends DataEntity<Balance> {
	
	private static final long serialVersionUID = 1L;
	private String no;		// 编号
	private String name;		// 品牌
	private Store store;		// 所属店
	private String baseUnit;		// 基本单位
	
	public Balance() {
		super();
	}

	public Balance(String id){
		super(id);
	}

	@ExcelField(title="编号", align=2, sort=1)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@ExcelField(title="品牌", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="所属店不能为空")
	@ExcelField(title="所属店", fieldType=Store.class, value="store.name", align=2, sort=3)
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	@ExcelField(title="基本单位", dictType="weight_base_unit", align=2, sort=4)
	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}
	
}