/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.jxc.entity;

import com.jeeplus.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.User;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 门店Entity
 * @author FxLsoft
 * @version 2019-02-11
 */
public class Store extends DataEntity<Store> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String area;		// 省市区
	private String address;		// 具体地址
	private Office office;		// 管理公司
	private User admin;		// 管理者
	private Double lon;		// 经度
	private Double lat;		// 纬度
	private String officeName;
	
	public Store() {
		super();
	}

	public Store(String id){
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
	
	@ExcelField(title="具体地址", align=2, sort=3)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotNull(message="管理公司不能为空")
	@ExcelField(title="管理公司", fieldType=Office.class, value="office.name", align=2, sort=4)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@NotNull(message="管理者不能为空")
	@ExcelField(title="管理者", fieldType=User.class, value="admin.name", align=2, sort=5)
	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
	
	@ExcelField(title="经度", align=2, sort=6)
	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	@ExcelField(title="纬度", align=2, sort=7)
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
}