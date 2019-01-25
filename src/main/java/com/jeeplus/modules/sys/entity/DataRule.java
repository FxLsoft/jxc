/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.entity;


import com.jeeplus.core.persistence.DataEntity;

import org.apache.commons.lang3.StringEscapeUtils;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 数据权限Entity
 * @author lgf
 * @version 2017-04-02
 */
public class DataRule extends DataEntity<DataRule> {
	
	private static final long serialVersionUID = 1L;
	private String menuId;		// 所属菜单
	private String name;		// 数据规则名称
	private String className;   //实体类名
	private String field;		// 规则字段
	private String express;		// 规则条件
	private String value;		// 规则值
	private String sqlSegment;		// 自定义sql
	
	public DataRule() {
		super();
	}

	public DataRule(String id){
		super(id);
	}

	@ExcelField(title="所属菜单", align=2, sort=1)
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	@ExcelField(title="数据规则名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="规则字段", align=2, sort=3)
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	@ExcelField(title="规则条件", dictType="t_express", align=2, sort=4)
	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}
	
	@ExcelField(title="规则值", align=2, sort=5)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@ExcelField(title="自定义sql", align=2, sort=6)
	public String getSqlSegment() {
		return sqlSegment;
	}

	public void setSqlSegment(String sqlSegment) {
		this.sqlSegment = sqlSegment;
	}
	
	public String getDataScopeSql(){
		StringBuffer sqlBuffer = new StringBuffer();
		if(StringUtils.isNotBlank(field)&&StringUtils.isNotBlank(value)){
			sqlBuffer.append(" AND " +field+" "+StringEscapeUtils.unescapeHtml4(express)+" "+value+" ");
		}
		if(StringUtils.isNotBlank(sqlSegment)){
			sqlBuffer.append(" AND "+StringEscapeUtils.unescapeHtml4(sqlSegment)+" ");
		}
		
		return sqlBuffer.toString();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}