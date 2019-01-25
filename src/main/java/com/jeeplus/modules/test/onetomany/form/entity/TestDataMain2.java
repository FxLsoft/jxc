/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.form.entity;

import com.jeeplus.modules.sys.entity.User;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Area;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 票务代理Entity
 * @author liugf
 * @version 2018-06-12
 */
public class TestDataMain2 extends DataEntity<TestDataMain2> {
	
	private static final long serialVersionUID = 1L;
	private User tuser;		// 归属用户
	private Office office;		// 归属部门
	private Area area;		// 归属区域
	private String name;		// 名称
	private String sex;		// 性别
	private Date inDate;		// 加入日期
	private Date beginInDate;		// 开始 加入日期
	private Date endInDate;		// 结束 加入日期
	private List<TestDataChild21> testDataChild21List = Lists.newArrayList();		// 子表列表
	private List<TestDataChild22> testDataChild22List = Lists.newArrayList();		// 子表列表
	private List<TestDataChild23> testDataChild23List = Lists.newArrayList();		// 子表列表
	
	public TestDataMain2() {
		super();
	}

	public TestDataMain2(String id){
		super(id);
	}

	@NotNull(message="归属用户不能为空")
	@ExcelField(title="归属用户", fieldType=User.class, value="tuser.name", align=2, sort=1)
	public User getTuser() {
		return tuser;
	}

	public void setTuser(User tuser) {
		this.tuser = tuser;
	}
	
	@NotNull(message="归属部门不能为空")
	@ExcelField(title="归属部门", fieldType=Office.class, value="office.name", align=2, sort=2)
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@NotNull(message="归属区域不能为空")
	@ExcelField(title="归属区域", fieldType=Area.class, value="area.name", align=2, sort=3)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@ExcelField(title="名称", align=2, sort=4)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="性别", dictType="sex", align=2, sort=5)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="加入日期不能为空")
	@ExcelField(title="加入日期", align=2, sort=6)
	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	
	public Date getBeginInDate() {
		return beginInDate;
	}

	public void setBeginInDate(Date beginInDate) {
		this.beginInDate = beginInDate;
	}
	
	public Date getEndInDate() {
		return endInDate;
	}

	public void setEndInDate(Date endInDate) {
		this.endInDate = endInDate;
	}
		
	public List<TestDataChild21> getTestDataChild21List() {
		return testDataChild21List;
	}

	public void setTestDataChild21List(List<TestDataChild21> testDataChild21List) {
		this.testDataChild21List = testDataChild21List;
	}
	public List<TestDataChild22> getTestDataChild22List() {
		return testDataChild22List;
	}

	public void setTestDataChild22List(List<TestDataChild22> testDataChild22List) {
		this.testDataChild22List = testDataChild22List;
	}
	public List<TestDataChild23> getTestDataChild23List() {
		return testDataChild23List;
	}

	public void setTestDataChild23List(List<TestDataChild23> testDataChild23List) {
		this.testDataChild23List = testDataChild23List;
	}
}