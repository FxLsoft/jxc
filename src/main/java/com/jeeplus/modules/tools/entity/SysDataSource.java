/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.tools.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 多数据源Entity
 * @author liugf
 * @version 2017-07-27
 */
public class SysDataSource extends DataEntity<SysDataSource> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 连接名称
	private String enname;  //连接英文名
	private String dbUserName;		// 数据库用户名
	private String dbPassword;		// 数据库密码
	private String dbUrl;		// 数据库链接
	private String dbDriver;		// 数据库驱动类
	
	public SysDataSource() {
		super();
	}

	public SysDataSource(String id){
		super(id);
	}

	@ExcelField(title="数据库名称", align=2, sort=1)
	public String getName() {
		return name;
	}


	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="数据库用户名", align=2, sort=2)
	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	
	@ExcelField(title="数据库密码", align=2, sort=3)
	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	@ExcelField(title="数据库链接", align=2, sort=4)
	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	@ExcelField(title="数据库驱动类", dictType="db_driver", align=2, sort=5)
	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}
	
}