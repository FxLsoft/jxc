/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客户管理Entity
 * @author FxLsoft
 * @version 2019-02-23
 */
public class Customer extends DataEntity<Customer> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 客户名称
	private String phone;		// 联系方式
	private String area;		// 省市区
	private String address;		// 详细地址
	
	public Customer() {
		super();
	}

	public Customer(String id){
		super(id);
	}

	@ExcelField(title="客户名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="联系方式", align=2, sort=2)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="省市区", align=2, sort=3)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@ExcelField(title="详细地址", align=2, sort=4)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}