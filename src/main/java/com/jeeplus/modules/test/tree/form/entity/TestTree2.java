/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.tree.form.entity;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 组织机构Entity
 * @author liugf
 * @version 2018-06-12
 */
public class TestTree2 extends TreeEntity<TestTree2> {
	
	private static final long serialVersionUID = 1L;
	
	
	public TestTree2() {
		super();
	}

	public TestTree2(String id){
		super(id);
	}

	public  TestTree2 getParent() {
			return parent;
	}
	
	@Override
	public void setParent(TestTree2 parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}