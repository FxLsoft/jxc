/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 经销商Entity
 * @author FxLsoft
 * @version 2019-02-11
 */
public class Agency extends DataEntity<Agency> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String area;		// 省市区
	private String address;		// 地址
	private String linkman;		// 联系人
	private String phone;		// 联系方式
	private String plateNumber;		// 车牌号
	
	public Agency() {
		super();
	}

	public Agency(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="省市区", align=2, sort=2)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@ExcelField(title="地址", align=2, sort=3)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="联系人", align=2, sort=4)
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	@ExcelField(title="联系方式", align=2, sort=5)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="车牌号", align=2, sort=6)
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
}