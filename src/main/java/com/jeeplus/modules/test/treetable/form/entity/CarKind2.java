/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.treetable.form.entity;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 车系Entity
 * @author lgf
 * @version 2018-06-12
 */
public class CarKind2 extends TreeEntity<CarKind2> {
	
	private static final long serialVersionUID = 1L;
	
	private List<Car2> car2List = Lists.newArrayList();		// 子表列表
	
	public CarKind2() {
		super();
	}

	public CarKind2(String id){
		super(id);
	}

	public  CarKind2 getParent() {
			return parent;
	}
	
	@Override
	public void setParent(CarKind2 parent) {
		this.parent = parent;
		
	}
	
	public List<Car2> getCar2List() {
		return car2List;
	}

	public void setCar2List(List<Car2> car2List) {
		this.car2List = car2List;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}