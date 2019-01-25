/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.treetable.form.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 车辆Entity
 * @author lgf
 * @version 2018-06-12
 */
public class Car2 extends DataEntity<Car2> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 品牌
	private CarKind2 kind;		// 车系 父类
	
	public Car2() {
		super();
	}

	public Car2(String id){
		super(id);
	}

	public Car2(CarKind2 kind){
		this.kind = kind;
	}

	@ExcelField(title="品牌", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public CarKind2 getKind() {
		return kind;
	}

	public void setKind(CarKind2 kind) {
		this.kind = kind;
	}
	
}