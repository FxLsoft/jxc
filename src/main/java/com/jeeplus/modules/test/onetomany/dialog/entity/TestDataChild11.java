/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.dialog.entity;

import com.jeeplus.modules.sys.entity.Area;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 火车票Entity
 * @author liugf
 * @version 2018-06-12
 */
public class TestDataChild11 extends DataEntity<TestDataChild11> {
	
	private static final long serialVersionUID = 1L;
	private Area startArea;		// 出发地
	private Area endArea;		// 目的地
	private Date starttime;		// 出发时间
	private Double price;		// 代理价格
	private String isHave;		// 是否有票
	private TestDataMain1 testDataMain;		// 业务主表ID 父类
	
	public TestDataChild11() {
		super();
	}

	public TestDataChild11(String id){
		super(id);
	}

	public TestDataChild11(TestDataMain1 testDataMain){
		this.testDataMain = testDataMain;
	}

	@NotNull(message="出发地不能为空")
	@ExcelField(title="出发地", fieldType=Area.class, value="startArea.name", align=2, sort=1)
	public Area getStartArea() {
		return startArea;
	}

	public void setStartArea(Area startArea) {
		this.startArea = startArea;
	}
	
	@NotNull(message="目的地不能为空")
	@ExcelField(title="目的地", fieldType=Area.class, value="endArea.name", align=2, sort=2)
	public Area getEndArea() {
		return endArea;
	}

	public void setEndArea(Area endArea) {
		this.endArea = endArea;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="出发时间不能为空")
	@ExcelField(title="出发时间", align=2, sort=3)
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	
	@NotNull(message="代理价格不能为空")
	@ExcelField(title="代理价格", align=2, sort=4)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@ExcelField(title="是否有票", dictType="yes_no", align=2, sort=5)
	public String getIsHave() {
		return isHave;
	}

	public void setIsHave(String isHave) {
		this.isHave = isHave;
	}
	
	public TestDataMain1 getTestDataMain() {
		return testDataMain;
	}

	public void setTestDataMain(TestDataMain1 testDataMain) {
		this.testDataMain = testDataMain;
	}
	
}