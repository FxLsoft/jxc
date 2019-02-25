/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 商品类型Entity
 * @author FxLsoft
 * @version 2019-02-11
 */
public class Category extends TreeEntity<Category> {
	
	private static final long serialVersionUID = 1L;
	
	
	public Category() {
		super();
	}

	public Category(String id){
		super(id);
	}

	public  Category getParent() {
			return parent;
	}
	
	@Override
	public void setParent(Category parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}