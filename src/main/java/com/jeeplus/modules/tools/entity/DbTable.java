/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tools.entity;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 业务表Entity
 * @author jeeplus
 * @version 2016-10-15
 */
public class DbTable extends DataEntity<DbTable> {
	
	private static final long serialVersionUID = 1L;
	private String name; 	// 名称
	private String comments;		// 描述
	private List<DbTableColumn> columnList = Lists.newArrayList();	// 表列
	

	public DbTable() {
		super();
	}

	public DbTable(String id){
		super(id);
	}

	@Length(min=1, max=200)
	public String getName() {
		return StringUtils.lowerCase(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	public List<DbTableColumn> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<DbTableColumn> columnList) {
		this.columnList = columnList;
	}

	/**
	 * 获取列名和说明
	 * @return
	 */
	public String getNameAndComments() {
		return getName() + (comments == null ? "" : "  :  " + comments);
	}


}


