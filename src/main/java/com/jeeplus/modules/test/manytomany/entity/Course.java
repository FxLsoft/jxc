/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.manytomany.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 课程Entity
 * @author lgf
 * @version 2018-06-12
 */
public class Course extends DataEntity<Course> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 课程名
	
	public Course() {
		super();
	}

	public Course(String id){
		super(id);
	}

	@ExcelField(title="课程名", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}